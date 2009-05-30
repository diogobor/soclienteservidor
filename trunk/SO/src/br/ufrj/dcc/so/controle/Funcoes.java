package br.ufrj.dcc.so.controle;

import java.io.File;

public class Funcoes {	
	
	public static final String ENDERECOSERVIDOR = "F:\\DVD";
	
	public static void detectarFuncao(int funcao, String secParameter){
		
		switch(funcao) {
			// ------ Lista diretorio -----
			case   0: { listarDiretorio(secParameter); break; }
			// ------ Enviar arquivo ------
			case   1: { enviarArquivo(); break; }
			// ------ Receber arquivo -----
			case   2: { receberArquivo(); break; }
			// ------ Renomear arquivo ----
			case   3: { renomearArquivo(); break; }
			// ------ Apagar Arquivo ------
			case   4: { apagarArquivo(secParameter); break; }
			
			case   5: { deleteExtensao(secParameter); break; }
			// ------ Pegar dados do arquivo -----
			case   6: { infArquivo(); break; }				
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
	
	public static void renomearArquivo(){
		
	}
	
	public static void apagarArquivo(String nomeArquivo){
		String completePath = ENDERECOSERVIDOR + "\\" + nomeArquivo;
		File arquivo = new File(completePath);
		if (arquivo.exists()){
			arquivo.delete();
			System.out.println("O arquivo " + nomeArquivo + " foi deletado com sucesso !");
		}
		else{
			System.out.println("Não foi possível deletar o arquivo !");
		}
	}
	
	public static void infArquivo(){
		
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
	public static void deleteTree(File inFile) {    
       if (inFile.isFile()) {    
           inFile.delete();    
       } else {    
            File files[] = inFile.listFiles();    
            for (int i=0;i< files.length; i ++) {    
            deleteTree(files[i]);  
            }    
       }    
	}    
	
	

}
