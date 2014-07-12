package gui;

import java.awt.BorderLayout;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;

public class Sobre extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();

	public Sobre() {
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{
			JLabel lblDanyloDaSilva = new JLabel("Danylo da Silva Souza");
			lblDanyloDaSilva.setBounds(22, 72, 249, 15);
			contentPanel.add(lblDanyloDaSilva);
		}
		{
			JLabel lblNewLabel = new JLabel("Gabriela Nunes Pereira");
			lblNewLabel.setBounds(22, 96, 186, 15);
			contentPanel.add(lblNewLabel);
		}
		{
			JLabel lblJosAlissonOliveira = new JLabel("José Alisson Oliveira Valério");
			lblJosAlissonOliveira.setBounds(25, 123, 217, 15);
			contentPanel.add(lblJosAlissonOliveira);
		}
		{
			JLabel lblLeonardoMaxwellPereira = new JLabel("Leonardo Maxwell Pereira Formiga");
			lblLeonardoMaxwellPereira.setBounds(22, 150, 262, 15);
			contentPanel.add(lblLeonardoMaxwellPereira);
		}
		setTitle("Desenvolvedores");
	}

}
