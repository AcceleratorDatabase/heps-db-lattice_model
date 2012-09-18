/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.openepics.model.entity;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author chu
 */
@Entity
@Table(name = "lattice")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Lattice.findAll", query = "SELECT l FROM Lattice l"),
    @NamedQuery(name = "Lattice.findByLatticeId", query = "SELECT l FROM Lattice l WHERE l.latticeId = :latticeId"),
    @NamedQuery(name = "Lattice.findByLatticeName", query = "SELECT l FROM Lattice l WHERE l.latticeName = :latticeName"),
    @NamedQuery(name = "Lattice.findByLatticeDescription", query = "SELECT l FROM Lattice l WHERE l.latticeDescription = :latticeDescription"),
    @NamedQuery(name = "Lattice.findByCreatedBy", query = "SELECT l FROM Lattice l WHERE l.createdBy = :createdBy"),
    @NamedQuery(name = "Lattice.findByCreateDate", query = "SELECT l FROM Lattice l WHERE l.createDate = :createDate"),
    @NamedQuery(name = "Lattice.findByUpdatedBy", query = "SELECT l FROM Lattice l WHERE l.updatedBy = :updatedBy"),
    @NamedQuery(name = "Lattice.findByUpdateDate", query = "SELECT l FROM Lattice l WHERE l.updateDate = :updateDate")})
public class Lattice implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "lattice_id")
    private Integer latticeId;
    @Column(name = "lattice_name")
    private String latticeName;
    @Column(name = "lattice_description")
    private String latticeDescription;
    @Column(name = "created_by")
    private String createdBy;
    @Column(name = "create_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createDate;
    @Column(name = "updated_by")
    private String updatedBy;
    @Column(name = "update_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateDate;
    @OneToMany(mappedBy = "latticeId")
    private Collection<Element> elementCollection;
    @OneToMany(mappedBy = "latticeId")
    private Collection<Model> modelCollection;
    @JoinColumn(name = "model_line_id", referencedColumnName = "model_line_id")
    @ManyToOne
    private ModelLine modelLineId;
    @JoinColumn(name = "model_geometry_id", referencedColumnName = "model_geometry_id")
    @ManyToOne
    private ModelGeometry modelGeometryId;
    @JoinColumn(name = "machine_mode_id", referencedColumnName = "machine_mode_id")
    @ManyToOne
    private MachineMode machineModeId;
    @OneToMany(mappedBy = "latticeId")
    private Collection<GoldLattice> goldLatticeCollection;

    public Lattice() {
    }

    public Lattice(Integer latticeId) {
        this.latticeId = latticeId;
    }

    public Integer getLatticeId() {
        return latticeId;
    }

    public void setLatticeId(Integer latticeId) {
        this.latticeId = latticeId;
    }

    public String getLatticeName() {
        return latticeName;
    }

    public void setLatticeName(String latticeName) {
        this.latticeName = latticeName;
    }

    public String getLatticeDescription() {
        return latticeDescription;
    }

    public void setLatticeDescription(String latticeDescription) {
        this.latticeDescription = latticeDescription;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    @XmlTransient
    public Collection<Element> getElementCollection() {
        return elementCollection;
    }

    public void setElementCollection(Collection<Element> elementCollection) {
        this.elementCollection = elementCollection;
    }

    @XmlTransient
    public Collection<Model> getModelCollection() {
        return modelCollection;
    }

    public void setModelCollection(Collection<Model> modelCollection) {
        this.modelCollection = modelCollection;
    }

    public ModelLine getModelLineId() {
        return modelLineId;
    }

    public void setModelLineId(ModelLine modelLineId) {
        this.modelLineId = modelLineId;
    }

    public ModelGeometry getModelGeometryId() {
        return modelGeometryId;
    }

    public void setModelGeometryId(ModelGeometry modelGeometryId) {
        this.modelGeometryId = modelGeometryId;
    }

    public MachineMode getMachineModeId() {
        return machineModeId;
    }

    public void setMachineModeId(MachineMode machineModeId) {
        this.machineModeId = machineModeId;
    }

    @XmlTransient
    public Collection<GoldLattice> getGoldLatticeCollection() {
        return goldLatticeCollection;
    }

    public void setGoldLatticeCollection(Collection<GoldLattice> goldLatticeCollection) {
        this.goldLatticeCollection = goldLatticeCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (latticeId != null ? latticeId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Lattice)) {
            return false;
        }
        Lattice other = (Lattice) object;
        if ((this.latticeId == null && other.latticeId != null) || (this.latticeId != null && !this.latticeId.equals(other.latticeId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.openepics.model.entity.Lattice[ latticeId=" + latticeId + " ]";
    }
    
}
