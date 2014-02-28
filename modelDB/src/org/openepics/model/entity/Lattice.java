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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author admin
 */
@Entity
@Table(name = "lattice")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Lattice.findAll", query = "SELECT l FROM Lattice l"),
    @NamedQuery(name = "Lattice.findByLatticeId", query = "SELECT l FROM Lattice l WHERE l.latticeId = :latticeId"),
    @NamedQuery(name = "Lattice.findByCreateDate", query = "SELECT l FROM Lattice l WHERE l.createDate = :createDate"),
    @NamedQuery(name = "Lattice.findByCreatedBy", query = "SELECT l FROM Lattice l WHERE l.createdBy = :createdBy"),
    @NamedQuery(name = "Lattice.findByLatticeDescription", query = "SELECT l FROM Lattice l WHERE l.latticeDescription = :latticeDescription"),
    @NamedQuery(name = "Lattice.findByLatticeName", query = "SELECT l FROM Lattice l WHERE l.latticeName = :latticeName"),
    @NamedQuery(name = "Lattice.findByUpdateDate", query = "SELECT l FROM Lattice l WHERE l.updateDate = :updateDate"),
    @NamedQuery(name = "Lattice.findByUpdatedBy", query = "SELECT l FROM Lattice l WHERE l.updatedBy = :updatedBy")})
public class Lattice implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "lattice_id")
    private Integer latticeId;
    @Column(name = "create_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createDate;
    @Size(max = 255)
    @Column(name = "created_by")
    private String createdBy;
    @Size(max = 255)
    @Column(name = "lattice_description")
    private String latticeDescription;
    @Size(max = 255)
    @Column(name = "lattice_name")
    private String latticeName;
    @Column(name = "update_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateDate;
    @Size(max = 255)
    @Column(name = "updated_by")
    private String updatedBy;
    @OneToMany(mappedBy = "latticeId")
    private Collection<Model> modelCollection;
    @JoinColumn(name = "model_geometry_id", referencedColumnName = "model_geometry_id")
    @ManyToOne
    private ModelGeometry modelGeometryId;
    @JoinColumn(name = "machine_mode_id", referencedColumnName = "machine_mode_id")
    @ManyToOne
    private MachineMode machineModeId;
    @JoinColumn(name = "model_line_id", referencedColumnName = "model_line_id")
    @ManyToOne
    private ModelLine modelLineId;
    @OneToMany(mappedBy = "latticeId")
    private Collection<ElementProp> elementPropCollection;
    @OneToMany(mappedBy = "latticeId")
    private Collection<BlsequenceLattice> blsequenceLatticeCollection;
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

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getLatticeDescription() {
        return latticeDescription;
    }

    public void setLatticeDescription(String latticeDescription) {
        this.latticeDescription = latticeDescription;
    }

    public String getLatticeName() {
        return latticeName;
    }

    public void setLatticeName(String latticeName) {
        this.latticeName = latticeName;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    @XmlTransient
    public Collection<Model> getModelCollection() {
        return modelCollection;
    }

    public void setModelCollection(Collection<Model> modelCollection) {
        this.modelCollection = modelCollection;
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

    public ModelLine getModelLineId() {
        return modelLineId;
    }

    public void setModelLineId(ModelLine modelLineId) {
        this.modelLineId = modelLineId;
    }

    @XmlTransient
    public Collection<ElementProp> getElementPropCollection() {
        return elementPropCollection;
    }

    public void setElementPropCollection(Collection<ElementProp> elementPropCollection) {
        this.elementPropCollection = elementPropCollection;
    }

    @XmlTransient
    public Collection<BlsequenceLattice> getBlsequenceLatticeCollection() {
        return blsequenceLatticeCollection;
    }

    public void setBlsequenceLatticeCollection(Collection<BlsequenceLattice> blsequenceLatticeCollection) {
        this.blsequenceLatticeCollection = blsequenceLatticeCollection;
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
