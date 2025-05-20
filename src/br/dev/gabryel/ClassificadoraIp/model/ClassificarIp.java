package br.dev.gabryel.ClassificadoraIp.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ClassificarIp {

	private int cidr;
	private String mascaraDecimal;
	private String mascaraBinaria;
	private String classe;
	private String primeiroOcteto;
	private String segundoOcteto;
	private String terceiroOcteto;
	private String quartoOcteto;
	private double ipsPorSubRede;

	public int getCidr() {
		return cidr;
	}

	public void setCidr(int cidr) {
		if (cidr < 0 || cidr > 32) {
			throw new IllegalArgumentException("CIDR inválido: deve estar entre 0 e 32.");// Limita o CIDR a 32
			// Thorw é usado para criar uma execução de erro
		}
		this.cidr = cidr;
	}

	public String getMascaraDecimal() {
		return mascaraDecimal;
	}

	public void setMascaraDecimal(String mascaraDecimal) {
		this.mascaraDecimal = mascaraDecimal;
	}

	public String getMascaraBinaria() {
		return mascaraBinaria;
	}

	public void setMascaraBinaria(String mascaraBinaria) {
		this.mascaraBinaria = mascaraBinaria;
	}

	public int getPrimeiroOcteto() {
		int primeiroOctetoInt = Integer.parseInt(primeiroOcteto);

		return primeiroOctetoInt;
	}

	public void setPrimeiroOcteto(String primeiroOcteto) {
		this.primeiroOcteto = primeiroOcteto;
	}

	public String getClasse() {

		int primeiroOctetoInt = getPrimeiroOcteto();
		if (primeiroOctetoInt >= 1 && primeiroOctetoInt <= 126)
			classe = "A";
		else if (primeiroOctetoInt >= 128 && primeiroOctetoInt <= 191)
			classe = "B";
		else if (primeiroOctetoInt >= 192 && primeiroOctetoInt <= 223)
			classe = "C";
		else if (primeiroOctetoInt >= 224 && primeiroOctetoInt <= 239)
			classe = "D";
		else if (primeiroOctetoInt >= 240 && primeiroOctetoInt <= 255)
			classe = "E";
		else if (primeiroOctetoInt < 1 && primeiroOctetoInt > 255)
			throw new IllegalArgumentException("O endereço IP não pode começar com um numero menor que 1.");// Define que o primeiro octeto não pode ser menor que 1
																											
		return classe;
	}

	public int getSegundoOcteto() {
		int segundoOctetoInt = Integer.parseInt(segundoOcteto);
		return segundoOctetoInt;
	}

	public void setSegundoOcteto(String segundoOcteto) {
		this.segundoOcteto = segundoOcteto;
	}

	public int getTerceiroOcteto() {
		int terceiroOctetoInt = Integer.parseInt(terceiroOcteto);
		return terceiroOctetoInt;
	}

	public void setTerceiroOcteto(String terceiroOcteto) {
		this.terceiroOcteto = terceiroOcteto;
	}

	public int getQuartoOcteto() {
		int quartoOctetoInt = Integer.parseInt(quartoOcteto);
		return quartoOctetoInt;
	}

	public void setQuartoOcteto(String quartoOcteto) {
		this.quartoOcteto = quartoOcteto;
	}

	public double getIpPorSubRede() {
		if (cidr > 32) {
			System.out.println("O CIDR NÃO PODE SER MAIOR QUE 32");
		} else if (cidr < 30) {
			ipsPorSubRede = Math.pow(2, 32 - cidr) - 2;
		} else {
			ipsPorSubRede = Math.pow(2, 32 - cidr); // Para CIDR 30 ou mais, pode ajustar a lógica
		}

		return ipsPorSubRede;
	}

	public StringBuilder gerarMascaraBinaria(int cidr) {
		StringBuilder mascaraBinaria = new StringBuilder();
		for (int i = 0; i < 32; i++) {
			mascaraBinaria.append(i < cidr ? '1' : '0');
//			  if (i < 8)
//		            mascaraBinaria.append(".");
		}
		// ? serve como uma expressão condicional ternária para o if-else.
		return mascaraBinaria;

	}

	public String gerarMascaraDecimal(StringBuilder binaria) {
		// Cria um novo StringBuilder para armazenar o resultado decimal da máscara
		StringBuilder decimal = new StringBuilder();

		// Loop que percorre os 4 octetos (32 bits / 8 bits por octeto = 4)
		for (int i = 0; i < 4; i++) {
			// Extrai 8 bits (1 octeto) da string binária, com base na posição atual
			String octeto = binaria.substring(i * 8, (i + 1) * 8);

			// Converte o octeto binário para decimal e adiciona ao resultado
			decimal.append(Integer.parseInt(octeto, 2));

			// Se não for o último octeto, adiciona um ponto como separador
			if (i < 3)
				decimal.append(".");
		}

		// Converte o StringBuilder final em uma String e retorna a máscara no formato
		// decimal
		return decimal.toString();
	}

	public int getSubRedes() {
	  //String subredes = new String();

	    if (cidr < 30) {
	        double subHosts = Math.pow(cidr, 2) ;
	        int subRedes = (int) subHosts; 
	    } else {
	    	 throw new IllegalArgumentException("CIDR inválido. Deve estar entre 0 e 32.");
	    }

	    return getSubRedes();
	}


	public void mostrarDados() {

		mascaraBinaria = gerarMascaraBinaria(cidr).toString();
		mascaraDecimal = gerarMascaraDecimal(new StringBuilder(mascaraBinaria));
		classe = getClasse();
		double ipsDisponiveis = getIpPorSubRede(); // força o cálculo dos IPs por sub rede

		System.out.println("------------------------------------");
		System.out.println("IP informado: " + primeiroOcteto + "." + segundoOcteto + "." + terceiroOcteto + "."
				+ quartoOcteto + "/" + cidr);
		System.out.println("Primeiro octeto: " + primeiroOcteto);
		System.out.println("Classe do IP: " + "Classe " + classe);
		System.out.println("Máscara binária: " + mascaraBinaria);
		System.out.println("Máscara decimal: " + mascaraDecimal);
		System.out.println("IPs por sub-rede com /" + cidr + ": " + (int) ipsPorSubRede + " IPs disponíveis");
		System.out.println("------------------------------------");

	}

}