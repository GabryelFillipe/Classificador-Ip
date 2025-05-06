package br.dev.gabryel.ClassificadoraIp.model;


import java.util.Scanner;

public class ClassificarIp {

	private String ip;
	private int cidr;
	private int primeiroOcteto;
	private StringBuilder mascaraBinaria;
	private String mascaraDecimal;
	private String ipClass;

	public void classificarIp() {

		if (primeiroOcteto >= 1 && primeiroOcteto <= 126)
			ipClass = "A";
		else if (primeiroOcteto >= 128 && primeiroOcteto <= 191)
			ipClass = "B";
		else if (primeiroOcteto >= 192 && primeiroOcteto <= 223)
			ipClass = "C";
		else if (primeiroOcteto >= 224 && primeiroOcteto <= 239)
			ipClass = "D";
		else
			ipClass = "E";

	}

	private StringBuilder gerarMascaraBinaria(int cidr) {
		StringBuilder bin = new StringBuilder();
		for (int i = 0; i < 32; i++) {
			bin.append(i < cidr ? '1' : '0');
		}
		return bin;
	}

	private String gerarMascaraDecimal(StringBuilder binaria) {
		StringBuilder decimal = new StringBuilder();
		for (int i = 0; i < 4; i++) {
			String octeto = binaria.substring(i * 8, (i + 1) * 8);
			decimal.append(Integer.parseInt(octeto, 2));
			if (i < 3)
				decimal.append(".");
		}
		return decimal.toString();
	}

	public void lerIpDoUsuario() {
		Scanner scanner = new Scanner(System.in);
		System.out.print("Digite o IP com CIDR (ex: 192.168.0.0/24): ");
		String input = scanner.nextLine();
		setIp(input);
		

	}

	public void setIp(String input) {
		this.ip = input;
		String[] partes = input.split("/");

		if (partes.length != 2) {
			System.out.println("Formato inválido. Use o formato xxx.xxx.xxx.xxx/yy siga minhas regras!!!!");
			return;
		}
		String ipParte = partes[0];
		this.cidr = Integer.parseInt(partes[1]);

		String[] octetos = ipParte.split("\\.");
		if (octetos.length != 4) {
			System.out.println("IP inválido.");
			return;
		}
		this.primeiroOcteto = Integer.parseInt(octetos[0]);
		classificarIp();
		this.mascaraBinaria = gerarMascaraBinaria(this.cidr);
		this.mascaraDecimal = gerarMascaraDecimal(this.mascaraBinaria);

	}

	public void mostrarDados() {
		
		System.out.println("------------------------------------");
		System.out.println("IP informado: " + ip);
		System.out.println("Primeiro octeto: " + primeiroOcteto);
		System.out.println("Classe do IP: " + ipClass);
		System.out.println("Máscara binária: " + mascaraBinaria);
		System.out.println("Máscara decimal: " + mascaraDecimal);
		System.out.println("------------------------------------");
		//DIvidindo as sub-redes
		if (cidr < 30) {
			 // Verifica se o CIDR (Classless Inter-Domain Routing) da rede original é menor que 30.
			 // Um CIDR menor que 30 indica que há bits suficientes na porção de host para criar sub-redes maiores.
			 // Um CIDR de 30 já resulta em apenas 2 hosts utilizáveis, sendo o limite prático para sub-redes ponto a ponto.

			 System.out.println("Ips de sub-redes:");

			 int subHosts = (int) Math.pow(2, 32 - cidr) - 2;
			 System.out.println( cidr + ": " + subHosts + " Ips disponiveis");
			 
			 
		}
	}

	// Looping
	public boolean continuar(Scanner leitor, String nome) {
		String resposta = "";

		while (!resposta.equalsIgnoreCase("s") && !resposta.equalsIgnoreCase("n")) {
			System.out.print(nome + ", digite S para continuar ou N para sair... ");
			resposta = leitor.next();
		}

		return resposta.equalsIgnoreCase("s");
	}
}
