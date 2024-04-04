package dev.sentchart.math;

import dev.sentchart.jni.LibraryUtils;

public class NativeMath {
    protected native static double[][] calculateFFT(double frequencySample, double[] signal);

    public static void main(String[] args) {

    }
}
