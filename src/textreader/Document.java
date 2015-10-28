package textreader;

import java.io.File;
import java.util.Collections;
import java.util.LinkedList;

public class Document {

    private LinkedList <String> loadedRows;
    private File file;
    private DocumentStatus status;
    
    public Document(File file) {
        this.file = file;
        loadedRows = new LinkedList();
        status = DocumentStatus.NotLoaded;
    }
    
    public LinkedList <String> getLoadedRows() {
        return loadedRows;
    }

    public File getFile() {
        return file;
    }

    public DocumentStatus getStatus() {
        return status;
    }

    public void setStatus(DocumentStatus status) {
        this.status = status;
    }
}
