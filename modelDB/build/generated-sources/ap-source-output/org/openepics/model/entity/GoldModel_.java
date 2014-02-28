package org.openepics.model.entity;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import org.openepics.model.entity.Model;

@Generated(value="EclipseLink-2.5.1.v20130918-rNA", date="2014-02-27T18:50:52")
@StaticMetamodel(GoldModel.class)
public class GoldModel_ { 

    public static volatile SingularAttribute<GoldModel, Model> modelId;
    public static volatile SingularAttribute<GoldModel, String> createdBy;
    public static volatile SingularAttribute<GoldModel, Date> createDate;
    public static volatile SingularAttribute<GoldModel, Integer> goldStatusInd;
    public static volatile SingularAttribute<GoldModel, Date> updateDate;
    public static volatile SingularAttribute<GoldModel, Integer> goldModelId;
    public static volatile SingularAttribute<GoldModel, String> updatedBy;

}