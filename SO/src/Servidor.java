/*
 * Servidor.java
 *
 * Created on 19 de Maio de 2009, 10:18
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
  
public class Servidor extends Thread{  
  
//	 socket deste cliente
	private Socket conexao;

	public Servidor(Socket s) {
		  conexao = s;
		}

    public static void main(String[] args) {  
          
        //Declaro o ServerSocket  
        ServerSocket serv=null;   
          
        //Declaro o Socket de comunica��o  
        Socket s= null;  
       
                  
        try{  
              
            //Cria o ServerSocket na porta 7000 se estiver dispon�vel  
            serv = new ServerSocket(7000);  
          
            while(true){
	            //Aguarda uma conex�o na porta especificada e cria retorna o socket que ir� comunicar com o cliente  
	            s = serv.accept();  
	              
	            Thread t = new Servidor(s);
	            t.start();
	            
            }
              
        //trata poss�veis excess�es de input/output. Note que as excess�es s�o as mesmas utilizadas para as classes de java.io    
        }catch(IOException e){  
          
            //Imprime uma notifica��o na sa�da padr�o caso haja algo errado.  
            System.out.println("Algum problema ocorreu para criar ou receber o socket.");  
          
        }finally{  
        	 try{  
                                  
                 //Encerro o ServerSocket  
                 serv.close();  
                   
             }catch(IOException e){  
             }  
           
        }        
    } 
    
    
    // execução da thread
	public void run() {
		
		
		try {
			

			PrintStream ps = null; 
			//enviar uma msg para o cliente 
			
			 ps = new PrintStream(conexao.getOutputStream());  
			 ps.println("Conexao estabelecida com o Cliente !");
			 ps.println("Oiiii...");
			
			
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
			}
			
			
     
			//Encerro o socket de comunica��o  
			conexao.close();  
			                       
			}catch(IOException e){  
				System.out.println(e);
			}
			
	}
    
} 