/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.msu.frib.xal.model2DB;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.openepics.model.extraEntity.BeamParams;
import org.openepics.model.extraEntity.Device;
import org.openepics.model.entity.BeamParameterProp;
import org.openepics.model.entity.ElementProp;
import xal.model.IElement;
import xal.model.probe.EnvelopeProbe;
import xal.model.probe.traj.EnvelopeProbeState;
import xal.model.probe.traj.Trajectory;
import xal.sim.scenario.Scenario;
import xal.smf.AcceleratorNode;
import xal.smf.AcceleratorSeq;
import xal.smf.impl.Electromagnet;
import xal.smf.impl.RfCavity;
import xal.smf.impl.RfGap;
import xal.smf.impl.qualify.NotTypeQualifier;
import xal.smf.impl.qualify.OrTypeQualifier;
import xal.tools.beam.Twiss;

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
        Trajectory traj = probe.getTrajectory();
        
        ArrayList devices = new ArrayList();
        double length = 0.;
        
        Iterator<AcceleratorNode> it = nodes.iterator();
        while (it.hasNext()) {
            AcceleratorNode node = it.next();
            List<IElement> states = model.elementsMappedTo(node);
            Iterator<IElement> it1 = states.iterator();
            while (it1.hasNext()) {
                IElement elem = it1.next();
                EnvelopeProbeState state = (EnvelopeProbeState) traj.stateForElement(elem.getId());
                double s = state.getPosition();
                Twiss[] twiss = state.twissParameters();
                
                Device dev = new Device();
                dev.setElementName(state.getElementId());
                dev.setPos(s);
                dev.setElementType(node.getType());
                dev.setLen(elem.getLength());
                dev.setBeamlineSequenceName(sequenceName);
                
                ArrayList beamParameterPropCollection = new ArrayList();
                BeamParams beamParams = new BeamParams();
                ArrayList elementPropCollection = new ArrayList();
                
                if (s > length)
                    length = s;
                
                // beam parameters
                BeamParameterProp x = new BeamParameterProp();
                x.setPropertyName("x");
                x.setBeamParameterDouble(state.phaseMean().getx());
                x.setPropertyDatatype("double");
                x.setPropCategory("phaseMean");
                beamParameterPropCollection.add(x);
                BeamParameterProp xp = new BeamParameterProp();
                xp.setPropertyName("xp");
                xp.setBeamParameterDouble(state.phaseMean().getxp());
                xp.setPropertyDatatype("double");
                xp.setPropCategory("phaseMean");
                beamParameterPropCollection.add(xp);
                BeamParameterProp y = new BeamParameterProp();
                y.setPropertyName("y");
                y.setBeamParameterDouble(state.phaseMean().gety());
                y.setPropertyDatatype("double");
                y.setPropCategory("phaseMean");
                beamParameterPropCollection.add(y);
                BeamParameterProp yp = new BeamParameterProp();
                yp.setPropertyName("yp");
                yp.setBeamParameterDouble(state.phaseMean().getyp());
                yp.setPropertyDatatype("double");
                yp.setPropCategory("phaseMean");
                beamParameterPropCollection.add(yp);
                BeamParameterProp z = new BeamParameterProp();
                z.setPropertyName("z");
                z.setBeamParameterDouble(state.phaseMean().getz());
                z.setPropertyDatatype("double");
                z.setPropCategory("phaseMean");
                beamParameterPropCollection.add(z);
                BeamParameterProp zp = new BeamParameterProp();
                zp.setPropertyName("zp");
                zp.setBeamParameterDouble(state.phaseMean().getzp());
                zp.setPropertyDatatype("double");
                zp.setPropCategory("phaseMean");
                beamParameterPropCollection.add(zp);

                BeamParameterProp beta_x = new BeamParameterProp();
                beta_x.setPropertyName("x_beta");
                beta_x.setBeamParameterDouble(twiss[0].getBeta());
                beta_x.setPropertyDatatype("double");
                beta_x.setPropCategory("twiss");
                beamParameterPropCollection.add(beta_x);
                BeamParameterProp alpha_x = new BeamParameterProp();
                alpha_x.setPropertyName("x_alpha");
                alpha_x.setBeamParameterDouble(twiss[0].getAlpha());
                alpha_x.setPropertyDatatype("double");
                alpha_x.setPropCategory("twiss");
                beamParameterPropCollection.add(alpha_x);
                BeamParameterProp emit_x = new BeamParameterProp();
                emit_x.setPropertyName("x_emittance");
                emit_x.setBeamParameterDouble(twiss[0].getEmittance());
                emit_x.setPropertyDatatype("double");
                emit_x.setPropCategory("twiss");
                beamParameterPropCollection.add(emit_x);
                BeamParameterProp beta_y = new BeamParameterProp();
                beta_y.setPropertyName("y_beta");
                beta_y.setBeamParameterDouble(twiss[1].getBeta());
                beta_y.setPropertyDatatype("double");
                beta_y.setPropCategory("twiss");
                beamParameterPropCollection.add(beta_y);
                BeamParameterProp alpha_y = new BeamParameterProp();
                alpha_y.setPropertyName("y_alpha");
                alpha_y.setBeamParameterDouble(twiss[1].getAlpha());
                alpha_y.setPropertyDatatype("double");
                alpha_y.setPropCategory("twiss");
                beamParameterPropCollection.add(alpha_y);
                BeamParameterProp emit_y = new BeamParameterProp();
                emit_y.setPropertyName("y_emittance");
                emit_y.setBeamParameterDouble(twiss[1].getEmittance());
                emit_y.setPropertyDatatype("double");
                emit_y.setPropCategory("twiss");
                beamParameterPropCollection.add(emit_y);
                BeamParameterProp beta_z = new BeamParameterProp();
                beta_z.setPropertyName("z_beta");
                beta_z.setBeamParameterDouble(twiss[2].getBeta());
                beta_z.setPropertyDatatype("double");
                beta_z.setPropCategory("twiss");
                beamParameterPropCollection.add(beta_z);
                BeamParameterProp alpha_z = new BeamParameterProp();
                alpha_z.setPropertyName("z_alpha");
                alpha_z.setBeamParameterDouble(twiss[2].getAlpha());
                alpha_z.setPropertyDatatype("double");
                alpha_z.setPropCategory("twiss");
                beamParameterPropCollection.add(alpha_z);
                BeamParameterProp emit_z = new BeamParameterProp();
                emit_z.setPropertyName("z_emittance");
                emit_z.setBeamParameterDouble(twiss[2].getEmittance());
                emit_z.setPropertyDatatype("double");
                emit_z.setPropCategory("twiss");
                beamParameterPropCollection.add(emit_z);
                BeamParameterProp energy = new BeamParameterProp();
                energy.setPropertyName("W");
                energy.setBeamParameterDouble(state.getKineticEnergy());
                energy.setPropertyDatatype("double");
                energy.setPropCategory("location");
                beamParameterPropCollection.add(energy);

                beamParams.setBeamParameterPropCollection(beamParameterPropCollection);
                dev.setBeamParams(beamParams);
                
                // element properties
                switch (node.getType()) {
                    case "SOL":
                    case "QH":
                    case "QV":
                    case "DCH":
                    case "DCV":
                        ElementProp B = new ElementProp();
                        B.setElementPropName("magFld");
                        B.setElementPropDouble(((Electromagnet)node).getDfltField());
                        elementPropCollection.add(B);
                        break;
                    case "RG":
                        ElementProp ph = new ElementProp();
                        ph.setElementPropName("phase");
                        ph.setElementPropDouble(((RfCavity) (node.getParent())).getDfltAvgCavPhase());
                        elementPropCollection.add(ph);
                        ElementProp amp = new ElementProp();
                        amp.setElementPropName("amp");
                        amp.setElementPropDouble(((RfCavity) (node.getParent())).getDfltCavAmp());
                        elementPropCollection.add(amp);
                        break;
                }
                
                dev.setElementPropCollection(elementPropCollection);
                
                // add this device to the device collection
                devices.add(dev);
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
