package br.ufrj.dcc.so.entidade;

import br.ufrj.dcc.so.controle.ControleArquivo;

public class DesconectarServidor extends Requisicao{
	
	private String tarefa = "conectando como servidor";
	
	@Override
	public void executar(ControleArquivo controleArquivo) {
		
		mensagemInicioTarefa(tarefa);
		
		//Usar semaforo neste ponto	
		//exclusao mutua
		if(controleArquivo.isClienteConectado(getCliente())){
			controleArquivo.desconectarCliente(getCliente());			
		}
		else{
			getErros().add("Este cliente nao esta conectado com o servidor");
		}
		//fim exclusao mutua
		
		mensagemFimTarefa(tarefa);
		
	}

}
