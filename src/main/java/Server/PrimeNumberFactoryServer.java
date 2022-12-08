package Server;

import io.grpc.Server;
import io.grpc.ServerBuilder;

import java.io.IOException;

public class PrimeNumberFactoryServer {
    public static void main(String[] args) {
        Server server = ServerBuilder.forPort(50052)
                .addService(new PrimeNumberFactoryServiceImpl())
                .build();

        try {
            server.start();
            System.out.println("Server start !!");
        }catch (IOException e){
            e.printStackTrace();
        }

        Runtime.getRuntime().addShutdownHook(new Thread(
                () -> {
                    System.out.println("Received Shutdown Request");
                    server.shutdown();
                    System.out.println("Successfully Shutdown");
                }
        ));

        try{
            server.awaitTermination();
        } catch(InterruptedException e){
            e.printStackTrace();
        }

    }


}
