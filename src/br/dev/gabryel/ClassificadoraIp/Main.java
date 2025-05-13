package br.dev.gabryel.ClassificadoraIp;

import java.util.Scanner;

import br.dev.gabryel.ClassificadoraIp.gui.TelaClassificador;
import br.dev.gabryel.ClassificadoraIp.model.ClassificarIp;

public class Main {

	public static void main(String[] args) {

		TelaClassificador tela = new TelaClassificador();
		tela.criarTelaClassificador();

		ClassificarIp ci = new ClassificarIp();

	}

}
