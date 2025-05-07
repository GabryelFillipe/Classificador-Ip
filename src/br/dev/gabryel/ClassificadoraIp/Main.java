package br.dev.gabryel.ClassificadoraIp;

import java.util.Scanner;

import br.dev.gabryel.ClassificadoraIp.gui.TelaClassificador;
import br.dev.gabryel.ClassificadoraIp.model.ClassificarIp;

public class Main {

	public static void main(String[] args) {

		TelaClassificador tela = new TelaClassificador();
		tela.criarTelaClassificador();

		ClassificarIp ci = new ClassificarIp();

		// Tentando setar o CIDR com controle de exceção
		try {
			ci.setCidr(31); // Definindo o CIDR aqui (pode ser alterado para testar)
		} catch (IllegalArgumentException e) {
			System.out.println("Erro ao definir o CIDR: " + e.getMessage());
			return; // Interrompe a execução do programa se o CIDR for inválido.
		}

		// Definindo outros parâmetros para teste
		ci.setPrimeiroOcteto("192");
		ci.setSegundoOcteto("168");
		ci.setTerceiroOcteto("1");
		ci.setQuartoOcteto("100");

		// Mostra os dados com base no CIDR e IP definidos
		ci.mostrarDados();

	}

}
