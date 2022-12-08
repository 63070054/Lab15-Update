package Server;

import com.proto.maxscore.MaxScoreGrpc;
import com.proto.maxscore.MaxScoreRequest;
import com.proto.maxscore.MaxScoreResponse;
import io.grpc.stub.StreamObserver;

public class MaxScoreServiceImpl extends MaxScoreGrpc.MaxScoreImplBase {
    private int maxScore = 0;
    @Override
    public StreamObserver<MaxScoreRequest> findMaxScore(StreamObserver<MaxScoreResponse> responseObserver) {
        StreamObserver<MaxScoreRequest> stream = new StreamObserver<MaxScoreRequest>() {
            @Override
            public void onNext(MaxScoreRequest request) {
                int scoreFromRequest = request.getScore();
                MaxScoreResponse response;
                if(scoreFromRequest > maxScore) {
                    maxScore = scoreFromRequest;
                }
                response = MaxScoreResponse.newBuilder()
                        .setHighScore(maxScore)
                        .build();
                responseObserver.onNext(response);
            }
            @Override
            public void onError(Throwable t) {

            }

            @Override
            public void onCompleted() {
                responseObserver.onCompleted();
            }
        };
        return stream;
    }
}
