package gui;

import gnu.io.NoSuchPortException;

import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EmptyBorder;

import arduino.conn.Connection;
import arduino.conn.SerialConnection;
import arduino.conn.packet.LCDPacket;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

/**
 * @author marcus
 * @author Alisson Oliveira
 * 
 */
public class ControlGUIArduino extends JFrame {

	
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textField;
	private Connection con;
	JLabel status = new JLabel("");
	private LCDPacket packet = new LCDPacket();
	
	
	private class EventWindownHandler extends WindowAdapter {
		public void windowClosing(WindowEvent e) {
			if(con != null) {
				con.close();
			}
		}
	}

	/**
	 * Create the frame.
	 */
	public ControlGUIArduino() {
		addWindowListener(new EventWindownHandler());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 424, 199);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		JButton btnLigar = new JButton("LIGAR");
		btnLigar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				packet.setDisplay(true);
				try {
					con.sendPacket(packet);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});

		JButton btnDesligar = new JButton("DESLIGAR");
		btnDesligar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				packet.setDisplay(false);
				try {
					con.sendPacket(packet);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});

		textField = new JTextField();
		textField.setColumns(10);

		JButton btnEnviarMensagem = new JButton("Enviar Mensagem");
		btnEnviarMensagem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				packet.setText(textField.getText());
				try {
					con.sendPacket(packet);
					status.setText("mensagem enviada!");
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});

		JLabel lblMensageiro = new JLabel("MENSAGEIRO DO AMOR");
		lblMensageiro.setFont(new Font("Tahoma", Font.BOLD, 18));
		
		JLabel lblStatus = new JLabel("status: ");
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addContainerGap()
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addComponent(btnDesligar)
								.addComponent(btnLigar, GroupLayout.PREFERRED_SIZE, 108, GroupLayout.PREFERRED_SIZE))
							.addGap(75)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING, false)
								.addComponent(textField)
								.addComponent(btnEnviarMensagem, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(69)
							.addComponent(lblMensageiro, GroupLayout.PREFERRED_SIZE, 233, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addContainerGap()
							.addComponent(lblStatus)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(status)))
					.addContainerGap(59, Short.MAX_VALUE))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addComponent(lblMensageiro)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(textField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnLigar))
					.addGap(18)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnEnviarMensagem)
						.addComponent(btnDesligar))
					.addPreferredGap(ComponentPlacement.RELATED, 37, Short.MAX_VALUE)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblStatus)
						.addComponent(status))
					.addContainerGap())
		);
		contentPane.setLayout(gl_contentPane);
		try {
			this.con = new SerialConnection();
			status.setText("Aguardando estabelecer conexão...");
			Thread.sleep(3000);
			
		} catch (NoSuchPortException e) {
			status.setText("Conexão recusada!");
		} catch (InterruptedException e1) {
			status.setText("Conexão interrompida");
		}
		status.setText("Conexão estabelecida");
	}

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ControlGUIArduino frame = new ControlGUIArduino();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
