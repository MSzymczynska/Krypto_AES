package Kryptografia.krypto_AES;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JTextPane;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.UIManager;

import java.awt.Color;
import javax.swing.JTextArea;

public class MainPanel extends JFrame { 
	
	private FunctionsAES functions = new FunctionsAES();
	
	String text;
	String key;
	
	public MainPanel() {
		
		getContentPane().setLayout(null);
		setSize(500, 500);
	    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    
	    try{
			UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
		}
		catch(Exception e){
			e.printStackTrace();
		}
	    
		JPanel panel = new JPanel();
		panel.setBounds(0, 0, 484, 462);
		getContentPane().add(panel);
		panel.setLayout(null);
		
		JTextArea userText = new JTextArea();
		userText.setBounds(33, 51, 411, 131);
		panel.add(userText);
		
		JLabel lblWpiszTekstDo = new JLabel("Wpisz tekst do zaszyfrowania");
		lblWpiszTekstDo.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblWpiszTekstDo.setBounds(33, 11, 301, 29);
		panel.add(lblWpiszTekstDo);
		
		JButton btnZaszyfruj = new JButton("Zaszyfruj");
		btnZaszyfruj.addActionListener(new ActionListener() {
			//---------------szyfrowanie-------------------
			public void actionPerformed(ActionEvent e) {
				
				//pobieranie tekstu wprowadzeonego przez uzytkownika
				text=userText.getText();
				//ustawiam jakis klucz na razie tutaj, potem mozemy przez GUI
				key="1a25s8fe5dsg65ad";
				//kodujemy
				functions.encode(text.getBytes(),key.getBytes() );			
			}
		});
		
		btnZaszyfruj.setBounds(33, 193, 89, 23);
		panel.add(btnZaszyfruj);
		
		JLabel lblWynik = new JLabel("Wynik:");
		lblWynik.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblWynik.setBounds(33, 233, 142, 23);
		panel.add(lblWynik);
		
		JButton btnOdszyfruj = new JButton("Odszyfruj");
		btnOdszyfruj.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				//odszyfrowanie
			}
		});
		btnOdszyfruj.setBounds(149, 193, 89, 23);
		panel.add(btnOdszyfruj);
		
		JLabel label = new JLabel("...");
		label.setForeground(Color.BLACK);
		label.setVerticalAlignment(SwingConstants.TOP);
		label.setBounds(33, 267, 411, 151);
		panel.add(label);
	}
	
	
	
}
