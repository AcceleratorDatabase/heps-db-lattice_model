/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.openepics.model.api;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnit;
import javax.persistence.Query;
import org.openepics.model.entity.BeamlineSequence;
import org.openepics.model.entity.Element;
import org.openepics.model.entity.ElementProp;
import org.openepics.model.entity.ElementType;
import org.openepics.model.entity.RfGap;

/**
 *
 * @author chu
 * @author lv
 */
public class ElementAPI {

    @PersistenceUnit
    static EntityManagerFactory emf = Persistence.createEntityManagerFactory("modelAPIPU");
    static EntityManager em = emf.createEntityManager();

    @PersistenceContext
    /**
     * get all properties for the specified element
     *
     * @param elementName element name
     * @return all properties for the specified element
     */
    public List<ElementProp> getAllPropertiesForElement(String elementName) {
        Query q;
        q = em.createQuery("SELECT ep FROM ElementProp ep JOIN ep.elementId e "
                + "WHERE e.elementName = :elementName").setParameter("elementName", elementName);
        List<ElementProp> epList = q.getResultList();
        return epList;
    }

    /**
     * get Element for the specified element name
     *
     * @param name element name
     * @return element for the specified element name
     */
    public Element getElementByName(String name) {
        Query q;
        q = em.createNamedQuery("Element.findByElementName").setParameter("elementName", name);
        List<Element> eList = q.getResultList();
        if (eList.isEmpty()) {
            return null;
        } else {
            return eList.get(0);
        }
    }

    /**
     * Insert a new element into database
     *
     * @param name element name
     * @param order element order within the entire accelerator
     * @param s global accumulated path length
     * @param len element length
     * @param dx horizontal coordinate error
     * @param dy vertical coordinate error
     * @param dz longitudinal coordinate error
     * @param pitch pitch error
     * @param yaw yaw error
     * @param roll roll error
     * @param pos distance from start of the sequence
     * @param sequence_name the sequence name the element resided in
     */
    public void setElement(String name, int order, double s,
            double len, double dx, double dy, double dz, double pitch, double yaw, double roll,
            double pos, String sequence_name) {
        // first, check if this element has already in the DB
        if (getElementByName(name) == null) {
            Element e = new Element();
            Date date = new Date();
            e.setInsertDate(date);
            e.setCreatedBy(System.getProperty("user.name"));
            e.setElementOrder(order);
            e.setS(s);
            e.setLen(len);
            e.setDx(dx);
            e.setDy(dy);
            e.setDz(dz);
            e.setPitch(pitch);
            e.setYaw(yaw);
            e.setRoll(roll);
            e.setPos(pos);

            // find out the beamline sequence id
            BeamlineSequenceAPI beamlineSequenceAPI = new BeamlineSequenceAPI();
            BeamlineSequence bls = beamlineSequenceAPI.getSequenceByName(sequence_name);
            e.setBeamlineSequenceId(bls);
        }
    }

    public void setElement(String ele_name, double s, double len, double dx, double dy, double dz, double pitch, double yaw, double roll,
            double pos, String seq_name, String ele_type_name) {
        if (getElementByName(ele_name) == null) {
            Element e = new Element();
            Date date = new Date();
            e.setInsertDate(date);
            e.setCreatedBy(System.getProperty("user.name"));
            e.setS(s);
            e.setLen(len);
            e.setDx(dx);
            e.setDy(dy);
            e.setDz(dz);
            e.setPitch(pitch);
            e.setYaw(yaw);
            e.setRoll(roll);
            e.setPos(pos);
            e.setElementName(ele_name);
            ElementType et = new ElementTypeAPI().getElementTypeByType(ele_type_name);
            e.setElementTypeId(et);
            // find out the beamline sequence id
            BeamlineSequenceAPI beamlineSequenceAPI = new BeamlineSequenceAPI();
            BeamlineSequence bls = beamlineSequenceAPI.getSequenceByName(seq_name);
            e.setBeamlineSequenceId(bls);
            em.getTransaction().begin();
            em.persist(e);
            em.getTransaction().commit();
        }
    }
    /*
     * Delete the element by the given name
     * Delete the ElementProps belonging to the Element
     * Delete the RfGaps belonging to the Element
     */

    public void deleteElementByName(String name) {
        em.getTransaction().begin();
        Element e = getElementByName(name);
        if (e != null) {
            List<ElementProp> epList = getAllPropertiesForElement(name);
            Iterator it1 = epList.iterator();
            while (it1.hasNext()) {
                ElementProp ep = (ElementProp) it1.next();
                em.remove(em.merge(ep));
            }

            RfGapAPI rfGapAPI = new RfGapAPI();
            List<RfGap> rfList = rfGapAPI.getAllRfgapsForCavity(name);
            Iterator it2 = rfList.iterator();
            while (it2.hasNext()) {
                RfGap rf = (RfGap) it2.next();
                em.remove(em.merge(rf));
            }
            em.remove(em.merge(e));
        } else {
            System.out.println("The element " + name + " doesn't exist!");
        }
        em.getTransaction().commit();

    }

    public void updateElement(String old_name, String new_name, Object order, double s,
            double len, double dx, double dy, double dz, double pitch, double yaw, double roll,
            double pos, String sequence_name) {

        Element e = getElementByName(old_name);
        if (e != null) {
            em.getTransaction().begin();
            e.setElementName(new_name);
            Date date = new Date();
            e.setInsertDate(date);
            e.setCreatedBy(System.getProperty("user.name"));
            if (order != null) {
                e.setElementOrder(Integer.parseInt(order.toString()));
            } else {
                e.setElementOrder(null);
            }
            e.setS(s);
            e.setLen(len);
            e.setDx(dx);
            e.setDy(dy);
            e.setDz(dz);
            e.setPitch(pitch);
            e.setYaw(yaw);
            e.setRoll(roll);
            e.setPos(pos);

            // find out the beamline sequence id
            BeamlineSequenceAPI beamlineSequenceAPI = new BeamlineSequenceAPI();
            BeamlineSequence bls = beamlineSequenceAPI.getSequenceByName(sequence_name);
            e.setBeamlineSequenceId(bls);

            em.merge(e);
            em.getTransaction().commit();

        } else {
            System.out.println("The element " + old_name + " doesn't exist!");
        }
    }

    public int getMaxId() {
        Query q;
        q = em.createQuery("SELECT MAX(e.elementId) FROM Element e");
        List<Integer> idList = q.getResultList();
        if (idList.get(0) == null) {
            return 0;
        } else {
            return idList.get(0);
        }
    }

    public ArrayList<Element> getAllElementsWithNoOrder() {
        Query q;
        //  q=em.createNamedQuery("Element.f")
        return null;
    }
}
