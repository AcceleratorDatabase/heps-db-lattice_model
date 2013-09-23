/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.openepics.model.api;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnit;
import javax.persistence.Query;
import org.openepics.model.entity.BeamParameter;
import org.openepics.model.entity.BeamParameterProp;
import org.openepics.model.entity.Element;
import org.openepics.model.entity.Model;
import org.openepics.model.entity.ParticleType;
import org.openepics.model.extraEntity.BeamParams;

/**
 *
 * @author chu
 * @author lv
 */
public class BeamParameterAPI {

    @PersistenceUnit
    static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("modelAPIPU");
    static final EntityManager em = emf.createEntityManager();

    @PersistenceContext
    /**
     *
     * @param elm
     */
    public void setBeamParametersForElement(String elm) {
        // TODO fill in code
    }

    public List<BeamParameter> getAllBeamParametersForModel(Model model) {
        List<BeamParameter> bpList = null;

        Query q;
        q = em.createQuery("SELECT bp FROM BeamParameter bp WHERE bp.modelId=:modelId").setParameter("modelId", model);
        bpList = q.getResultList();
        return bpList;

    }

    /**
     * get all beam parameters for the specified element
     *
     * @param elm element name
     * @return all beam parameters for the specified element
     */
    public Map<String, Object> getAllBeamParametersForElement(String elm) {
        Query q;
        q = em.createQuery("SELECT  bp FROM BeamParameter bp WHERE bp.elementId.elementName=:elName")
                .setParameter("elName", elm);
        List<BeamParameter> bps = q.getResultList();
        if (bps.isEmpty()) {
            return null;
        } else {
            //to remove the first field "serialVersionUID" 
            if (bps.size() > 1) {
                System.out.println("warning:there are more than 1 bean_parameter for the specified element!");
            } else {
                BeamParameter bp = bps.get(0);
                Map<String, Object> map = new HashMap<>();
                Field[] fields = bp.getClass().getDeclaredFields();
                int l = fields.length;
                System.out.println(l);
                for (int i = 1; i < l; i++) {
                    String fieldName = fields[i].getName();
                    if (!fieldName.toLowerCase().contains("collection")) {
                        String getter = "get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
                        if (!getter.contains("persistence")) {
                            Method method = null;
                            try {
                                //System.out.println(i + "**********" + getter);
                                method = bp.getClass().getMethod(getter, new Class[]{});
                                //System.out.println(i + "++++++++" + method);
                            } catch (NoSuchMethodException ex) {
                                Logger.getLogger(BeamParameterAPI.class.getName()).log(Level.SEVERE, null, ex);
                            } catch (SecurityException ex) {
                                Logger.getLogger(BeamParameterAPI.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            Object value = null;
                            try {
                                try {
                                    value = method.invoke(bp, new Object[]{});
                                } catch (IllegalAccessException ex) {
                                    Logger.getLogger(BeamParameterAPI.class.getName()).log(Level.SEVERE, null, ex);
                                } catch (IllegalArgumentException ex) {
                                    Logger.getLogger(BeamParameterAPI.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            } catch (InvocationTargetException ex) {
                                Logger.getLogger(BeamParameterAPI.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            System.out.println("******" + value);
                            map.put(fieldName, value);
                        }
                    }
                }
                return map;
            }
        }
        return null;
        /*Query q;
         q = em.createQuery("SELECT  bp FROM BeamParameter bp WHERE bp.elementId.elementName=:elName")
         .setParameter("elName", elm);
         List<BeamParameter> bps = q.getResultList();
         if (bps.isEmpty()) {
         return null;
         } else {
         //to remove the first field "serialVersionUID" 
         if (bps.size() > 1) {
         System.out.println("warning:there are more than 1 bean_parameter for the specified element!");
         } else {
         BeamParameter bp = bps.get(0);
         Map<String, Object> map = new HashMap<>();
         Field[] fields = bp.getClass().getDeclaredFields();
         int l = fields.length;
         System.out.println(l);
         for (int i = 1; i < l; i++) {
         String fieldName = fields[i].getName();
         System.out.println(fieldName);
         String getter = "get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
         System.out.println(i+"*******"+getter);
         Method method = null;
         try {
         method = bp.getClass().getMethod(getter, new Class[]{});
         } catch (NoSuchMethodException ex) {
         Logger.getLogger(BeamParameterAPI.class.getName()).log(Level.SEVERE, null, ex);
         } catch (SecurityException ex) {
         Logger.getLogger(BeamParameterAPI.class.getName()).log(Level.SEVERE, null, ex);
         }
         Object value = null;
         try {
         try {
         value = method.invoke(bp, new Object[]{});
         } catch (IllegalAccessException ex) {
         Logger.getLogger(BeamParameterAPI.class.getName()).log(Level.SEVERE, null, ex);
         } catch (IllegalArgumentException ex) {
         Logger.getLogger(BeamParameterAPI.class.getName()).log(Level.SEVERE, null, ex);
         }
         } catch (InvocationTargetException ex) {
         Logger.getLogger(BeamParameterAPI.class.getName()).log(Level.SEVERE, null, ex);

         }
         map.put(fieldName, value);
         }
         return map;
         }
         }
         return null;*/
    }

    public BeamParameter setBeamParameter(Element ele, Model model, ParticleType pt) {
        BeamParameter bp = new BeamParameter();
        bp.setElementId(ele);
        bp.setModelId(model);
        bp.setParticleType(pt);
        em.getTransaction().begin();
        em.persist(bp);
        em.getTransaction().commit();

        return bp;
    }

    public BeamParameter getBeamParameterForElementAndModel(String ele_name, String model_name) {
        Query q;
        if (model_name == null || model_name.equals("")) {
            q = em.createQuery("SELECT bp FROM BeamParameter bp WHERE bp.elementId.elementName=:eleName ").setParameter("eleName", ele_name);
        } else {
            q = em.createQuery("SELECT bp FROM BeamParameter bp WHERE bp.elementId.elementName=:eleName "
                    + "AND bp.modelId.modelName=:modelName").setParameter("eleName", ele_name).setParameter("modelName", model_name);
        }

        List<BeamParameter> bpList = q.getResultList();
        if (bpList.isEmpty()) {
            return null;
        } else {
            return bpList.get(0);
        }
    }

    public void deleteBeamParameter(BeamParameter bp) {
        if (bp != null) {
            em.getTransaction().begin();

            Collection<BeamParameterProp> bppList = bp.getBeamParameterPropCollection();
            new BeamParameterPropAPI().deleteBeamParameterPropCollection(bppList);
            if (em.contains(bp)) {
                em.remove(bp);
            } else {
                int id = (int) emf.getPersistenceUnitUtil().getIdentifier(bp);
                em.remove(em.find(BeamParameter.class, id));
            }
            em.getTransaction().commit();
        }
    }

    public void deleteBeamParameterCollection(Collection<BeamParameter> bpList) {
        Iterator it = bpList.iterator();
        em.getTransaction().begin();
        while (it.hasNext()) {
            BeamParameter bp = (BeamParameter) it.next();
            Collection<BeamParameterProp> bppList = bp.getBeamParameterPropCollection();
            new BeamParameterPropAPI().deleteBeamParameterPropCollection(bppList);

            if (em.contains(bp)) {
                em.remove(bp);
            } else {
                int id = (int) emf.getPersistenceUnitUtil().getIdentifier(bp);
                em.remove(em.find(BeamParameter.class, id));
            }
        }
        em.getTransaction().commit();
    }

    public List<BeamParameter> getAllBeamParameters() {
        Query q;
        q = em.createNamedQuery("BeamParameter.findAll");
        List<BeamParameter> bList = q.getResultList();

        return bList;
    }
}