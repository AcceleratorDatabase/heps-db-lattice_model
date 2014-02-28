package org.openepics.model.entity;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import org.openepics.model.entity.Element;

@Generated(value="EclipseLink-2.5.1.v20130918-rNA", date="2014-02-27T18:50:52")
@StaticMetamodel(RfGap.class)
public class RfGap_ { 

    public static volatile SingularAttribute<RfGap, Double> phaseFactor;
    public static volatile SingularAttribute<RfGap, Double> ampFactor;
    public static volatile SingularAttribute<RfGap, Double> ttf;
    public static volatile SingularAttribute<RfGap, Integer> endCellind;
    public static volatile SingularAttribute<RfGap, String> gapName;
    public static volatile SingularAttribute<RfGap, Double> len;
    public static volatile SingularAttribute<RfGap, Integer> rfGapId;
    public static volatile SingularAttribute<RfGap, Element> cavityId;
    public static volatile SingularAttribute<RfGap, Double> gapOffset;
    public static volatile SingularAttribute<RfGap, Double> pos;

}