import java.io.IOException;
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
public class LinhasSuperiores {
	
	/**
	 * Utilizado para salvar os simbolos que estao nas diagonais dos calculos
	 */
	private ArrayList<String> simbolosDiagonais = new ArrayList<>();
	/**
	 * Utilizado para salvar os simbolos que estao na vertical
	 */
	private ArrayList<String> simbolosVertical = new ArrayList<>();
	/**
	 * Variavel auxiliar dos simbolos nas diagonais.
	 * Utilizado quando uma posicao da matriz possui mais de um simbolo
	 */
	private ArrayList<String> simbolosDiagonais_aux = new ArrayList<>();
	/**
	 * Variavel auxiliar dos simbolos verticais.
	 * Utilizado quando uma posicao da matriz possui mais de um simbolo
	 */
	private ArrayList<String> simbolosVertical_aux = new ArrayList<>();
	/**
	 * Variavel de controle
	 */
	private int cont;
	/**
	 * Buffer de armazenamento temporario.
	 */
	private String buffer;
	Scanner ler = new Scanner(System.in);
	private AlgoritmoCYK cyk = new AlgoritmoCYK();

	/**
	 * Construtor vazio
	 */
	public LinhasSuperiores() {

	}
	/**
	 * Efetua o calculo da segunda linha em diante da matriz CYK.
	 * @param matriz Matriz com resultados do CYK
	 * @param tamanhoPalavra Tamanho da palavra a ser analisada
	 * @param regraDireita Simbolo depois do -> lido no arquivo .txt.
	 * @param regraEsquerda Simbolos antes de -> lido no arquivo .txt.
	 */
	public void calcularDemaisLinhas(String matriz[][], int tamanhoPalavra, List<String> regraDireita,
			List<String> regraEsquerda) {
		for (int linha = 2; linha <= tamanhoPalavra; linha++) {
			for (int coluna = 1; coluna < tamanhoPalavra - linha + 2; coluna++) {
				calcularElementos(matriz, linha, coluna, regraDireita, regraEsquerda);
			}
		}
		/**
		 * Limpar ArrayLists
		 */
		simbolosDiagonais.clear();
		simbolosVertical.clear();
		simbolosDiagonais_aux.clear();
		simbolosVertical_aux.clear();
	}
	
	/**
	 * Funcao que efetua o calculo dos elementos na matriz CYK.
	 * @param matriz Matriz com resultados do CYK
	 * @param linha Numero da linha a ser calculada
	 * @param coluna Numero da coluna a ser calculado
	 * @param regraDireita Simbolo depois do -> lido no arquivo .txt.
	 * @param regraEsquerda Simbolos antes de -> lido no arquivo .txt.
	 */
	public void calcularElementos(String[][] matriz, int linha, int coluna, List<String> regraDireita, List<String> regraEsquerda) {
		/**
		 * Varre em todas as linhas em ordem decrescente
		 */
		for (int i = linha; i >= 1; i--) {
			for (int j = 1; j < linha; j++) { //
				if (i + j == linha) {
					simbolosDiagonais.add(matriz[i][(coluna + j)]);
					simbolosVertical.add(matriz[j][coluna]);
					separarElementos(simbolosDiagonais, simbolosVertical, matriz, linha, coluna, regraDireita, regraEsquerda);
				}
			}
		}
	}

	/**
	 * Separa os elementos, caso a posicao da matriz possua mais de um elemento
	 * @param simbolosDiagonais Simbolos que estao na diagonal do calculo
	 * @param simbolosVertical Simbolos que estao na vertical do calculo
	 * @param matriz Matriz com resultados do CYK
	 * @param linha Numero da linha a ser calculada
	 * @param coluna Numero da coluna a ser calculado
	 * @param regraDireita Simbolo depois do -> lido no arquivo .txt.
	 * @param regraEsquerda Simbolos antes de -> lido no arquivo .txt.
	 */
	public void separarElementos(ArrayList<String> simbolosDiagonais, ArrayList<String> simbolosVertical, String[][] matriz, int linha,
			int coluna,List<String> regraDireita, List<String> regraEsquerda) {
		cont = 0;
		buffer = "";
		/**
		 * Faz a contagem de quantos simbolos possui em uma posicao da matriz
		 * Eles sao separados pelo caracter ':'
		 * Nessa primeira parte e analisado os simbolos na diagonal
		 */
		for (int i = 0; i < simbolosDiagonais.size(); i++) {
			for (int j = 0; j < simbolosDiagonais.get(i).length(); j++) {
				if (simbolosDiagonais.get(i).charAt(j) == ':') {
					cont++;
				}
			}
		}
		/**
		 * Se houver mais de um simbolo é necessario dividi-lo e salvar cada um em uma posicao do simbolosDiagonais_aux
		 */
		if (cont > 0) {
			for (int i = 0; i < simbolosDiagonais.size(); i++) {
				simbolosDiagonais.get(i).replaceFirst(":", "");
				for (int j = 0; j < simbolosDiagonais.get(i).length(); j++) {
					if (simbolosDiagonais.get(i).charAt(j) != ':') {
						buffer = (buffer + simbolosDiagonais.get(i).charAt(j));
					} else {
						simbolosDiagonais_aux.add(buffer.replace(" ", ""));
						buffer = "";
					}
				}
			}
		/**
		 * Caso so tenha um simbolo nao e necessario nenhuma modificacao
		 */
		} else {
			simbolosDiagonais_aux.add(simbolosDiagonais.get(0));
		}
		cont = 0;
		buffer = "";
		
		/**
		 * Faz a contagem de quantos simbolos possui em uma posicao da matriz
		 * Eles sao separados pelo caracter ':'
		 * Nessa segunda parte e analisado os simbolos na vertical
		 */
		for (int i = 0; i < simbolosVertical.size(); i++) {
			for (int j = 0; j < simbolosVertical.get(i).length(); j++) {
				if (simbolosVertical.get(i).charAt(j) == ':') {
					cont++;
				}
			}
		}
		/**
		 * Se houver mais de um simbolo é necessario dividi-lo e salvar cada um em uma posicao do simbolosVertical_aux
		 */
		if (cont > 0) {
			for (int i = 0; i < simbolosVertical.size(); i++) {
				simbolosVertical.get(i).replaceFirst(":", "");
				for (int j = 0; j < simbolosVertical.get(i).length(); j++) {
					if (simbolosVertical.get(i).charAt(j) != ':') {
						buffer = (buffer + simbolosVertical.get(i).charAt(j)).replaceAll(" ", "");
					} else {
						simbolosVertical_aux.add(buffer.replaceAll(" ", ""));
						buffer = "";
					}
				}
			}
		/**
		 * Caso so tenha um simbolo nao e necessario nenhuma modificacao
		 */
		} else {
			simbolosVertical_aux.add(simbolosVertical.get(0));
		}
		cont = 0;
		multiplicarSimbolos(matriz, linha, coluna, regraDireita, regraEsquerda);
		
		/**
		 * Limpa os ArrayList para serem usados no proximo calculo
		 */
		simbolosDiagonais_aux.clear();
		simbolosVertical_aux.clear();
		simbolosDiagonais.clear();
		simbolosVertical.clear();
	}
	
	/**
	 * Multiplica os simbolos da vertical e da diagonal, um de cada vez.
	 * @param matriz Matriz com resultados do CYK
	 * @param linha Numero da linha a ser calculada
	 * @param coluna Numero da coluna a ser calculado
	 * @param regraDireita Simbolo depois do -> lido no arquivo .txt.
	 * @param regraEsquerda Simbolos antes de -> lido no arquivo .txt.
	 */
	public void multiplicarSimbolos(String[][] matriz,
			int linha, int coluna,List<String> regraDireita, List<String> regraEsquerda) {
		for (int i = 0; i < simbolosDiagonais_aux.size(); i++) {
			for (int j = 0; j < simbolosVertical_aux.size(); j++) {
				for (int k = 0; k < regraDireita.size(); k++) {
					if ((simbolosVertical_aux.get(j) + simbolosDiagonais_aux.get(i)).replaceAll(" ", "").equals(regraDireita.get(k))) {
						if (!(matriz[linha][coluna]).contains(regraEsquerda.get(k))) {
							matriz[linha][coluna] = matriz[linha][coluna] + regraEsquerda.get(k) + ":";
						}
					}
				}
			}
		}
	}
	
	/**
	 * Retorna os simbolos das diagonais na matriz CYK
	 * @return the simbolosDiagonais
	 */
	public ArrayList<String> getSimbolosDiagonais() {
		return simbolosDiagonais;
	}

	/**
	 * Retorna os simbolos da vertical na matriz CYK
	 * @return the simbolosVertical
	 */
	public ArrayList<String> getSimbolosVertical() {
		return simbolosVertical;
	}

	/**
	 * Retorna os simbolos da diagonal auxiliar
	 * @return the simbolosDiagonais_aux
	 */
	public ArrayList<String> getSimbolosDiagonais_aux() {
		return simbolosDiagonais_aux;
	}

	/**
	 * Retorna os simbolos da vertical auxiliar
	 * @return the simbolosVertical_aux
	 */
	public ArrayList<String> getSimbolosVertical_aux() {
		return simbolosVertical_aux;
	}
}
