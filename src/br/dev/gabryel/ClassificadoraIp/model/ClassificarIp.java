package br.dev.gabryel.ClassificadoraIp.model;

import java.util.ArrayList;
import java.util.List;
import java.lang.Math;

public class ClassificarIp {
	private String primeiroOcteto;
	private String segundoOcteto;
	private String terceiroOcteto;
	private String quartoOcteto;
	private int cidr;
	private int subRede;

	
	public ClassificarIp() {
		
	}


	public String getPrimeiroOcteto() {
		return primeiroOcteto;
	}

	public void setPrimeiroOcteto(String primeiroOcteto) {
		this.primeiroOcteto = primeiroOcteto;
	}

	public String getSegundoOcteto() {
		return segundoOcteto;
	}

	public void setSegundoOcteto(String segundoOcteto) {
		this.segundoOcteto = segundoOcteto;
	}

	public String getTerceiroOcteto() {
		return terceiroOcteto;
	}

	public void setTerceiroOcteto(String terceiroOcteto) {
		this.terceiroOcteto = terceiroOcteto;
	}

	public String getQuartoOcteto() {
		return quartoOcteto;
	}

	public void setQuartoOcteto(String quartoOcteto) {
		this.quartoOcteto = quartoOcteto;
	}

	public int getCidr() {
		return cidr;
	}

	public void setCidr(int cidr) {
		this.cidr = cidr;
	}


	public String getClasse() {
		int primeiroOctetoInt = Integer.parseInt(primeiroOcteto);
		if (primeiroOctetoInt >= 1 && primeiroOctetoInt <= 126)
			return "A";
		if (primeiroOctetoInt >= 128 && primeiroOctetoInt <= 191)
			return "B";
		if (primeiroOctetoInt >= 192 && primeiroOctetoInt <= 223)
			return "C";
		if (primeiroOctetoInt >= 224 && primeiroOctetoInt <= 239)
			return "D (Multicast)";
		if (primeiroOctetoInt >= 240 && primeiroOctetoInt <= 255)
			return "E (Experimental)";
		return "Desconhecida";
	}

	public StringBuilder gerarMascaraBinaria(int cidr) {
		StringBuilder mascaraBinaria = new StringBuilder();
		for (int i = 0; i < 32; i++) {
			if (i < cidr) {
				mascaraBinaria.append("1");
			} else {
				mascaraBinaria.append("0");
			}
			if ((i + 1) % 8 == 0 && i < 31) {
				mascaraBinaria.append(".");
			}
		}
		return mascaraBinaria;
	}

	public String gerarMascaraDecimal(StringBuilder mascaraBinaria) {
		String[] octetosBinarios = mascaraBinaria.toString().split("\\.");
		StringBuilder mascaraDecimal = new StringBuilder();
		for (String octetoBinario : octetosBinarios) {
			mascaraDecimal.append(Integer.parseInt(octetoBinario, 2));
			if (mascaraDecimal.length() < 12) { // Evita adicionar '.' no final
				mascaraDecimal.append(".");
			}
		}
		return mascaraDecimal.toString();
	}

	public int getIpsTotal() {
		return (int) Math.pow(2, (32 - this.cidr));
	}

	public double getIpPorSubRede() {
		int bitsHost = 32 - this.cidr;
		if (bitsHost <= 1) { // Para /31 e /32, não há IPs utilizáveis no sentido tradicional (rede e broadcast)
			return Math.pow(2, bitsHost);
		}
		return Math.pow(2, bitsHost) - 2;
	}


	public List<String> gerarDetalhesSubRedes() {
		List<String> resultadosSubRede = new ArrayList<>();

		// Adaptação para CIDR <= 24
		if (this.cidr <= 24) {
			long totalIps = (long) Math.pow(2, (32 - this.cidr));
			long ipsUtilizaveis = totalIps;
			if (this.cidr <= 30) { // Para CIDR <= /30, normalmente subtraímos 2 (rede e broadcast)
				ipsUtilizaveis = totalIps - 2;
			}
			if (this.cidr == 31 || this.cidr == 32) { // Casos específicos para /31 e /32
				ipsUtilizaveis = totalIps;
			}

			// Calcular o endereço de rede
			int[] ipOctetos = { Integer.parseInt(primeiroOcteto), Integer.parseInt(segundoOcteto),
					Integer.parseInt(terceiroOcteto), Integer.parseInt(quartoOcteto) };

			// Mascara para os octetos (ex: para /16, a máscara é 255.255.0.0)
			int[] mascaraDecimal = new int[4];
			StringBuilder mascaraBinariaCompleta = gerarMascaraBinaria(this.cidr);
			String[] octetosMascaraBin = mascaraBinariaCompleta.toString().split("\\.");
			for (int i = 0; i < 4; i++) {
				mascaraDecimal[i] = Integer.parseInt(octetosMascaraBin[i], 2);
			}

			StringBuilder ipRedeBuilder = new StringBuilder();
			for (int i = 0; i < 4; i++) {
				ipRedeBuilder.append(ipOctetos[i] & mascaraDecimal[i]);
				if (i < 3)
					ipRedeBuilder.append(".");
			}
			String ipRede = ipRedeBuilder.toString();

			// Calcular o endereço de broadcast
			StringBuilder ipBroadcastBuilder = new StringBuilder();
			for (int i = 0; i < 4; i++) {
				ipBroadcastBuilder.append(ipOctetos[i] | (~mascaraDecimal[i] & 0xFF)); 
				if (i < 3)
					ipBroadcastBuilder.append(".");
			}
			String ipBroadcast = ipBroadcastBuilder.toString();

			// Calcular o primeiro e último host
			String primeiroHost = "N/A";
			String ultimoHost = "N/A";

			if (ipsUtilizaveis > 0) {
				// Para o primeiro host, basta adicionar 1 ao IP de rede no último octeto,
				// se o IP de rede terminar em .0, e adaptar para outros octetos se necessário.
			

				String[] redeParts = ipRede.split("\\.");
				String[] broadcastParts = ipBroadcast.split("\\.");

				// Primeiro Host
				int lastOctetRede = Integer.parseInt(redeParts[3]);
				if (this.cidr < 32) { // Se não for um host único
					if (lastOctetRede < 255) { // Se o último octeto de rede não for 255
						primeiroHost = redeParts[0] + "." + redeParts[1] + "." + redeParts[2] + "."
								+ (lastOctetRede + 1);
					} else {
					
						primeiroHost = "Verificar"; // Indicar que é mais complexo
					}
				} else { // Para /32, o IP de rede é o próprio host
					primeiroHost = ipRede;
				}

				// Último Host
				int ultimoOctetoBroadcast = Integer.parseInt(broadcastParts[3]);
				if (this.cidr < 32) { // Se não for um host único
					if (ultimoOctetoBroadcast > 0) { // Se o último octeto de broadcast não for 0
						ultimoHost = broadcastParts[0] + "." + broadcastParts[1] + "." + broadcastParts[2] + "."
								+ (ultimoOctetoBroadcast - 1);
					} else { 
						ultimoHost = "Verificar"; // Indicar que é mais complexo
					}
				} else { // Para /32, o IP de broadcast é o próprio host
					ultimoHost = ipBroadcast;
				}
			}

			resultadosSubRede.add("--- Detalhes da Rede Principal ---");
			resultadosSubRede.add(String.format("CIDR: /%d", this.cidr));
			resultadosSubRede.add(String.format("Total de IPs na rede: %d", totalIps));
			resultadosSubRede.add(String.format("IPs utilizáveis: %d", ipsUtilizaveis));
			resultadosSubRede.add(String.format("IP da Rede: %s", ipRede));
			resultadosSubRede.add(String.format("Primeiro Host: %s", primeiroHost));
			resultadosSubRede.add(String.format("Último Host: %s", ultimoHost));
			resultadosSubRede.add(String.format("IP de Broadcast: %s", ipBroadcast));

			return resultadosSubRede;
		}

		// Lógica para CIDR > 24
		if (this.cidr > 24) {
			int quantidadeBitsHostRestantes = 32 - this.cidr;
			int bitsEmprestadosUltimoOcteto = 8 - quantidadeBitsHostRestantes;

			int totalSubRedesGeradas = (int) Math.pow(2, bitsEmprestadosUltimoOcteto);
			int ipsUtilizaveisNaSubRedeMenor;
			if (this.cidr < 30) {
				ipsUtilizaveisNaSubRedeMenor = ((int) Math.pow(2, quantidadeBitsHostRestantes)) - 2;
			} else {
				ipsUtilizaveisNaSubRedeMenor = (int) Math.pow(2, quantidadeBitsHostRestantes);
			}

			int[] valoresBinariosOcteto = { 128, 64, 32, 16, 8, 4, 2, 1 };
			int incrementoOctetoFinal = 0;

			for (int i = 0; i < bitsEmprestadosUltimoOcteto; i++) {
				incrementoOctetoFinal += valoresBinariosOcteto[i];
			}
			incrementoOctetoFinal = 256 - incrementoOctetoFinal;

			resultadosSubRede.add("--- Detalhes das Sub-redes geradas (base /24 para o último octeto) ---");
			resultadosSubRede.add("Há " + String.valueOf(totalSubRedesGeradas) + " sub-redes de tamanho /" + this.cidr + ".");
			resultadosSubRede.add("Cada sub-rede tem " + ipsUtilizaveisNaSubRedeMenor + " IPs utilizáveis.");
		

			int octetoInicialSubRede = 0;
			String prefixoIpRede = primeiroOcteto + "." + segundoOcteto + "." + terceiroOcteto + ".";

			for (int i = 1; i <= totalSubRedesGeradas; i++) {

				String ipRede = prefixoIpRede + octetoInicialSubRede;

				int primeiroHost = octetoInicialSubRede + 1;
				int ultimoHost = octetoInicialSubRede + incrementoOctetoFinal - 2;

				int ipBroadcast = octetoInicialSubRede + incrementoOctetoFinal - 1;

				String hostsFormatado;
				if (this.cidr == 31) {
					hostsFormatado = prefixoIpRede + octetoInicialSubRede + " - " + prefixoIpRede
							+ (octetoInicialSubRede + 1);
				} else if (this.cidr == 32) {
					hostsFormatado = prefixoIpRede + octetoInicialSubRede + " - " + prefixoIpRede
							+ octetoInicialSubRede;
				} else if (ipsUtilizaveisNaSubRedeMenor == 0) {
					hostsFormatado = "Nenhum";
				} else {
					hostsFormatado = prefixoIpRede + primeiroHost + " - " + prefixoIpRede + ultimoHost;
				}

				if (this.cidr == 31) {
					ipBroadcast = octetoInicialSubRede + 1;
				} else if (this.cidr == 32) {
					ipBroadcast = octetoInicialSubRede;
				}

				 resultadosSubRede.add(String.format(
		                    "Sub-rede = %d,\n" +
		                    "IP de rede = %s,\n" +
		                    "Intervalo de hosts = %s,\n" +
		                    "IP de broadcast = %s\n" +
		                    "--------------------------------------------------------------------------------------------", 
		                    i, 
		                    ipRede, 
		                    hostsFormatado, 
		                    prefixoIpRede + ipBroadcast));

				octetoInicialSubRede += incrementoOctetoFinal;
			}
		}
		return resultadosSubRede;
	}
	public int getSubRede(){
		int quantidadeBitsHostRestantes = 32 - this.cidr;
		int bitsEmprestadosUltimoOcteto = 8 - quantidadeBitsHostRestantes;

		int subRede = (int) Math.pow(2, bitsEmprestadosUltimoOcteto);
		return subRede;
	}
}