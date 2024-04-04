package dev.sentchart.application.chart;

import dev.sentchart.io.SignalPoint;
import dev.sentchart.math.FFTCalculator;
import io.fair_acc.dataset.DataSet;
import dev.sentchart.io.SignalPointsDataBufferReader;
import javafx.scene.Node;
import javafx.scene.layout.VBox;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import io.fair_acc.dataset.spi.DefaultDataSet;


/**
 * example illustrating the discrete time fourier transform and Fast-Fourier transform and spectral interpolation
 * methods. Zoom into the peaks to see the details
 *
 * @author rstein
 */
public class FourierSample {
    private DataSet fraw;
    private DataSet fspectra1;

    public FourierSample(File dataFile) {
        initData(dataFile);
    }

    public Node getChartPanel() {
        final FourierChart chart1 = new FourierChart();
        chart1.getXAxis().setName("time");
        chart1.getXAxis().setUnit("s");
        chart1.getYAxis().setName("magnitude");
        chart1.getYAxis().setUnit("a.u.");
        chart1.getDatasets().add(fraw);

        final FourierChart chart2 = new FourierChart();
        chart2.getXAxis().setName("frequency");
        chart2.getXAxis().setUnit("fs");
        chart2.getYAxis().setName("magnitude");
        chart2.getYAxis().setUnit("a.u.");
        chart2.getDatasets().add(fspectra1);

        return new VBox(chart1, chart2);
    }

    private void initData(File file) {
        SignalPointsDataBufferReader reader = null;
        try {
            reader = new SignalPointsDataBufferReader(file);
            long times = System.currentTimeMillis();
            List<SignalPoint> signalPoints = reader.readAll();
            System.out.println(System.currentTimeMillis() - times);
            double[] time = new double[signalPoints.size()];
            double[] signal = new double[signalPoints.size()];
            for (int i = 0; i < signalPoints.size(); i++) {
                SignalPoint point = signalPoints.get(i);
                time[i] = point.time();
                signal[i] = point.signal();
            }
            System.out.println(System.currentTimeMillis() - times);
            fraw = new DefaultDataSet("raw data", time, signal, time.length, false);
            System.out.println("vekosvkp");
            System.out.println(System.currentTimeMillis() - times);
            double frequencySample = 1 / (time[1] - time[0]);
            fspectra1 = FFTCalculator.getFFTDataset(frequencySample, signal);
            System.out.println("vwsdk");
            System.out.println(System.currentTimeMillis() - times);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}