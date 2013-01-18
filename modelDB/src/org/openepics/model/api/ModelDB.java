/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.openepics.model.api;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.*;
import org.openepics.model.entity.Element;
import org.openepics.model.entity.ElementTypeProp;
import org.openepics.model.entity.GoldModel;
import org.openepics.model.entity.Lattice;
import org.openepics.model.entity.Model;

/**
 *
 * @author chu
 * @version
 */
public class ModelDB {

    @PersistenceUnit
    static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("modelAPIPU");
//    private static Object latticeId;
    static final EntityManager em = emf.createEntityManager();

    @PersistenceContext
    
    public void setModelDB() {
        // do nothing but to satisfy deployment to Glassfish
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
     * upload model data to database
     */
    public void saveModelData() {
        // TODO save model output data
    }

    /**
     * upload model input data to database
     */
    public void saveModelInputData() {
        // TODO save model input data
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws NoSuchMethodException {


        /*    ModelDB x = new ModelDB();
         List seqs = BeamlineSequenceAPI.getAllSequences();
         System.out.println("There are " + seqs.size() + " sequences.");*/

        //        ModelCodeAPI.setModelCode("XAL", "EnvelopeTracker");

        //        System.out.println("There are " + mcList.size() + " mode codes.");
        //        System.out.println("There are " + mcList.size() + " mode codes.");

        //        MachineModeAPI.setMachineMode("USER", "user defined model");

        //        MachineModeAPI.setMachineMode("USER", "user defined model");

        //        List<ModelCode> mcList = x.getAllModelCodes();
        //        System.out.println("There are " + mcList.size() + " mode codes.");

        //        System.out.println("1st XAL algorithm: " + x.getAlgorithmsForModelCode("XAL").get(0).getAlgorithm());

        //        ModelAPI.setModelHeader("LINAC", "XAL", "DESIGN", "Test", "A database upload test");

        //    List<Model> ml = ModelAPI.getAllModelsForModelLineAndMachineMode("LINAC", "LIVE");
        //        List<Model> ml = x.getAllModelsForModelLine("LINAC");
                 /*   if (!ml.isEmpty()) {
         Iterator<Model> it = ml.iterator();
         int i = 1;
         while (it.hasNext()) {
         Model m = it.next();
         System.out.println("Model " + i + ": " + m.getModelName() + " with mode = " + m.getLatticeId().getMachineModeId().getMachineModeName());
         i++;
         }
         }*/
        /*Query q;
         q=em.createNamedQuery("Lattice.findByLatticeId").setParameter("latticeId", 1);
                   
         Lattice lat = (Lattice) q.getResultList().get(0);
         GoldLatticeAPI.setGoldLattice(lat);*/

        //  Query q;
        //System.out.println(m.getModelName());
        //  Model m = (Model) q.getResultList().get(0);
        //System.out.println(m.getModelName());

        /*  List<Model> mList=GoldModelAPI.getAllPresentGoldModels();
         if(mList!=null)
         System.out.println(mList.get(0).getModelName());*/
        /*GoldModel gm=GoldModelAPI.getGoldModelForMachineModeAndModelLine("DESIGN", "1");
         System.out.println(gm.getCreatedBy());*/
        //  GoldModelAPI.setGoldModel(2);
        // GoldLatticeAPI.setGoldLattice(1);
        //  System.out.println(BeamlineSequenceAPI.getEntranceElementForSequence("aaa"));
        //   List<Element> eList=BeamlineSequenceAPI.getAllElementsOfTypeForSequence("SEQ1", "MARK");
        // System.out.println(BeamlineSequenceAPI.getElementCountForSequence("Sa"));
        try {
            BeamParameterAPI beamParameterAPI = new BeamParameterAPI();
           Map map= beamParameterAPI.getAllBeamParametersForElement("SEQ1_START");
           System.out.println(map.get("alphaX"));
          
        } catch (IllegalAccessException ex) {
            Logger.getLogger(ModelDB.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalArgumentException ex) {
            Logger.getLogger(ModelDB.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}

