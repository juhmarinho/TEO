package prvcejt_grasp_vnd;

import java.util.ArrayList;

public class Principal {


		public static void main(String[] args) {

				ManipulaDados entra = new ManipulaDados();
				ArrayList<String> registros = entra.lerArquivo();
				int linhaC = 0;
				Deposito deposito = new Deposito();
				
				for (String registro : registros) {
					String linhaArray[] = registro.split("\t");
					
					if(linhaC == 0){
						deposito.criaVeiculo(linhaArray[1]); //pego capacidade veiculo
						
						
					} else if (linhaC == 1){ // pego as coordenadas do deposito
						deposito.setCoordenadaX(Float.parseFloat(linhaArray[1]));// coo x deposito
						deposito.setCoordenadaY(Float.parseFloat(linhaArray[2])); //coo y deposito
					
					} else {//pego todas as informações: array[0]= id cliente, array[1]= x
						//array[2]= y, array[3]= demanda, array[4]= ,array[2]= 
						//array[6]= , array[7]= , array[8]= 
						
						deposito.criaCliente(linhaArray[0],linhaArray[1],linhaArray[2],linhaArray[3],linhaArray[4],linhaArray[5],linhaArray[6],linhaArray[7],linhaArray[8]);
						//System.out.println(linhaArray[0]);
					}
					
					linhaC++;
				}
		deposito.populaMatrizCusto();
		ArrayList<Rota> solucao = deposito.criaSolucaoInicial();
			
		}

	}



