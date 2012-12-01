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
@Table(name = "rf_gap")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "RfGap.findAll", query = "SELECT r FROM RfGap r"),
    @NamedQuery(name = "RfGap.findByRfGapId", query = "SELECT r FROM RfGap r WHERE r.rfGapId = :rfGapId"),
    @NamedQuery(name = "RfGap.findByPos", query = "SELECT r FROM RfGap r WHERE r.pos = :pos"),
    @NamedQuery(name = "RfGap.findByTtf", query = "SELECT r FROM RfGap r WHERE r.ttf = :ttf"),
    @NamedQuery(name = "RfGap.findByAmpFactor", query = "SELECT r FROM RfGap r WHERE r.ampFactor = :ampFactor"),
    @NamedQuery(name = "RfGap.findByEndCellind", query = "SELECT r FROM RfGap r WHERE r.endCellind = :endCellind"),
    @NamedQuery(name = "RfGap.findByGapOffset", query = "SELECT r FROM RfGap r WHERE r.gapOffset = :gapOffset"),
    @NamedQuery(name = "RfGap.findByLen", query = "SELECT r FROM RfGap r WHERE r.len = :len"),
    @NamedQuery(name = "RfGap.findByPhaseFactor", query = "SELECT r FROM RfGap r WHERE r.phaseFactor = :phaseFactor"),
    @NamedQuery(name = "RfGap.findByGapName", query = "SELECT r FROM RfGap r WHERE r.gapName = :gapName")})
public class RfGap implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "rf_gap_id")
    private Integer rfGapId;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "pos")
    private Double pos;
    @Column(name = "TTF")
    private Double ttf;
    @Column(name = "ampFactor")
    private Double ampFactor;
    @Column(name = "endCell_ind")
    private Integer endCellind;
    @Column(name = "gapOffset")
    private Double gapOffset;
    @Column(name = "len")
    private Double len;
    @Column(name = "phaseFactor")
    private Double phaseFactor;
    @Size(max = 45)
    @Column(name = "gap_name")
    private String gapName;
    @JoinColumn(name = "cavity_id", referencedColumnName = "element_id")
    @ManyToOne
    private Element cavityId;

    public RfGap() {
    }

    public RfGap(Integer rfGapId) {
        this.rfGapId = rfGapId;
    }

    public Integer getRfGapId() {
        return rfGapId;
    }

    public void setRfGapId(Integer rfGapId) {
        this.rfGapId = rfGapId;
    }

    public Double getPos() {
        return pos;
    }

    public void setPos(Double pos) {
        this.pos = pos;
    }

    public Double getTtf() {
        return ttf;
    }

    public void setTtf(Double ttf) {
        this.ttf = ttf;
    }

    public Double getAmpFactor() {
        return ampFactor;
    }

    public void setAmpFactor(Double ampFactor) {
        this.ampFactor = ampFactor;
    }

    public Integer getEndCellind() {
        return endCellind;
    }

    public void setEndCellind(Integer endCellind) {
        this.endCellind = endCellind;
    }

    public Double getGapOffset() {
        return gapOffset;
    }

    public void setGapOffset(Double gapOffset) {
        this.gapOffset = gapOffset;
    }

    public Double getLen() {
        return len;
    }

    public void setLen(Double len) {
        this.len = len;
    }

    public Double getPhaseFactor() {
        return phaseFactor;
    }

    public void setPhaseFactor(Double phaseFactor) {
        this.phaseFactor = phaseFactor;
    }

    public String getGapName() {
        return gapName;
    }

    public void setGapName(String gapName) {
        this.gapName = gapName;
    }

    public Element getCavityId() {
        return cavityId;
    }

    public void setCavityId(Element cavityId) {
        this.cavityId = cavityId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (rfGapId != null ? rfGapId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RfGap)) {
            return false;
        }
        RfGap other = (RfGap) object;
        if ((this.rfGapId == null && other.rfGapId != null) || (this.rfGapId != null && !this.rfGapId.equals(other.rfGapId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.openepics.model.entity.RfGap[ rfGapId=" + rfGapId + " ]";
    }
    
}
