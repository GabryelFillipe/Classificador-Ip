package br.dev.gabryel.ClassificadoraIp.gui;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;
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
	private JTextArea jtextSugestoesSubredes;
	private JLabel labelIpsDisponiveis;
	private JLabel labelMensagemErro;

	public void criarTelaClassificador() {

		// Criando a minha tela.
		JFrame tela = new JFrame();
		tela.setTitle("Classificador de IP");
		tela.setSize(450, 450);
		tela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		tela.setResizable(false);
		tela.setLayout(null);

		// Obtendo a referecia do painel de conteudo da tela.
		Container container = tela.getContentPane();

		Font fonteResultado = new Font("Bold", Font.BOLD, 14);
		Font fonteLabel = new Font("Bold", Font.BOLD, 16);
		Font fonteErro = new Font("Bold", Font.BOLD, 18);

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
		
		jtextSugestoesSubredes = new JTextArea();
		jtextSugestoesSubredes.setBounds(20, 290, 380, 100);
		jtextSugestoesSubredes.setEditable(false);
		jtextSugestoesSubredes.setLineWrap(true); // Quebra automática
		jtextSugestoesSubredes.setWrapStyleWord(true); // Quebra em palavras
		jtextSugestoesSubredes.setFont(fonteResultado);

		
		
		// Adicionando os Botoes
		buttonClassificarIp = new JButton();
		buttonClassificarIp.setText("Classificar");
		buttonClassificarIp.setBounds(20, 120, 185, 30);
		
		buttonClassificarIp.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					labelMensagemErro.setVisible(false);

					// Pega os valores digitados pelo usuario
					String primeiro = textPrimeiroOcteto.getText();
					String segundo = textSegundoOcteto.getText();
					String terceiro = textTerceiroOcteto.getText();
					String quarto = textQuartoOcteto.getText();
					int cidr = Integer.parseInt(textCidr.getText());

					//Converte tudo para INT
					int primeiroInt = Integer.parseInt(primeiro);
				    int segundoInt = Integer.parseInt(segundo);
				    int terceiroInt = Integer.parseInt(terceiro);
				    int quartoInt = Integer.parseInt(quarto);

				    
					// Validação dos octetos
				    if (primeiroInt < 0 || primeiroInt > 255 ||
				        segundoInt < 0 || segundoInt > 255 ||
				        terceiroInt < 0 || terceiroInt > 255 ||
				        quartoInt < 0 || quartoInt > 255) {
				        throw new IllegalArgumentException("Cada octeto deve estar entre 0 e 255.");
				    }

				    if (cidr < 0 || cidr > 32) {
				        throw new IllegalArgumentException("CIDR inválido. Deve estar entre 0 e 32.");
				    }
					//  Pega as informações do IP
					ClassificarIp classificar = new ClassificarIp();
					classificar.setPrimeiroOcteto(primeiro);
					classificar.setSegundoOcteto(segundo);
					classificar.setTerceiroOcteto(terceiro);
					classificar.setQuartoOcteto(quarto);
					classificar.setCidr(cidr);

					// Gera os dados do IP
					String classe = classificar.getClasse();
					String mascaraBinaria = classificar.gerarMascaraBinaria(cidr).toString();
					String mascaraDecimal = classificar.gerarMascaraDecimal(new StringBuilder(mascaraBinaria));
					int ips = (int) classificar.getIpPorSubRede();
					
					// Esse método retorna uma lista com sugestões de sub-redes baseadas no CIDR informado
					List<String> sugestoes = classificar.calcularSubRedes();
					StringBuilder builder = new StringBuilder("Sugestões de Sub-redes:\n");
					for (String sugestao : sugestoes) {
					    builder.append(sugestao).append("\n");
					}
					jtextSugestoesSubredes.setText(builder.toString());
					jtextSugestoesSubredes.setVisible(true); // <-- Isso é essencial aqui
					jtextSugestoesSubredes.repaint();


					// Mostra as informações geradas
					labelIpClass.setText("Classe do IP: " + classe);
					labelMascaraBinaria.setText("Máscara Binária: " + mascaraBinaria);
					labelMascaraDecimal.setText("Máscara Decimal: " + mascaraDecimal);
					labelIpsDisponiveis.setText("IPs disponíveis: " + ips);

					labelIpClass.setVisible(true);
					labelMascaraBinaria.setVisible(true);
					labelMascaraDecimal.setVisible(true);
					jtextSugestoesSubredes.setVisible(true);
					labelIpsDisponiveis.setVisible(true);

				} catch (Exception ex) {
					labelIpClass.setVisible(false);
					labelMascaraBinaria.setVisible(false);
					labelMascaraDecimal.setVisible(false);
					labelIpsDisponiveis.setVisible(false);
					jtextSugestoesSubredes.setVisible(false);

					labelMensagemErro.setBounds(50, 160, 370, 90);
					labelMensagemErro.setText("<html><body style='width: 250px'>Erro ao processar. Verifique se todos os campos foram preenchidos corretamente com números válidos.");
					labelMensagemErro.setVisible(true);
					textCidr.setText(null);
					textPrimeiroOcteto.setText(null);
					textQuartoOcteto.setText(null);
					textSegundoOcteto.setText(null);
					textTerceiroOcteto.setText(null);
				}
			}
		});


		buttonLimpar = new JButton();
		buttonLimpar.setText("Limpar");
		buttonLimpar.setBounds(215, 120, 185, 30);
		
		
		buttonLimpar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				labelIpClass.setVisible(false);
				labelMascaraBinaria.setVisible(false);
				labelMascaraDecimal.setVisible(false);
				labelIpsDisponiveis.setVisible(false);
				textPrimeiroOcteto.setText(null);
				textQuartoOcteto.setText(null);
				textSegundoOcteto.setText(null);
				textTerceiroOcteto.setText(null);
				textCidr.setText(null);
				
			}
		});
		
		
		labelIpClass = new JLabel();
		labelIpClass.setBounds(20, 170, 200, 50);
		labelIpClass.setFont(fonteResultado);
		

		
		
		labelMascaraDecimal = new JLabel();
		labelMascaraDecimal.setBounds(20, 190, 350, 50);
		labelMascaraDecimal.setFont(fonteResultado);
		
		labelMascaraBinaria = new JLabel();
		labelMascaraBinaria.setBounds(20, 210, 430, 50);
		labelMascaraBinaria.setFont(fonteResultado);
		
		labelIpsDisponiveis = new JLabel();
		labelIpsDisponiveis.setBounds(20, 230, 350, 50);
		labelIpsDisponiveis.setFont(fonteResultado);
		
		labelMensagemErro = new JLabel();
		labelMensagemErro.setForeground(Color.red);
		labelMensagemErro.setFont(fonteErro);
		//labelMensagemErro.setBounds(20, 250, 100, 50);
		

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
		container.add(labelIpClass);
		container.add(labelMascaraDecimal);
		container.add(labelMascaraBinaria);
		container.add(labelIpsDisponiveis);
		container.add(jtextSugestoesSubredes);
		container.add(labelMensagemErro);

		tela.setVisible(true);
	}

}
