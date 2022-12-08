package Server;

import com.proto.prime.PrimeFactoryGrpc;
import com.proto.prime.PrimeFactoryRequest;
import com.proto.prime.PrimeFactoryResponse;
import io.grpc.stub.StreamObserver;

public class PrimeNumberFactoryServiceImpl extends PrimeFactoryGrpc.PrimeFactoryImplBase {

    @Override
    public void primeDecomposition(PrimeFactoryRequest request, StreamObserver<PrimeFactoryResponse> responseObserver) {
        int prime = request.getNumber();

        int i = 2;

        while (prime != 1){
            while(prime % i == 0){
                prime = prime/i;
                PrimeFactoryResponse response = PrimeFactoryResponse.newBuilder()
                        .setPrime(i)
                        .build();
                responseObserver.onNext(response);
            }
            i += 1;

        }

        responseObserver.onCompleted();
    }
}
