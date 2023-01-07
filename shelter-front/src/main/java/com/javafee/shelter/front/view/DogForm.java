package com.javafee.shelter.front.view;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import lombok.Getter;

@Getter
public class DogForm {
	private JFrame frame;
	private JTextField textImie;
	private JTextField textRasa;
	private JTextField textWiek;
	private JCheckBox checkBoxPilnosc;
	private JButton buttonZatwierdz;
	private JButton buttonAnuluj;
	private JPanel Panel;
	private JLabel LabelIloscPsow;
	private JLabel lblIloscPsowDoAdopcji;
	private JCheckBox checkBoxRodowod;

	public DogForm() {
		frame = new JFrame("DogForm");
		frame.setContentPane(Panel);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.pack();
	}
}
