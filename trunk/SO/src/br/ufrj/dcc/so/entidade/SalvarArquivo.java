package br.ufrj.dcc.so.entidade;

import java.io.File;

import br.ufrj.dcc.so.controle.ControleArquivo;

public class SalvarArquivo extends Requisicao{

	private String nomeArquivo;
	private File arquivo;	
	
	public void setNomeArquivo(String nomeArquivo) {
		this.nomeArquivo = nomeArquivo;
	}

	public String getNomeArquivo() {
		return nomeArquivo;
	}

	public void setArquivo(File arquivo) {
		this.arquivo = arquivo;
	}

	public File getArquivo() {
		return arquivo;
	}

	@Override
	public void executar(ControleArquivo controleArquivo) {
		
	}

}
