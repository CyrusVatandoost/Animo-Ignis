package socket;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.EmptyBorder;

import game.Display;
import game.GameServer;
import game.Map;
import gui.GUIClient;
import gui.GUIServer;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Toolkit;

public class GUITester extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUITester frame = new GUITester();
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
	public GUITester() {
		
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
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 200, 160);
		setLocationRelativeTo(null);
		setIconImage(Toolkit.getDefaultToolkit().getImage("lib\\sprites\\logo_002.png"));
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnNewButton = new JButton("Server");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				addServer();
			}
		});
		btnNewButton.setBounds(10, 11, 174, 50);
		contentPane.add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("Client");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addClient();
			}
		});
		btnNewButton_1.setBounds(10, 72, 174, 50);
		contentPane.add(btnNewButton_1);
	}
	
	public void addServer() {
		GameServer game = new GameServer();
		Server server = new Server();
		GUIServer gui = new GUIServer(server, game);
		gui.run();
	}
	
	public void addClient() {
		Client client = new Client();
		GUIClient gui = new GUIClient(client);
		gui.run();
	}
	
}
