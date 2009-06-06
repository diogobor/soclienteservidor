package br.ufrj.dcc.so.vista;

import java.awt.Component;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;

import br.ufrj.dcc.so.controle.Cliente;

public class Comecar extends JFrame implements ActionListener{
	
	/**
	 * Classe responsavel por iniciar a execucao do Programa.
	 */
	private static final long serialVersionUID = 1L;
	
	private static final String RAIZSERVIDOR ="filesServer/arquivos";
	
	private static final String RAIZCLIENTE ="filesClient/arquivos";
	
	static Thread controla = null;
	
	public static JanelaPrincipal janela = null;
	
	public static PainelPrincipal painelFundo = null;
	
//	public static JPanel painelMemoria = null;
//	
//	public static JPanel painelMemoriaControl = null;
//	
	public static JPanel painelOpcoes = null;
	
	public static FileTree painelCliente = null;
	
	public static FileTree painelServidor = null;
	
	public static JPanel painelMsg = null;

	public static Font fonte = null;
	
	public static JLabel statusServidor = null;
	
	public static JLabel statusArquivo = null;
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
	private JRadioButton enviarArqDirButton = null;
	
	private JRadioButton enviarArqExtServButton = null;
	
	private JRadioButton recArqButton = null;
	
	private JRadioButton recArqExtButton = null;
	
	private JRadioButton renameArqButton = null;
	
	private JRadioButton deleteArqButton = null;
	
	private JRadioButton deleteArqExtButton = null;
	
	private JRadioButton infArqExtButton = null;
	

	
	/**
	 * Construtor da Classe. Cria uma nova janela e coloca o Menu e a imagem do
	 * Menu.
	 */
	
	public Comecar() {
		
		JanelaPrincipal.ProgramaLargura = 830;
		
		JanelaPrincipal.ProgramaAltura = 530;

		BarraDeMenu menu = new BarraDeMenu();
		
//		Color corMemoriaControl = new Color(255,255,255);
		
//		JScrollPane scrollTextArea = new JScrollPane(listaMemoria);
		
//		Color corMemoria = new Color(198,255,198);
		
		JScrollPane scrollTextPrograma = new JScrollPane(listaPrograma);
		
		criarPaines();
		colocarOpcoes();

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
		
		/*File f=new File(".");
		JTree arvore = new JTree(addNodes(null, f));)*/
		
		
		
		janela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		janela.setJMenuBar(menu.getBarraMenu());

		janela.setLayout(null);
		janela.add(painelFundo);
//		janela.add(painelMemoria);
		janela.add(painelOpcoes);
		janela.add(painelCliente);
		janela.add(painelServidor);
		janela.add(painelMsg);
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
	
	public void actionPerformed(ActionEvent evt) {
		Object source = evt.getSource();

		if (source == enviarArqDirButton) {
			
			System.out.println("Clicando no enviar Arquivo para o Servidor");
			desbloqueiaPainelCliente();
			bloqueiaPainelServidor();
		}	
		else if (source == infArqExtButton){
			System.out.println("Informacao do arquivo");
			
		}
		else if (source == deleteArqButton){
			MainConsole.apagarArquivo("");
			bloqueiaPainelCliente();
		}
		else if (source == enviarArqExtServButton){
			try {
				getExtensao();	
				bloqueiaPainelServidor();
			} catch (Exception e) {
				System.out.println("Acao Cancelar acionada");
			}
		}
		else if (source == recArqExtButton){
			try {
				getExtensao();
				bloqueiaPainelCliente();
			} catch (Exception e) {
				System.out.println("Acao Cancelar acionada");
			}
		}
		else if (source == deleteArqExtButton){
			try {
				//Cliente.mensagem = getExtensao();
				getExtensao();
				bloqueiaPainelCliente();
			} catch (Exception e) {
				System.out.println("Acao Cancelar acionada");
			}
		}
		
	}	
	public void criarPaines(){
		if (painelFundo == null) {
			painelFundo = new PainelPrincipal();
		}
		
		if (janela == null) {
			janela = new JanelaPrincipal();
		}

		if (painelOpcoes == null) {
			painelOpcoes = new JPanel();
		}
		
		if (painelCliente == null) {
			File diretorioCliente = new File(RAIZCLIENTE);
			painelCliente = new FileTree(diretorioCliente);
			painelCliente.setEnabled(false);
			bloqueiaPainelCliente();
		}
		
		if (painelServidor == null) {
			File diretorioServidor = new File(RAIZSERVIDOR);
			painelServidor = new FileTree(diretorioServidor);
			bloqueiaPainelServidor();
		}
		
		if (painelMsg == null) {
			painelMsg = new JPanel();
		}

// Painel do Fundo		
		painelFundo.setBounds(0, 0, 350, 330);

// Painel Opcoes	
		painelOpcoes.setBounds(10, 20, 250, 290);
		painelOpcoes.setLayout(new GridLayout(8, 1));	
		painelOpcoes.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Opcoes"));
		 
// Painel Cliente
	    
	    painelCliente.setBounds(280, 20, 250, 290);
	    //painelCliente.setLayout(new GridLayout(8, 1));
	    painelCliente.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Cliente"));
	    
// Painel Servidor
	    
	    painelServidor.setBounds(550, 20, 250, 290);
	    //painelServidor.setLayout(new GridLayout(8, 1));	
	    painelServidor.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Servidor"));
	    
// Painel Msg
	    
	    painelMsg.setBounds(10, 320, 790, 100);
	    painelMsg.setLayout(new GridLayout(8, 1));	
	    painelMsg.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Mensagens"));
	    
	    msgNoServidor("Nao conectado !");
	    
	    painelMsg.add(statusServidor);
	    
		PainelPrincipal.situacaoServidor = "Nao conectado !";


	}
	public void colocarOpcoes(){
		
		//Radio Buttons do painel Opcoes
		
		enviarArqDirButton = new JRadioButton("Enviar arquivo para o Servidor");
	    enviarArqDirButton.setMnemonic(KeyEvent.VK_E);
	    enviarArqDirButton.setActionCommand("Enviar arquivo para o Servidor");

	    enviarArqExtServButton = new JRadioButton("Enviar arquivos por extensao");
	    enviarArqExtServButton.setMnemonic(KeyEvent.VK_N);
	    enviarArqExtServButton.setActionCommand("Enviar arquivos por extensao");
	    //enviarArqExtServButton.setSelected(true);	    

	    recArqButton = new JRadioButton("Receber arquivo do Servidor");
	    recArqButton.setMnemonic(KeyEvent.VK_R);
	    recArqButton.setActionCommand("Receber arquivo do Servidor");
	    
	    recArqExtButton = new JRadioButton("Receber arquivos por extensao");
	    recArqExtButton.setMnemonic(KeyEvent.VK_C);
	    recArqExtButton.setActionCommand("Receber arquivos por extensao");

	    renameArqButton = new JRadioButton("Renomear arquivo no Servidor");
	    renameArqButton.setMnemonic(KeyEvent.VK_P);
	    renameArqButton.setActionCommand("Renomear arquivo no Servidor");
	    
	    deleteArqButton = new JRadioButton("Deletar arquivo no Servidor");
	    deleteArqButton.setMnemonic(KeyEvent.VK_D);
	    deleteArqButton.setActionCommand("Deletar arquivo no Servidor");
	    
	    deleteArqExtButton = new JRadioButton("Deletar arquivos por extensao");
	    deleteArqExtButton.setMnemonic(KeyEvent.VK_L);
	    deleteArqExtButton.setActionCommand("Deletar arquivos por extensao");
	    
	    infArqExtButton = new JRadioButton("Dados do arquivo");
	    infArqExtButton.setMnemonic(KeyEvent.VK_O);
	    infArqExtButton.setActionCommand("Dados do arquivo");
	    

	    //Juntar os Radio Buttons.
	    ButtonGroup radioOpcoes = new ButtonGroup();
	    radioOpcoes.add(enviarArqDirButton);
	    radioOpcoes.add(enviarArqExtServButton);
	    radioOpcoes.add(recArqButton);
	    radioOpcoes.add(recArqExtButton);
	    radioOpcoes.add(renameArqButton);
	    radioOpcoes.add(deleteArqButton);
	    radioOpcoes.add(deleteArqExtButton);
	    radioOpcoes.add(infArqExtButton);

	    //Listener dos RadioButtons
	    enviarArqDirButton.addActionListener(this);
	    enviarArqExtServButton.addActionListener(this);
	    recArqButton.addActionListener(this);
	    recArqExtButton.addActionListener(this);
	    renameArqButton.addActionListener(this);
	    deleteArqButton.addActionListener(this);
	    deleteArqExtButton.addActionListener(this);
	    infArqExtButton.addActionListener(this);
	    
	    //Adicionando no painel Opcoes
	    painelOpcoes.add(enviarArqDirButton);
	    painelOpcoes.add(enviarArqExtServButton);
	    painelOpcoes.add(recArqButton);
	    painelOpcoes.add(recArqExtButton);
	    painelOpcoes.add(renameArqButton);
	    painelOpcoes.add(deleteArqButton);
	    painelOpcoes.add(deleteArqExtButton);
	    painelOpcoes.add(infArqExtButton);
	}
	
	public String getExtensao(){
		String extensao = JOptionPane.showInputDialog("Digite a extensao:").toLowerCase();
		if (extensao == null){
			extensao = "";
		}
		return extensao;
	}
	
	public static void msgNoServidor(String texto){
		statusServidor = new JLabel(texto);
		fonte = new Font( "Verdana", Font.BOLD, 12 );
		statusServidor.setFont(fonte);
		statusServidor.setBounds(10, 20, statusServidor.WIDTH, statusServidor.HEIGHT);
		painelMsg.removeAll();
		painelMsg.add(statusServidor);
		painelMsg.revalidate();
	}
	
	public static void bloqueiaPainelCliente(){
		painelCliente.setEnabled(false);
	}
	public static void bloqueiaPainelServidor(){
		painelServidor.setEnabled(false);
	}
	public static void desbloqueiaPainelCliente(){
		painelCliente.setEnabled(true);
	}
	public static void desbloqueiaPainelServidor(){
		painelServidor.setEnabled(true);
	}
}
