 import org.jfree.chart.ChartFactory;
 import org.jfree.chart.ChartPanel;
 import org.jfree.chart.JFreeChart;
 import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.ui.ApplicationFrame;
import org.jfree.chart.ui.UIUtils;
import org.jfree.data.xy.XYSeries;
 import org.jfree.data.xy.XYSeriesCollection;

public class XYSeriesDemo extends ApplicationFrame {

/**
 * A demonstration application showing an XY series containing a null value.
 *
 * @param title  the frame title.
 */
public XYSeriesDemo(final String title, XYSeries ser1, XYSeries ser2) {

    super(title);
    /*
    final XYSeries series = new XYSeries("Παρατήρηση");
    series.add(1.0, 500.2);
    series.add(2.0, 694.1);
    series.add(3.0, 400.0);
    series.add(4, 664.4);
    series.add(5, 453.2);
    series.add(6, 500.2);
    series.add(7, 230.3);
    series.add(8, 634.4);
    series.add(9, 453.2);
    final XYSeries series2 = new XYSeries("Πρόβλεψη");
    series2.add(1, 510.2);
    series2.add(2, 654.1);
    series2.add(3, 450.0);
    series2.add(4, 654.4);
    series2.add(5, 413.2);
    series2.add(6, 520.2);
    series2.add(7, 300.2);
    series2.add(8, 630.4);
    series2.add(9, 423.2);
    */
    final XYSeriesCollection data = new XYSeriesCollection();
    data.addSeries(ser1);
    data.addSeries(ser2);
    final JFreeChart chart = ChartFactory.createScatterPlot(
        "XY Series Demo",
        "Μαθητές", 
        "Επιδόσεις", 
        data,
        PlotOrientation.VERTICAL,
        true,
        true,
        false
    );

    final ChartPanel chartPanel = new ChartPanel(chart);
    chartPanel.setPreferredSize(new java.awt.Dimension(1000, 270));
    //setContentPane(chartPanel);

}

// ****************************************************************************
// * JFREECHART DEVELOPER GUIDE                                               *
// * The JFreeChart Developer Guide, written by David Gilbert, is available   *
// * to purchase from Object Refinery Limited:                                *
// *                                                                          *
// * http://www.object-refinery.com/jfreechart/guide.html                     *
// *                                                                          *
// * Sales are used to provide funding for the JFreeChart project - please    * 
// * support us so that we can continue developing free software.             *
// ****************************************************************************

/**
 * Starting point for the demonstration application.
 *
 * @param args  ignored.
 */
public static void main(final String[] args) {

    //final XYSeriesDemo demo = new XYSeriesDemo("XY Series Demo");
    //demo.pack();
    //UIUtils.centerFrameOnScreen(demo);
    //demo.setVisible(true);

}

}

