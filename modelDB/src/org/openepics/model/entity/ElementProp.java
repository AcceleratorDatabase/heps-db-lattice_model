/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.openepics.model.entity;

import java.io.Serializable;
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
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author paul
 */
@Entity
@Table(name = "element_prop")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ElementProp.findAll", query = "SELECT e FROM ElementProp e"),
    @NamedQuery(name = "ElementProp.findByElementPropId", query = "SELECT e FROM ElementProp e WHERE e.elementPropId = :elementPropId"),
    @NamedQuery(name = "ElementProp.findByElementPropString", query = "SELECT e FROM ElementProp e WHERE e.elementPropString = :elementPropString"),
    @NamedQuery(name = "ElementProp.findByElementPropInt", query = "SELECT e FROM ElementProp e WHERE e.elementPropInt = :elementPropInt"),
    @NamedQuery(name = "ElementProp.findByElementPropDouble", query = "SELECT e FROM ElementProp e WHERE e.elementPropDouble = :elementPropDouble"),
    @NamedQuery(name = "ElementProp.findByElementPropIndex", query = "SELECT e FROM ElementProp e WHERE e.elementPropIndex = :elementPropIndex"),
    @NamedQuery(name = "ElementProp.findByPropCategory", query = "SELECT e FROM ElementProp e WHERE e.propCategory = :propCategory"),
    @NamedQuery(name = "ElementProp.findByElementPropName", query = "SELECT e FROM ElementProp e WHERE e.elementPropName = :elementPropName"),
    @NamedQuery(name = "ElementProp.findByElementPropDatatype", query = "SELECT e FROM ElementProp e WHERE e.elementPropDatatype = :elementPropDatatype")})
public class ElementProp implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "element_prop_id")
    private Integer elementPropId;
    @Size(max = 255)
    @Column(name = "element_prop_string")
    private String elementPropString;
    @Column(name = "element_prop_int")
    private Integer elementPropInt;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "element_prop_double")
    private Double elementPropDouble;
    @Column(name = "element_prop_index")
    private Integer elementPropIndex;
    @Size(max = 45)
    @Column(name = "prop_category")
    private String propCategory;
    @Size(max = 45)
    @Column(name = "element_prop_name")
    private String elementPropName;
    @Size(max = 45)
    @Column(name = "element_prop_datatype")
    private String elementPropDatatype;
    @JoinColumn(name = "lattice_id", referencedColumnName = "lattice_id")
    @ManyToOne
    private Lattice latticeId;
    @JoinColumn(name = "element_type_prop_id", referencedColumnName = "element_type_prop_id")
    @ManyToOne
    private ElementTypeProp elementTypePropId;
    @JoinColumn(name = "element_id", referencedColumnName = "element_id")
    @ManyToOne
    private Element elementId;

    public ElementProp() {
    }

    public ElementProp(Integer elementPropId) {
        this.elementPropId = elementPropId;
    }

    public Integer getElementPropId() {
        return elementPropId;
    }

    public void setElementPropId(Integer elementPropId) {
        this.elementPropId = elementPropId;
    }

    public String getElementPropString() {
        return elementPropString;
    }

    public void setElementPropString(String elementPropString) {
        this.elementPropString = elementPropString;
    }

    public Integer getElementPropInt() {
        return elementPropInt;
    }

    public void setElementPropInt(Integer elementPropInt) {
        this.elementPropInt = elementPropInt;
    }

    public Double getElementPropDouble() {
        return elementPropDouble;
    }

    public void setElementPropDouble(Double elementPropDouble) {
        this.elementPropDouble = elementPropDouble;
    }

    public Integer getElementPropIndex() {
        return elementPropIndex;
    }

    public void setElementPropIndex(Integer elementPropIndex) {
        this.elementPropIndex = elementPropIndex;
    }

    public String getPropCategory() {
        return propCategory;
    }

    public void setPropCategory(String propCategory) {
        this.propCategory = propCategory;
    }

    public String getElementPropName() {
        return elementPropName;
    }

    public void setElementPropName(String elementPropName) {
        this.elementPropName = elementPropName;
    }

    public String getElementPropDatatype() {
        return elementPropDatatype;
    }

    public void setElementPropDatatype(String elementPropDatatype) {
        this.elementPropDatatype = elementPropDatatype;
    }

    public Lattice getLatticeId() {
        return latticeId;
    }

    public void setLatticeId(Lattice latticeId) {
        this.latticeId = latticeId;
    }

    public ElementTypeProp getElementTypePropId() {
        return elementTypePropId;
    }

    public void setElementTypePropId(ElementTypeProp elementTypePropId) {
        this.elementTypePropId = elementTypePropId;
    }

    public Element getElementId() {
        return elementId;
    }

    public void setElementId(Element elementId) {
        this.elementId = elementId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (elementPropId != null ? elementPropId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ElementProp)) {
            return false;
        }
        ElementProp other = (ElementProp) object;
        if ((this.elementPropId == null && other.elementPropId != null) || (this.elementPropId != null && !this.elementPropId.equals(other.elementPropId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.openepics.model.entity.ElementProp[ elementPropId=" + elementPropId + " ]";
    }
    
}
