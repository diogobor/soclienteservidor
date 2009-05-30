package br.ufrj.dcc.so.controle;
/*
 * Servidor.java
 *
 * Created on 19 de Maio de 2009, 10:18
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
  
public class Servidor extends Thread{  
  
//	 socket deste um cliente
	private Socket conexao;

	public Servidor(Socket s) {
		  conexao = s;
		}

    public static void main(String[] args) {  
          
        //Declaro o ServerSocket  
        ServerSocket serv=null;   
          
        //Declaro o Socket de comunicacao
        Socket s= null;  
       
                  
        try{  
              
            //Cria o ServerSocket na porta 7000 se estiver disponï¿½vel  
            serv = new ServerSocket(7000);  
          
            while(true){
	            //Aguarda uma conexao na porta especificada e cria retorna o socket que ira comunicar com o cliente  
	            s = serv.accept();  
	              
	            Thread t = new Servidor(s);
	            t.start();
	            
            }
              
        //trata possï¿½veis excessï¿½es de input/output. Note que as excessï¿½es sï¿½o as mesmas utilizadas para as classes de java.io    
        }catch(IOException e){  
          
            //Imprime uma notificaï¿½ï¿½o na saï¿½da padrï¿½o caso haja algo errado.  
            System.out.println("O cliente encerrou de forma inesperada.");  
          
        }finally{  
        	 try{  
                                  
                 //Encerro o ServerSocket  
                 serv.close();  
                   
             }catch(IOException e){  
             }  
           
        }        
    } 
    
    
    // execucao da thread
	public void run() {
		
		
		try {
			

			PrintStream ps = null; 
			//enviar uma msg para o cliente 
			
			 ps = new PrintStream(conexao.getOutputStream());  
			 ps.println("Conexao estabelecida com o Cliente !");			
			
			//Declaro o leitor para a entrada de dados  
			BufferedReader entrada=null;  
			//Cria um BufferedReader para o canal da stream de entrada de dados do socket s  
			entrada = new BufferedReader(new InputStreamReader(conexao.getInputStream()));  
			String mensagem ="";
			  
			//Aguarda por algum dado e imprime a linha recebida quando recebe  
			mensagem = entrada.readLine();
			System.out.println(mensagem);
			while(!mensagem.equals("exit")){
				
				
				mensagem = entrada.readLine();
				System.out.println(mensagem);
				if (mensagem.equals("listarDir")){
					System.out.println("==== Lista Diretorio Servidor ====");
					listarDiretorio();
					System.out.println("==== Término ====");
				}
				
				
			}
			
			
     
			//Encerro o socket de comunicaï¿½ï¿½o  
			conexao.close();  
			                       
			}catch(IOException e){  
				System.out.println(e);
			}
			
	}
	
	public static void listarDiretorio(){
        
        File diretorio = new File("F:\\DVD"); 
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