package br.ufrj.dcc.so.controle;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
 
/**
 * Classe do Servidor
 * @author Diogo e Aline
 *
 */
public class Servidor extends Thread{  
	private byte data[] = new byte[1024]; 
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
				
				if (mensagem.equals("listarDir")){
					System.out.println("==== Lista Diretorio Servidor ====");
					System.out.println(entrada.readLine());
					Funcoes.detectarFuncao(0, Funcoes.ENDERECOSERVIDOR, null);
					System.out.println("==== Término ====");
				}
				else if (mensagem.equals("apagarArquivo")){
					System.out.println("==== Apaga arquivo no Servidor ====");
					System.out.println(entrada.readLine());
					Funcoes.detectarFuncao(4, entrada.readLine(), null);
					System.out.println("==== Término ====");
				}
				else if (mensagem.equals("apagarArquivoExtensao")){
					System.out.println("==== Apaga arquivo por Extensao no Servidor ====");
					System.out.println(entrada.readLine());
					Funcoes.detectarFuncao(5, entrada.readLine(), null);
					System.out.println("==== Término ====");
				}
				else if (mensagem.equals("infArquivo")){
					System.out.println("==== Obtem informacao do arquivo no Servidor ====");
					System.out.println(entrada.readLine());
					Funcoes.detectarFuncao(6, entrada.readLine(), null);
					System.out.println("==== Término ====");
				}
				else if (mensagem.equals("renomearArquivo")){
					System.out.println("==== Obtem informacao do arquivo no Servidor ====");
					System.out.println(entrada.readLine());
					Funcoes.detectarFuncao(3, entrada.readLine(), entrada.readLine());
					System.out.println("==== Término ====");
				}
				else if (mensagem.equals("receberArquivo")){
					System.out.println("==== Obtem informacao do arquivo no Servidor ====");
					System.out.println(entrada.readLine());
					
					 FileInputStream fileIn = new FileInputStream(Funcoes.ENDERECOSERVIDOR + "\\" + entrada.readLine());                      
					 OutputStream out = conexao.getOutputStream();  
           
			         int size;  
			         while ((size = fileIn.read(data)) != -1)  
			         {  
			             out.write(data, 0, size);  
			             out.flush();  
			         }   
			         fileIn.close(); 
			         out.close();  
			        
	
					System.out.println("==== Término ====");
				}
				mensagem = entrada.readLine();
				System.out.println(mensagem);
				
			}
			
			
     
			//Encerro o socket de comunicacao  
			conexao.close();  
			                       
			}catch(IOException e){  
				//System.out.println(e);
			}
			
	}
} 