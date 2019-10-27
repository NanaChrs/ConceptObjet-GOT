package gameplay;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;


public class FileManager {
    protected static String fileName = "logs.txt";
    
    public static void writeToLogFile(String content) throws IOException {
    	FileWriter fw = new FileWriter(FileManager.fileName, true);
        BufferedWriter bw = new BufferedWriter(fw);

        bw.write(content);
        bw.newLine();
        bw.close();
    }
    
    public static void createLogFile() {
    	try {
			Files.createFile(Paths.get(FileManager.fileName));
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
