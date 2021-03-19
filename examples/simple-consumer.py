import os
from confluent_kafka import Consumer

is_docker = os.getenv('DOCKER')
hosts_inside_docker = ['kafka{0}:{0}9092'.format(n) for n in [1, 2, 3]]
hosts_outside_docker = ['localhost:{0}9093'.format(n) for n in [1, 2, 3]]
hostnames = ','.join(hosts_inside_docker) if is_docker else ','.join(hosts_outside_docker)
topic = 'simple-topic'
group = 'simple-consumer'

consumer = Consumer({
    'bootstrap.servers': hostnames,
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

    print('Received message: {}'.format(msg.value().decode('utf-8')))

consumer.close()
