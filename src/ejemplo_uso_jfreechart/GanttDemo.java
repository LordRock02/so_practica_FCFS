package src.ejemplo_uso_jfreechart;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.ui.ApplicationFrame;
import org.jfree.data.category.IntervalCategoryDataset;
import org.jfree.data.gantt.Task;
import org.jfree.data.gantt.TaskSeries;
import org.jfree.data.gantt.TaskSeriesCollection;
import org.jfree.ui.RefineryUtilities;

import src.gantt_chart_mod.GanttChartFactory;
import src.gantt_chart_mod.TaskNumeric;

import javax.swing.*;
import java.awt.*;

public class GanttDemo extends ApplicationFrame {

    public GanttDemo(String s) {
        super(s);
        JPanel jpanel = createDemoPanel();
        jpanel.setPreferredSize(new Dimension(500, 270));
        setContentPane(jpanel);
    }

    private static JFreeChart createChart(IntervalCategoryDataset dataset) {
        final JFreeChart chart = GanttChartFactory.createGanttChart(
                "Gantt Chart Demo", "Task", "Value", dataset, true, true, false);
        return chart;
    }

    private static IntervalCategoryDataset createDataset() {
        TaskSeries taskseries = new TaskSeries("Scheduled");

        Task task = new TaskNumeric("task1", 0, 5);
        task.setPercentComplete(1.0D);
        taskseries.add(task);
        Task task1 = new TaskNumeric("task2", 2,9);
        taskseries.add(task1);
        Task task2 = TaskNumeric.duration("task3", 6, 5);

        task2.setPercentComplete(0.5);

        taskseries.add(task2);


        TaskSeriesCollection taskseriescollection = new TaskSeriesCollection();
        taskseriescollection.add(taskseries);
        return taskseriescollection;
    }

    public static JPanel createDemoPanel() {
        JFreeChart jfreechart = createChart(createDataset());
        ChartPanel chartpanel = new ChartPanel(jfreechart);
        chartpanel.setMouseWheelEnabled(true);
        return chartpanel;
    }

    public static void main(String args[]) {
        GanttDemo ganttdemo = new GanttDemo("gantt demo");
        ganttdemo.pack();
        RefineryUtilities.centerFrameOnScreen(ganttdemo);
        ganttdemo.setVisible(true);
    }

}