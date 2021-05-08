import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.nio.charset.StandardCharsets;
import java.util.Hashtable;
import java.util.Iterator;

public class Read {
    private SelectionKey key;
    private Selector selector;

    public Read(){ }
    public Read(SelectionKey key, Selector selector){
        this.selector = selector;
        this.key = key;
    }

    public void read(SelectionKey key) throws IOException, ClassNotFoundException {
        System.out.println("Чтение адреса файла!");

        Selector selector = Selector.open();
        ServerSocketChannel server = (ServerSocketChannel) key.channel();
        SocketChannel channel = server.accept();
        channel.configureBlocking(false);
        channel.register(selector, SelectionKey.OP_READ, ByteBuffer.allocate(2048));
        
        selector.select();
        Iterator<SelectionKey> keys = selector.selectedKeys().iterator();

        File file_main;

        while (keys.hasNext()) {
            key = keys.next();
            keys.remove();
            if (key.isReadable()) {
                System.out.println("read from a channel");
                SocketChannel channel1 = (SocketChannel) key.channel();
                ByteBuffer buffer = (ByteBuffer) key.attachment();
                channel1.read(buffer);
                buffer.flip();
                byte[] arr = new byte[buffer.remaining()];
                buffer.get(arr);
                System.out.println(new String(arr, StandardCharsets.UTF_8));
                file_main = new File(new String(arr, StandardCharsets.UTF_8));
                System.out.println(file_main.getAbsolutePath());

                Process process = new Process();
                key.interestOps(SelectionKey.OP_WRITE);
                process.process(file_main, key, selector);
            }
        }
    }
    public void readCommand(Hashtable<String, SpaceMarine> hashtable, File file) throws IOException, ClassNotFoundException {
        selector.select();
        Iterator<SelectionKey> keys = selector.selectedKeys().iterator();
        while (keys.hasNext()) {
            key = keys.next();
            keys.remove();
            System.out.println("key1");
            if (key.isReadable()) {
                System.out.println("read a command");
                SocketChannel channel1 = (SocketChannel) key.channel();
                ByteBuffer buffer = ByteBuffer.allocate(2048);
                channel1.read(buffer);
                System.out.println("command");
                ObjectInput in = new ObjectInputStream(new ByteArrayInputStream(buffer.array()));
                Command command = (Command) in.readObject();
                if (command.getNameOfCommand() == NameOfCommand.EXIT){break;}
                
                key.interestOps(SelectionKey.OP_WRITE);
                Process process = new Process();
                process.processCommand(command, key, selector, hashtable, file);
                
            }
        }
    }
}
