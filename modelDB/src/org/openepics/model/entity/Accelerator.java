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
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author chu
 */
@Entity
@Table(name = "accelerator")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Accelerator.findAll", query = "SELECT a FROM Accelerator a"),
    @NamedQuery(name = "Accelerator.findByAcceleratorid", query = "SELECT a FROM Accelerator a WHERE a.acceleratorid = :acceleratorid"),
    @NamedQuery(name = "Accelerator.findByAcceleratorName", query = "SELECT a FROM Accelerator a WHERE a.acceleratorName = :acceleratorName"),
    @NamedQuery(name = "Accelerator.findByAcceleratorDescription", query = "SELECT a FROM Accelerator a WHERE a.acceleratorDescription = :acceleratorDescription"),
    @NamedQuery(name = "Accelerator.findByDate", query = "SELECT a FROM Accelerator a WHERE a.createDate = :createDate")})
public class Accelerator implements Serializable {
    @OneToMany(mappedBy = "acceleratorId")
    private Collection<BeamlineSequence> beamlineSequenceCollection;
    @Column(name = "create_date")
    @Temporal(TemporalType.DATE)
    private Date createDate;
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "Accelerator_id")
    private Integer acceleratorid;
    @Size(max = 45)
    @Column(name = "accelerator_name")
    private String acceleratorName;
    @Size(max = 255)
    @Column(name = "accelerator_description")
    private String acceleratorDescription;

    public Accelerator() {
    }

    public Accelerator(Integer acceleratorid) {
        this.acceleratorid = acceleratorid;
    }

    public Integer getAcceleratorid() {
        return acceleratorid;
    }

    public void setAcceleratorid(Integer acceleratorid) {
        this.acceleratorid = acceleratorid;
    }

    public String getAcceleratorName() {
        return acceleratorName;
    }

    public void setAcceleratorName(String acceleratorName) {
        this.acceleratorName = acceleratorName;
    }

    public String getAcceleratorDescription() {
        return acceleratorDescription;
    }

    public void setAcceleratorDescription(String acceleratorDescription) {
        this.acceleratorDescription = acceleratorDescription;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (acceleratorid != null ? acceleratorid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Accelerator)) {
            return false;
        }
        Accelerator other = (Accelerator) object;
        if ((this.acceleratorid == null && other.acceleratorid != null) || (this.acceleratorid != null && !this.acceleratorid.equals(other.acceleratorid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.openepics.model.entity.Accelerator[ acceleratorid=" + acceleratorid + " ]";
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    @XmlTransient
    public Collection<BeamlineSequence> getBeamlineSequenceCollection() {
        return beamlineSequenceCollection;
    }

    public void setBeamlineSequenceCollection(Collection<BeamlineSequence> beamlineSequenceCollection) {
        this.beamlineSequenceCollection = beamlineSequenceCollection;
    }
    
}
