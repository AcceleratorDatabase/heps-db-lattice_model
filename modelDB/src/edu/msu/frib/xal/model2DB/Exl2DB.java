/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.msu.frib.xal.model2DB;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnit;
import org.openepics.model.api.BeamlineSequenceAPI;
import org.openepics.model.api.ElementAPI;
import org.openepics.model.api.ElementPropAPI;
import org.openepics.model.api.ElementTypeAPI;
import org.openepics.model.entity.BeamlineSequence;
import org.openepics.model.entity.Element;
import org.openepics.model.entity.ElementProp;
import org.openepics.model.entity.ElementType;
import org.openepics.model.entity.Lattice;

/**
 *
 * @author lv
 */
public class Exl2DB {

    @PersistenceUnit
    static EntityManagerFactory emf = Persistence.createEntityManagerFactory("modelAPIPU");
    static EntityManager em = emf.createEntityManager();
    @PersistenceContext
   
    public static String[] WNDS = {"xal_label", "id", "system", "subsystem", "energy", "suml",
        "align_x", "align_y", "align_z", "align_pitch", "align_yaw", "align_roll",
        "magnet_x_coor", "magnet_y_coor", "magnet_z_coor", "magnet_x_angle", "magnet_y_angle", "magnet_z_angle"};
    
    public static String[] ToElement = {"element_name", "sequence_id", "pos", "s", "len", "type"};
    
    public static String[] MagCat = {"integrated_field_bl", "integrated_field_gradient_gl", "field_gradient_g"};
  
    public static String[] Aperture = {"aperture_x", "x_size", "y_size"};

    @SuppressWarnings("empty-statement")
    public static void write2DB(String path) {
        Lattice lattice = new Lattice();

    
        HashMap sequences = new HashMap();
       
        HashMap elts = new HashMap();

        ArrayList contents = ReadExl.readExcel(path);
        Iterator it = contents.iterator();
        em.getTransaction().begin();
        while (it.hasNext()) {
            HashMap map = (HashMap) it.next();    
          
            if (ElementAPI.getElementByName(map.get("element_name").toString()) == null) {
                Element e = new Element();
//                e.setLatticeId(lattice);
                Iterator it1 = map.entrySet().iterator();
                while (it1.hasNext()) {
                    Map.Entry entry = (Map.Entry) it1.next();
                    String key = entry.getKey().toString();

                    boolean isWNDS = Tool.strContain(key, WNDS);
                    boolean is2element = Tool.strContain(key, ToElement);

                    if (isWNDS) {  
                    } else if (is2element) {   
                        
                   
                        if ("sequence_id".equals(key)) {
                            BeamlineSequence blsDB = BeamlineSequenceAPI.getSequenceByName(entry.getValue().toString());
                           
                            if (blsDB == null) {
                                boolean isExist = false;        
                                
                                if (!sequences.isEmpty()) {
                                    Iterator seqIt = sequences.entrySet().iterator();

                                    while (seqIt.hasNext()) {
                                        Map.Entry seqEny = (Map.Entry) seqIt.next();
                                        if (seqEny.getKey().toString().equals(entry.getValue().toString())) {
                                            isExist = true;
                                            e.setBeamlineSequenceId((BeamlineSequence) seqEny.getValue());
                                        }
                                    }

                                }

                               
                                if (!isExist) {
                                    BeamlineSequence bls = new BeamlineSequence();
                                    bls.setSequenceName(entry.getValue().toString());
                                    e.setBeamlineSequenceId(bls);
                                    em.persist(bls);
                                    sequences.put(entry.getValue().toString(), bls);
                                }
                            } 
                           
                            else {
                                e.setBeamlineSequenceId(blsDB);
                            }
                        } 
                        else if ("type".equals(key)) {
                            ElementType eltDB = ElementTypeAPI.getElementTypeByType(entry.getValue().toString());
                           
                            if (eltDB == null) {
                                boolean isExist1 = false;        
                                
                                if (!elts.isEmpty()) {
                                    Iterator eltIt = elts.entrySet().iterator();

                                    while (eltIt.hasNext()) {
                                        Map.Entry eltEny = (Map.Entry) eltIt.next();
                                        if (eltEny.getKey().toString().equals(entry.getValue().toString())) {
                                            isExist1 = true;
                                            e.setElementTypeId((ElementType) eltEny.getValue());
                                        }
                                    }
                                }

                               
                                if (!isExist1) {
                                    ElementType elt = new ElementType();
                                    elt.setElementType(entry.getValue().toString());
                                    e.setElementTypeId(elt);
                                    em.persist(elt);
                                    elts.put(entry.getValue().toString(), elt);
                                }
                            } 
                           
                            
                            else {
                                e.setElementTypeId(eltDB);
                            }
                        }
                        else {
                            
                            String fieldName;
                            String setter;
                            int unlPos = key.indexOf("_");   
                            if (unlPos > -1) {
                                
                                setter = "set" + key.substring(0, 1).toUpperCase() + key.substring(1, unlPos)
                                        + key.substring(unlPos + 1).substring(0, 1).toUpperCase()
                                        + key.substring(unlPos + 2);
                                fieldName = key.substring(0, unlPos) + key.substring(unlPos + 1).substring(0, 1).toUpperCase()
                                        + key.substring(unlPos + 2);
                                     } else {
                                setter = "set" + key.substring(0, 1).toUpperCase() + key.substring(1);
                                fieldName = key;
                            }
                            Field field = null;
                            try {
                                field = e.getClass().getDeclaredField(fieldName);
                            } catch (NoSuchFieldException | SecurityException ex) {
                                Logger.getLogger(Exl2DB.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            Method method;
                            if (field.getType().equals(java.lang.String.class)) {
                                try {
                                    method = e.getClass().getMethod(setter, new Class[]{String.class});
                                    method.invoke(e, entry.getValue().toString());
                                } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException ex) {
                                    Logger.getLogger(Exl2DB.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            } else if (field.getType().equals(java.lang.Double.class)) {
                                try {
                                    method = e.getClass().getMethod(setter, new Class[]{Double.class});
                                    if (!"".equals(entry.getValue())) {
                                        method.invoke(e, Double.parseDouble(entry.getValue().toString()));
                                    }
                                } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException ex) {
                                    Logger.getLogger(Exl2DB.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            }else if (field.getType().equals(java.lang.Integer.class)) {
                                try {
                                    method = e.getClass().getMethod(setter, new Class[]{Integer.class});
                                    if (!"".equals(entry.getValue())) {
                                        method.invoke(e, Integer.parseInt(entry.getValue().toString()));
                                    }
                                } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException ex) {
                                    Logger.getLogger(Exl2DB.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            }else{
                                System.out.println("The field is no String,Double,Integer type!");
                            }
                        }
                    } else {
                        
                        boolean ifPerst = true;    
                        ElementProp ep = new ElementProp();
                        int unlPos1 = key.indexOf("_");
                       
                        if (Tool.strContain(key, MagCat)) {
                            ep = ElementPropAPI.setElementProperty(e, "magnet", key, entry.getValue());
                        } else if (Tool.strContain(key, Aperture)) {
                            if (!(key.equals("aperture_x")) && "".equals(map.get("aperture_x"))) {
                                ep = ElementPropAPI.setElementProperty(e, "aperture", key.substring(0, unlPos1), entry.getValue());
                            } else if (key.equals("aperture_x") && (!"".equals(map.get("aperture_x")))) {
                                ep = ElementPropAPI.setElementProperty(e, "aperture", "x", entry.getValue());
                            } else {
                                ifPerst = false;
                            }

                        } else {
                            if (unlPos1 > -1) {
                                String category = key.substring(0, unlPos1);
                                String name = key.substring(unlPos1 + 1);
                                ep = ElementPropAPI.setElementProperty(e, category, name, entry.getValue());

                            } else {
                                ep = ElementPropAPI.setElementProperty(e, null, key, entry.getValue());
                            }
                        }
                        if (ifPerst) {
                            em.persist(ep);
                        }
                    }
                    ElementAPI.setEleInsErr(e, 0, 0, 0, 0, 0, 0);
                    em.persist(e);
                }
            }
        }
        em.persist(lattice);

        em.getTransaction().commit();

    }
}
