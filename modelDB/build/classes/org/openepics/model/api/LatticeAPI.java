/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.openepics.model.api;

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
import org.openepics.model.entity.BlsequenceLattice;
import org.openepics.model.entity.Element;
import org.openepics.model.entity.ElementProp;
import org.openepics.model.entity.Lattice;
import org.openepics.model.entity.Model;
import org.openepics.model.entity.RfGap;

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
    /**
     *
     */
    public int setLattice(String lattice_name, String created_by, Date create_date) {
        Lattice l = new Lattice();

        Date date = new Date();
        l.setLatticeName(lattice_name);
        l.setUpdatedBy(System.getProperty("user.name"));
        l.setUpdateDate(date);
        l.setCreatedBy(created_by);
        l.setCreateDate(create_date);
 
        em.getTransaction().begin();
        em.persist(l);
        em.getTransaction().commit();

        return l.getLatticeId();
    }

    /**
     *
     */
    public int setLattice(String lattice_name, String lattice_description, String created_by, Date create_date) {
        Lattice l = new Lattice();
        Date date = new Date();
        l.setLatticeName(lattice_name);
        l.setLatticeDescription(lattice_description);
        l.setUpdatedBy(System.getProperty("user.name"));
        l.setUpdateDate(date);
        l.setCreatedBy(created_by);
        l.setCreateDate(create_date);

        em.getTransaction().begin();
        em.persist(l);
        em.getTransaction().commit();

        return l.getLatticeId();
    }

    public Lattice getLatticeById(int lattice_id) {
        Lattice lattice = em.find(Lattice.class, lattice_id);
        //em.getTransaction().begin();    
        // em.getTransaction().commit();
        return lattice;
    }

    /**
     *
     * @param latticeName
     * @return
     */
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

    /**
     *
     * @param latticeName
     * @return
     */
    public List<ElementProp> getAllPropertiesForLattice(String latticeName) {
        Query q;
        q = em.createQuery("SELECT ep FROM ElementProp ep JOIN ep.latticeId l "
                + "WHERE l.latticeName = :latticeName").setParameter("latticeName", latticeName);
        List<ElementProp> epList = q.getResultList();
        return epList;
    }

    public List<Model> getModelsForLattice(String latticeName) {
        Query q;
        q = em.createQuery("SELECT m FROM Model m JOIN m.latticeId l WHERE l.latticeName=:latticeName")
                .setParameter("latticeName", latticeName);
        List<Model> mList = q.getResultList();
        return mList;
    }

    /**
     *
     * @param name
     */
    public void deleteLatticeByName(String name) {
        em.getTransaction().begin();
        Lattice l = getLatticeByName(name);
        if (l != null) {

            //model
            List modelList = (List) l.getModelCollection();
            if (!modelList.isEmpty()) {
                Iterator it5 = modelList.iterator();
                while (it5.hasNext()) {
                    Model m = (Model) it5.next();

                    List<BeamParameter> bpList = (List<BeamParameter>) m.getBeamParameterCollection();
                    if (!bpList.isEmpty()) {
                        Iterator it6 = bpList.iterator();
                        while (it6.hasNext()) {
                            BeamParameter bp = (BeamParameter) it6.next();
                            Collection<BeamParameterProp> bppList = (Collection<BeamParameterProp>) bp.getBeamParameterPropCollection();

                            Iterator it7 = bppList.iterator();
                            while (it7.hasNext()) {
                                BeamParameterProp bpp = (BeamParameterProp) it7.next();
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
                }
            }
            List blsList = new BlsequenceLatticeAPI().getSequencesForLattice(name);

            //blsequence_lattice
            Collection<BlsequenceLattice> blslList = l.getBlsequenceLatticeCollection();
            if (!blslList.isEmpty()) {
                Iterator it8 = blslList.iterator();
                while (it8.hasNext()) {
                    BlsequenceLattice blsl = (BlsequenceLattice) it8.next();
                    if (em.contains(blsl)) {
                        em.remove(blsl);
                    } else {
                        int id = (int) emf.getPersistenceUnitUtil().getIdentifier(blsl);
                        em.remove(em.find(BlsequenceLattice.class, id));
                    }
                }
            }

            //beamline sequence
            if (!blsList.isEmpty()) {
                Iterator it1 = blsList.iterator();
                while (it1.hasNext()) {
                    BeamlineSequence bls = (BeamlineSequence) it1.next();
                    List eleList = (List) bls.getElementCollection();
                    if (!eleList.isEmpty()) {
                        Iterator it2 = eleList.iterator();
                        while (it2.hasNext()) {

                            Element ele = (Element) it2.next();

                            List elePropList = (List) ele.getElementPropCollection();
                            //element prop
                            if (!elePropList.isEmpty()) {
                                Iterator it3 = elePropList.iterator();
                                while (it3.hasNext()) {
                                    ElementProp ep = (ElementProp) it3.next();
                                    if (em.contains(ep)) {
                                        em.remove(ep);
                                    } else {
                                        int id = (int) emf.getPersistenceUnitUtil().getIdentifier(ep);
                                        em.remove(em.find(ElementProp.class, id));
                                    }
                                }
                            }

                            //RfGap                         
                            List rfList = (List) ele.getRfGapCollection();
                            if (!rfList.isEmpty()) {
                                Iterator it4 = rfList.iterator();
                                while (it4.hasNext()) {
                                    RfGap rfGap = (RfGap) it4.next();
                                    if (em.contains(rfGap)) {
                                        em.remove(rfGap);
                                    } else {
                                        int id = (int) emf.getPersistenceUnitUtil().getIdentifier(rfGap);
                                        em.remove(em.find(RfGap.class, id));
                                    }

                                }
                            }
                            if (em.contains(ele)) {
                                em.remove(ele);
                            } else {
                                int id = (int) emf.getPersistenceUnitUtil().getIdentifier(ele);
                                em.remove(em.find(Element.class, id));
                            }
                        }
                    }

                    if (em.contains(bls)) {
                        em.remove(bls);
                    } else {
                        int id = (int) emf.getPersistenceUnitUtil().getIdentifier(bls);
                        em.remove(em.find(BeamlineSequence.class, id));
                    }
                }
            }

            if (em.contains(l)) {
                em.remove(l);
            } else {
                int id = (int) emf.getPersistenceUnitUtil().getIdentifier(l);
                em.remove(em.find(Lattice.class, id));
            }
            em.getTransaction().commit();
        } else {
            System.out.println("The lattice " + name + " doesn't exist!");
        }
    }
}