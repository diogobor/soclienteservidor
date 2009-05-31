package br.ufrj.dcc.so.vista;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

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
		
		desenvolvedores.setBounds(10, 30, 500, 20);
		painel.add(desenvolvedores);
		
		professor.setBounds(160, 50, 500, 20);
		painel.add(professor);
		
		//Mostra a Janela
		add(painel);
		setSize(tamanhoPrograma);
		setResizable(false);
		setLocation((screenSize.width - JanelaLargura) / 2,(screenSize.height - JanelaAltura) / 2);
		setTitle("Sobre");
		setVisible(true);
		
	}
	

	public void windowActivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	public void windowClosed(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	public void windowClosing(WindowEvent e) {
		// TODO Auto-generated method stub
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
	}

	public void windowDeactivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	public void windowDeiconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	public void windowIconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	public void windowOpened(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}
}
