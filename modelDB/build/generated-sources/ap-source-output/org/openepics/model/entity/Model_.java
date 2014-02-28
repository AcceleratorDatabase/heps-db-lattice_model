package org.openepics.model.entity;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import org.openepics.model.entity.BeamParameter;
import org.openepics.model.entity.GoldModel;
import org.openepics.model.entity.Lattice;
import org.openepics.model.entity.ModelCode;

@Generated(value="EclipseLink-2.5.1.v20130918-rNA", date="2014-02-27T18:50:52")
@StaticMetamodel(Model.class)
public class Model_ { 

    public static volatile CollectionAttribute<Model, BeamParameter> beamParameterCollection;
    public static volatile CollectionAttribute<Model, GoldModel> goldModelCollection;
    public static volatile SingularAttribute<Model, ModelCode> modelCodeId;
    public static volatile SingularAttribute<Model, Date> updateDate;
    public static volatile SingularAttribute<Model, Lattice> latticeId;
    public static volatile SingularAttribute<Model, String> updatedBy;
    public static volatile SingularAttribute<Model, Integer> modelId;
    public static volatile SingularAttribute<Model, Double> chromeX1;
    public static volatile SingularAttribute<Model, Double> chromeY0;
    public static volatile SingularAttribute<Model, Double> chromeX0;
    public static volatile SingularAttribute<Model, Double> chromeY2;
    public static volatile SingularAttribute<Model, Double> finalBeamEnergy;
    public static volatile SingularAttribute<Model, Double> chromeY1;
    public static volatile SingularAttribute<Model, String> modelName;
    public static volatile SingularAttribute<Model, String> createdBy;
    public static volatile SingularAttribute<Model, Double> chromeX2;
    public static volatile SingularAttribute<Model, String> modelDesc;
    public static volatile SingularAttribute<Model, Integer> initialConditionInd;
    public static volatile SingularAttribute<Model, Double> tuneX;
    public static volatile SingularAttribute<Model, Double> tuneY;
    public static volatile SingularAttribute<Model, Date> createDate;

}