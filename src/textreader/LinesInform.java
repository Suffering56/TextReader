/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package textreader;

/**
 *
 * @author Suffering
 */
public class LinesInform {
    public LinesInform(int parentLineIndex, int startIndex, int endIndex) {
        this.parentLineIndex = parentLineIndex;
        this.startIndex = startIndex;
        this.endIndex = endIndex;
    }

    public void setEndIndex(int endIndex) {
        this.endIndex = endIndex;
    }

    @Override
    public String toString() {
        return "LinesInform{" + "parentLineIndex=" + parentLineIndex + ", startIndex=" + startIndex + ", endIndex=" + endIndex + '}';
    }

    public int getStartIndex() {
        return startIndex;
    }

    public int getEndIndex() {
        return endIndex;
    }

    public int getParentLineIndex() {
        return parentLineIndex;
    }
    
    private int parentLineIndex;
    private int startIndex;
    private int endIndex; 
}
