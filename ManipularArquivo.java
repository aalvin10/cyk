

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
/**
 * 
 * @author Alvaro Ferreira
 * @author Deniscley Marfran
 * @author Enio Lemos
 * @version 1.0
 */
public class ManipularArquivo { 
	/**
	 * Armazena simbolos do lado esquerdo a ->
	 */
	private ArrayList<String> regraEsquerda = new ArrayList<>();
	/**
	 * Armazena simbolos do lado direito a ->
	 */
	private ArrayList<String> regraDireita = new ArrayList<>();
	/**
	 * Linha obtida do arquivo texto
	 */
	private String linha = "";
	/**
	 * Parte do arquivo que esta antes do ->
	 */
	private String ladoEsquerdo = "";
	/**
	 * Todo o conteudo apos o simbolo ->
	 */
	private String arqDireito = "";
	/**
	 * Parte de arqDireito que esta entre (->  |) ou entre (|  |).
	 */
	private String ladoDireito = "";
	private int i = 0;
	
	/**
	 * 
	 */
	ManipularArquivo(){
		
	}
	/**
	 * Funcao para manipular arquivo texto
	 * @param arquivoEntrada  Arquivo recebido pelo usuario
	 * @throws IOException Devido a leitura de arquivo
	 */
	public void tratarArquivo(String arquivoEntrada) throws IOException {
		FileInputStream stream = new FileInputStream(arquivoEntrada);
		InputStreamReader reader = new InputStreamReader(stream);
		BufferedReader br = new BufferedReader(reader);
		linha = br.readLine();
		
		/**
		 * Tratamentos sao feitos linha por linha do arquivo
		 */
		
		while (linha != null) {
			ladoEsquerdo = linha.substring(0, linha.indexOf("->"));
			arqDireito = linha.substring(linha.indexOf("->") + 3, linha.length());
			arqDireito = arqDireito.trim();
			arqDireito = arqDireito + "$";
			for (i = 0; i < arqDireito.length(); i++) {
				/**
				 * Verifica se uma variavel produz mais de uma regra
				 */
				if (arqDireito.charAt(i) != '|') {
					ladoDireito = (ladoDireito + arqDireito.charAt(i)).replace(" ", "");
				}
				/**
				 * Caso a variavel produza mais de uma regra, salva em um ArrayList o que já foi obtido ate o momento.
				 * regraEsquerda eh sempre salva com regraDireita, posicao de um elemento em um eh referente a posicao do outro
				 */
				if (arqDireito.charAt(i) == '|') {
					regraEsquerda.add(ladoEsquerdo.replaceAll(" ",""));
					regraDireita.add(ladoDireito.replaceAll(" ",""));
					ladoDireito = "";
				}
				/**
				 * Final da linha eh marcado pelo simbolo $, indica que pode gravar regra no ArrayList.
				 */
				if (arqDireito.charAt(i) == '$') {
					regraEsquerda.add(ladoEsquerdo.replaceAll(" ",""));
					ladoDireito = ladoDireito.replace("$", "");
					regraDireita.add(ladoDireito.replaceAll(" ",""));
					ladoDireito = " ";

				}

			}
			linha = br.readLine();
		}
		br.close();
	}

	/**
	 * Retorna as regras do lado esquerdo
	 * @return the regraEsquerda
	 */
	public ArrayList<String> getRegraEsquerda() {
		return regraEsquerda;
	}

	/**
	 * Retorna as regras do lado direito
	 * @return the regraDireita
	 */
	public ArrayList<String> getRegraDireita() {
		return regraDireita;
	}

}
