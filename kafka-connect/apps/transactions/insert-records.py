import os
import mysql.connector
from faker import Faker

is_docker = os.getenv('DOCKER')

mysql_host = 'mysql' if is_docker else 'localhost'
connection = mysql.connector.connect(user='root', password='notasecret', host=mysql_host, database='sandbox')

cursor = connection.cursor()
cursor.execute('CREATE TABLE IF NOT EXISTS customers (id int NOT NULL AUTO_INCREMENT, name VARCHAR(255), address VARCHAR(255), PRIMARY KEY (id))')

fake = Faker()
data_storage = [
    {
        'name': fake.name(),
        'address': fake.address()
    }
    for n in range(10)
]

for customer in data_storage:
    print(customer, 'inserted')
    sql = 'INSERT INTO customers (name, address) VALUES (%s, %s)'
    val = (customer['name'], customer['address'])
    cursor.execute(sql, val)
    connection.commit()


connection.close()
