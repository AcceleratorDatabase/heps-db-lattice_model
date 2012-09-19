/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.openepics.model.api;

import java.util.Date;
import java.util.Iterator;
import java.util.List;
import javax.persistence.*;
import org.openepics.model.entity.BeamlineSequence;
import org.openepics.model.entity.Element;
import org.openepics.model.entity.ElementType;
import org.openepics.model.entity.ElementTypeProp;
import org.openepics.model.entity.GoldLattice;
import org.openepics.model.entity.Lattice;
import org.openepics.model.entity.MachineMode;
import org.openepics.model.entity.Model;
import org.openepics.model.entity.ModelCode;
import org.openepics.model.entity.ModelGeometry;
import org.openepics.model.entity.ModelLine;

/**
 *
 * @author chu
 * @version
 */
public class ModelDB {
    @PersistenceUnit
    static EntityManagerFactory emf = Persistence.createEntityManagerFactory("modelAPIPU");
    EntityManager em = emf.createEntityManager();

    @PersistenceContext
    
    /**
     * Get all accelerator sequences.
     * @return all sequences
     */
    public List<BeamlineSequence> getAllSequences() {
        Query q;
        q = em.createNamedQuery("BeamlineSequence.findAll");
        List<BeamlineSequence> seqList = q.getResultList();

        return seqList;        
    }
    
    /**
     * Set a new beamline sequence
     * @param seq_name sequence name
     * @param first_elem_name first element name
     * @param last_elem_name last element name
     * @param previous_seq previous sequence name
     * @param seq_length sequence length
     * @param seq_desc description for this sequence
     */
    public void setBeamlineSequence(String seq_name, String first_elem_name, 
            String last_elem_name, String previous_seq, double seq_length, String seq_desc) {
        BeamlineSequence bs = new BeamlineSequence();
        bs.setSequenceName(seq_name);
        bs.setFirstElementName(first_elem_name);
        bs.setLastElementName(last_elem_name);
        bs.setPredecessorSequence(previous_seq);
        bs.setSequenceLength(seq_length);
        bs.setSequenceDescription(seq_desc);
        em.getTransaction().begin();
        em.persist(bs);
        em.getTransaction().commit();
    }
    
    /**
     * Get all modeling codes
     * @return all modeling codes
     */
    public List<ModelCode> getAllModelCodes() {
        Query q;
        q = em.createNamedQuery("ModelCode.findAll");
        List<ModelCode> codeList = q.getResultList();
        
        return codeList;
    }
    
    public List<ModelCode> getAlgorithmsForModelCode(String code_name) {
        Query q;
        q = em.createNamedQuery("ModelCode.findByCodeName").setParameter("codeName", code_name);
        return q.getResultList();
    }
    
    /**
     * 
     * @param mode
     * @param line
     * @return 
     */
    public GoldLattice getGoldLatticeForMachineModeAndModelLine(String mode, String line) {
        Query q;
        q = em.createQuery("SELECT g FROM Gold g WHERE "
                + "g.modelLineId.modelLineName = :lineName "
                + "AND g.machineModeId.machineModeName = :modeName "
                + "AND g.goldStatusInd = :gind").setParameter("lineName", line)
                .setParameter("modeName", mode).setParameter("gind", GoldLattice.PRESENT); 
        List<GoldLattice> gmList = q.getResultList();
        if (gmList.isEmpty()) {
            return null;
        }
        else { 
            return gmList.get(0);   
        }
    }
        
    /**
     * Get all models for the specified machine mode
     * 
     * @param mode machine mode name
     * @return models for the specified machine mode
     */
    public List<Model> getAllModelsForMachineMode(String mode) {
        Query q;
        q = em.createQuery("SELECT m FROM Model m JOIN m.latticeId l "
                + "JOIN l.machineModeId mm WHERE mm.machineModeName = :modeName").setParameter("modeName", mode);
        List<Model> mList = q.getResultList();
        
        return mList;               
    }
    
    /**
     * Get all models for the specified model line
     * 
     * @param line model line name
     * @return models for the specified model line
     */
    public List<Model> getAllModelsForModelLine(String line) {
        Query q;
        q = em.createQuery("SELECT m FROM Model m JOIN m.latticeId l "
                + "JOIN l.modelLineId ml WHERE ml.modelLineName = :lineName").setParameter("lineName", line);
        List<Model> mList = q.getResultList();
        
        return mList;        
    }
    
    /**
     * get all models with specified model line and machine mode
     * 
     * @param line model line name
     * @param mode machine mode
     * @return modes with specified model line and machine mode
     */
    public List<Model> getAllModelsForModelLineAndMachineMode(String line, String mode) {
        Query q;
        q = em.createQuery("SELECT m FROM Model m JOIN m.latticeId l "
                + "WHERE "
                + "l.modelLineId.modelLineName = :lineName "
                + "AND l.machineModeId.machineModeName = :modeName").setParameter("lineName", line).setParameter("modeName", mode);
        
        List<Model> mList = q.getResultList();
        return mList;
    }
    
    /**
     * get all element type to modeling class mappings
     * 
     * @return element type to model class mappings
     */
    public List<ElementTypeProp> getAllElementClassMappings() {
        Query q;
        q = em.createNamedQuery("ElementTypeProp.findByElementTypePropName").setParameter("elementTypePropName", "XAL_class_mapping");
        List<ElementTypeProp> maps = q.getResultList();
        return maps;
    }
    
    /**
     * Set a new element type
     * @param elem_type element type
     * @param elem_type_desc description for this element type
     */
    public void setElementType(String elem_type, String elem_type_desc) {
        ElementType et = new ElementType();
        et.setElementType(elem_type);
        et.setElementTypeDescription(elem_type_desc);
        em.getTransaction().begin();
        em.persist(et);
        em.getTransaction().commit();
    }
    
    /**
     * Set a new machine mode in DB
     * @param machine_mode_name machine mode name
     * @param machine_mode_desc description for this machine mode
     */
    public void setMachineMode(String machine_mode_name, String machine_mode_desc) {
        MachineMode mm = new MachineMode();
        mm.setMachineModeName(machine_mode_name);
        mm.setMachineModeDescription(machine_mode_desc);
        em.getTransaction().begin();
        em.persist(mm);
        em.getTransaction().commit();
    }
    
    /**
     * Set a new model code in DB
     * @param codeName model code name, e.g. XAL
     * @param algorithm algorithm used, e.g. EnvelopeTracker
     */
    public void setModelCode(String codeName, String algorithm) {
        ModelCode mc = new ModelCode();
        mc.setCodeName(codeName);
        mc.setAlgorithm(algorithm);
        em.getTransaction().begin();
        em.persist(mc);
        em.getTransaction().commit();
    }
    
    /**
     * Set a new model geometry in DB
     * @param model_geometry_name model geometry name
     * @param model_geometry_desc description for this model geometry
     */
    public void setModelGeometry(String model_geometry_name, String model_geometry_desc) {
        ModelGeometry mg = new ModelGeometry();
        mg.setModelGeometryName(model_geometry_name);
        mg.setModelGeometryDescription(model_geometry_desc);
        em.getTransaction().begin();
        em.persist(mg);
        em.getTransaction().commit();
    }
    
    /**
     * Set a new model line in DB
     * @param model_line_name model line's name
     * @param model_line_desc description for this model line
     * @param start_pos starting position for this model line
     * @param end_pos ending position for this model line
     * @param start_marker starting marker  name
     * @param end_marker ending marker name
     */
    public void setModelLine(String model_line_name, String model_line_desc, 
            double start_pos, double end_pos, String start_marker, String end_marker) {
        ModelLine ml = new ModelLine();
        ml.setModelLineName(model_line_name);
        ml.setModelLineDescription(model_line_desc);
        ml.setStartPosition(start_pos);
        ml.setEndPosition(end_pos);
        ml.setStartMarker(start_marker);
        ml.setEndMarker(end_marker);
        em.getTransaction().begin();
        em.persist(ml);
        em.getTransaction().commit();
    }
    
    /**
     * Set a new model information
     * 
     * @param model_line model line name
     * @param model_code code used 
     * @param machine_mode machine mode for this model
     * @param model_name a name for this model
     * @param model_desc description for this model
     */
    public void setModelHeader(String model_line, String model_code, String machine_mode, 
            String model_name, String model_desc) {

        Model m = new Model();

        Query q;
        
        // check if such lattice exists
        q = em.createNativeQuery("SELECT l.Lattice_Id FROM Lattice l, Model_Line ml, Machine_Mode mm "
                + "WHERE l.model_Line_Id=ml.model_Line_Id AND l.machine_Mode_Id=mm.machine_Mode_Id "
                + "AND ml.model_Line_Name=\"" + model_line + "\" AND mm.machine_Mode_Name=\"" 
                + machine_mode + "\"");
        q.setParameter("modelLineName", model_line);
        q.setParameter("machineModeName", machine_mode);
        List<Integer> lattices = q.getResultList();
        // check if the specified lattice exists
        if (lattices.isEmpty()) {
            Lattice l = new Lattice();
            // check if the specified model_line exists
            q = em.createNamedQuery("ModelLine.findByModelLineName").setParameter("modelLineName", model_line);
            // if the model_line exists, use it; otherwise, create a new model_line
            List<ModelLine> mlList = q.getResultList();
            if (mlList.isEmpty()) {
                ModelLine ml = new ModelLine();
                ml.setModelLineName(model_line);
                em.persist(ml);
              
                l.setModelLineId(ml);
            } else {
                l.setModelLineId(mlList.get(0));
            }
            
            
            // check if the machine_mode exists
            q = em.createNamedQuery("MachineMode.findByMachineModeName").setParameter("machineModeName", machine_mode);
            // if the model_line exists, use it; otherwise, create a new model_line
            List<MachineMode> mmList = q.getResultList();
            if (mmList.isEmpty()) {
                MachineMode mm = new MachineMode();
                mm.setMachineModeName(machine_mode);
                em.persist(mm);
                l.setMachineModeId(mm);
            } else {
                l.setMachineModeId(mmList.get(0));
            } 
            em.persist(l);
            m.setLatticeId(l);
        } else {
            q = em.createNamedQuery("Lattice.findByLatticeId").setParameter("latticeId", lattices.get(0));
            m.setLatticeId((Lattice) q.getResultList().get(0));
        }
               
        // check if the specified model_code exists
        q = em.createNamedQuery("ModelCode.findByCodeName").setParameter("codeName", model_code);
        List<ModelCode> mcList = q.getResultList();
        if (mcList.size() > 0) {
            ModelCode mc = (ModelCode) q.getResultList().get(0);
            m.setModelCodeId(mc);
        }
        else {
            // create new model_code
            ModelCode mc = new ModelCode();
            mc.setCodeName(model_code);
            em.persist(mc);
            m.setModelCodeId(mc);
        }
        
        m.setModelName(model_name);
        m.setModelDesc(model_desc);
        m.setCreatedBy(System.getProperty("user.name"));
        m.setCreateDate(new Date());
        em.getTransaction().begin();
        em.persist(m);
        em.getTransaction().commit();
    }
    
    /**
     * Tag a Lattice as Gold
     * 
     * @param l the Lattice to be tagged as Gold
     */
    public void setGoldLattice(Lattice l) {
        GoldLattice gl = new GoldLattice();
        Date date = new Date();
        gl.setCreateDate(date);
        gl.setCreatedBy(System.getProperty("user.name"));
        gl.setGoldStatusInd(GoldLattice.PRESENT);
        gl.setLatticeId(l);
        em.getTransaction().begin();
        em.persist(gl);
        
        // move present Gold to previous Gold
        try {
            GoldLattice g_old = getGoldLatticeForMachineModeAndModelLine(
                    gl.getLatticeId().getMachineModeId().getMachineModeName(), 
                gl.getLatticeId().getModelLineId().getModelLineName());
        
            g_old.setGoldStatusInd(GoldLattice.PREVIOUS);
            g_old.setUpdateDate(date);
            g_old.setUpdatedBy(System.getProperty("user.name"));
            em.persist(g_old);
        } 
        // if there is no Gold for this line/mode, skip it
        catch (NullPointerException e) {
            // do nothing
        }
        
        em.getTransaction().commit();
    }
    
    public void setElement() {
        // TODO save an individual element's model data
        Element e = new Element();
        Date date = new Date();
        e.setInsertDate(date);
        e.setBeamParameterCollection(null);
        
    }
    
    
    public void saveModelData() {
        // TODO save model output data
    }
    
    public void saveModelInputData() {
        // TODO save model input data
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        ModelDB x = new ModelDB();
        List seqs = x.getAllSequences();
        System.out.println("There are " + seqs.size() + " sequences.");
        
//        x.setModelCode("XAL", "EnvelopeTracker");
        
//        List<ModelCode> mcList = x.getAllModelCodes();
//        System.out.println("There are " + mcList.size() + " mode codes.");

//        x.setElementType("SH", "horizontal sextupole");
          
//        x.setMachineMode("USER", "user defined model");
                  
//        List<ModelCode> mcList = x.getAllModelCodes();
//        System.out.println("There are " + mcList.size() + " mode codes.");
        
//        System.out.println("1st XAL algorithm: " + x.getAlgorithmsForModelCode("XAL").get(0).getAlgorithm());
        
//        x.setModelHeader("LINAC", "XAL", "DESIGN", "Test", "A database upload test");
        
        List<Model> ml = x.getAllModelsForModelLineAndMachineMode("LINAC", "DESIGN");
//        List<Model> ml = x.getAllModelsForModelLine("LINAC");
        if (!ml.isEmpty()) {
            Iterator<Model> it = ml.iterator();
            int i = 1;
            while (it.hasNext()) {
                Model m = it.next();
                System.out.println("Model " + i + ": " + m.getModelName() + " with mode = " + m.getLatticeId().getMachineModeId().getMachineModeName());
                i++;
            }
        }

    }
    
    
}
