package gui;

import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class ControlUIArduino {
	
	JFrame janela;
	JPanel painelBotoes;
	JButton[] botoes;
	
	private void inicializarArrayBotao() {
		botoes = new JButton[3];
		botoes[0] = new JButton("Ligar");
		botoes[1] = new JButton("Desligar");
		botoes[2] = new JButton("Enviar mensagem");
	}
	
	public void adicionarPaineis(JPanel painel){
		janela.add(painel);
	}
	private void adicionarPaineis(){
		painelBotoes = new JPanel();
		adicionarPaineis(painelBotoes);
	}
	private void addTextField(JTextField tField) {
		JPanel painelCampoMensagem = new JPanel();
		painelCampoMensagem.add(tField);
		painelCampoMensagem.add(botoes[2]);
		adicionarPaineis(painelCampoMensagem);
	}
	
	private void addTextField(){
		JTextField campoMensagem = new JTextField();
		campoMensagem.setColumns(20);
		addTextField(campoMensagem);
	}
	
	public void addBotoesAoPainel(JPanel painel, JButton[] botoes) {
		for (int i = 0; i < botoes.length-1; i++) {
			painel.add(botoes[i]);
		}
	}
	
	private void addBotoesAoPainel(){
		addBotoesAoPainel(painelBotoes, botoes);
		prepararBotoesDefault(botoes);
	}
	
	private String getMsgBtEnviarMsg(JButton btMsg) {
		String msg = btMsg.getText().toString();
		return msg;
	}
	
	
	
	private void prepararBotoesDefault(JButton[] botoes) {
		final JButton btMsg = botoes[2];
		ActionListener ligarListener = new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// chamar método que liga o Arduíno
			}
		};
		
		ActionListener desligarListener = new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// chamar método que desliga o Arduíno				
			}
		};
		
		ActionListener enviarMensagemListener = new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String mensagem = getMsgBtEnviarMsg(btMsg);
				// chamar método que enviar a mensagem para o led conectado ao Arduíno
			}
		};
		
		//setando os listeners aos default buttons
		botoes[0].addActionListener(ligarListener); // botão ligar
		botoes[1].addActionListener(desligarListener); // botão desligar
		botoes[2].addActionListener(enviarMensagemListener); // botão enviarMensagem
	}
	
	private void prepararJanela(){
		janela = new JFrame("Controlando o Arduíno");
		janela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		janela.setLayout(new GridLayout());
	}
	
	public void mostrarJanela(int largura, int altura){
		//janela.pack(); // faz com que os componentes ocupem o menor espaço possível
		janela.setSize(largura, altura);
		janela.setLocationRelativeTo(null);
		janela.setVisible(true);
	}
	
	private void mostrarJanela(){
		mostrarJanela(614, 84);
	}
	
	public void montarTela(){
		prepararJanela();
		adicionarPaineis();
		inicializarArrayBotao();
		addTextField();
		addBotoesAoPainel();		
		mostrarJanela();
		
		
		
		
	}

	
	public static void main(String[] args){
		new ControlUIArduino().montarTela();
	}
	
}

