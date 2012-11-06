/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.msu.frib.xal.db2xal;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnit;
import org.openepics.model.api.BeamlineSequenceAPI;
import org.openepics.model.api.ElementAPI;
import org.openepics.model.api.ElementPropAPI;
import org.openepics.model.api.ModelDB;
import org.openepics.model.api.RfGapAPI;
import org.openepics.model.entity.BeamlineSequence;
import org.openepics.model.entity.Element;
import org.openepics.model.entity.ElementProp;
import org.openepics.model.entity.ElementTypeProp;
import org.openepics.model.entity.RfGap;

/**
 *
 * @author chu
 */
public class Db2Xal {

    @PersistenceUnit
//    static EntityManagerFactory emf = Persistence.createEntityManagerFactory("modelAPIPU");
//    EntityManager em = emf.createEntityManager();
    // define the accelerator name
    String accName = "frib";

    @PersistenceContext
    public void write2ModelParam() {
        // write the header
        StringBuilder sb = new StringBuilder("<?xml version = '1.0' encoding = 'UTF-8'?>\n"
                + "<!DOCTYPE tablegroup [\n"
                + "  <!ELEMENT tablegroup (table*) >\n"
                + "  <!ELEMENT table (schema, record*) >\n"
                + "    <!ATTLIST table name NMTOKEN #REQUIRED >\n"
                + "    <!ATTLIST table recordClass CDATA #IMPLIED >\n"
                + "    <!ELEMENT schema (attribute*) >\n"
                + "    <!ELEMENT attribute EMPTY >\n"
                + "    <!ATTLIST attribute isPrimaryKey (true|false) #IMPLIED>\n"
                + "    <!ATTLIST attribute name NMTOKEN #REQUIRED >\n"
                + "    <!ATTLIST attribute type CDATA #REQUIRED >\n"
                + "    <!ATTLIST attribute defaultValue CDATA #IMPLIED>\n"
                + "    <!ELEMENT record EMPTY >\n"
                + "    <!ATTLIST record name NMTOKEN #REQUIRED >\n"
                + "    <!ATTLIST record mass CDATA #IMPLIED >\n"
                + "    <!ATTLIST record charge CDATA #IMPLIED >\n"
                + "    <!ATTLIST record species CDATA #IMPLIED >\n"
                + "    <!ATTLIST record KE CDATA #IMPLIED >\n"
                + "]>\n");

        // find the first element for each sequence
        // loop over the first element collection for beam_parameters
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

        // write to file
        BufferedWriter writer = null;
        try {
            File file = new File("frib.impl");
            writer = new BufferedWriter(new FileWriter(file));
//            writer.write(sb.toString());
        } catch (IOException ex) {
            Logger.getLogger(Db2Xal.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                writer.close();
            } catch (IOException ex) {
                Logger.getLogger(Db2Xal.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void write2XDXF() {
        StringBuilder sb = new StringBuilder("<?xml version=\"1.0\" encoding=\"utf-8\"?>\n"
                + "<!DOCTYPE xdxf SYSTEM \"xdxf.dtd\">\n");
        sb.append("<xdxf date=\"");
        Date date = new Date();
        sb.append(date.toString());
        sb.append("\" system=\"");
        //TODO figure out the accelerator system name from DB
        sb.append(accName);
        sb.append("\" ver=\"1.0.0\">\n");
        //TODO set up combo sequences
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

            List<Element> eList = BeamlineSequenceAPI.getAllElementsForSequence(bls.getSequenceName());
            Iterator<Element> eIt = eList.iterator();
            while (eIt.hasNext()) {

                Element e = eIt.next();
                if (e.getElementTypeId() != null) {
                    // everything other than RF cavities treated as node
                    if (!e.getElementTypeId().getElementType().equals("CAV")) {
                        sb.append("      <node id=\"");
                    } // RF cavities treated as sequence
                    else {
                        sb.append("      <sequence id=\"");
                    }
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
                    sb.append("         <attributes>\n");

                    // set alignment data from element
                    sb.append("            <align ");
                    sb.append("yaw=\"");
                    sb.append(e.getYaw());
                    sb.append("\" roll=\"");
                    sb.append(e.getRoll());
                    sb.append("\" pitch=\"");
                    sb.append(e.getPitch());
                    sb.append("\" z=\"");
                    sb.append(e.getDz());
                    sb.append("\" y=\"");
                    sb.append(e.getDy());
                    sb.append("\" x=\"");
                    sb.append(e.getDx());
                    sb.append("\"/>\n");

                    if (!epList.isEmpty()) {
                        // insert node attributes

                        // set apertures
                        Map aperAttMap = ElementPropAPI.getApertureAttributesForElement(e.getElementName());
                        if (!aperAttMap.isEmpty()) {
                            sb.append("            <aperture ");

                            Set keySet1 = aperAttMap.keySet();
                            Iterator<String> keyIt1 = keySet1.iterator();
                            while (keyIt1.hasNext()) {
                                String key1 = keyIt1.next();
                                sb.append(key1);
                                sb.append("=\"");
                                sb.append(aperAttMap.get(key1));
                                sb.append("\" ");
                            }

                            sb.append("/>\n");
                        }

                        // set magnet attributes
                        Map magAttMap = ElementPropAPI.getMagnetAttributesForElement(e.getElementName());
                        if (!magAttMap.isEmpty()) {

                            sb.append("            <magnet ");

                            Set keySet2 = magAttMap.keySet();
                            Iterator<String> keyIt2 = keySet2.iterator();
                            while (keyIt2.hasNext()) {
                                String key2 = keyIt2.next();
                                sb.append(key2);
                                sb.append("=\"");
                                sb.append(magAttMap.get(key2));
                                sb.append("\" ");
                            }

                            sb.append("/>\n");
                        }

                        // set bpm attributes 
                        Map bpmAttMap = ElementPropAPI.getBpmAttributesForElement(e.getElementName());
                        if (!bpmAttMap.isEmpty()) {

                            sb.append("            <bpm ");

                            Set keySet3 = bpmAttMap.keySet();
                            Iterator<String> keyIt3 = keySet3.iterator();
                            while (keyIt3.hasNext()) {
                                String key3 = keyIt3.next();
                                sb.append(key3);
                                sb.append("=\"");
                                sb.append(bpmAttMap.get(key3));
                                sb.append("\" ");
                            }

                            sb.append("/>\n");
                        }

//                        // set rfcavity attributes
//                        Map rfAttMap = ElementPropAPI.getRfcavityAttributesForElement(e.getElementName());
//                        if (!rfAttMap.isEmpty()) {
//
//                            sb.append("            <rfgap ");
//
//                            Set keySet4 = rfAttMap.keySet();
//                            Iterator<String> keyIt4 = keySet4.iterator();
//                            while (keyIt4.hasNext()) {
//                                String key4 = keyIt4.next();
//                                sb.append(key4);
//                                sb.append("=\"");
//                                sb.append(rfAttMap.get(key4));
//                                sb.append("\" ");
//                            }
//
//                            sb.append("/>\n");
//                        }
                    }
                    sb.append("         </attributes>\n");

                    // if the node is not a marker, add EPICS channels 
                    if (!e.getElementTypeId().getElementType().equals("MARK")) {
                        sb.append("         <channelsuite>\n");
                        // for magnets
                        if (!ElementPropAPI.getMagnetAttributesForElement(e.getElementName()).isEmpty()) {
                            sb.append("            <channel handle=\"fieldRB\" signal=\"");
                            sb.append(e.getElementName());
                            sb.append(":B\" settable=\"false\"/>\n");
                            sb.append("            <channel handle=\"fieldSet\" signal=\"");
                            sb.append(e.getElementName());
                            sb.append(":B_Set\" settable=\"true\"/>\n");
                        }
                        // for BPMs
                        if (!ElementPropAPI.getBpmAttributesForElement(e.getElementName()).isEmpty()) {
                            sb.append("            <channel handle=\"xAvg\" signal=\"");
                            sb.append(e.getElementName());
                            sb.append(":xAvg\" settable=\"false\"/>\n");
                            sb.append("            <channel handle=\"yAvg\" signal=\"");
                            sb.append(e.getElementName());
                            sb.append(":yAvg\" settable=\"false\"/>\n");
                        }

                        sb.append("         </channelsuite>\n");

                        // now we need to check if this is an RF cavity and fill in all the RF gaps within this cavity
                        if (e.getElementTypeId().getElementType().equals("CAV")) {
                            List<RfGap> gaps = RfGapAPI.getAllRfgapsForCavity(e.getElementName());
                            Iterator<RfGap> gapIt = gaps.iterator();
                            while (gapIt.hasNext()) {
                                RfGap rg = gapIt.next();
                                sb.append("         <node type=\"RG\" id=\"");
                                sb.append(rg.getGapName());
                                sb.append("\" pos=\"");
                                sb.append(rg.getPos());
                                sb.append("\">\n");

                                sb.append("           <attributes>\n");
                                sb.append("             <rfgap length=\"");
                                sb.append(rg.getLen());
                                sb.append("\" phaseFactor=\"");
                                sb.append(rg.getPhaseFactor());
                                sb.append("\" ampFactor=\"");
                                sb.append(rg.getAmpFactor());
                                sb.append("\" TTF=\"");
                                sb.append(rg.getTtf());
                                sb.append("\" endCell=\"");
                                sb.append(rg.getEndCellind());
                                sb.append("\" gapOffset=\"");
                                sb.append(rg.getGapOffset());
                                sb.append("\"/>\n");

                                sb.append("           </attributes>\n");

                                sb.append("         </node>\n");
                            }
                        }
                    }

                    // close the <node>
                    if (!e.getElementTypeId().getElementType().equals("CAV")) {
                        sb.append("      </node>\n");
                    } // close the RF cavity <sequence>
                    else {
                        sb.append("      </sequence>\n");
                    }
                }
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

        // write to file
        BufferedWriter writer = null;
        try {
            File file = new File("frib.xdxf");
            writer = new BufferedWriter(new FileWriter(file));
            writer.write(sb.toString());
        } catch (IOException ex) {
            Logger.getLogger(Db2Xal.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                writer.close();
            } catch (IOException ex) {
                Logger.getLogger(Db2Xal.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Db2Xal x = new Db2Xal();

        // TODO get the accelerator name to override the default one (accName)

//        x.write2IMPL();
//        x.write2ModelParam();
        x.write2XDXF();
    }
}
