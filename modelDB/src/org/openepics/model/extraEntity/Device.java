/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.openepics.model.extraEntity;

import java.util.Collection;
import java.util.Date;
import org.openepics.model.entity.ElementProp;

/**
 *
 * @author lv
 */
public class Device {

    private Double dx;
    private Double dy;
    private Double dz;
    private String elementName;
    private Double len;
    private Double pitch;
    private Double pos;
    private Double roll;
    private Double s;
    private Double yaw;
    private String elementType;
    private String beamlineSequenceName;
    private Collection<ElementProp> elementPropCollection;
    private Collection<BeamParams> beamParamsCollection;

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

    public String getElementType() {
        return elementType;
    }

    public void setElementType(String elementType) {
        this.elementType = elementType;
    }

    public String getBeamlineSequenceName() {
        return beamlineSequenceName;
    }

    public void setBeamlineSequenceName(String beamlineSequenceName) {
        this.beamlineSequenceName = beamlineSequenceName;
    }

    public Collection<ElementProp> getElementPropCollection() {
        return elementPropCollection;
    }

    public void setElementPropCollection(Collection<ElementProp> elementPropCollection) {
        this.elementPropCollection = elementPropCollection;
    }

    public Collection<BeamParams> getBeamParamsCollection() {
        return beamParamsCollection;
    }

    public void setBeamParamsCollection(Collection<BeamParams> beamParamsCollection) {
        this.beamParamsCollection = beamParamsCollection;
    }
}
