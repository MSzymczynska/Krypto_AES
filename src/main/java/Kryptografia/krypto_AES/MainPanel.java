package Kryptografia.krypto_AES;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JTextPane;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.awt.event.ActionEvent;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.UIManager;

import java.awt.Color;
import javax.swing.JTextArea;
import javax.swing.JRadioButton;
import javax.swing.JComboBox;

public class MainPanel extends JFrame {

	private FunctionsAESEncrypt functionsEncrypt = new FunctionsAESEncrypt();
	private FunctionsAESDecrypt functionsDecrypt = new FunctionsAESDecrypt();

	private JTextArea userText = new JTextArea();
	private JLabel lblResulText = new JLabel("");
	private JTextField txtNazwapliku;
	private JRadioButton rdbtnWpiszKlucz;
	private JRadioButton rdbtnWybierzKlucz;
	private JComboBox comboBoxKeys;

	String text = "";
	String key = "";
	byte[] result;
	private JTextField txtKey;

	public MainPanel() {

		getContentPane().setLayout(null);
		setSize(500, 555);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		try {
			UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
		} catch (Exception e) {
			e.printStackTrace();
		}

		JPanel panel = new JPanel();
		panel.setBounds(0, 0, 484, 517);
		getContentPane().add(panel);
		panel.setLayout(null);

		userText.setBounds(33, 51, 411, 117);
		panel.add(userText);

		JLabel lblWpiszTekstDo = new JLabel("Wpisz tekst do zaszyfrowania lub");
		lblWpiszTekstDo.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblWpiszTekstDo.setBounds(33, 11, 301, 29);
		panel.add(lblWpiszTekstDo);

		JButton btnZaszyfruj = new JButton("Zaszyfruj");
		
		// TODO Nie kumam jednej rzeczy, a mianowicie:
		// czemu nie wchodzi mi w tą funkcję jeśli plik nie jest wybrany i nic
		// nie jest wpisane
		// a przecież nigdzie tego nie sprawdzamy
		btnZaszyfruj.addActionListener(new ActionListener() {
			// ---------------szyfrowanie-------------------
			public void actionPerformed(ActionEvent e) {
				// if(rdbtnWpiszKlucz.isSelected()) {
				// checkKey(txtKey.getText());
				// }
				// key = txtKey.getText();
				// }
				if (rdbtnWybierzKlucz.isSelected())
					key = String.valueOf(comboBoxKeys.getSelectedItem());
				else
					key = txtKey.getText();

				if (text == "" && userText.getText() == null) {
					System.out.println("Choose file to encrypt or write text.");
					JOptionPane.showMessageDialog(panel, "Brak tekstu do przetworzenia. Wpisz tekst lub wybierz plik.",
							"Brak tekstu do przetworzenia", JOptionPane.ERROR_MESSAGE);
				} else {
					// pobieranie tekstu wprowadzeonego przez uzytkownika
					text = userText.getText();

					// ustawiam jakis klucz na razie tutaj, potem mozemy przez
					// GUI
					// key = "1a25s8fe5dsg65ad";
					// kodujemy
					if (checkKey(key)) {
						result = functionsEncrypt.encode(text.getBytes(), key.getBytes());
						lblResulText.setText(new String(result));
					}
				}
			}
		});

		btnZaszyfruj.setBounds(33, 241, 89, 23);
		panel.add(btnZaszyfruj);

		JLabel lblWynik = new JLabel("Wynik:");
		lblWynik.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblWynik.setBounds(33, 285, 142, 23);
		panel.add(lblWynik);

		JButton btnOdszyfruj = new JButton("Odszyfruj");
		btnOdszyfruj.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (text == "" && userText.getText() == null) {
					System.out.println("Choose file to decrypt or write message.");
					JOptionPane.showMessageDialog(panel, "Brak tekstu do przetworzenia. Wpisz tekst lub wybierz plik.",
							"Brak tekstu do przetworzenia", JOptionPane.ERROR_MESSAGE);
				} else {
					text = userText.getText();
					// odszyfrowanie
					result = functionsDecrypt.decode(text.getBytes());
					lblResulText.setText(new String(result));
				}
			}
		});
		btnOdszyfruj.setBounds(149, 241, 89, 23);
		panel.add(btnOdszyfruj);
		lblResulText.setBackground(Color.LIGHT_GRAY);

		lblResulText.setForeground(Color.BLACK);
		lblResulText.setOpaque(true);
		lblResulText.setVerticalAlignment(SwingConstants.TOP);
		lblResulText.setBounds(33, 315, 422, 151);
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

		JButton btnZapiszDoPliku = new JButton("Zapisz do pliku");
		btnZapiszDoPliku.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println(lblResulText.getText());
				if (lblResulText.getText() == "") {
					System.out.println("There is no text to save.");
					JOptionPane.showMessageDialog(panel, "Nie ma tekstu do zapisania.", "Brak tekstu",
							JOptionPane.ERROR_MESSAGE);
				} else {
					System.out.println(lblResulText.getText());
					saveFile(lblResulText.getText());
				}
			}
		});
		btnZapiszDoPliku.setBounds(33, 480, 115, 23);
		panel.add(btnZapiszDoPliku);

		txtNazwapliku = new JTextField();
		txtNazwapliku.setText("nazwaPliku");
		txtNazwapliku.setBounds(158, 477, 297, 29);
		panel.add(txtNazwapliku);
		txtNazwapliku.setColumns(10);

		JButton btnWyczyWynik = new JButton("Wyczyść wynik");
		btnWyczyWynik.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				lblResulText.setText("");
			}
		});
		btnWyczyWynik.setBounds(326, 285, 129, 23);
		panel.add(btnWyczyWynik);

		rdbtnWpiszKlucz = new JRadioButton("Wpisz klucz");
		rdbtnWpiszKlucz.setBounds(33, 179, 95, 23);
		rdbtnWpiszKlucz.setSelected(true);
		panel.add(rdbtnWpiszKlucz);

		rdbtnWybierzKlucz = new JRadioButton("wybierz z listy");
		rdbtnWybierzKlucz.setBounds(254, 179, 109, 23);
		panel.add(rdbtnWybierzKlucz);

		ButtonGroup radioButtonsGroup = new ButtonGroup();
		radioButtonsGroup.add(rdbtnWpiszKlucz);
		radioButtonsGroup.add(rdbtnWybierzKlucz);

		txtKey = new JTextField();
		txtKey.setText("1a25s8fe5dsg65ad");
		txtKey.setBounds(33, 205, 142, 29);
		panel.add(txtKey);
		txtKey.setColumns(10);

		String[] keys = { "4affa3ce5dac65ad", "8a25sa9d534b65ad", "a9e6c54e5dsg65ad", "bb9372ae5dsg65ad" };
		comboBoxKeys = new JComboBox(keys);
		comboBoxKeys.setSelectedIndex(0);
		comboBoxKeys.setBounds(250, 205, 176, 29);
		panel.add(comboBoxKeys);

		JLabel lblLub = new JLabel("lub");
		lblLub.setBounds(202, 181, 46, 19);
		panel.add(lblLub);
	}

	private void readFile(String filePath) {
		try {
			BufferedReader bufferReader = new BufferedReader(new FileReader(filePath));
			String line;
			text = "";
			while ((line = bufferReader.readLine()) != null) {
				text += line + " ";
				userText.setText(text);
			}
			bufferReader.close();
			System.out.println(text);
		} catch (FileNotFoundException e) {
			System.out.println("There is no such file.");
			JOptionPane.showMessageDialog(this, "Nie ma takiego pliku.", "Brak pliku", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("Error during processing file.");
			JOptionPane.showMessageDialog(this, "Błąd podczas przetwarzania pliku.", "Błąd", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
	}

	private void saveFile(String message) {
		try {
			String fileName;
			if (txtNazwapliku.getText().isEmpty())
				fileName = "wynik";
			else
				fileName = txtNazwapliku.getText();
			PrintWriter printWriter = new PrintWriter(fileName + ".txt");
			printWriter.print(message);
			printWriter.close();
			System.out.println("File saved.");
			JOptionPane.showMessageDialog(this, "Tekst został zapisany do pliku " + fileName + ".txt");
		} catch (FileNotFoundException e) {
			System.out.println("Error during saving file.");
			JOptionPane.showMessageDialog(this, "Błąd podczas zapisywania pliku.", "Błąd zapisu",
					JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
	}

	// TODO Tu trzeba zmienić, bo klucz nie będzie jednej długości, tylko są 3
	// różne możliwości
	private Boolean checkKey(String key) {
		if (key.length() < 16) {
			System.out.println("Key is too short.");
			JOptionPane.showMessageDialog(this, "Podany klucz jest za krótki.", "Za krótki klucz",
					JOptionPane.ERROR_MESSAGE);
			return false;
		}
		if (key.length() > 16) {
			System.out.println("Key is too long.");
			JOptionPane.showMessageDialog(this, "Podany klucz jest za długi.", "Za długi klucz",
					JOptionPane.ERROR_MESSAGE);
			return false;
		}
		return true;
	}
}
