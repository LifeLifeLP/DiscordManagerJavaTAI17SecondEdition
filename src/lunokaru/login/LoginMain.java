package lunokaru.login;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.SystemColor;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.Locale;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JTextField;
import javax.swing.UIManager;

import org.slf4j.LoggerFactory;

import lifelifelp.botfuctions.BotFunctions;
import lifelifelp.botfuctions.DiscordEvents;
import sx.blah.discord.api.IDiscordClient;

public class LoginMain {
	
	//Klassenattribute
	private JFrame frmDiscordManagerBeta;
	private CardLayout myCL;
	private JTextField tfBotID;
	private static Object[] cancelOption = {"Ja", "Nein"};
	
	//Discord Klassenattribute
	private org.slf4j.Logger logger;
	private static IDiscordClient discordClient;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Throwable e) {
            e.printStackTrace();
        }
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoginMain window = new LoginMain();
					window.frmDiscordManagerBeta.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public LoginMain() {
		myCL = new CardLayout();
		logger = LoggerFactory.getLogger(LoginMain.class);
		initialize();
		myCL.show(frmDiscordManagerBeta.getContentPane(), "pLoginMain");
	}
	
	//Einen weiteren Thread f�r das Einloggen des Bots starten
	class Server implements Runnable{
		public void run() {
			logger = LoggerFactory.getLogger(LoginMain.class);
			discordClient = BotFunctions.getBuiltDiscordClient(tfBotID.getText());
			discordClient.getDispatcher().registerListener(new DiscordEvents());
		    discordClient.login();
			do {
			}while(!discordClient.isReady());
		    logger.info(discordClient.getApplicationName()+ " is back. Let's get going!");
			LoginData.setBotID(discordClient);
	    	frmDiscordManagerBeta.dispose();
//	    	guiHauptfenster.GuiMain.main(null);
	    }
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmDiscordManagerBeta = new JFrame();
		frmDiscordManagerBeta.setLocale(Locale.GERMANY);
		frmDiscordManagerBeta.setBackground(Color.LIGHT_GRAY);
		//frmDiscordManagerBeta.setIconImage(Toolkit.getDefaultToolkit().getImage(LoginMain.class.getResource("/bilder/Logo.png")));
		frmDiscordManagerBeta.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 12));
		frmDiscordManagerBeta.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		frmDiscordManagerBeta.setTitle("Discord Manager Beta V1");
		frmDiscordManagerBeta.setBounds(100, 100, 450, 300);
		frmDiscordManagerBeta.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmDiscordManagerBeta.getContentPane().setLayout(myCL);
		frmDiscordManagerBeta.setLocationRelativeTo(null);
		frmDiscordManagerBeta.setResizable(false);
		
		JPanel pLoginMain = new JPanel();
		pLoginMain.setBackground(UIManager.getColor("Table[Disabled+Selected].textBackground"));
		frmDiscordManagerBeta.getContentPane().add(pLoginMain, "pLoginMain");
		GridBagLayout gbl_pLoginMain = new GridBagLayout();
		gbl_pLoginMain.columnWidths = new int[]{0, 0, 195, 0, 0, 0};
		gbl_pLoginMain.rowHeights = new int[]{0, 0, 0, 0, 0, 0};
		gbl_pLoginMain.columnWeights = new double[]{0.0, 0.0, 1.0, 1.0, 0.0, Double.MIN_VALUE};
		gbl_pLoginMain.rowWeights = new double[]{1.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		pLoginMain.setLayout(gbl_pLoginMain);
		
		JLabel lLoginMainDiscordManager = new JLabel("Discord Manager");
		lLoginMainDiscordManager.setFont(new Font("Segoe UI Black", Font.BOLD, 35));
		GridBagConstraints gbc_lLoginMainDiscordManager = new GridBagConstraints();
		gbc_lLoginMainDiscordManager.gridwidth = 5;
		gbc_lLoginMainDiscordManager.insets = new Insets(0, 0, 5, 0);
		gbc_lLoginMainDiscordManager.gridx = 0;
		gbc_lLoginMainDiscordManager.gridy = 0;
		pLoginMain.add(lLoginMainDiscordManager, gbc_lLoginMainDiscordManager);
		
		JLabel lLoginMainLogin = new JLabel("Login");
		lLoginMainLogin.setFont(new Font("Segoe UI Black", Font.BOLD, 30));
		GridBagConstraints gbc_lLoginMainLogin = new GridBagConstraints();
		gbc_lLoginMainLogin.gridwidth = 5;
		gbc_lLoginMainLogin.insets = new Insets(0, 0, 5, 0);
		gbc_lLoginMainLogin.gridx = 0;
		gbc_lLoginMainLogin.gridy = 1;
		pLoginMain.add(lLoginMainLogin, gbc_lLoginMainLogin);
		
		Component horizontalStrut = Box.createHorizontalStrut(20);
		GridBagConstraints gbc_horizontalStrut = new GridBagConstraints();
		gbc_horizontalStrut.insets = new Insets(0, 0, 5, 5);
		gbc_horizontalStrut.gridx = 0;
		gbc_horizontalStrut.gridy = 3;
		pLoginMain.add(horizontalStrut, gbc_horizontalStrut);
		
		JLabel lLoginMainBotID = new JLabel("Bot-ID:");
		lLoginMainBotID.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 12));
		GridBagConstraints gbc_lLoginMainBotID = new GridBagConstraints();
		gbc_lLoginMainBotID.anchor = GridBagConstraints.EAST;
		gbc_lLoginMainBotID.insets = new Insets(0, 0, 5, 5);
		gbc_lLoginMainBotID.gridx = 1;
		gbc_lLoginMainBotID.gridy = 3;
		pLoginMain.add(lLoginMainBotID, gbc_lLoginMainBotID);
		
		tfBotID = new JTextField();
		tfBotID.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 12));
		tfBotID.setText("NTEyMjE0NjMyODMyOTU4NDk0.DtWGYA.VI6AIELp_bl8ucQI3f8xb6PqqCo");
		tfBotID.setColumns(10);
		GridBagConstraints gbc_tfBotID = new GridBagConstraints();
		gbc_tfBotID.fill = GridBagConstraints.BOTH;
		gbc_tfBotID.gridwidth = 2;
		gbc_tfBotID.insets = new Insets(0, 0, 5, 5);
		gbc_tfBotID.gridx = 2;
		gbc_tfBotID.gridy = 3;
		pLoginMain.add(tfBotID, gbc_tfBotID);
		
		Component horizontalStrut_1 = Box.createHorizontalStrut(20);
		GridBagConstraints gbc_horizontalStrut_1 = new GridBagConstraints();
		gbc_horizontalStrut_1.insets = new Insets(0, 0, 5, 0);
		gbc_horizontalStrut_1.gridx = 4;
		gbc_horizontalStrut_1.gridy = 3;
		pLoginMain.add(horizontalStrut_1, gbc_horizontalStrut_1);
		
		JButton btnLoginMainLogin = new JButton("Login");
		btnLoginMainLogin.setBackground(SystemColor.activeCaption);
		btnLoginMainLogin.setMnemonic(KeyEvent.VK_ENTER);
		btnLoginMainLogin.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 12));
		btnLoginMainLogin.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		GridBagConstraints gbc_btnLoginMainLogin = new GridBagConstraints();
		gbc_btnLoginMainLogin.fill = GridBagConstraints.BOTH;
		gbc_btnLoginMainLogin.insets = new Insets(0, 0, 0, 5);
		gbc_btnLoginMainLogin.gridx = 2;
		gbc_btnLoginMainLogin.gridy = 4;
		pLoginMain.add(btnLoginMainLogin, gbc_btnLoginMainLogin);
		btnLoginMainLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String botID = tfBotID.getText();
				if(!botID.equals("")) {	
					 Server myServer = new Server();
					 Thread thread = new Thread(myServer);
					 btnLoginMainLogin.setEnabled(false);
				     thread.start();
				}else {
					JOptionPane.showMessageDialog(frmDiscordManagerBeta, "Bitte geben Sie die Bot-ID ein.", "Hinweis", JOptionPane.CANCEL_OPTION);
				}
			}
		});
		
		JButton bntLoginMainAbbruch = new JButton("Abbruch");
		bntLoginMainAbbruch.setBackground(SystemColor.activeCaption);
		bntLoginMainAbbruch.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 12));
		bntLoginMainAbbruch.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		bntLoginMainAbbruch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int temp = JOptionPane.showOptionDialog(frmDiscordManagerBeta, "Wollen Sie das Programm wirklich verlassen?", "Hinweis", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, cancelOption, cancelOption[0]);
				if (temp == 0) {
					System.exit(0);
				}
			}
		});
		GridBagConstraints gbc_bntLoginMainAbbruch = new GridBagConstraints();
		gbc_bntLoginMainAbbruch.fill = GridBagConstraints.BOTH;
		gbc_bntLoginMainAbbruch.insets = new Insets(0, 0, 0, 5);
		gbc_bntLoginMainAbbruch.gridx = 3;
		gbc_bntLoginMainAbbruch.gridy = 4;
		pLoginMain.add(bntLoginMainAbbruch, gbc_bntLoginMainAbbruch);
		GridBagConstraints gbc_pBMain_1 = new GridBagConstraints();
		gbc_pBMain_1.insets = new Insets(0, 0, 0, 5);
		gbc_pBMain_1.gridx = 0;
		gbc_pBMain_1.gridy = 3;
	}

}
