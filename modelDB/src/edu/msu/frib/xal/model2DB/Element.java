/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.msu.frib.xal.model2DB;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author chu
 */

public class Element {
    private final SimpleStringProperty elementName;
    private final SimpleDoubleProperty pos;
    private final SimpleDoubleProperty energy;
    private final SimpleDoubleProperty phX;
    private final SimpleDoubleProperty phXP;
    private final SimpleDoubleProperty phY;
    private final SimpleDoubleProperty phYP;
    private final SimpleDoubleProperty phZ;
    private final SimpleDoubleProperty phZP;
    private final SimpleDoubleProperty betaX;
    private final SimpleDoubleProperty alphaX;
    private final SimpleDoubleProperty emitX;
    private final SimpleDoubleProperty betaY;
    private final SimpleDoubleProperty alphaY;
    private final SimpleDoubleProperty emitY;
    private final SimpleDoubleProperty betaZ;
    private final SimpleDoubleProperty alphaZ;
    private final SimpleDoubleProperty emitZ;

    public Element(String elementName, double pos, double energy, 
    		double phX, double phXP, double phY, double phYP, double phZ, double phZP,
    		double betaX, double alphaX, double emitX,
    		double betaY, double alphaY, double emitY,
    		double betaZ, double alphaZ, double emitZ) {
        this.elementName = new SimpleStringProperty(elementName);
        this.pos = new SimpleDoubleProperty(pos);
        this.energy = new SimpleDoubleProperty(energy);
        this.phX = new SimpleDoubleProperty(phX);
        this.phXP = new SimpleDoubleProperty(phXP);
        this.phY = new SimpleDoubleProperty(phY);
        this.phYP = new SimpleDoubleProperty(phYP);
        this.phZ = new SimpleDoubleProperty(phZ);
        this.phZP = new SimpleDoubleProperty(phZP);
        this.betaX = new SimpleDoubleProperty(betaX);
        this.alphaX = new SimpleDoubleProperty(alphaX);
        this.emitX = new SimpleDoubleProperty(emitX);
        this.betaY = new SimpleDoubleProperty(betaY);
        this.alphaY = new SimpleDoubleProperty(alphaY);
        this.emitY = new SimpleDoubleProperty(emitY);
        this.betaZ = new SimpleDoubleProperty(betaZ);
        this.alphaZ = new SimpleDoubleProperty(alphaZ);
        this.emitZ = new SimpleDoubleProperty(emitZ);
    }

    public String getElementName() {
        return elementName.get();
    }

    public void setElementName(String eName) {
        elementName.set(eName);
    }

    public double getPos() {
        return pos.get();
    }

    public void setPos(double s) {
        pos.set(s);
    }

    public double getEnergy() {
        return energy.get();
    }

    public void setEnergy(double e) {
        energy.set(e);
    }

    public double getPhX() {
        return phX.get();
    }

    public double getPhXP() {
        return phXP.get();
    }

    public double getPhY() {
        return phY.get();
    }

    public double getPhYP() {
        return phYP.get();
    }

    public double getPhZ() {
        return phZ.get();
    }

    public double getPhZP() {
        return phZP.get();
    }

    public double getBetaX() {
        return betaX.get();
    }

    public void setPhX(double x) {
        phX.set(x);
    }

    public void setPhXP(double xp) {
        phXP.set(xp);
    }

    public void setPhY(double y) {
        phY.set(y);
    }

    public void setPhYP(double yp) {
        phYP.set(yp);
    }

    public void setPhZ(double z) {
        phZ.set(z);
    }

    public void setPhZP(double zp) {
        phZP.set(zp);
    }

    public void setBetaX(double bX) {
        betaX.set(bX);
    }

    public double getAlphaX() {
        return alphaX.get();
    }

    public void setAlphaX(double aX) {
        alphaX.set(aX);
    }

    public double getEmitX() {
        return emitX.get();
    }

    public void setEmitX(double eX) {
        emitX.set(eX);
    }

    public double getBetaY() {
        return betaY.get();
    }

    public void setBetaY(double bY) {
        betaY.set(bY);
    }

    public double getAlphaY() {
        return alphaY.get();
    }

    public void setAlphaY(double aY) {
        alphaY.set(aY);
    }

    public double getEmitY() {
        return emitY.get();
    }

    public void setEmitY(double eY) {
        emitY.set(eY);
    }

    public double getBetaZ() {
        return betaZ.get();
    }

    public void setBetaZ(double bZ) {
        betaZ.set(bZ);
    }

    public double getAlphaZ() {
        return alphaZ.get();
    }

    public void setAlphaZ(double aZ) {
        alphaZ.set(aZ);
    }

    public double getEmitZ() {
        return emitZ.get();
    }

    public void setEmitZ(double eZ) {
        emitZ.set(eZ);
    }

    
}
