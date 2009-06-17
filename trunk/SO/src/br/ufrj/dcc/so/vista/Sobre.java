package br.ufrj.dcc.so.vista;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import br.ufrj.dcc.so.entidade.DesconectarServidor;

public class Sobre extends JFrame implements WindowListener{
	/**
	 * Janela das Sobre
	 */
	
	private static final long serialVersionUID = 1L;
	
	private final int JanelaLargura = 500;

	private final int JanelaAltura = 130;
	
	private JLabel titulo, desenvolvedores,professor;

	private JPanel painel;
	

	public Sobre (){
		
//		 Variavel para saber o tamanho da janela
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

		Dimension tamanhoPrograma = new Dimension(JanelaLargura, JanelaAltura);
		
		titulo = new JLabel("Trabalho de Sistemas Operacionais I");
		
		desenvolvedores = new JLabel("Autores: Alan Costa, Diogo Borges, Pedro Alberto, Rafael Lopes e Renan Sardinha");
		
		professor = new JLabel("Professor: Thome - Periodo: 2009/1");
		
		painel = new JPanel();
		
		
		painel.setLayout(null);
		
		//Adiciona Titulo no painel
		titulo.setBounds(155, 10, 500, 20);
		painel.add(titulo);
	    int style = Font.BOLD;  
	    int size = 11;  
	    Font fonte = new java.awt.Font("Arial", style, size);  
		
		desenvolvedores.setBounds(10, 30, 500, 20);
		desenvolvedores.setFont(fonte);
		painel.add(desenvolvedores);
		
		professor.setBounds(160, 50, 500, 20);
		painel.add(professor);
		
		//Mostra a Janela
		add(painel);
		setSize(tamanhoPrograma);
		setResizable(false);
		addWindowListener(this);
		setLocation((screenSize.width - JanelaLargura) / 2,(screenSize.height - JanelaAltura) / 2);
		setTitle("Sobre");
		setVisible(true);
		
	}
	

	public void windowClosing(WindowEvent e) {
		BarraDeMenu.menuSobre.setEnabled(true);
	}
	public void windowOpened(WindowEvent e) {
		BarraDeMenu.menuSobre.setEnabled(false);
	}
	public void windowClosed(WindowEvent e) {}
	public void windowDeactivated(WindowEvent e) {}
	public void windowDeiconified(WindowEvent e) {}
	public void windowIconified(WindowEvent e) {}
	public void windowActivated(WindowEvent e){}
}
