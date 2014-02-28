package org.openepics.model.entity;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import org.openepics.model.entity.Element;
import org.openepics.model.entity.ElementTypeProp;
import org.openepics.model.entity.Lattice;

@Generated(value="EclipseLink-2.5.1.v20130918-rNA", date="2014-02-27T18:50:52")
@StaticMetamodel(ElementProp.class)
public class ElementProp_ { 

    public static volatile SingularAttribute<ElementProp, Element> elementId;
    public static volatile SingularAttribute<ElementProp, String> propCategory;
    public static volatile SingularAttribute<ElementProp, Integer> elementPropId;
    public static volatile SingularAttribute<ElementProp, String> elementPropString;
    public static volatile SingularAttribute<ElementProp, Integer> elementPropIndex;
    public static volatile SingularAttribute<ElementProp, String> elementPropDatatype;
    public static volatile SingularAttribute<ElementProp, ElementTypeProp> elementTypePropId;
    public static volatile SingularAttribute<ElementProp, String> elementPropName;
    public static volatile SingularAttribute<ElementProp, Double> elementPropDouble;
    public static volatile SingularAttribute<ElementProp, Integer> elementPropInt;
    public static volatile SingularAttribute<ElementProp, Lattice> latticeId;

}