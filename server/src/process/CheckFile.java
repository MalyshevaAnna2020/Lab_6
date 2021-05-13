package process;

import java.io.File;
import java.io.FileNotFoundException;

public class CheckFile {
    public void checkFile(File file) throws FileNotFoundException, FileNotWrite, FileNotRead {
        if (!file.exists())
            throw new FileNotFoundException();

        if (!file.canRead())
            throw new FileNotRead();

        if (!file.canWrite())
            throw new FileNotWrite();

    }
}
