package process;

import java.io.File;
import java.io.FileNotFoundException;

public class FindFile {
    public File findFile(File file_main){
        try {
            new CheckFile().checkFile(file_main);
            System.out.println("Файл найден!");
            return file_main;
        } catch (FileNotFoundException e) {
            System.out.println("Файл не найден!");
            return null;
        } catch (FileNotRead e) {
            System.err.println("Файл не доступен для чтения!");
            return null;
        } catch (FileNotWrite e) {
            System.err.println("Файл не доступен для записи!");
            return file_main;
        }
    }
}
