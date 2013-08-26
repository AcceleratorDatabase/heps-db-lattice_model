/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.msu.frib.xal.db2xal;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
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
import javax.persistence.Query;
import org.openepics.model.api.BeamParameterAPI;
import org.openepics.model.api.BeamlineSequenceAPI;
import org.openepics.model.api.BlsequenceLatticeAPI;
import org.openepics.model.api.ElementAPI;
import org.openepics.model.api.ElementPropAPI;
import org.openepics.model.api.GoldModelAPI;
import org.openepics.model.api.ModelAPI;
import org.openepics.model.api.ModelDB;
import org.openepics.model.api.ParticleTypeAPI;
import org.openepics.model.api.RfGapAPI;
import org.openepics.model.entity.BeamParameter;
import org.openepics.model.entity.BeamParameterProp;
import org.openepics.model.entity.BeamlineSequence;
import org.openepics.model.entity.Element;
import org.openepics.model.entity.ElementProp;
import org.openepics.model.entity.ElementTypeProp;
import org.openepics.model.entity.Model;
import org.openepics.model.entity.ParticleType;
import org.openepics.model.entity.RfGap;

/**
 *
 * @author chu
 */
public class Db2Xal {

    @PersistenceUnit
    static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("modelAPIPU");
    static final EntityManager em = emf.createEntityManager();
    // define the accelerator name
    //String accName = "csns";

    @PersistenceContext
    public void write2ModelParam() {
        // get all model initial conditions
        ModelAPI mapi = new ModelAPI();
        List<Model> mList = mapi.getAllModelInitialConditions();
        List<BeamParameterProp> bppList = null;

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

        sb.append("<tablegroup>\n");

        sb.append("  <table name=\"species\">\n");
        sb.append("     <schema>\n");
        sb.append("         <attribute isPrimaryKey=\"true\" name=\"name\" type=\"java.lang.String\"/>\n");
        sb.append("         <attribute isPrimaryKey=\"false\" name=\"mass\" type=\"java.lang.Double\"/>\n");
        sb.append("         <attribute isPrimaryKey=\"false\" name=\"charge\" type=\"java.lang.Double\"/>\n");
        sb.append("     </schema>\n");

        // get all particle types
        ParticleTypeAPI pta = new ParticleTypeAPI();
        List<ParticleType> pList = pta.getAllParticleTypes();
        for (int i = 0; i < pList.size(); i++) {
            sb.append("     <record name=\"");
            sb.append(pList.get(i).getParticleName());
            sb.append("\" mass=\"");
            sb.append(pList.get(i).getParticleMass());
            sb.append("\" charge=\"");
            sb.append(pList.get(i).getParticleCharge());
            sb.append("\"/>\n");
        }

        sb.append("  </table>\n");

        // for "beam" table
        sb.append("  <table name=\"beam\">\n");
        sb.append("     <schema>\n");
        sb.append("         <attribute isPrimaryKey=\"true\" name=\"name\" type=\"java.lang.String\"/>\n");
        sb.append("         <attribute isPrimaryKey=\"false\" name=\"I\" type=\"java.lang.Double\"/>\n");
        sb.append("         <attribute isPrimaryKey=\"false\" name=\"Q\" type=\"java.lang.Double\"/>\n");
        sb.append("     </schema>\n");
        //I:current Q:charge
        sb.append("     <record name=\"default\" I=\"0.01483760\" Q=\"8.07168E-15\"/>\n");
        for (int i = 0; i < mList.size(); i++) {
            // get beam parameters for this model
            BeamParameterAPI bpa = new BeamParameterAPI();
            List<BeamParameter> bpaList = bpa.getAllBeamParametersForModel(mList.get(i));
            // get beam properties
            Query q;
            if (bpaList.size() > 0) {

                q = em.createQuery("SELECT bpp FROM BeamParameterProp bpp JOIN bpp.beamParameterId pid WHERE pid.modelId=:modelId")
                        .setParameter("modelId", mList.get(i));
                bppList = q.getResultList();

                if (!bppList.isEmpty()) {
                    sb.append("     <record name=\"");
                    //sb.append(bpList.get(i).getModelId().getModelName());
                    sb.append(mList.get(i).getModelName());
                    sb.append("\" ");
                    for (int j = 0; j < bppList.size(); j++) {
                        if (bppList.get(j).getPropCategory().equals("beam") && bppList.get(j).getPropertyName().equals("current")) {
                            sb.append("I=\"" + bppList.get(j).getBeamParameterDouble());
                            sb.append("\" ");

                        }
                        if (bppList.get(j).getPropCategory().equals("beam") && bppList.get(j).getPropertyName().equals("charge")) {
                            sb.append("Q=\"" + bppList.get(j).getBeamParameterDouble());
                            sb.append("\"");

                        }
                    }
                    sb.append("/>\n");
                }
            }
        }

        sb.append("  </table>\n");

        // for "adaptivetracker" table
        sb.append("  <table name=\"adaptivetracker\">\n");
        sb.append("     <schema>\n");
        sb.append("         <attribute isPrimaryKey=\"true\" name=\"name\" type=\"java.lang.String\"/>\n");
        sb.append("         <attribute isPrimaryKey=\"false\" name=\"errortol\" type=\"java.lang.Double\" defaultValue=\"1.0E-5\"/>\n");
        sb.append("         <attribute isPrimaryKey=\"false\" name=\"initstep\" type=\"java.lang.Double\" defaultValue=\"0.01\"/>\n");
        sb.append("         <attribute isPrimaryKey=\"false\" name=\"maxstep\" type=\"java.lang.Double\" defaultValue=\"0.0\"/>\n");
        sb.append("         <attribute isPrimaryKey=\"false\" name=\"norm\" type=\"java.lang.Integer\" defaultValue=\"0\"/>\n");
        sb.append("         <attribute isPrimaryKey=\"false\" name=\"order\" type=\"java.lang.Integer\" defaultValue=\"2\"/>\n");
        sb.append("         <attribute isPrimaryKey=\"false\" name=\"slack\" type=\"java.lang.Double\" defaultValue=\"0.05\"/>\n");
        sb.append("         <attribute isPrimaryKey=\"false\" name=\"maxiter\" type=\"java.lang.Integer\" defaultValue=\"50\"/>\n");
        sb.append("	</schema>\n");
        sb.append("	<record name=\"default\"/>\n");
        for (int i = 0; i < mList.size(); i++) {
            sb.append("     <record name=\"");
            sb.append(mList.get(i).getModelName());
            sb.append("\" errortol=\"0.1\" initstep=\"0.1\" maxiter=\"1000\"/>\n");
        }
        sb.append("  </table>\n");

        // for "twiss" table
        sb.append("  <table name=\"twiss\">\n");
        sb.append("     <schema>\n");
        sb.append("         <attribute isPrimaryKey=\"true\" name=\"name\" type=\"java.lang.String\"/>\n");
        sb.append("         <attribute isPrimaryKey=\"true\" name=\"coordinate\" type=\"java.lang.String\"/>\n");
        sb.append("         <attribute isPrimaryKey=\"false\" name=\"alpha\" type=\"java.lang.Double\"/>\n");
        sb.append("         <attribute isPrimaryKey=\"false\" name=\"beta\" type=\"java.lang.Double\"/>\n");
        sb.append("         <attribute isPrimaryKey=\"false\" name=\"emittance\" type=\"java.lang.Double\"/>\n");
        sb.append("	</schema>\n");
        // TODO
        for (int i = 0; i < mList.size(); i++) {
            // get twiss properties
            Query q;

            q = em.createQuery("SELECT bpp FROM BeamParameterProp bpp JOIN bpp.beamParameterId pid WHERE pid.modelId=:modelId")
                    .setParameter("modelId", mList.get(i));
            bppList = q.getResultList();
            // TODO: need to fix problem here 
            if (!bppList.isEmpty()) {
                sb.append("     <record name=\"");
                sb.append(mList.get(i).getModelName());
                sb.append("\" coordinate=\"x");
                // loop over all beam parameter properties
                for (int j = 0; j < bppList.size(); j++) {
                    switch (bppList.get(j).getPropertyName()) {
                        case "x_alpha":
                            sb.append("\" alpha=\"");
                            sb.append(bppList.get(j).getBeamParameterDouble());
                            break;
                        case "x_beta":
                            sb.append("\" beta=\"");
                            sb.append(bppList.get(j).getBeamParameterDouble());
                            break;
                        case "x_emittance":
                            sb.append("\" emittance=\"");
                            sb.append(bppList.get(j).getBeamParameterDouble());
                            break;
                    }
                }
                sb.append("\"/>\n");

                // for y coordinate
                sb.append("     <record name=\"");
                sb.append(mList.get(i).getModelName());
                sb.append("\" coordinate=\"y");
                // loop over all beam parameter properties
                for (int j = 0; j < bppList.size(); j++) {
                    switch (bppList.get(j).getPropertyName()) {
                        case "y_alpha":
                            sb.append("\" alpha=\"");
                            sb.append(bppList.get(j).getBeamParameterDouble());
                            break;
                        case "y_beta":
                            sb.append("\" beta=\"");
                            sb.append(bppList.get(j).getBeamParameterDouble());
                            break;
                        case "y_emittance":
                            sb.append("\" emittance=\"");
                            sb.append(bppList.get(j).getBeamParameterDouble());
                            break;
                    }
                }
                sb.append("\"/>\n");

                // for x coordinate
                sb.append("     <record name=\"");
                sb.append(mList.get(i).getModelName());
                sb.append("\" coordinate=\"z");
                // loop over all beam parameter properties
                for (int j = 0; j < bppList.size(); j++) {
                    switch (bppList.get(j).getPropertyName()) {
                        case "z_alpha":
                            sb.append("\" alpha=\"");
                            sb.append(bppList.get(j).getBeamParameterDouble());
                            break;
                        case "z_beta":
                            sb.append("\" beta=\"");
                            sb.append(bppList.get(j).getBeamParameterDouble());
                            break;
                        case "z_emittance":
                            sb.append("\" emittance=\"");
                            sb.append(bppList.get(j).getBeamParameterDouble());
                            break;
                    }
                }
                sb.append("\"/>\n");
            }
        }
        sb.append("  </table>\n");

        // for "location" table
        sb.append("  <table name=\"location\">\n");
        sb.append("     <schema>\n");
        sb.append("         <attribute isPrimaryKey=\"true\" name=\"name\" type=\"java.lang.String\"/>\n");
        sb.append("         <attribute isPrimaryKey=\"false\" name=\"species\" type=\"java.lang.String\" defaultValue=\"PROTON\"/>\n");
        sb.append("         <attribute isPrimaryKey=\"false\" name=\"W\" type=\"java.lang.Double\"/>\n");
        sb.append("         <attribute isPrimaryKey=\"false\" name=\"start_elem\" type=\"java.lang.String\"/>\n");
        sb.append("         <attribute isPrimaryKey=\"false\" name=\"s\" type=\"java.lang.Double\" defaultValue=\"0\"/>\n");
        sb.append("         <attribute isPrimaryKey=\"false\" name=\"t\" type=\"java.lang.Double\" defaultValue=\"0\"/>\n");
        sb.append("	</schema>\n");
        // TODO
        for (int i = 0; i < mList.size(); i++) {
            // get location properties
            Query q;
            q = em.createQuery("SELECT bpp FROM BeamParameterProp bpp JOIN bpp.beamParameterId pid WHERE pid.modelId=:modelId")
                    .setParameter("modelId", mList.get(i));
            bppList = q.getResultList();
            if (!bppList.isEmpty()) {
                sb.append("     <record name=\"");
                sb.append(mList.get(i).getModelName());
                q = em.createQuery("SELECT pt FROM ParticleType pt JOIN pt.beamParameterCollection bp WHERE bp.modelId=:modelId")
                        .setParameter("modelId", mList.get(i));
                List<ParticleType> ptList = q.getResultList();
                sb.append("\" species=\"");
                if (ptList.size() > 0) {
                    sb.append(ptList.get(0).getParticleName());
                }

                // get the energy from beam parameter properties
                for (int j = 0; j < bppList.size(); j++) {
                    switch (bppList.get(j).getPropertyName()) {
                        case "W":
                            sb.append("\" W=\"");
                            sb.append(bppList.get(j).getBeamParameterDouble());
                            break;
                    }
                }
                sb.append("/>\n");
            }
        }

        sb.append("  </table>\n");

        // close the tablegroup
        sb.append("</tablegroup>");

        System.out.println(sb);

        // write to file
        BufferedWriter writer = null;
        try {
            File file = new File("model.params");
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
     * write to XAL .impl file
     */
    public void write2IMPL(String accName) {
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
            // File file = new File("frib.impl");
            File file = new File(accName + ".impl");
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

    public void write2XDXF(String accName, String latticeName) {
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

        // for power supplies
        ArrayList<String> psList = new ArrayList<String>();

        // get all sequences
//        BeamlineSequenceAPI beamlineSequenceAPI = new BeamlineSequenceAPI();
//        List<BeamlineSequence> blsList = beamlineSequenceAPI.getAllSequences();
//        Iterator<BeamlineSequence> blsIt = blsList.iterator();

        BeamlineSequenceAPI beamlineSequenceAPI = new BeamlineSequenceAPI();
        // List<BeamlineSequence> blsList = beamlineSequenceAPI.getAllSequences();
        BlsequenceLatticeAPI blsequenceLatticeAPI =new BlsequenceLatticeAPI();
        List<BeamlineSequence> blsList=blsequenceLatticeAPI.getSequencesForLattice(latticeName);
        Iterator<BeamlineSequence> blsIt = blsList.iterator();

        
        // loop through each sequence
        while (blsIt.hasNext()) {
            BeamlineSequence bls = blsIt.next();
            sb.append("   <sequence id=\"");
            sb.append(bls.getSequenceName());
            sb.append("\" len=\"");
            sb.append(bls.getSequenceLength());
            sb.append("\"");
            // handle DTLs and CCLs
            if (bls.getSequenceName().contains("DTL"))
                sb.append(" type=\"DTLTank\"");
            if (bls.getSequenceName().contains("CCL"))
                sb.append(" type=\"CCLTank\"");  
       
            sb.append(">\n");
            sb.append("      <attributes>\n");
            sb.append("         <sequence predecessors=\"");
            sb.append(bls.getPredecessorSequence());
            sb.append("\"/>\n");
            
            // handle DTLs and CCLs
            if (bls.getSequenceName().contains("DTL") || bls.getSequenceName().contains("CCL")) {
                ElementAPI ea = new ElementAPI();
               // Element tank = ea.getElementByName(bls.getSequenceName());
                Element tank=ea.getElementByPid(bls.getSequenceName());             

                ElementPropAPI elementPropAPI = new ElementPropAPI();
                Map rfAttMap = elementPropAPI.getRfcavityAttributesForElement(tank.getElementName());
                if (!rfAttMap.isEmpty()) {

                    sb.append("         <rfcavity ");

                    Set keySet4 = rfAttMap.keySet();
                    Iterator<String> keyIt4 = keySet4.iterator();
                    while (keyIt4.hasNext()) {
                        String key4 = keyIt4.next();
                        sb.append(key4);
                        sb.append("=\"");
                        sb.append(rfAttMap.get(key4));
                        sb.append("\" ");
                    }
                    sb.append("/>\n");
                }
            }
            sb.append("      </attributes>\n");
                
            if (bls.getSequenceName().contains("DTL") || bls.getSequenceName().contains("CCL")) {                
                ElementAPI ea = new ElementAPI();
                //Element tank = ea.getElementByName(bls.getSequenceName());
                 Element tank=ea.getElementByPid(bls.getSequenceName());             

                sb.append("      <channelsuite name=\"rfsuite\">\n");
                sb.append("         <channel handle=\"cavAmpSet\" signal=\"");
                sb.append(tank.getElementName());
                sb.append(":ampSet\" settable=\"true\"/>\n");
                sb.append("         <channel handle=\"cavPhaseSet\" signal=\"");
                sb.append(tank.getElementName());
                sb.append(":phaseSet\" settable=\"true\"/>\n");
                sb.append("         <channel handle=\"cavAmpAvg\" signal=\"");
                sb.append(tank.getElementName());
                sb.append(":amp\" settable=\"false\"/>\n");
                sb.append("         <channel handle=\"cavPhaseAvg\" signal=\"");
                sb.append(tank.getElementName());
                sb.append(":phase\" settable=\"false\"/>\n");
                sb.append("      </channelsuite>\n");
            }

            // loop through each node
            // need to treat RF specially
            List<Element> eList = beamlineSequenceAPI.getAllElementsForSequence(bls.getSequenceName());
            Iterator<Element> eIt = eList.iterator();
            while (eIt.hasNext()) {

                Element e = eIt.next();
                if (e.getElementTypeId() != null) {
                    // get all properties for this element
                    ElementAPI elementAPI = new ElementAPI();
                    List<ElementProp> epList = elementAPI.getAllPropertiesForElement(e.getElementName());
                    // everything other than RF cavities treated as node
                    if (!e.getElementTypeId().getElementType().equals("CAV") && 
                            !e.getElementTypeId().getElementType().equals("Bnch") ) {
                        sb.append("      <node id=\"");
                    } // RF cavities treated as sequence
                    else {
                        sb.append("      <sequence id=\"");
                    }
                    sb.append(e.getElementName());
                    // get physics name, if there is any
                    ElementPropAPI elementPropAPI = new ElementPropAPI();
                    try {
                        if (!elementPropAPI.getPidForElement(e.getElementName()).equals("")
                                && !elementPropAPI.getPidForElement(e.getElementName()).isEmpty()) {
                            sb.append("\" pid=\"");
                            sb.append(elementPropAPI.getPidForElement(e.getElementName()));
                        }
                    } catch (NullPointerException ne) {
                        // do nothing if there is no pid
                    }

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
                        Map aperAttMap = elementPropAPI.getApertureAttributesForElement(e.getElementName());
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
                        Map magAttMap = elementPropAPI.getMagnetAttributesForElement(e.getElementName());
                        if (!magAttMap.isEmpty()) {
                            if (!e.getElementTypeId().getElementType().equals("CAV") &&
                                   !e.getElementTypeId().getElementType().equals("Bnch") ) {
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
                        }

                        // set bpm attributes 
                        Map bpmAttMap = elementPropAPI.getBpmAttributesForElement(e.getElementName());
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

                        // set rfcavity attributes
                        Map rfAttMap = elementPropAPI.getRfcavityAttributesForElement(e.getElementName());
                        if (!rfAttMap.isEmpty()) {

                            sb.append("            <rfcavity ");

                            Set keySet4 = rfAttMap.keySet();
                            Iterator<String> keyIt4 = keySet4.iterator();
                            while (keyIt4.hasNext()) {
                                String key4 = keyIt4.next();
                                sb.append(key4);
                                sb.append("=\"");
                                sb.append(rfAttMap.get(key4));
                                sb.append("\" ");
                            }
                            
                            if (!magAttMap.isEmpty()) {
                                    sb.append("len");
                                    sb.append("=\"");
                                    sb.append(magAttMap.get(""));
                                    sb.append("\" ");                               
                            }

                            sb.append("/>\n");
                        }
                    }
                    sb.append("         </attributes>\n");
                    
                    // if the node is not a marker, add EPICS channels 
                    if (!e.getElementTypeId().getElementType().equals("MARK")) {
                        
                        // for magnets
                        //if (!elementPropAPI.getMagnetAttributesForElement(e.getElementName()).isEmpty() &&
                        //       !e.getElementTypeId().getElementType().equals("CAV") ) 
                        if ((!elementPropAPI.getMagnetAttributesForElement(e.getElementName()).isEmpty() ||
                                e.getElementTypeId().getElementType().equals("DCH") ||
                                e.getElementTypeId().getElementType().equals("DCV")) &&
                                !e.getElementTypeId().getElementType().equals("CAV")) 
                        {
                            String psName = e.getElementName().replaceAll(":", ":PS_");
                            psList.add(psName);
                            sb.append("         <ps main=\"");
                            sb.append(psName);        
                            sb.append("\"/>\n");
                            sb.append("         <channelsuite name=\"magnetsuite\">\n");
                            sb.append("            <channel handle=\"fieldRB\" signal=\"");
                            sb.append(e.getElementName());
                            sb.append(":B\" settable=\"false\"/>\n");
                            //sb.append("            <channel handle=\"fieldSet\" signal=\"");
                            //sb.append(e.getElementName());
                            //sb.append(":B_Set\" settable=\"true\"/>\n");
                            sb.append("         </channelsuite>\n");
                        }
                        // for BPMs
                        if (e.getElementTypeId().getElementType().equals("BPM")) {
                            sb.append("         <channelsuite name=\"bpmsuite\">\n");
                            sb.append("            <channel handle=\"xAvg\" signal=\"");
                            sb.append(e.getElementName());
                            sb.append(":xAvg\" settable=\"false\"/>\n");
                            sb.append("            <channel handle=\"yAvg\" signal=\"");
                            sb.append(e.getElementName());
                            sb.append(":yAvg\" settable=\"false\"/>\n");
                            sb.append("         </channelsuite>\n");
                        }
                        // for RF cavities
                        if (e.getElementTypeId().getElementType().equals("CAV") || 
                                e.getElementTypeId().getElementType().equals("Bnch")) {
                            sb.append("         <channelsuite name=\"rfsuite\">\n");
                            sb.append("            <channel handle=\"cavAmpSet\" signal=\"");
                            sb.append(e.getElementName());
                            sb.append(":ampSet\" settable=\"true\"/>\n");
                            sb.append("            <channel handle=\"cavPhaseSet\" signal=\"");
                            sb.append(e.getElementName());
                            sb.append(":phaseSet\" settable=\"true\"/>\n");
                            sb.append("            <channel handle=\"cavAmpAvg\" signal=\"");
                            sb.append(e.getElementName());
                            sb.append(":amp\" settable=\"false\"/>\n");
                            sb.append("            <channel handle=\"cavPhaseAvg\" signal=\"");
                            sb.append(e.getElementName());
                            sb.append(":phase\" settable=\"false\"/>\n");
                            sb.append("         </channelsuite>\n");
                        }

                        // now we need to check if this is an RF cavity and fill in all the RF gaps within this cavity
                        if (e.getElementTypeId().getElementType().equals("CAV")) {
                            RfGapAPI rfGapAPI = new RfGapAPI();
                            List<RfGap> gaps = rfGapAPI.getAllRfgapsForCavity(e.getElementName());
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
                  //  if (!e.getElementTypeId().getElementType().equals("CAV")) {
                    if(!e.getElementTypeId().getElementType().equals("CAV") && 
                            !e.getElementTypeId().getElementType().equals("Bnch")){
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
        for (int i = 0; i<psList.size(); i++) {
            sb.append("     <ps type=\"main\" id=\"");
            sb.append(psList.get(i));
            sb.append("\">\n");
            sb.append("       <channelsuite name=\"pssuite\">\n");
            sb.append("         <channel handle=\"fieldSet\" signal=\"");
            sb.append(psList.get(i));
            sb.append(":B_Set\"/>\n");
            sb.append("         <channel handle=\"B_Book\" signal=\"");
            sb.append(psList.get(i));
            sb.append(":B_Book\"/>\n");
            sb.append("       </channelsuite>\n");
            sb.append("     </ps>\n");
        }
        sb.append("   </powersupplies>\n");
        // close
        sb.append("</xdxf>");
        System.out.println(sb);

        // write to file
        BufferedWriter writer = null;
        try {
           // File file = new File("frib.xdxf");
            File file = new File(accName + ".xdxf");
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

       // x.write2IMPL();
        x.write2ModelParam();
        x.write2XDXF("csns", "Linac_lattice_model_template_2013");
    }
}
