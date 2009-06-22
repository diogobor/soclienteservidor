package br.ufrj.dcc.so.entidade;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

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
		this.arquivo = arquivo;
	}

	public File getArquivo() {
		System.out.println("aquiiiiiiiiiii...");
		try {
			arquivo = transformaByteFile(arquivoPrincipal);
			
		} catch (Exception e) {
			getErros().add("Erro ao pegar o Arquivo.");
			System.out.println("Erro ao pegar o Arquivo.");
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
	
	private File transformaByteFile(byte[] arquivoAntigo) throws IOException{
		   
		InputStream  in = new ByteArrayInputStream (arquivoAntigo); 
		File arquivo = new File(getCaminhoCompleto());  
		FileOutputStream fout = new FileOutputStream(arquivo);  
		
		copy(in, fout);
		
		return arquivo;
		
	}
	
	private void copy(InputStream in,OutputStream out) throws IOException{  
		
		byte[] buffer = new byte[1024 * 4]; //4 Kb  
		int n = 0;  
		while (-1 != (n = in.read(buffer))) {  
		out.write(buffer, 0, n);  
		}  
		out.flush();  
				       
		out.close();  
		in.close();  
	}

}
