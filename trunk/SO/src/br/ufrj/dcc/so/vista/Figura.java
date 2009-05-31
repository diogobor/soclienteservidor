package br.ufrj.dcc.so.vista;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
/**
 * Classe que criara a Figura
 */
public class Figura {
	private int posicX;

	private int posicY;

	private BufferedImage imagem = null;

	public void setPosicaoX(int x) {
		posicX = x;
	}

	public void setPosicaoY(int y) {
		posicY = y;
	}

	public int getPosicaoX() {
		return posicX;
	}

	public int getPosicaoY() {
		return posicY;
	}

	public void setImagem(String caminhoFigura) {

		if (imagem == null) {

			try {
				imagem = ImageIO.read(new File(caminhoFigura));
			} catch (IOException e) {
				System.out.println("A imagem nao pode ser carregada. "
						+ getClass().getName());
			}

		}
	}

	/**
	 * @return a imagem da Figura carregada.
	 */
	public BufferedImage getImagem() {
		return imagem;
	}
}
