
package dev.sentchart.application.chart;

import java.util.ArrayList;
import java.util.List;

import io.fair_acc.chartfx.plugins.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import io.fair_acc.chartfx.XYChart;
import io.fair_acc.chartfx.axes.spi.DefaultNumericAxis;
import io.fair_acc.chartfx.renderer.ErrorStyle;
import io.fair_acc.chartfx.renderer.LineStyle;
import io.fair_acc.chartfx.renderer.spi.ErrorDataSetRenderer;

/**
 * Shorthand extension/configuration of the standard XYChart functionalities to make the samples more readable
 *
 * @author rstein
 */
public class FourierChart extends XYChart {
    private final List<DefaultNumericAxis> yAxes = new ArrayList<>();
    private final List<ErrorDataSetRenderer> renderer = new ArrayList<>();

    public FourierChart() {
        this(1);
    }

    public FourierChart(final int nAxes) {
        super(new DefaultNumericAxis("x-axis"), new DefaultNumericAxis("y-axis"));

        if (nAxes <= 0) {
            throw new IllegalArgumentException("nAxes= " + nAxes + " must be >=1");
        }

        ErrorDataSetRenderer defaultRenderer = (ErrorDataSetRenderer) getRenderers().get(0);
        defaultRenderer.setPolyLineStyle(LineStyle.NORMAL);
        defaultRenderer.setErrorStyle(ErrorStyle.ERRORCOMBO);
        renderer.add(defaultRenderer);

        yAxes.add(getYAxis());

        for (int i = 1; i < nAxes; i++) {
            DefaultNumericAxis yAxis = new DefaultNumericAxis("y-axis" + i);
            yAxes.add(yAxis);

            ErrorDataSetRenderer newRenderer = new ErrorDataSetRenderer();
            newRenderer.getAxes().addAll(getXAxis(), yAxis);
            getRenderers().add(newRenderer);
            renderer.add(newRenderer);
        }

        getPlugins().add(new ParameterMeasurements());
        getPlugins().add(new Zoomer());
        getPlugins().add(new DataPointTooltip());
        getPlugins().add(new TableViewer());
        getPlugins().add(new EditAxis());
        getPlugins().add(new DataPointTooltip());

        VBox.setVgrow(this, Priority.ALWAYS);
        HBox.setHgrow(this, Priority.ALWAYS);
    }

    public ErrorDataSetRenderer getRenderer() {
        return renderer.get(0);
    }

    public ErrorDataSetRenderer getRenderer(final int index) {
        return renderer.get(index);
    }

    @Override
    public DefaultNumericAxis getXAxis() {
        return (DefaultNumericAxis) super.getXAxis();
    }

    @Override
    public DefaultNumericAxis getYAxis() {
        return (DefaultNumericAxis) super.getYAxis();
    }

    public DefaultNumericAxis getYAxis(final int index) {
        return yAxes.get(index);
    }
}