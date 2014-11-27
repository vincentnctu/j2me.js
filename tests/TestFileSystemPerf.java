import javax.microedition.io.*;
import javax.microedition.io.file.*;
import java.io.*;

public class TestFileSystemPerf {
    public static void main(String args[]) {
        try {
            String dirPath = System.getProperty("fileconn.dir.private");
            long then;

            String str = "I am the very model of a modern major general.";
            byte[] bytes = str.getBytes();

            then = System.currentTimeMillis();
            FileConnection file = (FileConnection)Connector.open(dirPath + "test.txt");
            System.out.println("Time to open file: " + (System.currentTimeMillis() - then) + "ms");

            then = System.currentTimeMillis();
            file.create();
            System.out.println("Time to create file: " + (System.currentTimeMillis() - then) + "ms");

            then = System.currentTimeMillis();
            OutputStream out = file.openOutputStream();
            for (int i = 0; i < 1000; i++) {
                out.write(bytes);
                out.flush();
            }
            System.out.println("Time to write/flush to output stream: " + (System.currentTimeMillis() - then) + "ms");

            then = System.currentTimeMillis();
            out.close();
            System.out.println("Time to close output stream: " + (System.currentTimeMillis() - then) + "ms");

            then = System.currentTimeMillis();
            file.delete();
            System.out.println("Time to delete file: " + (System.currentTimeMillis() - then) + "ms");

            then = System.currentTimeMillis();
            file.close();
            System.out.println("Time to close file: " + (System.currentTimeMillis() - then) + "ms");

            then = System.currentTimeMillis();
            file = (FileConnection)Connector.open(dirPath + "test.txt");
            System.out.println("Time to reopen file: " + (System.currentTimeMillis() - then) + "ms");

            then = System.currentTimeMillis();
            file = (FileConnection)Connector.open(dirPath + "test2.txt");
            file.create();
            out = file.openOutputStream();
            out.write(bytes);
            out.flush();
            out.close();
            file.delete();
            file.close();
            System.out.println("Time to access another file: " + (System.currentTimeMillis() - then) + "ms");

        } catch (Exception e) {
            System.out.println("Unexpected exception: " + e);
            e.printStackTrace();
        }
    }
}