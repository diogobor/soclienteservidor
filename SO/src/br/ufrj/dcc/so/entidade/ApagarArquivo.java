package br.ufrj.dcc.so.entidade;

import java.io.File;

import br.ufrj.dcc.so.controle.ControleArquivo;

public class ApagarArquivo extends Requisicao{
	
	private String tarefa= "esta apagando o arquivo";
	private String nomeArquivo;
	
	/*
	 * Metodos
	 */
	public void setNomeArquivo(String nomeArquivo) {
		this.nomeArquivo = nomeArquivo;
	}

	public String getNomeArquivo() {
		return nomeArquivo;
	}

	@Override
	public void executar(ControleArquivo controleArquivo) {
		
		mensagemInicioTarefa(tarefa);
		
		//Verificar se nao tem ninguem usando o arquivo
		
		String completePath = getCaminho() + "\\" + nomeArquivo;
		File arquivo = new File(completePath);
		if (arquivo.exists()){
			arquivo.delete();			
		}
		else{
			getErros().add("Nao foi possivel deletar o arquivo !");
		}
		
		mensagemFimTarefa(tarefa);
		
	}

}
