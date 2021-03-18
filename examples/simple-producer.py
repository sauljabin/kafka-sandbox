import os
from confluent_kafka import Producer
from faker import Faker

is_docker = os.getenv('DOCKER')
hostnames = ['localhost:{0}9093'.format(n) for n in [1, 2, 3]]

if is_docker:
    hostnames = ['kafka{0}:{0}9092'.format(n) for n in [1, 2, 3]]

print('bootstrap.servers', hostnames)

fake = Faker()
producer = Producer({'bootstrap.servers': ','.join(hostnames)})

topic = 'simple-topic'
data = [fake.name() for n in range(100)]


def delivery_report(err, msg):
    # Called once for each message produced to indicate delivery result.
    # Triggered by poll() or flush().
    if err is not None:
        print('Message delivery failed: {}'.format(err))
    else:
        print('Message delivered to {} [{}]'.format(msg.topic(), msg.partition()))


for data in data:
    # Trigger any available delivery report callbacks from previous produce() calls
    producer.poll(0)

    # Asynchronously produce a message, the delivery report callback
    # will be triggered from poll() above, or flush() below, when the message has
    # been successfully delivered or failed permanently.
    producer.produce(topic, data.encode('utf-8'), callback=delivery_report)

# Wait for any outstanding messages to be delivered and delivery report
# callbacks to be triggered.
producer.flush()
