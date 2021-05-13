package write;

import read.Read;
import spacemarine.SpaceMarine;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.Hashtable;
import java.util.Iterator;


public class Write {
    private SelectionKey key;
    private Selector selector;

    public Write(SelectionKey key, Selector selector) {
        this.key = key;
        this.selector = selector;
    }

    public void write(Hashtable<String, SpaceMarine> hashtable, File file) throws IOException, ClassNotFoundException {
        System.out.println("Передача hashtable клиенту!");

        selector.select();
        Iterator<SelectionKey> keys = selector.selectedKeys().iterator();
        
        while (keys.hasNext()) {
            key = keys.next();
            keys.remove();
            System.out.println("key");
            if (key.isWritable()) {
                System.out.println("write from a channel");
                SocketChannel channel1 = (SocketChannel) key.channel();
                ByteBuffer buffer = (ByteBuffer) key.attachment();
                buffer.clear();
                String s = "Элементы коллекции успешно добавлены!";
                byte[] b = s.getBytes(StandardCharsets.UTF_8);
                for (byte value : b) {
                    buffer.put(value);
                }
                buffer.flip();
                channel1.write(buffer);
                if (buffer.hasRemaining()){
                    buffer.compact();
                } else {
                    buffer.clear();
                }
                key.interestOps(SelectionKey.OP_READ);
                Read read = new Read(key, selector);
                read.readCommand(hashtable, file);
            }
        }
    }
    public void writeCommand(Hashtable<String, SpaceMarine> hashtable, String s, File file) throws IOException, ClassNotFoundException {
        System.out.println("Передача результата клиенту!");

        selector.select();
        Iterator<SelectionKey> keys = selector.selectedKeys().iterator();

        while (keys.hasNext()) {
            key = keys.next();
            keys.remove();
            System.out.println("key");
            if (key.isWritable()) {
                System.out.println("write a command");
                SocketChannel channel1 = (SocketChannel) key.channel();
                ByteBuffer buffer = ByteBuffer.allocate(4096);
                System.out.println(s);
                buffer.clear();
                if (s.equals("")) s = "В коллекции нет элементов!";
                byte[] b = s.getBytes(StandardCharsets.UTF_8);
                for (byte value : b) {
                    if (buffer.hasRemaining()) {
                        buffer.put(value);
                    }else{break;}
                }
                buffer.flip();
                channel1.write(buffer);
                if (buffer.hasRemaining()){
                    buffer.compact();
                } else {
                    buffer.clear();
                }
                System.out.println("Команда передана клиенту!");
                key.interestOps(SelectionKey.OP_READ);
                Read read = new Read(key, selector);
                read.readCommand(hashtable, file);
            }
        }
    }
}
