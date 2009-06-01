package br.ufrj.dcc.so.controle;

import java.io.File;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.lang.String;

public class Funcoes {	
	
	public static final String ENDERECOSERVIDOR = "/home/diogobor/public_html";
	
	public static void detectarFuncao(int funcao, String secParameter, String thParameter){
		
		switch(funcao) {
			// ------ Lista diretorio -----
			case   0: { listarDiretorio(secParameter); break; }
			// ------ Enviar arquivo ------
			case   1: { enviarArquivo(); break; }
			// ------ Receber arquivo -----
			case   2: { receberArquivo(); break; }
			// ------ Renomear arquivo ----
			case   3: { renomearArquivo(secParameter,thParameter); break; }
			// ------ Apagar Arquivo ------
			case   4: { apagarArquivo(secParameter); break; }
			
			case   5: { deleteExtensao(secParameter); break; }
			// ------ Pegar dados do arquivo -----
			case   6: { infArquivo(secParameter); break; }				
			}
	}
	
	public static void listarDiretorio(String raiz){
	        
        File diretorio = new File(raiz); 
        File[] arquivos = diretorio.listFiles(); 
  
        if(arquivos != null){ 
            int length = arquivos.length; 
  
            for(int i = 0; i < length; ++i){ 
                File f = arquivos[i]; 
            
                if(f.isFile()){ 
                    System.out.println(f.getName()); 
                } 
                else if(f.isDirectory()){ 
                    System.out.println("Diretorio: " + f.getName()); 
                } 
            } 
        }    
    }
	
	public static void enviarArquivo(){
			
	}
	
	public static void receberArquivo(){
		
	}
	
	public static void renomearArquivo(String nomeAntigo, String nomeNovo){

        File arquivo1 = new File(ENDERECOSERVIDOR + "\\" + nomeAntigo);
    
        File arquivo2 = new File(ENDERECOSERVIDOR + "\\" + nomeNovo);
    
        boolean ok = arquivo1.renameTo(arquivo2);
        if(ok){
            System.out.println("Arquivo renomeado com sucesso.");
        }
        else{
            System.out.println("Nao foi possivel renomear o arquivo.");
        }
	}
	
	public static void apagarArquivo(String nomeArquivo){
		String completePath = ENDERECOSERVIDOR + "\\" + nomeArquivo;
		File arquivo = new File(completePath);
		if (arquivo.exists()){
			arquivo.delete();
			System.out.println("O arquivo " + nomeArquivo + " foi deletado com sucesso !");
		}
		else{
			System.out.println("N�o foi poss�vel deletar o arquivo !");
		}
	}
	
	public static void deleteExtensao(String extensao){
		File diretorio = new File(ENDERECOSERVIDOR); 
        File[] arquivos = diretorio.listFiles(); 
        String nomeArquivo ="";
        if(arquivos != null){ 
            int length = arquivos.length; 
  
            for(int i = 0; i < length; ++i){ 
                File f = arquivos[i]; 
                String[] texto = f.getName().split("\\.");
                String extensaoArquivo = texto[texto.length-1].toLowerCase();
                nomeArquivo = f.getName();
                if(extensaoArquivo.equals(extensao.toLowerCase())){
                	f.delete();
                    System.out.println("O arquivo" + nomeArquivo + " foi deletado com sucesso");
                } 
            } 
        }   
	}	
	
	public static void infArquivo(String nomeArquivo){
		String completePath = ENDERECOSERVIDOR + "\\" + nomeArquivo;
		File arquivo = new File(completePath);
		long tamanho = arquivo.length();
		if (arquivo.exists()){
			DateFormat formatData = new SimpleDateFormat("dd/MM/yyyy");  
			String data = formatData.format(new Date(arquivo.lastModified()));  
			System.out.println("Nome do Arquivo: " + arquivo.getName());
			System.out.println("Data da ultima modificacao: " + data);  
			System.out.println("O arquivo " + nomeArquivo + " tem " + tamanho/1024 + " KB.");
		}
		else{
			System.out.println("N�o foi poss�vel deletar o arquivo !");
		}
	}

}
