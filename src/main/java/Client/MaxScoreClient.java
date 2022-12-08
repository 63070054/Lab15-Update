package Client;

import com.proto.maxscore.MaxScoreGrpc;
import com.proto.maxscore.MaxScoreRequest;
import com.proto.maxscore.MaxScoreResponse;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;

import java.util.Scanner;
import java.util.concurrent.CountDownLatch;

public class MaxScoreClient {
    public static void main(String[] args) {
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 50052)
                .usePlaintext()
                .build();
        MaxScoreGrpc.MaxScoreStub asyncClient = MaxScoreGrpc.newStub(channel);
        CountDownLatch latch = new CountDownLatch(1);
        StreamObserver<MaxScoreRequest> stream = asyncClient.findMaxScore(new StreamObserver<MaxScoreResponse>() {
            @Override
            public void onNext(MaxScoreResponse value) {
                System.out.println("Server to Client >> Max Score is " + value.getHighScore() );
            }

            @Override
            public void onError(Throwable t) {

            }

            @Override
            public void onCompleted() {
                latch.countDown();
            }
        });

        while(true) {
            Scanner scanner = new Scanner(System.in);
            int scoreInput = scanner.nextInt();
            if(scoreInput == -1){
                channel.shutdown();
            } else {
                MaxScoreRequest request = MaxScoreRequest.newBuilder()
                        .setScore(scoreInput)
                        .build();
                System.out.println("Client To Server : " + request);
                stream.onNext(request);
            }
        }

    }


}
