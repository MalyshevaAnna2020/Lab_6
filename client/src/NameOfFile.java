import java.io.File;
import java.io.FileNotFoundException;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class NameOfFile {
    public File thefile() {
        Scanner in = new Scanner(System.in);
        System.out.println("Введите имя файла для чтения:");
        try {
            String file = in.nextLine();
            File file_main = new File(file);
            try {
                new CheckFile().checkFile(file_main);
            } catch (FileNotFoundException e) {
                System.out.println("Файл не найден!");
                file_main = new NameOfFile().thefile();
            } catch (FileNotRead e) {
                System.err.println("Файл не доступен для чтения!");
                file_main = new NameOfFile().thefile();
            } catch (FileNotWrite e) {
                System.err.println("Файл не доступен для записи!");
                file_main = new NameOfFile().thefile();
            }
            return file_main;
        } catch (NoSuchElementException exp) {
            System.err.println("Завершение работы программы!");
        }
        return null;

    }
}
