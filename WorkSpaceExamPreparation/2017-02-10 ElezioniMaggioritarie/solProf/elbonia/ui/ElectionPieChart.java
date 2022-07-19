package elbonia.ui;

import java.text.DecimalFormat;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.PieSectionLabelGenerator;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.DefaultPieDataset;

public class ElectionPieChart {
	
	private ChartPanel chartPanel;
	private DefaultPieDataset dataset;
	
	public ElectionPieChart(){
		this(new DefaultPieDataset());
	}

	public ElectionPieChart(DefaultPieDataset dataset){
		this.dataset=dataset;
		JFreeChart chart = ChartFactory.createPieChart(
			"Attribuzione seggi",  // chart title
			dataset,            // data
			true,              // legend
			false,               // tooltips
			false               // no URL generation
			);
		PiePlot plot = (PiePlot) chart.getPlot();
		plot.setSimpleLabels(true);
		PieSectionLabelGenerator gen = new StandardPieSectionLabelGenerator(
            "{0}: {1} ({2})", new DecimalFormat("0"), new DecimalFormat("0%"));
        plot.setLabelGenerator(gen);
        chartPanel = new ChartPanel(chart);
	}
	
	public ChartPanel getChartPanel(){
		return chartPanel;
	}
	
	public void setValue(String s, Integer n){
		this.dataset.setValue(s,n);
	}
}
