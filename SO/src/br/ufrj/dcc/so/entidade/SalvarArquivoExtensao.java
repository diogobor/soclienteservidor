package br.ufrj.dcc.so.entidade;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import br.ufrj.dcc.so.controle.ControleArquivo;

public class SalvarArquivoExtensao extends Requisicao{

	private String nomeExtensao;
	private List<File> arquivos;
	
	public SalvarArquivoExtensao(){
		arquivos = new ArrayList<File>();		
	}
		
	public void setNomeExtensao(String nomeExtensao) {
		this.nomeExtensao = nomeExtensao;
	}

	public String getNomeExtensao() {
		return nomeExtensao;
	}

	public void setArquivos(List<File> arquivos) {
		this.arquivos = arquivos;
	}

	public List<File> getArquivos() {
		return arquivos;
	}

	@Override
	public void executar(ControleArquivo controleArquivo) {
		
	}

}
