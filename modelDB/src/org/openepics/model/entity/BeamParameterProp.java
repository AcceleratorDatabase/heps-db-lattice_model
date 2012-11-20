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
@Table(name = "beam_parameter_prop")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "BeamParameterProp.findAll", query = "SELECT b FROM BeamParameterProp b"),
    @NamedQuery(name = "BeamParameterProp.findByBeamParameterPropId", query = "SELECT b FROM BeamParameterProp b WHERE b.beamParameterPropId = :beamParameterPropId"),
    @NamedQuery(name = "BeamParameterProp.findByPropertyName", query = "SELECT b FROM BeamParameterProp b WHERE b.propertyName = :propertyName"),
    @NamedQuery(name = "BeamParameterProp.findByPropertyDatatype", query = "SELECT b FROM BeamParameterProp b WHERE b.propertyDatatype = :propertyDatatype"),
    @NamedQuery(name = "BeamParameterProp.findByDescription", query = "SELECT b FROM BeamParameterProp b WHERE b.description = :description"),
    @NamedQuery(name = "BeamParameterProp.findByBeamParameterInt", query = "SELECT b FROM BeamParameterProp b WHERE b.beamParameterInt = :beamParameterInt"),
    @NamedQuery(name = "BeamParameterProp.findByBeamParameterDouble", query = "SELECT b FROM BeamParameterProp b WHERE b.beamParameterDouble = :beamParameterDouble"),
    @NamedQuery(name = "BeamParameterProp.findByBeamParameterString", query = "SELECT b FROM BeamParameterProp b WHERE b.beamParameterString = :beamParameterString"),
    @NamedQuery(name = "BeamParameterProp.findByTrnsferMatrix", query = "SELECT b FROM BeamParameterProp b WHERE b.trnsferMatrix = :trnsferMatrix")})
public class BeamParameterProp implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "beam_parameter_prop_id")
    private Integer beamParameterPropId;
    @Size(max = 45)
    @Column(name = "property_name")
    private String propertyName;
    @Size(max = 45)
    @Column(name = "property_datatype")
    private String propertyDatatype;
    @Size(max = 45)
    @Column(name = "description")
    private String description;
    @Column(name = "beam_parameter_int")
    private Integer beamParameterInt;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "beam_parameter_double")
    private Double beamParameterDouble;
    @Size(max = 45)
    @Column(name = "beam_parameter_string")
    private String beamParameterString;
    @Size(max = 2047)
    @Column(name = "trnsfer_matrix")
    private String trnsferMatrix;
    @JoinColumn(name = "beam_parameter_id", referencedColumnName = "twiss_id")
    @ManyToOne
    private BeamParameter beamParameterId;

    public BeamParameterProp() {
    }

    public BeamParameterProp(Integer beamParameterPropId) {
        this.beamParameterPropId = beamParameterPropId;
    }

    public Integer getBeamParameterPropId() {
        return beamParameterPropId;
    }

    public void setBeamParameterPropId(Integer beamParameterPropId) {
        this.beamParameterPropId = beamParameterPropId;
    }

    public String getPropertyName() {
        return propertyName;
    }

    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }

    public String getPropertyDatatype() {
        return propertyDatatype;
    }

    public void setPropertyDatatype(String propertyDatatype) {
        this.propertyDatatype = propertyDatatype;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getBeamParameterInt() {
        return beamParameterInt;
    }

    public void setBeamParameterInt(Integer beamParameterInt) {
        this.beamParameterInt = beamParameterInt;
    }

    public Double getBeamParameterDouble() {
        return beamParameterDouble;
    }

    public void setBeamParameterDouble(Double beamParameterDouble) {
        this.beamParameterDouble = beamParameterDouble;
    }

    public String getBeamParameterString() {
        return beamParameterString;
    }

    public void setBeamParameterString(String beamParameterString) {
        this.beamParameterString = beamParameterString;
    }

    public String getTrnsferMatrix() {
        return trnsferMatrix;
    }

    public void setTrnsferMatrix(String trnsferMatrix) {
        this.trnsferMatrix = trnsferMatrix;
    }

    public BeamParameter getBeamParameterId() {
        return beamParameterId;
    }

    public void setBeamParameterId(BeamParameter beamParameterId) {
        this.beamParameterId = beamParameterId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (beamParameterPropId != null ? beamParameterPropId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof BeamParameterProp)) {
            return false;
        }
        BeamParameterProp other = (BeamParameterProp) object;
        if ((this.beamParameterPropId == null && other.beamParameterPropId != null) || (this.beamParameterPropId != null && !this.beamParameterPropId.equals(other.beamParameterPropId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.openepics.model.entity.BeamParameterProp[ beamParameterPropId=" + beamParameterPropId + " ]";
    }
    
}
