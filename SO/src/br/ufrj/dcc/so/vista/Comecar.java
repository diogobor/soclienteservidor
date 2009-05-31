package br.ufrj.dcc.so.vista;

import java.awt.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;


public class Comecar extends JFrame implements ActionListener{
	
	/**
	 * Classe responsavel por iniciar a execucao do Programa.
	 */
	private static final long serialVersionUID = 1L;
	
	static Thread controla = null;
	
	public static JanelaPrincipal janela = null;
	
	public static PainelPrincipal painelFundo = null;
	
//	public static JPanel painelMemoria = null;
//	
//	public static JPanel painelMemoriaControl = null;
//	
	public static JPanel painelOpcoes = null;
//	
//	public static JPanel painelControle = null;
//	
//	public static List listaMemoria = new List();
//	
//	public static List listaMemoriaControl = new List(7,false);
//	
	public static List listaPrograma = new List();
//	
//	public static JButton proximoPasso = null;
//	
//	public static JButton finalizar = null;
//	
//	public static JLabel modoOperacao = null;
//	
//	public static JLabel status = null;
//	
//	public static boolean cliqueMenuComecar = true;
//	
//	public static boolean cliqueProximoPassoInstrucao = false;
//	
//	public static boolean cliqueProximoPassoMicroInstrucao = false;
	
	/**
	 * Construtor da Classe. Cria uma nova janela e coloca o Menu e a imagem do
	 * Menu.
	 */
	
	public Comecar() {
		
		JanelaPrincipal.ProgramaLargura = 350;
		
		JanelaPrincipal.ProgramaAltura = 400;

		BarraDeMenu menu = new BarraDeMenu();
		
//		Color corMemoriaControl = new Color(255,255,255);
		
//		JScrollPane scrollTextArea = new JScrollPane(listaMemoria);
		
//		Color corMemoria = new Color(198,255,198);
		
		JScrollPane scrollTextPrograma = new JScrollPane(listaPrograma);
		
		
		if (painelFundo == null) {
			painelFundo = new PainelPrincipal();
		}
		
		if (janela == null) {
			janela = new JanelaPrincipal();
		}

//		if (painelMemoria == null) {
//			painelMemoria = new JPanel();
//		}
//		
//		if (painelMemoriaControl == null) {
//			painelMemoriaControl = new JPanel();
//		}
//		
		if (painelOpcoes == null) {
			painelOpcoes = new JPanel();
		}
//		
//		if (painelControle == null) {
//			painelControle = new JPanel();
//		}
		
		painelFundo.setBounds(0, 0, 350, 330);
		
//		painelMemoria.setBounds(215, 255, 197, 230);
//		
//		painelMemoria.setBackground(corMemoria);
//		
//		painelMemoriaControl.setBounds(842, 300, 20, 100);
//		
//		painelMemoriaControl.setBackground(Color.black);
//		
		painelOpcoes.setBounds(10, 20, 200, 300);
//		
		painelOpcoes.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Opcoes"));
//		
//		painelControle.setBounds(10, 10, 175, 280);
//		
//		painelControle.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Controle"));
		
		PainelPrincipal.situacaoServidor = "Nao conectado !";

//		painelMemoria.setLayout(null);
//		listaMemoria.setBackground(corMemoria);
//		scrollTextArea.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
//		scrollTextArea.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
//		scrollTextArea.setBounds(0,0,197,230);
//		painelMemoria.add(scrollTextArea);
//		
//		listaMemoria.setFocusable(true);
//		
//		painelMemoriaControl.setLayout(null);
//		listaMemoriaControl.setBackground(corMemoriaControl);
//		listaMemoriaControl.setBounds(3,0,122,108);
//		listaMemoriaControl.setFocusable(false);
//		painelMemoriaControl.add(listaMemoriaControl);
//	
//		
		
		/**
		 * Lista que ficara no Programa
		 */
		
		painelOpcoes.setLayout(null);
//		scrollTextPrograma.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
//		scrollTextPrograma.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
//		scrollTextPrograma.setBounds(0,10,155,270);
//		painelOpcoes.add(scrollTextPrograma);
		
		/**
		 * Painel Controle
		 */
	
//		painelControle.setLayout(new FlowLayout(FlowLayout.CENTER,250,10));
//		proximoPasso = new JButton("Iniciar");
//		proximoPasso.setEnabled(false);
//		proximoPasso.addActionListener(this);
//		painelControle.add(proximoPasso);
		
		JLabel modoOperacaoTitulo = new JLabel("Modo Operacao: ");
		modoOperacaoTitulo.setBounds(0, 0, 100, 100);
//		painelControle.add(modoOperacaoTitulo);
//		modoOperacao = new JLabel("Executar Programa");
//		modoOperacao.setBounds(0, 0, 100, 100);
//		modoOperacao.setFont(new java.awt.Font("Tahoma", Font.PLAIN, 12));
//		painelControle.add(modoOperacao);
//		
//		finalizar = new JButton("Finalizar");
//		finalizar.addActionListener(this);
//		finalizar.setEnabled(false);
//		painelControle.add(finalizar);
		
		JLabel statusTitulo = new JLabel("Status: ");
		statusTitulo.setBounds(0, 0, 100, 100);
//		painelControle.add(statusTitulo);
//		status = new JLabel("Carregue um Programa");
//		status.setFont(new java.awt.Font("Tahoma", Font.PLAIN, 12));
//		status.setBounds(0, 0, 100, 100);
//		painelControle.add(status);
		
		
		/**
		 * Seta a janela
		 */
		
		janela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		janela.setJMenuBar(menu.getBarraMenu());

		janela.setLayout(null);
		janela.add(painelFundo);
//		janela.add(painelMemoria);
		janela.add(painelOpcoes);
//		janela.add(painelMemoriaControl);
//		janela.add(painelControle);
		janela.setVisible(true);
		
	}
	
	public static void colocarNaMemoria(){	
		int contadorList;
		
		
//		listaMemoria.select(0);
	}
	
	public static void inicializaListMemoria(){
		int contadorList;
//		listaMemoria.removeAll();
		
		//for(contadorList = 0; contadorList < Memoria.TAM_MEM; contadorList++){
		for(contadorList = 0; contadorList < 1000; contadorList++){
//			listaMemoria.add(Integer.toString(contadorList) + ". 0");
		}
		
//		listaMemoria.select(0);
	}
	
	public static void colocarNoListPrograma(){
//		listaPrograma.removeAll();
		
	}
	
//	public static int retornarIndexListMemoria(String item){
//		
//		int indice;
//		int indiceAtual;
		
//		indiceAtual = listaMemoria.getSelectedIndex();
		
//		for(indice = 0; indice < listaMemoria.getItemCount(); indice++){
//			if (listaMemoria.getItem(indice).equals(Integer.toString(indice) + ". " + item)){
//				if(indice > indiceAtual)
//					break;
//			}
//		}
//		if (indice == listaMemoria.getItemCount()){
//			return 0;
//		}else{
//			return indice;
//		}		
//	}

//	public static int retornarIndexListMemoriaControle(String item){
//		
//		int indice;
//	
//		for(indice = 0; indice < listaMemoriaControl.getItemCount(); indice++){
//			if (listaMemoriaControl.getItem(indice).equals(item)){
//				break;
//			}
//		}
//		
//		
//		
//		
//		if (indice == listaMemoriaControl.getItemCount()){
//			return 0;
//		}else{
//			return indice;
//		}		
//	}
//	
//	public static void zeraMemoriaEPrograma(){
//		listaPrograma.removeAll();
//		listaPrograma.add("Programa Pronto!");
//		status.setText("Pronto");
//		proximoPasso.setText("Iniciar");
//		finalizar.setEnabled(false);
//		
//		cliqueProximoPassoInstrucao = false;
//		cliqueProximoPassoMicroInstrucao = false;
//		/**
//		 * zera PC no inicio de cada programa.
//		 */
//		PainelPrincipal.pc = "0";
//		
//		painelFundo.repaint();
//	}
	
	public void actionPerformed(ActionEvent evt) {
		Object source = evt.getSource();

//		if (source == proximoPasso) {
//			
//			if(proximoPasso.getText().equals("Iniciar")){
//				/**
//				 * limpa flags
//				 */
//				PainelPrincipal.zero = PainelPrincipal.sinal = PainelPrincipal.carry = PainelPrincipal.overflow = PainelPrincipal.paridade = "";
//				
//				if (!modoOperacao.getText().equals("Executar Programa")){
//					proximoPasso.setText("Proximo Passo");
//					finalizar.setEnabled(true);
//				}
//				
//				if(cliqueMenuComecar){
//					
//				}
//				
//				if(modoOperacao.getText().equals("Executar Instrucao")){
//					
//				}
//				if(modoOperacao.getText().equals("Executar MicroInstrucao")){
//					
//				}
//				
//			}else if(proximoPasso.getText().equals("Proximo Passo")){
//				
//			}			
//		}
//		else if(source == finalizar){
//			proximoPasso.setText("Iniciar");
//			finalizar.setEnabled(false);
//			
//			cliqueProximoPassoInstrucao = false;
//			cliqueProximoPassoMicroInstrucao = false;
//		}
//		
	}	
}
