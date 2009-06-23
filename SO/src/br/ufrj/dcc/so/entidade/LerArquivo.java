package br.ufrj.dcc.so.entidade;

import java.io.File;
import java.io.IOException;

import br.ufrj.dcc.so.controle.ControleArquivo;

public class LerArquivo extends Requisicao{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String nomeArquivo;
	private TipoArquivo tipo;
	private byte[] arquivoBytes;
	
	private String tarefa = "lendo arquivo";
	
	public String getCaminhoCompleto() {		
		return getCaminho() + "/" + getNomeArquivo();
	}
	
	public void setNomeArquivo(String nomeArquivo) {
		this.nomeArquivo = nomeArquivo;
	}

	public String getNomeArquivo() {
		return nomeArquivo;
	}
	public byte[] getArquivoBytes() {
		return arquivoBytes;
	}	

	public File getArquivo() {
		File arquivo = null;
		try {
			arquivo = transformaByteFile(getArquivoBytes(), getCaminhoCompleto());
			
		} catch (Exception e) {
			getErros().add("Erro ao transformar bytes em Arquivo.");
		}

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
				getErros().add("Nao existe este arquivo no servidor");
				return;
			}	
			
			arquivoBytes = transformaFileByte(arq);
			
			if(isEscrita())
			{
				ArquivoUtilizado arquivoUtilizado = new ArquivoUtilizado(getCliente(), getCaminhoCompleto());
				
				controleArquivo.fecharAcessoListaArquivoUtilizado();
				
				if(controleArquivo.isArquivoUsadoPorOutroCliente(arquivoUtilizado))
				{
					getErros().add(String.format("Nao e possivel ler o arquivo %s. O arquivo esta sendo utlilizado por outro cliente.", getNomeArquivo()));					
					arquivoBytes = null;
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
		catch (IOException e) {
			getErros().add("Erro ao transformar o arquivo em bytes.");
		}
	}	

	private boolean isEscrita(){
		return getTipo() != null && getTipo() == TipoArquivo.ESCRITA;
	}

}
