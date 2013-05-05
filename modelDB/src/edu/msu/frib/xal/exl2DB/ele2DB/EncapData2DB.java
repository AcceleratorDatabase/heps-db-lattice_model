/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.msu.frib.xal.exl2DB.ele2DB;

import edu.msu.frib.xal.exl2DB.lat_mod2DB.ReadComSheet;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.Statement;
import edu.msu.frib.xal.exl2DB.tools.DBTools;
import edu.msu.frib.xal.exl2DB.tools.MyComparator;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.apache.poi.ss.usermodel.Workbook;
import org.openepics.model.api.BeamlineSequenceAPI;
import org.openepics.model.api.ElementAPI;
import org.openepics.model.api.ElementTypeAPI;
import org.openepics.model.api.LatticeAPI;
import org.openepics.model.entity.BeamlineSequence;
import org.openepics.model.entity.Element;
import org.openepics.model.entity.ElementType;
import org.openepics.model.entity.Lattice;

/**
 *
 * @author lv
 * @author chu
 */
public class EncapData2DB {

    public static void instDB(Workbook wb, String sheetName, String latticeName) {
        // if (latticeName == null || "".equals(latticeName)) {
        //      System.out.println("Please insert the Lattice name!");
        //  } else {

        LatticeAPI latticeAPI = new LatticeAPI();
        Lattice l = latticeAPI.getLatticeByName(latticeName);
        // if (l != null) {
        //      System.out.println("The Lattice " + latticeName + " is already in the database! Please don't insert repeatedly!");
        //   }
        if (l == null) {
            System.out.println("The Lattice " + latticeName + " doesn't exitst in the database! Please insert it first!");
        } else {
            Connection conn = null;
            PreparedStatement state = null;
            PreparedStatement propState = null;
            PreparedStatement eState = null;
            PreparedStatement sState = null;
            ResultSet rs = null;

            boolean propStateSign = false;
            boolean eStateSign = false;
            boolean sStateSign = false;
            try {
                conn = (Connection) DBTools.getConnection();           
                conn.setAutoCommit(false);
                //int lattice_id = latticeAPI.setLattice(latticeName, null);
                int lattice_id = l.getLatticeId();
                ArrayList eleNameList = ReadComSheet.getColList(wb, sheetName, "Eng_name", "Physical label");
                ArrayList sequenceList = ReadComSheet.getColList(wb, sheetName, "Section", "Physical Label");
                ArrayList eleTypeList = ReadComSheet.getColList(wb, sheetName, "XAL_KeyWord", "Physical Label");

                //key:element_id value:element/s
                HashMap<Integer, Double> hMap = new HashMap<Integer, Double>();


                ArrayList encapDataList = DataEncap.getEncapData(wb, sheetName);

                int t = 0;
                int p_sign = 0;
                int e_sign = 0;
                // System.out.println(eleNameList.size()+"**************"+sequenceList.size()+"*******"+eleTypeList.size()+"*********"+encapDataList.size());
                if (eleNameList.size() == sequenceList.size() && sequenceList.size() == eleTypeList.size() && eleTypeList.size() == encapDataList.size()) {

                    Iterator it = encapDataList.iterator();
                    while (it.hasNext()) {
                        ArrayList rowClsList = (ArrayList) it.next();

                        String sequence_name = (String) sequenceList.get(t);
                        BeamlineSequenceAPI beamlineSequenceAPI = new BeamlineSequenceAPI();
                        BeamlineSequence bls = beamlineSequenceAPI.getSequenceByName(sequence_name);

                        String ele_type = (String) eleTypeList.get(t);
                        ElementTypeAPI elementTypeAPI = new ElementTypeAPI();
                        ElementType et = elementTypeAPI.getElementTypeByType(ele_type);

                        if (bls == null) {
                            System.out.println("Warning:The sequence " + sequence_name + " doesn't exist!");
                        } else {
                            if (et == null) {
                                System.out.println("Warning:The element_type " + ele_type + " doesn't exist!");
                            } else {

                                int element_id;
                                String ele_name = (String) eleNameList.get(t);
                                ElementAPI elementAPI = new ElementAPI();
                                Element e = elementAPI.getElementByName(ele_name);

                                if (e != null && e.getBeamlineSequenceId().getSequenceName().equals(sequence_name)) {
                                    System.out.println("Warning:The element " + ele_name + " of " + sequence_name + " is already in the database! Please don't insert repeatedly!");
                                } else {
                                    e_sign++;

                                    String sql = "insert into element values()";
                                    state = (PreparedStatement) conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                                    state.executeUpdate();
                                    rs = state.getGeneratedKeys();
                                    rs.next();
                                    element_id = rs.getInt(1);

                                    java.util.Date utilDate = new java.util.Date();
                                    java.sql.Date date = new java.sql.Date(utilDate.getTime());
                                    String sql1 = "update element set beamline_sequence_id=?, element_type_id=?, created_by=?, insert_date=?, dx=?, dy=?, dz=?,pitch=?,yaw=?,roll=? where element_id=?";
                                    if (e_sign == 1) {
                                        eState = (PreparedStatement) conn.prepareStatement(sql1);
                                    }
                                    eState.setInt(1, bls.getBeamlineSequenceId());
                                    eState.setInt(2, et.getElementTypeId());
                                    eState.setString(3, System.getProperty("user.name"));
                                    eState.setDate(4, date);
                                    eState.setDouble(5, 0);
                                    eState.setDouble(6, 0);
                                    eState.setDouble(7, 0);
                                    eState.setDouble(8, 0);
                                    eState.setDouble(9, 0);
                                    eState.setDouble(10, 0);
                                    eState.setInt(11, element_id);
                                    eState.addBatch();

                                    eStateSign = true;


                                    Iterator it1 = rowClsList.iterator();

                                    while (it1.hasNext()) {
                                        ElementCell cellProp = (ElementCell) it1.next();
                                        String tableName = cellProp.getTableName();

                                        if ("element_prop".equals(tableName)) {

                                            Object value = cellProp.getValue();

                                            if (!"".equals(value) && value != null) {
                                                p_sign++;
                                                String sql2 = "insert into element_prop values()";
                                                state = (PreparedStatement) conn.prepareStatement(sql2, Statement.RETURN_GENERATED_KEYS);
                                                state.executeUpdate();
                                                rs = state.getGeneratedKeys();
                                                rs.next();
                                                int element_prop_id = rs.getInt(1);

                                                String category = cellProp.getCategory();


                                                String sql3 = "update element_prop set element_id=?,prop_category=?,element_prop_name=?,lattice_id=? where element_prop_id=?";
                                                if ("".equals(category)) {
                                                    category = null;
                                                }
                                                if ("".equals(value)) {
                                                    value = null;
                                                }
                                                if (p_sign == 1) {
                                                    propState = (PreparedStatement) conn.prepareStatement(sql3);
                                                }
                                                propState.setInt(1, element_id);
                                                propState.setString(2, category);
                                                propState.setString(3, cellProp.getName());
                                                propState.setInt(4, lattice_id);
                                                propState.setInt(5, element_prop_id);
                                                propState.addBatch();

                                                propStateSign = true;

                                                //System.out.println(cellProp.getType()+"******"+value);
                                                String sql4 = "update element_prop set " + cellProp.getType() + "=? where element_prop_id=?";
                                                state = (PreparedStatement) conn.prepareStatement(sql4);
                                                state.setObject(1, value);
                                                state.setInt(2, element_prop_id);
                                                state.executeUpdate();

                                            }
                                        } else if ("element".equals(tableName)) {
                                            Object value1 = cellProp.getValue();
                                            if ("".equals(value1)) {
                                                value1 = null;
                                            }
                                            String sql5 = "update element set " + cellProp.getName() + "=? where element_id=?";
                                            state = (PreparedStatement) conn.prepareStatement(sql5);
                                            state.setObject(1, value1);
                                            state.setInt(2, element_id);
                                            state.executeUpdate();

                                            if (cellProp.getName().equals("s")) {
                                                hMap.put(element_id, Double.valueOf(cellProp.getValue().toString()));
                                            }

                                        }
                                    }
                                }
                            }
                        }
                        t++;
                    }
                }

                int i = 0;
                if (!hMap.isEmpty()) {
                    List mapList = new ArrayList(hMap.entrySet());
                    Collections.sort(mapList, new MyComparator());
                    Iterator iter = mapList.iterator();
                    while (iter.hasNext()) {
                        i++;
                        String sql6 = "update element set element_order=? where element_id=?";
                        if (i == 1) {
                            sState = (PreparedStatement) conn.prepareStatement(sql6);
                        }
                        sState.setInt(1, i);
                        Map.Entry entry = (Map.Entry) iter.next();
                        sState.setInt(2, Integer.parseInt(entry.getKey().toString()));
                        sState.addBatch();

                        sStateSign = true;
                    }
                }
                if (propStateSign) {
                    propState.executeBatch();
                }
                if (eStateSign) {
                    eState.executeBatch();
                }
                if (sStateSign) {
                    sState.executeBatch();
                }
                conn.commit();
                conn.setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                if (rs != null) {
                    DBTools.closeResultSet(rs);
                }
                DBTools.closePreparedStatement(state);
                DBTools.closePreparedStatement(eState);
                DBTools.closePreparedStatement(sState);
                DBTools.closePreparedStatement(propState);
                DBTools.closeConnection(conn);
            }
        }
    }
    //  }
}
