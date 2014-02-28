package modelmanager;

import javafx.beans.property.BooleanPropertyBase;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;

public class SimModel {
	
    private final SimpleStringProperty modelId;
    private final SimpleStringProperty date;
    private final SimpleStringProperty modelSrc;
    private final SimpleStringProperty mode;
    private final SimpleStringProperty comment;
    private final SimpleStringProperty gold;
    private final SimpleBooleanProperty ref;
    private final SimpleBooleanProperty sel;

    public SimModel(String mId, String dat, String modelSource, String mode, 
    		String comment, String gold, boolean ref, boolean sel) {
        this.modelId = new SimpleStringProperty(mId);
        this.date = new SimpleStringProperty(dat);
        this.modelSrc = new SimpleStringProperty(modelSource);
        this.mode = new SimpleStringProperty(mode);    	
        this.comment = new SimpleStringProperty(comment);
        this.gold = new SimpleStringProperty(gold);
        this.ref = new SimpleBooleanProperty(ref);
        this.sel = new SimpleBooleanProperty(sel);
    }
    
    public String getModelId() {
        return modelId.get();
    }

    public void setModelId(String id) {
        modelId.set(id);
    }

    public String getDate() {
        return date.get();
    }

    public void setDate(String dat) {
        date.set(dat);
    }

    public String getModelSrc() {
        return modelSrc.get();
    }

    public void setModelSrc(String src) {
        modelSrc.set(src);
    }

    public String getMode() {
        return mode.get();
    }

    public void setMode(String mod) {
        mode.set(mod);
    }

    public String getComment() {
        return comment.get();
    }

    public void setComment(String comm) {
        comment.set(comm);
    }

    public String getGold() {
        return gold.get();
    }

    public void setGold(String gld) {
        gold.set(gld);
    }

    public boolean getRef() {
        return ref.get();
    }

    public void setRef(boolean rfc) {
        ref.set(rfc);
    }

    public boolean getSel() {
        return sel.get();
    }

    public void setSel(boolean slt) {
        sel.set(slt);
    }

}
