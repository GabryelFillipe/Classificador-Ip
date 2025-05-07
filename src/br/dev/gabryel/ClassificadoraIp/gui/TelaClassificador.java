package br.dev.gabryel.ClassificadoraIp.gui;

import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import br.dev.gabryel.ClassificadoraIp.model.ClassificarIp;

public class TelaClassificador {
	private JLabel labelIp;
	private JLabel labelDivisoria;
	private JTextField textPrimeiroOcteto;
	private JTextField textSegundoOcteto;
	private JTextField textTerceiroOcteto;
	private JTextField textQuartoOcteto;
	private JTextField textCidr;	
	private JButton buttonClassificarIp;
	private JButton buttonLimpar;
	private JLabel labelCidr;
	private JLabel labelIpClass;
	private JLabel labelMascaraDecimal;
	private JLabel labelMascaraBinaria;
	private JLabel labelIpsDisponiveis;
	private JLabel labelMensagemErro;

	public void criarTelaClassificador() {

		// Criando a minha tela.
		JFrame tela = new JFrame();
		tela.setTitle("Classificador de IP");
		tela.setSize(430, 600);
		tela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		tela.setResizable(false);
		tela.setLayout(null);

		// Obtendo a referecia do painel de conteudo da tela.
		Container container = tela.getContentPane();

		Font fonteResultado = new Font("Bold", Font.BOLD, 12);
		Font fonteLabel = new Font("Bold", Font.BOLD, 16);

		// Adicionando o LabelIp
		labelIp = new JLabel();
		labelIp.setText("DIGITE O IP:");
		labelIp.setBounds(20, 30, 300, 50);
		labelIp.setFont(fonteLabel);
		
		labelCidr = new JLabel();
		labelCidr.setText("DIGITE O CIDR AQUI:");
		labelCidr.setBounds(250, 30, 300, 50);
		labelCidr.setFont(fonteLabel);
		
		labelDivisoria = new JLabel();
		labelDivisoria.setText("/");
		labelDivisoria.setBounds(325, 75, 70, 30);
		labelDivisoria.setFont(fonteLabel);

		// Adicionando os campos para preencher os octetos do endereco IP

		textPrimeiroOcteto = new JTextField();
		textPrimeiroOcteto.setBounds(20, 70, 70, 40);
		
		textSegundoOcteto = new JTextField();
		textSegundoOcteto.setBounds(90, 70, 70, 40);
		
		textTerceiroOcteto = new JTextField();
		textTerceiroOcteto.setBounds(160, 70, 70, 40);
		
		textQuartoOcteto = new JTextField();
		textQuartoOcteto.setBounds(230, 70, 70, 40);
		
		textCidr = new JTextField();
		textCidr.setBounds(350, 70, 70, 40);
		
		// Adicionando os Botoes
		buttonClassificarIp = new JButton();
		buttonClassificarIp.setText("Classificar");
		buttonClassificarIp.setBounds(20, 120, 185, 30);

		buttonLimpar = new JButton();
		buttonLimpar.setText("Limpar");
		buttonLimpar.setBounds(215, 120, 185, 30);

		container.add(labelIp);
		container.add(labelCidr);
		container.add(labelDivisoria);
		container.add(textPrimeiroOcteto);
		container.add(textSegundoOcteto);
		container.add(textTerceiroOcteto);
		container.add(textQuartoOcteto);
		container.add(textCidr);
		container.add(buttonClassificarIp);
		container.add(buttonLimpar);

		tela.setVisible(true);
	}

}
