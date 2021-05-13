package process;

import command.Command;
import command.NameOfCommand;
import spacemarine.Chapter;
import spacemarine.Coordinates;
import spacemarine.SpaceMarine;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.*;

public class Play {
    private final List<String> files = new ArrayList<>();
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

    public Hashtable<Hashtable<String, SpaceMarine>, String> play
            (Hashtable<String, SpaceMarine> hashtable, Command command, String mess, File fileHashtable) throws IOException {
        byte[] b = new byte[1024];
        ByteBuffer f = ByteBuffer.wrap(b);
        f.clear();
        StringBuilder message = new StringBuilder();
        message.append(mess);
        System.out.println(message.toString());
        Hashtable<Hashtable<String, SpaceMarine>, String> mainHashtable = new Hashtable<>();
        ResultOfCommand resultOfCommand = new ResultOfCommand();
        System.out.println(command.getNameOfCommand());

        switch (command.getNameOfCommand()){
            case EXIT:
                mainHashtable.put(hashtable, message.toString());
                System.exit(0);
                break;
            case HELP:
                message.append(resultOfCommand.help());
                mainHashtable.put(hashtable, message.toString());
                break;
            case INFO:
                message.append(resultOfCommand.info(hashtable));
                mainHashtable.put(hashtable, message.toString());
                break;
            case SHOW:
                message.append(resultOfCommand.show(hashtable));
                mainHashtable.put(hashtable, message.toString());
                break;
            case REMOVE_KEY:
                if (hashtable.containsKey(command.getKey())) {
                    System.out.println(command.getKey().trim());
                    hashtable.remove(command.getKey().trim());
                    message.append("Элемент с ключом \"").append(command.getKey()).append("\" удален!");
                }else{
                    message.append("Элемент с ключом \"").append(command.getKey()).append("\" не найден!");
                }
                mainHashtable.put(hashtable, message.toString());
                break;
            case CLEAR:
                hashtable.clear();
                message.append("Все элементы коллекции успешно удалены!");
                mainHashtable.put(hashtable, message.toString());
                break;
            case PRINT_FIELD_DESCENDING_CATEGORY:
                message.append(resultOfCommand.print(hashtable));
                mainHashtable.put(hashtable, message.toString());
                break;
            case REMOVE_ANY_BY_CHAPTER:
                hashtable = resultOfCommand.remove_any_by_chapter(hashtable, command);
                message.append(resultOfCommand.remove_any_by_chapter(command));
                mainHashtable.put(hashtable, message.toString());
                break;
            case REMOVE_LOWER:
                try {
                    hashtable = resultOfCommand.remove_lower(hashtable, command);
                    message.append(resultOfCommand.remove_lower(command));
                } catch (NumberFormatException e) {
                    message.append("Переменная id представляет собой натуральное число!");
                }
                mainHashtable.put(hashtable, message.toString());
                break;
            case REMOVE_GREATER_KEY:
                try {
                    hashtable = resultOfCommand.remove_greater_key(hashtable, command);
                    message.append(resultOfCommand.remove_greater_key(command));
                } catch (NumberFormatException e) {
                    message.append("Количество символов строки ключа представляет собой натуральное число!");
                }
                mainHashtable.put(hashtable, message.toString());
                break;
            case REMOVE_LOWER_KEY:
                try {
                    hashtable = resultOfCommand.remove_lower_key(hashtable, command);
                    message.append(resultOfCommand.remove_lower_key(command));
                } catch (NumberFormatException e) {
                    message.append("Количество символов строки ключа представляет собой натуральное число!");
                }
                mainHashtable.put(hashtable, message.toString());
                break;
            case FILTER_GREATER_THAN_ACHIEVEMENTS:
                try {
                    message.append(resultOfCommand.filter_greater_than_achievements(hashtable, command));
                } catch (NumberFormatException e) {
                    message.append("Количество символов в строке достижений представляет собой натуральное число!");
                }
                mainHashtable.put(hashtable, message.toString());
                break;
            case INSERT:
                SpaceMarine spaceMarine = command.getSpaceMarine();
                spaceMarine.setId(hashtable.size() + 1);
                hashtable.put(command.getKey(), spaceMarine);
                message.append(resultOfCommand.insert(command));
                mainHashtable.put(hashtable, message.toString());
                break;
            case UPDATE:
                hashtable = resultOfCommand.update(hashtable, command);
                message.append(resultOfCommand.update(command, hashtable));
                mainHashtable.put(hashtable, message.toString());

                break;
            case EXECUTE_SCRIPT:
                String file = command.getKey();
                // Singleton
                ExecuteScript executeScript = ExecuteScript.getInstance();
                if (files.contains(file)){
                    message.append(executeScript.checkFile(file));
                }
                try {
                    String s = executeScript.writeCommandsFromFiles(file);
                    s += "\n";
                    files.add(file);

                    while (s.contains("\n")){
                        String newcommand = s.substring(0, s.indexOf("\n"));
                        if (newcommand.length() > 256){
                            message.append(newcommand).append(" - очень длинная строка!");
                            continue;
                        }
                        if (newcommand.equals("")) continue;
                        Command command1 = new Command();
                        for (String key : commandMap.keySet()){
                            if (newcommand.contains(key)){
                                command1.setNameOfCommand(commandMap.get(key));
                                command1.setKey(newcommand.substring(newcommand.indexOf(key) + key.length()).trim());
                                break;
                            }else command1.setNameOfCommand(NameOfCommand.NOTHING);
                        }
                        SpaceMarine spmarine = new SpaceMarine();
                        if (newcommand.contains("insert")) {
                            s = s.substring(s.indexOf("\n") + 1);
                            spmarine.setId(hashtable.size() + 1);
                        }
                        int count = 0;
                        if (newcommand.contains("update")) {
                            try {
                                int newId = Integer.parseInt(newcommand.substring(newcommand.indexOf("update") + 6).trim());
                                s = s.substring(s.indexOf("\n") + 1);
                                s += "\n";
                                for (String key : hashtable.keySet()) if (hashtable.get(key).getId() == newId){
                                    count = 1;
                                    command1.setKey(key);
                                }
                                if (count == 1) {
                                    //command1 = executeScript.update(newId, command1, s);
                                    spmarine.setId(newId);
                                }else continue;
                            }catch (NumberFormatException e){
                                System.err.println("Переменная id представляет собой число!");
                            }
                        }
                        if ((newcommand.contains("insert")) ||
                                ((newcommand.contains("update")) && (count == 1))){
                            Coordinates coordinates = new Coordinates();
                            Chapter chapter = new Chapter();
                            for (int i = 0; i < 9; i++){
                                if (s.contains("\n")) {
                                    switch (i) {
                                        case 0:
                                            spmarine.setName(s.substring(0, s.indexOf("\n") - 1));
                                            break;
                                        case 1:
                                            coordinates.setX(s.substring(0, s.indexOf("\n") - 1));
                                            break;
                                        case 2:
                                            coordinates.setY(s.substring(0, s.indexOf("\n") - 1));
                                        case 3:
                                            String health = s.substring(0, s.indexOf("\n") - 1);
                                            try {
                                                spmarine.setHealth(Long.parseLong(health));
                                                if (Long.parseLong(health) <= 0) spmarine.setHealth(1L);
                                            }catch (NumberFormatException e){
                                                spmarine.setHealth(1L);
                                            }
                                            break;
                                        case 4:
                                            String strHeartCount = s.substring(0, s.indexOf("\n") - 1);
                                            try{
                                                spmarine.setHeartCount(Integer.parseInt(strHeartCount.trim()));
                                                if (Integer.parseInt(strHeartCount.trim()) > 3) spmarine.setHeartCount(3);
                                                if (Integer.parseInt(strHeartCount.trim()) < 1) spmarine.setHeartCount(1);
                                            }catch (NumberFormatException e){
                                                spmarine.setHeartCount(1);
                                            }
                                            break;
                                        case 5:
                                            spmarine.setAchievements(s.substring(0, s.indexOf("\n") - 1));
                                            break;
                                        case 6:
                                            spmarine.setCategory(s.substring(0, s.indexOf("\n") - 1));
                                            break;
                                        case 7:
                                            chapter.setName(s.substring(0, s.indexOf("\n") - 1));
                                            break;
                                        case 8:
                                            chapter.setMarinesCount(s.substring(0, s.indexOf("\n") - 1));
                                            break;
                                    }
                                    s = s.substring(s.indexOf("\n") + 1);
                                }else{
                                    switch (i){
                                        case 0:
                                            spmarine.setName("Id_" + spmarine.getId());
                                            break;
                                        case 1:
                                            coordinates.setX("0");
                                            break;
                                        case 2:
                                            coordinates.setY("0");
                                            break;
                                        case 3:
                                            spmarine.setHealth(1L);
                                            break;
                                        case 4:
                                            spmarine.setHeartCount(1);
                                            break;
                                        case 5:
                                            spmarine.setAchievements("Достижений нет");
                                            break;
                                        case 7:
                                            chapter.setName("-");
                                            break;
                                        case 8:
                                            chapter.setMarinesCount("1");
                                            break;
                                    }
                                }
                            }
                            spmarine.setCoordinates(coordinates);
                            spmarine.setCreationDate(new Date());
                            spmarine.setChapter(chapter);
                            command1.setSpaceMarine(spmarine);
                        }
                        System.out.println(message.toString());
                        Play play = new Play();
                        mainHashtable.clear();
                        mainHashtable = play.play(hashtable, command1, message.toString(), fileHashtable);
                        for (Hashtable<String, SpaceMarine> hashtable1 : mainHashtable.keySet()){
                            hashtable = hashtable1;
                            message.setLength(0);
                            message.append(mainHashtable.get(hashtable)).append("\n");
                        }
                        s = s.substring(s.indexOf("\n") + 1);
                        if (s.length() <= 1) break;
                        else s = s.substring(1);
                    }
                } catch (FileNotFoundException e) {
                    message.append("Файл ").append(file).append(" для чтения не найден!");
                } catch (FileNotRead e) {
                    message.append("Файл ").append(file).append("не доступен для чтения!");
                } catch (IOException e) {
                    e.printStackTrace();
                    message.append("java.io.IOException");
                } catch (FileNotWrite fileNotWrite) {
                    fileNotWrite.printStackTrace();
                }
                break;
            case NOTHING:
                message.append("Команда help поможет узнать Вам о существующих командах");
                break;
        }

        try {
            assert fileHashtable != null;
            if (fileHashtable.canWrite()) {
                new WriteToFile().write(hashtable, fileHashtable);
            }
        }catch (FileNotFoundException e){
            System.out.println(e.getMessage());
        }
        mainHashtable.put(hashtable, message.toString());
        System.out.println(mainHashtable);
        return mainHashtable;
    }
}
