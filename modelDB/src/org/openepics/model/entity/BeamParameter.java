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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author paul
 */
@Entity
@Table(name = "beam_parameter")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "BeamParameter.findAll", query = "SELECT b FROM BeamParameter b"),
    @NamedQuery(name = "BeamParameter.findByTwissId", query = "SELECT b FROM BeamParameter b WHERE b.twissId = :twissId"),
    @NamedQuery(name = "BeamParameter.findBySpeciesName", query = "SELECT b FROM BeamParameter b WHERE b.speciesName = :speciesName"),
    @NamedQuery(name = "BeamParameter.findBySpeciesMass", query = "SELECT b FROM BeamParameter b WHERE b.speciesMass = :speciesMass"),
    @NamedQuery(name = "BeamParameter.findBySpeciesCharge", query = "SELECT b FROM BeamParameter b WHERE b.speciesCharge = :speciesCharge")})
public class BeamParameter implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "twiss_id")
    private Integer twissId;
    @Size(max = 45)
    @Column(name = "species_name")
    private String speciesName;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "species_mass")
    private Double speciesMass;
    @Column(name = "species_charge")
    private Double speciesCharge;
    @JoinColumn(name = "model_id", referencedColumnName = "model_id")
    @ManyToOne
    private Model modelId;
    @JoinColumn(name = "element_id", referencedColumnName = "element_id")
    @ManyToOne
    private Element elementId;
    @OneToMany(mappedBy = "beamParameterId")
    private Collection<BeamParameterProp> beamParameterPropCollection;

    public BeamParameter() {
    }

    public BeamParameter(Integer twissId) {
        this.twissId = twissId;
    }

    public Integer getTwissId() {
        return twissId;
    }

    public void setTwissId(Integer twissId) {
        this.twissId = twissId;
    }

    public String getSpeciesName() {
        return speciesName;
    }

    public void setSpeciesName(String speciesName) {
        this.speciesName = speciesName;
    }

    public Double getSpeciesMass() {
        return speciesMass;
    }

    public void setSpeciesMass(Double speciesMass) {
        this.speciesMass = speciesMass;
    }

    public Double getSpeciesCharge() {
        return speciesCharge;
    }

    public void setSpeciesCharge(Double speciesCharge) {
        this.speciesCharge = speciesCharge;
    }

    public Model getModelId() {
        return modelId;
    }

    public void setModelId(Model modelId) {
        this.modelId = modelId;
    }

    public Element getElementId() {
        return elementId;
    }

    public void setElementId(Element elementId) {
        this.elementId = elementId;
    }

    @XmlTransient
    public Collection<BeamParameterProp> getBeamParameterPropCollection() {
        return beamParameterPropCollection;
    }

    public void setBeamParameterPropCollection(Collection<BeamParameterProp> beamParameterPropCollection) {
        this.beamParameterPropCollection = beamParameterPropCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (twissId != null ? twissId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof BeamParameter)) {
            return false;
        }
        BeamParameter other = (BeamParameter) object;
        if ((this.twissId == null && other.twissId != null) || (this.twissId != null && !this.twissId.equals(other.twissId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.openepics.model.entity.BeamParameter[ twissId=" + twissId + " ]";
    }
    
}
