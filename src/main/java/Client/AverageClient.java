package Client;

import com.pro.average.AverageGrpc;
import com.pro.average.AverageRequest;
import com.pro.average.AverageResponse;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class AverageClient {
    public static void main(String[] args) {
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 50052)
                .usePlaintext()
                .build();

        CountDownLatch latch = new CountDownLatch(1);

        AverageGrpc.AverageStub asyncClient = AverageGrpc.newStub(channel);
        StreamObserver<AverageRequest> stream = asyncClient.computeAverage(new StreamObserver<AverageResponse>() {
            @Override
            public void onNext(AverageResponse value) {
                System.out.println("Average is " + value.getAverageOutput());
            }

            @Override
            public void onError(Throwable t) {

            }

            @Override
            public void onCompleted() {
                latch.countDown();
                System.out.println("Completed sending from Server");
            }
        });

        AverageRequest request1 = AverageRequest.newBuilder()
                .setNumber(1)
                .build();
        AverageRequest request2 = AverageRequest.newBuilder()
                .setNumber(2)
                .build();
        AverageRequest request3 = AverageRequest.newBuilder()
                .setNumber(3)
                .build();
        AverageRequest request4 = AverageRequest.newBuilder()
                .setNumber(4)
                .build();
        AverageRequest request5 = AverageRequest.newBuilder()
                .setNumber(5)
                .build();
        AverageRequest request6 = AverageRequest.newBuilder()
                .setNumber(6)
                .build();
        AverageRequest request7 = AverageRequest.newBuilder()
                .setNumber(7)
                .build();
        AverageRequest request8 = AverageRequest.newBuilder()
                .setNumber(8)
                .build();
        AverageRequest request9 = AverageRequest.newBuilder()
                .setNumber(9)
                .build();
        AverageRequest request10 = AverageRequest.newBuilder()
                .setNumber(10)
                .build();

        stream.onNext(request1);
        stream.onNext(request2);
        stream.onNext(request3);
        stream.onNext(request4);
        stream.onNext(request5);
        stream.onNext(request6);
        stream.onNext(request7);
        stream.onNext(request8);
        stream.onNext(request9);
        stream.onNext(request10);
        stream.onCompleted();

        try {
            latch.await(3L, TimeUnit.SECONDS);
        } catch (InterruptedException e){
            e.printStackTrace();
        }


    }
}
