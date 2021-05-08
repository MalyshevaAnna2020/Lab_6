import java.io.*;
import java.nio.ByteBuffer;
import java.util.*;
import java.util.stream.Collectors;

public class Play {
    private final List<String> files = new ArrayList<>();
    public Hashtable<Hashtable<String, SpaceMarine>, String> play
            (Hashtable<String, SpaceMarine> hashtable, Command command, String mess, File fileHashtable) throws IOException {
        byte[] b = new byte[1024];
        ByteBuffer f = ByteBuffer.wrap(b);
        f.clear();
        StringBuilder message = new StringBuilder(mess);
        Hashtable<Hashtable<String, SpaceMarine>, String> mainHashtable = new Hashtable<>();
        if (command.getNameOfCommand() == NameOfCommand.HELP) {
            mess = message.append("help : вывести справку по доступным командам\n" + "info : вывести в стандартный поток вывода информацию о коллекции (тип, дата инициализации, количество элементов и т.д.)\n" + "show : вывести в стандартный поток вывода все элементы коллекции в строковом представлении\n" + "insert null {element} : добавить новый элемент с заданным ключом\n" + "update id {element} : обновить значение элемента коллекции, id которого равен заданному\n" + "remove_key null : удалить элемент из коллекции по его ключу\n" + "clear : очистить коллекцию\n" + "execute_script file_name : считать и исполнить скрипт из указанного файла. В скрипте содержатся команды в таком же виде, в котором их вводит пользователь в интерактивном режиме.\n" + "exit : завершить программу (без сохранения в файл)\n" + "remove_lower {element} : удалить из коллекции все элементы, меньшие, чем заданный\n" + "remove_greater_key null : удалить из коллекции все элементы, ключ которых превышает заданный\n" + "remove_lower_key null : удалить из коллекции все элементы, ключ которых меньше, чем заданный\n" + "remove_any_by_chapter chapter : удалить из коллекции один элемент, значение поля chapter которого эквивалентно заданному\n" + "filter_greater_than_achievements achievements : вывести элементы, значение поля achievements которых больше заданного\n" + "print_field_descending_category : вывести значения поля category всех элементов в порядке убывания").toString();
        }else if (command.getNameOfCommand() == NameOfCommand.INFO) {
            StringBuilder info = new StringBuilder();
            if (!hashtable.isEmpty()) {
                List<String> sortedList = hashtable.keySet().stream().sorted(Comparator.comparingInt(String::length))
                        .collect(Collectors.toList());
                for (String key : sortedList) {
                    info.append("key=\"").append(key).append("\"\t").append("id=\"").append(hashtable.get(key).getId()).append("\"\t").append("name=\"").append(hashtable.get(key).getName()).append("\"\t").append("coordinates=\"").append(hashtable.get(key).getCoordinates().toString()).append("\"\t").append("creationDate=\"").append(hashtable.get(key).getCreationDate()).append("\"\t").append("health=\"").append(hashtable.get(key).getHealth()).append("\"\t").append("heartCount=\"").append(hashtable.get(key).getHeartCount()).append("\"\t").append("achievements=\"").append(hashtable.get(key).getAchievements()).append("\"\t").append("category=\"").append(hashtable.get(key).getCategory()).append("\"\t").append("chapter=\"").append(hashtable.get(key).getChapter()).append("\"");
                    if (hashtable.get(key).getHealth() == null) info.append("\nЗначение переменной health равно null!\n");
                    if (hashtable.get(key).getAchievements() == null) info.append("\nЗначение переменной achievements равно null!\n");
                    if (hashtable.get(key).getCategory() == null) info.append("\nЗначение переменной category равно null!\n");
                    if (hashtable.get(key).getChapter() == null) info.append("\nЗначение переменной chapter равно null!\n");
                }
            }else info.append("В коллекции нет элементов!");
            mess = message.append(info.toString()).toString();
        }else if(command.getNameOfCommand() == NameOfCommand.SHOW) {
            StringBuilder show = new StringBuilder();
            if (!hashtable.isEmpty()) show.append("Элементы коллекции: ").append(hashtable.keySet().stream().sorted(Comparator.comparingInt(String::length)).collect(Collectors.toList()).toString(), 1, hashtable.keySet().toString().length() - 1);
            else show.append("В коллекции нет элементов!");
            mess = message.append(show.toString()).toString();
        }else if (command.getNameOfCommand() == NameOfCommand.REMOVE_KEY) {
            if (hashtable.contains(command.getKey())) {
                hashtable.remove(command.getKey());
                mess = message.append("Элемент с ключом \"").append(command.getKey()).append("\" удален!").toString();
            }else{ mess = message.append("Элемент с ключом \"").append(command.getKey()).append("\" не найден!").toString(); }
        }else if (command.getNameOfCommand() == NameOfCommand.CLEAR) {
            hashtable.clear();
            mess = message.append("Все элементы коллекции успешно удалены!").toString();
        }else if (command.getNameOfCommand() == NameOfCommand.PRINT_FIELD_DESCENDING_CATEGORY) {
            List<SpaceMarine> sortedList = hashtable.values().stream().sorted(Comparator.comparing(SpaceMarine::getCategory)).collect(Collectors.toList());
            StringBuilder show = new StringBuilder();
            if (!hashtable.isEmpty()) for (SpaceMarine sp : sortedList) for (String key : hashtable.keySet()) if (hashtable.get(key) == sp) show.append(key).append(" ");
            else show.append("В коллекции нет элементов!");
            mess = message.append(show.toString()).toString();
        }else if (command.getNameOfCommand() == NameOfCommand.REMOVE_ANY_BY_CHAPTER) {
            List<SpaceMarine> sortedList = hashtable.values().stream().filter(s-> s.getChapter().toString().equals(command.getKey())).collect(Collectors.toList());
            if (!hashtable.isEmpty()) for (SpaceMarine sp : sortedList) for (String key : hashtable.keySet()) if (hashtable.get(key) == sp) hashtable.remove(key);
            mess = message.append("Элементы коллекции, принадлежащие ").append(command.getKey()).append(" успешно удалены!").toString();
        }else if (command.getNameOfCommand() == NameOfCommand.REMOVE_LOWER) {
            try {
                List<SpaceMarine> sortedList = hashtable.values().stream().filter(s-> s.getId() >= command.getI()).collect(Collectors.toList());
                Hashtable<String, SpaceMarine> subhashtable = new Hashtable<>();
                for (SpaceMarine sp : sortedList) for (String key : hashtable.keySet()) if (sp == hashtable.get(key)) subhashtable.put(key, hashtable.get(key));
                hashtable = subhashtable;
                message.append("Элементы коллекции с id меньше ").append(command.getI()).append(" успешно удалены!");
            } catch (NumberFormatException e) { message.append("Переменная id представляет собой натуральное число!"); }
            mess = message.toString();
        }else if (command.getNameOfCommand() == NameOfCommand.REMOVE_GREATER_KEY) {
            try {
                Hashtable<String, SpaceMarine> subhashtable = new Hashtable<>();
                List<String> sortedList = hashtable.keySet().stream().filter(s-> s.length() <= command.getI()).collect(Collectors.toList());
                for (String key : sortedList) subhashtable.put(key, subhashtable.get(key));
                hashtable = subhashtable;
                message.append("Элементы коллекции с длиной ключа больше ").append(command.getI()).append(" успешно удалены!");
            } catch (NumberFormatException e) {
                message.append("Количество символов строки ключа представляет собой натуральное число!");
            }
            mess = message.toString();

        }else if (command.getNameOfCommand() == NameOfCommand.REMOVE_LOWER_KEY) {
            try {
                Hashtable<String, SpaceMarine> subhashtable = new Hashtable<>();
                List<String> sortedList = hashtable.keySet().stream().filter(s-> s.length() >= command.getI()).collect(Collectors.toList());
                for (String key : sortedList) subhashtable.put(key, subhashtable.get(key));
                hashtable = subhashtable;
                message.append("Элементы коллекции с длиной ключа меньше ").append(command.getI()).append(" успешно удалены!");
            } catch (NumberFormatException e) {
                message.append("Количество символов строки ключа представляет собой натуральное число!");
            }
            mess = message.toString();

        }else if (command.getNameOfCommand() == NameOfCommand.FILTER_GREATER_THAN_ACHIEVEMENTS) {
            try {
                List<SpaceMarine> sortedList = hashtable.values().stream().filter(s-> s.getAchievements().length() > command.getI()).collect(Collectors.toList());
                for (SpaceMarine sp : sortedList) for (String key : hashtable.keySet()) if (hashtable.get(key) == sp) message.append(key).append(" ");
                System.out.println();
            } catch (NumberFormatException e) {
                message.append("Количество символов в строке достижений представляет собой " + "натуральное число!");
            }
            mess = message.toString();
        }else if (command.getNameOfCommand() == NameOfCommand.INSERT) {
            hashtable.put(command.getKey(), command.getSpaceMarine());
            message.append("Элемент коллекции с ключом \"").append(command.getKey()).append("\" успешно добавлен/удален!");
            mess = message.toString();
        }else if (command.getNameOfCommand() == NameOfCommand.UPDATE){
            for (String key : hashtable.keySet()){
                if (hashtable.get(key).getId() == command.getI()){
                    hashtable.put(key, command.getSpaceMarine());
                }
            }
            message.append("Элемент коллекции с Id ").append(command.getI()).append(" успешно обновлен!");
            mess = message.toString();
        }else if (command.getNameOfCommand() == NameOfCommand.EXECUTE_SCRIPT) {
            String file = command.getKey();
            if (files.contains(file)){
                message.append("execute_script ").append(file).append(" - это команда на исполнение этого же файла, возможна рекурсия!");
            }
            try {
                new CheckFile().checkFile(new File(file));
                char [] chars = new char[4096];
                FileReader reader = new FileReader(file);
                reader.read(chars);
                System.out.println(chars);
                String s = (new String(chars)).trim();
                reader.close();
                System.out.println(s);
                files.add(file);

                while (s.contains("\n")){
                    String subs = s.substring(0, s.indexOf("\n"));
                    if (subs.length() > 256){
                        message.append(subs).append(" - очень длинная строка!");
                        continue;
                    }
                    if (subs.equals("")) continue;
                    Command command1 = new Command();
                    if (subs.equals("exit")) {
                        command1.setNameOfCommand(NameOfCommand.EXIT);
                    }else if (subs.contains("help")){
                        command1.setNameOfCommand(NameOfCommand.HELP);
                    } else if (subs.contains("info")){
                        command1.setNameOfCommand(NameOfCommand.INFO);
                    } else if (subs.contains("show")){
                        command1.setNameOfCommand(NameOfCommand.SHOW);
                    } else if (subs.contains("insert")){
                        command1.setNameOfCommand(NameOfCommand.INSERT);
                        command1.setKey(subs.substring(subs.indexOf("insert") + 6).trim());
                        s = s.substring(s.indexOf("\n") + 1);
                        s += "\n";

                        SpaceMarine spmarine = new SpaceMarine();
                        spmarine.setId(hashtable.size() + 1);
                        if (s.contains("\n")) {
                            spmarine.setName(s.substring(0, s.indexOf("\n") - 1));
                            s = s.substring(s.indexOf("\n") + 1);
                        }else spmarine.setName("Id_" + spmarine.getId());
                        Coordinates coordinates = new Coordinates();
                        if (s.contains("\n")) {
                            coordinates.setX(s.substring(0, s.indexOf("\n") - 1));
                            s = s.substring(s.indexOf("\n") + 1);
                        }else coordinates.setX("0");
                        if (s.contains("\n")) {
                            coordinates.setY(s.substring(0, s.indexOf("\n") - 1));
                            s = s.substring(s.indexOf("\n") + 1);
                        }else coordinates.setY("0");
                        spmarine.setCoordinates(coordinates);
                        spmarine.setCreationDate(new Date());
                        if (s.contains("\n")) {
                            spmarine.setHealth(s.substring(0, s.indexOf("\n") - 1));
                            s = s.substring(s.indexOf("\n") + 1);
                        }else spmarine.setHealth("1");
                        if (s.contains("\n")) {
                            spmarine.setHeartCount(s.substring(0, s.indexOf("\n") - 1));
                            s = s.substring(s.indexOf("\n") + 1);
                        }else spmarine.setHealth("1");
                        if (s.contains("\n")) {
                            spmarine.setAchievements(s.substring(0, s.indexOf("\n") - 1));
                            s = s.substring(s.indexOf("\n") + 1);
                        }else spmarine.setAchievements("-");
                        if (s.contains("\n")) {
                            spmarine.setCategory(s.substring(0, s.indexOf("\n") - 1));
                            s = s.substring(s.indexOf("\n") + 1);
                        }
                        Chapter chapter = new Chapter();
                        if (s.contains("\n")) {
                            chapter.setName(s.substring(0, s.indexOf("\n") - 1));
                            s = s.substring(s.indexOf("\n") + 1);
                        } else { chapter.setName("-");}
                        if (s.contains("\n")) {
                            chapter.setMarinesCount(s.substring(0, s.indexOf("\n") - 1));
                        } else { chapter.setMarinesCount("-");}
                        spmarine.setChapter(chapter);
                        command1.setSpaceMarine(spmarine);
                    }else if (subs.contains("update")){
                        command1.setNameOfCommand(NameOfCommand.UPDATE);
                        try {
                            int newId = Integer.parseInt(subs.substring(subs.indexOf("update") + 6).trim());
                            int count = 0;
                            s = s.substring(s.indexOf("\n") + 1);
                            s += "\n";
                            for (String key : hashtable.keySet()) if (hashtable.get(key).getId() == newId){
                                count = 1;
                                command1.setKey(key);
                            }
                            if (count == 1) {
                                SpaceMarine spmarine = new SpaceMarine();
                                spmarine.setId(newId);
                                if (s.contains("\n")) {
                                    spmarine.setName(s.substring(0, s.indexOf("\n") - 1));
                                    s = s.substring(s.indexOf("\n") + 1);
                                }else spmarine.setName("Id_" + spmarine.getId());
                                Coordinates coordinates = new Coordinates();
                                if (s.contains("\n")) {
                                    coordinates.setX(s.substring(0, s.indexOf("\n") - 1));
                                    s = s.substring(s.indexOf("\n") + 1);
                                }else coordinates.setX("0");
                                if (s.contains("\n")) {
                                    coordinates.setY(s.substring(0, s.indexOf("\n") - 1));
                                    s = s.substring(s.indexOf("\n") + 1);
                                }else coordinates.setY("0");
                                spmarine.setCoordinates(coordinates);
                                spmarine.setCreationDate(new Date());
                                if (s.contains("\n")) {
                                    spmarine.setHealth(s.substring(0, s.indexOf("\n") - 1));
                                    s = s.substring(s.indexOf("\n") + 1);
                                }else spmarine.setHealth("1");
                                if (s.contains("\n")) {
                                    spmarine.setHeartCount(s.substring(0, s.indexOf("\n") - 1));
                                    s = s.substring(s.indexOf("\n") + 1);
                                }else spmarine.setHealth("1");
                                if (s.contains("\n")) {
                                    spmarine.setAchievements(s.substring(0, s.indexOf("\n") - 1));
                                    s = s.substring(s.indexOf("\n") + 1);
                                }else spmarine.setAchievements("-");
                                if (s.contains("\n")) {
                                    spmarine.setCategory(s.substring(0, s.indexOf("\n") - 1));
                                    s = s.substring(s.indexOf("\n") + 1);
                                }
                                Chapter chapter = new Chapter();
                                if (s.contains("\n")) {
                                    chapter.setName(s.substring(0, s.indexOf("\n") - 1));
                                    s = s.substring(s.indexOf("\n") + 1);
                                } else { chapter.setName("-");}
                                if (s.contains("\n")) {
                                    chapter.setMarinesCount(s.substring(0, s.indexOf("\n") - 1));
                                } else { chapter.setMarinesCount("-");}
                                spmarine.setChapter(chapter);

                                command1.setSpaceMarine(spmarine);
                                command1.setI(newId);
                            }else continue;
                        }catch (NumberFormatException e){
                            System.err.println("Переменная id представляет собой число!");
                        }
                    } else if (subs.contains("remove_key")){
                        command1.setNameOfCommand(NameOfCommand.REMOVE_KEY);
                        command1.setKey(subs.substring(subs.indexOf("remove_key") + 10).trim());
                    } else if (subs.contains("clear")){
                        command1.setNameOfCommand(NameOfCommand.CLEAR);
                    } else if (subs.contains("execute_script")) {
                        command1.setNameOfCommand(NameOfCommand.EXECUTE_SCRIPT);
                        command1.setKey(subs.substring(subs.indexOf("execute_script") + 14).trim());
                    } else if (subs.contains("remove_lower_key")){
                        command1.setNameOfCommand(NameOfCommand.REMOVE_LOWER_KEY);
                        command1.setI(Integer.parseInt(subs.substring(subs.indexOf("remove_lower_key") + 16).trim()));
                    } else if (subs.contains("remove_lower")){
                        command1.setNameOfCommand(NameOfCommand.REMOVE_LOWER);
                        command1.setI(Integer.parseInt(subs.substring(subs.indexOf("remove_lower") + 12).trim()));
                    } else if (subs.contains("remove_greater_key")){
                        command1.setNameOfCommand(NameOfCommand.REMOVE_GREATER_KEY);
                        command1.setI(Integer.parseInt(subs.substring(subs.indexOf("remove_greater_key") + 18).trim()));
                    } else if (subs.contains("remove_any_by_chapter")){
                        command1.setNameOfCommand(NameOfCommand.REMOVE_ANY_BY_CHAPTER);
                        command1.setKey((("remove_any_by_chapter") + 16).trim());
                    } else if (subs.contains("filter_greater_than_achievements")) {
                        command1.setNameOfCommand(NameOfCommand.FILTER_GREATER_THAN_ACHIEVEMENTS);
                    }else if(subs.contains("print_field_descending_category")){
                        command1.setNameOfCommand(NameOfCommand.PRINT_FIELD_DESCENDING_CATEGORY);
                    } else {
                        message.append("Введите команду help для получений сведений о существующих командах");
                        continue;
                    }
                    System.out.println(message.toString());
                    Play play = new Play();
                    mainHashtable = play.play(hashtable, command1, message.toString(), fileHashtable);
                    for (Hashtable<String, SpaceMarine> hashtable1 : mainHashtable.keySet()){
                        hashtable = hashtable1;
                        message.append(mainHashtable.get(hashtable)).append("\n");
                        mess = message.toString();
                    }
                    s = s.substring(s.indexOf("\n") + 1);
                    if (s.length() <= 1) break;
                    else s = s.substring(1);
                }
            } catch (FileNotFoundException e) {
                message.append("Файл для чтения не найден!");
            } catch (FileNotRead e) {
                message.append("Файл не доступен для чтения!");
            } catch (IOException e) {
                e.printStackTrace();
                message.append("java.io.IOException");
            }

        }
        new WriteToFile().write(hashtable, fileHashtable);
        mainHashtable.put(hashtable, mess);
        System.out.println(mainHashtable);
        return mainHashtable;
    }
}
