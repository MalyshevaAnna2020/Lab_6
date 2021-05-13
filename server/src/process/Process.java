package process;

import command.Command;
import spacemarine.SpaceMarine;
import write.Write;

import java.io.*;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.Hashtable;

public class Process {
    
    public void process(String file, SelectionKey key, Selector selector) throws IOException, ClassNotFoundException {
        System.out.println("Обработка файла!");

        char [] chars = new char[4096];

        String s = "";
        Hashtable<String, SpaceMarine> hashtable = new Hashtable<>();
        File file_main = new FindFile().findFile(new File(file));
        try {
            if (file_main != null) {
                FileReader reader = new FileReader(file_main);
                reader.read(chars);
                System.out.println(chars);
                s = new String(chars);
                reader.close();
                hashtable = new Console().console(s);
            }
        }catch (FileNotFoundException e){
            System.out.println(e.getMessage());
        }
        System.out.println("spcemarines:");
        System.out.println(hashtable);

        assert file_main != null;
        try {
            if (file_main.canWrite()) {
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
            }
        }catch (FileNotFoundException e){
            System.out.println(e.getMessage());
        }

        Write write = new Write(key, selector);
        write.write(hashtable, file_main);
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
