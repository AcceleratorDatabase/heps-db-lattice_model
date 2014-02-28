package org.openepics.model.entity;

import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import org.openepics.model.entity.BeamParameter;

@Generated(value="EclipseLink-2.5.1.v20130918-rNA", date="2014-02-27T18:50:52")
@StaticMetamodel(ParticleType.class)
public class ParticleType_ { 

    public static volatile SingularAttribute<ParticleType, Double> particleMass;
    public static volatile SingularAttribute<ParticleType, Integer> particleTypeId;
    public static volatile SingularAttribute<ParticleType, String> particleName;
    public static volatile CollectionAttribute<ParticleType, BeamParameter> beamParameterCollection;
    public static volatile SingularAttribute<ParticleType, Integer> particleCharge;

}