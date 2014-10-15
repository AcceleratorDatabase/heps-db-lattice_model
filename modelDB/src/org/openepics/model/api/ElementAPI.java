/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.openepics.model.api;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import edu.msu.frib.xal.exl2DB.tools.DBTools;
import edu.msu.frib.xal.exl2DB.tools.MyComparator;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
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
import org.openepics.model.entity.BeamlineSequence;
import org.openepics.model.entity.Element;
import org.openepics.model.entity.ElementProp;
import org.openepics.model.entity.ElementType;
import org.openepics.model.entity.Lattice;
import org.openepics.model.entity.RfGap;

/**
 *
 * @author chu
 * @author lv
 */
public class ElementAPI {

    @PersistenceUnit
    static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("modelAPIPU");
    static final EntityManager em = emf.createEntityManager();

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
    
    public Element getElementByName(String acc, String name) {
        Query q;
        q = em.createQuery("SELECT e FROM Element e WHERE e.elementName=:name AND e.beamlineSequenceId.acceleratorId.acceleratorName=:acc"
                ).setParameter("name", name).setParameter("acc", acc);
        List<Element> eList = q.getResultList();
        if (eList.isEmpty()) {
            return null;
        } else {
            return eList.get(0);
        }        
    }
    
    public Element getElementByAliasName(String name) {
        Query q;
        q = em.createNamedQuery("Element.findByAliasName").setParameter("aliasName", name);
        List<Element> eList = q.getResultList();
        if (eList.isEmpty()) {
            return null;
        } else {
            return eList.get(0);
        }        
    }
    
    public Element getElementByAliasName(String acc, String aliasName) {
        Query q;
        q = em.createQuery("SELECT e FROM Element e WHERE e.aliasName=:aliasName AND e.beamlineSequenceId.acceleratorId.acceleratorName=:acc"
                ).setParameter("aliasName", aliasName).setParameter("acc", acc);
//        q = em.createQuery("SELECT e FROM Element e WHERE e.aliasName=:aliasName"
//                ).setParameter("aliasName", aliasName);
        List<Element> eList = q.getResultList();
        if (eList.isEmpty()) {
            return null;
        } else {
            return eList.get(0);
        }              
    }

    public Element getElementByNameAndType(String name, String type) {
        Query q;
        q = em.createQuery("SELECT e FROM Element e WHERE e.elementName=:elementName "
                + "AND e.elementTypeId.elementType=:elementType").setParameter("elementName", name)
                .setParameter("elementType", type);
        List eList = q.getResultList();
        if (eList.isEmpty()) {
            return null;
        } else {
            return (Element) eList.get(0);
        }
    }

    public Element getElementByPid(String pid) {
        Query q;
        q = em.createQuery("SELECT ep FROM ElementProp ep WHERE ep.elementPropName=:propName"
                + " AND ep.elementPropString=:propString").setParameter("propName", "pid")
                .setParameter("propString", pid);
        List epList = q.getResultList();
        if (!epList.isEmpty()) {
            ElementProp ep = (ElementProp) epList.get(0);
            return ep.getElementId();
        } else {
            return null;
        }
    }

    public Element getElementByNameAndLattice(String ele_name, String lattice_name) {
        /*Query q;
         q = em.createNamedQuery("Element.findByElementName").setParameter("elementName", ele_name);
         List<Element> eList = q.getResultList();
         if (eList.isEmpty()) {
         return null;
         } else {
         for (int i = 0; i < eList.size(); i++) {
         Element e = eList.get(i);
         System.out.println(e);
         Query q1;
         q1 = em.createQuery("SELECT ep FROM ElementProp ep WHERE ep.elementId.elementName=:elementName").setParameter("elementName", ele_name);
         List epList = q1.setMaxResults(2).getResultList();
         if (epList.isEmpty()) {
         return null;
         } else {
         ElementProp ep = (ElementProp) epList.get(0);
         System.out.println(ep);
         if (lattice_name.equals(ep.getLatticeId().getLatticeName())) {
         return e;
         }

         }
         }
         }*/
        return null;
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
    public void setElement(String name, double s,
            double len, double dx, double dy, double dz, double pitch, double yaw, double roll,
            double pos, String sequence_name) {
        // first, check if this element has already in the DB
        if (getElementByName(name) == null) {
            Element e = new Element();
            Date date = new Date();
            e.setInsertDate(date);
            e.setCreatedBy(System.getProperty("user.name"));
            // e.setElementOrder(order);
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
            double pos, String created_by, Date create_date, String seq_name, String ele_type_name) {
        if (getElementByName(ele_name) == null) {
            Element e = new Element();
            e.setInsertDate(create_date);
            e.setCreatedBy(created_by);
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
     * Delete the BeamParameters belonging to the Element
     */

    public void deleteElementByName(String name) {
        em.getTransaction().begin();
        Element e = getElementByName(name);
        if (e != null) {
            //ElementProp
            Collection<ElementProp> epList = e.getElementPropCollection();
            new ElementPropAPI().deleteElementPropCollection(epList);
            //RfGap
            RfGapAPI rfGapAPI = new RfGapAPI();
            Collection<RfGap> rfList = (Collection<RfGap>) e.getRfGapCollection();
            new RfGapAPI().deleteRfGapCollection(rfList);
            //BeamParameter
            Collection<BeamParameter> bpList = e.getBeamParameterCollection();
            new BeamParameterAPI().deleteBeamParameterCollection(bpList);

            if (em.contains(e)) {
                em.remove(e);
            } else {
                int id = (int) emf.getPersistenceUnitUtil().getIdentifier(e);
                em.remove(em.find(RfGap.class, id));
            }

        } else {
            System.out.println("The element " + name + " doesn't exist!");
        }
        em.getTransaction().commit();
    }

    public void updateElement(int id, String element_name, double s,
            double len, double dx, double dy, double dz, double pitch, double yaw, double roll,
            double pos, String sequence_name) {

        // Element e = getElementByName(old_name);
        Element e = em.find(Element.class, id);
        if (e != null) {
            em.getTransaction().begin();
            e.setElementName(element_name);
            Date date = new Date();
            e.setInsertDate(date);
            e.setCreatedBy(System.getProperty("user.name"));
            /* if (order != null) {
             e.setElementOrder(Integer.parseInt(order.toString()));
             } else {
             e.setElementOrder(null);
             }*/
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
            System.out.println("The element " + element_name + " doesn't exist!");
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

    /**
     * get all elements within the lattice
     * @param latticeName lattice name in String
     * @return all elements within the specified lattice
     */
    public ArrayList<Element> getAllElementsForLattice(String latticeName) {
        Query q;
        ArrayList<Element> eList = new ArrayList();
        List<BeamlineSequence> blsList = new BlsequenceLatticeAPI().getSequencesForLattice(latticeName);
        Iterator it = blsList.iterator();
        while (it.hasNext()) {
            BeamlineSequence bls = (BeamlineSequence) it.next();
            q = em.createQuery("SELECT e FROM Element e WHERE e.beamlineSequenceId=:beamlineSequence ")
                    .setParameter("beamlineSequence", bls);

            List<Element> eList1 = q.getResultList();
            eList.addAll(eList1);
        }
        return eList;
    }

    /**
     * get all elements within the lattice
     * @param lattice lattice
     * @return all elements within the specified lattice
     */
    public ArrayList<Element> getAllElementsForLattice(Lattice lattice) {
        Query q;
        ArrayList<Element> eList = new ArrayList();
        q = em.createQuery("SELECT ep FROM ElementProp ep WHERE ep.latticeId=:lattice_id ")
                    .setParameter("lattice_id", lattice);
        List<ElementProp> epList = q.getResultList();
        Iterator<ElementProp> it1 = epList.iterator();
        while (it1.hasNext()) {
            ElementProp ep = it1.next();
            Element elem = ep.getElementId();
            if (!eList.contains(elem))
                eList.add(elem);
        }
                 
        return eList;        
    }

    public List<Element> getAllElements() {
        Query q;
        q = em.createNamedQuery("Element.findAll");
        List<Element> eList = q.getResultList();

        return eList;
    }

    public List<Element> getElementsByText(String searchItem) {
        Query q;
        q = em.createQuery("SELECT e FROM Element e WHERE e.elementName LIKE :ele_name OR e.beamlineSequenceId.sequenceName LIKE :seq_name")
                .setParameter("ele_name", "%" + searchItem + "%").setParameter("seq_name", "%" + searchItem + "%");
        List l = q.getResultList();

        Query q1 = em.createQuery("SELECT ep FROM ElementProp ep WHERE ep.elementPropName=:propName"
                + " AND ep.elementPropString LIKE :propString").setParameter("propName", "pid")
                .setParameter("propString", "%" + searchItem + "%");
        List epList = q1.getResultList();
        for (int i = 0; i < epList.size(); i++) {
            ElementProp ep = (ElementProp) epList.get(i);
            l.add(ep.getElementId());
        }
        return l;
    }

    public void sortElementsForLattice(String lattice_name) {
        Connection conn = null;
        PreparedStatement state = null;
        try {
            conn = (Connection) DBTools.getConnection();
            conn.setAutoCommit(false);
            List<Element> eleList = this.getAllElementsForLattice(lattice_name);
            Lattice l = new LatticeAPI().getLatticeByName(lattice_name);
            int lattice_id = l.getLatticeId();
            HashMap<Integer, Double> hMap = new HashMap<>();
            Iterator it = eleList.iterator();
            while (it.hasNext()) {
                Element ele = (Element) it.next();
                hMap.put(ele.getElementId(), ele.getS());
            }
            ElementPropAPI epAPI = new ElementPropAPI();
            int i = 0;
            if (!hMap.isEmpty()) {
                List mapList = new ArrayList(hMap.entrySet());
                Collections.sort(mapList, new MyComparator());
                Iterator iter = mapList.iterator();
                while (iter.hasNext()) {
                    i++;
                    Map.Entry entry = (Map.Entry) iter.next();
                    String sql = "insert into element_prop set element_id=?,element_prop_name=?,element_prop_int=?,lattice_id=?";
                    if (i == 1) {
                        state = (PreparedStatement) conn.prepareStatement(sql);
                    }
                    state.setInt(1, Integer.parseInt(entry.getKey().toString()));
                    state.setString(2, "element_order");
                    state.setInt(3, i);
                    state.setInt(4, lattice_id);
                    state.addBatch();
                }
                state.executeBatch();
                conn.commit();
            }
        } catch (SQLException ex) {
            Logger.getLogger(ElementAPI.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            DBTools.closePreparedStatement(state);
            DBTools.closeConnection();
        }
    }
}
