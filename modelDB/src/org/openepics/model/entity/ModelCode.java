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
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author paul
 */
@Entity
@Table(name = "model_code")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ModelCode.findAll", query = "SELECT m FROM ModelCode m"),
    @NamedQuery(name = "ModelCode.findByModelCodeId", query = "SELECT m FROM ModelCode m WHERE m.modelCodeId = :modelCodeId"),
    @NamedQuery(name = "ModelCode.findByCodeName", query = "SELECT m FROM ModelCode m WHERE m.codeName = :codeName"),
    @NamedQuery(name = "ModelCode.findByAlgorithm", query = "SELECT m FROM ModelCode m WHERE m.algorithm = :algorithm")})
public class ModelCode implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "model_code_id")
    private Integer modelCodeId;
    @Column(name = "code_name")
    private String codeName;
    @Column(name = "algorithm")
    private String algorithm;
    @OneToMany(mappedBy = "modelCodeId")
    private Collection<Model> modelCollection;

    public ModelCode() {
    }

    public ModelCode(Integer modelCodeId) {
        this.modelCodeId = modelCodeId;
    }

    public Integer getModelCodeId() {
        return modelCodeId;
    }

    public void setModelCodeId(Integer modelCodeId) {
        this.modelCodeId = modelCodeId;
    }

    public String getCodeName() {
        return codeName;
    }

    public void setCodeName(String codeName) {
        this.codeName = codeName;
    }

    public String getAlgorithm() {
        return algorithm;
    }

    public void setAlgorithm(String algorithm) {
        this.algorithm = algorithm;
    }

    @XmlTransient
    public Collection<Model> getModelCollection() {
        return modelCollection;
    }

    public void setModelCollection(Collection<Model> modelCollection) {
        this.modelCollection = modelCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (modelCodeId != null ? modelCodeId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ModelCode)) {
            return false;
        }
        ModelCode other = (ModelCode) object;
        if ((this.modelCodeId == null && other.modelCodeId != null) || (this.modelCodeId != null && !this.modelCodeId.equals(other.modelCodeId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.openepics.model.entity.ModelCode[ modelCodeId=" + modelCodeId + " ]";
    }
    
}
