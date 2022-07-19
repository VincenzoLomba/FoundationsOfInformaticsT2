package elbonia.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BoundedRangeModel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import elbonia.ui.controller.Controller;

public class ElectionSlider extends JSlider implements ChangeListener {

	private static final long serialVersionUID = 1L;
	
	private Controller controller;
	private ArrayList<ActionListener> actionListenerList = new ArrayList<>();

	private int dimensioneCollegio;
	private int numeroCollegi;

	public ElectionSlider(Controller controller) {
		this.controller = controller;
		setUp();
	}

	public ElectionSlider(Controller controller, int orientation) {
		super(orientation);
		this.controller = controller;
		setUp();
	}

	public ElectionSlider(Controller controller, BoundedRangeModel brm) {
		super(brm);
		this.controller = controller;
		setUp();
	}
	
	public void addActionListener(ActionListener listener) {
		if (!actionListenerList.contains(listener)) {
			actionListenerList.add(listener);
		}
	}
	
	public void removeActionListener(ActionListener listener) {
		actionListenerList.remove(listener);
	}
	
	public int getNumeroCollegi() {
		return numeroCollegi;
	}
	
	public int getDimensioneCollegio() {
		return dimensioneCollegio;
	}
	
	protected void fireActionListener() {
		ActionEvent evt = new ActionEvent(this, ActionEvent.ACTION_PERFORMED, "Moved");
		for (ActionListener listener : actionListenerList) {
			listener.actionPerformed(evt);
		}
	}

	private void setUp() {
		setMinimum(1);
		setMaximum(controller.getSeggiMassimi());
		setMajorTickSpacing(1);
		setSnapToTicks(true);
		addChangeListener(this);
	}

	@Override
	public void stateChanged(ChangeEvent e) {
		float numeroTeoricoCollegi = getValue();
		if (numeroTeoricoCollegi==0) return;
		float dimensioneTeoricaCollegio = this.controller.getSeggiMassimi()/numeroTeoricoCollegi;
		boolean dimensioneCollegioNonIntera = dimensioneTeoricaCollegio - Math.round(dimensioneTeoricaCollegio) != 0;
		if (dimensioneCollegioNonIntera) {
			dimensioneCollegio = Math.round(dimensioneTeoricaCollegio);
			numeroCollegi = this.controller.getSeggiMassimi()/dimensioneCollegio;
			removeChangeListener(this);
			setValue(numeroCollegi); 
			revalidate();
			addChangeListener(this);
		}
		else {
			dimensioneCollegio = (int) dimensioneTeoricaCollegio;
			numeroCollegi = getValue();						
		}
		fireActionListener();
	}

}
