package br.ufrj.dcc.so.entidade;

import java.io.File;

import br.ufrj.dcc.so.controle.ControleArquivo;

public class RenomearArquivo extends Requisicao{

	private String tarefa = "renomeando arquivo";
	private String nomeArquivo;
	private String nomeNovoArquivo;
	
	
	public void setNomeArquivo(String nomeArquivo) {
		this.nomeArquivo = nomeArquivo;
	}

	public String getNomeArquivo() {
		return nomeArquivo;
	}

	public void setNomeNovoArquivo(String nomeNovoArquivo) {
		this.nomeNovoArquivo = nomeNovoArquivo;
	}

	public String getNomeNovoArquivo() {
		return nomeNovoArquivo;
	}

	@Override
	public void executar(ControleArquivo controleArquivo) {
		
		mensagemInicioTarefa(tarefa);
		
		//Tem que verificar se o arquivo esta com algum usuario
		
		File arquivo = new File(getCaminho() + "\\" + nomeArquivo);
	    
        File arquivoNovo = new File(getCaminho() + "\\" + nomeNovoArquivo);

        boolean ok = arquivo.renameTo(arquivoNovo);
        
        if(!ok)
        {
            getErros().add("Nao foi possivel renomear o arquivo.");
        }
        
        mensagemFimTarefa(tarefa);	
	}

}
