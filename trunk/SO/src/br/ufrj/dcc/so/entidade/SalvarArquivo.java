package br.ufrj.dcc.so.entidade;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import br.ufrj.dcc.so.controle.ControleArquivo;

public class SalvarArquivo extends Requisicao{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String nomeArquivo;
	private File arquivo;
	private byte[] arquivoBytes;

	private String tarefa = "salvando arquivo";
	
	public String getCaminhoCompleto() {		
		return getCaminho() + "/" + getNomeArquivo();
	}
	
	public void setNomeArquivo(String nomeArquivo) {
		this.nomeArquivo = nomeArquivo;
	}

	public String getNomeArquivo() {
		return nomeArquivo;
	}
	
	public void setArquivoBytes(byte[] arquivoBytes) {
		this.arquivoBytes = arquivoBytes;
	}

//	public byte[] getArquivoBytes() {
//		return arquivoBytes;
//	}

	public void setArquivo(File arquivo) {

		try {
			setArquivoBytes(transformaFileByte(arquivo));
		} catch (Exception e) {
			getErros().add("Erro ao gravar arquivo no Servidor");
			System.out.println("Erro ao gravar arquivo no Servidor");
		}
		
	}

	@Override
	public void executar(ControleArquivo controleArquivo) {
		mensagemInicioTarefa(tarefa);
		
		ArquivoUtilizado arquivoUtilizado = new ArquivoUtilizado(getCliente(),getCaminhoCompleto());
		
		try {
			
			controleArquivo.fecharAcessoListaArquivoUtilizado();
			if(controleArquivo.isArquivoUsadoPorOutroCliente(arquivoUtilizado))
			{
				getErros().add(String.format("Nao e possivel salvar o arquivo %s. O arquivo esta sendo utlilizado por outro cliente.", getNomeArquivo()));					
				setArquivoBytes(null);
			}
			else
			{
				arquivo = transformaByteFile(arquivoBytes, getCaminhoCompleto());
				
				if(!controleArquivo.isArquivoUsadoPorCliente(arquivoUtilizado))
				{					
					controleArquivo.adicionarArquivoUtilizado(arquivoUtilizado);
				}
			}
			
			controleArquivo.abrirAcessoListaArquivoUtilizado();
			
			
		} catch (IOException e) {
			getErros().add("Erro ao transformar bytes em Arquivo.");
		} catch (InterruptedException e) {
			getErros().add("Ocorreu um erro na execucao do semaforo.");
		}

		mensagemFimTarefa(tarefa);
	}

}
