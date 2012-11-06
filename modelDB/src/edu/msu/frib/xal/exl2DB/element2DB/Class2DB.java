/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.msu.frib.xal.exl2DB.element2DB;

import edu.msu.frib.xal.exl2DB.tools.DBTools;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.openepics.model.api.BeamlineSequenceAPI;
import org.openepics.model.api.ElementAPI;
import org.openepics.model.api.ElementTypeAPI;
import org.openepics.model.entity.BeamlineSequence;
import org.openepics.model.entity.Element;
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
        PreparedStatement state = null;
        ResultSet rs = null;

        try {
            conn = DBTools.getConnection();
            conn.setAutoCommit(false);
            Data2Class d2c = new Data2Class(this.filePath);

            String sqll = "insert into lattice(lattice_name,  created_by, create_date)  values(?,?,?);";
            state = conn.prepareStatement(sqll, Statement.RETURN_GENERATED_KEYS);
            state.setString(1, "frib");
            state.setString(2, System.getProperty("user.name"));
            java.util.Date utilDate = new java.util.Date();
            java.sql.Date date = new java.sql.Date(utilDate.getTime());
            state.setDate(3, date);
            state.executeUpdate();

            rs = state.getGeneratedKeys();
            rs.next();
            int lattice_id = rs.getInt(1);

            Map<String, Integer> blseqs = new HashMap();
            Map<String, Integer> ele_types = new HashMap();

            int ele_name_col = new ReadEleExl(this.filePath).getEleNameCol();

            ArrayList dataClsList = d2c.getClsData();
            Iterator it = dataClsList.iterator();

            while (it.hasNext()) {
                ArrayList rowClsList = (ArrayList) it.next();
                int element_id;
                CellProperty cellP = (CellProperty) rowClsList.get(ele_name_col);
                String ele_name = cellP.getValue().toString();

                Element e = ElementAPI.getElementByName(ele_name);

                if (e != null) {
                    element_id = e.getElementId();

                } else {
                    String sql = "insert into element values()";
                    state = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                    state.executeUpdate();
                    rs = state.getGeneratedKeys();
                    rs.next();
                    element_id = rs.getInt(1);
                }

                Iterator it1 = rowClsList.iterator();
                while (it1.hasNext()) {
                    CellProperty cellProp = (CellProperty) it1.next();
                    String tableName = cellProp.getTableName();
                    if ("element_prop".equals(tableName)) {
                        String category = cellProp.getCategory();
                        Object value = cellProp.getValue();
                        String sql1 = "insert into element_prop(element_id,prop_category,element_prop_name,"
                                + cellProp.getType() + "," + "lattice_id" + ") values (?,?,?,?,?)";
                        if ("".equals(category)) {
                            category = null;
                        }
                        if ("".equals(value)) {
                            value = null;
                        }
                        state = conn.prepareStatement(sql1);
                        state.setInt(1, element_id);
                        state.setString(2, category);
                        state.setString(3, cellProp.getName());
                        state.setObject(4, value);
                        state.setInt(5, lattice_id);
                        state.executeUpdate();
                    } else if ("element".equals(tableName)) {
                        if (e == null) {
                            Object value1 = cellProp.getValue();
                            if ("".equals(value1)) {
                                value1 = null;
                            }
                            String sql2 = "update element set " + cellProp.getName() + "=? where element_id=?";
                            state = conn.prepareStatement(sql2);
                            state.setObject(1, value1);
                            state.setInt(2, element_id);
                            state.executeUpdate();
                        }
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
                                        break;
                                    }
                                }
                            }
                            if (isRead) {
                                if (e == null) {

                                    String sql10 = "update element set beamline_sequence_id=? where element_id=?";
                                    state = conn.prepareStatement(sql10);
                                    state.setInt(1, blseqs.get(sequence_name));
                                    state.setInt(2, element_id);
                                    state.executeUpdate();
                                }

                            } else {

                                String sql3 = "insert into beamline_sequence(sequence_name) values(?)";
                                state = conn.prepareStatement(sql3, Statement.RETURN_GENERATED_KEYS);
                                state.setString(1, sequence_name);
                                state.executeUpdate();

                                rs = state.getGeneratedKeys();
                                rs.next();
                                int beamline_sequence_id = rs.getInt(1);
                                if (e == null) {

                                    String sql4 = "update element set beamline_sequence_id=? where element_id=?";
                                    state = conn.prepareStatement(sql4);
                                    state.setInt(1, beamline_sequence_id);
                                    state.setInt(2, element_id);
                                    state.executeUpdate();
                                }

                                blseqs.put(sequence_name, beamline_sequence_id);
                            }

                        } else {
                            if (e == null) {

                                String sql5 = "update element set beamline_sequence_id=? where element_id=?";
                                state = conn.prepareStatement(sql5);
                                state.setInt(1, bls.getBeamlineSequenceId());
                                state.setInt(2, element_id);
                                state.executeUpdate();
                            }
                        }
                    } else if ("element_type".equals(tableName)) {
                        if (e == null) {

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
                                                break;
                                            }
                                        }

                                    }
                                    if (isRead) {
                                        String sql12 = "update element set element_type_id=? where element_id=?";
                                        state=conn.prepareStatement(sql12);
                                        state.setInt(1, ele_types.get(element_type_value));
                                        state.setInt(2, element_id);
                                        state.executeUpdate();

                                    } else {

                                        String sql13 = "insert into element_type(element_type) values(?)";
                                        state = conn.prepareStatement(sql13, Statement.RETURN_GENERATED_KEYS);
                                        state.setString(1, element_type_value);
                                        state.executeUpdate();

                                        rs = state.getGeneratedKeys();
                                        rs.next();
                                        int element_type_id = rs.getInt(1);

                                        String sql14 = "update element set element_type_id=? where element_id=?";
                                        state = conn.prepareStatement(sql14);
                                        state.setInt(1, element_type_id);
                                        state.setInt(2, element_id);
                                        state.executeUpdate();
                                        ele_types.put(element_type_value, element_type_id);
                                    }

                                } else {

                                    String sql8 = "update element set element_type_id=? where element_id=?";
                                    state = conn.prepareStatement(sql8);
                                    state.setInt(1, et.getElementTypeId());
                                    state.setInt(2, element_id);
                                    state.executeUpdate();

                                }
                            }
                        }
                    }

                }
                if (e == null) {

                    String sql9 = "update element set created_by=?, dx=?, dy=?, dz=? where element_id=?";

                    state = conn.prepareStatement(sql9);
                    state.setString(1, System.getProperty("user.name"));
                    state.setDouble(2, 0);
                    state.setDouble(3, 0);
                    state.setDouble(4, 0);
                    state.setInt(5, element_id);
                    state.executeUpdate();

                    String sql15 = "update element set  pitch=?, yaw=?, roll=?, insert_date=? where element_id=?";
                    state = conn.prepareStatement(sql15);
                    state.setDouble(1, 0);
                    state.setDouble(2, 0);
                    state.setDouble(3, 0);
                    state.setDate(4, date);
                    state.setInt(5, element_id);
                    state.executeUpdate();
                }
            }
            conn.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (rs != null) {
                DBTools.closeResultSet(rs);
            }
            DBTools.closePreparedStatement(state);
            DBTools.closeConnection(conn);
        }
    }
}
