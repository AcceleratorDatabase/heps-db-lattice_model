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
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author chu
 */
@Entity
@Table(name = "machine_mode")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MachineMode.findAll", query = "SELECT m FROM MachineMode m"),
    @NamedQuery(name = "MachineMode.findByMachineModeId", query = "SELECT m FROM MachineMode m WHERE m.machineModeId = :machineModeId"),
    @NamedQuery(name = "MachineMode.findByMachineModeName", query = "SELECT m FROM MachineMode m WHERE m.machineModeName = :machineModeName"),
    @NamedQuery(name = "MachineMode.findByMachineModeDescription", query = "SELECT m FROM MachineMode m WHERE m.machineModeDescription = :machineModeDescription")})
public class MachineMode implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "machine_mode_id")
    private Integer machineModeId;
    @Column(name = "machine_mode_name")
    private String machineModeName;
    @Column(name = "machine_mode_description")
    private String machineModeDescription;
    @OneToMany(mappedBy = "machineModeId")
    private Collection<Model> modelCollection;

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

    public String getMachineModeName() {
        return machineModeName;
    }

    public void setMachineModeName(String machineModeName) {
        this.machineModeName = machineModeName;
    }

    public String getMachineModeDescription() {
        return machineModeDescription;
    }

    public void setMachineModeDescription(String machineModeDescription) {
        this.machineModeDescription = machineModeDescription;
    }

    @XmlTransient
    public Collection<Model> getModelCollection() {
        return modelCollection;
    }

    public void setModelCollection(Collection<Model> modelCollection) {
        this.modelCollection = modelCollection;
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
