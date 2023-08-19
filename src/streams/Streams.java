package streams;

//todo - in this class create two methods that can handle file copying
// 1.first method must include src file and from it your method will have to
// create another duplicate file. Then name of duplicate file should be like
// notes.txt => notes(1).txt
// 2.second method must include as an input two parameters , scr file and
// destination file. This method will reads from src file and writes to
// destination file.
// 3.For the class involve logger to log method operation details.
// When your methods throws an exception log error message to the console
// When copy file successfully finishes log info about it.

import java.io.*;
import java.nio.file.Files;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
public class Streams {
    private static final Logger logger = Logger.getLogger(Streams.class.getName());

    static {
        Logger rootLogger = Logger.getLogger("");
        ConsoleHandler consoleHandler = new ConsoleHandler();
        consoleHandler.setLevel(Level.ALL);
        rootLogger.addHandler(consoleHandler);
        rootLogger.setLevel(Level.ALL);
    }

    public static void duplicateFile(File srcFile) {
        String fileName = srcFile.getName();
        File destFile = getFile(srcFile, fileName);

        try {
            Files.copy(srcFile.toPath(), destFile.toPath());
            logger.log(Level.INFO, "File duplicated successfully. Source: " + srcFile.getAbsolutePath() + ", Destination: " + destFile.getAbsolutePath());
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Error duplicating file: " + e.getMessage());
        }
    }

    private static File getFile(File srcFile, String fileName) {
        String baseName = fileName.substring(0, fileName.lastIndexOf('.'));
        String extension = fileName.substring(fileName.lastIndexOf('.'));
        int copyIndex = 1;

        String copyFileName = baseName + "(" + copyIndex + ")" + extension;
        File destFile = new File(srcFile.getParent(), copyFileName);

        while (destFile.exists()) {
            copyIndex++;
            copyFileName = baseName + "(" + copyIndex + ")" + extension;
            destFile = new File(srcFile.getParent(), copyFileName);
        }
        return destFile;
    }

    public static void copyFile(String srcFile, String destFile) {
        try (FileInputStream fis = new FileInputStream(srcFile);
             FileOutputStream fos = new FileOutputStream(destFile)) {
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = fis.read(buffer)) != -1) {
                fos.write(buffer, 0, bytesRead);
            }
            logger.log(Level.INFO, "File copied successfully. Source: " + srcFile + ", Destination: " + destFile);
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Error copying file: " + e.getMessage());
        }
    }


    public static void main(String[] args) throws IOException {
        File file = new File("src/streams/hello.txt");
        duplicateFile(file);
        copyFile("src/streams/hello.txt","src/streams/copied.txt");
    }
}