package br.ufrj.dcc.so.controle;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
  
public class Servidor extends Thread{  
  
/**
 * Classe do Servidor
 */
	private Socket conexao;
	/**
	 * construtor da classe
	 * @param s
	 */
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
              
        //trata possiveis excecoes de input/output.    
        }catch(IOException e){  
          
            //Imprime uma notificacaoo na saida padrao caso haja algo errado.  
            System.out.println("O cliente encerrou de forma inesperada.");  
          
        }finally{  
        	 try{  
                                  
                 //Encerra-se o ServerSocket  
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
					Funcoes.listarDiretorio("F:\\DVD");
					System.out.println("==== Término ====");
				}
				
				
			}
			
			
     
			//Encerro o socket de comunicaï¿½ï¿½o  
			conexao.close();  
			                       
			}catch(IOException e){  
				System.out.println(e);
			}
			
	}
} 