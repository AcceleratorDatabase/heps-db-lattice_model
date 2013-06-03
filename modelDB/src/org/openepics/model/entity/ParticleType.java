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
 * @author lv
 */
@Entity
@Table(name = "particle_type")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ParticleType.findAll", query = "SELECT p FROM ParticleType p"),
    @NamedQuery(name = "ParticleType.findByParticleTypeId", query = "SELECT p FROM ParticleType p WHERE p.particleTypeId = :particleTypeId"),
    @NamedQuery(name = "ParticleType.findByParticleName", query = "SELECT p FROM ParticleType p WHERE p.particleName = :particleName"),
    @NamedQuery(name = "ParticleType.findByParticleMass", query = "SELECT p FROM ParticleType p WHERE p.particleMass = :particleMass"),
    @NamedQuery(name = "ParticleType.findByParticleCharge", query = "SELECT p FROM ParticleType p WHERE p.particleCharge = :particleCharge")})
public class ParticleType implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "particle_type_id")
    private Integer particleTypeId;
    @Size(max = 45)
    @Column(name = "particle_name")
    private String particleName;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "particle_mass")
    private Double particleMass;
    @Column(name = "particle_charge")
    private Integer particleCharge;
    @OneToMany(mappedBy = "particleType")
    private Collection<BeamParameter> beamParameterCollection;

    public ParticleType() {
    }

    public ParticleType(Integer particleTypeId) {
        this.particleTypeId = particleTypeId;
    }

    public Integer getParticleTypeId() {
        return particleTypeId;
    }

    public void setParticleTypeId(Integer particleTypeId) {
        this.particleTypeId = particleTypeId;
    }

    public String getParticleName() {
        return particleName;
    }

    public void setParticleName(String particleName) {
        this.particleName = particleName;
    }

    public Double getParticleMass() {
        return particleMass;
    }

    public void setParticleMass(Double particleMass) {
        this.particleMass = particleMass;
    }

    public Integer getParticleCharge() {
        return particleCharge;
    }

    public void setParticleCharge(Integer particleCharge) {
        this.particleCharge = particleCharge;
    }

    @XmlTransient
    public Collection<BeamParameter> getBeamParameterCollection() {
        return beamParameterCollection;
    }

    public void setBeamParameterCollection(Collection<BeamParameter> beamParameterCollection) {
        this.beamParameterCollection = beamParameterCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (particleTypeId != null ? particleTypeId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ParticleType)) {
            return false;
        }
        ParticleType other = (ParticleType) object;
        if ((this.particleTypeId == null && other.particleTypeId != null) || (this.particleTypeId != null && !this.particleTypeId.equals(other.particleTypeId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.openepics.model.entity.ParticleType[ particleTypeId=" + particleTypeId + " ]";
    }
    
}
