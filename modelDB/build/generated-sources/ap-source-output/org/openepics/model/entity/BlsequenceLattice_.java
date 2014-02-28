package org.openepics.model.entity;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import org.openepics.model.entity.BeamlineSequence;
import org.openepics.model.entity.Lattice;

@Generated(value="EclipseLink-2.5.1.v20130918-rNA", date="2014-02-27T18:50:52")
@StaticMetamodel(BlsequenceLattice.class)
public class BlsequenceLattice_ { 

    public static volatile SingularAttribute<BlsequenceLattice, BeamlineSequence> beamlineSequenceId;
    public static volatile SingularAttribute<BlsequenceLattice, Integer> blsequenceLatticeId;
    public static volatile SingularAttribute<BlsequenceLattice, Integer> beamlineOrder;
    public static volatile SingularAttribute<BlsequenceLattice, Lattice> latticeId;

}