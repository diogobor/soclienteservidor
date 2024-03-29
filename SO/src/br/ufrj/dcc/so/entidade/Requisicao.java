package br.ufrj.dcc.so.entidade;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import br.ufrj.dcc.so.controle.ControleArquivo;

public abstract class Requisicao implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ArrayList<byte[]> diretorioCompactado;
	
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

	public String getCaminho(){
//		String barra = "\\";
//		String caminhoFinal = "";
//		String[] temp;
//		if(System.getProperty("os.name").equals("Linux")){
//			barra = "/";
//		}
//		if (caminho.contains("/"))temp = caminho.split("/");
//		else temp = caminho.split("\\");
//		
//		
//		
//		if (temp.length > 1){
//			
//			for (int i = 1; i < temp.length; i++) {
//				caminhoFinal += barra + temp[i];
//			}
//			return temp[0]+caminhoFinal;
//		}
		
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
	
	protected byte[] transformaFileByte(File file) throws IOException {
		if(file.isFile()){
		    return cadaArquivo(file);
		}else{
			return null;
		}
	} 
	
	protected ArrayList<byte[]> getBytesFromDirectory(File file) throws IOException {
		diretorioCompactado = new ArrayList<byte[]>();
		arquivoDiretorio(file);
		return diretorioCompactado;
	}
	
	public ArrayList<byte[]> getDiretorioCompactado(){
		return diretorioCompactado;
	}
	
	protected void arquivoDiretorio(File file)throws IOException{
		File[] temp = file.listFiles();
		for (int i = 0; i < temp.length; i++) {
			if (temp[i].isDirectory()) {
				arquivoDiretorio(temp[i]);
				
			}else{
				diretorioCompactado.add(cadaArquivo(temp[i]));
				
			}
		}
	}
	
	protected byte[] cadaArquivo(File file) throws IOException {
		InputStream is = null;   
	    try {   
	        long length = file.length();   
	        if (length > MAXLENGTH) throw new IllegalArgumentException ("File is too big");   
	        byte[] ret = new byte [(int) length]; 
	        is = new FileInputStream(file);
	        is.read (ret);   
	        return ret;  
	    } finally {   
	        if (is != null) try { is.close(); } catch (IOException ex) {}   
	    } 
	}
	
	protected File transformaByteFile(byte[] arquivoAntigo,String caminhoCompleto) throws IOException{
		   
		InputStream  in = new ByteArrayInputStream (arquivoAntigo); 
		File arquivo = new File(caminhoCompleto);  
		FileOutputStream fout = new FileOutputStream(arquivo);
		//FileOutputStream fout = new FileOutputStream("./filesServer/diogo.txt");
		
		copy(in, fout);
		
		return arquivo;
		
	}
	
	protected void copy(InputStream in,OutputStream out) throws IOException{  
		
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
