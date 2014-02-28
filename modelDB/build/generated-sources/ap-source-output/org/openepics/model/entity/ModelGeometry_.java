package org.openepics.model.entity;

import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import org.openepics.model.entity.Lattice;

@Generated(value="EclipseLink-2.5.1.v20130918-rNA", date="2014-02-27T18:50:52")
@StaticMetamodel(ModelGeometry.class)
public class ModelGeometry_ { 

    public static volatile SingularAttribute<ModelGeometry, Integer> modelGeometryId;
    public static volatile CollectionAttribute<ModelGeometry, Lattice> latticeCollection;
    public static volatile SingularAttribute<ModelGeometry, String> modelGeometryName;
    public static volatile SingularAttribute<ModelGeometry, String> modelGeometryDescription;

}