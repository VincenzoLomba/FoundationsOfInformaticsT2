package zannopoll.ui.swing;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Optional;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.WindowConstants;

import zannopoll.model.Sesso;
import zannopoll.model.SondaggioPercentuale;
import zannopoll.ui.controller.Controller;

public class JMainFrame extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;

	private Controller controller;
	private JPanel mainPanel;
	private JRadioButton entrambi, m, f;
	private ButtonGroup grp;
	private JComboBox<Integer> ageMin, ageMax;
	private JTextArea output;
	
	public JMainFrame(Controller controller) {
		this.controller = controller;
		initGUI();
		setSize(400, 280);
	}

	private void initGUI() {
		mainPanel = new JPanel();
		getContentPane().add(mainPanel);
		setTitle("ZannoPoll - Sondaggi per tutti i gusti");
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		//
		JLabel label = new JLabel("ZannoPoll - Risultati sondaggio");
		mainPanel.add(label, BorderLayout.NORTH);
		
		JPanel ageControls = new JPanel();
		ageControls.setLayout(new GridLayout(2,2));		
		ageControls.add(new JLabel("Età minima intervistati"));
		ageMin = new JComboBox<>(); 
		ageControls.add(ageMin);
		ageControls.add(new JLabel("Età massima intervistati"));
		ageMax = new JComboBox<>(); 
		ageControls.add(ageMax);
		for (int i=controller.getSondaggioPercentuale().getEtaMinIntervistati(); i<=controller.getSondaggioPercentuale().getEtaMaxIntervistati(); i++) {
			ageMin.addItem(i); ageMax.addItem(i);
		}
		ageMin.setSelectedIndex(0);
		ageMax.setSelectedIndex(controller.getSondaggioPercentuale().getEtaMaxIntervistati()-controller.getSondaggioPercentuale().getEtaMinIntervistati());
		
		Box buttonsBox = new Box(BoxLayout.LINE_AXIS);
		grp = new ButtonGroup();
		entrambi = new JRadioButton("entrambi"); grp.add(entrambi);
		m = new JRadioButton(Sesso.MASCHIO.toString());  grp.add(m);
		f = new JRadioButton(Sesso.FEMMINA.toString());	 grp.add(f);
		entrambi.setSelected(true);
		buttonsBox.add(new JLabel("Sesso intervistati"));
		buttonsBox.add(entrambi);
		buttonsBox.add(m);
		buttonsBox.add(f);
		
		JPanel filterControlPane = new JPanel();
		filterControlPane.setLayout(new GridLayout(2,1));
		filterControlPane.add(ageControls);
		filterControlPane.add(buttonsBox);
		mainPanel.add(filterControlPane, BorderLayout.CENTER);
				
		entrambi.addActionListener(this);
		m.addActionListener(this);
		f.addActionListener(this);
		ageMin.addActionListener(this);
		ageMax.addActionListener(this);
		
		output = new JTextArea(5,30); output.setEditable(false);
		mainPanel.add(output, BorderLayout.SOUTH);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		int minAgeValue = (int) ageMin.getSelectedItem();
		int maxAgeValue = (int) ageMax.getSelectedItem();
		Optional<Sesso> sex = Optional.empty();	
		if (!entrambi.isSelected()){
			sex = Optional.of(m.isSelected() ? Sesso.MASCHIO : Sesso.FEMMINA);  
		}
		
		Optional<SondaggioPercentuale> sondaggioFiltrato = controller.getSondaggioPercentuale(minAgeValue, maxAgeValue, sex);
		if (sondaggioFiltrato.isPresent()) {
			output.setText(sondaggioFiltrato.get().toString());
		} //else {
//			JOptionPane.showMessageDialog(this, "I parametri selezionati danno luogo ad un insieme vuoto di interviste");
//		}
	}

}
