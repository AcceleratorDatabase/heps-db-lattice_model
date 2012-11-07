/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.msu.frib.xal.exl2DB.sequence2DB;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnit;
import org.openepics.model.api.BeamlineSequenceAPI;
import org.openepics.model.entity.BeamlineSequence;

/**
 *
 * @author lv
 * @author chu
 */
public class SeqMap2DB {

    @PersistenceUnit
    static EntityManagerFactory emf = Persistence.createEntityManagerFactory("modelAPIPU");
    static EntityManager em = emf.createEntityManager();
    @PersistenceContext
    public String filePath;

    public SeqMap2DB() {
    }

    public SeqMap2DB(String path) {
        filePath = path;
    }

    public void insertDB() {
        ArrayList mapList = new Data2Map(this.filePath).getMapData();
        Iterator it = mapList.iterator();
        while (it.hasNext()) {
            Map dataMap = (Map) it.next();
            String sequence_name = (String) dataMap.get("sequence_name");
            String first_ele_name = dataMap.get("first_element_name").toString();
            String last_ele_name = dataMap.get("last_element_name").toString();
            String pre_seq1 = dataMap.get("predecessor_sequence").toString();
            String pre_seq=pre_seq1;
            if("".equals(pre_seq1)||pre_seq==null||"null".equals(pre_seq)){
                pre_seq=null;
            }
            Double seq_length = Double.parseDouble(dataMap.get("sequence_length").toString());
            String seq_des = dataMap.get("sequence_description").toString();
            BeamlineSequence bs = BeamlineSequenceAPI.getSequenceByName(sequence_name);
            if (bs == null) {
                BeamlineSequenceAPI.setBeamlineSequence(sequence_name, first_ele_name,
                        last_ele_name, pre_seq, seq_length, seq_des);
            } else {
                em.getTransaction().begin();
                bs=em.find(BeamlineSequence.class, bs.getBeamlineSequenceId());
                bs.setFirstElementName(first_ele_name);
                bs.setLastElementName(last_ele_name);
                bs.setPredecessorSequence(pre_seq);
                bs.setSequenceLength(seq_length);
                bs.setSequenceDescription(seq_des);
                em.getTransaction().commit();
            }
        }
        em.close();
        emf.close();
    }
}
