package org.openepics.model.entity;

import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import org.openepics.model.entity.BeamParameterProp;
import org.openepics.model.entity.Element;
import org.openepics.model.entity.Model;
import org.openepics.model.entity.ParticleType;

@Generated(value="EclipseLink-2.5.1.v20130918-rNA", date="2014-02-27T18:50:52")
@StaticMetamodel(BeamParameter.class)
public class BeamParameter_ { 

    public static volatile SingularAttribute<BeamParameter, Model> modelId;
    public static volatile SingularAttribute<BeamParameter, Element> elementId;
    public static volatile SingularAttribute<BeamParameter, ParticleType> particleType;
    public static volatile CollectionAttribute<BeamParameter, BeamParameterProp> beamParameterPropCollection;
    public static volatile SingularAttribute<BeamParameter, Integer> twissId;
    public static volatile SingularAttribute<BeamParameter, Integer> sliceId;

}