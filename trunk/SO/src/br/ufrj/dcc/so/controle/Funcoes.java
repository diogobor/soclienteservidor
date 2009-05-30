package br.ufrj.dcc.so.controle;

import java.io.File;

public class Funcoes {
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
}
