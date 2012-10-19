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
import javax.xml.bind.annotation.XmlRootElement;

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
    @NamedQuery(name = "BeamParameter.findByPos", query = "SELECT b FROM BeamParameter b WHERE b.pos = :pos"),
    @NamedQuery(name = "BeamParameter.findByAlphaX", query = "SELECT b FROM BeamParameter b WHERE b.alphaX = :alphaX"),
    @NamedQuery(name = "BeamParameter.findByBetaX", query = "SELECT b FROM BeamParameter b WHERE b.betaX = :betaX"),
    @NamedQuery(name = "BeamParameter.findByNuX", query = "SELECT b FROM BeamParameter b WHERE b.nuX = :nuX"),
    @NamedQuery(name = "BeamParameter.findByEtaX", query = "SELECT b FROM BeamParameter b WHERE b.etaX = :etaX"),
    @NamedQuery(name = "BeamParameter.findByEtapX", query = "SELECT b FROM BeamParameter b WHERE b.etapX = :etapX"),
    @NamedQuery(name = "BeamParameter.findByAlphaY", query = "SELECT b FROM BeamParameter b WHERE b.alphaY = :alphaY"),
    @NamedQuery(name = "BeamParameter.findByBetaY", query = "SELECT b FROM BeamParameter b WHERE b.betaY = :betaY"),
    @NamedQuery(name = "BeamParameter.findByNuY", query = "SELECT b FROM BeamParameter b WHERE b.nuY = :nuY"),
    @NamedQuery(name = "BeamParameter.findByEtaY", query = "SELECT b FROM BeamParameter b WHERE b.etaY = :etaY"),
    @NamedQuery(name = "BeamParameter.findByEtapY", query = "SELECT b FROM BeamParameter b WHERE b.etapY = :etapY"),
    @NamedQuery(name = "BeamParameter.findByTransferMatrix", query = "SELECT b FROM BeamParameter b WHERE b.transferMatrix = :transferMatrix"),
    @NamedQuery(name = "BeamParameter.findByCoX", query = "SELECT b FROM BeamParameter b WHERE b.coX = :coX"),
    @NamedQuery(name = "BeamParameter.findByCoY", query = "SELECT b FROM BeamParameter b WHERE b.coY = :coY"),
    @NamedQuery(name = "BeamParameter.findByIndexSliceChk", query = "SELECT b FROM BeamParameter b WHERE b.indexSliceChk = :indexSliceChk"),
    @NamedQuery(name = "BeamParameter.findByEnergy", query = "SELECT b FROM BeamParameter b WHERE b.energy = :energy"),
    @NamedQuery(name = "BeamParameter.findByParticleSpecies", query = "SELECT b FROM BeamParameter b WHERE b.particleSpecies = :particleSpecies"),
    @NamedQuery(name = "BeamParameter.findByParticleMass", query = "SELECT b FROM BeamParameter b WHERE b.particleMass = :particleMass"),
    @NamedQuery(name = "BeamParameter.findByParticleCharge", query = "SELECT b FROM BeamParameter b WHERE b.particleCharge = :particleCharge"),
    @NamedQuery(name = "BeamParameter.findByBeamChargeDensity", query = "SELECT b FROM BeamParameter b WHERE b.beamChargeDensity = :beamChargeDensity"),
    @NamedQuery(name = "BeamParameter.findByBeamCurrent", query = "SELECT b FROM BeamParameter b WHERE b.beamCurrent = :beamCurrent"),
    @NamedQuery(name = "BeamParameter.findByX", query = "SELECT b FROM BeamParameter b WHERE b.x = :x"),
    @NamedQuery(name = "BeamParameter.findByXp", query = "SELECT b FROM BeamParameter b WHERE b.xp = :xp"),
    @NamedQuery(name = "BeamParameter.findByY", query = "SELECT b FROM BeamParameter b WHERE b.y = :y"),
    @NamedQuery(name = "BeamParameter.findByYp", query = "SELECT b FROM BeamParameter b WHERE b.yp = :yp"),
    @NamedQuery(name = "BeamParameter.findByZ", query = "SELECT b FROM BeamParameter b WHERE b.z = :z"),
    @NamedQuery(name = "BeamParameter.findByZp", query = "SELECT b FROM BeamParameter b WHERE b.zp = :zp"),
    @NamedQuery(name = "BeamParameter.findByEmitX", query = "SELECT b FROM BeamParameter b WHERE b.emitX = :emitX"),
    @NamedQuery(name = "BeamParameter.findByEmitY", query = "SELECT b FROM BeamParameter b WHERE b.emitY = :emitY"),
    @NamedQuery(name = "BeamParameter.findByEmitZ", query = "SELECT b FROM BeamParameter b WHERE b.emitZ = :emitZ"),
    @NamedQuery(name = "BeamParameter.findByPsiX", query = "SELECT b FROM BeamParameter b WHERE b.psiX = :psiX"),
    @NamedQuery(name = "BeamParameter.findByPsiY", query = "SELECT b FROM BeamParameter b WHERE b.psiY = :psiY"),
    @NamedQuery(name = "BeamParameter.findByNuS", query = "SELECT b FROM BeamParameter b WHERE b.nuS = :nuS")})
public class BeamParameter implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "twiss_id")
    private Integer twissId;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "pos")
    private Double pos;
    @Column(name = "alpha_x")
    private Double alphaX;
    @Column(name = "beta_x")
    private Double betaX;
    @Column(name = "nu_x")
    private Double nuX;
    @Column(name = "eta_x")
    private Double etaX;
    @Column(name = "etap_x")
    private Double etapX;
    @Column(name = "alpha_y")
    private Double alphaY;
    @Column(name = "beta_y")
    private Double betaY;
    @Column(name = "nu_y")
    private Double nuY;
    @Column(name = "eta_y")
    private Double etaY;
    @Column(name = "etap_y")
    private Double etapY;
    @Column(name = "transfer_matrix")
    private String transferMatrix;
    @Column(name = "co_x")
    private Double coX;
    @Column(name = "co_y")
    private Double coY;
    @Column(name = "index_slice_chk")
    private Integer indexSliceChk;
    @Column(name = "energy")
    private Double energy;
    @Column(name = "particle_species")
    private String particleSpecies;
    @Column(name = "particle_mass")
    private Double particleMass;
    @Column(name = "particle_charge")
    private Integer particleCharge;
    @Column(name = "beam_charge_density")
    private Double beamChargeDensity;
    @Column(name = "beam_current")
    private Double beamCurrent;
    @Column(name = "x")
    private Double x;
    @Column(name = "xp")
    private Double xp;
    @Column(name = "y")
    private Double y;
    @Column(name = "yp")
    private Double yp;
    @Column(name = "z")
    private Double z;
    @Column(name = "zp")
    private Double zp;
    @Column(name = "emit_x")
    private Double emitX;
    @Column(name = "emit_y")
    private Double emitY;
    @Column(name = "emit_z")
    private Double emitZ;
    @Column(name = "psi_x")
    private Double psiX;
    @Column(name = "psi_y")
    private Double psiY;
    @Column(name = "nu_s")
    private Double nuS;
    @JoinColumn(name = "model_id", referencedColumnName = "model_id")
    @ManyToOne
    private Model modelId;
    @JoinColumn(name = "element_id", referencedColumnName = "element_id")
    @ManyToOne
    private Element elementId;

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

    public Double getPos() {
        return pos;
    }

    public void setPos(Double pos) {
        this.pos = pos;
    }

    public Double getAlphaX() {
        return alphaX;
    }

    public void setAlphaX(Double alphaX) {
        this.alphaX = alphaX;
    }

    public Double getBetaX() {
        return betaX;
    }

    public void setBetaX(Double betaX) {
        this.betaX = betaX;
    }

    public Double getNuX() {
        return nuX;
    }

    public void setNuX(Double nuX) {
        this.nuX = nuX;
    }

    public Double getEtaX() {
        return etaX;
    }

    public void setEtaX(Double etaX) {
        this.etaX = etaX;
    }

    public Double getEtapX() {
        return etapX;
    }

    public void setEtapX(Double etapX) {
        this.etapX = etapX;
    }

    public Double getAlphaY() {
        return alphaY;
    }

    public void setAlphaY(Double alphaY) {
        this.alphaY = alphaY;
    }

    public Double getBetaY() {
        return betaY;
    }

    public void setBetaY(Double betaY) {
        this.betaY = betaY;
    }

    public Double getNuY() {
        return nuY;
    }

    public void setNuY(Double nuY) {
        this.nuY = nuY;
    }

    public Double getEtaY() {
        return etaY;
    }

    public void setEtaY(Double etaY) {
        this.etaY = etaY;
    }

    public Double getEtapY() {
        return etapY;
    }

    public void setEtapY(Double etapY) {
        this.etapY = etapY;
    }

    public String getTransferMatrix() {
        return transferMatrix;
    }

    public void setTransferMatrix(String transferMatrix) {
        this.transferMatrix = transferMatrix;
    }

    public Double getCoX() {
        return coX;
    }

    public void setCoX(Double coX) {
        this.coX = coX;
    }

    public Double getCoY() {
        return coY;
    }

    public void setCoY(Double coY) {
        this.coY = coY;
    }

    public Integer getIndexSliceChk() {
        return indexSliceChk;
    }

    public void setIndexSliceChk(Integer indexSliceChk) {
        this.indexSliceChk = indexSliceChk;
    }

    public Double getEnergy() {
        return energy;
    }

    public void setEnergy(Double energy) {
        this.energy = energy;
    }

    public String getParticleSpecies() {
        return particleSpecies;
    }

    public void setParticleSpecies(String particleSpecies) {
        this.particleSpecies = particleSpecies;
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

    public Double getBeamChargeDensity() {
        return beamChargeDensity;
    }

    public void setBeamChargeDensity(Double beamChargeDensity) {
        this.beamChargeDensity = beamChargeDensity;
    }

    public Double getBeamCurrent() {
        return beamCurrent;
    }

    public void setBeamCurrent(Double beamCurrent) {
        this.beamCurrent = beamCurrent;
    }

    public Double getX() {
        return x;
    }

    public void setX(Double x) {
        this.x = x;
    }

    public Double getXp() {
        return xp;
    }

    public void setXp(Double xp) {
        this.xp = xp;
    }

    public Double getY() {
        return y;
    }

    public void setY(Double y) {
        this.y = y;
    }

    public Double getYp() {
        return yp;
    }

    public void setYp(Double yp) {
        this.yp = yp;
    }

    public Double getZ() {
        return z;
    }

    public void setZ(Double z) {
        this.z = z;
    }

    public Double getZp() {
        return zp;
    }

    public void setZp(Double zp) {
        this.zp = zp;
    }

    public Double getEmitX() {
        return emitX;
    }

    public void setEmitX(Double emitX) {
        this.emitX = emitX;
    }

    public Double getEmitY() {
        return emitY;
    }

    public void setEmitY(Double emitY) {
        this.emitY = emitY;
    }

    public Double getEmitZ() {
        return emitZ;
    }

    public void setEmitZ(Double emitZ) {
        this.emitZ = emitZ;
    }

    public Double getPsiX() {
        return psiX;
    }

    public void setPsiX(Double psiX) {
        this.psiX = psiX;
    }

    public Double getPsiY() {
        return psiY;
    }

    public void setPsiY(Double psiY) {
        this.psiY = psiY;
    }

    public Double getNuS() {
        return nuS;
    }

    public void setNuS(Double nuS) {
        this.nuS = nuS;
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
