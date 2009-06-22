package br.ufrj.dcc.so.vista;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JRadioButtonMenuItem;

import br.ufrj.dcc.so.controle.Cliente;
import br.ufrj.dcc.so.entidade.ConectarServidor;
import br.ufrj.dcc.so.entidade.DesconectarServidor;
import br.ufrj.dcc.so.entidade.Requisicao;

public class BarraDeMenu implements ActionListener {

	/**
	 * Classe responsavel por fazer a Barra de Menu na Janela Principal
	 */
	
	private static final long serialVersionUID = 1L;

	private JMenuBar barraMenu = new JMenuBar();
	
	public static CriaPrograma clicaNovoPrograma = null;

	// MENU PROGRAMA

	private JMenu menuPrograma = new JMenu();
	
	public static JMenuItem menuConectarServidor = new JMenuItem();
	
	private JMenuItem menuSair = new JMenuItem();

	// MENU AJUDA

	private JMenu menuAjuda = new JMenu();

	private JMenuItem menuInstrucoes = new JMenuItem();

	public static JMenuItem menuSobre = new JMenuItem();
	
	public static String nomeServidor = "";
	
	private Comecar comecar = null;

	public BarraDeMenu(Comecar comecar){
		this.comecar=comecar;
	}
	
	public BarraDeMenu(){
		
	}
	
	/**
	 * Executa as funcoes para cada parte do Menu.
	 */
	public synchronized void actionPerformed(ActionEvent evt) {
		Object source = evt.getSource();
		
		if (source == menuConectarServidor) {
			conectarServidor();
			
		}

		else if (source == menuSair) {
			if(!menuConectarServidor.isEnabled()){
				DesconectarServidor desconectar = new DesconectarServidor();
				try {
					desconectar = (DesconectarServidor)executarRequisicao(desconectar, BarraDeMenu.nomeServidor);
				} catch (Exception e) {
					System.out.println("Erro ao desconectar o Cliente!\nCliente nao desconectado.");
				}
				
				
				if(desconectar.hasErros()){
					System.out.println(desconectar.errosString());
					return;			
				}		
				System.out.println("Cliente desconectado");
			}
			System.exit(0);
		}
		else if (source == menuSobre) {
			new Sobre();
		}
	}

	private void conectarServidor() {
		
		menuConectarServidor.setEnabled(false);
		
		try{
			nomeServidor = JOptionPane.showInputDialog("Digite o endereco do Servidor:", "localhost").toLowerCase();
			if (nomeServidor == null){
				nomeServidor = "localhost";
			}

			ConectarServidor conServidor = new ConectarServidor();
			conServidor = (ConectarServidor)executarRequisicao(conServidor, nomeServidor);
			
			if(conServidor.hasErros())
				errosServidor(conServidor);
			else
				sucessoConexao(conServidor);
							
		}
		catch(Exception e){
			menuConectarServidor.setEnabled(true);
			System.out.println("Erro ao conectar o servidor.");
		}
	}
	
	public static Requisicao executarRequisicao(Requisicao req, String enderecoServidor) throws InterruptedException {
		
		Cliente cliente = new Cliente(enderecoServidor, 7000, req);
		cliente.start();
		//espera fim da execucao da thread
		cliente.join();		
		
		return cliente.getRequisicao();
	}
	
	public void sucessoConexao(Requisicao requisicao){
		String ip = "";
		ip = requisicao.getCliente();
		Comecar.msgNoServidor("Conectado !");
		Comecar.msgIP(ip);
		//Criar a Jtree do Servidor
		//Comecar painel = new Comecar("");
		//painel.criaPainelServer();
		Comecar.criaPainelServer();
		
		Comecar.painelCliente.setVisible(true);
		Comecar.painelServidor.setVisible(true);
		Comecar.painelOpcoes.setVisible(true);
		Comecar.janela.validate();
	}
	
	public void errosServidor(Requisicao requisicao){
		menuConectarServidor.setEnabled(true);
		System.out.println(requisicao.errosString());
		Comecar.msgNoServidor("Erro na conexao com o Servidor.");
		Comecar.msgIP("Null");
	}

	/**
	 * Cria a Barra de Menu
	 */
	protected JMenuBar getBarraMenu() {
		barraMenu.add(getMenuPrograma());
		//barraMenu.add(getMenuExecutar());
		barraMenu.add(getMenuAjuda());

		return barraMenu;
	}

	/**
	 * Cria o menu Arquivo na barra de menu.
	 */
	private JMenu getMenuPrograma() {
		menuPrograma.setMnemonic('A');
		menuPrograma.setText("Arquivo");
		menuPrograma.add(getSubMenuConectarServidor());
		menuPrograma.addSeparator();
		menuPrograma.add(getSubMenuSair());

		return menuPrograma;
	}
	
	/**
	 * Cria o submenu Conectar com o Servidor.
	 */
	private JMenuItem getSubMenuConectarServidor() {
		menuConectarServidor.setMnemonic('C');
		menuConectarServidor.setText("Conectar Servidor");
		menuConectarServidor.addActionListener(this);

		return menuConectarServidor;
	}
	
	/**
	 * Cria o submenu Sair.
	 */
	private JMenuItem getSubMenuSair() {
		menuSair.setMnemonic('S');
		menuSair.setText("Sair");
		menuSair.addActionListener(this);

		return menuSair;
	}
	

	/**
	 * Cria o menu Ajuda na barra de menu.
	 */
	private JMenu getMenuAjuda() {
		menuAjuda.setMnemonic('j');
		menuAjuda.setText("Ajuda");
		menuAjuda.add(getSubMenuInstrucoes());
		menuAjuda.addSeparator();
		menuAjuda.add(getSubMenuSobre());

		return menuAjuda;
	}

	/**
	 * Cria o submenu Instrucoes.
	 */
	private JMenuItem getSubMenuInstrucoes() {
		menuInstrucoes.setMnemonic('I');
		menuInstrucoes.setText("Instrucoes");
		menuInstrucoes.addActionListener(this);

		return menuInstrucoes;
	}

	/**
	 * Cria o submenu Sobre.
	 */
	private JMenuItem getSubMenuSobre() {
		menuSobre.setMnemonic('S');
		menuSobre.setText("Sobre");
		menuSobre.addActionListener(this);

		return menuSobre;
	}
}
