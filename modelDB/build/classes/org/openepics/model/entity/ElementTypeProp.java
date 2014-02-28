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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author admin
 */
@Entity
@Table(name = "element_type_prop")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ElementTypeProp.findAll", query = "SELECT e FROM ElementTypeProp e"),
    @NamedQuery(name = "ElementTypeProp.findByElementTypePropId", query = "SELECT e FROM ElementTypeProp e WHERE e.elementTypePropId = :elementTypePropId"),
    @NamedQuery(name = "ElementTypeProp.findByElementTypePropDatatype", query = "SELECT e FROM ElementTypeProp e WHERE e.elementTypePropDatatype = :elementTypePropDatatype"),
    @NamedQuery(name = "ElementTypeProp.findByElementTypePropDefault", query = "SELECT e FROM ElementTypeProp e WHERE e.elementTypePropDefault = :elementTypePropDefault"),
    @NamedQuery(name = "ElementTypeProp.findByElementTypePropDescription", query = "SELECT e FROM ElementTypeProp e WHERE e.elementTypePropDescription = :elementTypePropDescription"),
    @NamedQuery(name = "ElementTypeProp.findByElementTypePropName", query = "SELECT e FROM ElementTypeProp e WHERE e.elementTypePropName = :elementTypePropName"),
    @NamedQuery(name = "ElementTypeProp.findByElementTypePropUnit", query = "SELECT e FROM ElementTypeProp e WHERE e.elementTypePropUnit = :elementTypePropUnit")})
public class ElementTypeProp implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "element_type_prop_id")
    private Integer elementTypePropId;
    @Size(max = 255)
    @Column(name = "element_type_prop_datatype")
    private String elementTypePropDatatype;
    @Size(max = 255)
    @Column(name = "element_type_prop_default")
    private String elementTypePropDefault;
    @Size(max = 255)
    @Column(name = "element_type_prop_description")
    private String elementTypePropDescription;
    @Size(max = 255)
    @Column(name = "element_type_prop_name")
    private String elementTypePropName;
    @Size(max = 255)
    @Column(name = "element_type_prop_unit")
    private String elementTypePropUnit;
    @JoinColumn(name = "element_type_id", referencedColumnName = "element_type_id")
    @ManyToOne
    private ElementType elementTypeId;
    @OneToMany(mappedBy = "elementTypePropId")
    private Collection<ElementProp> elementPropCollection;

    public ElementTypeProp() {
    }

    public ElementTypeProp(Integer elementTypePropId) {
        this.elementTypePropId = elementTypePropId;
    }

    public Integer getElementTypePropId() {
        return elementTypePropId;
    }

    public void setElementTypePropId(Integer elementTypePropId) {
        this.elementTypePropId = elementTypePropId;
    }

    public String getElementTypePropDatatype() {
        return elementTypePropDatatype;
    }

    public void setElementTypePropDatatype(String elementTypePropDatatype) {
        this.elementTypePropDatatype = elementTypePropDatatype;
    }

    public String getElementTypePropDefault() {
        return elementTypePropDefault;
    }

    public void setElementTypePropDefault(String elementTypePropDefault) {
        this.elementTypePropDefault = elementTypePropDefault;
    }

    public String getElementTypePropDescription() {
        return elementTypePropDescription;
    }

    public void setElementTypePropDescription(String elementTypePropDescription) {
        this.elementTypePropDescription = elementTypePropDescription;
    }

    public String getElementTypePropName() {
        return elementTypePropName;
    }

    public void setElementTypePropName(String elementTypePropName) {
        this.elementTypePropName = elementTypePropName;
    }

    public String getElementTypePropUnit() {
        return elementTypePropUnit;
    }

    public void setElementTypePropUnit(String elementTypePropUnit) {
        this.elementTypePropUnit = elementTypePropUnit;
    }

    public ElementType getElementTypeId() {
        return elementTypeId;
    }

    public void setElementTypeId(ElementType elementTypeId) {
        this.elementTypeId = elementTypeId;
    }

    @XmlTransient
    public Collection<ElementProp> getElementPropCollection() {
        return elementPropCollection;
    }

    public void setElementPropCollection(Collection<ElementProp> elementPropCollection) {
        this.elementPropCollection = elementPropCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (elementTypePropId != null ? elementTypePropId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ElementTypeProp)) {
            return false;
        }
        ElementTypeProp other = (ElementTypeProp) object;
        if ((this.elementTypePropId == null && other.elementTypePropId != null) || (this.elementTypePropId != null && !this.elementTypePropId.equals(other.elementTypePropId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.openepics.model.entity.ElementTypeProp[ elementTypePropId=" + elementTypePropId + " ]";
    }
    
}
