package gui;

import java.awt.EventQueue;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.EmptyBorder;

import socket.Client;
import javax.swing.JSlider;
import javax.swing.event.ChangeListener;

import game.Account;

import javax.swing.event.ChangeEvent;
import javax.swing.ImageIcon;

public class GUIClient extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtIP;
	private JTextField txtPort;
	
	private Client client;
	private GUIActivity activity;

	private Account account = new Account("user", -1);
	private JTextField txtName;
	
	/**
	 * Launch the application.
	 */
	public void run() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUIClient frame = new GUIClient(client);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public GUIClient(Client arg1) {

		this.client = arg1;
		
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		} catch (InstantiationException e1) {
			e1.printStackTrace();
		} catch (IllegalAccessException e1) {
			e1.printStackTrace();
		} catch (UnsupportedLookAndFeelException e1) {
			e1.printStackTrace();
		}

		setResizable(false);
		setTitle("Client");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 600, 700);
		setLocationRelativeTo(null);
		setIconImage(Toolkit.getDefaultToolkit().getImage("lib\\sprites\\logo_002.png"));
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		contentPane = 	new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblIpAddress = new JLabel("IP Address");
		lblIpAddress.setBounds(10, 10, 100, 25);
		contentPane.add(lblIpAddress);
		
		JLabel lblPortNumber = new JLabel("Port Number");
		lblPortNumber.setBounds(10, 45, 100, 25);
		contentPane.add(lblPortNumber);
		
		txtIP = new JTextField();
		txtIP.setBounds(120, 10, 150, 25);
		contentPane.add(txtIP);
		txtIP.setColumns(10);
		
		txtPort = new JTextField();
		txtPort.setColumns(10);
		txtPort.setBounds(120, 45, 150, 25);
		contentPane.add(txtPort);
		
		JButton btnConnect = new JButton("Connect");
		btnConnect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				client.connect(txtIP.getText(), Integer.parseInt(txtPort.getText()));
				if(client.isConnected())
					btnConnect.setEnabled(false);
			}
		});
		btnConnect.setBounds(120, 80, 150, 25);
		contentPane.add(btnConnect);
		
		JLabel lblActivity = new JLabel("Activity");
		lblActivity.setBounds(280, 10, 100, 25);
		contentPane.add(lblActivity);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(10, 115, 260, 25);
		contentPane.add(separator);
		
		txtIP.setText("localhost");
		txtPort.setText("4444");
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(280, 45, 304, 344);
		contentPane.add(scrollPane);
		
		JTextArea txtActivity = new JTextArea();
		scrollPane.setViewportView(txtActivity);
		txtActivity.setLineWrap(true);
		txtActivity.setEditable(false);
		activity = new GUIActivity(txtActivity);
		
		JSlider sliderSize = new JSlider();
		sliderSize.setMajorTickSpacing(1);
		sliderSize.setPaintLabels(true);
		sliderSize.setSnapToTicks(true);
		sliderSize.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				if(client.getGame().isRunning()) {
					client.getGame().getDisplay().setFrameSize(sliderSize.getValue());
				}
			}
		});
		sliderSize.setValue(2);
		sliderSize.setMinimum(1);
		sliderSize.setMaximum(5);
		sliderSize.setBounds(95, 150, 175, 30);
		contentPane.add(sliderSize);
		
		JLabel lblWindowSize = new JLabel("Window Size");
		lblWindowSize.setBounds(10, 150, 75, 25);
		contentPane.add(lblWindowSize);
		
		JLabel lblFps = new JLabel("FPS");
		lblFps.setBounds(10, 190, 75, 25);
		contentPane.add(lblFps);
		
		JSlider sliderFPS = new JSlider();
		sliderFPS.setSnapToTicks(true);
		sliderFPS.setMinorTickSpacing(5);
		sliderFPS.setMajorTickSpacing(10);
		sliderFPS.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				if(client.getGame().isRunning()) {
					client.getGame().getDisplay().setFPS(sliderFPS.getValue());
				}
			}
		});
		sliderFPS.setValue(20);
		sliderFPS.setPaintLabels(true);
		sliderFPS.setMaximum(60);
		sliderFPS.setBounds(95, 190, 175, 30);
		contentPane.add(sliderFPS);
		
		JButton btn6 = new JButton("");
		btn6.setIcon(new ImageIcon("lib\\sprites\\player_006.png"));
		btn6.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				client.getGame().setAccount(new Account(txtName.getText(), 6));
			}
		});
		btn6.setBounds(10, 589, 50, 50);
		contentPane.add(btn6);
		
		JButton btn7 = new JButton("");
		btn7.setIcon(new ImageIcon("lib\\sprites\\player_007.png"));
		btn7.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				client.getGame().setAccount(new Account(txtName.getText(), 7));
			}
		});
		btn7.setBounds(70, 589, 50, 50);
		contentPane.add(btn7);
		
		JButton btn8 = new JButton("");
		btn8.setIcon(new ImageIcon("lib\\sprites\\player_008.png"));
		btn8.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				client.getGame().setAccount(new Account(txtName.getText(), 8));
			}
		});
		btn8.setBounds(130, 589, 50, 50);
		contentPane.add(btn8);
		
		JButton btn9 = new JButton("");
		btn9.setIcon(new ImageIcon("lib\\sprites\\player_009.png"));
		btn9.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				client.getGame().setAccount(new Account(txtName.getText(), 9));
			}
		});
		btn9.setBounds(190, 589, 50, 50);
		contentPane.add(btn9);
		
		JButton btn10 = new JButton("");
		btn10.setIcon(new ImageIcon("lib\\sprites\\player_010.png"));
		btn10.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				client.getGame().setAccount(new Account(txtName.getText(), 10));
			}
		});
		btn10.setBounds(250, 589, 50, 50);
		contentPane.add(btn10);
		
		JButton btn1 = new JButton("");
		btn1.setIcon(new ImageIcon("lib\\sprites\\player_001.png"));
		btn1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				client.getGame().setAccount(new Account(txtName.getText(), 1));
			}
		});
		btn1.setBounds(10, 528, 50, 50);
		contentPane.add(btn1);
		
		JButton btn2 = new JButton("");
		btn2.setIcon(new ImageIcon("lib\\sprites\\player_002.png"));
		btn2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				client.getGame().setAccount(new Account(txtName.getText(), 2));
			}
		});
		btn2.setBounds(70, 528, 50, 50);
		contentPane.add(btn2);
		
		JButton btn3 = new JButton("");
		btn3.setIcon(new ImageIcon("lib\\sprites\\player_003.png"));
		btn3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				client.getGame().setAccount(new Account(txtName.getText(), 3));
			}
		});
		btn3.setBounds(130, 528, 50, 50);
		contentPane.add(btn3);
		
		JButton btn4 = new JButton("");
		btn4.setIcon(new ImageIcon("lib\\sprites\\player_004.png"));
		btn4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				client.getGame().setAccount(new Account(txtName.getText(), 4));
			}
		});
		btn4.setBounds(190, 528, 50, 50);
		contentPane.add(btn4);
		
		JButton btn5 = new JButton("");
		btn5.setIcon(new ImageIcon("lib\\sprites\\player_005.png"));
		btn5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				client.getGame().setAccount(new Account(txtName.getText(), 5));
			}
		});
		btn5.setBounds(250, 528, 50, 50);
		contentPane.add(btn5);
		
		JLabel lblName = new JLabel("Name");
		lblName.setBounds(10, 492, 50, 25);
		contentPane.add(lblName);
		
		txtName = new JTextField();
		txtName.setBounds(70, 494, 230, 20);
		contentPane.add(txtName);
		txtName.setColumns(10);
		client.setActivity(activity);
		
	}
	
	public Account getAccount() {
		return account;
	}
	
}
