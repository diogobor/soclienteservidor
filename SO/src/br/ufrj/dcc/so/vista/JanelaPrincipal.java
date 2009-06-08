package br.ufrj.dcc.so.vista;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JFrame;

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
		setLocation((screenSize.width - ProgramaLargura) / 2,
				(screenSize.height - ProgramaAltura) / 2);
	}

	public void windowClosed(WindowEvent e) {
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
	}

	public void windowClosing(WindowEvent e) {
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
	}

	public void windowDeactivated(WindowEvent e) {}
	public void windowDeiconified(WindowEvent e) {}
	public void windowIconified(WindowEvent e) {}
	public void windowOpened(WindowEvent e) {}
	public void windowActivated(WindowEvent e){}
}
