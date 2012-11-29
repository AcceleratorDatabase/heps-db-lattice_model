/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.openepics.model.entity;

import java.io.Serializable;
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
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author chu
 */
@Entity
@Table(name = "gold_lattice")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "GoldLattice.findAll", query = "SELECT g FROM GoldLattice g"),
    @NamedQuery(name = "GoldLattice.findByGoldId", query = "SELECT g FROM GoldLattice g WHERE g.goldId = :goldId"),
    @NamedQuery(name = "GoldLattice.findByMachineModeId", query = "SELECT g FROM GoldLattice g WHERE g.machineModeId = :machineModeId"),
    @NamedQuery(name = "GoldLattice.findByCreateDate", query = "SELECT g FROM GoldLattice g WHERE g.createDate = :createDate"),
    @NamedQuery(name = "GoldLattice.findByUpdatedBy", query = "SELECT g FROM GoldLattice g WHERE g.updatedBy = :updatedBy"),
    @NamedQuery(name = "GoldLattice.findByUpdateDate", query = "SELECT g FROM GoldLattice g WHERE g.updateDate = :updateDate"),
    @NamedQuery(name = "GoldLattice.findByGoldStatusInd", query = "SELECT g FROM GoldLattice g WHERE g.goldStatusInd = :goldStatusInd"),
    @NamedQuery(name = "GoldLattice.findByCreatedBy", query = "SELECT g FROM GoldLattice g WHERE g.createdBy = :createdBy")})
public class GoldLattice implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "gold_id")
    private Integer goldId;
    @Size(max = 45)
    @Column(name = "machine_mode_id")
    private String machineModeId;
    @Column(name = "create_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createDate;
    @Size(max = 45)
    @Column(name = "updated_by")
    private String updatedBy;
    @Column(name = "update_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateDate;
    @Column(name = "gold_status_ind")
    private Integer goldStatusInd;
    @Size(max = 45)
    @Column(name = "created_by")
    private String createdBy;
    @JoinColumn(name = "lattice_id", referencedColumnName = "lattice_id")
    @ManyToOne
    private Lattice latticeId;

    public GoldLattice() {
    }

    public GoldLattice(Integer goldId) {
        this.goldId = goldId;
    }

    public Integer getGoldId() {
        return goldId;
    }

    public void setGoldId(Integer goldId) {
        this.goldId = goldId;
    }

    public String getMachineModeId() {
        return machineModeId;
    }

    public void setMachineModeId(String machineModeId) {
        this.machineModeId = machineModeId;
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

    public Integer getGoldStatusInd() {
        return goldStatusInd;
    }

    public void setGoldStatusInd(Integer goldStatusInd) {
        this.goldStatusInd = goldStatusInd;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Lattice getLatticeId() {
        return latticeId;
    }

    public void setLatticeId(Lattice latticeId) {
        this.latticeId = latticeId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (goldId != null ? goldId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof GoldLattice)) {
            return false;
        }
        GoldLattice other = (GoldLattice) object;
        if ((this.goldId == null && other.goldId != null) || (this.goldId != null && !this.goldId.equals(other.goldId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.openepics.model.entity.GoldLattice[ goldId=" + goldId + " ]";
    }
    
}
