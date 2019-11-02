package gameplay;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;


public class FileManager {
    protected static String fileName = "logs.txt";
    
    private static void writeToLogFile(String content) throws IOException {
    	FileWriter fw = new FileWriter(FileManager.fileName, true);
        BufferedWriter bw = new BufferedWriter(fw);

        bw.write(content);
        bw.newLine();
        bw.close();
    }
    
    public static void writeToLogFile(String header, String content) throws IOException {
        writeToLogFile("["+header+"] "+content);
    }
    
    public static void writeToLogFile(int nbLineBreak, String header, String content) throws IOException {
        String lineBreak = "";
        for (int i = 0; i < nbLineBreak; ++i) {
        	lineBreak += "\n";
        }
        writeToLogFile(lineBreak+"["+header+"] "+content);
    }
    
    public static void createLogFile() {
    	try {
            if (Files.notExists(Paths.get(FileManager.fileName))) {
                Files.createFile(Paths.get(FileManager.fileName));
            }
        } 
        
        catch (IOException e) {
            System.out.println("[ERROR] Impossible de créer le fichier log");
        }
    }
    
    public static void cleanLogFile () {
    	try {
            Files.delete(Paths.get(FileManager.fileName));
            createLogFile();
        } 

        catch (IOException e) {
            System.out.println("[ERROR] Impossible de nettoyer le fichier log");
        }
    }
}
