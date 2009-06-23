package br.ufrj.dcc.so.entidade;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import br.ufrj.dcc.so.controle.ControleArquivo;

public class LerDiretorio extends Requisicao {	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String tarefa = "lendo diretorio";
	private File diretorio;
	private byte[] diretorioPrincipal;
	private List<String> arquivosNoDiretorio;
	private String sistemaOperacional="";
	
	public LerDiretorio(){
		
	}

	public void setDiretorio(File arquivo) {
		this.diretorio = arquivo;
	}
	
	public byte[] getDiretorioPrincipal(){
		return diretorioPrincipal;
	}
	
	public List<String> getListaNomeArquivos(){
		return arquivosNoDiretorio;
	}
	
	public void setSistemaOperacional(String sistemaOperacional) {
		this.sistemaOperacional = sistemaOperacional;
	}

	public String getSistemaOperacional() {
		return sistemaOperacional;
	}	

	@Override
	public void executar(ControleArquivo controleArquivo) {
		
		mensagemInicioTarefa(tarefa);

		File diretorio = new File(getCaminho());
		
		if(!diretorio.exists()){
			getErros().add("Diretorio inexistente no servidor!");
			return;
		}
		
        File[] arquivos = diretorio.listFiles();        
       
        if(arquivos == null){        	
        	getErros().add("Nao existe arquivo(s) no diretorio");
        	return;
        }
			
		obterListaArquivos(arquivos);
		
		setSistemaOperacional(System.getProperty("os.name"));
		
		mensagemFimTarefa(tarefa);
		
	}
	
	private void obterListaArquivos(File[] arquivos) {
		arquivosNoDiretorio = new ArrayList<String>();
		for(int i = 0; i < arquivos.length; ++i){ 
		    File f = arquivos[i]; 
		    if(f.isFile()){
		    	arquivosNoDiretorio.add(f.getPath());	
		    }else if (f.isDirectory()){
		    	File[] temp = arquivos[i].listFiles();
		    	obterListaArquivos(temp);
		    }
		                        
		}
	}
	
}
