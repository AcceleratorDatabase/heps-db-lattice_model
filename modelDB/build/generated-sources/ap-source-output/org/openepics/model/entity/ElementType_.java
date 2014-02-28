package org.openepics.model.entity;

import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import org.openepics.model.entity.Element;
import org.openepics.model.entity.ElementTypeProp;

@Generated(value="EclipseLink-2.5.1.v20130918-rNA", date="2014-02-27T18:50:52")
@StaticMetamodel(ElementType.class)
public class ElementType_ { 

    public static volatile CollectionAttribute<ElementType, ElementTypeProp> elementTypePropCollection;
    public static volatile SingularAttribute<ElementType, Integer> elementTypeId;
    public static volatile SingularAttribute<ElementType, String> elementTypeDescription;
    public static volatile SingularAttribute<ElementType, String> elementType;
    public static volatile CollectionAttribute<ElementType, Element> elementCollection;

}