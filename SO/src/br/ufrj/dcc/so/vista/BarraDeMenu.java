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

public class BarraDeMenu implements ActionListener {

	/**
	 * Classe responsavel por fazer a Barra de Menu na Janela Principal
	 */
	
	private static final long serialVersionUID = 1L;

	private JMenuBar barraMenu = new JMenuBar();
	
	public static CriaPrograma clicaNovoPrograma = null;
	
	public static JFileChooser escolhePrograma = null;
	
	private Cliente inicia = null;

	// MENU PROGRAMA

	private JMenu menuPrograma = new JMenu();
	
	public static JMenuItem menuConectarServidor = new JMenuItem();

	private JMenuItem menuNovoPrograma = new JMenuItem();
	
	private JMenuItem menuFazerPrograma = new JMenuItem();
	
	private JMenuItem menuSair = new JMenuItem();

	//MENU EXECUTAR
	
	private JMenu menuExecutar = new JMenu();
	
	private JMenuItem menuExecutaPrograma = new JRadioButtonMenuItem();
	
	private JMenuItem menuExecutaInstrucao = new JRadioButtonMenuItem();
	
	private JMenuItem menuExecutaMicroInstrucao = new JRadioButtonMenuItem();

	// MENU AJUDA

	private JMenu menuAjuda = new JMenu();

	private JMenuItem menuInstrucoes = new JMenuItem();

	private JMenuItem menuSobre = new JMenuItem();
	
	public static String nomeServidor = "";


	/**
	 * Executa as funcoes para cada parte do Menu.
	 */
	public void actionPerformed(ActionEvent evt) {
		Object source = evt.getSource();
		
		if (source == menuConectarServidor) {
			try{
				nomeServidor = JOptionPane.showInputDialog("Digite o endereco do Servidor:", "localhost").toLowerCase();
				if (nomeServidor == null){
					nomeServidor = "localhost";
				}
				//System.out.println(nomeServidor);
				ConectarServidor conServidor = new ConectarServidor();
//				inicia = new Cliente(nomeServidor, 7000, conServidor);
//				inicia.start();
				MainConsole.conectarServidor();
				if(conServidor.hasErros())
				{				
					PainelPrincipal.situacaoServidor = conServidor.errosString();
	            	Comecar.painelFundo.repaint();	            	
	            	BarraDeMenu.menuConectarServidor.setEnabled(true);
	            }
				else{
					//escrever conectado na tela
				}
				
			}
			catch(Exception e){
				System.out.println("Acao Cancelar acionada !");
			}
		}
		else if (source == menuNovoPrograma) {
			
			escolhePrograma = new JFileChooser();  
			escolhePrograma.setFileFilter(new ExtensionFileFilter("Arquivos texto", "txt"));  
			if (escolhePrograma.showOpenDialog(escolhePrograma) != JFileChooser.APPROVE_OPTION)   
			   return;  
			
			System.out.println("Arquivo selecionado: " + escolhePrograma.getSelectedFile().toString());
			
		}
		else if (source == menuFazerPrograma) {
			clicaNovoPrograma = new CriaPrograma();
		}
		else if (source == menuSair) {
			System.exit(0);
		}
		else if (source == menuSobre) {
			new Sobre();
		}
		else if (source == menuExecutaPrograma) {
			
			menuExecutaPrograma.setSelected(true);
			menuExecutaInstrucao.setSelected(false);
			menuExecutaMicroInstrucao.setSelected(false);
		}
		else if (source == menuExecutaInstrucao) {
			
			
			menuExecutaPrograma.setSelected(false);
			menuExecutaInstrucao.setSelected(true);
			menuExecutaMicroInstrucao.setSelected(false);
//			Comecar.modoOperacao.setText("Executar Instrucao");
			System.out.println("Executa Instrucao do Programa.");
		}
		else if (source == menuExecutaMicroInstrucao) {
			
			menuExecutaPrograma.setSelected(false);
			menuExecutaInstrucao.setSelected(false);
			menuExecutaMicroInstrucao.setSelected(true);
//			Comecar.modoOperacao.setText("Executar MicroInstrucao");
			System.out.println("Executa MicroInstrucao do Programa.");
		}
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
		menuPrograma.add(getSubMenuCarregarPrograma());
		menuPrograma.add(getSubMenuNovoPrograma());
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
	 * Cria o submenu Carregar Programa.
	 */
	private JMenuItem getSubMenuCarregarPrograma() {
		menuNovoPrograma.setMnemonic('r');
		menuNovoPrograma.setText("Carregar Programa");
		menuNovoPrograma.addActionListener(this);

		return menuNovoPrograma;
	}
	
	/**
	 * Cria o submenu Criar Programa.
	 */
	private JMenuItem getSubMenuNovoPrograma() {
		menuFazerPrograma.setMnemonic('P');
		menuFazerPrograma.setText("Criar Programa");
		menuFazerPrograma.addActionListener(this);

		return menuFazerPrograma;
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
	 * Cria o menu Executar na barra de menu.
	 */
	private JMenu getMenuExecutar() {
		menuExecutar.setMnemonic('E');
		menuExecutar.setText("Executar");
		menuExecutar.add(getSubMenuExecutaPrograma());
		menuExecutar.add(getSubMenuExecutaInstrucao());
		menuExecutar.add(getSubMenuExecutaMicroInstrucao());
		
		return menuExecutar;
	}
	
	/**
	 * Cria o submenu Executa Programa.
	 */
	private JMenuItem getSubMenuExecutaPrograma() {
		menuExecutaPrograma.setMnemonic('P');
		menuExecutaPrograma.setText("Executar Programa");
		menuExecutaPrograma.setSelected(true);
		menuExecutaPrograma.addActionListener(this);

		return menuExecutaPrograma;
	}
	
	/**
	 * Cria o submenu Executa Programa Por Instrucao.
	 */
	private JMenuItem getSubMenuExecutaInstrucao() {
		menuExecutaInstrucao.setMnemonic('I');
		menuExecutaInstrucao.setText("Executar Instrucao");
		menuExecutaInstrucao.addActionListener(this);

		return menuExecutaInstrucao;
	}
	
	/**
	 * Cria o submenu Executa Programa Por MicroInstrucao.
	 */
	private JMenuItem getSubMenuExecutaMicroInstrucao() {
		menuExecutaMicroInstrucao.setMnemonic('M');
		menuExecutaMicroInstrucao.setText("Executar MicroInstrucao");
		menuExecutaMicroInstrucao.addActionListener(this);

		return menuExecutaMicroInstrucao;
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
