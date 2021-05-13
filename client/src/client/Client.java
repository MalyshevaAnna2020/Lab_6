package client;

import java.io.*;


public class Client {
    public static final int PORT = 2000;
    public static final String HOST = "localhost";

    public static void main(String[] args) throws InterruptedException {
        try {
            StringBuilder m_file = new StringBuilder();
            for (String string_arg : args) {
                m_file.append(string_arg);
            }
            System.out.println("Запущен клиент!");
            File file_main = new File(m_file.toString());
            ReadCommand readCommand = new ReadCommand(PORT, HOST);
            readCommand.readCommand(file_main);
        }catch (NullPointerException e){
            System.out.println("Завершение работы!");
        }

    }

}
