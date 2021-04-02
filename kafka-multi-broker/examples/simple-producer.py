import os
from confluent_kafka import Producer
from faker import Faker

is_docker = os.getenv('DOCKER')
kafka_host = 'kafka{0}' if is_docker else 'localhost'
kafka_port = '9092' if is_docker else '9093'
schema_registry_host = 'schema-registry' if is_docker else 'localhost'
schema_registry_port = '8081'

bootstrap_servers = ','.join(['{0}:{1}{2}'.format(kafka_host.format(n), n, kafka_port) for n in [1, 2, 3]])

print('Connecting to', bootstrap_servers)

topic = 'simple-topic'


def delivery_report(err, msg):
    # Called once for each message produced to indicate delivery result.
    # Triggered by poll() or flush().
    if err is not None:
        print('Message delivery failed: {}'.format(err))
    else:
        print('Message {} delivered to {} [{}]'.format(msg.value(), msg.topic(), msg.partition()))


producer = Producer({'bootstrap.servers': bootstrap_servers})
fake = Faker()
data_storage = [fake.name() for n in range(10)]

for data in data_storage:
    # Trigger any available delivery report callbacks from previous produce() calls
    producer.poll(0)

    # Asynchronously produce a message, the delivery report callback
    # will be triggered from poll() above, or flush() below, when the message has
    # been successfully delivered or failed permanently.
    producer.produce(topic, data.encode('utf-8'), callback=delivery_report)

# Wait for any outstanding messages to be delivered and delivery report
# callbacks to be triggered.
producer.flush()