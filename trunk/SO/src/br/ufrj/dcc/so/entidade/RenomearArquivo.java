package br.ufrj.dcc.so.entidade;

import java.io.File;

import br.ufrj.dcc.so.controle.ControleArquivo;

public class RenomearArquivo extends Requisicao{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String tarefa = "renomeando arquivo.";
	private String nomeArquivo;
	private String nomeNovoArquivo;
	
	private String getCaminhoCompleto() {		
		return getCaminho() + "/" + getNomeArquivo();
	}
	
	public void setNomeArquivo(String nomeArquivo) {
		this.nomeArquivo = nomeArquivo;
	}

	public String getNomeArquivo() {
		return nomeArquivo;
	}

	public void setNomeNovoArquivo(String nomeNovoArquivo) {
		this.nomeNovoArquivo = nomeNovoArquivo;
	}

	public String getNomeNovoArquivo() {
		return nomeNovoArquivo;
	}

	@Override
	public void executar(ControleArquivo controleArquivo) {
		
		mensagemInicioTarefa(tarefa);
		
		renomearArquivo(controleArquivo);
        
        mensagemFimTarefa(tarefa);	
	}

	private void renomearArquivo(ControleArquivo controleArquivo) {
		
		ArquivoUtilizado arquivoUtilizado = new ArquivoUtilizado(getCliente(),getCaminhoCompleto());
		
		try {
			controleArquivo.fecharAcessoListaArquivoUtilizado();
		
			if(controleArquivo.isArquivoUsadoPorOutroCliente(arquivoUtilizado)){
				getErros().add(String.format("Nao e possivel renomear o arquivo %s. O arquivo esta sendo utlilizado por outro cliente.", getNomeArquivo()));
			}
			else {
				
				boolean ok = renomearArquivo();	
				
		        if(ok) {		        	
		        	//Se o arquivo estiver aberto para o cliente, remove e adiciona um novo arquivoUtilizado com o novo nome de arquivo
			        if(controleArquivo.isArquivoUsadoPorCliente(arquivoUtilizado))
			        {
			        	controleArquivo.removerArquivoUtilizado(arquivoUtilizado);
			        	arquivoUtilizado.setCaminhoArquivo(getCaminho() + "/" + getNomeNovoArquivo());
			        	controleArquivo.adicionarArquivoUtilizado(arquivoUtilizado);
					} 
		        }
		        else{
		        	getErros().add("Nao foi possivel renomear o arquivo.");
		        }
			}
	        
	        controleArquivo.abrirAcessoListaArquivoUtilizado();
        
		} catch (InterruptedException e) {
			getErros().add("Ocorreu um erro na execucao do semaforo.");
		}
	}

	private boolean renomearArquivo() {
		File arquivo = new File(getCaminhoCompleto());			    
		File arquivoNovo = new File(getCaminho() + "/" + nomeNovoArquivo);	
		
		return arquivo.renameTo(arquivoNovo);
	}

}
