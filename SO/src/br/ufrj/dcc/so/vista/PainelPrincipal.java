package br.ufrj.dcc.so.vista;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;

/**
 * Classe onde tem as strings de cada registrador e os Sinais de controle, e a Figura de fundo
 */
public class PainelPrincipal extends Component{
	private static final long serialVersionUID = 1L;

	private Figura fundoMenu = new Figura();

	public static Graphics grafico;

	String temp = new String();
	
	public static String situacaoServidor = new String();
	
	
	
	
	public void paint(Graphics graficoPrincipal) {
		
		if (graficoPrincipal != null) {
			grafico = graficoPrincipal;
		}
		
		//fundoMenu.setImagem("./imagens/arquiteturanovo.png");
		//grafico.drawImage(fundoMenu.getImagem(), 0, 0, null);
		
		grafico.setColor(Color.black); // define a cor do painel
		grafico.setFont(new Font("Arial", Font.BOLD, 12)); // a fonte a ser usada
		
		grafico.drawString(situacaoServidor, 0, 20);
		
	}
}
