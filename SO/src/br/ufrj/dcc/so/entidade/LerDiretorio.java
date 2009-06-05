package br.ufrj.dcc.so.entidade;

import java.io.File;

import br.ufrj.dcc.so.controle.ControleArquivo;

public class LerDiretorio extends Requisicao {	
	
	private String tarefa = "lendo diretorio";
	private File diretorio;
	
	public LerDiretorio(){
		
	}
	
	public void setDiretorio(File arquivo) {
		this.diretorio = arquivo;
	}

	public File getDiretorio() {
		return diretorio;
	}

	@Override
	public void executar(ControleArquivo controleArquivo) {
		
		mensagemInicioTarefa(tarefa);
		
		diretorio = new File(controleArquivo.ENDERECO_SERVIDOR + getCaminho());
				
		mensagemFimTarefa(tarefa);
		
	}	

}
