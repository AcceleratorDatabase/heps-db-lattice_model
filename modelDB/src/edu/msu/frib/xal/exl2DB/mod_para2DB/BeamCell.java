/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.msu.frib.xal.exl2DB.mod_para2DB;

/**
 *
 * @author lv
 * @author chu
 */
public class BeamCell {

    public String tableName;
    public String name;
    public String category;
    public String datatype;
    public Object value;

    public String getTableName() {
        return tableName;
    }
    
    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDatatype() {
        return datatype;
    }

    public void setDatatype(String datatype) {
        this.datatype = datatype;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }    
}
