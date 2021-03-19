import os
from confluent_kafka import avro
from confluent_kafka.avro import AvroProducer
from faker import Faker

is_docker = os.getenv('DOCKER')
hosts_inside_docker = ['kafka{0}:{0}9092'.format(n) for n in [1, 2, 3]]
hosts_outside_docker = ['localhost:{0}9093'.format(n) for n in [1, 2, 3]]
hostnames = ','.join(hosts_inside_docker) if is_docker else ','.join(hosts_outside_docker)
topic = 'avro-topic'
schema_registry = 'http://localhost:8081'

value_schema_str = """
{
   "namespace": "my.test",
   "name": "person",
   "type": "record",
   "fields" : [
     {
       "name" : "name",
       "type" : "string"
     }
   ]
}
"""

value_schema = avro.loads(value_schema_str)


def delivery_report(err, msg):
    # Called once for each message produced to indicate delivery result.
    # Triggered by poll() or flush().
    if err is not None:
        print('Message delivery failed: {}'.format(err))
    else:
        print('Message delivered to {} [{}]'.format(msg.topic(), msg.partition()))


avroProducer = AvroProducer({
    'bootstrap.servers': hostnames,
    'on_delivery': delivery_report,
    'schema.registry.url': schema_registry
}, default_value_schema=value_schema)
fake = Faker()
data_storage = [{"name": fake.name()} for n in range(1)]

for data in data_storage:
    # Trigger any available delivery report callbacks from previous produce() calls
    avroProducer.poll(0)

    # Asynchronously produce a message, the delivery report callback
    # will be triggered from poll() above, or flush() below, when the message has
    # been successfully delivered or failed permanently.
    avroProducer.produce(topic=topic, value=data)

avroProducer.flush()
