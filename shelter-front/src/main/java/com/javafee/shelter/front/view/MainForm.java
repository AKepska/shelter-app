package com.javafee.shelter.front.view;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import com.javafee.shelter.front.view.model.ShelterTableModel;

import lombok.Getter;

@Getter
public class MainForm {
	private JFrame frame;
	private JPanel panel1;
	private JButton btnUsun;
	private JButton btnDodaj;
	private JButton btnModyfikuj;
	private JButton btnAdoptuj;
	private JTextField textFieldWyszukaj;
	private JButton buttonSzukaj;
	private JLabel fieldiloscPsowAdoptowanych;
	private JLabel fieldIloscPsowPrzyjetych;
	private JLabel fieldSredniWiek;
	private JLabel fieldOstatniaAdopcja;
	private JLabel fldIloscAdopcji;
	private JLabel fldIloscPsowPrzyjetych;
	private JLabel fldSredniWiekPsow;
	private JLabel fldOstatniaAdopcja;
	private JLabel sredniWiekPsowAdoptowanych;
	private JTable tableShelter;
	private JScrollPane scrollPane;

	public MainForm() {
		frame = new JFrame("MainForm");
		frame.setContentPane(panel1);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
	}

	private void createUIComponents() {
		// TODO: place custom component creation code here
		ShelterTableModel shelterTableModel = new ShelterTableModel();

		this.scrollPane = new JScrollPane();
		tableShelter = new JTable();
		tableShelter.setModel(shelterTableModel);
		tableShelter.setAutoCreateRowSorter(true);
		scrollPane.setViewportView(tableShelter);
	}

	// pominąć
	//        if (defaultTableConfiguration) {
	//            @SuppressWarnings("unused")
	//            TableFilterHeader customTableFilterHeader = new CustomTableFilterHeader(table);
	//            table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	//            table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
	//        }
	// pominąć
}
