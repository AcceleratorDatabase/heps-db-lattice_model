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
@Table(name = "model_line")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ModelLine.findAll", query = "SELECT m FROM ModelLine m"),
    @NamedQuery(name = "ModelLine.findByModelLineId", query = "SELECT m FROM ModelLine m WHERE m.modelLineId = :modelLineId"),
    @NamedQuery(name = "ModelLine.findByModelLineName", query = "SELECT m FROM ModelLine m WHERE m.modelLineName = :modelLineName"),
    @NamedQuery(name = "ModelLine.findByModelLineDescription", query = "SELECT m FROM ModelLine m WHERE m.modelLineDescription = :modelLineDescription"),
    @NamedQuery(name = "ModelLine.findByStartPosition", query = "SELECT m FROM ModelLine m WHERE m.startPosition = :startPosition"),
    @NamedQuery(name = "ModelLine.findByEndPosition", query = "SELECT m FROM ModelLine m WHERE m.endPosition = :endPosition"),
    @NamedQuery(name = "ModelLine.findByStartMarker", query = "SELECT m FROM ModelLine m WHERE m.startMarker = :startMarker"),
    @NamedQuery(name = "ModelLine.findByEndMarker", query = "SELECT m FROM ModelLine m WHERE m.endMarker = :endMarker")})
public class ModelLine implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "model_line_id")
    private Integer modelLineId;
    @Size(max = 45)
    @Column(name = "model_line_name")
    private String modelLineName;
    @Size(max = 255)
    @Column(name = "model_line_description")
    private String modelLineDescription;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "start_position")
    private Double startPosition;
    @Column(name = "end_position")
    private Double endPosition;
    @Size(max = 45)
    @Column(name = "start_marker")
    private String startMarker;
    @Size(max = 45)
    @Column(name = "end_marker")
    private String endMarker;
    @OneToMany(mappedBy = "modelLineId")
    private Collection<Model> modelCollection;

    public ModelLine() {
    }

    public ModelLine(Integer modelLineId) {
        this.modelLineId = modelLineId;
    }

    public Integer getModelLineId() {
        return modelLineId;
    }

    public void setModelLineId(Integer modelLineId) {
        this.modelLineId = modelLineId;
    }

    public String getModelLineName() {
        return modelLineName;
    }

    public void setModelLineName(String modelLineName) {
        this.modelLineName = modelLineName;
    }

    public String getModelLineDescription() {
        return modelLineDescription;
    }

    public void setModelLineDescription(String modelLineDescription) {
        this.modelLineDescription = modelLineDescription;
    }

    public Double getStartPosition() {
        return startPosition;
    }

    public void setStartPosition(Double startPosition) {
        this.startPosition = startPosition;
    }

    public Double getEndPosition() {
        return endPosition;
    }

    public void setEndPosition(Double endPosition) {
        this.endPosition = endPosition;
    }

    public String getStartMarker() {
        return startMarker;
    }

    public void setStartMarker(String startMarker) {
        this.startMarker = startMarker;
    }

    public String getEndMarker() {
        return endMarker;
    }

    public void setEndMarker(String endMarker) {
        this.endMarker = endMarker;
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
        hash += (modelLineId != null ? modelLineId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ModelLine)) {
            return false;
        }
        ModelLine other = (ModelLine) object;
        if ((this.modelLineId == null && other.modelLineId != null) || (this.modelLineId != null && !this.modelLineId.equals(other.modelLineId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.openepics.model.entity.ModelLine[ modelLineId=" + modelLineId + " ]";
    }
    
}
