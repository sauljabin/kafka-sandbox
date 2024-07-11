package kafka.sandbox.grpc;

import io.grpc.stub.StreamObserver;
import kafka.sandbox.proto.CountReply;
import kafka.sandbox.proto.CountRequest;
import kafka.sandbox.proto.CountServiceGrpc.CountServiceImplBase;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StoreQueryParameters;
import org.apache.kafka.streams.state.QueryableStoreTypes;
import org.apache.kafka.streams.state.ReadOnlyKeyValueStore;

public class CountService extends CountServiceImplBase {

    private final KafkaStreams streams;

    public CountService(KafkaStreams streams) {
        this.streams = streams;
    }

    @Override
    public void getCountByCountry(CountRequest request, StreamObserver<CountReply> responseObserver) {
        ReadOnlyKeyValueStore<String, Long> keyValueStore = streams.store(
                StoreQueryParameters.fromNameAndType("SupplierCountByCountry", QueryableStoreTypes.keyValueStore()));

        String country = request.getName();
        Long total = keyValueStore.get(country);
        String value = String.format("Country: %s, Total Suppliers: %s", country, total != null ? total : 0);
        CountReply reply = CountReply.newBuilder().setMessage(value).build();
        responseObserver.onNext(reply);
        responseObserver.onCompleted();
    }
}