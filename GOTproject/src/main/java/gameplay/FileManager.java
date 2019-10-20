package gameplay;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;


public class FileManager {
    protected static String fileName = "logs.txt";
    
    /** 
     * Constructor of the class
     * @throws IOException 
     */ 
    public FileManager() throws IOException {
    	//creates file for logs 
        Files.createFile(Paths.get(FileManager.fileName));
    }
    
    public static void writeToLogFile(String content) throws IOException {
    	FileWriter fw = new FileWriter(FileManager.fileName, true);
        BufferedWriter bw = new BufferedWriter(fw);

        bw.write(content);
        bw.newLine();
        bw.close();
    }
}
