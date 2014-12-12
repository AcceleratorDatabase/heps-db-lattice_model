/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.openepics.model.extraEntity;

import java.util.Collection;
import java.util.HashMap;
import org.openepics.model.entity.BeamParameterProp;

/**
 *
 * @author lv
 */
public class BeamParams {
    private String particleName;
    private HashMap<Integer,Collection<BeamParameterProp>> beamParameterPropCollections = new HashMap<>();
    private int max_slice_id = 0;

    public String getParticleName() {
        return particleName;
    }

    public void setParticleName(String particleName) {
        this.particleName = particleName;
    }

    public Collection<BeamParameterProp> getBeamParameterPropCollection(int slice_id) {
        return beamParameterPropCollections.get(slice_id);
    }

    public void setBeamParameterPropCollection(Collection<BeamParameterProp> beamParameterPropCollection, int slice_id) {
        this.beamParameterPropCollections.put(slice_id, beamParameterPropCollection);
        if (slice_id > max_slice_id)
            max_slice_id = slice_id;
    }
    
    /**
     * Get the maximum slice ID
     * @return maximum slice ID
     */
    public int getMaxSliceId() {
        return max_slice_id;
    }
}
