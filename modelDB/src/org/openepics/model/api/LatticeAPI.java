/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.openepics.model.api;

import java.util.Date;
import java.util.Iterator;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnit;
import javax.persistence.Query;
import org.openepics.model.entity.ElementProp;
import org.openepics.model.entity.Lattice;

/**
 *
 * @author chu
 * @author lv
 */
public class LatticeAPI {

    @PersistenceUnit
    static EntityManagerFactory emf = Persistence.createEntityManagerFactory("modelAPIPU");
    static EntityManager em = emf.createEntityManager();

    @PersistenceContext
    public Lattice getLatticeByName(String latticeName) {
        Query q;
        q = em.createNamedQuery("Lattice.findByLatticeName").setParameter("latticeName", latticeName);
        List<Lattice> latList = q.getResultList();
        if (latList.isEmpty()) {
            return null;
        } else {
            return latList.get(0);
        }
    }

    public List<ElementProp> getAllPropertiesForLattice(String latticeName) {
        Query q;
        q = em.createQuery("SELECT ep FROM ElementProp ep JOIN ep.latticeId l "
                + "WHERE l.latticeName = :latticeName").setParameter("latticeName", latticeName);
        List<ElementProp> epList = q.getResultList();
        return epList;
    }

    public void deleteLatticeByName(String name) {
        Lattice l = getLatticeByName(name);
        if (l != null) {
            em.getTransaction().begin();
            List<ElementProp> epList = getAllPropertiesForLattice(name);
            Iterator it = epList.iterator();
            while (it.hasNext()) {
                ElementProp ep = (ElementProp) it.next();
                em.remove(em.merge(ep));
            }
            em.remove(em.merge(l));
            em.getTransaction().commit();

        } else {
            System.out.println("The lattice " + name + " doesn't exist!");
        }
    }

  
    public int setLattice(String lattice_name,String lattice_description) {
        Lattice l=new Lattice();
        Date date=new Date();
        l.setLatticeName(lattice_name);
        l.setLatticeDescription(lattice_description);
        l.setCreatedBy(System.getProperty("user.name"));
        l.setCreateDate(date);
        
        em.getTransaction().begin();
        em.persist(l);
        em.getTransaction().commit();
        
        return l.getLatticeId();
    }
}
