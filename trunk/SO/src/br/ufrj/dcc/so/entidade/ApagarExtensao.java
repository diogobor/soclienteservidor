package br.ufrj.dcc.so.entidade;

import java.io.File;
import java.util.List;

import br.ufrj.dcc.so.controle.ControleArquivo;

public class ApagarExtensao extends Requisicao{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String tarefa = "apagando arquivo por extensao";
	private String nomeExtensao;	
	
	/*
	 * Metodos
	 */
	public void setNomeExtensao(String nomeExtensao) {
		this.nomeExtensao = nomeExtensao;
	}

	public String getNomeExtensao() {
		return nomeExtensao;
	}

	@Override
	public void executar(ControleArquivo controleArquivo) {
		
		mensagemInicioTarefa(tarefa);
		
		deletarArquivos(controleArquivo); 
        
        mensagemFimTarefa(tarefa);
		
	}

	private void deletarArquivos(ControleArquivo controleArquivo) {
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
			
		List<String> arquivosComExtensao = obterListaArquivosComExtensao(arquivos, getNomeExtensao());
		
		if(arquivosComExtensao.size() == 0) {
			getErros().add("Nao existe nenhum arquivo com esta extensao.");
			return;
		}
		
		for (String nomeArquivo : arquivosComExtensao) {
			
			ApagarArquivo apagar = criarApagarArquivo(nomeArquivo);
			apagar.executar(controleArquivo);
			
			if(apagar.hasErros()) getErros().addAll(apagar.getErros());			
		}
	}

	private ApagarArquivo criarApagarArquivo(String nomeArquivo) {
		ApagarArquivo apagar = new ApagarArquivo();
		apagar.setCliente(getCliente());
		apagar.setCaminho(getCaminho());
		apagar.setNomeArquivo(nomeArquivo);
		return apagar;
	}
}
