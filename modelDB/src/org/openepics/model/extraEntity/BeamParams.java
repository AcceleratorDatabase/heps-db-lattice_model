/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.openepics.model.extraEntity;

import java.util.Collection;
import org.openepics.model.entity.BeamParameterProp;

/**
 *
 * @author lv
 */
public class BeamParams {
    private String particleName;
    private Collection<BeamParameterProp> beamParameterPropCollection;

    public String getParticleName() {
        return particleName;
    }

    public void setParticleName(String particleName) {
        this.particleName = particleName;
    }

    public Collection<BeamParameterProp> getBeamParameterPropCollection() {
        return beamParameterPropCollection;
    }

    public void setBeamParameterPropCollection(Collection<BeamParameterProp> beamParameterPropCollection) {
        this.beamParameterPropCollection = beamParameterPropCollection;
    }
    
    
}
