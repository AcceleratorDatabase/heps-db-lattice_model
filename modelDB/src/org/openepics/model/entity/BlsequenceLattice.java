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
 * @author chu
 */
@Entity
@Table(name = "blsequence_lattice")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "BlsequenceLattice.findAll", query = "SELECT b FROM BlsequenceLattice b"),
    @NamedQuery(name = "BlsequenceLattice.findByBlsequenceLatticeId", query = "SELECT b FROM BlsequenceLattice b WHERE b.blsequenceLatticeId = :blsequenceLatticeId"),
    @NamedQuery(name = "BlsequenceLattice.findByBeamlineOrder", query = "SELECT b FROM BlsequenceLattice b WHERE b.beamlineOrder = :beamlineOrder")})
public class BlsequenceLattice implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "blsequence_lattice_id")
    private Integer blsequenceLatticeId;
    @Column(name = "beamline_order")
    private Integer beamlineOrder;
    @JoinColumn(name = "beamline_sequence_id", referencedColumnName = "beamline_sequence_id")
    @ManyToOne
    private BeamlineSequence beamlineSequenceId;
    @JoinColumn(name = "lattice_id", referencedColumnName = "lattice_id")
    @ManyToOne
    private Lattice latticeId;

    public BlsequenceLattice() {
    }

    public BlsequenceLattice(Integer blsequenceLatticeId) {
        this.blsequenceLatticeId = blsequenceLatticeId;
    }

    public Integer getBlsequenceLatticeId() {
        return blsequenceLatticeId;
    }

    public void setBlsequenceLatticeId(Integer blsequenceLatticeId) {
        this.blsequenceLatticeId = blsequenceLatticeId;
    }

    public Integer getBeamlineOrder() {
        return beamlineOrder;
    }

    public void setBeamlineOrder(Integer beamlineOrder) {
        this.beamlineOrder = beamlineOrder;
    }

    public BeamlineSequence getBeamlineSequenceId() {
        return beamlineSequenceId;
    }

    public void setBeamlineSequenceId(BeamlineSequence beamlineSequenceId) {
        this.beamlineSequenceId = beamlineSequenceId;
    }

    public Lattice getLatticeId() {
        return latticeId;
    }

    public void setLatticeId(Lattice latticeId) {
        this.latticeId = latticeId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (blsequenceLatticeId != null ? blsequenceLatticeId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof BlsequenceLattice)) {
            return false;
        }
        BlsequenceLattice other = (BlsequenceLattice) object;
        if ((this.blsequenceLatticeId == null && other.blsequenceLatticeId != null) || (this.blsequenceLatticeId != null && !this.blsequenceLatticeId.equals(other.blsequenceLatticeId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.openepics.model.entity.BlsequenceLattice[ blsequenceLatticeId=" + blsequenceLatticeId + " ]";
    }
    
}
