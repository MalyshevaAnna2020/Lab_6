package process;

import command.Command;
import spacemarine.Chapter;
import spacemarine.Coordinates;
import spacemarine.SpaceMarine;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Date;
import java.util.Hashtable;

public class ExecuteScript {
    private static ExecuteScript instance = null;

    private ExecuteScript(){ }

    public static ExecuteScript getInstance() {
        if (ExecuteScript.instance == null) ExecuteScript.instance = new ExecuteScript();
        return ExecuteScript.instance;
    }

    public String checkFile(String file){
        return "execute_script " + 
                file + 
                " - это команда на исполнение этого же файла, возможна рекурсия!";
    }
    public String writeCommandsFromFiles(String file) throws IOException, FileNotRead, FileNotWrite {
        new CheckFile().checkFile(new File(file));
        char [] chars = new char[4096];
        FileReader reader = new FileReader(file);
        reader.read(chars);
        System.out.println(chars);
        String s = (new String(chars)).trim();
        reader.close();
        System.out.println(s);
        return s;
    }

}
