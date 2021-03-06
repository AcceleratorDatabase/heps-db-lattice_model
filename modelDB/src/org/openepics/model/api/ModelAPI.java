/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.openepics.model.api;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnit;
import javax.persistence.Query;
import org.openepics.model.entity.BeamParameter;
import org.openepics.model.entity.BeamParameterProp;
import org.openepics.model.entity.BeamlineSequence;
import org.openepics.model.entity.Element;
import org.openepics.model.entity.ElementProp;
import org.openepics.model.entity.ElementType;
import org.openepics.model.entity.GoldModel;
import org.openepics.model.entity.Lattice;
import org.openepics.model.entity.MachineMode;
import org.openepics.model.entity.Model;
import org.openepics.model.entity.ModelCode;
import org.openepics.model.entity.ModelLine;
import org.openepics.model.entity.ParticleType;
import org.openepics.model.extraEntity.BeamParams;
import org.openepics.model.extraEntity.Device;

/**
 *
 * @author chu
 */
public class ModelAPI {

    @PersistenceUnit
    static EntityManagerFactory emf = Persistence.createEntityManagerFactory("modelAPIPU");
    static EntityManager em = emf.createEntityManager();

    @PersistenceContext
    /**
     * get all models
     *
     * @return all models
     */
    public List<Model> getAllModels() {
        final Query q = em.createNamedQuery("Model.findAll");
        List<Model> mc = q.getResultList();

        return mc;

    }

    /**
     * get the model with a specific model ID
     * @param m_id Model ID
     * @return Model with the specified ID
     */
    public Model getModel(int m_id) {
        final Query q = em.createNamedQuery("Model.findByModelId").setParameter("modelId", m_id);
        List<Model> m = q.getResultList();
        
        return m.get(0);
    }
    
    /**
     * get models within a time range
     * @param start_time the start time of the range
     * @param end_time the end time of the range
     * @return 
     */
    public List<Model> getModelsFor(Date start_time, Date end_time) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        dateFormat.format(start_time);
        dateFormat.format(end_time);
        List<Model> mList = null;
        Query q;
        q = em.createQuery("SELECT m FROM Model m WHERE m.initialConditionInd=:init_ind "
                + "AND m.createDate BETWEEN :s_time AND :e_time").setParameter("init_ind", 0)
                .setParameter("s_time", start_time).setParameter("e_time", end_time);
        mList = q.getResultList();
        return mList;
    }
 
 /**
     * Get all models for the specified machine mode
     *
     * @param mode machine mode name
     * @return models for the specified machine mode
     */
    public List<Model> getAllModelsForMachineMode(String mode) {
        Query q;
        q = em.createQuery("SELECT m FROM Model m "
                + "JOIN m.machineModeId mm WHERE mm.machineModeName = :modeName").setParameter("modeName", mode);
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
     * get the default (Gold) model for the default line
     *
     * @return the default model
     */
    public Model getDefaultModel() {
        Model m = null;

        // TODO fill in the code

        return m;
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
                + " AND mm.machine_Mode_Name=\""
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
                //  m.setModelLineId(ml);
                l.setModelLineId(ml);
            } else {
                //  m.setModelLineId((ModelLine) q.getResultList().get(0));
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
                // m.setMachineModeId(mm);
                l.setMachineModeId(mm);
            } else {
                // m.setMachineModeId(mmList.get(0));
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
        } else {
            // create new model_code
            ModelCode mc = new ModelCode();
            mc.setCodeName(model_code);
            em.persist(mc);
            m.setModelCodeId(mc);
        }

        m.setModelName(model_name);
        m.setModelDesc(model_desc);
        m.setUpdatedBy(System.getProperty("user.name"));
        m.setUpdateDate(new Date());
        em.getTransaction().begin();
        em.persist(m);
        em.getTransaction().commit();
    }

    public void setModelForInit(String model_name, String lattice_name, String created_by, Date create_date) {
        Model m = new Model();
        Lattice l = new LatticeAPI().getLatticeByName(lattice_name);
        m.setLatticeId(l);
        m.setModelName(model_name);
        m.setUpdatedBy(System.getProperty("user.name"));
        m.setUpdateDate(new Date());
        m.setCreatedBy(created_by);
        m.setCreateDate(create_date);
        m.setInitialConditionInd(1);
        em.getTransaction().begin();
        em.persist(m);
        em.getTransaction().commit();
    }

    public Model getModelForName(String model_name) {
        Query q;
        q = em.createQuery("SELECT m FROM Model m WHERE m.modelName=:modelName").setParameter("modelName", model_name);
        List<Model> mList = q.getResultList();
        if (mList.isEmpty()) {
            return null;
        } else {
            return mList.get(0);
        }
    }

    public List<Model> getAllModelInitialConditions() {
        List<Model> mList = null;

        Query q;
        q = em.createQuery("SELECT m FROM Model m WHERE m.initialConditionInd=:ind").setParameter("ind", 1);
        mList = q.getResultList();

        return mList;
    }

    public void setModel(String acc_name, String model_name, String lattice_name, Collection<Device> deviceCollection) {
        em.getTransaction().begin();

        Lattice l = new Lattice();
        l.setLatticeName(lattice_name);
        l.setCreatedBy(System.getProperty("user.name"));
        l.setCreateDate(new Date());
        em.persist(l);

        Model m = new Model();
        m.setModelName(model_name);
        m.setCreatedBy(System.getProperty("user.name"));
        m.setCreateDate(new Date());
        m.setLatticeId(l);
        m.setInitialConditionInd(0);
        em.persist(m);

        int count = 0;
        Iterator it = deviceCollection.iterator();
        while (it.hasNext()) {
            Device device = (Device) it.next();
            ElementType et = new ElementTypeAPI().getElementTypeByType(device.getElementType());
            BeamlineSequence bls = new BeamlineSequenceAPI().getSequenceByName(device.getBeamlineSequenceName());

            //TODO re-use Elements already in the DB, instead of generating new ones and populate the new one to DB.
            ElementAPI elementAPI = new ElementAPI();
            
            String elementName = device.getElementName();
            int slice_id = 0;
            // If an Element name contains "_A", it is the 2nd slice of a thick Element.
            // We need to strip off the "_A" and assign the slice_id as a beam parameter
            if (device.getElementName().contains("_A")) {
                elementName = elementName.replace("_A", "");
                slice_id = 1;
            }
            
            // System.out.println("Acc name = " + acc_name + ", Element name = " + elementName);
            
            Element element = null;
            // If this is for XAL Model, use the Element_id; if this is MADX or other models, use the element's alias name
            if (model_name.contains("XAL")) {
                // handle RF Gap 
                // Here we rely on naming convention. A more reliable way should be loading the accelerator object and find
                // the RF Gap's parent node; however, that also has its own flaw because the XDXF file might be out of sync from 
                // the DB record.
                if (elementName.contains("RG")) {
                    element = elementAPI.getElementByName(acc_name, elementName.substring(0, elementName.indexOf("RG")-1));
                } else {
                
                    element = elementAPI.getElementByName(acc_name, elementName);
                }
            } else {
                element = elementAPI.getElementByAliasName(acc_name, elementName);  
            }
            
            if (element != null) {
//            Element element = new Element();
//                element.setDx(device.getDx());
//                element.setDy(device.getDy());
//                element.setDz(device.getDz());
//                element.setElementName(device.getElementName());
//                element.setLen(device.getLen());
//                element.setPitch(device.getPitch());
//                element.setPos(device.getPos());
//                element.setRoll(device.getRoll());
//                element.setS(device.getS());
//                element.setYaw(device.getYaw());
//                element.setElementTypeId(et);
//                element.setBeamlineSequenceId(bls);
//
//                element.setCreatedBy(System.getProperty("user.name"));
//                element.setInsertDate(new Date());

                BeamParams beamParams = device.getBeamParams();

                int max_slice_id = beamParams.getMaxSliceId();
//                System.out.println(elementName + " has max_slice_id = " + max_slice_id);
                
                // loop through all slices
                for (int i = 0; i <= max_slice_id; i++) {
                    BeamParameter beamParameter = new BeamParameter();
                    ParticleType pt = new ParticleTypeAPI().getParticleType(beamParams.getParticleName());

                    beamParameter.setElementId(element);
                    beamParameter.setModelId(m);
                    beamParameter.setParticleType(pt);
                    // set slice_id
                    beamParameter.setSliceId(i);
                    Collection<BeamParameterProp> beamParameterPropCollection = beamParams.getBeamParameterPropCollection(i);
                    Iterator bppit = beamParameterPropCollection.iterator();
                    while (bppit.hasNext()) {
                        BeamParameterProp beamParameterProp = (BeamParameterProp) bppit.next();
                        beamParameterProp.setBeamParameterId(beamParameter);
                        em.persist(beamParameterProp);
                    }
                    em.persist(beamParameter);
                }
                
                Collection<ElementProp> elementPropCollection = device.getElementPropCollection();
                Iterator epit = elementPropCollection.iterator();
                while (epit.hasNext()) {
                    ElementProp ep = new ElementProp();

                    ElementProp elementProp = (ElementProp) epit.next();

                    ep.setElementPropDouble(elementProp.getElementPropDouble());
                    ep.setElementPropInt(elementProp.getElementPropInt());
                    ep.setElementPropString(elementProp.getElementPropString());
                    ep.setElementPropName(elementProp.getElementPropName());
                    ep.setPropCategory(elementProp.getPropCategory());

                    ep.setElementId(element);
                    ep.setLatticeId(l);

                    em.persist(ep);
                }
//                System.out.println("acc_name = " + acc_name + ", elementName = " + elementName);

                count++;
//                em.persist(element);
            } else {
                System.out.println("Cannot find Element: " + elementName);
            }
        }
        em.getTransaction().commit();
        System.out.println(count + " elements' properties/beam properties saved to the DB.");
    }

    public void deleteModel(String model_name) {

        Model m = this.getModelForName(model_name);
        if (m != null) {
            em.getTransaction().begin();
            List<GoldModel> gmList = new GoldModelAPI().getGoldModelByModelName(model_name);
            if (!(gmList.isEmpty() || gmList == null)) {
                Iterator it3 = gmList.iterator();
                while (it3.hasNext()) {
                    GoldModel gm = (GoldModel) it3.next();
                    if (em.contains(gm)) {
                        em.remove(gm);
                    } else {
                        int id = (int) emf.getPersistenceUnitUtil().getIdentifier(gm);
                        em.remove(em.find(GoldModel.class, id));
                    }

                }
            }

            List<BeamParameter> bpList = (List<BeamParameter>) m.getBeamParameterCollection();
            if (!bpList.isEmpty()) {
                Iterator it1 = bpList.iterator();
                while (it1.hasNext()) {
                    BeamParameter bp = (BeamParameter) it1.next();
                    Collection<BeamParameterProp> bppList = (Collection<BeamParameterProp>) bp.getBeamParameterPropCollection();

                    Iterator it2 = bppList.iterator();
                    while (it2.hasNext()) {
                        BeamParameterProp bpp = (BeamParameterProp) it2.next();
                        if (em.contains(bpp)) {
                            em.remove(bpp);
                        } else {
                            int id = (int) emf.getPersistenceUnitUtil().getIdentifier(bpp);
                            em.remove(em.find(BeamParameterProp.class, id));
                        }

                    }
                    if (em.contains(bp)) {
                        em.remove(bp);
                    } else {
                        int id = (int) emf.getPersistenceUnitUtil().getIdentifier(bp);
                        em.remove(em.find(BeamParameter.class, id));
                    }
                }
                if (em.contains(m)) {
                    em.remove(m);
                } else {
                    int id = (int) emf.getPersistenceUnitUtil().getIdentifier(m);
                    em.remove(em.find(Model.class, id));
                }
            }
            em.getTransaction().commit();
        }
    }
}
