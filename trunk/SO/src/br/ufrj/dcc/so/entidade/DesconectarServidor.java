package br.ufrj.dcc.so.entidade;

import br.ufrj.dcc.so.controle.ControleArquivo;

public class DesconectarServidor extends Requisicao{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String tarefa = "desconectando com o servidor";
	
	@Override
	public void executar(ControleArquivo controleArquivo) {
		
		mensagemInicioTarefa(tarefa);
		
		try 
		{
			controleArquivo.fecharAcessoListaCliente();	
			removerCliente(controleArquivo);						
			controleArquivo.abrirAcessoListaCliente();
			
			controleArquivo.fecharAcessoListaArquivoUtilizado();
			controleArquivo.removerArquivosUtilizados(getCliente());
			controleArquivo.abrirAcessoListaArquivoUtilizado();
		
		} catch (InterruptedException e) {
			getErros().add("Ocorreu um erro na execucao do semaforo.");
		}
		
		mensagemFimTarefa(tarefa);
		
	}

	private void removerCliente(ControleArquivo controleArquivo) {
		if(controleArquivo.contemCliente(getCliente())){
			controleArquivo.removerCliente(getCliente());				
		}
		else
		{
			getErros().add("Este cliente nao esta conectado com o servidor");
		}
	}

}
