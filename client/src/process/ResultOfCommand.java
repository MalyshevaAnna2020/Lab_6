package process;

import command.Command;
import spacemarine.SpaceMarine;

import java.util.Comparator;
import java.util.Hashtable;
import java.util.List;
import java.util.stream.Collectors;

public class ResultOfCommand {
    public String help(){
        return "help : вывести справку по доступным командам\n"
                + "info : вывести в стандартный поток вывода информацию о коллекции " +
                "(тип, дата инициализации, количество элементов и т.д.)\n"
                + "show : вывести в стандартный поток вывода все элементы коллекции в строковом представлении\n"
                + "insert null {element} : добавить новый элемент с заданным ключом\n"
                + "update id {element} : обновить значение элемента коллекции, id которого равен заданному\n"
                + "remove_key null : удалить элемент из коллекции по его ключу\n" + "clear : очистить коллекцию\n"
                + "execute_script file_name : считать и исполнить скрипт из указанного файла. " +
                "В скрипте содержатся команды в таком же виде, в котором их вводит пользователь в интерактивном режиме.\n"
                + "exit : завершить программу (без сохранения в файл)\n"
                + "remove_lower {element} : удалить из коллекции все элементы, меньшие, чем заданный\n"
                + "remove_greater_key null : удалить из коллекции все элементы, ключ которых превышает заданный\n"
                + "remove_lower_key null : удалить из коллекции все элементы, ключ которых меньше, чем заданный\n"
                + "remove_any_by_chapter chapter : удалить из коллекции один элемент, " +
                "значение поля chapter которого эквивалентно заданному\n"
                + "filter_greater_than_achievements achievements : " +
                "вывести элементы, значение поля achievements которых больше заданного\n"
                + "print_field_descending_category : вывести значения поля category всех элементов в порядке убывания";
    }
    public String info(Hashtable<String, SpaceMarine> hashtable){
        StringBuilder info = new StringBuilder();
        if (!hashtable.isEmpty()) {
            List<String> sortedList = hashtable
                    .keySet()
                    .stream()
                    .sorted(Comparator.comparingInt(String::length))
                    .collect(Collectors.toList());
            for (String key : sortedList) {
                info.append("key=\"").append(key).append("\"\t")
                        .append("id=\"").append(hashtable.get(key).getId()).append("\"\t")
                        .append("name=\"").append(hashtable.get(key).getName()).append("\"\t")
                        .append("coordinates=\"").append(hashtable.get(key).getCoordinates().toString()).append("\"\t")
                        .append("creationDate=\"").append(hashtable.get(key).getCreationDate()).append("\"\t")
                        .append("health=\"").append(hashtable.get(key).getHealth()).append("\"\t")
                        .append("heartCount=\"").append(hashtable.get(key).getHeartCount()).append("\"\t")
                        .append("achievements=\"").append(hashtable.get(key).getAchievements()).append("\"\t")
                        .append("category=\"").append(hashtable.get(key).getCategory()).append("\"\t")
                        .append("chapter=\"").append(hashtable.get(key).getChapter()).append("\"");
                if (hashtable.get(key).getHealth() == null) info.append("\nЗначение переменной health равно null!\n");
                if (hashtable.get(key).getAchievements() == null) info.append("\nЗначение переменной achievements равно null!\n");
                if (hashtable.get(key).getCategory() == null) info.append("\nЗначение переменной category равно null!\n");
                if (hashtable.get(key).getChapter() == null) info.append("\nЗначение переменной chapter равно null!\n");
            }
        }else info.append("В коллекции нет элементов!");
        return info.toString();
    }
    public String show(Hashtable<String, SpaceMarine> hashtable){
        StringBuilder show = new StringBuilder();
        if (!hashtable.isEmpty()) 
            show.append("Элементы коллекции: ")
                .append(hashtable.keySet()
                        .stream()
                        .sorted(Comparator.comparingInt(String::length))
                        .collect
                                (Collectors.toList())
                        .toString(),
                        1,
                        hashtable.keySet().toString().length() - 1);
        else show.append("В коллекции нет элементов!");
        return show.toString();
    }
    public String print(Hashtable<String, SpaceMarine> hashtable){
        List<SpaceMarine> sortedList = hashtable
                .values()
                .stream()
                .sorted(
                        Comparator.comparing(SpaceMarine::getCategory))
                .collect(
                        Collectors.toList());
        StringBuilder show = new StringBuilder();
        if (!hashtable.isEmpty()) for (SpaceMarine sp : sortedList) for (String key : hashtable.keySet()) if (hashtable.get(key) == sp) show.append(key).append(" ");
        else show.append("В коллекции нет элементов!");
        return show.toString();
    }
    public Hashtable<String, SpaceMarine> remove_any_by_chapter(Hashtable<String, SpaceMarine> hashtable, Command command){
        List<SpaceMarine> sortedList = hashtable
                .values()
                .stream()
                .filter(s-> s.getChapter().toString().equals(command.getKey()))
                .collect(Collectors.toList());
        if (!hashtable.isEmpty())
            for (SpaceMarine sp : sortedList)
                for (String key : hashtable.keySet())
                    if (hashtable.get(key) == sp) hashtable.remove(key);
        return hashtable;
    }
    public String remove_any_by_chapter(Command command){
        return "Элементы коллекции, принадлежащие " +
                command.getKey() +
                " успешно удалены!";
    }
    public Hashtable<String, SpaceMarine> remove_lower(Hashtable<String, SpaceMarine> hashtable, Command command){
        try {
            List<SpaceMarine> sortedList = hashtable.values()
                    .stream()
                    .filter(s -> s.getId() >= Integer.parseInt(command.getKey()))
                    .collect(Collectors.toList());
            Hashtable<String, SpaceMarine> subhashtable = new Hashtable<>();
            for (SpaceMarine sp : sortedList)
                for (String key : hashtable.keySet())
                    if (sp == hashtable.get(key)) subhashtable.put(key, hashtable.get(key));
            return subhashtable;
        }catch(NumberFormatException e){
            return hashtable;
        }
    }
    public String remove_lower(Command command){
        try {
            return "Элементы коллекции с id меньше " +
                    Integer.parseInt(command.getKey()) +
                    " успешно удалены!";
        }catch (NumberFormatException e){
            return "id представляет собой целое число!";
        }
    }
    public Hashtable<String, SpaceMarine> remove_greater_key(Hashtable<String, SpaceMarine> hashtable, Command command){
        try {
            Hashtable<String, SpaceMarine> subhashtable = new Hashtable<>();
            List<String> sortedList = hashtable
                    .keySet()
                    .stream()
                    .filter(s -> s.length() <= Integer.parseInt(command.getKey()))
                    .collect(Collectors.toList());
            for (String key : sortedList) subhashtable.put(key, subhashtable.get(key));
            return subhashtable;
        } catch (NumberFormatException e){
            return hashtable;
        }
    }
    public String remove_greater_key(Command command){
        try {
            return "Элементы коллекции с длиной ключа больше " +
                    Integer.parseInt(command.getKey()) +
                    " успешно удалены!";
        }catch (NumberFormatException e){
            return "Длина ключа - целое число!";
        }
    }
    public Hashtable<String, SpaceMarine> remove_lower_key (Hashtable<String, SpaceMarine> hashtable, Command command){
        try {
            Hashtable<String, SpaceMarine> subhashtable = new Hashtable<>();
            List<String> sortedList = hashtable
                    .keySet()
                    .stream()
                    .filter(s -> s.length() >= Integer.parseInt(command.getKey()))
                    .collect(Collectors.toList());
            for (String key : sortedList) subhashtable.put(key, subhashtable.get(key));
            return subhashtable;
        }catch (NumberFormatException e){
            return hashtable;
        }
    }
    public String remove_lower_key (Command command){
        try {
            return "Элементы коллекции с длиной ключа меньше " +
                    Integer.parseInt(command.getKey()) +
                    " успешно удалены!";
        }catch(NumberFormatException e){
            return "Длина ключа - целое число!";
        }
    }
    public String filter_greater_than_achievements(Hashtable<String, SpaceMarine> hashtable, Command command){
        try {
            StringBuilder message = new StringBuilder();
            List<SpaceMarine> sortedList = hashtable
                    .values()
                    .stream()
                    .filter(s -> s.getAchievements().length() > Integer.parseInt(command.getKey()))
                    .collect(Collectors.toList());
            for (SpaceMarine sp : sortedList)
                for (String key : hashtable.keySet())
                    if (hashtable.get(key) == sp) message.append(key).append(" ");
            return message.toString();
        }catch(NumberFormatException e){
            return "Длина строки \"achievements\" - целое число!";
        }
    }
    public String insert(Command command){
        return "Элемент коллекции с ключом \"" + 
                command.getKey() +
                "\" успешно добавлен/обновлен!";
    }
    public Hashtable<String, SpaceMarine> update(Hashtable<String, SpaceMarine> hashtable, Command command){
        try {
            for (String key : hashtable.keySet()) {
                if (hashtable.get(key).getId() == Integer.parseInt(command.getKey())) {
                    hashtable.put(key, command.getSpaceMarine());
                }
            }
            return hashtable;
        }catch (NumberFormatException e){
            return hashtable;
        }
    }
    public String update(Command command, Hashtable<String, SpaceMarine> hashtable){
        try {
            int id = -1;
            for (String key : hashtable.keySet()) {
                if (hashtable.get(key).getId() == Integer.parseInt(command.getKey())) {
                    hashtable.put(key, command.getSpaceMarine());
                    id = hashtable.get(key).getId();
                }
            }
            if (id > -1) return "Элемент коллекции с Id " +
                    Integer.parseInt(command.getKey()) +
                    " успешно обновлен!";
            else return "Элемента с Id = " + Integer.parseInt(command.getKey() + " в коллекции нет!");
        }catch (NumberFormatException e){
            return "id представляет собой целое число";
        }
    }
}
