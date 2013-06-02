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
 * @author lv
 */
@Entity
@Table(name = "element_install_device")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ElementInstallDevice.findAll", query = "SELECT e FROM ElementInstallDevice e"),
    @NamedQuery(name = "ElementInstallDevice.findByElementInstallId", query = "SELECT e FROM ElementInstallDevice e WHERE e.elementInstallId = :elementInstallId"),
    @NamedQuery(name = "ElementInstallDevice.findByInstallId", query = "SELECT e FROM ElementInstallDevice e WHERE e.installId = :installId"),
    @NamedQuery(name = "ElementInstallDevice.findBySlice", query = "SELECT e FROM ElementInstallDevice e WHERE e.slice = :slice"),
    @NamedQuery(name = "ElementInstallDevice.findByIndex", query = "SELECT e FROM ElementInstallDevice e WHERE e.index = :index")})
public class ElementInstallDevice implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "element__install_id")
    private Integer elementInstallId;
    @Column(name = "install_id")
    private Integer installId;
    @Column(name = "slice")
    private Integer slice;
    @Column(name = "index")
    private Integer index;
    @JoinColumn(name = "element_id", referencedColumnName = "element_id")
    @ManyToOne
    private Element elementId;

    public ElementInstallDevice() {
    }

    public ElementInstallDevice(Integer elementInstallId) {
        this.elementInstallId = elementInstallId;
    }

    public Integer getElementInstallId() {
        return elementInstallId;
    }

    public void setElementInstallId(Integer elementInstallId) {
        this.elementInstallId = elementInstallId;
    }

    public Integer getInstallId() {
        return installId;
    }

    public void setInstallId(Integer installId) {
        this.installId = installId;
    }

    public Integer getSlice() {
        return slice;
    }

    public void setSlice(Integer slice) {
        this.slice = slice;
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
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
        hash += (elementInstallId != null ? elementInstallId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ElementInstallDevice)) {
            return false;
        }
        ElementInstallDevice other = (ElementInstallDevice) object;
        if ((this.elementInstallId == null && other.elementInstallId != null) || (this.elementInstallId != null && !this.elementInstallId.equals(other.elementInstallId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.openepics.model.entity.ElementInstallDevice[ elementInstallId=" + elementInstallId + " ]";
    }
    
}
