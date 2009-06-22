package br.ufrj.dcc.so.entidade;

import java.io.File;
import java.io.FileInputStream;
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
	
	
	protected  byte[] getBytesFromFile(File file) throws IOException {
		
		FileInputStream is = new FileInputStream(file);

        // Get the size of the file
        long length = file.length();
        
        // You cannot create an array using a long type.
        // It needs to be an int type.
        // Before converting to an int type, check
        // to ensure that file is not larger than Integer.MAX_VALUE.
        if (length > Integer.MAX_VALUE) {
            // File is too large
        }
    
        // Create the byte array to hold the data
        byte[] bytes = new byte[(int)length];
    
        // Read in the bytes
        int offset = 0;
        int numRead = 0;
        System.out.println("passa1....");
        while (offset < bytes.length
               && (numRead=is.read(bytes, offset, bytes.length-offset)) >= 0) {
            offset += numRead;
        }
        System.out.println("passa....");
        // Ensure all the bytes have been read in
        if (offset < bytes.length) {
            throw new IOException("Could not completely read file "+file.getName());
        }
    
        // Close the input stream and return bytes
        is.close();
        return bytes;
    }
	
}
