package JTEengine.Standard;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class JTEstandard {

    public JTEstandard() {}

    public static String fileContents(String filename) {
        try {
            File file = new File(filename);
            Scanner scanner = new Scanner(file);
            String contents = "";
            while (scanner.hasNextLine()) {
                contents = contents + scanner.nextLine() + "\n";
            }
            scanner.close();
            return contents.toString();
        } catch (FileNotFoundException fileNotFoundException) {
            fileNotFoundException.printStackTrace();
            return null;
        }
    }
}
