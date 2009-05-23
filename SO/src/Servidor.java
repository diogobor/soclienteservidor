/*
 * Servidor.java
 *
 * Created on 19 de Maio de 2009, 10:18
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

import java.io.BufferedReader;  
import java.io.IOException;  
import java.io.InputStreamReader;  
import java.net.ServerSocket;  
import java.net.Socket;  
  
public class Servidor {  
  
    public static void main(String[] args) {  
          
        //Declaro o ServerSocket  
        ServerSocket serv=null;   
          
        //Declaro o Socket de comunicação  
        Socket s= null;  
          
        //Declaro o leitor para a entrada de dados  
        BufferedReader entrada=null;  
                  
        try{  
              
            //Cria o ServerSocket na porta 7000 se estiver disponível  
            serv = new ServerSocket(7000);  
          
            //Aguarda uma conexão na porta especificada e cria retorna o socket que irá comunicar com o cliente  
            s = serv.accept();  
              
            //Cria um BufferedReader para o canal da stream de entrada de dados do socket s  
            entrada = new BufferedReader(new InputStreamReader(s.getInputStream()));  
              
            //Aguarda por algum dado e imprime a linha recebida quando recebe  
            System.out.println(entrada.readLine());   
              
        //trata possíveis excessões de input/output. Note que as excessões são as mesmas utilizadas para as classes de java.io    
        }catch(IOException e){  
          
            //Imprime uma notificação na saída padrão caso haja algo errado.  
            System.out.println("Algum problema ocorreu para criar ou receber o socket.");  
          
        }finally{  
              
            try{  
                  
                //Encerro o socket de comunicação  
                s.close();  
                  
                //Encerro o ServerSocket  
                serv.close();  
                  
            }catch(IOException e){  
            }  
        }  
      
          
          
          
          
    }  
} 