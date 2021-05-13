package client;

import java.io.*;
import java.net.Socket;
import java.util.concurrent.TimeUnit;

public class ReadCommand {

    public static int PORT;
    public static String HOST;

    public ReadCommand(int port, String host) {
        PORT = port;
        HOST = host;
    }

    public void readCommand(File file_main) throws InterruptedException {
        int count = 0;
        while (count == 0) {
            try (Socket s = new Socket(HOST, PORT)) {
                count++;
                WorkWithServer workWithServer = new WorkWithServer();
                workWithServer.writeFile(file_main, s);
                workWithServer.writeAndReadCommand(s);

            } catch (IOException e) {
                System.out.println("Сервер еще не подключился!");
                TimeUnit.SECONDS.sleep(2);
            }
        }
    }
}
