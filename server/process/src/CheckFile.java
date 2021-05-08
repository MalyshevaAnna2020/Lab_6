import java.io.File;
import java.io.FileNotFoundException;

/**
 * This class checks the ability of the file to write or read
 * @author Ann
 */
public class CheckFile {
    /**
     * Checks the ability of the file to read
     * @param file is the file (File)
     * @throws FileNotFoundException work with files (existing)
     * @throws FileNotRead work with files (reading)
     */
    public void checkFile(File file) throws FileNotFoundException, FileNotRead {
        if (!file.exists())
            throw new FileNotFoundException();

        if (!file.canRead())
            throw new FileNotRead();

    }


}
