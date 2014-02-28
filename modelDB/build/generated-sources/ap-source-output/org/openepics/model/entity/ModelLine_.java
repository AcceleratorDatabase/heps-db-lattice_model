package org.openepics.model.entity;

import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import org.openepics.model.entity.Lattice;

@Generated(value="EclipseLink-2.5.1.v20130918-rNA", date="2014-02-27T18:50:52")
@StaticMetamodel(ModelLine.class)
public class ModelLine_ { 

    public static volatile SingularAttribute<ModelLine, String> startMarker;
    public static volatile SingularAttribute<ModelLine, String> endMarker;
    public static volatile SingularAttribute<ModelLine, String> modelLineName;
    public static volatile SingularAttribute<ModelLine, Integer> modelLineId;
    public static volatile CollectionAttribute<ModelLine, Lattice> latticeCollection;
    public static volatile SingularAttribute<ModelLine, String> modelLineDescription;
    public static volatile SingularAttribute<ModelLine, Double> endPosition;
    public static volatile SingularAttribute<ModelLine, Double> startPosition;

}