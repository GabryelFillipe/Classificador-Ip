package br.dev.gabryel.ClassificadoraIp;

import java.util.Scanner;

import br.dev.gabryel.ClassificadoraIp.gui.TelaClassificador;
import br.dev.gabryel.ClassificadoraIp.model.ClassificarIp;


public class Main {

	public static void main(String[] args) {
		
		TelaClassificador tela = new TelaClassificador();
		tela.criarTelaClassificador();

		Scanner scanner = new Scanner(System.in);
		boolean continuar = true;

		while (continuar) {
			ClassificarIp ci = new ClassificarIp();

			ci.lerIpDoUsuario();
			ci.mostrarDados();

			continuar = ci.continuar(scanner, "Usuário");
		}

		System.out.println("Programa finalizado.");
		scanner.close();
	}
}
