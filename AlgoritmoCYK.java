import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * 
 * @author Alvaro Ferreira
 * @author Deniscley Marfran
 * @author Enio Lemos
 * @version 1.0
 */
public class AlgoritmoCYK {
	/**
	 * tamanho da palavra
	 */
	private int tamanhoPalavra = 0;
	/**
	 * Matriz do CYK
	 */
	private String[][] matriz;
	/**
	 * Palavra salva em um vetor 
	 */
	private String[] palavraVetor;
	/**
	 * Palavra recebida como entrada pelo usuario
	 */
	private String palavra;
	/**
	 * Arquivo de entrada recebido pelo usuario
	 */
	private String arquivoEntrada;
	/**
	 * Arquivo de saida recebido pelo usuario
	 */
	private String arquivoSaida;
	/**
	 * Regras do lado direito
	 */
	private List<String> regraDireita;
	/**
	 * Regras do lado esquerdo
	 */
	private List<String> regraEsquerda;
	/**
	 * Resultados obtidos no calculo da primeira linha
	 */
	private ArrayList<String> resultados = new ArrayList<>();

	Scanner ler = new Scanner(System.in);
	/**
	 * Construtor vazio
	 */
	public AlgoritmoCYK(){
		
	}
	/**
	 * Construtor que recebe os parametros de entrada do programa
	 * @param args Arquivo de Entrada
	 * @param args2 Palavra de Entrada
	 * @param args3 Arquivo de Saida
	 */
	public AlgoritmoCYK(String args, String args2, String args3){
		arquivoEntrada = args;
		palavra = args2;
		arquivoSaida = args3;
	}
	/**
	 * Verifica se arquivo de entrada possui o simbolo inicial S e efetua chamada para os demais metodos.
	 * @throws IOException Para manipulacao de arquivo texto
	 */
	public void funcoes() throws IOException {
		ManipularArquivo arq = new ManipularArquivo();
		arq.tratarArquivo(arquivoEntrada);
		regraDireita = arq.getRegraDireita();
		regraEsquerda = arq.getRegraEsquerda();
		if (!regraEsquerda.get(0).equals("S")){
			System.out.println("Erro, não possui estado inicial");
		}else {
			tratarPalavra();
			primeiraLinha();			
			LinhasSuperiores ls = new LinhasSuperiores();
			ls.calcularDemaisLinhas(matriz, palavra.length(),regraDireita, regraEsquerda);
			saidaArquivoTexto(palavra);
		}
	}
	
	/**
	 * Faz o tratamento da palavra recebida como parametro, passando a palavra para um vetor. 
	 */
	public void tratarPalavra(){
		tamanhoPalavra = getPalavra().length();
		matriz = new String[tamanhoPalavra + 1][tamanhoPalavra + 1];
		palavraVetor = new String[tamanhoPalavra];
		for (int i = 0; i < tamanhoPalavra; i++) {
			palavraVetor[i] = Character.toString(palavra.charAt(i));
		}
		
		/**
		 * Campos da matriz na linha 0 e coluna 0 sao marcados com simbolos especiais.
		 * A necessidade se deu porque o indice da linha e coluna sao utilizados em multiplicacoes posteriores.
		 * Evitando assim multiplicacao por 0.
		 */
		for (int i = 0; i <= tamanhoPalavra; i++) {
			for (int j = 0; j <= tamanhoPalavra; j++) {
				matriz[0][j] = "¬";
				matriz[i][0] = "¬";
				matriz[i][j] = "";
			}
		}
	}

	/**
	 * Efetua o calculo da primeira linha da matriz no CYK.
	 */
	public void primeiraLinha() {
		int cont = 1;
		for (int i = 0; i < tamanhoPalavra; i++) {
			for (int j = 0; j < regraDireita.size(); j++) {
				if (regraDireita.get(j).replaceAll(" ", "").equals(palavraVetor[i].replaceAll(" ", ""))) {
					resultados.add(regraEsquerda.get(j));
					matriz[1][cont] = (matriz[1][cont]  + regraEsquerda.get(j) + ":").replaceAll(" ", "");
				}
			}
			cont++;
		}
	}
 
	/**
	 * Salva em um arquivo texto o resultado da computacao do CYK.
	 * @param palavra Palavra de entrada passado pelo usuario.
	 * @throws IOException Excessao para leitura de arquivo de saida
	 */
	public void saidaArquivoTexto(String palavra) throws IOException {
		FileWriter arq = new FileWriter(arquivoSaida);
		PrintWriter gravarArq = new PrintWriter(arq);
		int tamanhoPalavra = palavra.length();
		for (int i = tamanhoPalavra; i >= 1; i--) {
			gravarArq.print(i + " ");
			for (int j = 1; j <= tamanhoPalavra - i + 1; j++) {
				matriz[i][j] = "{" + matriz[i][j].replaceAll(":", ",") + "}";
				matriz[i][j] = matriz[i][j].replaceAll(",}", "}");
				gravarArq.print(" " + matriz[i][j]);
			}
			gravarArq.println();
		}
		gravarArq.print("p   ");
		for (int i=0;i<palavra.length();i++){
			gravarArq.print(palavra.charAt(i) + "   ");
		}
		gravarArq.close();
	}

	/**
	 * Retorna a matriz CYK
	 * @return the matriz
	 */
	public String[][] getMatriz() {
		return matriz;
	}

	/**
	 * Retorna a palavra
	 * @return the palavra
	 */
	public String getPalavra() {
		return palavra;
	}

	/**
	 * Retorna as regras do lado direito
	 * @return the regraDireita
	 */
	public List<String> getRegraDireita() {
		return regraDireita;
	}

	/**
	 * Retorna as regras do lado esquerda
	 * @return the regraEsquerda
	 */
	public List<String> getRegraEsquerda() {
		return regraEsquerda;
	}	
}
