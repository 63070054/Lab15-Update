package Server;

import com.pro.average.AverageGrpc;
import com.pro.average.AverageRequest;
import com.pro.average.AverageResponse;
import io.grpc.stub.StreamObserver;

public class AverageServiceImpl extends AverageGrpc.AverageImplBase {

    private int sum = 0;
    private int count = 0;

    @Override
    public StreamObserver<AverageRequest> computeAverage(StreamObserver<AverageResponse> responseObserver) {
        StreamObserver<AverageRequest> stream = new StreamObserver<AverageRequest>() {
            @Override
            public void onNext(AverageRequest value) {
                sum += value.getNumber();
                count += 1;
                System.out.println("Message No. " + count + " From Client >> Number is " + value.getNumber());
            }

            @Override
            public void onError(Throwable t) {

            }

            @Override
            public void onCompleted() {
                double average = (double) sum / count;
                AverageResponse response = AverageResponse.newBuilder()
                        .setAverageOutput(average)
                        .build();
                responseObserver.onNext(response);
                responseObserver.onCompleted();
            }
        };
        return stream;
    }
}
