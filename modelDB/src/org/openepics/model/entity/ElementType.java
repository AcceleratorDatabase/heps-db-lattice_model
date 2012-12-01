/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.openepics.model.entity;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author paul
 */
@Entity
@Table(name = "element_type")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ElementType.findAll", query = "SELECT e FROM ElementType e"),
    @NamedQuery(name = "ElementType.findByElementTypeId", query = "SELECT e FROM ElementType e WHERE e.elementTypeId = :elementTypeId"),
    @NamedQuery(name = "ElementType.findByElementType", query = "SELECT e FROM ElementType e WHERE e.elementType = :elementType"),
    @NamedQuery(name = "ElementType.findByElementTypeDescription", query = "SELECT e FROM ElementType e WHERE e.elementTypeDescription = :elementTypeDescription")})
public class ElementType implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "element_type_id")
    private Integer elementTypeId;
    @Size(max = 45)
    @Column(name = "element_type")
    private String elementType;
    @Size(max = 255)
    @Column(name = "element_type_description")
    private String elementTypeDescription;
    @OneToMany(mappedBy = "elementTypeId")
    private Collection<Element> elementCollection;
    @OneToMany(mappedBy = "elementTypeId")
    private Collection<ElementTypeProp> elementTypePropCollection;

    public ElementType() {
    }

    public ElementType(Integer elementTypeId) {
        this.elementTypeId = elementTypeId;
    }

    public Integer getElementTypeId() {
        return elementTypeId;
    }

    public void setElementTypeId(Integer elementTypeId) {
        this.elementTypeId = elementTypeId;
    }

    public String getElementType() {
        return elementType;
    }

    public void setElementType(String elementType) {
        this.elementType = elementType;
    }

    public String getElementTypeDescription() {
        return elementTypeDescription;
    }

    public void setElementTypeDescription(String elementTypeDescription) {
        this.elementTypeDescription = elementTypeDescription;
    }

    @XmlTransient
    public Collection<Element> getElementCollection() {
        return elementCollection;
    }

    public void setElementCollection(Collection<Element> elementCollection) {
        this.elementCollection = elementCollection;
    }

    @XmlTransient
    public Collection<ElementTypeProp> getElementTypePropCollection() {
        return elementTypePropCollection;
    }

    public void setElementTypePropCollection(Collection<ElementTypeProp> elementTypePropCollection) {
        this.elementTypePropCollection = elementTypePropCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (elementTypeId != null ? elementTypeId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ElementType)) {
            return false;
        }
        ElementType other = (ElementType) object;
        if ((this.elementTypeId == null && other.elementTypeId != null) || (this.elementTypeId != null && !this.elementTypeId.equals(other.elementTypeId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.openepics.model.entity.ElementType[ elementTypeId=" + elementTypeId + " ]";
    }
    
}
