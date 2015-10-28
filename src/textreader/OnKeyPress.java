/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package textreader;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 *
 * @author Suffering
 */
public class OnKeyPress implements KeyListener {
    
    public OnKeyPress(MainPanel processedPanel) {
        this.processedPanel = processedPanel;
    }
    
        @Override
        public void keyTyped(KeyEvent e) {}

        @Override
        public void keyPressed(KeyEvent e) {

            System.out.println("when = " + e.getWhen());
            System.out.println("mod = " + e.getModifiers());
            System.out.println("id = " + e.getID()); 
            System.out.println("keyCode = " + e.getKeyCode());
            System.out.println("component = " + e.getComponent());
            System.out.println("keyChar = " + e.getKeyChar());
            
            System.out.println("vk+up = " + KeyEvent.VK_UP);
            
            if (e.getKeyCode() == KeyEvent.VK_UP) {
                processedPanel.setKeyCommand(KeyEvent.VK_UP);
                processedPanel.repaint();
            }
            else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                processedPanel.setKeyCommand(KeyEvent.VK_DOWN);
                processedPanel.repaint();
            }
            else if (e.getKeyCode() == KeyEvent.VK_PAGE_UP) { 
                processedPanel.setKeyCommand(KeyEvent.VK_PAGE_UP);
                processedPanel.repaint();
            }
            else if (e.getKeyCode() == KeyEvent.VK_PAGE_DOWN) {
                processedPanel.setKeyCommand(KeyEvent.VK_PAGE_DOWN);
                processedPanel.repaint();
            }
            else if (e.getKeyCode() == KeyEvent.VK_HOME) {
                processedPanel.setKeyCommand(KeyEvent.VK_HOME);
                processedPanel.repaint();
            }
            else if (e.getKeyCode() == KeyEvent.VK_END) {
                processedPanel.setKeyCommand(KeyEvent.VK_END);
                processedPanel.repaint();
            }
            else if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                processedPanel.setKeyCommand(KeyEvent.VK_ENTER);
                processedPanel.repaint();
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {}     
    
    private MainPanel processedPanel;
}
