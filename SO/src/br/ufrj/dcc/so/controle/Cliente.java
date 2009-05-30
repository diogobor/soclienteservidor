package br.ufrj.dcc.so.controle;
/*
 * Cliente.java
 *
 * Created on 19 de Maio de 2009, 10:21
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */



/**
 *
 * @author alunos
 */
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
  
public class Cliente {  
  
    public static void main(String[] args) {  
          
        //Declaro o socket cliente  
        Socket s = null;  
          
        //Declaro a Stream de saida de dados  
        PrintStream ps = null;  
          
        try{  
        	              
            //Cria o socket com o recurso desejado na porta especificada  
            s = new Socket("localhost",7000); 
            
            
            //bem-vindo cliente
            
            BufferedReader entrada=null;  
			//Cria um BufferedReader para o canal da stream de entrada de dados do socket s  
			entrada = new BufferedReader(new InputStreamReader(s.getInputStream()));  
			String bemVindo ="";
       	
        	bemVindo = entrada.readLine();        
        	System.out.println("Servidor: " + bemVindo);
                 
            //Cria a Stream de saida de dados  
            ps = new PrintStream(s.getOutputStream());  
              
            //Imprime uma linha para a stream de saï¿½da de dados 
            BufferedReader in = new BufferedReader(new InputStreamReader(System.in));  
           
            System.out.println("==== Arquivos em F:\\Dioog ======");
            listarDiretorio();
            System.out.println("===== Termino =====");
            
            String mensagem = "Cliente: " +  s.getInetAddress();// imprime o endereço do cliente
            ps.println(mensagem);
            
            mensagem = in.readLine();
            ps.println(mensagem);
            while(!mensagem.equals("exit")){
            	
            	mensagem = in.readLine();
            	ps.println(mensagem);
            
            	
            }
              
              
        //Trata possï¿½veis exceï¿½ï¿½es  
        }catch(IOException e){  
              
            System.out.println("Algum problema ocorreu ao criar ou enviar dados pelo socket.");  
          
        }finally{  
              
            try{  
                  
                //Encerra o socket cliente  
                s.close();  
                  
            }catch(Exception e){
            	System.out.println("Erro ao fechar o arquivo !");
            }  
            	
        }  
  
    }  
    
        public static void listarDiretorio(){
            
            File diretorio = new File("F:\\Diogo"); 
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
