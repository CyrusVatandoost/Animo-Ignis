package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import game.Account;

public class GUICreateAccount extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JTextField textField;
	private int num = -1;
	private GUIClient client;
	private Account account;
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUICreateAccount dialog = new GUICreateAccount();
					dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
					dialog.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public GUICreateAccount() {
		
		setTitle("Create Account");
		setResizable(false);
		setBounds(100, 100, 320, 225);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		JLabel lblName = new JLabel("Name:");
		lblName.setBounds(10, 11, 50, 20);
		contentPanel.add(lblName);
		
		textField = new JTextField();
		textField.setBounds(70, 10, 238, 25);
		contentPanel.add(textField);
		textField.setColumns(10);
		
		JButton btnPlayer1 = new JButton("");
		btnPlayer1.setIcon(new ImageIcon("lib\\sprites\\player_001.png"));
		btnPlayer1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				account = new Account(textField.getText(), 1);
				num = 1;
				setVisible(false);
				dispose();
			}
		});
		btnPlayer1.setBounds(10, 40, 50, 50);
		contentPanel.add(btnPlayer1);
		
		JButton btnPlayer6 = new JButton("");
		btnPlayer6.setIcon(new ImageIcon("lib\\sprites\\player_006.png"));
		btnPlayer6.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				account = new Account(textField.getText(), 6);
				num = 6;
				setVisible(false);
				dispose();
			}
		});
		btnPlayer6.setBounds(10, 100, 50, 50);
		contentPanel.add(btnPlayer6);
		
		JButton btnPlayer2 = new JButton("");
		btnPlayer2.setIcon(new ImageIcon("lib\\sprites\\player_002.png"));
		btnPlayer2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				account = new Account(textField.getText(), 2);
				num = 2;
				setVisible(false);
				dispose();
			}
		});
		btnPlayer2.setBounds(70, 40, 52, 50);
		contentPanel.add(btnPlayer2);
		
		JButton btnPlayer7 = new JButton("");
		btnPlayer7.setIcon(new ImageIcon("lib\\sprites\\player_007.png"));
		btnPlayer7.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				account = new Account(textField.getText(), 7);
				num = 7;
				setVisible(false);
				dispose();
			}
		});
		btnPlayer7.setBounds(70, 100, 52, 50);
		contentPanel.add(btnPlayer7);
		
		JButton btnPlayer3 = new JButton("");
		btnPlayer3.setIcon(new ImageIcon("lib\\sprites\\player_003.png"));
		btnPlayer3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				account = new Account(textField.getText(), 3);
				num = 3;
				setVisible(false);
				dispose();
			}
		});
		btnPlayer3.setBounds(132, 40, 52, 50);
		contentPanel.add(btnPlayer3);
		
		JButton btnPlayer8 = new JButton("");
		btnPlayer8.setIcon(new ImageIcon("lib\\sprites\\player_008.png"));
		btnPlayer8.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				account = new Account(textField.getText(), 8);
				num = 8;
				setVisible(false);
				dispose();
			}
		});
		btnPlayer8.setBounds(132, 100, 52, 50);
		contentPanel.add(btnPlayer8);
		
		JButton btnPlayer4 = new JButton("");
		btnPlayer4.setIcon(new ImageIcon("lib\\sprites\\player_004.png"));
		btnPlayer4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				account = new Account(textField.getText(), 4);
				num = 4;
				setVisible(false);
				dispose();
			}
		});
		btnPlayer4.setBounds(194, 40, 52, 50);
		contentPanel.add(btnPlayer4);
		
		JButton btnPlayer9 = new JButton("");
		btnPlayer9.setIcon(new ImageIcon("lib\\sprites\\player_009.png"));
		btnPlayer9.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				account = new Account(textField.getText(), 9);
				num = 5;
				setVisible(false);
				dispose();
			}
		});
		btnPlayer9.setBounds(194, 100, 52, 50);
		contentPanel.add(btnPlayer9);
		
		JButton btnPlayer5 = new JButton("");
		btnPlayer5.setIcon(new ImageIcon("lib\\sprites\\player_005.png"));
		btnPlayer5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				account = new Account(textField.getText(), 5);
				num = 9;
				setVisible(false);
				dispose();
			}
		});
		btnPlayer5.setBounds(256, 40, 52, 50);
		contentPanel.add(btnPlayer5);
		
		JButton btnPlayer10 = new JButton("");
		btnPlayer10.setIcon(new ImageIcon("lib\\sprites\\player_010.png"));
		btnPlayer10.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				account = new Account(textField.getText(), 10);
				num = 10;
				setVisible(false);
				dispose();
			}
		});
		btnPlayer10.setBounds(256, 100, 52, 50);
		contentPanel.add(btnPlayer10);

	}
	
	public Account showDialog() {
		setVisible(true);
		return account;
	}

}
