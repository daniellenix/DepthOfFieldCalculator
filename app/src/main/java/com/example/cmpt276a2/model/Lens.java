package com.example.cmpt276a2.model;

/**
 * A class to hold lens information.
 */
public class Lens {

    private String make;
    private double maxAperture;
    private int focalLength;

    public Lens(String make, double maxAperture, int focalLength) {
        this.make = make;
        this.maxAperture = maxAperture;
        this.focalLength = focalLength;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public void setMaxAperture(double maxAperture) {
        this.maxAperture = maxAperture;
    }

    public void setFocalLength(int focalLength) {
        this.focalLength = focalLength;
    }

    public String getMake() {
        return make;
    }

    public double getMaxAperture() {
        return maxAperture;
    }

    public int getFocalLength() {
        return focalLength;
    }

    @Override
    public String toString() {
        return make + " " + focalLength + "mm F" + maxAperture;
    }
}