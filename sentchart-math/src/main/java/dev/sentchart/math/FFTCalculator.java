package dev.sentchart.math;

import io.fair_acc.dataset.spi.DefaultDataSet;
import io.fair_acc.dataset.spi.DoubleDataSet;

import java.util.Collection;

public class FFTCalculator extends NativeMath {
    public static DoubleDataSet getFFTDataset(double frequencySample, double[] signal) {
        double[][] fftResult = calculateFFT(frequencySample, signal);
        return new DefaultDataSet("FFT forward", fftResult[0], fftResult[1], fftResult[0].length, false);
    }

    public static DoubleDataSet getFFTDataset(double frequencySample, Collection<Double> signal) {
        double[] signalArray = signal.stream().mapToDouble(Double::doubleValue).toArray();
        return getFFTDataset(frequencySample, signalArray);
    }
}
