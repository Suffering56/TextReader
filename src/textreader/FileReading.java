package textreader;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collections;

public class FileReading extends Thread {
    public FileReading(Document doc) {
        this.document = doc;
    }
    
    @Override
    public void run()  {
        String line;
        BufferedReader buffInputStream;
        document.setStatus(DocumentStatus.Loading);
        
        try {
            buffInputStream = new BufferedReader(new InputStreamReader(new FileInputStream(document.getFile()), "UTF-8"));
            while ((line = buffInputStream.readLine()) != null) {
                line = line.trim();
                Collections.synchronizedCollection(document.getLoadedRows()).add(line);
                Thread.sleep(0);       
            }
            document.setStatus(DocumentStatus.Loaded);
            System.out.println("File loaded");
                
        } 
        catch (IOException | InterruptedException ex) {}
        
        }       //Конец метода run();
        private Document document;
    }   //Конец класса

