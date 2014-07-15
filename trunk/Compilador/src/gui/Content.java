package gui;

import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JLabel;

import java.awt.Font;

import javax.swing.JTextField;
import javax.swing.JButton;

import analisador.lexico.Scanner;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;

public class Content extends JPanel {

	private static final long serialVersionUID = 1L;
	private JTextField txtArquivo;
	private final JFileChooser chooser;
	private File file;
	private File toSave;
	private final JLabel msg;
	private JTextField txtSalvar;
	public Content() {
		setLayout(null);

		JLabel lblG = new JLabel("Analisador Léxico");
		lblG.setBounds(128, 34, 153, 19);
		lblG.setFont(new Font("Dialog", Font.BOLD, 16));
		add(lblG);
		chooser = new JFileChooser();
		txtArquivo = new JTextField();
		txtArquivo.setEditable(false);
		txtArquivo.setFont(new Font("Dialog", Font.PLAIN, 14));
		txtArquivo.setBounds(64, 65, 238, 25);
		add(txtArquivo);
		txtArquivo.setColumns(10);

		JButton btnArquivo = new JButton("Arquivo");
		btnArquivo.setBounds(314, 65, 118, 25);
		btnArquivo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (chooser.showOpenDialog(getParent()) == JFileChooser.APPROVE_OPTION) {
					file = chooser.getSelectedFile();
					txtArquivo.setText(file.toString());
				}
			}
		});
		add(btnArquivo);
		
		JButton btnToken = new JButton("Tokenizar");
		btnToken.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					Scanner p = new Scanner(file, toSave);
					p.parse();
					msg.setText("Arquivo token gerado!");
				} catch (IOException e1) {
					
				}
			}
		});
		btnToken.setBounds(314, 135, 118, 25);
		add(btnToken);
		
		txtSalvar = new JTextField();
		txtSalvar.setEditable(false);
		txtSalvar.setFont(new Font("Dialog", Font.PLAIN, 14));
		txtSalvar.setBounds(64, 99, 238, 25);
		add(txtSalvar);
		txtSalvar.setColumns(10);
		
		JLabel lblSource = new JLabel("source");
		lblSource.setBounds(12, 68, 70, 15);
		add(lblSource);
		
		JLabel lblSa = new JLabel("saida");
		lblSa.setBounds(12, 103, 70, 15);
		add(lblSa);
		
		JButton btnEs = new JButton("Escolher");
		btnEs.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(chooser.showSaveDialog(getParent()) == JFileChooser.APPROVE_OPTION) {
					toSave = chooser.getSelectedFile();
					txtSalvar.setText(toSave.toString());
				}
			}
		});
		btnEs.setBounds(314, 98, 117, 25);
		add(btnEs);
		
		msg = new JLabel("");
		msg.setBounds(64, 135, 238, 15);
		add(msg);
		
		JButton btnSobre = new JButton("Sobre");
		btnSobre.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Sobre dialog = new Sobre();
				dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
				dialog.setVisible(true);
			}
		});
		btnSobre.setBounds(315, 173, 117, 25);
		add(btnSobre);

	}
}
