package br.dev.gabryel.ClassificadoraIp.gui;

import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class TelaClassificador {
	private JTextField textIp;
	private JTextField textCidr;
	private JButton buttonClassificarIp;
	private JButton buttonLimpar;
	private JLabel labelMensagemErro;
	private JLabel labelIp;
	private JLabel labelCidr;
	private JLabel labelIpClass;
	private JLabel labelMascaraDecimal;
	private JLabel labelMascaraBinaria;
	private JLabel labelIpsDisponiveis;
	
	public void criarTelaClassificador(){
		
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
					
					textIp = new JTextField();
//					textIp.setText("Digite o IP");
					textIp.setBounds(20, 70, 390, 30);
					
					
					
					textCidr = new JTextField();
//					textCidr.setText("Digite o CIDR");
					textCidr.setBounds(20, 110, 390, 30);
					
					
					
					
					buttonClassificarIp = new JButton();
					buttonClassificarIp.setText("Classificar");
					buttonClassificarIp.setBounds(20, 150, 185, 30);
					
					buttonClassificarIp.addActionListener(new ActionListener() {
						
						@Override
						public void actionPerformed(ActionEvent e) {
							
							String textip = textIp+"textIp";
							labelIp.setText();
							
						}
					});
					
					
					buttonLimpar = new JButton();
					buttonLimpar.setText("Limpar");
					buttonLimpar.setBounds(215, 150, 185, 30);
					
					buttonLimpar.addActionListener(new ActionListener() {
						
						@Override
						public void actionPerformed(ActionEvent e) {
							 
						labelIp.setText(null);
						}
					});
					
					labelIp = new JLabel();
					labelIp.setBounds(20, 180, 250, 50);
					labelIp.setFont(fonteResultado);
					
					
					
					
					
					
					
					container.add(textCidr);
					container.add(textIp);
					container.add(buttonClassificarIp);
					container.add(buttonLimpar);
					container.add(labelIp);
					
					
					
					
					
					tela.setVisible(true);
	}
	
}
