package br.ufrj.dcc.so.entidade;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import br.ufrj.dcc.so.controle.ControleArquivo;;

public abstract class Requisicao implements Serializable {
	
	public enum TipoArquivo{
		LEITURA,
		ESCRITA;
	}
	/*
	 * Variaveis	 
	 */
	private String caminho;
	private String cliente;
	private List<String> erros;
	private static final int MAXLENGTH = 100000000;
	 
		
	public abstract void executar(ControleArquivo controleArquivo);
	
	public void setCaminho(String caminho) {
		this.caminho = caminho;
	}

	public String getCaminho() {
		return caminho;
	}

	public void setCliente(String cliente) {
		this.cliente = cliente;
	}

	public String getCliente() {
		return cliente;
	}

	public void setErros(List<String> erros) {
		this.erros = erros;
	}

	public List<String> getErros() {
		if(erros == null) erros = new ArrayList<String>();
		return erros;
	}
	
	public String errosString(){
		String s = ""; 
		
		for (String erro : erros) {
			s += erro + "\n";
		}
		return s;
	}
	
	public boolean hasErros(){
		return ((erros != null) && (erros.size()!= 0));	
	}
	
	protected void mensagemFimTarefa(String tarefa) {
		System.out.println(String.format("FIM: Cliente %s %s.", getCliente(), tarefa));
	}

	protected void mensagemInicioTarefa(String tarefa) {
		System.out.println(String.format("INICIO: Cliente %s %s", getCliente(), tarefa));
	}
	
	protected List<String> obterListaArquivosComExtensao(File[] arquivos, String nomeExtensao) {
		List<String> arquivosComExtensao = new ArrayList<String>();
		
		for(int i = 0; i < arquivos.length; ++i){ 
		    File f = arquivos[i]; 
		    String[] texto = f.getName().split("\\.");
		    String extensaoArquivo = texto[texto.length-1].toLowerCase();
		    
		    if(extensaoArquivo.equals(nomeExtensao.toLowerCase())){
		    	arquivosComExtensao.add(f.getName());                    
		    } 
		}
		
		return arquivosComExtensao;
	}
	
	protected byte[] getBytesFromFile(File file) throws IOException {

	    InputStream is = null;   
	    try {   
	        long length = file.length();   
	        if (length > MAXLENGTH) throw new IllegalArgumentException ("File is too big");   
	        byte[] ret = new byte [(int) length];  
	        is = new FileInputStream (file);
	        is.read (ret);   
	        return ret;  
	    } finally {   
	        if (is != null) try { is.close(); } catch (IOException ex) {}   
	    }   
	}   
	
}
