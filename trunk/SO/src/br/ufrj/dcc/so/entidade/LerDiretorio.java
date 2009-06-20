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
		
		if(getCaminho().equals(""))diretorio = new File(controleArquivo.ENDERECO_SERVIDOR);
		else diretorio = new File(getCaminho());
		
		if(!diretorio.exists()){
			getErros().add("N�o existe este diretorio no servidor");
			diretorio = null;			
		}
				
		mensagemFimTarefa(tarefa);
		
	}	

}
