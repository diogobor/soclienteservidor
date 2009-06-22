package br.ufrj.dcc.so.entidade;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import br.ufrj.dcc.so.controle.ControleArquivo;

public class SalvarArquivo extends Requisicao{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String nomeArquivo;
	private File arquivo;	

	private String tarefa = "salvando arquivo";
	
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
		return arquivo;
	}

	@Override
	public void executar(ControleArquivo controleArquivo) {
		mensagemInicioTarefa(tarefa);
		try 
		{	
			File arq = new File(getCaminho() + "/" + arquivo.getName());
			FileInputStream in2 = new FileInputStream(arquivo);
			FileOutputStream fileOut = new FileOutputStream(arq);  
			byte data[] = new byte[1024]; 
			int size;  
			while ((size = in2.read(data)) != -1)  
			{  
			    fileOut.write(data, 0, size);  
			    fileOut.flush();
			}  
			fileOut.close();
			
		}
		catch (Exception e) {				
			getErros().add("Nao foi possivel Salvar o Arquivo no Servidor");
		}
		
		mensagemFimTarefa(tarefa);
	}

}
