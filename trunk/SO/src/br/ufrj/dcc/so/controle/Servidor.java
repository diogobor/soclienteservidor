package br.ufrj.dcc.so.controle;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
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
	private Socket conexao;
	private ObjectOutputStream saida_arquivo ;  
	private ObjectInputStream entrada_arquivo ; 
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
              
            //Cria o ServerSocket na porta 7000 se estiver dispon�vel  
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
			
			//Cria um BufferedReader para o canal da stream de entrada de dados do socket s (conexao) 
			entrada = new BufferedReader(new InputStreamReader(conexao.getInputStream()));  
			String mensagem ="";
			  
			//Aguarda por algum dado e imprime a linha recebida quando recebe  
				try {
					//dentro de try catch pq se o cliente encerrar a tela entao soltara uma excecao
					mensagem = entrada.readLine();
					System.out.println(mensagem);
				
					while(!mensagem.equals("exit")){
						if (mensagem.equals("listarDir")){
							System.out.println("==== Lista Diretorio Servidor ====");
							System.out.println("Cliente: " + conexao.getInetAddress());
							Funcoes.detectarFuncao(0, Funcoes.ENDERECOSERVIDOR, null);
							System.out.println("==== T�rmino ====");
						}
						else if (mensagem.equals("apagarArquivo")){
							System.out.println("==== Apaga arquivo no Servidor ====");
							System.out.println("Cliente: " + conexao.getInetAddress());
							Funcoes.detectarFuncao(4, entrada.readLine(), null);
							System.out.println("==== T�rmino ====");
						}
						else if (mensagem.equals("apagarArquivoExtensao")){
							System.out.println("==== Apaga arquivo por Extensao no Servidor ====");
							System.out.println("Cliente: " + conexao.getInetAddress());
							Funcoes.detectarFuncao(5, entrada.readLine(), null);
							System.out.println("==== T�rmino ====");
						}
						else if (mensagem.equals("infArquivo")){
							System.out.println("==== Obtem informacao do arquivo no Servidor ====");
							System.out.println("Cliente: " + conexao.getInetAddress());
							Funcoes.detectarFuncao(6, entrada.readLine(), null);
							System.out.println("==== T�rmino ====");
						}
						else if (mensagem.equals("renomearArquivo")){
							System.out.println("==== Renomeia arquivo no Servidor ====");
							System.out.println("Cliente: " + conexao.getInetAddress());
							Funcoes.detectarFuncao(3, entrada.readLine(), entrada.readLine());
							System.out.println("==== T�rmino ====");
						}
						else if (mensagem.equals("receberArquivo")){
							System.out.println("==== Recebe arquivo do Servidor ====");
							System.out.println("Cliente: " + conexao.getInetAddress());
							
							FileInputStream fileIn = new FileInputStream(Funcoes.ENDERECOSERVIDOR + "\\" + entrada.readLine());                      
							OutputStream out = conexao.getOutputStream();  
							byte data[] = new byte[1024]; 
							int size;  
					        while ((size = fileIn.read(data)) != -1)  
					        {  
					            out.write(data, 0, size);  
					            out.flush();  
					        }   
					        fileIn.close(); 
					        out.close();  
					      
							System.out.println("==== T�rmino ====");
						}
						else if (mensagem.equals("enviarArquivoServ")){
							System.out.println("==== Envia arquivo para o Servidor ====");	
							
							
							DataInputStream dadoEntrada = new DataInputStream (conexao.getInputStream());
							 /* abrir arquivo para o envio  */
			                FileOutputStream arquivoSaida = new FileOutputStream ("F:\\DVD\\soAgora.txt");
			                DataOutputStream dadoSaida = new DataOutputStream (arquivoSaida);
		
			                /* cria um buffer de 1024 bytes para o envio */
			                byte buffer[] = new byte[1024];            
		
			                /* envia os dados :) */
			                while (dadoEntrada.read(buffer) != -1)
			                	dadoSaida.write(buffer,0,buffer.length);
							//dadoSaida.close();
			                dadoEntrada.close();
			                arquivoSaida.close();
							
							
							System.out.println("==== T�rmino ====");
							entrada = null;
							}
						
							mensagem = entrada.readLine();
							System.out.println(mensagem);
				}		
				} catch (Exception e) {
					System.out.println("Cliente -" + conexao.getInetAddress().getHostName() + "- encerrou sessao !");
				}
     
			//Encerro o socket de comunicacao  
			conexao.close();  
			                       
			}catch(IOException e){  
				System.out.println("Erro de leitura ou escrita no arquivo no Servidor!");
			}
			
	}  
} 