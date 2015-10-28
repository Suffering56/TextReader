package textreader;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import javax.swing.JFileChooser;

public class TextReader {

    public static void main(String[] args) throws FileNotFoundException, IOException {    
        File loadingFile = new File("");
        if (!loadingFile.exists() ) {                                           //Если файла, указанного в аргументе не существует, 
            loadingFile = fileChoose();                                         //открываем окно выбора нового файла
        }
        
        Document doc = new Document(loadingFile);
        new FileReading(doc).start();

        MainFrame frame;
        (frame = new MainFrame(doc) ).setVisible(true);          
    }     
 
    private static File fileChoose() {
        File result = null;                               
        JFileChooser chooser = new JFileChooser();                         
        chooser.setCurrentDirectory(new File("./texts"));        

        if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {          
            result = chooser.getSelectedFile();
        }
        else {
            System.exit(0);                                                     //Если пользователь нажал на cancel - выходим из программы
        }
        return result;
    }
}

