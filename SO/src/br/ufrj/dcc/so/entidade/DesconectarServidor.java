package br.ufrj.dcc.so.entidade;

import br.ufrj.dcc.so.controle.ControleArquivo;

public class DesconectarServidor extends Requisicao{
	
	private String tarefa = "desconectando com o servidor";
	
	@Override
	public void executar(ControleArquivo controleArquivo) {
		
		mensagemInicioTarefa(tarefa);
		
		try 
		{
			controleArquivo.fecharAcessoListaCliente();		
			controleArquivo.removerCliente(this);			
			controleArquivo.abrirAcessoListaCliente();
			
			controleArquivo.fecharAcessoListaRequisicoes();
			controleArquivo.limparRequisicoesAbertas(getCliente());
			controleArquivo.abrirAcessoListaRequisicoes();
		
		} catch (InterruptedException e) {
			getErros().add("Ocorreu um erro na execucao do semaforo.");
		}
		
		mensagemFimTarefa(tarefa);
		
	}

}
