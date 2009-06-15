package br.ufrj.dcc.so.entidade;

import br.ufrj.dcc.so.controle.ControleArquivo;

public class ConectarServidor extends Requisicao{
	
	private String tarefa = "conectando com o servidor";
	
	@Override
	public void executar(ControleArquivo controleArquivo) {
		
		mensagemInicioTarefa(tarefa);
		
		try {
			controleArquivo.fecharAcessoListaCliente();			
			
			controleArquivo.adicionarCliente(this);			
			
			controleArquivo.abrirAcessoListaCliente();
		
		} catch (InterruptedException e) {
			getErros().add("Ocorreu um erro na execucao do semaforo.");
		}		
		
		mensagemFimTarefa(tarefa);
		
	}

}
