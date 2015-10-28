package textreader;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
import java.util.Arrays;
import java.util.LinkedList;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.Timer;

class MainPanel extends JPanel {

    /**
     * Процедура отправки строки на обработку
     * 
     * @param line строка
     */
    
    public MainPanel(Document doc) {
        this.doc = doc;
        setBorder(BorderFactory.createLineBorder(Color.yellow));                
        setFocusable(true);                                                     
        listener = new OnKeyPress(this);       
        addKeyListener(listener);
        ActionListener timerListener = new OnTime();
        Timer timer = new Timer(500, timerListener);
        timer.start();
    }   
    
    @Override
    public void paintComponent(Graphics g) {                                    
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;  
        g2.setFont(largeFont);
        startY = fontHeight;
        
        if (!fontParamsApplied) {
            fontParams();
        }
        maxRowsOnFrame = ((getHeight() - (getHeight() % fontHeight))/fontHeight) - 1; //Определяем максимум строк, которые уместятся на фрейме

//        int changeIndex = 0;
//        if (prevRowIndex != currentRowIndex ) {
//            changeIndex = currentRowIndex - prevRowIndex;
//        }
//        prevRowIndex = currentRowIndex;
//
        LinkedList <String> visibleRows = getVisibleRows();

        for (int i = 0; i < visibleRows.size(); i++) {
//          g2.drawString("[" + i + "] = " + visibleRows.get(i), startX, startY);
            g2.drawString(visibleRows.get(i), startX, startY);
            startY += fontHeight;
        } 
    }
    
    private LinkedList <String> getVisibleRows() {
        
        
        LinkedList <String> internalRows = new LinkedList();
        LinkedList <String> visibleRows = new LinkedList();
        int counter = 0;
        boolean start = false;
        boolean end = false;
        
        switch(keyCommand) {
            case KeyEvent.VK_UP : {
                if (jStartFindIndex > 0) {
                    jStartFindIndex--;
                }
                else {
                    if (iStartFindIndex > 0) {
                        iStartFindIndex--;
                        jStartFindIndex = lineToInternalRows(doc.getLoadedRows().get(iStartFindIndex)).size() - 1;
                    }
                }
            } break;
                
            case KeyEvent.VK_DOWN : {
                if (jStartFindIndex == firstExternalRowSize - 1) {
                    jStartFindIndex = 0;
                    iStartFindIndex++;
                }
                else {
                    jStartFindIndex++;
                }
                if (iStartFindIndex > doc.getLoadedRows().size() - maxRowsOnFrame - 1) {
                    for (int i = iStartFindIndex; i < doc.getLoadedRows().size(); i++) {
                        counter += lineToInternalRows(doc.getLoadedRows().get(i)).size();
                    }
                    counter = counter - jStartFindIndex;

                    if (counter < maxRowsOnFrame) {
                        if (jStartFindIndex > 0) {
                            jStartFindIndex--;
                        }
                        else {
                            if (iStartFindIndex > 0) {
                                iStartFindIndex--;
                                jStartFindIndex = lineToInternalRows(doc.getLoadedRows().get(iStartFindIndex)).size() - 1;
                            }
                        }
                    }
                }
            } break;
                
            case KeyEvent.VK_PAGE_UP : {
//                KeyEvent e = new KeyEvent(this, 401 ,System.currentTimeMillis(), 0, 38 );
//                listener.keyPressed(e);
                for (int i = 0; i < maxRowsOnFrame; i++) {
                    if (jStartFindIndex > 0) {
                        jStartFindIndex--;
                    }
                    else {
                        if (iStartFindIndex > 0) {
                            iStartFindIndex--;
                            jStartFindIndex = lineToInternalRows(doc.getLoadedRows().get(iStartFindIndex)).size() - 1;
                        }
                    }
                }
            } break;    
                
            case KeyEvent.VK_PAGE_DOWN : {
                for (int k = 0; k < maxRowsOnFrame; k++) {
                    if (jStartFindIndex == firstExternalRowSize - 1) {
                        System.out.println("jStartFindIndex = " + jStartFindIndex);
                        System.out.println("");
                        jStartFindIndex = 0;
                        iStartFindIndex++;
                    }
                    else {
                        jStartFindIndex++;
                    }
                    if (iStartFindIndex > doc.getLoadedRows().size() - maxRowsOnFrame - 1) {
                        for (int i = iStartFindIndex; i < doc.getLoadedRows().size(); i++) {
                            counter += lineToInternalRows(doc.getLoadedRows().get(i)).size();
                        }
                        counter = counter - jStartFindIndex;

                        if (counter < maxRowsOnFrame) {
                            if (jStartFindIndex > 0) {
                                jStartFindIndex--;
                            }
                            else {
                                if (iStartFindIndex > 0) {
                                    iStartFindIndex--;
                                    jStartFindIndex = lineToInternalRows(doc.getLoadedRows().get(iStartFindIndex)).size() - 1;
                                }
                            }
                        }
                    }   
                }
            } break;
                
            case KeyEvent.VK_HOME : {
                iStartFindIndex = 0;
                jStartFindIndex = 0;
            } break; 
                
            case KeyEvent.VK_END : {
                for (int i = doc.getLoadedRows().size() - 1; i >= 0; i--) {
                    counter += lineToInternalRows(doc.getLoadedRows().get(i)).size();
                    if (counter >= maxRowsOnFrame) {
                        iStartFindIndex = i;
                        jStartFindIndex = counter - maxRowsOnFrame;
                        break;
                    }
                }
                
            } break;
            case KeyEvent.VK_ENTER : {

            } break;
        }
        
        keyCommand = KeyEvent.VK_ENTER;
        counter = 0;
        
        for (int i = iStartFindIndex; i < doc.getLoadedRows().size(); i++) {
            if (end) {
                break;
            }
            internalRows = lineToInternalRows(doc.getLoadedRows().get(i));
            for (int j = 0; j < internalRows.size(); j++) {
                if (j == jStartFindIndex && start == false) {
                    start = true;
                    firstExternalRowSize = internalRows.size();
                }  
                if (start) {
                    visibleRows.add(internalRows.get(j));
                    counter++;
                    if (counter == maxRowsOnFrame) {
                        lastExternalRowSize = internalRows.size();
                        iEndFindIndex = i;
                        jEndFindIndex = j;
                        end = true;
                        break;
                    }
                }
            }
        }
        return visibleRows;
    }
    


    private LinkedList <String> rowToWords(String row) { 
        LinkedList <String> gettingWords  = new LinkedList();
        gettingWords.addAll(Arrays.asList(row.split(" ")));          
        return gettingWords;
    }
    
    private LinkedList <String> lineToInternalRows (String line) {               //При считывании новой линии
        
        LinkedList <String> words = rowToWords(line);
        LinkedList <String> editedRows = new LinkedList();
        LinkedList <String> rowsList  = new LinkedList();
        editedRows.add("");                                                     //!!! Добавляем новую(пустую) строку
        
        Font currFont = largeFont;
       
        int rowLength = 0;
        int currentWordLength;
        
        
        for (int i = 0; i < words.size(); i++) { 
            currentWordLength = (int)currFont.getStringBounds(words.get(i), context).getWidth();

            if (currentWordLength + 2*offset > this.getWidth() ) {                      //Если ширина слова больше ширины экрана 
                editedRows.set(editedRows.size()-1, words.get(i));              
                rowsList.add(editedRows.getLast());                             //rowsList.add
                editedRows.add("");                                             //!!! Добавляем новую(пустую) строку
                rowLength = 0;                                             
            }
            else {                                                                          //Если ширина слова меньше ширины экрана
                if (rowLength + currentWordLength + 2*offset < this.getWidth() ) {          //И ширина текущей строки + текущего слова меньше ширины экрана
                    rowLength += currentWordLength;                                    
                    
                    editedRows.set(editedRows.size()-1, editedRows.getLast() + words.get(i) );  //Добавляем в конец строки слово
                                   
                    
                    if (words.get(i).equals(" ") == false) {                                //Если текущее слово не является пробелом
                        rowLength += spaceWidth;                                                  
                        editedRows.set(editedRows.size()-1, editedRows.getLast() + " ");    //Добавляем в конец строки пробел
                    }
                }
                else {                                                                      //Если строка не уместилась
                    rowLength = currentWordLength + spaceWidth;  
                    rowsList.add(editedRows.getLast());                         //rowsList.add
                    editedRows.add(words.get(i) + " ");                         //!!! Добавляем новую строку и вставляем в нее первое слово + пробел                                        
                }
            }                  
        }   //конец цикла for
        rowsList.add(editedRows.getLast());
        return rowsList;    
    }   //конец метода
    
    public final void fontParams() {
        Graphics2D graphics = (Graphics2D)getGraphics();
        context = graphics.getFontRenderContext();
        spaceWidth = (int)largeFont.getStringBounds(" ",context).getWidth();    //Ширина пробела
        fontHeight = (int)largeFont.getStringBounds(" ",context).getHeight();   //Высота текста  
        fontParamsApplied = true;
    }

    
    static LinkedList <String> loadedRows;
    private int currentRowIndex = 0;
    private int initialNewRowsCount = 0;
    private int iStartFindIndex = 0;
    private int jStartFindIndex = 0;  
    private int iEndFindIndex = 0;
    private int jEndFindIndex = 0;
    
    private int lastExternalRowSize = 0;
    private int firstExternalRowSize = 0;
    
    private static final Font largeFont = new Font("SansSerif", Font.PLAIN, 16);              //Создаем большой шрифт
    private static final Font smallFont = new Font("SansSerif", Font.PLAIN, 12);              //Создаем мелкий шрифт
    private FontRenderContext context;
    private int spaceWidth;
    private int fontHeight;
    private int maxRowsOnFrame;
    private boolean fontParamsApplied;
    
    
    private int prevFrameWidth = 0;
    

    private int editedRowsCount = -1;
    private int startFindIndex = 0;
    
    private int prevRowIndex = 0;
    private KeyListener listener;
    private Font prevFont;
    private Rectangle2D prevWordBound;  
    private double prevWordWidth;  
    private int startX = 10, startY = 45;
    private static final int offset = 10;
    private Document doc;
    private int visibleRowsCount = 0;
    private int indexingRowsCount = 0;
    private int keyCommand;

    public void setKeyCommand(int keyCommand) {
        this.keyCommand = keyCommand;
    }

    public int getKeyCommand() {
        return keyCommand;
    }
    
    
    private class OnTime implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            repaint();

        if (prevFrameWidth != getWidth() ) {
            
        }
        prevFrameWidth = getWidth();
        }
    }
}

