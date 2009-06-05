package br.ufrj.dcc.so.entidade;

import java.io.File;

import br.ufrj.dcc.so.controle.ControleArquivo;

public class ApagarExtensao extends Requisicao{

	private String tarefa = "esta apagando por extensao";
	private String nomeExtensao;	
	
	/*
	 * Metodos
	 */
	public void setNomeExtensao(String nomeExtensao) {
		this.nomeExtensao = nomeExtensao;
	}

	public String getNomeExtensao() {
		return nomeExtensao;
	}

	@Override
	public void executar(ControleArquivo controleArquivo) {
		
		mensagemInicioTarefa(tarefa);
		
		//Verificar se todos os arquivos estao livres
		
		File diretorio = new File(getCaminho()); 
        File[] arquivos = diretorio.listFiles(); 
        String nomeArquivo ="";
        if(arquivos != null){ 
            int length = arquivos.length; 
  
            for(int i = 0; i < length; ++i){ 
                File f = arquivos[i]; 
                String[] texto = f.getName().split("\\.");
                String extensaoArquivo = texto[texto.length-1].toLowerCase();
                nomeArquivo = f.getName();
                if(extensaoArquivo.equals(nomeExtensao.toLowerCase())){
                	f.delete();                    
                } 
            } 
        } 
        
        mensagemFimTarefa(tarefa);
		
	}
}
