package br.ufrj.dcc.so.controle;

import java.net.ServerSocket;
import java.net.Socket;
import java.io.IOException;

public class mainServidor {

		public static void main(String[] args) {  
        
        //Declaro o ServerSocket  
        ServerSocket serv=null;   
                  
        try{  
              
            //Cria o ServerSocket na porta 7000 se estiver disponivel  
            serv = new ServerSocket(7000);  
            System.out.println("Escutando porta.");
            
            while(true){
	            //Aguarda uma conexao na porta especificada e cria retorna o socket que ira comunicar com o cliente  
            	Socket s = serv.accept();
	            Thread t = new Servidor(s);
	            t.start();
            }
        //trata possiveis excecoes de input/output.    
        }catch(IOException e){
            //Imprime uma notificacaoo na saida padrao caso haja algo errado.  
            System.out.println("O cliente encerrou de forma inesperada.");
        }
        finally{  
        	 try{                
                 //Encerra-se o ServerSocket  
                 serv.close();  
                   
             }catch(IOException e){ 
             }  
           
        }        
    } 

}
