/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.msu.frib.xal.db2xal;

import java.util.Date;
import java.util.Iterator;
import java.util.List;
import org.openepics.model.api.ModelDB;
import org.openepics.model.entity.BeamlineSequence;
import org.openepics.model.entity.Element;
import org.openepics.model.entity.ElementTypeProp;

/**
 *
 * @author chu
 */
public class Db2Xal {
    
    public void write2ModelParam() {
        
    }
    
    /**
     * write to XAL .impl file
     */
    public void write2IMPL() {
        ModelDB md = new ModelDB();
        
        List<ElementTypeProp> typeMapping = md.getAllElementClassMappings();
        StringBuilder sb = new StringBuilder("<?xml version = '1.0' encoding = 'UTF-8'?>\n" 
            + "<!DOCTYPE deviceMapping SYSTEM \"xdxf.dtd\">\n"
            + "<deviceMapping>\n");
        
        Iterator<ElementTypeProp> it = typeMapping.iterator();
        while (it.hasNext()) {
            ElementTypeProp etp = it.next();
            sb.append("   <device type=\"");
            sb.append(etp.getElementTypeId().getElementType());
            sb.append("\" class=\"");
            sb.append(etp.getElementTypePropDefault());
            sb.append("\"/>\n");
        }
        
        sb.append("</deviceMapping>\n");
        
        System.out.println(sb);
    }
    
    public void write2XDXF() {
        StringBuilder sb = new StringBuilder("<?xml version=\"1.0\" encoding=\"utf-8\"?>\n"
            + "<!DOCTYPE xdxf SYSTEM \"xdxf.dtd\">\n");
        sb.append("<xdxf date=\"");
        Date date = new Date();
        sb.append(date.toString());
        sb.append("\" system=\"");
        // TODO figure out the accelerator system name from DB
        sb.append("accelerator\" ");
        sb.append("ver=\"1.0.0\">\n");
        
        // TODO set up combo sequences
        
        // get all sequences
        ModelDB mdb = new ModelDB();
        List<BeamlineSequence> blsList = mdb.getAllSequences();
        Iterator<BeamlineSequence> blsIt = blsList.iterator();
        
        // loop through each sequence
        while (blsIt.hasNext()) {
            BeamlineSequence bls = blsIt.next();
            sb.append("   <sequence id=\"");
            sb.append(bls.getSequenceName());
            sb.append("\" len=\"");
            sb.append(bls.getSequenceLength());
            sb.append("\">\n");
            sb.append("      <attributes>\n");
            sb.append("         <sequence predecessors=\"");
            sb.append(bls.getPredecessorSequence());
            sb.append("\"/>\n");
            sb.append("      </attributes>\n");
            
            // loop through each node
            List<Element> eList = mdb.getAllElementForSequence(bls.getSequenceName());
            Iterator<Element> eIt = eList.iterator();
            while (eIt.hasNext()) {
                Element e = eIt.next();
                sb.append("      <node id=\"");
                sb.append("");
            }
            
            // close the sequence
            sb.append("   </sequence>\n");
        }
        
        // TODO for power supplies
        
        
        // close
        sb.append("</xdxf>");
        
        System.out.println(sb);
   }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Db2Xal x = new Db2Xal();
        x.write2XDXF();
    }
    
}
