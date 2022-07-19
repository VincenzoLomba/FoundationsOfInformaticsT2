package elbonia.ui;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.jfree.chart.ChartPanel;

import elbonia.model.Partito;
import elbonia.ui.controller.Controller;

public class ElboniaPanel extends JPanel implements ActionListener {
	private static final long serialVersionUID = 1L;
	private JTextField numeroCollegiField, dimensioneCollegioField;
	private ElectionSlider slider;
	private ElectionTablePanel electionTable;
	private ElectionPieChart myChart;
	private Controller controller;

	public ElboniaPanel(Controller controller) {
		this.controller = controller;

		this.setBackground(Color.yellow);

		JLabel label1 = new JLabel("Numero collegi:"); // slider
		label1.setForeground(Color.black);
		add(label1);

		slider = new ElectionSlider(this.controller);
		//slider.addActionListener(e -> actionPerformed(e));
		slider.addActionListener(this);
		add(slider);
		
		JLabel label2 = new JLabel(" Numero collegi:");
		label2.setForeground(Color.black);
		add(label2);
		
		numeroCollegiField = new JTextField(3); numeroCollegiField.setEditable(false);
		add(numeroCollegiField);

		JLabel label3 = new JLabel(" Dimensione collegio:");
		label3.setForeground(Color.black);
		add(label3);
		
		dimensioneCollegioField = new JTextField(3); dimensioneCollegioField.setEditable(false);
		add(dimensioneCollegioField);

		int dimensioneCollegio;
		int numeroCollegi;
		numeroCollegi = slider.getValue();
		numeroCollegiField.setText("" + numeroCollegi);
		dimensioneCollegio = this.controller.getSeggiMassimi()/numeroCollegi;
		dimensioneCollegioField.setText("" + dimensioneCollegio);
		
		List<Partito> listaPartiti = controller.getListaPartiti();
		Map<String, Integer> mappaSeggi = controller.getMappaSeggiVuota();		
		electionTable = new ElectionTablePanel(listaPartiti, mappaSeggi);
		add(electionTable);
		
		myChart = new ElectionPieChart();
		ChartPanel chartPanel = myChart.getChartPanel();
		add(chartPanel);
	}


	public void actionPerformed(ActionEvent e) {

		int dimensioneCollegio = slider.getDimensioneCollegio();
		int numeroCollegi = slider.getNumeroCollegi();
		
		numeroCollegiField.setText("" + numeroCollegi);
		dimensioneCollegioField.setText("" + dimensioneCollegio);
	
		Map<String, Integer> mappaSeggi = controller.calcola(dimensioneCollegio);
		if (mappaSeggi != null) {
			electionTable.update(mappaSeggi);
			for (Entry<String, Integer> entry : mappaSeggi.entrySet()) {
				myChart.setValue(entry.getKey(), entry.getValue());
			}
		}	
		this.repaint();
	}


}
