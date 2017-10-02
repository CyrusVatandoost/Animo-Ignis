package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.EmptyBorder;

import game.Event;
import game.GameServer;
import socket.Server;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JSlider;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;
import java.awt.event.MouseMotionAdapter;

public class GUIServer extends JFrame {

	private JPanel contentPane;
	private JTextField txtIP;
	private JTextField txtPort;
	
	private GUIActivity activity;
	private Server server;
	private GameServer game;

	public void run() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUIServer frame = new GUIServer(server, game);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public GUIServer(Server arg0, GameServer arg1) {
		
		this.server = arg0;
		this.game = arg1;
		
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

		setTitle("Server");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 700, 500);
		setLocationRelativeTo(null);
		setResizable(false);
		setIconImage(Toolkit.getDefaultToolkit().getImage("lib\\sprites\\logo_002.png"));
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnNewMenu = new JMenu("New menu");
		menuBar.add(mnNewMenu);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(280, 45, 395, 390);
		contentPane.add(scrollPane);
		
		JTextArea txtActivity = new JTextArea();
		txtActivity.setLineWrap(true);
		txtActivity.setEditable(false);
		scrollPane.setViewportView(txtActivity);
		
		JLabel lblIp = new JLabel("IP Address");
		lblIp.setBounds(10, 10, 100, 25);
		contentPane.add(lblIp);
		
		JLabel lblPortNumber = new JLabel("Port Number");
		lblPortNumber.setBounds(10, 45, 100, 25);
		contentPane.add(lblPortNumber);
		
		txtIP = new JTextField(getMachineIP());
		txtIP.setEditable(false);
		txtIP.setBounds(120, 10, 150, 25);
		contentPane.add(txtIP);
		txtIP.setColumns(10);
		
		txtPort = new JTextField();
		txtPort.setColumns(10);
		txtPort.setBounds(120, 45, 150, 25);
		contentPane.add(txtPort);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(10, 115, 260, 25);
		contentPane.add(separator);
		
		JButton btnDisconnect = new JButton("Disconnect");
		btnDisconnect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
			}
		});
		btnDisconnect.setBounds(10, 220, 260, 25);
		contentPane.add(btnDisconnect);
		
		JLabel lblActivity = new JLabel("Activity");
		lblActivity.setBounds(280, 10, 100, 25);
		contentPane.add(lblActivity);
		
		JButton btnConnect = new JButton("Connect");
		btnConnect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(!txtPort.getText().equals("")) {
					server.waitForConnection(Integer.parseInt(txtPort.getText()));
					if(server.isConnected())
						btnConnect.setEnabled(false);
				}
			}
		});
		btnConnect.setBounds(120, 80, 150, 25);
		contentPane.add(btnConnect);
		
		JButton btnStart = new JButton("Start Game");
		btnStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				server.startGame();
			}
		});
		btnStart.setBounds(10, 184, 125, 25);
		contentPane.add(btnStart);
		
		JButton btnDisconnectAll = new JButton("Disconnect All");
		btnDisconnectAll.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
			}
		});
		btnDisconnectAll.setBounds(145, 185, 125, 25);
		contentPane.add(btnDisconnectAll);
		
		txtPort.setText("4444");
		activity = new GUIActivity(txtActivity);
		
		JLabel lblWindowSize = new JLabel("Window Size");
		lblWindowSize.setBounds(10, 255, 75, 25);
		contentPane.add(lblWindowSize);
		
		JSlider sdrSize = new JSlider();
		sdrSize.setPaintLabels(true);
		sdrSize.setMajorTickSpacing(1);
		sdrSize.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				if(server.getGame().isOnline()) {
					server.getGame().getDisplay().setFrameSize(sdrSize.getValue());
				}
			}
		});
		sdrSize.setMinimum(1);
		sdrSize.setMaximum(5);
		sdrSize.setValue(2);
		sdrSize.setBounds(95, 255, 175, 30);
		contentPane.add(sdrSize);
		
		JSlider sliderFPS = new JSlider();
		sliderFPS.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				if(server.getGame().isOnline()) {
					server.getGame().getDisplay().setFPS(sliderFPS.getValue());
				}
			}
		});
		sliderFPS.setSnapToTicks(true);
		sliderFPS.setMinorTickSpacing(5);
		sliderFPS.setValue(2);
		sliderFPS.setPaintLabels(true);
		sliderFPS.setMaximum(60);
		sliderFPS.setMajorTickSpacing(10);
		sliderFPS.setBounds(95, 295, 175, 30);
		contentPane.add(sliderFPS);
		
		JLabel lblFps = new JLabel("FPS");
		lblFps.setBounds(10, 295, 75, 25);
		contentPane.add(lblFps);
		
		JButton btnBeginGame = new JButton("Begin Game");
		btnBeginGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				server.getGame().getMap().beginGame();
			}
		});
		btnBeginGame.setBounds(145, 150, 125, 25);
		contentPane.add(btnBeginGame);
		
		JButton btnAskAccounts = new JButton("Ask Accounts");
		btnAskAccounts.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				server.askForAccounts();
			}
		});
		btnAskAccounts.setBounds(10, 151, 125, 25);
		contentPane.add(btnAskAccounts);
		
		server.setActivity(activity);
		
	}
	
	public String getMachineIP() {
		InetAddress inet = null;
		try {
			inet = InetAddress.getLocalHost();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		return inet.getHostAddress();
	}
}
