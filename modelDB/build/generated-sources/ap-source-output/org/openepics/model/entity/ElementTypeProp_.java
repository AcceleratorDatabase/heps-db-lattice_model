package org.openepics.model.entity;

import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import org.openepics.model.entity.ElementProp;
import org.openepics.model.entity.ElementType;

@Generated(value="EclipseLink-2.5.1.v20130918-rNA", date="2014-02-27T18:50:52")
@StaticMetamodel(ElementTypeProp.class)
public class ElementTypeProp_ { 

    public static volatile SingularAttribute<ElementTypeProp, String> elementTypePropDescription;
    public static volatile SingularAttribute<ElementTypeProp, Integer> elementTypePropId;
    public static volatile SingularAttribute<ElementTypeProp, ElementType> elementTypeId;
    public static volatile CollectionAttribute<ElementTypeProp, ElementProp> elementPropCollection;
    public static volatile SingularAttribute<ElementTypeProp, String> elementTypePropDatatype;
    public static volatile SingularAttribute<ElementTypeProp, String> elementTypePropDefault;
    public static volatile SingularAttribute<ElementTypeProp, String> elementTypePropName;
    public static volatile SingularAttribute<ElementTypeProp, String> elementTypePropUnit;

}