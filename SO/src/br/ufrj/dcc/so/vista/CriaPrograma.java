package br.ufrj.dcc.so.vista;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import br.ufrj.dcc.so.controle.LerEscreverArquivo;


public class CriaPrograma extends JFrame implements WindowListener, ActionListener{

	/**
	 * Janela do Cria Programa
	 */
	
	private static final long serialVersionUID = 1L;
	
	public static JTextArea areaTexto;
	
	private final int ProgramaLargura = 450;

	private final int ProgramaAltura = 450;
	
	private JLabel titulo;

	private JPanel painel;
	
	private JScrollPane scrollTextArea;
	
	public static JButton botaoCarrega;
	
	@SuppressWarnings("unused")
	private LerEscreverArquivo arquivoLido;

	public CriaPrograma (){
		
		/**
		 * Variavel para saber o tamanho da janela
		 */
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

		Dimension tamanhoPrograma = new Dimension(ProgramaLargura, ProgramaAltura);
		
		titulo = new JLabel("Digite seu programa na area abaixo:");
		
		areaTexto = new JTextArea("Digite aqui...");
		
		painel = new JPanel();
		
		scrollTextArea = new JScrollPane(areaTexto);
		
		botaoCarrega = new JButton("Carregar Programa");
		
		painel.setLayout(null);
		
		/**
		 * Adiciona item no painel
		 */
		titulo.setBounds(110, 10, 500, 20);
		painel.add(titulo);
		
		areaTexto.setLineWrap(true);
		scrollTextArea.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollTextArea.setBounds(48,40,350,300);
		painel.add(scrollTextArea);
		
		botaoCarrega.setBounds(150, 360, 160, 20);
		botaoCarrega.addActionListener(this);
		painel.add(botaoCarrega);
		
		/**
		 * Mostra a Janela
		 */
		add(painel);
		setSize(tamanhoPrograma);
		setResizable(false);
		setLocation((screenSize.width - ProgramaLargura) / 2,(screenSize.height - ProgramaAltura) / 2);
		setTitle("Cria Programa");
		setVisible(true);
		
	}
	
	public void actionPerformed(ActionEvent evt) {
		
		Object source = evt.getSource();

		if (source == botaoCarrega) {
			System.out.println(areaTexto.getText());
			try{
				if(areaTexto.getText().equals("Digite aqui...") || areaTexto.getText().equals("")){
					JOptionPane.showMessageDialog(null, "Falha ao carregar este programa !\nDigite um programa valido ou feche a janela!", "ERRO", JOptionPane.INFORMATION_MESSAGE);
				}
				else{
					arquivoLido = new LerEscreverArquivo(areaTexto.getText(),areaTexto.getText());
					
//					Comecar.cliqueMenuComecar = false;
//					Comecar.proximoPasso.setEnabled(true);
//					Comecar.zeraMemoriaEPrograma();
					setVisible(false);
				}
			}
			catch(Exception e){
				System.out.println("Erro na gravacao do Arquivo - Clique do botao.");
			}		
		}
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