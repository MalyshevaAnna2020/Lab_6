import java.io.*;
import java.net.ConnectException;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Client {
    public static final int PORT = 1;
    public static final String HOST = "localhost";

    public static void main(String[] args) throws IOException {
        try {
            StringBuilder m_file = new StringBuilder();
            for (String string_arg : args) {
                m_file.append(string_arg);
            }
            System.out.println("Запущен клиент!");
            File file_main = new File(m_file.toString());
            try {
                new CheckFile().checkFile(file_main);
                System.out.println("Файл найден!");
            } catch (FileNotFoundException e) {
                System.out.println("Файл не найден!");
                file_main = new NameOfFile().thefile();
            } catch (FileNotRead e) {
                System.err.println("Файл не доступен для чтения!");
                file_main = new NameOfFile().thefile();
            } catch (FileNotWrite e) {
                System.err.println("Файл не доступен для записи!");
                file_main = new NameOfFile().thefile();
            } catch (NoSuchElementException ignored) {
            }
            int count = 0;
            while (count == 0) {
                try (Socket s = new Socket(HOST, PORT);) {
                    count++;
                    OutputStream oos = s.getOutputStream();
                    oos.write((file_main.getAbsolutePath()).getBytes(StandardCharsets.UTF_8));

                    InputStream ois = s.getInputStream();
                    byte[] bytes = new byte[2048];
                    ois.read(bytes);
                    System.out.println((new String(bytes, StandardCharsets.UTF_8)).trim());

                    System.out.println("Можно начинать вводить команды (длина строки не должна превышать 256)!");
                    String newcommand;
                    Scanner in = new Scanner(System.in);
                    int id = 1;
                    while (true) {
                        try {
                            newcommand = in.nextLine();
                            if (newcommand.length() > 256) {
                                System.err.println("Очень длинная строка! Введите более короткий вариант!");
                                continue;
                            }
                            if (newcommand.equals("")) continue;
                            newcommand = newcommand.trim();
                            Command command = new Command();
                            if (newcommand.equals("exit")) {
                                command.setNameOfCommand(NameOfCommand.EXIT);
                            } else if (newcommand.contains("help")) {
                                command.setNameOfCommand(NameOfCommand.HELP);
                            } else if (newcommand.contains("info")) {
                                command.setNameOfCommand(NameOfCommand.INFO);
                            } else if (newcommand.contains("show")) {
                                command.setNameOfCommand(NameOfCommand.SHOW);
                            } else if (newcommand.contains("insert")) {
                                command.setNameOfCommand(NameOfCommand.INSERT);
                                SpaceMarine spmarine = new SpaceMarine();
                                spmarine.setId(id++);
                                command.setKey(newcommand.substring(newcommand.indexOf("insert") + 6).trim());
                                command.setSpaceMarine(setSpaceMarine(spmarine));
                            } else if (newcommand.contains("update")) {
                                command.setNameOfCommand(NameOfCommand.UPDATE);
                                try {
                                    int newId = Integer.parseInt(newcommand.substring(newcommand.indexOf("update") + 6).trim());
                                    SpaceMarine spmarine = new SpaceMarine();
                                    spmarine.setId(newId);
                                    command.setSpaceMarine(setSpaceMarine(spmarine));
                                    command.setI(newId);
                                } catch (NumberFormatException e) {
                                    System.err.println("Переменная id представляет собой число!");
                                }
                            } else if (newcommand.contains("remove_key")) {
                                command.setNameOfCommand(NameOfCommand.REMOVE_KEY);
                                command.setKey(newcommand.substring(newcommand.indexOf("remove_key") + 10).trim());
                            } else if (newcommand.contains("clear")) {
                                command.setNameOfCommand(NameOfCommand.CLEAR);
                            } else if (newcommand.contains("execute_script")) {
                                try {
                                    command.setNameOfCommand(NameOfCommand.EXECUTE_SCRIPT);
                                    String strFile = newcommand.substring(newcommand.indexOf("execute_script") + 14).trim();
                                    File fileCommand = new File(strFile);
                                    new CheckFile().checkFileRead(fileCommand);
                                    command.setKey(fileCommand.getAbsolutePath());
                                } catch (FileNotFoundException e) {
                                    System.out.println("Файл не найден!");
                                    continue;
                                } catch (FileNotRead e) {
                                    System.err.println("Файл не доступен для чтения!");
                                    continue;
                                }
                            } else if (newcommand.contains("remove_lower_key")) {
                                command.setNameOfCommand(NameOfCommand.REMOVE_LOWER_KEY);
                                command.setI(Integer.parseInt(newcommand.substring(newcommand.indexOf("remove_lower_key") + 16).trim()));
                            } else if (newcommand.contains("remove_lower")) {
                                command.setNameOfCommand(NameOfCommand.REMOVE_LOWER);
                                command.setI(Integer.parseInt(newcommand.substring(newcommand.indexOf("remove_lower") + 12).trim()));
                            } else if (newcommand.contains("remove_greater_key")) {
                                command.setNameOfCommand(NameOfCommand.REMOVE_GREATER_KEY);
                                command.setI(Integer.parseInt(newcommand.substring(newcommand.indexOf("remove_greater_key") + 18).trim()));
                            } else if (newcommand.contains("remove_any_by_chapter")) {
                                command.setNameOfCommand(NameOfCommand.REMOVE_ANY_BY_CHAPTER);
                                command.setKey((("remove_any_by_chapter") + 16).trim());
                            } else if (newcommand.contains("filter_greater_than_achievements")) {
                                command.setNameOfCommand(NameOfCommand.FILTER_GREATER_THAN_ACHIEVEMENTS);
                            } else if (newcommand.contains("print_field_descending_category")) {
                                command.setNameOfCommand(NameOfCommand.PRINT_FIELD_DESCENDING_CATEGORY);
                            } else {
                                System.out.println("Введите команду help для получений сведений о существующих командах");
                                continue;
                            }

                            ByteArrayOutputStream bos = new ByteArrayOutputStream();
                            ObjectOutputStream out = new ObjectOutputStream(bos);
                            out.writeObject(command);
                            out.flush();
                            byte[] yourBytes = bos.toByteArray();
                            OutputStream os = s.getOutputStream();
                            os.write(yourBytes);
                            System.out.println("Команда передана серверу!");
                            if (newcommand.equals("exit")) {
                                break;
                            }
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

                        } catch (NoSuchElementException ignored) {
                        }
                    }
                } catch (ConnectException ignored){}
            }
        }catch (NullPointerException ignored){
        }

    }
    public static SpaceMarine setSpaceMarine(SpaceMarine spaceMarine){
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
