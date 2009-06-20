package br.ufrj.dcc.so.entidade;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import br.ufrj.dcc.so.controle.ControleArquivo;
import br.ufrj.dcc.so.entidade.Requisicao.TipoArquivo;

public class LerArquivoExtensao extends Requisicao{

	private String tarefa = "lendo arquivo por extensao";
	private String nomeExtensao;
	private List<File> arquivos;
	private TipoArquivo tipo;
	
	public LerArquivoExtensao(){
		setArquivos(new ArrayList<File>());		
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
			else this.arquivos.add(ler.getArquivo());
			
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
