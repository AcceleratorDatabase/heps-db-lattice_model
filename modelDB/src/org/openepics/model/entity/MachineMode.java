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
 * @author admin
 */
@Entity
@Table(name = "machine_mode")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MachineMode.findAll", query = "SELECT m FROM MachineMode m"),
    @NamedQuery(name = "MachineMode.findByMachineModeId", query = "SELECT m FROM MachineMode m WHERE m.machineModeId = :machineModeId"),
    @NamedQuery(name = "MachineMode.findByMachineModeDescription", query = "SELECT m FROM MachineMode m WHERE m.machineModeDescription = :machineModeDescription"),
    @NamedQuery(name = "MachineMode.findByMachineModeName", query = "SELECT m FROM MachineMode m WHERE m.machineModeName = :machineModeName")})
public class MachineMode implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "machine_mode_id")
    private Integer machineModeId;
    @Size(max = 255)
    @Column(name = "machine_mode_description")
    private String machineModeDescription;
    @Size(max = 255)
    @Column(name = "machine_mode_name")
    private String machineModeName;
    @OneToMany(mappedBy = "machineModeId")
    private Collection<Lattice> latticeCollection;

    public MachineMode() {
    }

    public MachineMode(Integer machineModeId) {
        this.machineModeId = machineModeId;
    }

    public Integer getMachineModeId() {
        return machineModeId;
    }

    public void setMachineModeId(Integer machineModeId) {
        this.machineModeId = machineModeId;
    }

    public String getMachineModeDescription() {
        return machineModeDescription;
    }

    public void setMachineModeDescription(String machineModeDescription) {
        this.machineModeDescription = machineModeDescription;
    }

    public String getMachineModeName() {
        return machineModeName;
    }

    public void setMachineModeName(String machineModeName) {
        this.machineModeName = machineModeName;
    }

    @XmlTransient
    public Collection<Lattice> getLatticeCollection() {
        return latticeCollection;
    }

    public void setLatticeCollection(Collection<Lattice> latticeCollection) {
        this.latticeCollection = latticeCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (machineModeId != null ? machineModeId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MachineMode)) {
            return false;
        }
        MachineMode other = (MachineMode) object;
        if ((this.machineModeId == null && other.machineModeId != null) || (this.machineModeId != null && !this.machineModeId.equals(other.machineModeId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.openepics.model.entity.MachineMode[ machineModeId=" + machineModeId + " ]";
    }
    
}
