package br.ufrj.dcc.so.controle;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

/**
 * Classe do Cliente
 * @author SO
 *
 */
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
           
            
            String mensagem = "Cliente: " +  s.getInetAddress();// imprime o endereço do cliente
            ps.println(mensagem);
            
            mensagem = in.readLine();
            //ps.println(mensagem);
            
            
            while(!mensagem.equals("exit")){
				
				if (mensagem.equals("listarDir")){
					System.out.println("==== Lista Diretorio Local ====");
					Funcoes.detectarFuncao(0, "F:\\Diogo");
					System.out.println("==== Término ====");
				}
				else if (mensagem.equals("listarDirServ")){
					System.out.println("==== Lista Diretorio do Servidor ====");
					ps.println("listarDir");
					System.out.println("==== Término ====");
				}
				else if (mensagem.equals("enviarArquivo")){
					System.out.println("==== Envia arquivo para Servidor ====");
					Funcoes.detectarFuncao(1, "F:\\DVD");
					System.out.println("==== Término ====");
				}
				else if (mensagem.equals("apagarArquivo")){
					System.out.println("==== Apaga arquivo no Servidor ====");
					ps.println("apagarArquivo");
					ps.println("teste2.txt");
					System.out.println("==== Término ====");
				}
				else if (mensagem.equals("apagarArquivoExtensao")){
					System.out.println("==== Apaga arquivo no Servidor ====");
					ps.println("apagarArquivoExtensao");
					ps.println("txt");
					System.out.println("==== Término ====");
				}
				mensagem = in.readLine();
				//ps.println(mensagem);
			}            
              
        //Trata possiveis excecoes  
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
}
