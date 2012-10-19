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
 * @author paul
 */
@Entity
@Table(name = "model")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Model.findAll", query = "SELECT m FROM Model m"),
    @NamedQuery(name = "Model.findByModelId", query = "SELECT m FROM Model m WHERE m.modelId = :modelId"),
    @NamedQuery(name = "Model.findByModelName", query = "SELECT m FROM Model m WHERE m.modelName = :modelName"),
    @NamedQuery(name = "Model.findByModelDesc", query = "SELECT m FROM Model m WHERE m.modelDesc = :modelDesc"),
    @NamedQuery(name = "Model.findByCreatedBy", query = "SELECT m FROM Model m WHERE m.createdBy = :createdBy"),
    @NamedQuery(name = "Model.findByCreateDate", query = "SELECT m FROM Model m WHERE m.createDate = :createDate"),
    @NamedQuery(name = "Model.findByUpdatedBy", query = "SELECT m FROM Model m WHERE m.updatedBy = :updatedBy"),
    @NamedQuery(name = "Model.findByUpdateDate", query = "SELECT m FROM Model m WHERE m.updateDate = :updateDate"),
    @NamedQuery(name = "Model.findByTuneX", query = "SELECT m FROM Model m WHERE m.tuneX = :tuneX"),
    @NamedQuery(name = "Model.findByTuneY", query = "SELECT m FROM Model m WHERE m.tuneY = :tuneY"),
    @NamedQuery(name = "Model.findByChromeX0", query = "SELECT m FROM Model m WHERE m.chromeX0 = :chromeX0"),
    @NamedQuery(name = "Model.findByChromeX1", query = "SELECT m FROM Model m WHERE m.chromeX1 = :chromeX1"),
    @NamedQuery(name = "Model.findByChromeX2", query = "SELECT m FROM Model m WHERE m.chromeX2 = :chromeX2"),
    @NamedQuery(name = "Model.findByChromeY0", query = "SELECT m FROM Model m WHERE m.chromeY0 = :chromeY0"),
    @NamedQuery(name = "Model.findByChromeY1", query = "SELECT m FROM Model m WHERE m.chromeY1 = :chromeY1"),
    @NamedQuery(name = "Model.findByChromeY2", query = "SELECT m FROM Model m WHERE m.chromeY2 = :chromeY2"),
    @NamedQuery(name = "Model.findByFinalBeamEnergy", query = "SELECT m FROM Model m WHERE m.finalBeamEnergy = :finalBeamEnergy")})
public class Model implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "model_id")
    private Integer modelId;
    @Column(name = "model_name")
    private String modelName;
    @Column(name = "model_desc")
    private String modelDesc;
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
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "tune_x")
    private Double tuneX;
    @Column(name = "tune_y")
    private Double tuneY;
    @Column(name = "chrome_x_0")
    private Double chromeX0;
    @Column(name = "chrome_x_1")
    private Double chromeX1;
    @Column(name = "chrome_x_2")
    private Double chromeX2;
    @Column(name = "chrome_y_0")
    private Double chromeY0;
    @Column(name = "chrome_y_1")
    private Double chromeY1;
    @Column(name = "chrome_y_2")
    private Double chromeY2;
    @Column(name = "final_beam_energy")
    private Double finalBeamEnergy;
    @JoinColumn(name = "model_code_id", referencedColumnName = "model_code_id")
    @ManyToOne
    private ModelCode modelCodeId;
    @JoinColumn(name = "lattice_id", referencedColumnName = "lattice_id")
    @ManyToOne
    private Lattice latticeId;
    @JoinColumn(name = "model_line_id", referencedColumnName = "model_line_id")
    @ManyToOne
    private ModelLine modelLineId;
    @OneToMany(mappedBy = "modelId")
    private Collection<BeamParameter> beamParameterCollection;
    @OneToMany(mappedBy = "modelId")
    private Collection<ElementProp> elementPropCollection;
    @OneToMany(mappedBy = "modelId")
    private Collection<GoldModel> goldModelCollection;

    public Model() {
    }

    public Model(Integer modelId) {
        this.modelId = modelId;
    }

    public Integer getModelId() {
        return modelId;
    }

    public void setModelId(Integer modelId) {
        this.modelId = modelId;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public String getModelDesc() {
        return modelDesc;
    }

    public void setModelDesc(String modelDesc) {
        this.modelDesc = modelDesc;
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

    public Double getTuneX() {
        return tuneX;
    }

    public void setTuneX(Double tuneX) {
        this.tuneX = tuneX;
    }

    public Double getTuneY() {
        return tuneY;
    }

    public void setTuneY(Double tuneY) {
        this.tuneY = tuneY;
    }

    public Double getChromeX0() {
        return chromeX0;
    }

    public void setChromeX0(Double chromeX0) {
        this.chromeX0 = chromeX0;
    }

    public Double getChromeX1() {
        return chromeX1;
    }

    public void setChromeX1(Double chromeX1) {
        this.chromeX1 = chromeX1;
    }

    public Double getChromeX2() {
        return chromeX2;
    }

    public void setChromeX2(Double chromeX2) {
        this.chromeX2 = chromeX2;
    }

    public Double getChromeY0() {
        return chromeY0;
    }

    public void setChromeY0(Double chromeY0) {
        this.chromeY0 = chromeY0;
    }

    public Double getChromeY1() {
        return chromeY1;
    }

    public void setChromeY1(Double chromeY1) {
        this.chromeY1 = chromeY1;
    }

    public Double getChromeY2() {
        return chromeY2;
    }

    public void setChromeY2(Double chromeY2) {
        this.chromeY2 = chromeY2;
    }

    public Double getFinalBeamEnergy() {
        return finalBeamEnergy;
    }

    public void setFinalBeamEnergy(Double finalBeamEnergy) {
        this.finalBeamEnergy = finalBeamEnergy;
    }

    public ModelCode getModelCodeId() {
        return modelCodeId;
    }

    public void setModelCodeId(ModelCode modelCodeId) {
        this.modelCodeId = modelCodeId;
    }

    public Lattice getLatticeId() {
        return latticeId;
    }

    public void setLatticeId(Lattice latticeId) {
        this.latticeId = latticeId;
    }

    public ModelLine getModelLineId() {
        return modelLineId;
    }

    public void setModelLineId(ModelLine modelLineId) {
        this.modelLineId = modelLineId;
    }

    @XmlTransient
    public Collection<BeamParameter> getBeamParameterCollection() {
        return beamParameterCollection;
    }

    public void setBeamParameterCollection(Collection<BeamParameter> beamParameterCollection) {
        this.beamParameterCollection = beamParameterCollection;
    }

    @XmlTransient
    public Collection<ElementProp> getElementPropCollection() {
        return elementPropCollection;
    }

    public void setElementPropCollection(Collection<ElementProp> elementPropCollection) {
        this.elementPropCollection = elementPropCollection;
    }

    @XmlTransient
    public Collection<GoldModel> getGoldModelCollection() {
        return goldModelCollection;
    }

    public void setGoldModelCollection(Collection<GoldModel> goldModelCollection) {
        this.goldModelCollection = goldModelCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (modelId != null ? modelId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Model)) {
            return false;
        }
        Model other = (Model) object;
        if ((this.modelId == null && other.modelId != null) || (this.modelId != null && !this.modelId.equals(other.modelId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.openepics.model.entity.Model[ modelId=" + modelId + " ]";
    }
    
}
