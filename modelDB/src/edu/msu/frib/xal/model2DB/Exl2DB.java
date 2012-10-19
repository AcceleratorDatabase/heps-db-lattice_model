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
    //这些label的值不写到数据库中
    public static String[] WNDS = {"xal_label", "id", "system", "subsystem", "energy", "suml",
        "align_x", "align_y", "align_z", "align_pitch", "align_yaw", "align_roll",
        "magnet_x_coor", "magnet_y_coor", "magnet_z_coor", "magnet_x_angle", "magnet_y_angle", "magnet_z_angle"};
    //这些label写到element表中,其中"sequence_id"填到beamlineSequence里，"type"填到elementType里
    public static String[] ToElement = {"element_name", "sequence_id", "pos", "s", "len", "type"};
    //这些是magnet类型，但是在名称上没有体现，所以要进行判断
    public static String[] MagCat = {"integrated_field_bl", "integrated_field_gradient_gl", "field_gradient_g"};
    //这些要特殊处理，x y分别代表两个方向的孔径，如果是圆形的话则只有aperture_x，椭圆的话才有两个值
    public static String[] Aperture = {"aperture_x", "x_size", "y_size"};

    @SuppressWarnings("empty-statement")
    public static void write2DB(String path) {
        Lattice lattice = new Lattice();

        //存放已经读取到的BeamlineSequence，key是name，value是BeamlineSequence的class
        HashMap sequences = new HashMap();
        //存放已经读取到的ElementType，key是name，value是ElementType的class
        HashMap elts = new HashMap();

        ArrayList contents = ReadExl.readExcel(path);
        Iterator it = contents.iterator();
        em.getTransaction().begin();
        while (it.hasNext()) {
            HashMap map = (HashMap) it.next();    //取出每个element的具体内容
            //如果数据库中已经存在就不在插入，如果不存在再执行以下操作
            if (ElementAPI.getElementByName(map.get("element_name").toString()) == null) {
                Element e = new Element();
                e.setLatticeId(lattice);
                Iterator it1 = map.entrySet().iterator();
                while (it1.hasNext()) {
                    Map.Entry entry = (Map.Entry) it1.next();
                    String key = entry.getKey().toString();

                    boolean isWNDS = Tool.strContain(key, WNDS);
                    boolean is2element = Tool.strContain(key, ToElement);

                    if (isWNDS) {  //什么都不做，不需要填到数据库中
                    } else if (is2element) {   //需要填到element表中
                        
                    /*填到beamlineSequence里，首先判断数据库中是否已经存在，如果存在，就直接set;
                         如果不存在，再判断sequences里是否存在，如果存在，就说明已经读过，不需要再读了
                         如果sequences不存在，再新建一个，并加到sequences里,sequences的key就是sequences_name*/
                        if ("sequence_id".equals(key)) {
                            BeamlineSequence blsDB = BeamlineSequenceAPI.getSequenceByName(entry.getValue().toString());
                            //数据库中不存在
                            if (blsDB == null) {
                                boolean isExist = false;        //表里是否存在
                                //判断表里是否已经读到了
                                if (!sequences.isEmpty()) {
                                    Iterator seqIt = sequences.entrySet().iterator();

                                    while (seqIt.hasNext()) {
                                        Map.Entry seqEny = (Map.Entry) seqIt.next();
                                        if (seqEny.getKey().toString().equals(entry.getValue().toString())) {
                                            isExist = true;
                                            e.setSequenceId((BeamlineSequence) seqEny.getValue());
                                        }
                                    }

                                }//判断表里是否已经读到了

                                //表里不存在，需要新建一个
                                if (!isExist) {
                                    BeamlineSequence bls = new BeamlineSequence();
                                    bls.setSequenceName(entry.getValue().toString());
                                    e.setSequenceId(bls);
                                    em.persist(bls);
                                    sequences.put(entry.getValue().toString(), bls);
                                }//表里不存在，需要新建一个
                            } /////////////////数据库中不存在
                            //数据库中存在
                            else {
                                e.setSequenceId(blsDB);
                            }
                        } //填elementType里,与上面判断相同
                        else if ("type".equals(key)) {
                            ElementType eltDB = ElementTypeAPI.getElementTypeByType(entry.getValue().toString());
                            //数据库中不存在
                            if (eltDB == null) {
                                boolean isExist1 = false;        //表里是否存在
                                //判断表里是否已经读到了
                                if (!elts.isEmpty()) {
                                    Iterator eltIt = elts.entrySet().iterator();

                                    while (eltIt.hasNext()) {
                                        Map.Entry eltEny = (Map.Entry) eltIt.next();
                                        if (eltEny.getKey().toString().equals(entry.getValue().toString())) {
                                            isExist1 = true;
                                            e.setElementTypeId((ElementType) eltEny.getValue());
                                        }
                                    }
                                }//判断表里是否已经读到了

                                //表里不存在，需要新建一个
                                if (!isExist1) {
                                    ElementType elt = new ElementType();
                                    elt.setElementType(entry.getValue().toString());
                                    e.setElementTypeId(elt);
                                    em.persist(elt);
                                    elts.put(entry.getValue().toString(), elt);
                                }//表里不存在，需要新建一个
                            } /////////////////数据库中不存在
                           
                            //数据库中存在
                            else {
                                e.setElementTypeId(eltDB);
                            }
                        } //填到element中
                        else {
                            //利用反射机制，调用Element对应的set方法，这些都是element的属性值
                            String fieldName;
                            String setter;
                            int unlPos = key.indexOf("_");    //判断下划线的位置
                            if (unlPos > -1) {
                                //将element_name变成setElementName的形式，将len变成setLen
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
                        //这些需要填到element_prop表中
                        boolean ifPerst = true;    //针对aperture的特殊情况，防止把不需要的ep持久化
                        ElementProp ep = new ElementProp();
                        int unlPos1 = key.indexOf("_");
                        //如果含有下划线，就将下划线前的填到category里，后的填到name里；否则直接填到name里
                        //如果是上面magCat里面的，不按规则
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
