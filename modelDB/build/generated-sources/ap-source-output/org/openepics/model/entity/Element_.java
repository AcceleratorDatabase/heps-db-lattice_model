package org.openepics.model.entity;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import org.openepics.model.entity.BeamParameter;
import org.openepics.model.entity.BeamlineSequence;
import org.openepics.model.entity.ElementInstallDevice;
import org.openepics.model.entity.ElementProp;
import org.openepics.model.entity.ElementType;
import org.openepics.model.entity.RfGap;

@Generated(value="EclipseLink-2.5.1.v20130918-rNA", date="2014-02-27T18:50:52")
@StaticMetamodel(Element.class)
public class Element_ { 

    public static volatile CollectionAttribute<Element, BeamParameter> beamParameterCollection;
    public static volatile SingularAttribute<Element, Integer> elementId;
    public static volatile SingularAttribute<Element, String> elementName;
    public static volatile SingularAttribute<Element, Double> dx;
    public static volatile SingularAttribute<Element, Double> dy;
    public static volatile CollectionAttribute<Element, RfGap> rfGapCollection;
    public static volatile SingularAttribute<Element, Double> dz;
    public static volatile SingularAttribute<Element, Date> insertDate;
    public static volatile SingularAttribute<Element, Double> len;
    public static volatile SingularAttribute<Element, Double> pos;
    public static volatile SingularAttribute<Element, Double> yaw;
    public static volatile SingularAttribute<Element, Double> roll;
    public static volatile SingularAttribute<Element, String> createdBy;
    public static volatile SingularAttribute<Element, Double> s;
    public static volatile SingularAttribute<Element, BeamlineSequence> beamlineSequenceId;
    public static volatile SingularAttribute<Element, ElementType> elementTypeId;
    public static volatile CollectionAttribute<Element, ElementProp> elementPropCollection;
    public static volatile SingularAttribute<Element, Double> pitch;
    public static volatile CollectionAttribute<Element, ElementInstallDevice> elementInstallDeviceCollection;

}