package modelmanager;

import javafx.beans.property.SimpleStringProperty;

public class Device {
    private final SimpleStringProperty deviceName;
    private final SimpleStringProperty pos;
    private final SimpleStringProperty propName;
    private final SimpleStringProperty propVal;

    public Device(String dName, String pos, String propName, String propVal) {
        this.deviceName = new SimpleStringProperty(dName);
        this.pos = new SimpleStringProperty(pos);
        this.propName = new SimpleStringProperty(propName);
        this.propVal = new SimpleStringProperty(propVal);
    }

    public String getDeviceName() {
        return deviceName.get();
    }

    public void setDeviceName(String dName) {
        deviceName.set(dName);
    }

    public String getPos() {
        return pos.get();
    }

    public void setPos(String s) {
        pos.set(s);
    }

    public String getPropName() {
        return propName.get();
    }

    public void setPropName(String pName) {
        propName.set(pName);
    }
    
    public String getPropVal() {
    	return propVal.get();
    }
    
    public void setPropVal(String val) {
    	propVal.set(val);
    }

}
