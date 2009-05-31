package br.ufrj.dcc.so.controle;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe responsavel por instanciar um arquivo e ler ou escrever no mesmo.
 */
public class LerEscreverArquivo{

	private static final long serialVersionUID = 1L;

	public ArquivoTexto arqParser;

	public List<String> qtdParser = new ArrayList<String>();
	
	public LerEscreverArquivo(String nome_do_arquivo){
		try {
			arqParser = new ArquivoTexto(nome_do_arquivo);

			while (arqParser.maisLinhas()) {
				
				if (!(arqParser.getLinha().equals(""))){
					qtdParser.add(arqParser.getLinha());
				}
			}
			
			System.out.println("Quantidade de Linhas Lidas do Arquivo: " + qtdParser.size());

		} catch (Exception e) {
			System.out.println("Erro no metodo LerArquivo!");
		}
	}
	
	public LerEscreverArquivo(String gravacao, String teste) throws IOException {
		ArquivoTexto novoArquivo = new ArquivoTexto();
		try{
			novoArquivo.gravarLinha(gravacao);
		}
		catch(Exception e){
			System.out.println("Erro ao gravar arquivo.");
		}
		novoArquivo.fecharArquivo();
		
	}
}
