import os
from confluent_kafka.avro import AvroConsumer
from confluent_kafka.avro.serializer import SerializerError

is_docker = os.getenv('DOCKER')
kafka_host = 'kafka{0}' if is_docker else 'localhost'
kafka_port = '9092' if is_docker else '9093'
schema_registry_host = 'schema-registry' if is_docker else 'localhost'
schema_registry_port = '8081'

bootstrap_servers = ','.join(['{0}:{1}{2}'.format(kafka_host.format(n), n, kafka_port) for n in [1, 2, 3]])
schema_registry_server = 'http://{0}:{1}'.format(schema_registry_host, schema_registry_port)

print('Connecting to', bootstrap_servers, 'and', schema_registry_server)

topic = 'avro-topic'
group = 'avro-consumer'

avroConsumer = AvroConsumer(
    {
        'bootstrap.servers': bootstrap_servers,
        'group.id': group,
        'auto.offset.reset': 'earliest',  # 'latest',
        'schema.registry.url': schema_registry_server
    }
)

avroConsumer.subscribe([topic])

while True:
    try:
        msg = avroConsumer.poll(10)

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

avroConsumer.close()
