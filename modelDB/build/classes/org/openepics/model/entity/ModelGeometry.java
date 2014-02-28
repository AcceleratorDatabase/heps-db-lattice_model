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
 * @author admin
 */
@Entity
@Table(name = "model_geometry")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ModelGeometry.findAll", query = "SELECT m FROM ModelGeometry m"),
    @NamedQuery(name = "ModelGeometry.findByModelGeometryId", query = "SELECT m FROM ModelGeometry m WHERE m.modelGeometryId = :modelGeometryId"),
    @NamedQuery(name = "ModelGeometry.findByModelGeometryDescription", query = "SELECT m FROM ModelGeometry m WHERE m.modelGeometryDescription = :modelGeometryDescription"),
    @NamedQuery(name = "ModelGeometry.findByModelGeometryName", query = "SELECT m FROM ModelGeometry m WHERE m.modelGeometryName = :modelGeometryName")})
public class ModelGeometry implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "model_geometry_id")
    private Integer modelGeometryId;
    @Size(max = 255)
    @Column(name = "model_geometry_description")
    private String modelGeometryDescription;
    @Size(max = 255)
    @Column(name = "model_geometry_name")
    private String modelGeometryName;
    @OneToMany(mappedBy = "modelGeometryId")
    private Collection<Lattice> latticeCollection;

    public ModelGeometry() {
    }

    public ModelGeometry(Integer modelGeometryId) {
        this.modelGeometryId = modelGeometryId;
    }

    public Integer getModelGeometryId() {
        return modelGeometryId;
    }

    public void setModelGeometryId(Integer modelGeometryId) {
        this.modelGeometryId = modelGeometryId;
    }

    public String getModelGeometryDescription() {
        return modelGeometryDescription;
    }

    public void setModelGeometryDescription(String modelGeometryDescription) {
        this.modelGeometryDescription = modelGeometryDescription;
    }

    public String getModelGeometryName() {
        return modelGeometryName;
    }

    public void setModelGeometryName(String modelGeometryName) {
        this.modelGeometryName = modelGeometryName;
    }

    @XmlTransient
    public Collection<Lattice> getLatticeCollection() {
        return latticeCollection;
    }

    public void setLatticeCollection(Collection<Lattice> latticeCollection) {
        this.latticeCollection = latticeCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (modelGeometryId != null ? modelGeometryId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ModelGeometry)) {
            return false;
        }
        ModelGeometry other = (ModelGeometry) object;
        if ((this.modelGeometryId == null && other.modelGeometryId != null) || (this.modelGeometryId != null && !this.modelGeometryId.equals(other.modelGeometryId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.openepics.model.entity.ModelGeometry[ modelGeometryId=" + modelGeometryId + " ]";
    }
    
}
