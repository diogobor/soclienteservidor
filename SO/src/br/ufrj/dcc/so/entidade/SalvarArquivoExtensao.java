package br.ufrj.dcc.so.entidade;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import br.ufrj.dcc.so.controle.ControleArquivo;

public class SalvarArquivoExtensao extends Requisicao{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String nomeExtensao;
	//private File diretorio;
	private List<File> arquivos;
	private HashMap<String, byte[]> arquivosBytes;
	private String tarefa = "salvando lista de arquivos";
	
	public SalvarArquivoExtensao(){
		//arquivos = new ArrayList<File>();		
	}
		
	public void setNomeExtensao(String nomeExtensao) {
		this.nomeExtensao = nomeExtensao;
	}

	public String getNomeExtensao() {
		return nomeExtensao;
	}

//	public void setDiretorio(File diretorio) {
//		this.diretorio = diretorio;
//	}
	
	public void setArquivos(List<File> arquivos) {
		try {
			for (File file : arquivos) {
				
				byte[] arqByte = transformaFileByte(file);
				arquivosBytes.put(file.getPath(), arqByte);				
			}		
		} catch (IOException e) {
			getErros().add("Erro ao transformar Arquivos em bytes.");	
		}
	}

//	public List<File> getArquivos() {
//		return arquivos;
//	}

	@Override
	public void executar(ControleArquivo controleArquivo) {
		mensagemInicioTarefa(tarefa);
		try 
		{
	        for (String caminhoCompleto : arquivosBytes.keySet()) {
				SalvarArquivo salvar = criarSalvarArquivo(caminhoCompleto, arquivosBytes.get(caminhoCompleto));
				salvar.executar(controleArquivo);
				
				if(salvar.hasErros()) getErros().addAll(salvar.getErros());
			}	
		}
		catch (Exception e) {				
			getErros().add("Nao foi possivel Salvar o Arquivo no Servidor");
		}
		
		mensagemFimTarefa(tarefa);
	}
	
	private SalvarArquivo criarSalvarArquivo(String caminhoCompleto, byte[] arquivoByte) {
		SalvarArquivo salvar = new SalvarArquivo();
		salvar.setCliente(getCliente());
		salvar.setArquivoBytes(arquivoByte);
		salvar.setCaminho(caminhoCompleto);		
		return salvar;
	}

}
