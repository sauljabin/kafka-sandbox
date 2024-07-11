package kafka.sandbox.cli;

import io.grpc.Grpc;
import io.grpc.InsecureChannelCredentials;
import io.grpc.ManagedChannel;
import kafka.sandbox.proto.CountReply;
import kafka.sandbox.proto.CountRequest;
import kafka.sandbox.proto.CountServiceGrpc;
import kafka.sandbox.proto.CountServiceGrpc.CountServiceBlockingStub;
import lombok.extern.slf4j.Slf4j;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;

import java.util.concurrent.Callable;

@Slf4j
@Command(name = "count", description = "Print the total supplier count by a given country")
public class Count implements Callable<Integer> {

    @Parameters(index = "0", description = "Country (default: ${DEFAULT-VALUE})", defaultValue = "Ecuador")
    private String country;

    @Override
    public Integer call() {
        ManagedChannel channel = Grpc.newChannelBuilder("localhost:5050", InsecureChannelCredentials.create())
                .build();

        CountServiceBlockingStub blockingStub = CountServiceGrpc.newBlockingStub(channel);
        CountReply countByCountry = blockingStub.getCountByCountry(CountRequest.newBuilder().setName(country).build());
        System.out.println(countByCountry.getMessage());

        return CommandLine.ExitCode.OK;
    }

}
