import os
from confluent_kafka import avro
from confluent_kafka.avro import AvroProducer
from faker import Faker

is_docker = os.getenv('DOCKER')
kafka_host = 'kafka{0}' if is_docker else 'localhost'
kafka_port = '9092' if is_docker else '9093'
schema_registry_host = 'schema-registry' if is_docker else 'localhost'
schema_registry_port = '8081'

bootstrap_servers = ','.join(['{0}:{1}{2}'.format(kafka_host.format(n), n, kafka_port) for n in [1, 2, 3]])
schema_registry_server = 'http://{0}:{1}'.format(schema_registry_host, schema_registry_port)

print('Connecting to', bootstrap_servers, 'and', schema_registry_server)

topic = 'avro-topic'


def delivery_report(err, msg):
    # Called once for each message produced to indicate delivery result.
    # Triggered by poll() or flush().
    if err is not None:
        print('Message delivery failed: {}'.format(err))
    else:
        print('Message {} delivered to {} [{}]'.format(msg.value(), msg.topic(), msg.partition()))


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

avroProducer = AvroProducer(
    {
        'bootstrap.servers': bootstrap_servers,
        'on_delivery': delivery_report,
        'schema.registry.url': schema_registry_server
    },
    default_value_schema=value_schema
)

fake = Faker()
data_storage = [{"name": fake.name()} for n in range(10)]

for data in data_storage:
    # Trigger any available delivery report callbacks from previous produce() calls
    avroProducer.poll(0)

    # Asynchronously produce a message, the delivery report callback
    # will be triggered from poll() above, or flush() below, when the message has
    # been successfully delivered or failed permanently.
    avroProducer.produce(topic=topic, value=data)

avroProducer.flush()
