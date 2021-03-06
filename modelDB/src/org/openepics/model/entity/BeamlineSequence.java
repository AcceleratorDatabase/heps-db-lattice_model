/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
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
 * @author chu
 */
@Entity
@Table(name = "beamline_sequence")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "BeamlineSequence.findAll", query = "SELECT b FROM BeamlineSequence b"),
    @NamedQuery(name = "BeamlineSequence.findByBeamlineSequenceId", query = "SELECT b FROM BeamlineSequence b WHERE b.beamlineSequenceId = :beamlineSequenceId"),
    @NamedQuery(name = "BeamlineSequence.findByFirstElementName", query = "SELECT b FROM BeamlineSequence b WHERE b.firstElementName = :firstElementName"),
    @NamedQuery(name = "BeamlineSequence.findByLastElementName", query = "SELECT b FROM BeamlineSequence b WHERE b.lastElementName = :lastElementName"),
    @NamedQuery(name = "BeamlineSequence.findByPredecessorSequence", query = "SELECT b FROM BeamlineSequence b WHERE b.predecessorSequence = :predecessorSequence"),
    @NamedQuery(name = "BeamlineSequence.findBySequenceDescription", query = "SELECT b FROM BeamlineSequence b WHERE b.sequenceDescription = :sequenceDescription"),
    @NamedQuery(name = "BeamlineSequence.findBySequenceLength", query = "SELECT b FROM BeamlineSequence b WHERE b.sequenceLength = :sequenceLength"),
    @NamedQuery(name = "BeamlineSequence.findBySequenceName", query = "SELECT b FROM BeamlineSequence b WHERE b.sequenceName = :sequenceName")})
public class BeamlineSequence implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "beamline_sequence_id")
    private Integer beamlineSequenceId;
    @Size(max = 255)
    @Column(name = "first_element_name")
    private String firstElementName;
    @Size(max = 255)
    @Column(name = "last_element_name")
    private String lastElementName;
    @Size(max = 255)
    @Column(name = "predecessor_sequence")
    private String predecessorSequence;
    @Size(max = 255)
    @Column(name = "sequence_description")
    private String sequenceDescription;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "sequence_length")
    private Double sequenceLength;
    @Size(max = 255)
    @Column(name = "sequence_name")
    private String sequenceName;
    @JoinColumn(name = "accelerator_id", referencedColumnName = "Accelerator_id")
    @ManyToOne
    private Accelerator acceleratorId;
    @OneToMany(mappedBy = "beamlineSequenceId")
    private Collection<Element> elementCollection;
    @OneToMany(mappedBy = "beamlineSequenceId")
    private Collection<BlsequenceLattice> blsequenceLatticeCollection;

    public BeamlineSequence() {
    }

    public BeamlineSequence(Integer beamlineSequenceId) {
        this.beamlineSequenceId = beamlineSequenceId;
    }

    public Integer getBeamlineSequenceId() {
        return beamlineSequenceId;
    }

    public void setBeamlineSequenceId(Integer beamlineSequenceId) {
        this.beamlineSequenceId = beamlineSequenceId;
    }

    public String getFirstElementName() {
        return firstElementName;
    }

    public void setFirstElementName(String firstElementName) {
        this.firstElementName = firstElementName;
    }

    public String getLastElementName() {
        return lastElementName;
    }

    public void setLastElementName(String lastElementName) {
        this.lastElementName = lastElementName;
    }

    public String getPredecessorSequence() {
        return predecessorSequence;
    }

    public void setPredecessorSequence(String predecessorSequence) {
        this.predecessorSequence = predecessorSequence;
    }

    public String getSequenceDescription() {
        return sequenceDescription;
    }

    public void setSequenceDescription(String sequenceDescription) {
        this.sequenceDescription = sequenceDescription;
    }

    public Double getSequenceLength() {
        return sequenceLength;
    }

    public void setSequenceLength(Double sequenceLength) {
        this.sequenceLength = sequenceLength;
    }

    public String getSequenceName() {
        return sequenceName;
    }

    public void setSequenceName(String sequenceName) {
        this.sequenceName = sequenceName;
    }

    public Accelerator getAcceleratorId() {
        return acceleratorId;
    }

    public void setAcceleratorId(Accelerator acceleratorId) {
        this.acceleratorId = acceleratorId;
    }

    @XmlTransient
    public Collection<Element> getElementCollection() {
        return elementCollection;
    }

    public void setElementCollection(Collection<Element> elementCollection) {
        this.elementCollection = elementCollection;
    }

    @XmlTransient
    public Collection<BlsequenceLattice> getBlsequenceLatticeCollection() {
        return blsequenceLatticeCollection;
    }

    public void setBlsequenceLatticeCollection(Collection<BlsequenceLattice> blsequenceLatticeCollection) {
        this.blsequenceLatticeCollection = blsequenceLatticeCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (beamlineSequenceId != null ? beamlineSequenceId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof BeamlineSequence)) {
            return false;
        }
        BeamlineSequence other = (BeamlineSequence) object;
        if ((this.beamlineSequenceId == null && other.beamlineSequenceId != null) || (this.beamlineSequenceId != null && !this.beamlineSequenceId.equals(other.beamlineSequenceId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.openepics.model.entity.BeamlineSequence[ beamlineSequenceId=" + beamlineSequenceId + " ]";
    }
    
}
