package client;

import command.Command;
import command.NameOfCommand;
import spacemarine.Chapter;
import spacemarine.Coordinates;
import spacemarine.SpaceMarine;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class WorkWithServer {
    private static final Map<String, NameOfCommand> commandMap = new HashMap<String, NameOfCommand>(){{
        put("exit", NameOfCommand.EXIT);
        put("help", NameOfCommand.HELP);
        put("info", NameOfCommand.INFO);
        put("show", NameOfCommand.SHOW);
        put("insert", NameOfCommand.INSERT);
        put("update", NameOfCommand.UPDATE);
        put("remove_key", NameOfCommand.REMOVE_KEY);
        put("clear", NameOfCommand.CLEAR);
        put("execute_script", NameOfCommand.EXECUTE_SCRIPT);
        put("remove_lower_key", NameOfCommand.REMOVE_LOWER_KEY);
        put("remove_lower", NameOfCommand.REMOVE_LOWER);
        put("remove_greater_key", NameOfCommand.REMOVE_GREATER_KEY);
        put("remove_any_by_chapter", NameOfCommand.REMOVE_ANY_BY_CHAPTER);
        put("filter_greater_than_achievements", NameOfCommand.FILTER_GREATER_THAN_ACHIEVEMENTS);
        put("print_field_descending_category", NameOfCommand.PRINT_FIELD_DESCENDING_CATEGORY);
    }};

    public void writeFile(File file_main, Socket s) throws IOException {
        OutputStream oos = s.getOutputStream();
        oos.write((file_main.getAbsolutePath()).getBytes(StandardCharsets.UTF_8));

        InputStream ois = s.getInputStream();
        byte[] bytes = new byte[2048];
        ois.read(bytes);
        System.out.println((new String(bytes, StandardCharsets.UTF_8)).trim());
    }
    public void writeAndReadCommand(Socket s){
        System.out.println("Можно начинать вводить команды (длина строки не должна превышать 256)!");
        String newcommand;
        Scanner in = new Scanner(System.in);
        while (true) {
            try {
                newcommand = in.nextLine();
                if (newcommand.length() > 256) {
                    System.err.println("Очень длинная строка! Введите более короткий вариант!");
                    continue;
                }
                Command command = new Command();
                if (newcommand.equals("")) continue;
                newcommand = newcommand.trim();
                for (String key : commandMap.keySet()){
                    if (newcommand.contains(key)){
                        System.out.println(commandMap.get(key));
                        command.setNameOfCommand(commandMap.get(key));
                        command.setKey(newcommand.substring(newcommand.indexOf(key) + key.length()).trim());
                        break;
                    }else{
                        command.setNameOfCommand(NameOfCommand.NOTHING);
                    }
                }

                if (newcommand.contains("insert")) {
                    SpaceMarine spmarine = new SpaceMarine();
                    command.setSpaceMarine(setSpaceMarine(spmarine));
                }
                if (newcommand.contains("update")) {
                    SpaceMarine spmarine = new SpaceMarine();
                    command.setSpaceMarine(setSpaceMarine(spmarine));
                }

                WriteCommand writeCommand = new WriteCommand();
                writeCommand.writeCommand(command, s);
                if (newcommand.equals("exit")) {
                    System.out.println("Завершение работы!");
                    break;
                }

            } catch (NoSuchElementException | IOException ignored) {
            }
        }
    }
    public SpaceMarine setSpaceMarine(SpaceMarine spaceMarine){
        Scanner in = new Scanner(System.in);
        Insert insert = new Insert();

        System.out.print("Введите имя: ");
        spaceMarine.setName(in.nextLine());

        spaceMarine.setCreationDate(new Date());

        Coordinates coordinates = new Coordinates();
        coordinates.setX(insert.insertX());
        coordinates.setY(insert.insertY());
        spaceMarine.setCoordinates(coordinates);

        spaceMarine.setHealth(insert.insertHealth());

        spaceMarine.setHeartCount(insert.insertHeartCount());

        System.out.print("Введите достижения: ");
        spaceMarine.setAchievements(in.nextLine());

        spaceMarine.setCategory(insert.insertCategory());

        Chapter chapter = new Chapter();
        System.out.print("Введите название части: ");
        chapter.setName(in.nextLine());
        chapter.setMarinesCount(insert.insertMarinesCount());
        spaceMarine.setChapter(chapter);

        return spaceMarine;
    }
}
