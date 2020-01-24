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
        return make + " " + maxAperture + "mm F" + focalLength;
    }
}