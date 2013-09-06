/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.openepics.model.api;

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

    public void setModel(String model_name, String lattice_name, Collection<Device> deviceCollection) {
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

        Iterator it = deviceCollection.iterator();
        while (it.hasNext()) {
            Device device = (Device) it.next();
            ElementType et = new ElementTypeAPI().getElementTypeByType(device.getElementType());
            BeamlineSequence bls = new BeamlineSequenceAPI().getSequenceByName(device.getBeamlineSequenceName());

            Element element = new Element();
            element.setDx(device.getDx());
            element.setDy(device.getDy());
            element.setDz(device.getDz());
            element.setElementName(device.getElementName());
            element.setLen(device.getLen());
            element.setPitch(device.getPitch());
            element.setPos(device.getPos());
            element.setRoll(device.getRoll());
            element.setS(device.getS());
            element.setYaw(device.getYaw());
            element.setElementTypeId(et);
            element.setBeamlineSequenceId(bls);

            element.setCreatedBy(System.getProperty("user.name"));
            element.setInsertDate(new Date());

            Collection<BeamParams> beamParamCollection = device.getBeamParamsCollection();
            Iterator bpit = beamParamCollection.iterator();
            while (bpit.hasNext()) {
                BeamParameter beamParameter = new BeamParameter();

                BeamParams beamParams = (BeamParams) bpit.next();
                ParticleType pt = new ParticleTypeAPI().getParticleType(beamParams.getParticleName());

                beamParameter.setElementId(element);
                beamParameter.setModelId(m);
                beamParameter.setParticleType(pt);
                em.persist(beamParameter);

                Collection<BeamParameterProp> beamParameterPropCollection = beamParams.getBeamParameterPropCollection();
                Iterator bppit = beamParameterPropCollection.iterator();
                while (bppit.hasNext()) {
                    BeamParameterProp beamParameterProp = (BeamParameterProp) bppit.next();
                    beamParameterProp.setBeamParameterId(beamParameter);
                    em.persist(beamParameterProp);
                }
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
            em.persist(element);
        }
        em.getTransaction().commit();
    }
}
