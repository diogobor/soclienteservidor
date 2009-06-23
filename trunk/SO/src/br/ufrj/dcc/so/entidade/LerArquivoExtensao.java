package br.ufrj.dcc.so.entidade;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import br.ufrj.dcc.so.controle.ControleArquivo;

public class LerArquivoExtensao extends Requisicao{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String tarefa = "lendo arquivo por extensao";
	private String nomeExtensao;
	//private List<File> arquivos;
	private HashMap<String, byte[]> arquivosBytes;
	private TipoArquivo tipo;
	
	public LerArquivoExtensao(){
	
	}
		
	public void setNomeExtensao(String nomeExtensao) {
		this.nomeExtensao = nomeExtensao;
	}

	public String getNomeExtensao() {
		return nomeExtensao;
	}
	
	public List<File> getArquivos() {
		List<File> arquivos = new ArrayList<File>();
		try {			
			for (String caminhoCompleto : arquivosBytes.keySet()) 
			{				
				File arquivo = transformaByteFile(arquivosBytes.get(caminhoCompleto), caminhoCompleto);
					
				arquivos.add(arquivo);				
			}
		} catch (Exception e) {
			getErros().add("Erro ao transformar bytes em Arquivo.");			
		}
		return arquivos;
	}

	public void setTipo(TipoArquivo tipo) {
		this.tipo = tipo;
	}	
	
	public TipoArquivo getTipo() {
		return tipo;
	}

	@Override
	public void executar(ControleArquivo controleArquivo) {
		mensagemInicioTarefa(tarefa);		
		
		lerArquivo(controleArquivo);			
				
		mensagemFimTarefa(tarefa);	
	}

	private void lerArquivo(ControleArquivo controleArquivo) {
		
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
		
		if(arquivosComExtensao.size() == 0) getErros().add("Nao existe nenhum arquivo com esta extensao.");
		
		for (String nomeArquivo : arquivosComExtensao) {
			
			LerArquivo ler = criarLerArquivo(nomeArquivo);
			ler.executar(controleArquivo);
			
			if(ler.hasErros()) getErros().addAll(ler.getErros());
			else arquivosBytes.put(ler.getCaminhoCompleto(), ler.getArquivoBytes());			
		}
	}

	private LerArquivo criarLerArquivo(String nomeArquivo) {
		LerArquivo ler = new LerArquivo();
		ler.setCliente(getCliente());
		ler.setCaminho(getCaminho());
		ler.setNomeArquivo(nomeArquivo);
		ler.setTipo(getTipo());
		return ler;
	}

}
