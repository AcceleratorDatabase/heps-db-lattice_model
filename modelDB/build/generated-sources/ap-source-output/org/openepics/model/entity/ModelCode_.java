package org.openepics.model.entity;

import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import org.openepics.model.entity.Model;

@Generated(value="EclipseLink-2.5.1.v20130918-rNA", date="2014-02-27T18:50:52")
@StaticMetamodel(ModelCode.class)
public class ModelCode_ { 

    public static volatile SingularAttribute<ModelCode, String> codeName;
    public static volatile CollectionAttribute<ModelCode, Model> modelCollection;
    public static volatile SingularAttribute<ModelCode, Integer> modelCodeId;
    public static volatile SingularAttribute<ModelCode, String> algorithm;

}