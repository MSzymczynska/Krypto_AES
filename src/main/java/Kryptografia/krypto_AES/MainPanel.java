package Kryptografia.krypto_AES;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JTextPane;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.awt.event.ActionEvent;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.UIManager;

import java.awt.Color;
import javax.swing.JTextArea;

public class MainPanel extends JFrame {

	private FunctionsAES functions = new FunctionsAES();
	
	private JTextArea userText = new JTextArea();
	private JLabel lblResulText = new JLabel("");

	String text = "";
	String key = "";
	byte[] result;

	public MainPanel() {

		getContentPane().setLayout(null);
		setSize(500, 500);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		try {
			UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
		} catch (Exception e) {
			e.printStackTrace();
		}

		JPanel panel = new JPanel();
		panel.setBounds(0, 0, 484, 462);
		getContentPane().add(panel);
		panel.setLayout(null);

		
		userText.setBounds(33, 51, 411, 131);
		panel.add(userText);

		JLabel lblWpiszTekstDo = new JLabel("Wpisz tekst do zaszyfrowania lub");
		lblWpiszTekstDo.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblWpiszTekstDo.setBounds(33, 11, 301, 29);
		panel.add(lblWpiszTekstDo);

		JButton btnZaszyfruj = new JButton("Zaszyfruj");
		// TODO Nie kumam jednej rzeczy, a mianowicie:
		//czemu nie wchodzi mi w tą funkcję jeśli plik nie jest wybrany i nic nie jest wpisane
		//a przecież nigdzie tego nie sprawdzamy
		btnZaszyfruj.addActionListener(new ActionListener() {
			// ---------------szyfrowanie-------------------
			public void actionPerformed(ActionEvent e) {;
				if (text == "" && userText.getText() == null) {
					System.out.println("Choose file.");
					JOptionPane.showMessageDialog(panel, "Brak tekstu do przetworzenia. Wpisz tekst lub wybierz plik.",
							"Brak tekstu do przetworzenia", JOptionPane.ERROR_MESSAGE);
				} else {
					// pobieranie tekstu wprowadzeonego przez uzytkownika
					text = userText.getText();

					// ustawiam jakis klucz na razie tutaj, potem mozemy przez
					// GUI
					key = "1a25s8fe5dsg65ad";
					// kodujemy
					result = functions.encode(text.getBytes(), key.getBytes());
					lblResulText.setText(new String(result));
				}
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

				// odszyfrowanie
			}
		});
		btnOdszyfruj.setBounds(149, 193, 89, 23);
		panel.add(btnOdszyfruj);

		
		lblResulText.setForeground(Color.BLACK);
		lblResulText.setVerticalAlignment(SwingConstants.TOP);
		lblResulText.setBounds(33, 267, 411, 151);
		panel.add(lblResulText);

		JButton btnWybierzPlik = new JButton("Wybierz plik");
		btnWybierzPlik.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
				int result = fileChooser.showDialog(panel, "Wybierz plik");
				if (result == JFileChooser.APPROVE_OPTION) {
					File selectedFile = fileChooser.getSelectedFile();
					System.out.println("Selected file: " + selectedFile.getAbsolutePath());
					String filePath = String.valueOf(fileChooser.getSelectedFile());
					readFile(filePath);
				}

			}
		});
		btnWybierzPlik.setBounds(329, 11, 115, 23);
		panel.add(btnWybierzPlik);
	}
	
	private void readFile(String filePath) {
		try {
			BufferedReader in = new BufferedReader(new FileReader(filePath));
			String line;
			text = "";
			while ((line = in.readLine()) != null) {
				text += line + " ";
				userText.setText(text);
			}
			in.close();
			System.out.println(text);
		} catch (FileNotFoundException e) {
			System.out.println("There is no such file.");
			JOptionPane.showMessageDialog(this, "Nie ma takiego pliku.", "Brak pliku",
					JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("Error during processing file.");
			JOptionPane.showMessageDialog(this, "Błąd podczas przetwarzania pliku.", "Błąd",
					JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
	}
}
