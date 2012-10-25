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
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author chu
 */
@Entity
@Table(name = "gold_model")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "GoldModel.findAll", query = "SELECT g FROM GoldModel g"),
    @NamedQuery(name = "GoldModel.findByGoldModelId", query = "SELECT g FROM GoldModel g WHERE g.goldModelId = :goldModelId"),
    @NamedQuery(name = "GoldModel.findByCreatedBy", query = "SELECT g FROM GoldModel g WHERE g.createdBy = :createdBy"),
    @NamedQuery(name = "GoldModel.findByCreateDate", query = "SELECT g FROM GoldModel g WHERE g.createDate = :createDate"),
    @NamedQuery(name = "GoldModel.findByUpdatedBy", query = "SELECT g FROM GoldModel g WHERE g.updatedBy = :updatedBy"),
    @NamedQuery(name = "GoldModel.findByUpdateDate", query = "SELECT g FROM GoldModel g WHERE g.updateDate = :updateDate"),
    @NamedQuery(name = "GoldModel.findByGoldStatusInd", query = "SELECT g FROM GoldModel g WHERE g.goldStatusInd = :goldStatusInd")})
public class GoldModel implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "gold_model_id")
    private Integer goldModelId;
    @Column(name = "created_by")
    private String createdBy;
    @Column(name = "create_date")
    @Temporal(TemporalType.DATE)
    private Date createDate;
    @Column(name = "updated_by")
    private String updatedBy;
    @Column(name = "update_date")
    @Temporal(TemporalType.DATE)
    private Date updateDate;
    @Column(name = "gold_status_ind")
    private Integer goldStatusInd;
    @JoinColumn(name = "model_id", referencedColumnName = "model_id")
    @ManyToOne
    private Model modelId;

    public static int PRESENT = 0;
    
    public static int PREVIOUS = 1;

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

    public Integer getGoldStatusInd() {
        return goldStatusInd;
    }

    public void setGoldStatusInd(Integer goldStatusInd) {
        this.goldStatusInd = goldStatusInd;
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
