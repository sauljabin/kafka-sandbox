import os
from confluent_kafka import Consumer

is_docker = os.getenv('DOCKER')
kafka_host = 'kafka{0}' if is_docker else 'localhost'
kafka_port = '9092' if is_docker else '9093'
schema_registry_host = 'schema-registry' if is_docker else 'localhost'
schema_registry_port = '8081'

bootstrap_servers = ','.join(['{0}:{1}{2}'.format(kafka_host.format(n), n, kafka_port) for n in [1, 2, 3]])

print('Connecting to', bootstrap_servers)

topic = 'simple-topic'
group = 'simple-consumer'

consumer = Consumer({
    'bootstrap.servers': bootstrap_servers,
    'group.id': group,
    'auto.offset.reset': 'earliest'  # 'latest'
})

consumer.subscribe([topic])

while True:
    msg = consumer.poll(1.0)

    if msg is None:
        continue
    if msg.error():
        print("Consumer error: {}".format(msg.error()))
        continue

    print('Message received: {}'.format(msg.value().decode('utf-8')))

consumer.close()
