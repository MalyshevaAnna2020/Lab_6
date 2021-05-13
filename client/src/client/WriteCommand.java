package client;

import command.Command;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class WriteCommand {
    public void writeCommand(Command command, Socket s) throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream out = new ObjectOutputStream(bos);
        out.writeObject(command);
        out.flush();
        byte[] yourBytes = bos.toByteArray();
        OutputStream os = s.getOutputStream();
        os.write(yourBytes);
        System.out.println("Команда передана серверу!");

        byte[] answer = new byte[4096];
        InputStream is = s.getInputStream();
        is.read(answer);
        System.out.println("Ответ принят!");
        ByteArrayInputStream bis = new ByteArrayInputStream(answer);
        int n = bis.available();
        for (int i = 0; i < n; i++) {
            bis.read();
        }
        String result = new String(answer, StandardCharsets.UTF_8);
        System.out.println(result.trim());
    }

}
