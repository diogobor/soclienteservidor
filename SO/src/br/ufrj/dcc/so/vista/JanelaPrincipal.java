package br.ufrj.dcc.so.vista;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JFrame;

import br.ufrj.dcc.so.entidade.DesconectarServidor;

/**
 * Classe da Janela Principal do Programa
 */
public class JanelaPrincipal extends JFrame implements WindowListener {
	private static final long serialVersionUID = 1L;

	public static int ProgramaLargura;

	public static int ProgramaAltura;
	
	Figura icone = new Figura();

	// Variavel para saber o tamanho da janela
	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

	Dimension tamanhoPrograma = new Dimension(ProgramaLargura, ProgramaAltura);

	/**
	 * Construtor da Classe. Nela serao definidas todos os parametros que a janela
	 * do programa tera.
	 * 
	 */
	public JanelaPrincipal() {

		setTitle("Trabalho de Sistemas Operacionais I");
		setSize(tamanhoPrograma);
		setResizable(false);
		addWindowListener(this);
		setLocation((screenSize.width - ProgramaLargura) / 2,
				(screenSize.height - ProgramaAltura) / 2);
	}

	

	public void windowClosing(WindowEvent e) {
		if(!BarraDeMenu.menuConectarServidor.isEnabled()){
			DesconectarServidor desconectar = new DesconectarServidor();
			try {
				desconectar = (DesconectarServidor)BarraDeMenu.executarRequisicao(desconectar, BarraDeMenu.nomeServidor);
			} catch (Exception evento) {
				System.out.println("Erro ao desconectar o Cliente!\nCliente nao desconectado.");
			}
			
			
			if(desconectar.hasErros()){
				System.out.println(desconectar.errosString());
				return;			
			}		
			System.out.println("Cliente desconectado");
		}
	}
	public void windowClosed(WindowEvent e) {}
	public void windowDeactivated(WindowEvent e) {}
	public void windowDeiconified(WindowEvent e) {}
	public void windowIconified(WindowEvent e) {}
	public void windowOpened(WindowEvent e) {}
	public void windowActivated(WindowEvent e){}
}
