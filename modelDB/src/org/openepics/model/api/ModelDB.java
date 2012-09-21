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
        List seqs = BeamlineSequenceAPI.getAllSequences();
        System.out.println("There are " + seqs.size() + " sequences.");
        
//        ModelCodeAPI.setModelCode("XAL", "EnvelopeTracker");
        
//        List<ModelCode> mcList = x.getAllModelCodes();
//        System.out.println("There are " + mcList.size() + " mode codes.");

//        ElementAPI.setElementType("SH", "horizontal sextupole");
          
//        MachineModeAPI.setMachineMode("USER", "user defined model");
                  
//        List<ModelCode> mcList = x.getAllModelCodes();
//        System.out.println("There are " + mcList.size() + " mode codes.");
        
//        System.out.println("1st XAL algorithm: " + x.getAlgorithmsForModelCode("XAL").get(0).getAlgorithm());
        
//        ModelAPI.setModelHeader("LINAC", "XAL", "DESIGN", "Test", "A database upload test");
        
        List<Model> ml = ModelAPI.getAllModelsForModelLineAndMachineMode("LINAC", "DESIGN");
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
