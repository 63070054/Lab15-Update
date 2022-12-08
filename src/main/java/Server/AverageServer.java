package Server;

import io.grpc.Server;
import io.grpc.ServerBuilder;

import java.io.IOException;

public class AverageServer {
    public static void main(String[] args) {
        Server server = ServerBuilder.forPort(50052)
                .addService(new AverageServiceImpl())
                .build();

        try {
            System.out.println("Server start");
            server.start();
        } catch(IOException e){
            e.printStackTrace();
        }

        Runtime.getRuntime().addShutdownHook(new Thread(
                () -> {
                    System.out.println("Request server shutdown");
                    server.shutdown();
                    System.out.println("Successfully shutdown");
                }
        ));

        try {
            server.awaitTermination();
        } catch(InterruptedException e){
            e.printStackTrace();
        }

    }
}
