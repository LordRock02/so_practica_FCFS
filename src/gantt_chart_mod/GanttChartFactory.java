package src.gantt_chart_mod;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartTheme;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.StandardChartTheme;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.labels.IntervalCategoryToolTipGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.CategoryItemRenderer;
import org.jfree.chart.renderer.category.GanttRenderer;
import org.jfree.chart.urls.StandardCategoryURLGenerator;
import org.jfree.data.category.IntervalCategoryDataset;
import org.jfree.data.gantt.TaskSeriesCollection;

import java.text.NumberFormat;

public class GanttChartFactory extends ChartFactory {

    protected static ChartTheme currentTheme = new StandardChartTheme("JFree");

    public static JFreeChart createGanttChart(String title,
                                              String categoryAxisLabel, String valueAxisLabel,
                                              TaskSeriesCollection dataset, boolean legend, boolean tooltips,
                                              boolean urls) {

        CategoryAxis categoryAxis = new CategoryAxis(categoryAxisLabel);
        ValueAxis valueAxis = new NumberAxis(valueAxisLabel);

        CategoryItemRenderer renderer = new MyRenderer(dataset);
        if (tooltips) {
            renderer.setDefaultToolTipGenerator(
                    new IntervalCategoryToolTipGenerator(
                            "{1}: {3} - {4}", NumberFormat.getNumberInstance()));
        }
        if (urls) {
            renderer.setDefaultItemURLGenerator(
                    new StandardCategoryURLGenerator());
        }

        CategoryPlot plot = new CategoryPlot(dataset, categoryAxis, valueAxis,
                renderer);
        plot.setOrientation(PlotOrientation.HORIZONTAL);
        JFreeChart chart = new JFreeChart(title, JFreeChart.DEFAULT_TITLE_FONT,
                plot, legend);
        currentTheme.apply(chart);
        return chart;
    }
}
