/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.msu.frib.xal.db2xal;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnit;
import javax.persistence.Query;
import org.openepics.model.api.BeamlineSequenceAPI;
import org.openepics.model.api.ElementAPI;
import org.openepics.model.api.ModelDB;
import org.openepics.model.entity.BeamlineSequence;
import org.openepics.model.entity.Element;
import org.openepics.model.entity.ElementProp;
import org.openepics.model.entity.ElementTypeProp;

/**
 *
 * @author chu
 */
public class Db2Xal {
    @PersistenceUnit
    static EntityManagerFactory emf = Persistence.createEntityManagerFactory("modelAPIPU");
    EntityManager em = emf.createEntityManager();

    @PersistenceContext
        
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
        List<BeamlineSequence> blsList = BeamlineSequenceAPI.getAllSequences();
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
            // need to treat RF specially
            List<Element> eList = ElementAPI.getAllElementForSequence(bls.getSequenceName());
            Iterator<Element> eIt = eList.iterator();
            while (eIt.hasNext()) {
                Element e = eIt.next();
                sb.append("      <node id=\"");
                sb.append(e.getElementName());
                sb.append("\" type=\"");
                sb.append(e.getElementTypeId().getElementType());
                sb.append("\" pos=\"");
                sb.append(e.getPos());
                sb.append("\" len=\"");
                sb.append(e.getLen());
                sb.append("\" s=\"");
                sb.append(e.getS());
                sb.append("\">\n");
                // check if there is any attribute for the element
                List<ElementProp> epList = ElementAPI.getAllPropertiesForElement(e.getElementName());
                if (!epList.isEmpty()) {
                    // insert node attributes
                    sb.append("         <attributes>\n");
                    
                    // set alignment data
                    Map attMap = getAlignmentAttributesForElement(e.getElementName());
                    if (!attMap.isEmpty()) {                                              
                        sb.append("            <align ");
                        
                        Set keySet = attMap.keySet();
                        Iterator<String> keyIt = keySet.iterator();
                        while (keyIt.hasNext()) {
                            String key = keyIt.next();
                            sb.append(key);
                            sb.append("=\"");
                            sb.append(attMap.get(key));
                            sb.append("\" ");
                        }
                                                
                        sb.append("/>\n");
                    }
                    
                    // set apertures
                    Map attMap1 = getApertureAttributesForElement(e.getElementName());
                    if (!attMap1.isEmpty()) {                        
                        sb.append("            <aperture ");
                        
                        Set keySet1 = attMap1.keySet();
                        Iterator<String> keyIt1 = keySet1.iterator();
                        while (keyIt1.hasNext()) {
                            String key1 = keyIt1.next();
                            sb.append(key1);
                            sb.append("=\"");
                            sb.append(attMap1.get(key1));
                            sb.append("\" ");
                        }
                                                
                        sb.append("/>\n");
                    }
                    
                    // set magnet attributes
                    Map attMap2 = getMagnetAttributesForElement(e.getElementName());
                    if (!attMap2.isEmpty()) {
                        
                        sb.append("            <magnet ");
                        
                        Set keySet2 = attMap2.keySet();
                        Iterator<String> keyIt2 = keySet2.iterator();
                        while (keyIt2.hasNext()) {
                            String key2 = keyIt2.next();
                            sb.append(key2);
                            sb.append("=\"");
                            sb.append(attMap.get(key2));
                            sb.append("\" ");
                        }
                        
                        sb.append("/>\n");
                    }
                    
                    // set bpm attributes 
                    Map attMap3 = getBpmAttributesForElement(e.getElementName());
                    if (!attMap3.isEmpty()) {
                        
                        sb.append("            <bpm ");
                        
                        Set keySet3 = attMap3.keySet();
                        Iterator<String> keyIt3 = keySet3.iterator();
                        while (keyIt3.hasNext()) {
                            String key3 = keyIt3.next();
                            sb.append(key3);
                            sb.append("=\"");
                            sb.append(attMap.get(key3));
                            sb.append("\" ");
                        }
                        
                        sb.append("/>\n");
                    }
                    
                    // set rfgap attributes
                    Map attMap4 = getRfgapAttributesForElement(e.getElementName());
                    if (!attMap4.isEmpty()) {
                        
                        sb.append("            <bpm ");
                        
                        Set keySet4 = attMap4.keySet();
                        Iterator<String> keyIt4 = keySet4.iterator();
                        while (keyIt4.hasNext()) {
                            String key4 = keyIt4.next();
                            sb.append(key4);
                            sb.append("=\"");
                            sb.append(attMap.get(key4));
                            sb.append("\" ");
                        }
                        
                        sb.append("/>\n");
                    }                    
                    
                    sb.append("         </attributes>\n");
                
                }
                
                sb.append("      </node>\n");
            }
            
            // close the sequence
            sb.append("   </sequence>\n");
        }
        
        // TODO for power supplies
        sb.append("   <powersupplies>\n");
        
        sb.append("   </powersupplies>\n");
        
        // close
        sb.append("</xdxf>");
        
        System.out.println(sb);
    }
    
    private Map getMagnetAttributesForElement(String elm) {
        HashMap<String, Object> atts = new HashMap<>();
        
        Query q;
        q = em.createQuery("SELECT ep from ElementProp ep "
                + "WHERE ep.elementId.elementName = :elementname "
                + "AND ep.propCategory = \"magnet\"")
                .setParameter("elementname", elm);
        List<ElementProp> attrList = q.getResultList();
        
        Iterator<ElementProp> it = attrList.iterator();
        while (it.hasNext()) {
            ElementProp ep = it.next();
            if (ep.getElementPropDouble() != null) {
                atts.put(ep.getElementPropName(), ep.getElementPropDouble());
            }
            if (ep.getElementPropInt() != null) {
                atts.put(ep.getElementPropName(), ep.getElementPropInt());
            }
            if (ep.getElementPropString() != null) {
                atts.put(ep.getElementPropName(), ep.getElementPropString());
            }
        }
        
        return atts;
    }
    
    private Map getApertureAttributesForElement(String elm) {
        HashMap<String, Object> atts = new HashMap<>();
        
        Query q;
        q = em.createQuery("SELECT ep from ElementProp ep "
                + "WHERE ep.elementId.elementName = :elementname "
                + "AND ep.propCategory = \"aperture\"")
                .setParameter("elementname", elm);
        List<ElementProp> attrList = q.getResultList();
        
        Iterator<ElementProp> it = attrList.iterator();
        while (it.hasNext()) {
            ElementProp ep = it.next();
            if (ep.getElementPropDouble() != null) {
                atts.put(ep.getElementPropName(), ep.getElementPropDouble());
            }
        }
        
        return atts;
    }
    
    private Map getAlignmentAttributesForElement(String elm) {
        HashMap<String, Object> atts = new HashMap<>();
        
        Query q;
        q = em.createQuery("SELECT ep from ElementProp ep "
                + "WHERE ep.elementId.elementName = :elementname "
                + "AND ep.propCategory = \"align\"")
                .setParameter("elementname", elm);
        List<ElementProp> attrList = q.getResultList();
        
        Iterator<ElementProp> it = attrList.iterator();
        while (it.hasNext()) {
            ElementProp ep = it.next();
            if (ep.getElementPropDouble() != null) {
                atts.put(ep.getElementPropName(), ep.getElementPropDouble());
            }
        }
        
        return atts;
    }
    
    private Map getBpmAttributesForElement(String elm) {
        HashMap<String, Object> atts = new HashMap<>();
        
        Query q;
        q = em.createQuery("SELECT ep from ElementProp ep "
                + "WHERE ep.elementId.elementName = :elementname "
                + "AND ep.propCategory = \"bpm\"")
                .setParameter("elementname", elm);
        List<ElementProp> attrList = q.getResultList();
        
        Iterator<ElementProp> it = attrList.iterator();
        while (it.hasNext()) {
            ElementProp ep = it.next();
            if (ep.getElementPropDouble() != null) {
                atts.put(ep.getElementPropName(), ep.getElementPropDouble());
            }
        }
        
        return atts;
    } 
    
    private Map getRfgapAttributesForElement(String elm) {
        HashMap<String, Object> atts = new HashMap<>();
        
        Query q;
        q = em.createQuery("SELECT ep from ElementProp ep "
                + "WHERE ep.elementId.elementName = :elementname "
                + "AND ep.propCategory = \"rfgap\"")
                .setParameter("elementname", elm);
        List<ElementProp> attrList = q.getResultList();
        
        Iterator<ElementProp> it = attrList.iterator();
        while (it.hasNext()) {
            ElementProp ep = it.next();
            if (ep.getElementPropDouble() != null) {
                atts.put(ep.getElementPropName(), ep.getElementPropDouble());
            }
        }
        
        return atts;
    } 
    
        
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Db2Xal x = new Db2Xal();
//        x.write2IMPL();
//        x.write2ModelParam();
        x.write2XDXF();
    }
    
}
