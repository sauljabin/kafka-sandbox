# Interactive Queries

Interactive Queries allow you to leverage the state of your application from outside your application. The Kafka Streams API enables your applications to be queryable.

- [interactive queries](https://docs.confluent.io/platform/current/streams/developer-guide/interactive-queries.html)

This example is using [gRPC](https://grpc.io/) to request queries to the kafka stream server.

<iframe width="560" height="315" src="https://www.youtube.com/embed/fVDdY36Wk3w"></iframe>

### Query Results

Having kafka streams running (see [Kafka Streams](kafka-streams.md)),
you can query form the terminal a specific country, example:

```bash
./gradlew -q kafka-streams:run --args="count Ecuador"
```

This is possible because we have access to the kafka streams stores in runtime.
This way we can query over the store a return and response.

Check the gRPC server:

```java
ReadOnlyKeyValueStore<String, Long> keyValueStore = streams.store(
        StoreQueryParameters.fromNameAndType("SupplierCountByCountry", QueryableStoreTypes.keyValueStore()));

String country = request.getName();
Long total = keyValueStore.get(country);
String value = String.format("Country: %s, Total Suppliers: %s", country, total != null ? total : 0);
CountReply reply = CountReply.newBuilder().setMessage(value).build();
responseObserver.onNext(reply);
```

Check the gRPC client:

```java
ManagedChannel channel = Grpc.newChannelBuilder("localhost:5050", InsecureChannelCredentials.create())
                .build();

CounterServiceBlockingStub blockingStub = CounterServiceGrpc.newBlockingStub(channel);
CountReply countByCountry = blockingStub.getCountByCountry(CountRequest.newBuilder().setName(country).build());
System.out.println(countByCountry.getMessage());
```