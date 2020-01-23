package com.example.cmpt276a2.model;

/**
 * A class to calculate depth of field.
 */
public class DepthCalculator {

    private static final double COC = 0.029;      // COC = "circle of confusion".
    static double inf = Double.POSITIVE_INFINITY; // value produced when distance is bigger than the hyper focal point.

    /**
     * Calculates hyper focal distance.
     * @param focalLength of lens.
     * @param aperture of lens.
     */
    public double hyperFocalDistance(int focalLength, double aperture) {
        double answer = (double)focalLength * (double)focalLength / (aperture * COC);
        return answer / 1000;
    }

    /**
     * Calculates near focal point.
     * @param focalPoint calculated from hyper focal distance function.
     * @param distance of subject.
     * @param focalLength of lens.
     */
    public double nearFocalPoint(double focalPoint, double distance, int focalLength) {
        double distanceInMM = distance * 1000;
        focalPoint = focalPoint * 1000;
        double answer = (focalPoint * distanceInMM) / (focalPoint + (distanceInMM - (double)focalLength));
        return answer / 1000;
    }

    /**
     * Calculates far focal point.
     * @param focalPoint calculated from hyper focal distance function.
     * @param distance of subject.
     * @param focalLength of lens.
     */
    public double farFocalPoint(double focalPoint, double distance, int focalLength) {
        double distanceInMM = distance * 1000;
        focalPoint = focalPoint * 1000;

        if(distanceInMM > focalPoint) {
            return inf;
        } else {
            double result = (focalPoint * distanceInMM) / (focalPoint - (distanceInMM - (double)focalLength));
            return result / 1000;
        }
    }

    /**
     * Calculates depth of field.
     * @param farFocalPoint calculated from far focal point function.
     * @param nearFocalPoint calculated from near focal point function.
     */
    public double depthOfField(double farFocalPoint, double nearFocalPoint) {
        return ((farFocalPoint * 1000) - (nearFocalPoint * 1000)) / 1000;
    }

}