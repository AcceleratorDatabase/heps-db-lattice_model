package org.openepics.model.entity;

import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import org.openepics.model.entity.BlsequenceLattice;
import org.openepics.model.entity.Element;

@Generated(value="EclipseLink-2.5.1.v20130918-rNA", date="2014-02-27T18:50:52")
@StaticMetamodel(BeamlineSequence.class)
public class BeamlineSequence_ { 

    public static volatile SingularAttribute<BeamlineSequence, String> sequenceName;
    public static volatile SingularAttribute<BeamlineSequence, Integer> beamlineSequenceId;
    public static volatile SingularAttribute<BeamlineSequence, String> sequenceDescription;
    public static volatile SingularAttribute<BeamlineSequence, String> firstElementName;
    public static volatile SingularAttribute<BeamlineSequence, String> lastElementName;
    public static volatile SingularAttribute<BeamlineSequence, Double> sequenceLength;
    public static volatile SingularAttribute<BeamlineSequence, String> predecessorSequence;
    public static volatile CollectionAttribute<BeamlineSequence, BlsequenceLattice> blsequenceLatticeCollection;
    public static volatile CollectionAttribute<BeamlineSequence, Element> elementCollection;

}