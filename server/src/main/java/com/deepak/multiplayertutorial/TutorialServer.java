package com.deepak.multiplayertutorial;

import com.deepak.multiplayertutorial.data.ConnectionData;
import com.deepak.multiplayertutorial.services.TutorialActionServiceImpl;
import com.deepak.multiplayertutorial.utils.Constants;
import com.deepak.multiplayertutorial.utils.ServerHeaderInterceptor;
import io.grpc.Server;
import io.grpc.ServerBuilder;

import java.io.File;
import java.io.IOException;
import java.net.Inet4Address;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class TutorialServer {

    public static ArrayList<ConnectionData> connectionDataList = new ArrayList<>();

    static public void main(String args[]) {

        File serverCertificateFile = new File("server/server.crt");
        File serverKeyFile = new File("server/server.key");

        java.security.Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());

        Server server = ServerBuilder
                .forPort(Constants.SERVER_PORT)
                .addService(new TutorialActionServiceImpl())
                .intercept(new ServerHeaderInterceptor())
                //.useTransportSecurity(serverCertificateFile, serverKeyFile)
                .handshakeTimeout(30, TimeUnit.SECONDS)
                //.addStreamTracerFactory(new StreamTerminationTracer())
                .build();

        try {

            System.out.print("Server started !\n");
            server.start();

            System.out.println(Inet4Address.getLocalHost().getHostAddress());
            System.out.println(Inet4Address.getLocalHost().getHostName());
            server.awaitTermination();

        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
        }
    }
}
