package Client;

import com.proto.prime.PrimeFactoryGrpc;
import com.proto.prime.PrimeFactoryRequest;
import com.proto.prime.PrimeFactoryResponse;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

public class PrimeNumberFactoryClient {

    public static void main(String[] args) {
        ManagedChannel channel = ManagedChannelBuilder
                .forAddress("localhost", 50052)
                .usePlaintext()
                .build();

        PrimeFactoryGrpc.PrimeFactoryBlockingStub primeClient;
        primeClient = PrimeFactoryGrpc.newBlockingStub(channel);
        PrimeFactoryRequest primeRequest = PrimeFactoryRequest.newBuilder()
                .setNumber(108)
                .build();

        System.out.println("Client to Server : " + primeRequest);

        primeClient.primeDecomposition(primeRequest).forEachRemaining(primeResponse -> {
            System.out.println("Server to Client : " + primeResponse.getPrime());
        });

    }

}
