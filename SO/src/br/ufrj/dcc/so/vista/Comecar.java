package br.ufrj.dcc.so.vista;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

import com.sun.corba.se.impl.orbutil.graph.NodeData;

import br.ufrj.dcc.so.controle.Cliente;
import br.ufrj.dcc.so.entidade.ApagarArquivo;
import br.ufrj.dcc.so.entidade.ApagarExtensao;
import br.ufrj.dcc.so.entidade.InformacoesArquivo;
import br.ufrj.dcc.so.entidade.LerArquivo;
import br.ufrj.dcc.so.entidade.LerArquivoExtensao;
import br.ufrj.dcc.so.entidade.LerDiretorio;
import br.ufrj.dcc.so.entidade.RenomearArquivo;
import br.ufrj.dcc.so.entidade.Requisicao;
import br.ufrj.dcc.so.entidade.Requisicao.TipoArquivo;

public class Comecar extends JFrame implements ActionListener,TreeSelectionListener{
	
	/**
	 * Classe responsavel por iniciar a execucao do Programa.
	 */
	private static final long serialVersionUID = 1L;
	
	public static final String RAIZSERVIDOR ="filesServer";
	
	public static final String RAIZCLIENTE ="filesClient";
	
	private static final int IP_SERVIDOR =0;
	
	static Thread controla = null;
	
	public static JanelaPrincipal janela = null;
	
	public static PainelPrincipal painelFundo = null;
	
	public static FileTree painelCliente = null;
	
	public static FileTree painelServidor = null;
	
	public static JPanel painelOpcoes = null;
	
	public static JPanel painelMsg = null;

	public static Font fontePrincipal = null;
	
	public static Font fonteSecundaria = null;
	
	public static JLabel statusServidor = new JLabel("");
	
	public static JLabel nomeStatusServidor = new JLabel("");
	
	public static JLabel statusIP = new JLabel("");
	
	public static JLabel nomeStatusIP = new JLabel("");
	
	public static JLabel statusArquivo = new JLabel("");
	
	public static String nomeArquivoSelecionado;
	
	public static String nomeDiretorio;
	
	public static String caminhoArquivoSelecionado;

	private JRadioButton enviarArqServButton = null;
	
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
	
	public Comecar(String teste){
		
	}
	public Comecar() {
		
		JanelaPrincipal.ProgramaLargura = 830;
		
		JanelaPrincipal.ProgramaAltura = 530;

		//BarraDeMenu menu = new BarraDeMenu(this);
		BarraDeMenu menu = new BarraDeMenu();
		
		
		criarPaines();
		colocarOpcoes();

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
		janela.add(BorderLayout.WEST,painelOpcoes);
		janela.add(painelCliente);
		janela.add(painelServidor);
		janela.add(BorderLayout.SOUTH,painelMsg);
		painelCliente.setVisible(false);
		painelServidor.setVisible(false);
		painelOpcoes.setVisible(false);
		janela.setVisible(true);
	
	}
	
	public void actionPerformed(ActionEvent evt) {
		Object source = evt.getSource();

		if (source == enviarArqServButton) {
			
			/*pedir diretorio de destino*/
			
//			File arquivoEntrada=new File(caminhoArquivoSelecionado);
//			
//			//System.out.println("Clicando no enviar Arquivo para o Servidor");
//			desbloqueiaPainelCliente();
//			bloqueiaPainelServidor();
		}	
		
		else if (source == enviarArqExtServButton){
			try {
				getExtensao();	
			} catch (Exception e) {
				System.out.println("Acao Cancelar acionada");
			}
		}	
		
		else if (source == infArqExtButton){
			obterInformacoesArquivo();
			
		}
		
		else if (source == renameArqButton){
			renomeiaArquivo();
			
		}
			
		else if (source == deleteArqButton){
			apagarArquivo();
			
		}
		
		else if (source == deleteArqExtButton){
			apagarArquivoExtensao();
			
		}
		
		else if (source == recArqButton){
			salvaArquivoCliente();
			
		}
		
		else if (source == recArqExtButton){
			recebeVariosArquivos();
		}
	
	}	
	
	//Event do painelServidor
	public void valueChanged(TreeSelectionEvent e) {
	    if (BarraDeMenu.menuConectarServidor.isEnabled()){
	    	JOptionPane.showMessageDialog(null, "Cliente NAO esta conectado com o Servidor!", "ERRO", JOptionPane.ERROR_MESSAGE);
	    }
	    else{
	    	
			JTree treeSource = (JTree) e.getSource();
			TreePath path = treeSource.getSelectionPath();
			if(path != null){
				DefaultMutableTreeNode node = (DefaultMutableTreeNode) e.getPath().getLastPathComponent();
				if(node.isLeaf()){
					caminhoArquivoSelecionado = getCaminhoArquivo(path.toString());
					nomeArquivoSelecionado = (String)((DefaultMutableTreeNode) path.getLastPathComponent()).getUserObject();
				}
				else{
					caminhoArquivoSelecionado = (String)((DefaultMutableTreeNode) path.getLastPathComponent()).getUserObject();
				}
			}
			
			
			
//			System.out.println("1 - " + path);
//	        System.out.println("1.1 - " + getCaminhoArquivo(path.toString()));
//	        System.out.println("2 - " + path.getPath());
//	        //System.out.println(path.getParentPath());
//	        System.out.println("3 - " + ((DefaultMutableTreeNode) path.getLastPathComponent()).getUserObject());
//	        System.out.println("3.1 - " + ((DefaultMutableTreeNode) path.getLastPathComponent()));
//	        System.out.println("4 - " + path.getPathCount());
	      //System.out.println("Diretorio: " + e.getPath() + "  " + no);
		//System.out.println("arquivo: " + no);
//		 nomeArquivoSelecionado=e.getPath().getLastPathComponent().toString();
//	     nomeDiretorio=e.getPath().getParentPath().getLastPathComponent().toString();
//	     caminhoArquivoSelecionado=nomeDiretorio+'/'+nomeArquivoSelecionado;
//	     System.out.println(caminhoArquivoSelecionado);
//	     
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
			painelCliente.addComecarListenerCliente(this);
			
			/*painelCliente.setEnabled(false);
		    bloqueiaPainelCliente();*/

		}
		
		if (painelServidor == null) {
			painelServidor = new FileTree(new File(RAIZCLIENTE));
			painelServidor.addComecarListener(this);
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
	    msgIP("Null");
	    

//		painelCliente.setEnabled(false);
//		bloqueiaPainelCliente();
//		bloqueiaPainelServidor();


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
		statusServidor = new JLabel(texto);
		pintarMsgs();
	}
	
	public static void msgIP(String texto){
		statusIP = new JLabel(texto);
		pintarMsgs();		
	}
	
	public static void pintarMsgs(){
		painelMsg.removeAll();
		fontePrincipal = new Font( "Verdana", Font.BOLD, 15 );
		fonteSecundaria = new Font( "Verdana", Font.BOLD, 12 );
		//Status -Server-
		nomeStatusServidor = new JLabel("Servidor:");
		nomeStatusServidor.setFont(fontePrincipal);
		nomeStatusServidor.setBounds(10, 20, 80, 15);
		painelMsg.add(nomeStatusServidor);
		statusServidor.setFont(fonteSecundaria);
		statusServidor.setForeground(Color.red);
		statusServidor.setBounds(103, 21, 230, 15);
		painelMsg.add(statusServidor);
		
		// -IP-
		nomeStatusIP = new JLabel("IP:");
		nomeStatusIP.setFont(fontePrincipal);
		nomeStatusIP.setBounds(10, 40, 80, 15);
		painelMsg.add(nomeStatusIP);
		statusIP.setFont(fonteSecundaria);
		statusIP.setForeground(Color.red);
		statusIP.setBounds(103, 41, 120, 15);
		painelMsg.add(statusIP);
		
		painelMsg.revalidate();
		painelMsg.repaint();
	}
	
	public static void criaPainelServer(){
//		Crio o objeto lerDiretorio
		LerDiretorio lerDiretorio = new LerDiretorio();
//		Preciso do caminho do diretório passado na tela e setar no objeto lerDiretorio
		lerDiretorio.setCaminho(RAIZSERVIDOR);
		
//		Crio o objeto cliente passando o lerDiretorio como parametro. Passo tambem o endereco do servidor e a porta.
		
		Cliente cliente = new Cliente(BarraDeMenu.nomeServidor, 7000, lerDiretorio);
//		 executo a thread cliente
		cliente.start();
//		espera fim da execucao da thread
		try {
			cliente.join(); 	
		} catch (Exception e) {
			System.out.println("erro criar painel Server");
		}
		
		lerDiretorio = (LerDiretorio)cliente.getRequisicao();

//		 Verifico se ocorreu algum erro de leitura no servidor
		if(lerDiretorio.hasErros()){
		            // Exibo o erro na tela
					System.out.println(lerDiretorio.errosString());
					mensagemDeErro(lerDiretorio.errosString());
		        } 

//		 Se nao ocorreu erro algum entao foi lido os arquivos do diretório com sucesso
		File diretorio = lerDiretorio.getDiretorio();
		
		
		janela.remove(painelServidor);
		janela.validate();
		painelServidor = null;
		painelServidor = new FileTree(diretorio);
		painelServidor.addComecarListener(new Comecar(""));
		painelServidor.setBounds(550, 20, 250, 290);
		painelServidor.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Servidor"));
		painelServidor.revalidate(); 
		janela.add(painelServidor);
		janela.validate();		
	}
	
	public void criaPainelClient(String nomeDiretorio){
		painelCliente.removeAll();
		painelCliente = null;
		painelCliente = new FileTree(new File(nomeDiretorio));
		painelCliente.addComecarListenerCliente(this);
		painelCliente.setBounds(280, 20, 250, 290);
		painelCliente.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Cliente"));
		painelCliente.revalidate(); 
		janela.add(painelCliente);
		janela.validate();
	}
	
	public static void mensagemDeErro(String mensagem){
		JOptionPane.showMessageDialog(null, 
				mensagem, // mensagem
				"Erro", //titulo
				JOptionPane.ERROR_MESSAGE);
	}
	
	private void obterInformacoesArquivo(){
		InformacoesArquivo informArquivo = new InformacoesArquivo();
		informArquivo.setCaminho(caminhoArquivoSelecionado);
		informArquivo.setNomeArquivo(nomeArquivoSelecionado);
		
		Cliente cliente = new Cliente(BarraDeMenu.nomeServidor, 7000, informArquivo);
		cliente.start();
		try {
			cliente.join(); 	
		} catch (Exception e) {
			System.out.println("erro ao ler arquivo");
		}
		
		informArquivo = (InformacoesArquivo)cliente.getRequisicao();

		if(informArquivo.hasErros()){
		            // Exibo o erro na tela
				System.out.println(informArquivo.errosString());
				mensagemDeErro(informArquivo.errosString());
		}else{
			JOptionPane.showMessageDialog(null, 
					"Nome Arquivo: " + informArquivo.getNomeArquivo() + "\n" + //mensagem
					"Tamanho Arquivo: " + informArquivo.getTamanhoArquivo() + " byte(s) \n" +
					"Data do Arquivo: " + informArquivo.getDataModificacao(),
					"Dados do Arquivo", //titulo
					JOptionPane.PLAIN_MESSAGE);	//INFORMATION_MESSAGE	
		}
	}
	
	
	private void renomeiaArquivo(){
		RenomearArquivo renomArquivo = new RenomearArquivo();
		renomArquivo.setCaminho(caminhoArquivoSelecionado);
		renomArquivo.setNomeArquivo(nomeArquivoSelecionado);
		
		try {
			
			String novoArquiv = JOptionPane.showInputDialog("Digite o novo Nome:").toLowerCase() + somenteExtensaoArquivo(nomeArquivoSelecionado);
			
			while (novoArquiv.equals(somenteExtensaoArquivo(nomeArquivoSelecionado))){
				novoArquiv = JOptionPane.showInputDialog("Nome nao preenchido, digite o novo Nome:").toLowerCase() + somenteExtensaoArquivo(nomeArquivoSelecionado);
			}
			
			if (novoArquiv != null){
				renomArquivo.setNomeNovoArquivo(novoArquiv);
				
				
				Cliente cliente = new Cliente(BarraDeMenu.nomeServidor, 7000, renomArquivo);
				cliente.start();
				try {
					cliente.join(); 	
				} catch (Exception e) {
					System.out.println("erro ao ler arquivo");
				}
				
				renomArquivo = (RenomearArquivo)cliente.getRequisicao();

				if(renomArquivo.hasErros()){
				    // Exibo o erro na tela
					System.out.println(renomArquivo.errosString());
					mensagemDeErro(renomArquivo.errosString());
				}else{
					
					sucessoRenomeaArquivo();
					
				}
				
			}
			else{
				JOptionPane.showMessageDialog(null, 
						"Arquivo Nao renomeado !", // mensagem
						"Atencao", //titulo
						JOptionPane.WARNING_MESSAGE);
			}
			
			
		} catch (Exception e) {
			System.out.println("Renomeia Arquivo - Acao Cancelar pressionada !");
		}
	}
	
	private void sucessoRenomeaArquivo(){
		
		//Repinta o Painel Servidor com o novo nome do Arquivo.
		criaPainelServer();
		JOptionPane.showMessageDialog(null, 
				"Arquivo Renomeado com Sucesso !", // mensagem
				"Atencao", //titulo
				JOptionPane.INFORMATION_MESSAGE);
	}
	
	private String somenteExtensaoArquivo(String nomeArquivoSelecionado) {
		
		String[] temp = nomeArquivoSelecionado.split("\\.");
		nomeArquivoSelecionado = "." + temp[temp.length-1];
		
		return nomeArquivoSelecionado;
	}
	
	private void apagarArquivo(){
		ApagarArquivo apagArquivo = new ApagarArquivo();
		apagArquivo.setCaminho(caminhoArquivoSelecionado);
		apagArquivo.setNomeArquivo(nomeArquivoSelecionado);
		
		int opcao = JOptionPane.showConfirmDialog(null,"Deseja Realmente Deletar o Arquivo: " + nomeArquivoSelecionado + " ?","Atencao",JOptionPane.YES_NO_OPTION);    
        if(opcao == JOptionPane.YES_OPTION){ 
		
			Cliente cliente = new Cliente(BarraDeMenu.nomeServidor, 7000, apagArquivo);
			cliente.start();
			try {
				cliente.join(); 	
			} catch (Exception e) {
				System.out.println("erro ao ler arquivo");
			}
			
			apagArquivo = (ApagarArquivo)cliente.getRequisicao();
	
			if(apagArquivo.hasErros()){
			            // Exibo o erro na tela
				System.out.println(apagArquivo.errosString());
					mensagemDeErro(apagArquivo.errosString());
			}else{
				//Repinta o Painel Servidor com o novo nome do Arquivo.
				criaPainelServer();
				JOptionPane.showMessageDialog(null, 
						"Arquivo Deletado com Sucesso !", //mensagem
						"Apagar Arquivo", //titulo
						JOptionPane.INFORMATION_MESSAGE);	
			}
        }
	}
	
	private void apagarArquivoExtensao(){
		try {
			String extensaoArquivo = getExtensao();
			
			
			ApagarExtensao apagArquivoExtensao = new ApagarExtensao();
			apagArquivoExtensao.setCaminho(caminhoArquivoSelecionado);
			apagArquivoExtensao.setNomeExtensao(extensaoArquivo);
			
			int opcao = JOptionPane.showConfirmDialog(null,"Deseja Realmente Deletar todos os Arquivos *." + extensaoArquivo + " do Servidor ?","Atencao",JOptionPane.YES_NO_OPTION);    
	        if(opcao == JOptionPane.YES_OPTION){ 
			
				Cliente cliente = new Cliente(BarraDeMenu.nomeServidor, 7000, apagArquivoExtensao);
				cliente.start();
				try {
					cliente.join(); 	
				} catch (Exception e) {
					System.out.println("erro ao ler arquivo");
				}
				
				apagArquivoExtensao = (ApagarExtensao)cliente.getRequisicao();
		
				if(apagArquivoExtensao.hasErros()){
				            // Exibo o erro na tela
						System.out.println(apagArquivoExtensao.errosString());
						mensagemDeErro(apagArquivoExtensao.errosString());
				}else{
					//Repinta o Painel Servidor com o novo nome do Arquivo.
					criaPainelServer();
					JOptionPane.showMessageDialog(null, 
							"Arquivos *." + extensaoArquivo  + " Deletados com Sucesso !", //mensagem
							"Apagar Arquivos", //titulo
							JOptionPane.INFORMATION_MESSAGE);	
				}
	        }
			
		} catch (Exception e) {
			System.out.println("ApagarArquivoExtensao - Acao Cancelar acionada");
		}
	}
	
	private String getCaminhoArquivo(String caminho){
		String[] temp = caminho.split(",");
		
		caminho = temp[temp.length-2];
		if(temp.length < 3)caminho = caminho.replaceAll("\\[", "");
		
		caminho = caminho.replaceAll(" ", "");
		caminho = caminho.replaceAll("\\\\", "\\\\\\\\");
		return caminho;
	}
	
	public static LerArquivo lerArquivoServidor(Requisicao.TipoArquivo tipoReq){
		LerArquivo lerArquivoServ = new LerArquivo();
		lerArquivoServ.setCaminho(caminhoArquivoSelecionado);
		lerArquivoServ.setNomeArquivo(nomeArquivoSelecionado);
		lerArquivoServ.setTipo(tipoReq);
		
		Cliente cliente = new Cliente(BarraDeMenu.nomeServidor, 7000, lerArquivoServ);
		cliente.start();
		try {
			cliente.join(); 	
		} catch (Exception e) {
			System.out.println("erro ao ler arquivo");
		}
		
		lerArquivoServ = (LerArquivo)cliente.getRequisicao();

		return lerArquivoServ;

	}
	
	public void salvaArquivoCliente(){
		
		int opcao = JOptionPane.showConfirmDialog(null,"Deseja receber o Arquivo: " + nomeArquivoSelecionado + " ?","Atencao",JOptionPane.YES_NO_OPTION);
		if(opcao == JOptionPane.YES_OPTION){
		
			LerArquivo lerArquivoServ = lerArquivoServidor(null);
			
			if(lerArquivoServ.hasErros()){
			    // Exibo o erro na tela
					System.out.println(lerArquivoServ.errosString());
					mensagemDeErro(lerArquivoServ.errosString());
			}else{
				
				try {
					FileInputStream in2 = new FileInputStream(lerArquivoServ.getArquivo());
					FileOutputStream fileOut = new FileOutputStream(RAIZCLIENTE + "\\" + nomeArquivoSelecionado);  
					byte data[] = new byte[1024]; 
					int size;  
					while ((size = in2.read(data)) != -1)  
					{  
					    fileOut.write(data, 0, size);  
					    fileOut.flush();
					}  
					fileOut.close();
					criaPainelClient(RAIZCLIENTE);
					
					System.out.println("Arquivo Recebido com Sucesso !");
					JOptionPane.showMessageDialog(null, 
							"Arquivo Recebido com Sucesso !", // mensagem
							"Atencao", //titulo
							JOptionPane.INFORMATION_MESSAGE);
					
				} catch (Exception e) {
					System.out.println("Erro ao gravar arquivo no Cliente");
					mensagemDeErro("Erro ao gravar arquivo no Cliente");
					
				}
			}
		}
		
		
	}
	public static void abrirArquivo(){
		LerArquivo lerArquivoServ = null;
		
		if (BarraDeMenu.menuConectarServidor.isEnabled()){
	    	JOptionPane.showMessageDialog(null, "Cliente NAO esta conectado com o Servidor!", "ERRO", JOptionPane.ERROR_MESSAGE);
	    }
	    else{
		
			int opcao = JOptionPane.showConfirmDialog(null,"Deseja Alterar o Arquivo: " + nomeArquivoSelecionado + " ?","Atencao",JOptionPane.YES_NO_OPTION);    
	        if(opcao == JOptionPane.YES_OPTION) lerArquivoServ = lerArquivoServidor(Requisicao.TipoArquivo.ESCRITA);
	        else lerArquivoServ = lerArquivoServidor(Requisicao.TipoArquivo.LEITURA);
		
			if(lerArquivoServ.hasErros()){
			            // Exibo o erro na tela
					System.out.println(lerArquivoServ.errosString());
					mensagemDeErro(lerArquivoServ.errosString());
					
			}else{
				
				File novoArquivo = lerArquivoServ.getArquivo();
				String tipo ="";
				if (lerArquivoServ.getTipo() == TipoArquivo.ESCRITA)tipo = "escrita";
				else tipo = "leitura";
				
				new CriaPrograma(novoArquivo,tipo);
			}
	    }	
	}
	
	public void recebeVariosArquivos(){
		String extensaoArquivo = getExtensao();
		
		int opcao = JOptionPane.showConfirmDialog(null,"Deseja receber todos os Arquivos *." + extensaoArquivo + " do Servidor ?","Atencao",JOptionPane.YES_NO_OPTION);    
        if(opcao == JOptionPane.YES_OPTION){ 
			try {
				
				
				LerArquivoExtensao lerArquivo = new LerArquivoExtensao();
				lerArquivo.setTipo(TipoArquivo.ESCRITA);
				lerArquivo.setCaminho(caminhoArquivoSelecionado);
				lerArquivo.setNomeExtensao(extensaoArquivo);
				
				Cliente cliente = new Cliente(BarraDeMenu.nomeServidor, 7000, lerArquivo);
				cliente.start();
				try {
					cliente.join(); 	
				} catch (Exception e) {
					System.out.println("erro ao ler arquivo");
				}
				
				lerArquivo = (LerArquivoExtensao)cliente.getRequisicao();
	
				if(lerArquivo.hasErros()){
					System.out.println(lerArquivo.errosString());
					mensagemDeErro(lerArquivo.errosString());
				}else
					exibirLeituraArquivoExtensao(lerArquivo);
			} catch (Exception e) {
				System.out.println("ApagarArquivoExtensao - Acao Cancelar acionada");
			}
        }
	}	
	public void exibirLeituraArquivoExtensao(LerArquivoExtensao lerArquivo){
		try {
			for (File arquivo : lerArquivo.getArquivos()) {
				if(arquivo != null){    			    
				   if(arquivo.isFile()){ 
					   
					   	FileInputStream in2 = new FileInputStream(arquivo);
						FileOutputStream fileOut = new FileOutputStream(RAIZCLIENTE + "\\" + arquivo.getName());  
						byte data[] = new byte[1024]; 
						int size;  
						while ((size = in2.read(data)) != -1)  
						{  
						    fileOut.write(data, 0, size);  
						    fileOut.flush();
						}  
						fileOut.close();
						criaPainelClient(RAIZCLIENTE);
						
						System.out.println("Arquivo "+ arquivo.getName() +" Recebido com Sucesso !");
					   //System.out.println(arquivo.getName());
				   }		        
				}
			}
			System.out.println("Arquivos Recebidos com Sucesso !");
			JOptionPane.showMessageDialog(null, 
					"Arquivos Recebidos com Sucesso !", // mensagem
					"Atencao", //titulo
					JOptionPane.INFORMATION_MESSAGE);
		} catch (Exception e) {
			System.out.println("Erro ao ler arquivos com Extensao");
		}
	}
}
