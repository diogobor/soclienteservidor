package br.ufrj.dcc.so.controle;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
/**
 * Classe responsavel por criar um arquivo.
 * @author Projeto Arquitetura
 *
 */
public class ArquivoTexto {

	BufferedReader bf;

	BufferedWriter bw;

	String linha;

	/**
	 * Construtor 1 da Classe. Neste construtor eh passado como parametro o
	 * arquivo que sera lido.
	 */
	public ArquivoTexto(String nomeArq) throws IOException {
		bf = new BufferedReader(new FileReader(nomeArq));
	}

	/**
	 * Construtor 2 da Classe. Neste construtor eh criado um arquivo novo de
	 * texto.
	 */

	public ArquivoTexto() throws IOException {
		bw = new BufferedWriter(new FileWriter("./arquivo/programa.txt"));
	}

	/**
	 * 
	 * @return A linha que esta sendo lida.
	 */

	public String getLinha() {
		return linha;
	}

	/**
	 * 
	 * @return Booleano que diz se tem mais conteudo no arquivo texto.
	 * @throws IOException
	 */
	public boolean maisLinhas() throws IOException {
		while (bf.ready()) { // enquanto o arquivo ainda nao acabou...
		// o readLine le a linha atual e muda o ponteiro de leitura para a
		// proxima linha
			linha = bf.readLine();
			return true;
		}
		bf.close();
		return false;
	}

	/**
	 * Libera os dados da memoria para o arquivo e Fecha o arquivo.
	 * 
	 */
	public void fecharArquivo() {
		try {
			bw.flush();
			bw.close();
		} catch (IOException e) {
			System.out.println("Erro ao fechar o arquivo de Recordes!");
		}
	}

	/**
	 * Grava uma String no arquivo.
	 * 
	 * @param gravacao 
	 *            String a ser gravada no arquivo.
	 */

	public void gravarLinha(String gravacao) {
		try {
			bw.write(gravacao, 0, gravacao.length());
			bw.newLine();
		} catch (IOException e) {
			System.out.println("Erro ao gravar o arquivo!");
		}
	}

}

