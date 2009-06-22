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
	private byte[] arquivoPrincipal;

	private String tarefa = "salvando arquivo";
	
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
			getErros().add("Erro ao gravar arquivo no Servidor");
			System.out.println("Erro ao gravar arquivo no Servidor");
		}
		
	}

	public File getArquivo() {
		return arquivo;
	}

	@Override
	public void executar(ControleArquivo controleArquivo) {
		mensagemInicioTarefa(tarefa);
		
		try {
			arquivo = transformaByteFile(arquivoPrincipal, getCaminhoCompleto());
			
		} catch (Exception e) {
			getErros().add("Erro ao pegar o Arquivo.");
		}
//		try 
//		{	
//			File arq = new File(getCaminhoCompleto());
//			FileInputStream in2 = new FileInputStream(getArquivo());
//			in2.close();
//			FileOutputStream fileOut = new FileOutputStream(arq);  
//			byte data[] = new byte[1024]; 
//			int size;  
//			while ((size = in2.read(data)) != -1)  
//			{  
//			    fileOut.write(data, 0, size);
//			    fileOut.flush();
//			}  
//			fileOut.close();
//						
//		}
//		catch (Exception e) {				
//			getErros().add("Nao foi possivel Salvar o Arquivo no Servidor");
//		}
//		
		mensagemFimTarefa(tarefa);
	}

}
