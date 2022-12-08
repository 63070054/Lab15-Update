package Server;

import io.grpc.Server;
import io.grpc.ServerBuilder;

import java.io.IOException;

public class MaxScoreServer {

    public static void main(String[] args) {
        Server server = ServerBuilder.forPort(50052)
                .addService(new MaxScoreServiceImpl())
                .build();

        try {
            System.out.println("Server Start");
            server.start();
        } catch(IOException e){
            e.printStackTrace();
        }

        Runtime.getRuntime().addShutdownHook(new Thread(
                () -> {
                    System.out.println("Received Shutdown Request");
                    server.shutdown();
                    System.out.println("Successfully Shutdown");
                }
        ));

        try {
            server.awaitTermination();
        } catch(InterruptedException e) {
            e.printStackTrace();
        }

    }

}
