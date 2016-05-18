package prvcejt_grasp_vnd;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;





public class Deposito {
	
	private float coordenadaX;
	private float coordenadaY;
	private Veiculo veiculo;
	ArrayList<Integer> listaColunas =  new ArrayList<Integer>();	
	private ArrayList<Rota> listaRota = new ArrayList<>();
	private HashMap<Integer, Cliente> listaCliente = new HashMap<>(); 
	private float[][] matrizCustos;
	private HashMap<Integer, Float> listaDemanda = new HashMap<>();
	
	
	public float getCoordenadaX() {
		return coordenadaX;
	}

	public void setCoordenadaX(float coordenadaX) {
		this.coordenadaX = coordenadaX;
	}

	public float getCoordenadaY() {
		return coordenadaY;
	}

	public void setCoordenadaY(float coordenadaY) {
		this.coordenadaY = coordenadaY;
	}

	public Veiculo getVeiculo() {
		return veiculo;
	}

	public void setVeiculo(Veiculo veiculo) {
		this.veiculo = veiculo;
	}

	
	public void criaCliente(String id, String coordenadaX, String coordenadaY,
			String demanda,String iniciojt,String fimjt,String tempoatendimento,String coleta,String entrega ) {		

		Cliente cliente = new Cliente(Integer.parseInt(id)-1, Float.parseFloat(coordenadaX), Float.parseFloat(coordenadaY), 
				Float.parseFloat(demanda),Float.parseFloat(iniciojt),Float.parseFloat(fimjt),Float.parseFloat(tempoatendimento),Float.parseFloat(coleta),Float.parseFloat(entrega));

		this.listaCliente.put(Integer.parseInt(id)-1, cliente);
		//System.out.println(listaCliente);
	}

	public void criaVeiculo(String capacidade) {
		this.veiculo = new Veiculo();
		this.veiculo.setCapacidade(Integer.parseInt(capacidade));
		
	}

public void populaMatrizCusto() {
		
		int tamanho = this.listaCliente.size() ;
		this.matrizCustos = new float[tamanho][tamanho];				
		
		for(int i = 0; i <= this.matrizCustos.length - 1; i++){
			for(int j = 0; j <= this.matrizCustos.length - 1; j++){
				if(i == 0 && j == 0){
					this.matrizCustos[i][j] = 0;
					
				} else if (i == 0){
				
					this.matrizCustos[i][j] = calculaDistancia(this.coordenadaX, this.listaCliente.get(j).getCoordenadaX(), 
							   this.coordenadaY, this.listaCliente.get(j).getCoordenadaY());
				} else if (j == 0) {
					this.matrizCustos[i][j] = calculaDistancia(this.listaCliente.get(i).getCoordenadaX(), this.coordenadaX, 
								this.listaCliente.get(i).getCoordenadaY(), this.coordenadaY);
				} else {
					this.matrizCustos[i][j] = calculaDistancia(this.listaCliente.get(i).getCoordenadaX(), this.listaCliente.get(j).getCoordenadaX(), 
							   this.listaCliente.get(i).getCoordenadaY(), this.listaCliente.get(j).getCoordenadaY());
				}
			}
		}
		ManipulaDados manipula = new ManipulaDados();
		manipula.escreveArquivo(this.matrizCustos, "matriz.txt");
	}
	
	public float calculaDistancia(float x1, float x2, float y1, float y2) {
		return (float) Math.sqrt(Math.pow(x1 - x2,2) + Math.pow(y1 - y2,2));
	}
	
	public float custoDemanda(Rota rota){
		float valor = 0;
		
		for (Cliente cliente : rota.getListaCliente()) {
			valor += cliente.getDemanda();
		}
		return valor;
	}
	
	private ArrayList<Rota> criaSol() {
		
		return listaRota;
		
		
	}
	
	public ArrayList<Rota> criaSolucaoInicial() {
		
		@SuppressWarnings("unused")
		long tempoInicio = System.currentTimeMillis();  
	
		ArrayList<Rota> solucao = criaSol();
			
		System.out.println("---------SOLUCAO INICIAL--------");
	
	//System.out.println("Valor objetivo: " + funcaoObjetivo(solucao));
	for (Rota rota2 : solucao) {
		System.out.print("Rota - " + "Dist: " + rota2.getCustoTotal() + " - ");
		System.out.print("Dem: " + custoDemanda(rota2) + " - ");
		for (Cliente cliente : rota2.getListaCliente()) {
			System.out.print(cliente.getId() + ";");
		}
		System.out.println("");
	}
	
		Cliente deposito = new Cliente();
		deposito.setCoordenadaX(this.coordenadaX);
		deposito.setCoordenadaY(this.coordenadaY);
		deposito.setDemanda(0);
		deposito.setId(0);
		
		int linhaAux = 0;
		HashMap<Integer, Cliente> listaCandidatos = new HashMap<>(this.listaCliente);		
		int tamanhoLista = 1;
		ArrayList<Rota> rotas = new ArrayList<>();
		Rota rota = new Rota();
		
		while(!listaCandidatos.isEmpty()){	
			//Se deposito
			if(linhaAux == 0){
				rota = new Rota();
				rota.setItemListaCliente(deposito);
				rota.setCustoTotal(0.0);
			}		
			
			for (Integer colunaAux : listaCandidatos.keySet()) {
				if((linhaAux != colunaAux) && //Verifica se i != i
					(verificaCusto(listaColunas, colunaAux, linhaAux, tamanhoLista) && //Pode ser na lista de colunas 
					(verificaCapacidade(custoDemanda(rota), this.veiculo.getCapacidade(), listaCandidatos.get(colunaAux).getDemanda())))){ //Verifica se ultrapassa a capacidade do veiculo
					
					colocaListaColunas(listaColunas, colunaAux, linhaAux, tamanhoLista);
				}
			}
			
			//Insere cliente na rota			
			if(listaColunas.size() != 0){
				
				int coluna = escolheItemRota(listaColunas);
				
				rota.setItemListaCliente(listaCandidatos.get(listaColunas.get(coluna)));
				rota.setCustoTotal(rota.getCustoTotal() + matrizCustos[linhaAux][listaColunas.get(coluna)]);
				
				linhaAux = listaColunas.get(coluna);
				
				listaCandidatos.remove(listaColunas.get(coluna));
				
				if(listaCandidatos.size() == 0){
					linhaAux = 0;
					rota.setItemListaCliente(deposito);
					int posicao = rota.getListaCliente().get(rota.getListaCliente().size() - 1).getId();
					rota.setCustoTotal(rota.getCustoTotal() + this.matrizCustos[linhaAux][posicao]);				
					
					rotas.add(rota);
					listaColunas.clear();
				}
				
				listaColunas.clear();
	
			} else {												
				linhaAux = 0;
								
				rota.setItemListaCliente(deposito);
				int posicao = rota.getListaCliente().get(rota.getListaCliente().size() - 1).getId();
				rota.setCustoTotal(rota.getCustoTotal() + this.matrizCustos[linhaAux][posicao]);				
				
				rotas.add(rota);
				listaColunas.clear();				
			}			
		}

		return rotas;
	}
	
	public int escolheItemRota(ArrayList<Integer> listaColunas) {
		
		if(listaColunas.size() == 1)
			return 0;
		
		Random gerador = new Random();
		
        int numero = gerador.nextInt(listaColunas.size() - 1);
        return numero;
	}
	
	public boolean verificaVazio(HashMap<Integer, Cliente> listaCandidatos) {
		if(listaCandidatos.size() == 1 && listaCandidatos.get(0) == null)
			return true;
		
		return false;
	}

	public boolean verificaCusto(ArrayList<Integer> listaColunas, int coluna, int linha, int tamanhoLista) {
		
		if(listaColunas.size() < tamanhoLista){
			return true;
		}

		for (Integer cliente : listaColunas) {
			if(matrizCustos[linha][coluna] < matrizCustos[linha][cliente]){
				return true;
			}
		}
		
		return false;
	}
	
	public void colocaListaColunas(ArrayList<Integer> listaColunas, int coluna, int linha, int tamanhoLista) {
		
		if(listaColunas.size() < tamanhoLista){		
			listaColunas.add(coluna);		
		} else {
			for(int i=0; i < listaColunas.size(); i++){			
				if(this.matrizCustos[linha][coluna] < this.matrizCustos[linha][listaColunas.get(i)]){
					listaColunas.remove(i);
					listaColunas.add(coluna);
				}
			}
		}
	}
	
	public boolean verificaCapacidade(float custoAtual, int capacidade, float demanda) {
		if((custoAtual + demanda) <= capacidade){
			return true;
		}			
		else
			return false;
	}
	
}
