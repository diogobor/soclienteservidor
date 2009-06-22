package br.ufrj.dcc.so.vista;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import br.ufrj.dcc.so.controle.ArquivoTexto;
import br.ufrj.dcc.so.controle.Cliente;
import br.ufrj.dcc.so.controle.LerEscreverArquivo;
import br.ufrj.dcc.so.entidade.LerArquivo;
import br.ufrj.dcc.so.entidade.SalvarArquivo;


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
	
	private File arquivoAntigo = null;
	
	public static JButton botaoCarrega;
	
	@SuppressWarnings("unused")
	private LerEscreverArquivo arquivoLido;
	

	public CriaPrograma (File arquivoAntigo, String tipoArquivo){
		
		/**
		 * Adiciona o arquivo na variavel da classe
		 */
		this.arquivoAntigo = arquivoAntigo;
		/**
		 * Variavel para saber o tamanho da janela
		 */
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

		Dimension tamanhoPrograma = new Dimension(ProgramaLargura, ProgramaAltura);
		
		if(tipoArquivo.equals("escrita"))titulo = new JLabel("Altere o arquivo na area abaixo:");
		else titulo = new JLabel("Visualize o arquivo na area abaixo:");
		
		
		areaTexto = new JTextArea(insereArquivoNoText(arquivoAntigo));
		
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
		if(tipoArquivo.equals("escrita"))botaoCarrega.setEnabled(true);
		else botaoCarrega.setEnabled(false);
		
		/**
		 * Mostra a Janela
		 */
		add(painel);
		setSize(tamanhoPrograma);
		setResizable(false);
		setLocation((screenSize.width - ProgramaLargura) / 2,(screenSize.height - ProgramaAltura) / 2);
		if(tipoArquivo.equals("escrita"))setTitle("Alterar " + arquivoAntigo.getName());
		else setTitle("Visualizar " + arquivoAntigo.getName());
		setVisible(true);
		
	}
	
	public void actionPerformed(ActionEvent evt) {
		
		Object source = evt.getSource();

		if (source == botaoCarrega) {
			
			try{
				if(areaTexto.getText().equals("Digite aqui...") || areaTexto.getText().equals("")){
					JOptionPane.showMessageDialog(null, "Falha ao carregar este programa !\nDigite um programa valido ou feche a janela!", "ERRO", JOptionPane.INFORMATION_MESSAGE);
				}
				else{
					arquivoLido = new LerEscreverArquivo(areaTexto.getText(),arquivoAntigo.getPath());
					JOptionPane.showMessageDialog(null, 
							"Arquivo Alterado com Sucesso !", // mensagem
							"Atencao", //titulo
							JOptionPane.INFORMATION_MESSAGE);
					setVisible(false);
					
					File arquivoEnviar = new File(arquivoAntigo.getPath());
					
					LerArquivo arquivoAEnviar = new LerArquivo();
					arquivoAEnviar.setArquivo(arquivoEnviar);
					
					Cliente cliente = new Cliente(BarraDeMenu.nomeServidor, 7000, arquivoAEnviar);
					cliente.start();
					try {
						cliente.join(); 	
					} catch (Exception e) {
						System.out.println("erro ao ler arquivo");
					}
					
					arquivoAEnviar = (LerArquivo)cliente.getRequisicao();
					if(arquivoAEnviar.hasErros()){
					    // Exibo o erro na tela
						System.out.println(arquivoAEnviar.errosString());
					
					}else{
						System.out.println("");
					}
					
				}
			}
			catch(Exception e){
				System.out.println("Erro na gravacao do Arquivo - Clique do botao.");
			}		
		}
	}
	
	private String insereArquivoNoText(File arquivo){
		String conteudoArquivo = "";
		try {
			
			ArquivoTexto arquivoVelho = new ArquivoTexto(arquivo.getPath(),"");
			while(arquivoVelho.maisLinhas()){
				conteudoArquivo += "\n" + arquivoVelho.getLinha();
			}
		} catch (Exception e) {
			System.out.println("Erro ao ler Arquivo no Programa");
		} 
		return conteudoArquivo;
	}

	public void windowClosing(WindowEvent e) {}
	public void windowOpened(WindowEvent e) {}
	public void windowClosed(WindowEvent e) {}
	public void windowDeactivated(WindowEvent e) {}
	public void windowDeiconified(WindowEvent e) {}
	public void windowIconified(WindowEvent e) {}
	public void windowActivated(WindowEvent e){}
}