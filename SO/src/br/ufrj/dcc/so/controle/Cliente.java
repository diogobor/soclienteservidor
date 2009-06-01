package br.ufrj.dcc.so.controle;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

import br.ufrj.dcc.so.vista.BarraDeMenu;
import br.ufrj.dcc.so.vista.Comecar;
import br.ufrj.dcc.so.vista.PainelPrincipal;

/**
 * Classe do Cliente
 * @author SO
 *
 */

public class Cliente extends Thread {  

	public Cliente(){
		
    }
	
	public void run() {
		executaCliente();
	}
	
	public static void executaCliente(){
		//Thread startCliente = new Cliente();
		//startCliente.start();
		//Declaro o socket cliente
		Socket s = null;  
        
        //Declaro a Stream de saida de dados  
        PrintStream ps = null;  
          
        try{  
        	              
            //Cria o socket com o recurso desejado na porta especificada  
            s = new Socket(BarraDeMenu.nomeServidor,7000); 
            
            
            //bem-vindo cliente
            
            BufferedReader entrada=null;  
			//Cria um BufferedReader para o canal da stream de entrada de dados do socket s  
			entrada = new BufferedReader(new InputStreamReader(s.getInputStream()));  
			String bemVindo ="";
       	
        	bemVindo = entrada.readLine();        
        	System.out.println("Servidor: " + bemVindo);
                 
            //Cria a Stream de saida de dados  
            ps = new PrintStream(s.getOutputStream());  
              
            //Imprime uma linha para a stream de sa�da de dados 
            BufferedReader in = new BufferedReader(new InputStreamReader(System.in));  
           
            String mensagem = "";
            
            PainelPrincipal.situacaoServidor = "Conectado !";
            Comecar.painelFundo.repaint();
            BarraDeMenu.menuConectarServidor.setEnabled(false);
            
            mensagem = in.readLine();
            //ps.println(mensagem);
            
            
            while(!mensagem.equals("exit")){
				
				if (mensagem.equals("listarDir")){
					System.out.println("==== Lista Diretorio Local ====");
					Funcoes.detectarFuncao(0, "/home/diogobor", null);
					System.out.println("==== T�rmino ====");
				}
				else if (mensagem.equals("listarDirServ")){
					System.out.println("==== Lista Diretorio do Servidor ====");
					ps.println("listarDir");
					System.out.println("==== T�rmino ====");
				}
				else if (mensagem.equals("enviarArquivo")){
					System.out.println("==== Envia arquivo para Servidor ====");
					Funcoes.detectarFuncao(1, "F:\\DVD", null);
					System.out.println("==== T�rmino ====");
				}
				else if (mensagem.equals("apagarArquivo")){
					System.out.println("==== Apaga arquivo no Servidor ====");
					ps.println("apagarArquivo");
					ps.println("teste2.txt");
					System.out.println("==== T�rmino ====");
				}
				else if (mensagem.equals("apagarArquivoExtensao")){
					System.out.println("==== Apaga arquivo no Servidor ====");
					ps.println("apagarArquivoExtensao");
					ps.println("txt");
					System.out.println("==== T�rmino ====");
				}
				else if (mensagem.equals("infArquivo")){
					System.out.println("==== Obtem informacao do arquivo no Servidor ====");
					ps.println("infArquivo");
					ps.println("teste.fla");
					System.out.println("==== T�rmino ====");
				}
				else if (mensagem.equals("renomearArquivo")){
					System.out.println("==== Obtem informacao do arquivo no Servidor ====");
					ps.println("renomearArquivo");
					ps.println("teste.fla");
					ps.println("funcionaaaaa.fla");
					System.out.println("==== T�rmino ====");
				}
				else if (mensagem.equals("receberArquivo")){
					System.out.println("==== Recebe arquivo do Servidor ====");
					ps.println("receberArquivo");
					
					ps.println("diogo.txt");
					
					InputStream in2 = s.getInputStream();  
			        FileOutputStream fileOut = new FileOutputStream("F:\\Diogo\\so.txt");  
			        byte data[] = new byte[1024]; 
			        int size;  
			        while ((size = in2.read(data)) != -1)  
			        {  
			            fileOut.write(data, 0, size);  
			            fileOut.flush();
			        }  
			        fileOut.close();
			        System.out.println("==== T�rmino ====");
				}
				else if (mensagem.equals("enviarArquivoServ")){
					System.out.println("==== Envia arquivo para o Servidor ====");
					ps.println("enviarArquivoServ");

					
					DataOutputStream dadoSaida = new DataOutputStream (s.getOutputStream());
					 /* abrir arquivo para o envio  */
	                FileInputStream arquivoEntrada = new FileInputStream ("F:\\Diogo\\so.txt");
	                DataInputStream dadoEntrada = new DataInputStream (arquivoEntrada);

	                /* cria um buffer de 1024 bytes para o envio */
	                byte buffer[] = new byte[1024];            

	                /* envia os dados :) */
	                while (dadoEntrada.read(buffer) != -1)
	                	dadoSaida.write(buffer,0,buffer.length);
	                dadoSaida.close();
	                arquivoEntrada.close();
	                //in2.close();
					System.out.println("==== T�rmino ====");
				}
				else{
					System.out.println("Opcao invalida! Digite novamente:");
				}
				mensagem = in.readLine();
				//ps.println(mensagem);
			}            
              
        //Trata possiveis excecoes  
        }catch(IOException e){  
              
            System.out.println("Algum problema ocorreu ao criar ou enviar dados pelo socket.");
            PainelPrincipal.situacaoServidor = "Erro na conexao !";
            Comecar.painelFundo.repaint();
            BarraDeMenu.menuConectarServidor.setEnabled(true);
          
        }finally{  
              
            try{  
                  
                //Encerra o socket cliente  
                s.close();  
                  
            }catch(Exception e){
            	System.out.println("Erro ao fechar o arquivo !");
            }   	
        }  
	}
	
//	public static void main(String[] args) {
//		// TODO Auto-generated method stub
//		  
//        Cliente inicia = new Cliente();
//	}

}
