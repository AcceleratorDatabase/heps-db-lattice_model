/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.openepics.model.api;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnit;
import javax.persistence.Query;
import org.openepics.model.entity.Element;
import org.openepics.model.entity.ElementProp;

/**
 *
 * @author chu
 */
public class ElementPropAPI {

    @PersistenceUnit
    static EntityManagerFactory emf = Persistence.createEntityManagerFactory("modelAPIPU");
    static EntityManager em = emf.createEntityManager();

    @PersistenceContext
    /**
     * get the physics name for the element
     *
     * @param elm element name
     * @return physics name for the element
     */
    public String getPidForElement(String elm) {
        String pid = "";
        Query q;
        q = em.createQuery("SELECT ep from ElementProp ep "
                + "WHERE ep.elementId.elementName = :elementname "
                + "AND ep.elementPropName = \"pid\"")
                .setParameter("elementname", elm);
        List<ElementProp> attrList = q.getResultList();
        Iterator<ElementProp> it = attrList.iterator();
        while (it.hasNext()) {
            ElementProp ep = it.next();
            pid = ep.getElementPropString();
        }

        return pid;
    }

    public List<String> getAllPropertyNames() {
        List<String> names = new ArrayList();
        Query q;
        String sql = "SELECT DISTINCT element_prop_name FROM element_prop";
        q = em.createNativeQuery(sql);
        List l = q.getResultList();
        return l;
    }

    public Object getElementPropValueByNameForElement(String ele_prop_name, Element e) {
        Query q;
        String sql = "SELECT element_prop_id FROM (SELECT * FROM element_prop WHERE element_id=" + e.getElementId() + ")" + " AS ep" + " WHERE element_prop_name='" + ele_prop_name + "'";
        // System.out.println(sql);
        q = em.createNativeQuery(sql);
        List idList = q.getResultList();
        // System.out.println(idList);
        if (idList.isEmpty()) {
            return null;
        } else {
            ElementProp ep = em.find(ElementProp.class, idList.get(0));
            if (ep.getElementPropDouble() != null) {
                return ep.getElementPropDouble();
            } else if (ep.getElementPropInt() != null) {
                return ep.getElementPropInt();
            } else if (ep.getElementPropString() != null) {
                return ep.getElementPropString();
            }
        }
        return null;
    }

    /**
     * get magnet attributes for the specified element
     *
     * @param elm element name
     * @return magnet attributes for the specified element
     */
    public Map getMagnetAttributesForElement(String elm) {
        HashMap<String, Object> atts = new HashMap<>();

        Query q;
        q = em.createQuery("SELECT ep from ElementProp ep "
                + "WHERE ep.elementId.elementName = :elementname "
                + "AND ep.propCategory = \"magnet\"")
                .setParameter("elementname", elm);
        List<ElementProp> attrList = q.getResultList();

        Iterator<ElementProp> it = attrList.iterator();
        while (it.hasNext()) {
            ElementProp ep = it.next();
            if (ep.getElementPropDouble() != null) {
                atts.put(ep.getElementPropName(), ep.getElementPropDouble());
            }
            if (ep.getElementPropInt() != null) {
                atts.put(ep.getElementPropName(), ep.getElementPropInt());
            }
            if (ep.getElementPropString() != null) {
                atts.put(ep.getElementPropName(), ep.getElementPropString());
            }
        }

        return atts;
    }

    /**
     * get aperture attributes for the specified element
     *
     * @param elm element name
     * @return aperture attributes for the specified element
     */
    public Map getApertureAttributesForElement(String elm) {
        HashMap<String, Object> atts = new HashMap<>();

        Query q;
        q = em.createQuery("SELECT ep from ElementProp ep "
                + "WHERE ep.elementId.elementName = :elementname "
                + "AND ep.propCategory = \"aperture\"")
                .setParameter("elementname", elm);
        List<ElementProp> attrList = q.getResultList();

        Iterator<ElementProp> it = attrList.iterator();
        while (it.hasNext()) {
            ElementProp ep = it.next();
            if (ep.getElementPropDouble() != null) {
                atts.put(ep.getElementPropName(), ep.getElementPropDouble());
            }
        }

        return atts;
    }

    /**
     * get alignment attributes for the specified element
     *
     * @param elm element name
     * @return alignment attributes for the specified element
     */
    public Map getAlignmentAttributesForElement(String elm) {
        HashMap<String, Object> atts = new HashMap<>();

        Query q;
        q = em.createQuery("SELECT ep from ElementProp ep "
                + "WHERE ep.elementId.elementName = :elementname "
                + "AND ep.propCategory = \"align\"")
                .setParameter("elementname", elm);
        List<ElementProp> attrList = q.getResultList();

        Iterator<ElementProp> it = attrList.iterator();
        while (it.hasNext()) {
            ElementProp ep = it.next();
            if (ep.getElementPropDouble() != null) {
                atts.put(ep.getElementPropName(), ep.getElementPropDouble());
            }
        }

        return atts;
    }

    /**
     * get BPM attributes for the specified element
     *
     * @param elm element name
     * @return bpm attributes for the specified element
     */
    public Map getBpmAttributesForElement(String elm) {
        HashMap<String, Object> atts = new HashMap<>();

        Query q;
        q = em.createQuery("SELECT ep from ElementProp ep "
                + "WHERE ep.elementId.elementName = :elementname "
                + "AND ep.propCategory = \"bpm\"")
                .setParameter("elementname", elm);
        List<ElementProp> attrList = q.getResultList();

        Iterator<ElementProp> it = attrList.iterator();
        while (it.hasNext()) {
            ElementProp ep = it.next();
            if (ep.getElementPropDouble() != null) {
                atts.put(ep.getElementPropName(), ep.getElementPropDouble());
            }
        }

        return atts;
    }

    /**
     * get rfgap attributes for the specified element
     *
     * @param elm element name
     * @return rfgap attributes for the specified element
     */
    public Map getRfcavityAttributesForElement(String elm) {
        HashMap<String, Object> atts = new HashMap<>();

        Query q;
        q = em.createQuery("SELECT ep from ElementProp ep "
                + "WHERE ep.elementId.elementName = :elementname "
                + "AND ep.propCategory = \"rfcavity\"")
                .setParameter("elementname", elm);
        List<ElementProp> attrList = q.getResultList();

        Iterator<ElementProp> it = attrList.iterator();
        while (it.hasNext()) {
            ElementProp ep = it.next();
            if (ep.getElementPropDouble() != null) {
                atts.put(ep.getElementPropName(), ep.getElementPropDouble());
            } else if (ep.getElementPropString() != null) {
                atts.put(ep.getElementPropName(), ep.getElementPropString());
            } else if (ep.getElementPropInt() != null) {
                atts.put(ep.getElementPropName(), ep.getElementPropInt());
            }
        }

        return atts;
    }

    /**
     * set ElementProp for the specified Element
     *
     * @param e Element
     * @param propCategory prop category
     * @param propName elment prop name
     * @param prop elment prop value
     * @return initialized ElementProp
     */
    public ElementProp setElementProperty(Element e, String propCategory, String propName, String datatype, Object prop) {
        em.getTransaction().begin();
        ElementProp ep = new ElementProp();
        ep.setElementId(e);
        ep.setPropCategory(propCategory);
        ep.setElementPropName(propName);
        switch (datatype.toLowerCase()) {
            case "string":
                ep.setElementPropString(prop.toString());
                break;
            case "int":
                ep.setElementPropInt(Integer.parseInt(prop.toString()));
                break;
            case "double":
                ep.setElementPropDouble(Double.parseDouble(prop.toString()));
                break;
        }
        em.persist(ep);
        em.getTransaction().commit();
        return ep;
    }

    public int getMaxId() {
        Query q;
        q = em.createQuery("SELECT MAX(ep.elementPropId) FROM ElementProp ep");
        List<Integer> idList = q.getResultList();
        if (idList.get(0) == null) {
            return 0;
        } else {
            return idList.get(0);
        }

    }

    public void deleteElementProp(ElementProp ep) {
        if (ep != null) {
            em.getTransaction().begin();
            if (em.contains(ep)) {
                em.remove(ep);
            } else {
                int id = (int) emf.getPersistenceUnitUtil().getIdentifier(ep);
                em.remove(em.find(ElementProp.class, id));
            }
            em.getTransaction().commit();
        }
    }

    public void deleteElementPropCollection(Collection<ElementProp> epList) {
        Iterator it = epList.iterator();
        em.getTransaction().begin();
        while (it.hasNext()) {
            ElementProp ep = (ElementProp) it.next();
            if (em.contains(ep)) {
                em.remove(ep);
            } else {
                int id = (int) emf.getPersistenceUnitUtil().getIdentifier(ep);
                em.remove(em.find(ElementProp.class, id));
            }
        }
        em.getTransaction().commit();
    }
}
