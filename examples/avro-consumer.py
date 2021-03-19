import os
from confluent_kafka.avro import AvroConsumer
from confluent_kafka.avro.serializer import SerializerError

is_docker = os.getenv('DOCKER')
hosts_inside_docker = ['kafka{0}:{0}9092'.format(n) for n in [1, 2, 3]]
hosts_outside_docker = ['localhost:{0}9093'.format(n) for n in [1, 2, 3]]
hostnames = ','.join(hosts_inside_docker) if is_docker else ','.join(hosts_outside_docker)
schema_registry = 'http://localhost:8081'
topic = 'avro-topic'
group = 'avro-consumer'

consumer = AvroConsumer({
    'bootstrap.servers': hostnames,
    'group.id': group,
    'auto.offset.reset': 'earliest',  # 'latest',
    'schema.registry.url': schema_registry})

consumer.subscribe([topic])

while True:
    try:
        msg = consumer.poll(10)

    except SerializerError as e:
        print("Message deserialization failed for {}: {}".format(msg, e))
        break

    if msg is None:
        continue

    if msg.error():
        print("AvroConsumer error: {}".format(msg.error()))
        continue

    print('Raw:', msg.value())
    print('Name:', msg.value()['name'])

consumer.close()
