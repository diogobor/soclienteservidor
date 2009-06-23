package br.ufrj.dcc.so.entidade;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import br.ufrj.dcc.so.controle.ControleArquivo;

public class LerDiretorio extends Requisicao {	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String tarefa = "lendo diretorio";
	private File diretorio;
	private byte[] diretorioPrincipal;
	
	public LerDiretorio(){
		
	}

	public void setDiretorio(File arquivo) {
		this.diretorio = arquivo;
	}

	public File getDiretorio() {
		try {
			diretorio = transformaByteFile(diretorioPrincipal,getCaminho());
		} catch (Exception e) {
			getErros().add("Erro ao pegar o Diretorio.");
			System.out.println("Erro ao pegar o Diretorio.");
		}
		return diretorio;
	}
	
	public byte[] getDiretorioPrincipal(){
		return diretorioPrincipal;
	}

	@Override
	public void executar(ControleArquivo controleArquivo) {
		
		mensagemInicioTarefa(tarefa);
		
		if(getCaminho().equals(""))diretorio = new File(controleArquivo.ENDERECO_SERVIDOR);
		else diretorio = new File(getCaminho());
		
		try {
			diretorioPrincipal = transformaFileByte(diretorio);
		} catch (Exception e) {
			getErros().add("Erro ao ler diretorio no Servidor");
			System.out.println("Erro ao ler diretorio no Servidor");
		}
				
		mensagemFimTarefa(tarefa);
		
	}	
	
	
	
	

}
