/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.msu.frib.xal.model2DB;

import java.util.Iterator;
import java.util.List;
import xal.model.IElement;
import xal.model.probe.EnvelopeProbe;
import xal.model.probe.traj.EnvelopeTrajectory;
import xal.sim.scenario.Scenario;
import xal.smf.AcceleratorNode;
import xal.smf.AcceleratorSeq;
import xal.smf.impl.qualify.NotTypeQualifier;
import xal.smf.impl.qualify.OrTypeQualifier;

/**
 *
 * @author chu
 */
public class XalModel2DB extends Model2DB {
    String MODEL = "XAL";
    
    AcceleratorSeq accSeq;
    Scenario model;
    String sequenceName;
    
    public XalModel2DB(){
        setModelName(MODEL);        
    }
    
    public XalModel2DB(AcceleratorSeq accSeq, Scenario model) {
        setModelName(MODEL);
        this.accSeq = accSeq;
        this.model = model;
        sequenceName = accSeq.getId();
        modelData();
    }
    
    private void modelData() {
        OrTypeQualifier otq = new OrTypeQualifier();
        otq.or("Bnch");
        otq.or("CAV");
        otq.or("RfCavity");
        NotTypeQualifier ntq = new NotTypeQualifier(otq);
        List<AcceleratorNode> nodes = accSeq.getAllNodesWithQualifier(ntq);
        
        EnvelopeProbe probe = (EnvelopeProbe) model.getProbe();
        EnvelopeTrajectory traj = (EnvelopeTrajectory) probe.getTrajectory();
        
        Iterator<AcceleratorNode> it = nodes.iterator();
        while (it.hasNext()) {
            AcceleratorNode node = it.next();
            List<IElement> states = model.elementsMappedTo(node);
            Iterator it1 = states.iterator();
            while (it1.hasNext()) {
                Object elem = it1.next();
            }
        }
    }
    
    public void setSequence(AcceleratorSeq seq) {
        this.accSeq = seq;
    }
    
    public void setSequenceName(String seqName) {
        sequenceName = seqName;
    }
    
    public void setModel(Scenario model) {
        this.model = model;
    }
}
