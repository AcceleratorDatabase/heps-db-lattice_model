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
 * @author admin
 */
@Entity
@Table(name = "gold_model")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "GoldModel.findAll", query = "SELECT g FROM GoldModel g"),
    @NamedQuery(name = "GoldModel.findByGoldModelId", query = "SELECT g FROM GoldModel g WHERE g.goldModelId = :goldModelId"),
    @NamedQuery(name = "GoldModel.findByCreateDate", query = "SELECT g FROM GoldModel g WHERE g.createDate = :createDate"),
    @NamedQuery(name = "GoldModel.findByCreatedBy", query = "SELECT g FROM GoldModel g WHERE g.createdBy = :createdBy"),
    @NamedQuery(name = "GoldModel.findByGoldStatusInd", query = "SELECT g FROM GoldModel g WHERE g.goldStatusInd = :goldStatusInd"),
    @NamedQuery(name = "GoldModel.findByUpdateDate", query = "SELECT g FROM GoldModel g WHERE g.updateDate = :updateDate"),
    @NamedQuery(name = "GoldModel.findByUpdatedBy", query = "SELECT g FROM GoldModel g WHERE g.updatedBy = :updatedBy")})
public class GoldModel implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "gold_model_id")
    private Integer goldModelId;
    @Column(name = "create_date")
    @Temporal(TemporalType.DATE)
    private Date createDate;
    @Size(max = 255)
    @Column(name = "created_by")
    private String createdBy;
    @Column(name = "gold_status_ind")
    private Integer goldStatusInd;
    @Column(name = "update_date")
    @Temporal(TemporalType.DATE)
    private Date updateDate;
    @Size(max = 255)
    @Column(name = "updated_by")
    private String updatedBy;
    @JoinColumn(name = "model_id", referencedColumnName = "model_id")
    @ManyToOne
    private Model modelId;

    public GoldModel() {
    }

    public GoldModel(Integer goldModelId) {
        this.goldModelId = goldModelId;
    }

    public Integer getGoldModelId() {
        return goldModelId;
    }

    public void setGoldModelId(Integer goldModelId) {
        this.goldModelId = goldModelId;
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

    public Integer getGoldStatusInd() {
        return goldStatusInd;
    }

    public void setGoldStatusInd(Integer goldStatusInd) {
        this.goldStatusInd = goldStatusInd;
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

    public Model getModelId() {
        return modelId;
    }

    public void setModelId(Model modelId) {
        this.modelId = modelId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (goldModelId != null ? goldModelId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof GoldModel)) {
            return false;
        }
        GoldModel other = (GoldModel) object;
        if ((this.goldModelId == null && other.goldModelId != null) || (this.goldModelId != null && !this.goldModelId.equals(other.goldModelId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.openepics.model.entity.GoldModel[ goldModelId=" + goldModelId + " ]";
    }
    
}
