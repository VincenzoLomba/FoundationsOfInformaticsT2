package elbonia.ui;
import elbonia.model.Partito;

import java.awt.Dimension;
import java.util.List;
import java.util.Map;

import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;


public class ElectionTablePanel extends JScrollPane {
	private static final long serialVersionUID = 1L;
	
	private JTable table;
	private List<Partito> listaPartiti;
	
	public ElectionTablePanel(List<Partito> listaPartiti, Map<String, Integer> mappaSeggi) {
		this.listaPartiti = listaPartiti;
		Object[] titoliColonne = {"Partito", "Voti", "Seggi"};
		Object[][] rowData = new Object[listaPartiti.size()][3];
		for (int i=0; i<listaPartiti.size(); i++){
			Partito p = listaPartiti.get(i);
			rowData[i][0] = p.getNome();
			rowData[i][1] = p.getVoti();
			rowData[i][2] = mappaSeggi.get(p.getNome());
		}
		
		table = new JTable(rowData, titoliColonne);
		getViewport().add(table, null);
		setPreferredSize(new Dimension(300,87));
		((JLabel) table.getDefaultRenderer(Object.class)).setHorizontalAlignment(SwingConstants.CENTER);
	}
	
	public void update(Map<String, Integer> mappaSeggi) {
		for (int i=0; i<listaPartiti.size(); i++){
			Partito p = listaPartiti.get(i);
			table.setValueAt(p.getNome(), i,0);
			table.setValueAt(p.getVoti(), i,1);
			table.setValueAt(mappaSeggi.get(p.getNome()),i,2);
		}
	}
}
