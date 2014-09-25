/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author chu
 */
@Entity
@Table(name = "element")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Element.findAll", query = "SELECT e FROM Element e"),
    @NamedQuery(name = "Element.findByElementId", query = "SELECT e FROM Element e WHERE e.elementId = :elementId"),
    @NamedQuery(name = "Element.findByCreatedBy", query = "SELECT e FROM Element e WHERE e.createdBy = :createdBy"),
    @NamedQuery(name = "Element.findByDx", query = "SELECT e FROM Element e WHERE e.dx = :dx"),
    @NamedQuery(name = "Element.findByDy", query = "SELECT e FROM Element e WHERE e.dy = :dy"),
    @NamedQuery(name = "Element.findByDz", query = "SELECT e FROM Element e WHERE e.dz = :dz"),
    @NamedQuery(name = "Element.findByElementName", query = "SELECT e FROM Element e WHERE e.elementName = :elementName"),
    @NamedQuery(name = "Element.findByInsertDate", query = "SELECT e FROM Element e WHERE e.insertDate = :insertDate"),
    @NamedQuery(name = "Element.findByLen", query = "SELECT e FROM Element e WHERE e.len = :len"),
    @NamedQuery(name = "Element.findByPitch", query = "SELECT e FROM Element e WHERE e.pitch = :pitch"),
    @NamedQuery(name = "Element.findByPos", query = "SELECT e FROM Element e WHERE e.pos = :pos"),
    @NamedQuery(name = "Element.findByRoll", query = "SELECT e FROM Element e WHERE e.roll = :roll"),
    @NamedQuery(name = "Element.findByS", query = "SELECT e FROM Element e WHERE e.s = :s"),
    @NamedQuery(name = "Element.findByYaw", query = "SELECT e FROM Element e WHERE e.yaw = :yaw")})
public class Element implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "element_id")
    private Integer elementId;
    @Size(max = 255)
    @Column(name = "created_by")
    private String createdBy;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "dx")
    private Double dx;
    @Column(name = "dy")
    private Double dy;
    @Column(name = "dz")
    private Double dz;
    @Size(max = 255)
    @Column(name = "element_name")
    private String elementName;
    @Column(name = "insert_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date insertDate;
    @Column(name = "len")
    private Double len;
    @Column(name = "pitch")
    private Double pitch;
    @Column(name = "pos")
    private Double pos;
    @Column(name = "roll")
    private Double roll;
    @Column(name = "s")
    private Double s;
    @Column(name = "yaw")
    private Double yaw;
    @JoinColumn(name = "element_type_id", referencedColumnName = "element_type_id")
    @ManyToOne
    private ElementType elementTypeId;
    @JoinColumn(name = "beamline_sequence_id", referencedColumnName = "beamline_sequence_id")
    @ManyToOne
    private BeamlineSequence beamlineSequenceId;
    @OneToMany(mappedBy = "elementId")
    private Collection<ElementInstallDevice> elementInstallDeviceCollection;
    @OneToMany(mappedBy = "elementId")
    private Collection<BeamParameter> beamParameterCollection;
    @OneToMany(mappedBy = "elementId")
    private Collection<ElementProp> elementPropCollection;
    @OneToMany(mappedBy = "cavityId")
    private Collection<RfGap> rfGapCollection;

    public Element() {
    }

    public Element(Integer elementId) {
        this.elementId = elementId;
    }

    public Integer getElementId() {
        return elementId;
    }

    public void setElementId(Integer elementId) {
        this.elementId = elementId;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Double getDx() {
        return dx;
    }

    public void setDx(Double dx) {
        this.dx = dx;
    }

    public Double getDy() {
        return dy;
    }

    public void setDy(Double dy) {
        this.dy = dy;
    }

    public Double getDz() {
        return dz;
    }

    public void setDz(Double dz) {
        this.dz = dz;
    }

    public String getElementName() {
        return elementName;
    }

    public void setElementName(String elementName) {
        this.elementName = elementName;
    }

    public Date getInsertDate() {
        return insertDate;
    }

    public void setInsertDate(Date insertDate) {
        this.insertDate = insertDate;
    }

    public Double getLen() {
        return len;
    }

    public void setLen(Double len) {
        this.len = len;
    }

    public Double getPitch() {
        return pitch;
    }

    public void setPitch(Double pitch) {
        this.pitch = pitch;
    }

    public Double getPos() {
        return pos;
    }

    public void setPos(Double pos) {
        this.pos = pos;
    }

    public Double getRoll() {
        return roll;
    }

    public void setRoll(Double roll) {
        this.roll = roll;
    }

    public Double getS() {
        return s;
    }

    public void setS(Double s) {
        this.s = s;
    }

    public Double getYaw() {
        return yaw;
    }

    public void setYaw(Double yaw) {
        this.yaw = yaw;
    }

    public ElementType getElementTypeId() {
        return elementTypeId;
    }

    public void setElementTypeId(ElementType elementTypeId) {
        this.elementTypeId = elementTypeId;
    }

    public BeamlineSequence getBeamlineSequenceId() {
        return beamlineSequenceId;
    }

    public void setBeamlineSequenceId(BeamlineSequence beamlineSequenceId) {
        this.beamlineSequenceId = beamlineSequenceId;
    }

    @XmlTransient
    public Collection<ElementInstallDevice> getElementInstallDeviceCollection() {
        return elementInstallDeviceCollection;
    }

    public void setElementInstallDeviceCollection(Collection<ElementInstallDevice> elementInstallDeviceCollection) {
        this.elementInstallDeviceCollection = elementInstallDeviceCollection;
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
    public Collection<RfGap> getRfGapCollection() {
        return rfGapCollection;
    }

    public void setRfGapCollection(Collection<RfGap> rfGapCollection) {
        this.rfGapCollection = rfGapCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (elementId != null ? elementId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Element)) {
            return false;
        }
        Element other = (Element) object;
        if ((this.elementId == null && other.elementId != null) || (this.elementId != null && !this.elementId.equals(other.elementId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.openepics.model.entity.Element[ elementId=" + elementId + " ]";
    }
    
}
