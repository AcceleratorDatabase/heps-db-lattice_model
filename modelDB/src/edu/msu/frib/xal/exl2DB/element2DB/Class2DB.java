/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.msu.frib.xal.exl2DB.element2DB;

import edu.msu.frib.xal.exl2DB.tools.DBTools;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.openepics.model.api.BeamlineSequenceAPI;
import org.openepics.model.api.ElementTypeAPI;
import org.openepics.model.entity.BeamlineSequence;
import org.openepics.model.entity.ElementType;

/**
 *
 * @author lv
 * @author chu
 */
public class Class2DB {

    public String filePath;

    public Class2DB() {
    }

    public Class2DB(String path) {
        filePath = path;
    }

    public void insertDB() {
        Connection conn = null;
        Statement state = null;
        ResultSet rs = null;

        try {
            conn = DBTools.getConnection();
            state = conn.createStatement();
            conn.setAutoCommit(false);

            Data2Class d2c = new Data2Class(this.filePath);
            ArrayList dataClsList = d2c.getClsData();
            Iterator it = dataClsList.iterator();

            Map<String, Integer> blseqs = new HashMap();
            Map<String, Integer> ele_types = new HashMap();

            while (it.hasNext()) {
                String sql = "insert into element values()";
                state.executeUpdate(sql, Statement.RETURN_GENERATED_KEYS);
                rs = state.getGeneratedKeys();
                rs.next();
                int element_id = rs.getInt(1);
                //System.out.println("++++++++"+id.getInt(1));
                ArrayList rowClsList = (ArrayList) it.next();
                Iterator it1 = rowClsList.iterator();
                while (it1.hasNext()) {
                    CellProperty cellProp = (CellProperty) it1.next();
                    String tableName = cellProp.getTableName();
                    if ("element_prop".equals(tableName)) {
                        String categorySql = null;
                        String valueSql = null;
                        String category = cellProp.getCategory();
                        Object value = cellProp.getValue();
                        if (!("".equals(category) || (category == null))) {
                            categorySql = "\"" + category + "\"";
                        }
                        if (!("".equals(value) || (value == null))) {
                            valueSql = "\"" + value + "\"";
                        }
                        String sql1 = "insert into element_prop(element_id,prop_category,element_prop_name,"
                                + cellProp.getType() + ") values(" + element_id + "," + categorySql
                                + "," + "\"" + cellProp.getName() + "\"" + "," + valueSql + ")";
                       // System.out.println(sql1);
                        state.executeUpdate(sql1);
                    } else if ("element".equals(tableName)) {
                        String valueSql1 = null;
                        Object value1 = cellProp.getValue();
                        if (!("".equals(value1) || (value1 == null))) {
                            valueSql1 = "\"" + value1 + "\"";
                        }
                        String sql2 = "update element set " + cellProp.getName()
                                + "=" + valueSql1 + " where element_id=" + element_id;
                       // System.out.println(sql2);
                        state.executeUpdate(sql2);
                    } else if ("beamline_sequence".equals(tableName)) {
                        String sequence_name = cellProp.getValue().toString();
                        BeamlineSequence bls = BeamlineSequenceAPI.getSequenceByName(sequence_name);
                        if (bls == null) {
                            boolean isRead = false;
                            if (!blseqs.isEmpty()) {
                                Iterator seqIt = blseqs.entrySet().iterator();
                                while (seqIt.hasNext()) {
                                    Map.Entry entry = (Map.Entry) seqIt.next();
                                  
                                    if (entry.getKey().toString().equals(sequence_name)) {
                                        isRead = true;
                                    }
                                }
                            }
                            if (isRead) {
                                String sql10 = "update element set beamline_sequence_id="
                                        + blseqs.get(sequence_name) + " where element_id=" + element_id;
                                state.executeUpdate(sql10);
                            } else {
                                String sql3 = "insert into beamline_sequence(sequence_name) values("
                                        + "\"" + sequence_name + "\"" + ")";
                                state.executeUpdate(sql3, Statement.RETURN_GENERATED_KEYS);
                                rs = state.getGeneratedKeys();
                                rs.next();
                                int beamline_sequence_id = rs.getInt(1);

                                String sql4 = "update element set beamline_sequence_id="
                                        + beamline_sequence_id + " where element_id=" + element_id;
                                state.executeUpdate(sql4);

                                blseqs.put(sequence_name, beamline_sequence_id);
                            }

                        } else {
                            String sql5 = "update element set beamline_sequence_id="
                                    + bls.getBeamlineSequenceId() + " where element_id=" + element_id;
                            state.executeUpdate(sql5);
                        }
                    } else if ("element_type".equals(tableName)) {
                        String element_type_value = cellProp.getValue().toString();
                        if ((!"".equals(element_type_value)) && element_type_value != null) {
                            ElementType et = ElementTypeAPI.getElementTypeByType(element_type_value);
                            if (et == null) {
                                boolean isRead = false;
                                if (!ele_types.isEmpty()) {
                                    Iterator etIt = ele_types.entrySet().iterator();
                                    while (etIt.hasNext()) {
                                        Map.Entry entry = (Map.Entry) etIt.next();
                                       
                                        if (entry.getKey().toString().equals(element_type_value)) {
                                            isRead = true;
                                        }
                                    }

                                }
                                if (isRead) {
                                    String sql12 = "update element set element_type_id="
                                            + ele_types.get(element_type_value) + " where element_id=" + element_id;
                                    state.executeUpdate(sql12);
                                } else {
                                    String sql13 = "insert into element_type(element_type) values("
                                            + "\"" + element_type_value + "\"" + ")";
                                    state.executeUpdate(sql13, Statement.RETURN_GENERATED_KEYS);
                                    rs = state.getGeneratedKeys();
                                    rs.next();
                                    int element_type_id = rs.getInt(1);

                                    String sql14 = "update element set element_type_id="
                                            + element_type_id + " where element_id=" + element_id;
                                    state.executeUpdate(sql14);
                                    
                                    ele_types.put(element_type_value, element_type_id);
                                }

                            } else {
                                String sql8 = "update element set element_type_id="
                                        + et.getElementTypeId() + " where element_id=" + element_id;
                                state.executeUpdate(sql8);
                            }
                        }
                    }

                }
                Timestamp tt = new Timestamp(new Date().getTime());
                String sql9 = "update element set created_by=" + "\"" + System.getProperty("user.name") + "\""
                        + "," + "dx=0,dy=0,dz=0,pitch=0,yaw=0,roll=0" + ","
                        + "insert_date=" + "\"" + tt + "\""
                        + " where element_id=" + element_id;
                state.executeUpdate(sql9);
                // System.out.println(sql9);
               


            }
            conn.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBTools.closeResultSet(rs);
            // DBTools.closeResultSet(rs2);
            DBTools.closeStatement(state);
            DBTools.closeConnection(conn);
        }
    }
}
