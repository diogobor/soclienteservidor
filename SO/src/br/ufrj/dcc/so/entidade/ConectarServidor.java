package br.ufrj.dcc.so.entidade;

import br.ufrj.dcc.so.controle.ControleArquivo;

public class ConectarServidor extends Requisicao{
	
	private String tarefa = "conectando como servidor";
	
	@Override
	public void executar(ControleArquivo controleArquivo) {
		
		mensagemInicioTarefa(tarefa);
		
		try {
			controleArquivo.fecharAcessoListaCliente();
				
			if(!controleArquivo.isClienteConectado(getCliente())){
				controleArquivo.conectarCliente(getCliente());			
			}
			else{
				getErros().add("Cliente ja esta conectado com o servidor");
			}
			
			controleArquivo.abrirAcessoListaCliente();
		
		} catch (InterruptedException e) {
			getErros().add("Ocorreu um erro na execucao do semaforo.");
		}
		//fim exclusao mutua
		
		mensagemFimTarefa(tarefa);
		
	}

}
