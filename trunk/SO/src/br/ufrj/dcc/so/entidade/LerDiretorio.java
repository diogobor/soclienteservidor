package br.ufrj.dcc.so.entidade;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import br.ufrj.dcc.so.controle.ControleArquivo;

public class LerDiretorio extends Requisicao {	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String tarefa = "lendo diretorio";
	private File diretorio;
	private byte[] diretorioPrincipal;
	private List<String> arquivosNoDiretorio;
	
	public LerDiretorio(){
		
	}

	public void setDiretorio(File arquivo) {
		this.diretorio = arquivo;
	}
	
	public byte[] getDiretorioPrincipal(){
		return diretorioPrincipal;
	}
	
	public List<String> getListaNomeArquivos(){
		return arquivosNoDiretorio;
	}

	@Override
	public void executar(ControleArquivo controleArquivo) {
		
		mensagemInicioTarefa(tarefa);

		File diretorio = new File(getCaminho());
		
		if(!diretorio.exists()){
			getErros().add("Diretorio inexistente no servidor!");
			return;
		}
		
        File[] arquivos = diretorio.listFiles();        
       
        if(arquivos == null){        	
        	getErros().add("Nao existe arquivo(s) no diretorio");
        	return;
        }
			
		arquivosNoDiretorio = obterListaArquivos(arquivos);
	
		mensagemFimTarefa(tarefa);
		
	}	
	
}
