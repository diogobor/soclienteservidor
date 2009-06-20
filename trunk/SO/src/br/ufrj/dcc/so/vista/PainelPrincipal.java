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

	public static Graphics grafico;

	public void paint(Graphics graficoPrincipal) {
		
		if (graficoPrincipal != null) {
			grafico = graficoPrincipal;
		}
		
		
	}
}
