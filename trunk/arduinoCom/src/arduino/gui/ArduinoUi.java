package arduino.gui;

import gnu.io.NoSuchPortException;

import java.awt.EventQueue;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.SpringLayout;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;
import javax.swing.JToggleButton;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;

import arduino.conn.SerialConnection;
import arduino.conn.packet.LCDPacket;

import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JTextField;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;

/**
 * 
 * @author Alisson Oliveira
 * 
 */
public class ArduinoUi {

	private JFrame frame;
	private boolean display = true;
	private boolean cursor = false;
	private boolean blink = false;
	private boolean toright = true;
	private boolean scroll = false;
	private boolean clear = false;
	JFormattedTextField coluna;
	JFormattedTextField linha;
	private SerialConnection con;
	private LCDPacket packet = new LCDPacket();
	JLabel status = new JLabel("Aguardando Conexão");
	JLabel lblPorta_1 = new JLabel("porta");
	private JTextField textField;
	JComboBox<String> portlist;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					@SuppressWarnings("unused")
					ArduinoUi window = new ArduinoUi();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	private class EventWindownHandler extends WindowAdapter {
		@Override
		public void windowClosing(WindowEvent e) {
			if(con != null && con.isConnected()) {
				con.close();
			}
		}
	}

	/**
	 * Create the application.
	 */
	public ArduinoUi() {
		initialize();
		frame.setVisible(true);
		try {
			con = new SerialConnection();
			status.setText("Estabelecendo conexão");
			Thread.sleep(2000);
			status.setText("Conexão estabelecida");
			for(String st : con.getSerialPorts().keySet()) {
				portlist.addItem(st);
			}
			lblPorta_1.setText(con.getSerialPort().getName());
		} catch (NoSuchPortException e) {
			status.setText("Serial port não encontrada");
		} catch (InterruptedException e) {
			status.setText("Conexão não estabelecida");
			e.printStackTrace();
		}
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setTitle("Mensageiro do Amor");
		frame.setBounds(100, 100, 450, 300);
		frame.addWindowListener(new EventWindownHandler());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		SpringLayout springLayout = new SpringLayout();
		frame.getContentPane().setLayout(springLayout);

		JPanel panel = new JPanel();
		springLayout.putConstraint(SpringLayout.NORTH, panel, 10, SpringLayout.NORTH, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, panel, 10, SpringLayout.WEST, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.SOUTH, panel, 112, SpringLayout.NORTH, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, panel, 438, SpringLayout.WEST, frame.getContentPane());
		frame.getContentPane().add(panel);

		JPanel panel_1 = new JPanel();
		springLayout.putConstraint(SpringLayout.NORTH, panel_1, 6, SpringLayout.SOUTH, panel);
		springLayout.putConstraint(SpringLayout.WEST, panel_1, 0, SpringLayout.WEST, panel);
		springLayout.putConstraint(SpringLayout.EAST, panel_1, 0, SpringLayout.EAST, panel);
		panel_1.setBorder(new TitledBorder(null, "Op\u00E7\u00F5es", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		SpringLayout sl_panel = new SpringLayout();
		panel.setLayout(sl_panel);

		JToggleButton btnLigar = new JToggleButton("On/Off");
		sl_panel.putConstraint(SpringLayout.SOUTH, btnLigar, -41, SpringLayout.SOUTH, panel);
		btnLigar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				display ^= true;
				packet.setDisplay(display);
				try {
					if(con == null) {
						status.setText("Aguardando Conexão");
						return;
					}
					if(!con.isConnected()) {
						lblPorta_1.setText("Não conectado");
						return;
					}
					con.sendPacket(packet);
				} catch (IOException e1) {
					status.setText("Conexão não estabelecida");
				}
			}
		});
		panel.add(btnLigar);

		textField = new JTextField();
		sl_panel.putConstraint(SpringLayout.WEST, textField, 0, SpringLayout.WEST, panel);
		panel.add(textField);
		textField.setColumns(10);

		JButton btnEnviar = new JButton("Enviar");
		btnEnviar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				packet.setText(textField.getText());
				int x, y;
				try {
					x = Integer.parseInt(linha.getText());
				} catch (Exception ex) {
					x = 0;
				}
				try {
					y = Integer.parseInt(coluna.getText());
				} catch (Exception ex) {
					y = 0;
				}
				packet.setPosition((byte)x, (byte)y);
				try {
					if(con == null) {
						status.setText("Aguardando Conexão");
						return;
					}
					if(!con.isConnected()) {
						lblPorta_1.setText("Não conectado");
						return;
					}
					con.sendPacket(packet);
				} catch (IOException e1) {
					status.setText("Conexão não estabelecida");
				}
			}
		});
		sl_panel.putConstraint(SpringLayout.NORTH, textField, 3, SpringLayout.NORTH, btnEnviar);
		sl_panel.putConstraint(SpringLayout.EAST, textField, -6, SpringLayout.WEST, btnEnviar);
		sl_panel.putConstraint(SpringLayout.WEST, btnEnviar, 0, SpringLayout.WEST, btnLigar);
		sl_panel.putConstraint(SpringLayout.SOUTH, btnEnviar, -6, SpringLayout.NORTH, btnLigar);
		panel.add(btnEnviar);
		frame.getContentPane().add(panel_1);
		SpringLayout sl_panel_1 = new SpringLayout();
		panel_1.setLayout(sl_panel_1);

		JCheckBox chckbxCursor = new JCheckBox("cursor");
		chckbxCursor.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				cursor ^= true;
				packet.setCursor(cursor);
			}
		});
		sl_panel_1.putConstraint(SpringLayout.NORTH, chckbxCursor, 0, SpringLayout.NORTH, panel_1);
		sl_panel_1.putConstraint(SpringLayout.WEST, chckbxCursor, 0, SpringLayout.WEST, panel_1);
		panel_1.add(chckbxCursor);

		JCheckBox chckbxBlink = new JCheckBox("blink");
		chckbxBlink.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				blink ^= true;
				packet.setBlink(blink);
			}
		});
		sl_panel_1.putConstraint(SpringLayout.NORTH, chckbxBlink, 6, SpringLayout.SOUTH, chckbxCursor);
		sl_panel_1.putConstraint(SpringLayout.WEST, chckbxBlink, 0, SpringLayout.WEST, panel_1);
		panel_1.add(chckbxBlink);

		JCheckBox chckbxDaEsquerdaPara = new JCheckBox("da esquerda para direita");
		chckbxDaEsquerdaPara.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				toright ^= true;
				packet.setToRight(toright);
			}
		});
		sl_panel_1.putConstraint(SpringLayout.NORTH, chckbxDaEsquerdaPara, 6, SpringLayout.SOUTH, chckbxBlink);
		sl_panel_1.putConstraint(SpringLayout.WEST, chckbxDaEsquerdaPara, 0, SpringLayout.WEST, chckbxCursor);
		panel_1.add(chckbxDaEsquerdaPara);

		JCheckBox chckbxAutoScroll = new JCheckBox("auto scroll");
		chckbxAutoScroll.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				scroll ^= true;
				packet.setScroll(scroll);
			}
		});
		sl_panel_1.putConstraint(SpringLayout.NORTH, chckbxAutoScroll, 0, SpringLayout.NORTH, chckbxCursor);
		sl_panel_1.putConstraint(SpringLayout.WEST, chckbxAutoScroll, 30,SpringLayout.EAST, chckbxCursor);
		panel_1.add(chckbxAutoScroll);

		JCheckBox chckbxClear = new JCheckBox("clear");
		chckbxClear.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				clear ^= true;
				packet.setClear(clear);
			}
		});
		sl_panel_1.putConstraint(SpringLayout.NORTH, chckbxClear, 0, SpringLayout.NORTH, chckbxBlink);
		sl_panel_1.putConstraint(SpringLayout.WEST, chckbxClear, 0,SpringLayout.WEST, chckbxAutoScroll);
		panel_1.add(chckbxClear);

		JLabel lblNewLabel = new JLabel("status:");
		springLayout.putConstraint(SpringLayout.SOUTH, panel_1, -6, SpringLayout.NORTH, lblNewLabel);

		JLabel lblLine = new JLabel("linha");
		sl_panel_1.putConstraint(SpringLayout.NORTH, lblLine, 4, SpringLayout.NORTH, chckbxCursor);
		panel_1.add(lblLine);

		JLabel lblNewLabel_1 = new JLabel("coluna");
		sl_panel_1.putConstraint(SpringLayout.WEST, lblLine, 0, SpringLayout.WEST, lblNewLabel_1);
		sl_panel_1.putConstraint(SpringLayout.NORTH, lblNewLabel_1, 0, SpringLayout.NORTH, chckbxBlink);
		sl_panel_1.putConstraint(SpringLayout.WEST, lblNewLabel_1, 66, SpringLayout.EAST, chckbxClear);
		panel_1.add(lblNewLabel_1);

		linha = new JFormattedTextField();
		linha.setColumns(5);
		sl_panel_1.putConstraint(SpringLayout.NORTH, linha, 0, SpringLayout.NORTH, chckbxCursor);
		sl_panel_1.putConstraint(SpringLayout.WEST, linha, 31, SpringLayout.EAST, lblLine);
		linha.setText("0");
		panel_1.add(linha);

		coluna = new JFormattedTextField();
		coluna.setColumns(5);
		sl_panel_1.putConstraint(SpringLayout.WEST, coluna, 19, SpringLayout.EAST, lblNewLabel_1);
		coluna.setText("0");
		sl_panel_1.putConstraint(SpringLayout.NORTH, coluna, 0, SpringLayout.NORTH, chckbxBlink);
		panel_1.add(coluna);
		springLayout.putConstraint(SpringLayout.WEST, lblNewLabel, 0, SpringLayout.WEST, panel);

		portlist = new JComboBox<>();
		sl_panel.putConstraint(SpringLayout.NORTH, portlist, 0, SpringLayout.NORTH, btnLigar);
		panel.add(portlist);

		JLabel lblPorta = new JLabel("Porta:");
		sl_panel.putConstraint(SpringLayout.WEST, portlist, 6, SpringLayout.EAST, lblPorta);
		sl_panel.putConstraint(SpringLayout.NORTH, lblPorta, 5, SpringLayout.NORTH, btnLigar);
		panel.add(lblPorta);

		JButton btnConectar = new JButton("conectar");
		sl_panel.putConstraint(SpringLayout.NORTH, btnConectar, 6, SpringLayout.SOUTH, btnLigar);
		sl_panel.putConstraint(SpringLayout.EAST, btnConectar, -10, SpringLayout.EAST, panel);
		btnConectar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					if(con != null && con.isConnected()) {
						con.close();	
					}
					con = new SerialConnection((String) portlist.getSelectedItem());
					lblPorta_1.setText(con.getSerialPort().getName());
					status.setText("Conexão estabelecida");
				} catch (NoSuchPortException e1) {
					status.setText("Serial Port Não encontrada");
				} catch (Exception e2) {
					lblPorta_1.setText("Não Conectado");
				}
			}
		});
		panel.add(btnConectar);

		JLabel lblConectado = new JLabel("conectado:");
		sl_panel.putConstraint(SpringLayout.NORTH, lblConectado, 7, SpringLayout.SOUTH, portlist);
		sl_panel.putConstraint(SpringLayout.WEST, lblPorta, 0, SpringLayout.WEST, lblConectado);
		sl_panel.putConstraint(SpringLayout.WEST, lblConectado, 10, SpringLayout.WEST, panel);
		panel.add(lblConectado);

		sl_panel.putConstraint(SpringLayout.NORTH, lblPorta_1, 0, SpringLayout.NORTH, lblConectado);
		sl_panel.putConstraint(SpringLayout.WEST, lblPorta_1, 6, SpringLayout.EAST, lblConectado);
		panel.add(lblPorta_1);

		JButton btnProcurar = new JButton("procurar...");
		sl_panel.putConstraint(SpringLayout.WEST, btnLigar, 6, SpringLayout.EAST, btnProcurar);
		btnProcurar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					if(con == null)
						con = new SerialConnection();
					else {
						con.close();
						con = new SerialConnection();	
					}
					portlist.removeAllItems();
					for(String s : con.getSerialPorts().keySet()) {
						portlist.addItem(s);
					}
					status.setText("Conexão estabelecida");
					lblPorta_1.setText(con.getSerialPort().getName());
					
				} catch (NoSuchPortException e1) {
					status.setText("Porta Serial não encontrada");
				} catch (Exception e2) {
					lblPorta_1.setText("Não conectado");
				}
			}
		});
		sl_panel.putConstraint(SpringLayout.WEST, btnProcurar, 223, SpringLayout.WEST, panel);
		sl_panel.putConstraint(SpringLayout.EAST, portlist, -6, SpringLayout.WEST, btnProcurar);
		sl_panel.putConstraint(SpringLayout.NORTH, btnProcurar, 0, SpringLayout.NORTH, btnLigar);
		panel.add(btnProcurar);
		springLayout.putConstraint(SpringLayout.SOUTH, lblNewLabel, -10, SpringLayout.SOUTH, frame.getContentPane());
		frame.getContentPane().add(lblNewLabel);

		status.setText("Aguardando Conexão");
		springLayout.putConstraint(SpringLayout.NORTH, status, 0, SpringLayout.NORTH, lblNewLabel);
		springLayout.putConstraint(SpringLayout.WEST, status, 16, SpringLayout.EAST, lblNewLabel);
		frame.getContentPane().add(status);
	}
}
