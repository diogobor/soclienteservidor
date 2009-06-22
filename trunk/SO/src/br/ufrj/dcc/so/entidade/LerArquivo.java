package br.ufrj.dcc.so.entidade;

import java.io.File;

import br.ufrj.dcc.so.controle.ControleArquivo;

public class LerArquivo extends Requisicao{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String nomeArquivo;
	private TipoArquivo tipo;
	private File arquivo;
	private byte[] arquivoPrincipal;
	
	private String tarefa = "lendo arquivo";
	
	private String getCaminhoCompleto() {		
		return getCaminho() + "/" + getNomeArquivo();
	}
	
	public void setNomeArquivo(String nomeArquivo) {
		this.nomeArquivo = nomeArquivo;
	}

	public String getNomeArquivo() {
		return nomeArquivo;
	}

	public void setArquivo(File arquivo) {
		
		try {
			arquivoPrincipal = getBytesFromFile(arquivo);
		} catch (Exception e) {
			getErros().add("Erro ao ler arquivo no Servidor");
			System.out.println("Erro ao ler arquivo no Servidor");
		}
		
	}

	public File getArquivo() {
		try {
			arquivo = transformaByteFile(arquivoPrincipal, getCaminhoCompleto());
			
		} catch (Exception e) {
			getErros().add("Erro ao pegar o Arquivo.");
		}
		System.out.println("arquivo no cliente:" +arquivo.getPath());
		return arquivo;
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
		try 
		{	
			File arq = new File(getCaminhoCompleto());			
			
			if(!arq.exists())
			{
				getErros().add("No existe este arquivo no servidor");
				return;
			}
			
			arquivo = arq;
			
			
			try {
				arquivoPrincipal = getBytesFromFile(arquivo);
			} catch (Exception e) {
				getErros().add("Erro ao ler arquivo no Servidor");
				System.out.println("Erro ao ler arquivo no Servidor");
			}
			
			
			
			
			if(isEscrita())
			{
				ArquivoUtilizado arquivoUtilizado = new ArquivoUtilizado(getCliente(), getCaminhoCompleto());
				
				controleArquivo.fecharAcessoListaArquivoUtilizado();
				
				if(controleArquivo.isArquivoUsadoPorOutroCliente(arquivoUtilizado))
				{
					getErros().add("Este arquivo ja esta aberto para escrita com outro usuario.");					
					arquivo = null;
				}
				else if(!controleArquivo.isArquivoUsadoPorCliente(arquivoUtilizado))
				{
					controleArquivo.adicionarArquivoUtilizado(arquivoUtilizado);					
				}				
				
				controleArquivo.abrirAcessoListaArquivoUtilizado();
			}
			
		}
		catch (InterruptedException e) {				
			getErros().add("Ocorreu um erro na execucao do semaforo.");
		}
	}	

	private boolean isEscrita(){
		return getTipo() != null && getTipo() == TipoArquivo.ESCRITA;
	}
	
	

}
