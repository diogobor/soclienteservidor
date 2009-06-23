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
import java.util.ArrayList;
import java.util.List;

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

import com.sun.org.apache.bcel.internal.generic.GETSTATIC;

import br.ufrj.dcc.so.controle.Cliente;
import br.ufrj.dcc.so.entidade.ApagarArquivo;
import br.ufrj.dcc.so.entidade.ApagarExtensao;
import br.ufrj.dcc.so.entidade.InformacoesArquivo;
import br.ufrj.dcc.so.entidade.LerArquivo;
import br.ufrj.dcc.so.entidade.LerArquivoExtensao;
import br.ufrj.dcc.so.entidade.LerDiretorio;
import br.ufrj.dcc.so.entidade.RenomearArquivo;
import br.ufrj.dcc.so.entidade.Requisicao;
import br.ufrj.dcc.so.entidade.SalvarArquivo;
import br.ufrj.dcc.so.entidade.SalvarArquivoExtensao;
import br.ufrj.dcc.so.entidade.Requisicao.TipoArquivo;

public class Comecar extends JFrame implements ActionListener,TreeSelectionListener{
	
	/**
	 * Classe responsavel por iniciar a execucao do Programa.
	 */
	private static final long serialVersionUID = 1L;
	
	public static final String RAIZSERVIDOR ="./filesServer";
	
	public static final String RAIZCLIENTE ="./filesClient";
	
	public static final String BARRA ="\\\\";
	
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
	
	public static String nomeArquivoSelecionado = "";
	
	public static String nomeArquivoSelecionadoCliente = "";
	
	public static String caminhoArquivoSelecionadoCliente = "";
	
	public static String caminhoArquivoSelecionado = "";

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
			enviarUmArquivo();
			
		}	
		
		else if (source == enviarArqExtServButton){
			enviarArquivoExtensao();
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
		try {
			String extensao = JOptionPane.showInputDialog("Digite a extensao:").toLowerCase();
			if (extensao == null){
				extensao = "";
			}
			return extensao;
		} catch (Exception e) {
			System.out.println("Extensao - Acao Cancelar pressionada !");
			return "";
		}
		
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
	
	public static void limparArquivos(File temp){
		
		if(temp.isFile()){
			temp.delete();
		}else if (temp.isDirectory()){
			String[] filhos = temp.list();
			for (int i = 0; i < filhos.length; i++) {
				limparArquivos(new File(temp,filhos[i]));
			}
		}

	}
	
	public static void criaPainelServer(){
		
		try {
			limparArquivos(new File(RAIZSERVIDOR));
			
			LerDiretorio lerDiretorio = new LerDiretorio();
			lerDiretorio.setCaminho(RAIZSERVIDOR);
			
			Cliente cliente = new Cliente(BarraDeMenu.nomeServidor, 7000, lerDiretorio);
			cliente.start();
			try {
				cliente.join(); 	
			} catch (Exception e) {
				System.out.println("erro ao ler arquivo");
			}
			
			lerDiretorio = (LerDiretorio)cliente.getRequisicao();

			if(lerDiretorio.hasErros()){
				System.out.println(lerDiretorio.errosString());
				mensagemDeErro(lerDiretorio.errosString());
			}else{}
			
		
			List<String> teste = lerDiretorio.getListaNomeArquivos();
			
			for (String string : teste) {
				
				File arquivoTemp = new File(dirStringArquivo(string, lerDiretorio.getSistemaOperacional()),nomeStringArquivo(string,lerDiretorio.getSistemaOperacional()));
				arquivoTemp.createNewFile();
			}	
		
			janela.remove(painelServidor);
			janela.validate();
			painelServidor = null;
			painelServidor = new FileTree(new File(RAIZSERVIDOR));
			
			painelServidor.addComecarListener(new Comecar(""));
			painelServidor.setBounds(550, 20, 250, 290);
			painelServidor.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Servidor"));
			painelServidor.revalidate(); 
			janela.add(painelServidor);
			janela.validate();		
		
		} catch (Exception e) {
			System.out.println("ApagarArquivoExtensao - Acao Cancelar acionada");
		}
	}
	
	public static String nomeStringArquivo(String nomeCompleto, String sistemaOperacional){
		
		String[] temp = nomeCompleto.split(BARRA);
		return nomeCompleto = temp[temp.length-1];
	}
	//      filesServer/trabalho.txt -> linux
	//		filesServer\\trabalho.txt -> Windows
	
	public static String barraSO(String sistemaOperacional){
		if (sistemaOperacional.equals("Linux")) return "/";
		return "\\";
	}
	
	public static String dirStringArquivo(String nomeCompleto, String sistemaOperacional){

		String[] temp = nomeCompleto.split(BARRA);
		nomeCompleto = temp[0];
		for(int i =1; i < temp.length-1; i++){
			nomeCompleto += BARRA + temp[i];
		}
		return nomeCompleto;
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
		if(nomeArquivoSelecionado.equals("")){
			JOptionPane.showMessageDialog(null, "Selecione um Arquivo no Servidor!", "ERRO", JOptionPane.ERROR_MESSAGE);
			
		}else{
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
	}
	
	
	private void renomeiaArquivo(){
		if(nomeArquivoSelecionado.equals("")){
			JOptionPane.showMessageDialog(null, "Selecione um Arquivo no Servidor!", "ERRO", JOptionPane.ERROR_MESSAGE);
			
		}else{
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
		if(nomeArquivoSelecionado.equals("")){
			JOptionPane.showMessageDialog(null, "Selecione um Arquivo no Servidor!", "ERRO", JOptionPane.ERROR_MESSAGE);
			
		}else{
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
	}
	
	private void apagarArquivoExtensao(){
		if(caminhoArquivoSelecionado.equals("")){
			JOptionPane.showMessageDialog(null, "Selecione um Diretorio no Servidor!", "ERRO", JOptionPane.ERROR_MESSAGE);
			
		}else{
			try {
				String extensaoArquivo = getExtensao();
				
				if (!extensaoArquivo.equals("")){
				
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
				}
				
			} catch (Exception e) {
				System.out.println("ApagarArquivoExtensao - Acao Cancelar acionada");
			}
		}
	}
	
	public static String getCaminhoArquivo(String caminho){
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
	
	private void salvaArquivoCliente(){
		
		if(nomeArquivoSelecionado.equals("")){
			JOptionPane.showMessageDialog(null, "Selecione um arquivo no Servidor!", "ERRO", JOptionPane.ERROR_MESSAGE);
			
		}
		else if(caminhoArquivoSelecionadoCliente.equals("")){
			JOptionPane.showMessageDialog(null, "Selecione um diretorio no Cliente!", "ERRO", JOptionPane.ERROR_MESSAGE);
		}
		else{
		
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
						FileOutputStream fileOut = new FileOutputStream(caminhoArquivoSelecionadoCliente + "/" + nomeArquivoSelecionado);  
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
	
	private void recebeVariosArquivos(){
		
		if(caminhoArquivoSelecionado.equals("")){
			JOptionPane.showMessageDialog(null, "Selecione um diretorio no Servidor!", "ERRO", JOptionPane.ERROR_MESSAGE);
			
		}
		else if(caminhoArquivoSelecionadoCliente.equals("")){
			JOptionPane.showMessageDialog(null, "Selecione um diretorio no Cliente!", "ERRO", JOptionPane.ERROR_MESSAGE);
		}
		else{
		
			String extensaoArquivo = getExtensao();
			if (!extensaoArquivo.equals("")){
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
		}
	}	
	private void exibirLeituraArquivoExtensao(LerArquivoExtensao lerArquivo){
		try {
			for (File arquivo : lerArquivo.getArquivos()) {
				if(arquivo != null){    			    
				   if(arquivo.isFile()){ 
					   
					   	FileInputStream in2 = new FileInputStream(arquivo);
						FileOutputStream fileOut = new FileOutputStream(caminhoArquivoSelecionadoCliente + "\\" + arquivo.getName());  
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
	
	private void enviarUmArquivo(){
		if(nomeArquivoSelecionadoCliente.equals("")){
			JOptionPane.showMessageDialog(null, "Selecione um Arquivo no Cliente!", "ERRO", JOptionPane.ERROR_MESSAGE);
			
		}
		else if(caminhoArquivoSelecionado.equals("")){
			JOptionPane.showMessageDialog(null, "Selecione um diretorio no Servidor!", "ERRO", JOptionPane.ERROR_MESSAGE);
		}
		else{
			SalvarArquivo salvarUmArquivo = new SalvarArquivo();
			salvarUmArquivo.setArquivo(new File(caminhoArquivoSelecionadoCliente + "/" + nomeArquivoSelecionadoCliente));
			salvarUmArquivo.setCaminho(caminhoArquivoSelecionado);
			salvarUmArquivo.setNomeArquivo(nomeArquivoSelecionadoCliente);
			
			Cliente cliente = new Cliente(BarraDeMenu.nomeServidor, 7000, salvarUmArquivo);
			cliente.start();
			try {
				cliente.join(); 	
			} catch (Exception e) {
				System.out.println("erro ao ler arquivo");
			}
			
			salvarUmArquivo = (SalvarArquivo)cliente.getRequisicao();
			if(salvarUmArquivo.hasErros()){
			    // Exibo o erro na tela
				System.out.println(salvarUmArquivo.errosString());
				mensagemDeErro(salvarUmArquivo.errosString());
			}else{
				criaPainelServer();
				System.out.println("Arquivo enviado com sucesso !");
				JOptionPane.showMessageDialog(null, 
						"Arquivo Enviado com Sucesso !", // mensagem
						"Atencao", //titulo
						JOptionPane.INFORMATION_MESSAGE);
			}
		}
	}
	
	private void enviarArquivoExtensao(){
		if(caminhoArquivoSelecionadoCliente.equals("")){
			JOptionPane.showMessageDialog(null, "Selecione um Diretorio no Cliente!", "ERRO", JOptionPane.ERROR_MESSAGE);
			
		}
		else if(caminhoArquivoSelecionado.equals("")){
			JOptionPane.showMessageDialog(null, "Selecione um Diretorio no Servidor!", "ERRO", JOptionPane.ERROR_MESSAGE);
		}
		else{
			String extensaoArquivo = getExtensao();	
			if (!extensaoArquivo.equals("")){
				
				List<File> arquivos = obterListaFiles(extensaoArquivo);
				
				SalvarArquivoExtensao salvarArquivos = new SalvarArquivoExtensao();
				salvarArquivos.setArquivos(arquivos);
				salvarArquivos.setCaminho(caminhoArquivoSelecionado);
				
				Cliente cliente = new Cliente(BarraDeMenu.nomeServidor, 7000, salvarArquivos);
				cliente.start();
				try {
					cliente.join(); 	
				} catch (Exception e) {
					System.out.println("erro ao ler arquivo");
				}
				
				salvarArquivos = (SalvarArquivoExtensao)cliente.getRequisicao();
				if(salvarArquivos.hasErros()){
				    // Exibo o erro na tela
					System.out.println(salvarArquivos.errosString());
					mensagemDeErro(salvarArquivos.errosString());
				}else{
					criaPainelServer();
					System.out.println("Arquivo enviado com sucesso !");
					JOptionPane.showMessageDialog(null, 
							"Arquivos Enviados com Sucesso !", // mensagem
							"Atencao", //titulo
							JOptionPane.INFORMATION_MESSAGE);
				}
			}
		}
	}
	
	private List<File> obterListaFiles(String nomeExtensao) 
	{
		List<File> arquivos = new ArrayList<File>();
		File diretorio = new File(caminhoArquivoSelecionadoCliente);
		
		if(!diretorio.exists() || !diretorio.isDirectory()) return arquivos;
		
		File[] arquivoDiretorio = diretorio.listFiles();
		
		for(int i = 0; i < arquivoDiretorio.length; ++i){ 
		    File f = arquivoDiretorio[i]; 
		    String[] texto = f.getName().split("\\.");
		    String extensaoArquivo = texto[texto.length-1].toLowerCase();
		    
		    if(extensaoArquivo.equals(nomeExtensao.toLowerCase())){
		    	arquivos.add(f);                    
		    } 
		}
		return arquivos;
	}
}
