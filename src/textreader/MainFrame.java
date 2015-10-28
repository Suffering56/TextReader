package textreader;

import java.awt.Toolkit;
import javax.swing.JFrame;

class MainFrame extends JFrame{
    
    public MainFrame(Document doc) {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle(doc.getFile().getName());
        setBounds(screenWidth/4,screenHeight/4,screenWidth/2,screenHeight/2); 
        panel = new MainPanel(doc);
        getContentPane().add(panel);
    }    
   
    public static final int screenWidth = Toolkit.getDefaultToolkit().getScreenSize().width;
    public static final int screenHeight = Toolkit.getDefaultToolkit().getScreenSize().height;
    public static MainPanel panel;
}   
