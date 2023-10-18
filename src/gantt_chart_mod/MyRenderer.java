package src.gantt_chart_mod;

import org.jfree.chart.renderer.category.GanttRenderer;
import org.jfree.data.gantt.Task;
import org.jfree.data.gantt.TaskSeries;
import org.jfree.data.gantt.TaskSeriesCollection;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class MyRenderer extends GanttRenderer {

    private static final int PASS = 2; // assumes two passes
    private final List<Color> clut = new ArrayList<Color>();
    private final TaskSeriesCollection model;
    private int row;
    private int col;
    private int index;

    public MyRenderer(TaskSeriesCollection model) {
        this.model = model;
    }

    @Override
    public Paint getItemPaint(int row, int col) {
        if (clut.isEmpty() || this.row != row || this.col != col) {
            initClut(row, col);
            this.row = row;
            this.col = col;
            index = 0;
        }
        int clutIndex = index++ / PASS;
        return clut.get(clutIndex);
    }

    private void initClut(int row, int col) {
        clut.clear();
        Color c = (Color) super.getItemPaint(row, col);
        float[] a = new float[3];
        Color.RGBtoHSB(c.getRed(), c.getGreen(), c.getBlue(), a);
        TaskSeries series = (TaskSeries) model.getRowKeys().get(row);
        List<Task> tasks = series.getTasks(); // unchecked
        int taskCount = tasks.get(col).getSubtaskCount();
        taskCount = Math.max(1, taskCount);
        String description;
        for (int i = 0; i < taskCount; i++) {
            description = tasks.get(col).getSubtask(i).getDescription();

            System.out.println(description + ": " + i);
            if (description.equals("Ejecutando")) {
                clut.add(Color.green);
            }
            if (description.equals("Esperando")) {
                clut.add(Color.red);
            }
        }
    }
}