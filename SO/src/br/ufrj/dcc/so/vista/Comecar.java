package br.ufrj.dcc.so.vista;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

public class Comecar extends JFrame implements ActionListener,TreeSelectionListener{
	
	/**
	 * Classe responsavel por iniciar a execucao do Programa.
	 */
	private static final long serialVersionUID = 1L;
	
	private static final String RAIZSERVIDOR ="filesServer";
	
	private static final String RAIZCLIENTE ="filesClient";
	
	static Thread controla = null;
	
	public static JanelaPrincipal janela = null;
	
	public static PainelPrincipal painelFundo = null;
	
	public static FileTree painelCliente = null;
	
	public static FileTree painelServidor = null;
	
	public static JPanel painelOpcoes = null;
	
	public static JPanel painelMsg = null;

	public static Font fonte = null;
	
	public static JLabel statusServidor = null;
	
	public static JLabel nomeStatusServidor = null;
	
	public static JLabel statusArquivo = null;

	public static List listaPrograma = new List();

	private JRadioButton enviarArqServButton = null;
	
	private JRadioButton enviarArqExtServButton = null;
	
	private JRadioButton recArqButton = null;
	
	private JRadioButton recArqExtButton = null;
	
	private JRadioButton renameArqButton = null;
	
	private JRadioButton deleteArqButton = null;
	
	private JRadioButton deleteArqExtButton = null;
	
	private JRadioButton infArqExtButton = null;
	
	private String nomeArquivoSelecionado;
	
	private String nomeDiretorio;
	
	private String caminhoArquivoSelecionado;
	

	
	/**
	 * Construtor da Classe. Cria uma nova janela e coloca o Menu e a imagem do
	 * Menu.
	 */
	
	public Comecar() {
		
		JanelaPrincipal.ProgramaLargura = 830;
		
		JanelaPrincipal.ProgramaAltura = 530;

		BarraDeMenu menu = new BarraDeMenu();
		
		JScrollPane scrollTextPrograma = new JScrollPane(listaPrograma);
		
		criarPaines();
		colocarOpcoes();

		painelCliente.addComecarListener(this);
		painelServidor.addComecarListener(this);
		
		JLabel modoOperacaoTitulo = new JLabel("Modo Operacao: ");
		modoOperacaoTitulo.setBounds(0, 0, 100, 100);
		
		JLabel statusTitulo = new JLabel("Status: ");
		statusTitulo.setBounds(0, 0, 100, 100);
		
		
		
		
		/**
		 * Seta a janela
		 */
		
		janela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		janela.setJMenuBar(menu.getBarraMenu());

		janela.setLayout(null);
		janela.add(painelFundo);
		janela.add(painelOpcoes);
		janela.add(painelCliente);
		janela.add(painelServidor);
		janela.add(painelMsg);
		janela.setVisible(true);
	
	}
	
	public void actionPerformed(ActionEvent evt) {
		Object source = evt.getSource();

		if (source == enviarArqServButton) {
			
			/*gravar arquivo cujo nome é caminhoArquivoSelecionado
			 * e gravar no servdor*/
			
			//System.out.println("Clicando no enviar Arquivo para o Servidor");
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
				String extensao;
				extensao=getExtensao();
				Comecar.msgNoServidor("arquivos do servidor com extensao "+extensao+" bloqueados");
				bloqueiaPainelCliente();
			} catch (Exception e) {
				System.out.println("Acao Cancelar acionada");
			}
		}
		
	}	
	
	public void valueChanged(TreeSelectionEvent e) {
	     /* DefaultMutableTreeNode no = (DefaultMutableTreeNode) e.getPath().getLastPathComponent();
	      JTree treeSource = (JTree) e.getSource();
	        TreePath path = treeSource.getSelectionPath();
	        System.out.println(path);
	        System.out.println(path.getPath());
	        System.out.println(path.getParentPath());
	        System.out.println(((DefaultMutableTreeNode) path.getLastPathComponent()).getUserObject());
	        System.out.println(path.getPathCount());
	      System.out.println("Diretorio: " + e.getPath() + "  " + no);*/
		
		 nomeArquivoSelecionado=e.getPath().getLastPathComponent().toString();
	     nomeDiretorio=e.getPath().getParentPath().getLastPathComponent().toString();
	     caminhoArquivoSelecionado=nomeDiretorio+'/'+nomeArquivoSelecionado;
	     
	     /*
	     File teste=new File(caminhoArquivoSelecionado);
	     
	     try{
	    	 BufferedReader br=new BufferedReader(new FileReader(teste));
	    	 
	    	 try{
	    		System.out.println(br.readLine());
	    	 }catch(IOException ioe){
	    		 
	    	 }
	    	 
	     }catch(FileNotFoundException fnfe){
	    	 
	     }*/
		
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
	    painelCliente.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Cliente"));
	    
// Painel Servidor
	    
	    painelServidor.setBounds(550, 20, 250, 290);
	    painelServidor.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Servidor"));
	    
// Painel Msg
	    
	    painelMsg.setBounds(10, 320, 790, 100);
	    painelMsg.setLayout(null);	
	    painelMsg.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Mensagens"));
	    
	    msgNoServidor("Nao Conectado !");
	    
		PainelPrincipal.situacaoServidor = "Nao conectado !";


	}
	public void colocarOpcoes(){
		
		//Radio Buttons do painel Opcoes
		
		enviarArqServButton = new JRadioButton("Enviar arquivo para o Servidor");
	    enviarArqServButton.setMnemonic(KeyEvent.VK_E);
	    enviarArqServButton.setActionCommand("Enviar arquivo para o Servidor");

	    enviarArqExtServButton = new JRadioButton("Enviar arquivos por extensao");
	    enviarArqExtServButton.setMnemonic(KeyEvent.VK_N);
	    enviarArqExtServButton.setActionCommand("Enviar arquivos por extensao");	    

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
	    radioOpcoes.add(enviarArqServButton);
	    radioOpcoes.add(enviarArqExtServButton);
	    radioOpcoes.add(recArqButton);
	    radioOpcoes.add(recArqExtButton);
	    radioOpcoes.add(renameArqButton);
	    radioOpcoes.add(deleteArqButton);
	    radioOpcoes.add(deleteArqExtButton);
	    radioOpcoes.add(infArqExtButton);

	    //Listener dos RadioButtons
	    enviarArqServButton.addActionListener(this);
	    enviarArqExtServButton.addActionListener(this);
	    recArqButton.addActionListener(this);
	    recArqExtButton.addActionListener(this);
	    renameArqButton.addActionListener(this);
	    deleteArqButton.addActionListener(this);
	    deleteArqExtButton.addActionListener(this);
	    infArqExtButton.addActionListener(this);
	    
	    //Adicionando no painel Opcoes
	    painelOpcoes.add(enviarArqServButton);
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
		//Titulo -Status-
		nomeStatusServidor = new JLabel("Servidor:");
		fonte = new Font( "Verdana", Font.BOLD, 15 );
		nomeStatusServidor.setFont(fonte);
		nomeStatusServidor.setBounds(10, 20, 70, 15);
		painelMsg.add(nomeStatusServidor);
		
		statusServidor = new JLabel(texto);
		fonte = new Font( "Verdana", Font.BOLD, 12 );
		statusServidor.setFont(fonte);
		statusServidor.setForeground(Color.red);
		statusServidor.setBounds(83, 21, 120, texto.length());
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
