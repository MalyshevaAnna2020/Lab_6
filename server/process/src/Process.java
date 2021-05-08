import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.charset.StandardCharsets;
import java.nio.file.StandardOpenOption;
import java.util.EnumSet;
import java.util.Hashtable;

public class Process {
    
    public void process(File file, SelectionKey key, Selector selector) throws IOException, ClassNotFoundException {
        System.out.println("Обработка файла!");

        char [] chars = new char[4096];
        FileReader reader = new FileReader(file);
        reader.read(chars);
        System.out.println(chars);
        String s = new String(chars);
        reader.close();

        System.out.println("spcemarines:");
        Hashtable<String, SpaceMarine> hashtable = new Console().console(s);
        System.out.println(hashtable);

        FileWriter writer = new FileWriter(file);
        writer.write("<?xml version=\"1.0\" encoding=\"utf-8\"?>\n<!DOCTYPE hashtable>\n<hashtable>\n");
        if (!hashtable.isEmpty()) {
            for (String key1 : hashtable.keySet())
                writer.write("    <" + key1 + " id=\"" + hashtable.get(key1).getId() + "\" name=\"" + hashtable.get(key1).getName() + "\" coordinates=\""
                        + hashtable.get(key1).getCoordinates().toString() + "\" creationDate=\"" + hashtable.get(key1).getCreationDate()
                        + "\" health=\"" + hashtable.get(key1).getHealth() + "\" heartCount=\"" + hashtable.get(key1).getHeartCount()
                        + "\" achievements=\"" + hashtable.get(key1).getAchievements() + "\" category=\""
                        + hashtable.get(key1).getCategory() + "\" chapter=\"" + hashtable.get(key1).getChapter() + "\" />\n");
        }
        writer.write("</hashtable>\n");
        writer.close();
        System.out.println("Элементы коллекции успешно сохранены!");

        Write write = new Write(key, selector);
        write.write(hashtable, file);
    }

    public void processCommand(Command command, SelectionKey key, Selector selector,
                               Hashtable<String, SpaceMarine> hashtable, File file) throws IOException, ClassNotFoundException {
        System.out.println("Обработка команды!");
        Play play = new Play();
        Write write = new Write(key, selector);
        Hashtable<Hashtable<String, SpaceMarine>, String> commandHashtable = play.play(hashtable, command, "", file);
        System.out.println("&" + commandHashtable.get(hashtable) + "&");
        for (Hashtable<String, SpaceMarine> hashtable1 : commandHashtable.keySet()){
            hashtable = hashtable1;
            write.writeCommand(hashtable, commandHashtable.get(hashtable), file);
        }
    }
}
