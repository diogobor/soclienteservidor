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
import java.io.IOException;  
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
              
            //Cria a Stream de saida de dados  
            ps = new PrintStream(s.getOutputStream());  
              
            //Imprime uma linha para a stream de saída de dados  
            ps.println("Estou enviando dados para o servidor");  
              
        //Trata possíveis exceções  
        }catch(IOException e){  
              
            System.out.println("Algum problema ocorreu ao criar ou enviar dados pelo socket.");  
          
        }finally{  
              
            try{  
                  
                //Encerra o socket cliente  
                s.close();  
                  
            }catch(IOException e){}  
          
        }  
  
    }  
}
