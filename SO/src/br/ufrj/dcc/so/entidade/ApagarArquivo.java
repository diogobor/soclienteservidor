package br.ufrj.dcc.so.entidade;

import java.io.File;

import br.ufrj.dcc.so.controle.ControleArquivo;

public class ApagarArquivo extends Requisicao{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String tarefa= "apagando arquivo";
	private String nomeArquivo;
	
	/*
	 * Metodos
	 */
	private String getCaminhoCompleto() {		
		return getCaminho() + "/" + getNomeArquivo();
	}
	
	public void setNomeArquivo(String nomeArquivo) {
		this.nomeArquivo = nomeArquivo;
	}

	public String getNomeArquivo() {
		return nomeArquivo;
	}

	@Override
	public void executar(ControleArquivo controleArquivo) {
		
		mensagemInicioTarefa(tarefa);
		
		try {
			ArquivoUtilizado arquivoUtilizado = new ArquivoUtilizado(getCliente(), getCaminhoCompleto());
			
			controleArquivo.fecharAcessoListaArquivoUtilizado();		
			
			if(controleArquivo.isArquivoUsadoPorOutroCliente(arquivoUtilizado))
			{
				getErros().add(String.format("Nao e possivel deletar o arquivo %s. O arquivo esta sendo utlilizado por outro cliente.", getNomeArquivo()));
			}
			else{
				deletarArquivo();			
				//remove requisicao se o cliente abriu o arquivo para escrita anteriormente
				controleArquivo.removerArquivoUtilizado(arquivoUtilizado);
			}
			
			controleArquivo.abrirAcessoListaArquivoUtilizado();
		
		} catch (InterruptedException e) {
			getErros().add("Ocorreu um erro na execucao do semaforo.");
		}
		
		mensagemFimTarefa(tarefa);
		
	}

	private void deletarArquivo() {
		File arquivo = new File(getCaminhoCompleto());
		if (arquivo.exists()){
			arquivo.delete();			
		}
		else{
			getErros().add("Arquivo inexistente no servidor!");
		}
	}

}
