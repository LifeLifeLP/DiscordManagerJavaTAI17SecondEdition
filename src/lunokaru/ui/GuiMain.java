//
//Discord Bot Manager zur Verwaltung von Discord
//wird entwickelt von Lukas Rupnow und Kevin Schulz
//unter Benutzung von Java sowie der Discord API Discord4J
//

//Package
package lunokaru.ui;

//Imports
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.ComponentOrientation;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Hashtable;
import java.util.List;
import java.util.Vector;

import javax.swing.Box;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JColorChooser;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JSlider;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.BevelBorder;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import javax.swing.table.DefaultTableModel;

import lunokaru.login.LoginData;
import lunokaru.login.LoginMain;
import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.handle.obj.ICategory;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IGuild;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.handle.obj.IRole;
import sx.blah.discord.handle.obj.IUser;
import sx.blah.discord.handle.obj.IVoiceChannel;
import java.awt.Toolkit;
import java.awt.SystemColor;

public class GuiMain {

	//Discordattribute
	private static IDiscordClient discordClient;
	private IUser discordUser;
	private IRole discordRolle;
	private int discordChannelCount,
				discordUserCount;
	private Color discordRollenFarbe;
	
	//
	//Klassenattribute
	//
	private JFrame frmDiscordManagerBeta;
	
	//CardLayouts
	private CardLayout 	cardLayoutMenueSwitch,
						cardLayoutSwitch,
						cardLayoutUVSwitch,
						cardLayoutCVSwitchMenue,
						cardLayoutCVSwitchMain,
						cardLayoutKVSwitchMain,
						cardLayoutRVSwitchMain;
	//JPanels
	private JPanel  pWillkommen,
					pSwitch,
					pUVSwitch,
					pUVRollenBearbeiten,
					pUVUserKicken,
					pUVUserBannen,
					pUVMain,
					pMenueSwitch,
					pMenue,
					pBackToMenue,
					pCVAlleChannel,
					pCVSwitchMenue,
					pCVSwitchMain,
					pKVSwitch,
					pRVSwitch;
	
	//JScrollPane
	private JScrollPane sPRVRollenErstellen;
	
	//JLabel
	private JLabel 	lblchannelcountzahl,
					lblPrivateMessageAn,
					lblUserBannenWertigkeit,
					lblAlleChannel,
					lblBackToMenueBotName,
					lblNicknameAnzeigen,
					lblUVStatsNickname,
					lblUVStatsStatus;
	
	//JTable
	private JTable 	tUserRollenAlt,
					tUserRollenNeu;
	
	//DefaulTableModel
	private DefaultTableModel 	dtmUserRollenAlt,
								dtmUserRollenNeu;
	
	//JTextField
	private JTextField 	tFUVNickname,
						tFUVChat,
						tFUVKick,
						tFCVChannelName,
						tFCVChannelTopic,
						tFCVChannelNameNew,
						tFCVChannelTopicNew,
						tFKVKategorieName,
						tFKVKategorieNameNew;
	
	//JComboBox<String>
	private JComboBox<String> 	cBCVAlleChannel,
								cBCVChannelinKategorie,
								cBKVAlleKategorien,
								cBRVAlleRollen,
								cBServerListe,
								cBUserVerwalten;
	
	//Vector<String>
	private Vector<String> test;
	
	//JButton
	private JButton btnRollenUserHinzufuegen,
					btnRollenUserLoeschen,
					btnChannelBearbeiten,
					btnChannelLoeschen;
								
	//ButtonGroup
	private ButtonGroup buttonGroupUserBannen;
	
	//JRadioButton
	private JRadioButton	rdbtnKeine,
							rdbtnLetzteStunden,
							rdbtnLetzteTage,
							rdbtnAlleNachrichten;
	
	//JSlider
	private JSlider slCVPositioninKategorie;
	
	//Hashtable
	private Hashtable<Integer, JLabel> 	labelsForCBUserlimit,
										labelsForCBBitrate;
										
	//Object
	private static Object[] cancelOption = {"Ja", "Nein"};
	
	//JCheckBox
	private JCheckBox 	chBCVChannelNSWF,
						chBCVChannelinKategorie;
	
	
	private JTextField textField;

	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GuiMain window = new GuiMain();
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
	public GuiMain() {
		discordClient = LoginData.getBotID();
		discordRollenFarbe = new Color(0, 0, 0);
		cardLayoutMenueSwitch = new CardLayout();
		cardLayoutSwitch = new CardLayout();
	    cardLayoutUVSwitch = new CardLayout();
	    cardLayoutCVSwitchMenue = new CardLayout();
	    cardLayoutCVSwitchMain = new CardLayout();
	    cardLayoutKVSwitchMain = new CardLayout();
	    cardLayoutRVSwitchMain = new CardLayout();
	    buttonGroupUserBannen = new ButtonGroup();
	    labelsForCBUserlimit  = new Hashtable<>();
	    labelsForCBUserlimit.put(0, new JLabel("∞"));
	    labelsForCBUserlimit.put(99, new JLabel("99"));
	    labelsForCBBitrate = new Hashtable<>();
	    labelsForCBBitrate.put(8, new JLabel("8kbps"));
	    labelsForCBBitrate.put(64, new JLabel("64kbps"));
	    labelsForCBBitrate.put(96, new JLabel("96kbps"));
	    
	    computeDiscordChannels();
	    computeDiscordUsers();
	    tableModelsinitialize();
		initialize();
		cardLayoutMenueSwitch.show(pMenueSwitch, "pMenue");
		fillStatistic();
		createButtonGroupUserBannen();
	}
	
	
	//Funktion zum Setzen der aktuell ausgewählten Guild
	private IGuild findCurrentGuild() {
		IGuild discordServer = null;
		for (IGuild ig : discordClient.getGuilds()) {
			if(ig.getName().equals(cBServerListe.getSelectedItem())) {
				discordServer = ig;
				break;
			}
		}
		return discordServer;
	}
	
	
	//Funktion zum Befuellen der ComboBox cbRVAlleRollen
	private void fillCBRVAlleRollen() {
		cBRVAlleRollen.removeAllItems();
		IGuild ig = findCurrentGuild();
		for (IRole ir : ig.getRoles()) {
			cBRVAlleRollen.addItem(ir.getName());
		}
		cBRVAlleRollen.setSelectedIndex(-1);
	}
	
	
	//Funktion zum Befuellen der ComboBox cBCVAlleKategorien
	private void fillCBKVAlleKategorien() {
		cBKVAlleKategorien.removeAllItems();
		IGuild ig = findCurrentGuild();
		for (ICategory ic : ig.getCategories()) {
			cBKVAlleKategorien.addItem(ic.getName());
		}
		cBKVAlleKategorien.setSelectedIndex(-1);
	}
	
	//Funktion zum Befuellen der ComboBox cBCVAlleChannel
	private void fillCBCVAlleChannel() {
		cBCVAlleChannel.removeAllItems();
		IGuild ig = findCurrentGuild();
		for (IChannel ic : ig.getChannels()) {
			cBCVAlleChannel.addItem(ic.getName());
		}
		cBCVAlleChannel.setSelectedIndex(-1);
	}
	
	//Funktion zum Setzen des dynamischen Textes fuer JLabel lblUVStatsStatus
	private void setUVUserStatus() {
		IGuild ig = findCurrentGuild();
		for (IUser iu : ig.getUsers()) {
			if (iu.getName().equals(cBUserVerwalten.getSelectedItem())) {
				lblUVStatsStatus.setText("Status des Users: "+ iu.getPresence().getStatus().toString().toLowerCase());
			}
		}
	}

	//Funktion zum Setzen des dynamischen Textes fuer JLabel lblUVStatsNickname 
	private void setUVUserNickname() {
		IGuild ig = findCurrentGuild();
		for (IUser iu : ig.getUsers()) {
			if (iu.getName().equals(cBUserVerwalten.getSelectedItem())) {
				lblUVStatsNickname.setText("Nickname des Users: " + iu.getNicknameForGuild(ig));
			}
		}
	}
	
	//Funktion zum Aendern des User Nicknames
	private void changeUserNickname() {
		IGuild ig = findCurrentGuild();
		for (IUser iu : ig.getUsers()) {
			if(iu.getName().equals(cBUserVerwalten.getSelectedItem())) {
				ig.setUserNickname(iu, tFUVNickname.getText());
			}
		}
	}
	
	//Funktion zum Befuellen der ComboBox cBUserVerwalten
	private void fillcBUserVerwalten() {
		IGuild ig = findCurrentGuild();
		for(IUser iu : ig.getUsers()) {
			if(!iu.getName().equals(discordClient.getApplicationName())) {
				cBUserVerwalten.addItem(iu.getName());
			}
		}
	}
	
	//Funktion zum Setzen des dynamischen Textes fuer JLabel lblNicknameAnzeigen
	private void getUserNickname() {
		IGuild ig = findCurrentGuild();
		for (IUser iu : ig.getUsers()) {
			if (iu.getName().equals(cBUserVerwalten.getSelectedItem())) {
				lblNicknameAnzeigen.setText(iu.getNicknameForGuild(ig));
			}
		}
	}
	
	
	//Funktion zum Setzen einer Rolle in discordRolle
	private void setDiscordRolle(boolean x) {
		IGuild ig = findCurrentGuild();
		for (IRole ir : ig.getRoles()) {
			if(x == true) {
				if (ir.getName().equals(dtmUserRollenNeu.getValueAt(tUserRollenNeu.getSelectedRow(), 0))) {
							discordRolle = ir;
						}
					}else {
						if (ir.getName().equals(dtmUserRollenAlt.getValueAt(tUserRollenAlt.getSelectedRow(), 0))) {
							discordRolle = ir;
						}
					}
				}
			}
		
	
	
	//Funktion zum Setzen des discordUser
	private void setDiscordUser() {
		IGuild ig = findCurrentGuild();
		for (IUser iu : ig.getUsers()) {
			if (iu.getName().equals(cBUserVerwalten.getSelectedItem())) {
				discordUser = iu;
			}
		}
	}
	
	//Funktion zum Befuellen der TabellenModell dtmUserRollenAlt und dtmUserRollenNeu
	private void collectFullUserRollen() {
		if(dtmUserRollenAlt.getRowCount() > 0) {
			dtmUserRollenAlt.setRowCount(0);
			fillTableUserRollenAlt();
		}else {
			fillTableUserRollenAlt();
		}
		if(dtmUserRollenNeu.getRowCount() > 0) {
			dtmUserRollenNeu.setRowCount(0);
			fillTableUserRollenNeu();
		}else {
			fillTableUserRollenNeu();
		}
	}
	
	//Funktion zum Wechseln des JPanels und setzen eines dynamischen Textes
	private void subMenueSwitch() {
		cardLayoutMenueSwitch.show(pMenueSwitch, "pBackToMenue");
		IGuild ig = findCurrentGuild();
		lblBackToMenueBotName.setText(discordClient.getOurUser().getNicknameForGuild(ig));
	}
	
	//Funktion zum Erstellen einer ButtonGroup und befuellen dieser
	private void createButtonGroupUserBannen() {
		rdbtnKeine.setActionCommand("Nichts");
		rdbtnLetzteStunden.setActionCommand("24 Stunden");
		rdbtnLetzteTage.setActionCommand("7 Tage");
		rdbtnAlleNachrichten.setActionCommand("Alles");
		buttonGroupUserBannen.add(rdbtnKeine);
		buttonGroupUserBannen.add(rdbtnLetzteStunden);
		buttonGroupUserBannen.add(rdbtnLetzteTage);
		buttonGroupUserBannen.add(rdbtnAlleNachrichten);
	};

	//Funktion zum Zaehlen der User sichtbar fuer den Bot
	private void computeDiscordUsers() {
		for (IGuild ig : discordClient.getGuilds()) {
			discordUserCount = ig.getUsers().size();
		}
	}

	//Funktion zum Zaehlen der Guilds sichtbar fuer den Bot
	private void computeDiscordChannels() {
		for (IGuild ig : discordClient.getGuilds()) {
			discordChannelCount += ig.getChannels().size();
		}
	}

	//Funktion zum Erstellen der DefaultTableModels dtmuserRollenAlt und dtmUserRollenNeu 
	private void tableModelsinitialize() {
		 dtmUserRollenAlt = new DefaultTableModel(){
		        /**
				 * 
				 */
				private static final long serialVersionUID = -2915648547917092140L;
				@Override
		        public boolean isCellEditable(int row, int column) {
		           //gibt zurueck dass nichts editierbar ist
		           return false;
		        }
		    };
		    dtmUserRollenAlt.addColumn("User hat Rollen:");
		    dtmUserRollenNeu = new DefaultTableModel() {
				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;
				@Override
		        public boolean isCellEditable(int row, int column) {
		           //gibt zurueck dass nichts editierbar ist
		           return false;
		        }
		    };
		    dtmUserRollenNeu.addColumn("Nicht vergebene Rollen:");
	}

	//Funktion zum Befuellen der Statistik
	private void fillStatistic() {
		int temp = 0;
	    for (IGuild iG : discordClient.getGuilds()) {
	    	List<IChannel> discordChannels = iG.getChannels();
	    	List<IVoiceChannel> discordVoiceChannels = iG.getVoiceChannels();
	    	temp = temp + discordChannels.size() + discordVoiceChannels.size();
	    	lblchannelcountzahl.setText(String.valueOf(temp));
		}
		for (IGuild iG : discordClient.getGuilds()) {
			cBServerListe.addItem(iG.getName());
		}
	}
	
	//Funktion zum Befuellen der Tabelle TableUserRollenNeu
	private  void fillTableUserRollenNeu() {
		for(IGuild ig : discordClient.getGuilds()) {
			if(ig.getName().equals(cBServerListe.getSelectedItem())) {
				for(IRole ir : ig.getRoles()) {
					if(!ir.isEveryoneRole() && !test.contains(ir.getName())) {
						Vector<String> vtmp = new Vector<String>();
						vtmp.add(ir.getName());
						dtmUserRollenNeu.addRow(vtmp);
					}
				}
			}
		}
	}

	//Funktion zum Befuellen der Tabelle TableUserRollenAlt
	private void fillTableUserRollenAlt() {
		for(IGuild ig : discordClient.getGuilds()) {
			if(ig.getName().equals(cBServerListe.getSelectedItem())) {
				for(IUser iu : ig.getUsers()) {
					if(iu.getName().equals(cBUserVerwalten.getSelectedItem())) {
						for(IRole ir : iu.getRolesForGuild(ig)) {
							if(!ir.isEveryoneRole()) {
								Vector<String> vtmp = new Vector<String>();
								vtmp.add(ir.getName());
								test.add(ir.getName());
								dtmUserRollenAlt.addRow(vtmp);
							}
						}
					}
				}
			}
		}
	}
	
	//Funktion zum Loeschen der Nachrichten eines Users
	private void afterBanCleanup(IUser user, IGuild guild){
        for(IChannel ic: guild.getChannels()) {
            for(IMessage im: ic.getFullMessageHistory()) {
                if(im.getAuthor().equals(user)) {
                    im.delete();
                }
            }
        }
    }

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmDiscordManagerBeta = new JFrame();
		frmDiscordManagerBeta.setIconImage(Toolkit.getDefaultToolkit().getImage(GuiMain.class.getResource("/lunokaru/picture/Logo.png")));
		frmDiscordManagerBeta.setBounds(100, 100, 840, 1142);
		frmDiscordManagerBeta.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 12));
		frmDiscordManagerBeta.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		frmDiscordManagerBeta.setTitle("Discord Manager V1");
		frmDiscordManagerBeta.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmDiscordManagerBeta.getContentPane().setLayout(new CardLayout(0, 0));
		
		JPanel pMain = new JPanel();
		pMain.setBorder(null);
		frmDiscordManagerBeta.getContentPane().add(pMain, "pMain");
		GridBagLayout gbl_pMain = new GridBagLayout();
		gbl_pMain.columnWidths = new int[]{280, 0, 0};
		gbl_pMain.rowHeights = new int[]{0, 0};
		gbl_pMain.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		gbl_pMain.rowWeights = new double[]{1.0, Double.MIN_VALUE};
		pMain.setLayout(gbl_pMain);
		
		pMenueSwitch = new JPanel();
		pMenueSwitch.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		GridBagConstraints gbc_pMenueSwitch = new GridBagConstraints();
		gbc_pMenueSwitch.fill = GridBagConstraints.BOTH;
		gbc_pMenueSwitch.insets = new Insets(0, 0, 0, 5);
		gbc_pMenueSwitch.gridx = 0;
		gbc_pMenueSwitch.gridy = 0;
		pMain.add(pMenueSwitch, gbc_pMenueSwitch);
		pMenueSwitch.setLayout(cardLayoutMenueSwitch);
		
		pMenue = new JPanel();
		pMenueSwitch.add(pMenue, "pMenue");
		GridBagLayout gbl_pMenue = new GridBagLayout();
		gbl_pMenue.columnWidths = new int[]{115, 0};
		gbl_pMenue.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gbl_pMenue.columnWeights = new double[]{0.0, Double.MIN_VALUE};
		gbl_pMenue.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		pMenue.setLayout(gbl_pMenue);
		
		JLabel lblNewLabel = new JLabel("");
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 0);
		gbc_lblNewLabel.gridx = 0;
		gbc_lblNewLabel.gridy = 0;
		pMenue.add(lblNewLabel, gbc_lblNewLabel);
		lblNewLabel.setIcon(new ImageIcon(GuiMain.class.getResource("/lunokaru/picture/DiscordBot.jpg")));
		
		JLabel lBotName = new JLabel(discordClient.getApplicationName());
		GridBagConstraints gbc_lBotName = new GridBagConstraints();
		gbc_lBotName.insets = new Insets(0, 0, 5, 0);
		gbc_lBotName.gridx = 0;
		gbc_lBotName.gridy = 1;
		pMenue.add(lBotName, gbc_lBotName);
		lBotName.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 20));
		
		JSeparator separator_2 = new JSeparator();
		GridBagConstraints gbc_separator_2 = new GridBagConstraints();
		gbc_separator_2.fill = GridBagConstraints.BOTH;
		gbc_separator_2.insets = new Insets(0, 0, 5, 0);
		gbc_separator_2.gridx = 0;
		gbc_separator_2.gridy = 2;
		pMenue.add(separator_2, gbc_separator_2);
		separator_2.setSize(new Dimension(0, 2));
		
		JButton btnLogout = new JButton("Logout");
		GridBagConstraints gbc_btnLogout = new GridBagConstraints();
		gbc_btnLogout.anchor = GridBagConstraints.NORTH;
		gbc_btnLogout.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnLogout.insets = new Insets(0, 0, 5, 0);
		gbc_btnLogout.gridx = 0;
		gbc_btnLogout.gridy = 3;
		pMenue.add(btnLogout, gbc_btnLogout);
		btnLogout.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 13));
		
		JButton btnProgrammSchieen = new JButton("Programm schie\u00DFen");
		GridBagConstraints gbc_btnProgrammSchieen = new GridBagConstraints();
		gbc_btnProgrammSchieen.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnProgrammSchieen.insets = new Insets(0, 0, 5, 0);
		gbc_btnProgrammSchieen.gridx = 0;
		gbc_btnProgrammSchieen.gridy = 4;
		pMenue.add(btnProgrammSchieen, gbc_btnProgrammSchieen);
		btnProgrammSchieen.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 13));
		
		JButton btnEinstellungen = new JButton("Einstellungen");
		GridBagConstraints gbc_btnEinstellungen = new GridBagConstraints();
		gbc_btnEinstellungen.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnEinstellungen.insets = new Insets(0, 0, 5, 0);
		gbc_btnEinstellungen.gridx = 0;
		gbc_btnEinstellungen.gridy = 5;
		pMenue.add(btnEinstellungen, gbc_btnEinstellungen);
		btnEinstellungen.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 13));
		
		JSeparator sepMenueMitte = new JSeparator();
		GridBagConstraints gbc_sepMenueMitte = new GridBagConstraints();
		gbc_sepMenueMitte.fill = GridBagConstraints.BOTH;
		gbc_sepMenueMitte.insets = new Insets(0, 0, 5, 0);
		gbc_sepMenueMitte.gridx = 0;
		gbc_sepMenueMitte.gridy = 6;
		pMenue.add(sepMenueMitte, gbc_sepMenueMitte);
		
		JLabel lblServerliste = new JLabel("Serverliste");
		GridBagConstraints gbc_lblServerliste = new GridBagConstraints();
		gbc_lblServerliste.insets = new Insets(0, 0, 5, 0);
		gbc_lblServerliste.gridx = 0;
		gbc_lblServerliste.gridy = 7;
		pMenue.add(lblServerliste, gbc_lblServerliste);
		lblServerliste.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 20));
		
		cBServerListe = new JComboBox<String>();
		GridBagConstraints gbc_cBServerListe = new GridBagConstraints();
		gbc_cBServerListe.fill = GridBagConstraints.HORIZONTAL;
		gbc_cBServerListe.insets = new Insets(0, 0, 5, 0);
		gbc_cBServerListe.gridx = 0;
		gbc_cBServerListe.gridy = 8;
		pMenue.add(cBServerListe, gbc_cBServerListe);
		cBServerListe.setMaximumRowCount(3);
		cBServerListe.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		cBServerListe.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 12));
		
		Component verticalStrut = Box.createVerticalStrut(20);
		GridBagConstraints gbc_verticalStrut = new GridBagConstraints();
		gbc_verticalStrut.insets = new Insets(0, 0, 5, 0);
		gbc_verticalStrut.gridx = 0;
		gbc_verticalStrut.gridy = 9;
		pMenue.add(verticalStrut, gbc_verticalStrut);
		
		JSeparator separator_1 = new JSeparator();
		GridBagConstraints gbc_separator_1 = new GridBagConstraints();
		gbc_separator_1.fill = GridBagConstraints.BOTH;
		gbc_separator_1.insets = new Insets(0, 0, 5, 0);
		gbc_separator_1.gridx = 0;
		gbc_separator_1.gridy = 10;
		pMenue.add(separator_1, gbc_separator_1);
		separator_1.setMinimumSize(new Dimension(0, 2));
		
		JLabel lblVerwaltung = new JLabel("Verwaltung");
		GridBagConstraints gbc_lblVerwaltung = new GridBagConstraints();
		gbc_lblVerwaltung.insets = new Insets(0, 0, 5, 0);
		gbc_lblVerwaltung.gridx = 0;
		gbc_lblVerwaltung.gridy = 11;
		pMenue.add(lblVerwaltung, gbc_lblVerwaltung);
		lblVerwaltung.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 20));
		
		JButton btnUserVerwalten = new JButton("User verwalten");
		GridBagConstraints gbc_btnUserVerwalten = new GridBagConstraints();
		gbc_btnUserVerwalten.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnUserVerwalten.insets = new Insets(0, 0, 5, 0);
		gbc_btnUserVerwalten.gridx = 0;
		gbc_btnUserVerwalten.gridy = 12;
		pMenue.add(btnUserVerwalten, gbc_btnUserVerwalten);
		btnUserVerwalten.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 13));
		
		JButton btnChannelVerwalten = new JButton("Channel verwalten");
		GridBagConstraints gbc_btnChannelVerwalten = new GridBagConstraints();
		gbc_btnChannelVerwalten.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnChannelVerwalten.insets = new Insets(0, 0, 5, 0);
		gbc_btnChannelVerwalten.gridx = 0;
		gbc_btnChannelVerwalten.gridy = 13;
		pMenue.add(btnChannelVerwalten, gbc_btnChannelVerwalten);
		btnChannelVerwalten.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 13));
		
		JButton btnKategorienVerwalten = new JButton("Kategorien verwalten");
		GridBagConstraints gbc_btnKategorienVerwalten = new GridBagConstraints();
		gbc_btnKategorienVerwalten.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnKategorienVerwalten.insets = new Insets(0, 0, 5, 0);
		gbc_btnKategorienVerwalten.gridx = 0;
		gbc_btnKategorienVerwalten.gridy = 14;
		pMenue.add(btnKategorienVerwalten, gbc_btnKategorienVerwalten);
		btnKategorienVerwalten.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 13));
		
		JButton btnRollenVerwalten = new JButton("Rollen verwalten");
		GridBagConstraints gbc_btnRollenVerwalten = new GridBagConstraints();
		gbc_btnRollenVerwalten.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnRollenVerwalten.gridx = 0;
		gbc_btnRollenVerwalten.gridy = 15;
		pMenue.add(btnRollenVerwalten, gbc_btnRollenVerwalten);
		btnRollenVerwalten.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 13));
		
		pBackToMenue = new JPanel();
		pMenueSwitch.add(pBackToMenue, "pBackToMenue");
		GridBagLayout gbl_pBackToMenue = new GridBagLayout();
		gbl_pBackToMenue.columnWidths = new int[]{0, 0};
		gbl_pBackToMenue.rowHeights = new int[]{0, 0, 0, 0, 0, 0};
		gbl_pBackToMenue.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_pBackToMenue.rowWeights = new double[]{0.0, 0.0, 0.0, 1.0, 0.0, Double.MIN_VALUE};
		pBackToMenue.setLayout(gbl_pBackToMenue);
		
		JLabel lblBackToMenueBild = new JLabel("");
		lblBackToMenueBild.setIcon(new ImageIcon(GuiMain.class.getResource("/lunokaru/picture/DiscordBot.jpg")));
		GridBagConstraints gbc_lblBackToMenueBild = new GridBagConstraints();
		gbc_lblBackToMenueBild.insets = new Insets(0, 0, 5, 0);
		gbc_lblBackToMenueBild.gridx = 0;
		gbc_lblBackToMenueBild.gridy = 0;
		pBackToMenue.add(lblBackToMenueBild, gbc_lblBackToMenueBild);
		
		lblBackToMenueBotName = new JLabel(discordClient.getApplicationName());
		lblBackToMenueBotName.setFont(new Font("Segoe UI Semibold", Font.BOLD, 20));
		GridBagConstraints gbc_lblBackToMenueBotName = new GridBagConstraints();
		gbc_lblBackToMenueBotName.insets = new Insets(0, 0, 5, 0);
		gbc_lblBackToMenueBotName.gridx = 0;
		gbc_lblBackToMenueBotName.gridy = 1;
		pBackToMenue.add(lblBackToMenueBotName, gbc_lblBackToMenueBotName);
		
		JSeparator separator_3 = new JSeparator();
		separator_3.setSize(new Dimension(0, 2));
		GridBagConstraints gbc_separator_3 = new GridBagConstraints();
		gbc_separator_3.insets = new Insets(0, 0, 5, 0);
		gbc_separator_3.fill = GridBagConstraints.BOTH;
		gbc_separator_3.gridx = 0;
		gbc_separator_3.gridy = 2;
		pBackToMenue.add(separator_3, gbc_separator_3);
		
		Component verticalStrut_1 = Box.createVerticalStrut(20);
		GridBagConstraints gbc_verticalStrut_1 = new GridBagConstraints();
		gbc_verticalStrut_1.insets = new Insets(0, 0, 5, 0);
		gbc_verticalStrut_1.gridx = 0;
		gbc_verticalStrut_1.gridy = 3;
		pBackToMenue.add(verticalStrut_1, gbc_verticalStrut_1);
		
		JButton btnBackToMenue = new JButton("Zur\u00FCck zum Hauptmen\u00FC");
		btnBackToMenue.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cardLayoutMenueSwitch.show(pMenueSwitch, "pMenue");
				cardLayoutSwitch.show(pSwitch, "pWillkommen");
			}
		});
		GridBagConstraints gbc_btnBackToMenue = new GridBagConstraints();
		gbc_btnBackToMenue.fill = GridBagConstraints.BOTH;
		gbc_btnBackToMenue.gridx = 0;
		gbc_btnBackToMenue.gridy = 4;
		pBackToMenue.add(btnBackToMenue, gbc_btnBackToMenue);
		btnRollenVerwalten.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				subMenueSwitch();
				fillCBRVAlleRollen();
				cardLayoutSwitch.show(pSwitch, "pRollenVerwalten");
				
			}
		});
		btnKategorienVerwalten.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				subMenueSwitch();
				fillCBKVAlleKategorien();
				cardLayoutSwitch.show(pSwitch, "pKategorienVerwalten");
				
			}
		});
		btnChannelVerwalten.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				subMenueSwitch();
				for (IGuild ig : discordClient.getGuilds()) {
					if(ig.getName() == cBServerListe.getSelectedItem()) {
						lblAlleChannel.setText("Alle Channels des Servers: "+ ig.getName());
					}
				}
				fillCBCVAlleChannel();
				cardLayoutSwitch.show(pSwitch, "pChannelVerwalten");
				cardLayoutCVSwitchMenue.show(pCVSwitchMenue, "pCVAlleChannel");
				cardLayoutCVSwitchMain.show(pCVSwitchMain, "pCVHauptFenster");
			}
		});
		btnUserVerwalten.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				subMenueSwitch();
				cardLayoutSwitch.show(pSwitch, "pUserVerwalten");
				cardLayoutUVSwitch.show(pUVSwitch, "pUVMain");
				if(cBUserVerwalten.getItemCount() > 0) {
					cBUserVerwalten.removeAllItems();
					fillcBUserVerwalten();
				}else {
					fillcBUserVerwalten();
				}
			}

		});
		
		btnEinstellungen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null, "Hier würde man später persistente Einstellungen vornehmen können.");
			}
		});
		btnProgrammSchieen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int temp = JOptionPane.showOptionDialog(frmDiscordManagerBeta, "Wollen Sie das Programm wirklich verlassen?", "Hinweis", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, cancelOption, cancelOption[0]);
				if (temp == 0) {
					discordClient.logout();
					frmDiscordManagerBeta.dispose();
				}
			}
		});
		btnLogout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int temp = JOptionPane.showOptionDialog(frmDiscordManagerBeta, "Wollen Sie das Programm wirklich verlassen?", "Hinweis", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, cancelOption, cancelOption[0]);
				if (temp == 0) {
					discordClient.logout();
					LoginMain.main(null);
					frmDiscordManagerBeta.dispose();
				}
			}
		});
		
		pSwitch = new JPanel();
		pSwitch.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		GridBagConstraints gbc_pSwitch = new GridBagConstraints();
		gbc_pSwitch.fill = GridBagConstraints.BOTH;
		gbc_pSwitch.gridx = 1;
		gbc_pSwitch.gridy = 0;
		pMain.add(pSwitch, gbc_pSwitch);
		pSwitch.setLayout(cardLayoutSwitch);
		
		pWillkommen = new JPanel();
		pSwitch.add(pWillkommen, "pWillkommen");
		GridBagLayout gbl_pWillkommen = new GridBagLayout();
		gbl_pWillkommen.columnWidths = new int[]{170, 177, 0};
		gbl_pWillkommen.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0};
		gbl_pWillkommen.columnWeights = new double[]{1.0, 1.0, Double.MIN_VALUE};
		gbl_pWillkommen.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 4.9E-324};
		pWillkommen.setLayout(gbl_pWillkommen);
		
		JLabel lblStatistik = new JLabel("Statistik");
		GridBagConstraints gbc_lblStatistik = new GridBagConstraints();
		gbc_lblStatistik.gridwidth = 2;
		gbc_lblStatistik.insets = new Insets(0, 0, 5, 0);
		gbc_lblStatistik.gridx = 0;
		gbc_lblStatistik.gridy = 0;
		pWillkommen.add(lblStatistik, gbc_lblStatistik);
		lblStatistik.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 25));
		
		JLabel lblServercount = new JLabel("Servercount:");
		GridBagConstraints gbc_lblServercount = new GridBagConstraints();
		gbc_lblServercount.insets = new Insets(0, 0, 5, 5);
		gbc_lblServercount.gridx = 0;
		gbc_lblServercount.gridy = 1;
		pWillkommen.add(lblServercount, gbc_lblServercount);
		lblServercount.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 13));
		
		JLabel lblLblservercountzahl = new JLabel(String.valueOf(discordClient.getGuilds().size()));
		lblLblservercountzahl.setFont(new Font("Segoe UI", Font.PLAIN, 13));
		GridBagConstraints gbc_lblLblservercountzahl = new GridBagConstraints();
		gbc_lblLblservercountzahl.insets = new Insets(0, 0, 5, 0);
		gbc_lblLblservercountzahl.gridx = 1;
		gbc_lblLblservercountzahl.gridy = 1;
		pWillkommen.add(lblLblservercountzahl, gbc_lblLblservercountzahl);
		
		JLabel lblChannelcount = new JLabel("Channelcount:");
		lblChannelcount.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 13));
		GridBagConstraints gbc_lblChannelcount = new GridBagConstraints();
		gbc_lblChannelcount.insets = new Insets(0, 0, 5, 5);
		gbc_lblChannelcount.gridx = 0;
		gbc_lblChannelcount.gridy = 2;
		pWillkommen.add(lblChannelcount, gbc_lblChannelcount);
		
		lblchannelcountzahl = new JLabel(String.valueOf(discordChannelCount));
		lblchannelcountzahl.setFont(new Font("Segoe UI", Font.PLAIN, 13));
		GridBagConstraints gbc_lblLblchannelcountzahl = new GridBagConstraints();
		gbc_lblLblchannelcountzahl.insets = new Insets(0, 0, 5, 0);
		gbc_lblLblchannelcountzahl.gridx = 1;
		gbc_lblLblchannelcountzahl.gridy = 2;
		pWillkommen.add(lblchannelcountzahl, gbc_lblLblchannelcountzahl);
		
		JLabel lblUsercount = new JLabel("Usercount:");
		lblUsercount.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 13));
		GridBagConstraints gbc_lblUsercount = new GridBagConstraints();
		gbc_lblUsercount.insets = new Insets(0, 0, 5, 5);
		gbc_lblUsercount.gridx = 0;
		gbc_lblUsercount.gridy = 3;
		pWillkommen.add(lblUsercount, gbc_lblUsercount);
		
		JLabel lblLblusercountanzahl = new JLabel(String.valueOf(discordUserCount));
		lblLblusercountanzahl.setFont(new Font("Segoe UI", Font.PLAIN, 13));
		GridBagConstraints gbc_lblLblusercountanzahl = new GridBagConstraints();
		gbc_lblLblusercountanzahl.insets = new Insets(0, 0, 5, 0);
		gbc_lblLblusercountanzahl.gridx = 1;
		gbc_lblLblusercountanzahl.gridy = 3;
		pWillkommen.add(lblLblusercountanzahl, gbc_lblLblusercountanzahl);
		
		JLabel lblDiscordapiverbindung = new JLabel("DiscordAPI-Verbindung:");
		lblDiscordapiverbindung.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 13));
		GridBagConstraints gbc_lblDiscordapiverbindung = new GridBagConstraints();
		gbc_lblDiscordapiverbindung.insets = new Insets(0, 0, 5, 5);
		gbc_lblDiscordapiverbindung.gridx = 0;
		gbc_lblDiscordapiverbindung.gridy = 4;
		pWillkommen.add(lblDiscordapiverbindung, gbc_lblDiscordapiverbindung);
		
		JLabel lblLbldiscordapizahl = new JLabel(String.valueOf(discordClient.getShardCount()));
		lblLbldiscordapizahl.setFont(new Font("Segoe UI", Font.PLAIN, 13));
		GridBagConstraints gbc_lblLbldiscordapizahl = new GridBagConstraints();
		gbc_lblLbldiscordapizahl.insets = new Insets(0, 0, 5, 0);
		gbc_lblLbldiscordapizahl.gridx = 1;
		gbc_lblLbldiscordapizahl.gridy = 4;
		pWillkommen.add(lblLbldiscordapizahl, gbc_lblLbldiscordapizahl);
		
		JLabel lblAktuelleTtigkeit = new JLabel("aktuelle T\u00E4tigkeit:");
		lblAktuelleTtigkeit.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 13));
		GridBagConstraints gbc_lblAktuelleTtigkeit = new GridBagConstraints();
		gbc_lblAktuelleTtigkeit.insets = new Insets(0, 0, 5, 5);
		gbc_lblAktuelleTtigkeit.gridx = 0;
		gbc_lblAktuelleTtigkeit.gridy = 5;
		pWillkommen.add(lblAktuelleTtigkeit, gbc_lblAktuelleTtigkeit);
		
		JLabel label = new JLabel(String.valueOf(discordClient.getOurUser().getPresence().getStatus()).toLowerCase());
		label.setFont(new Font("Segoe UI", Font.PLAIN, 13));
		GridBagConstraints gbc_label = new GridBagConstraints();
		gbc_label.insets = new Insets(0, 0, 5, 0);
		gbc_label.gridx = 1;
		gbc_label.gridy = 5;
		pWillkommen.add(label, gbc_label);
		
		JSeparator separator = new JSeparator();
		separator.setMinimumSize(new Dimension(0, 2));
		GridBagConstraints gbc_separator = new GridBagConstraints();
		gbc_separator.insets = new Insets(0, 0, 5, 0);
		gbc_separator.gridwidth = 2;
		gbc_separator.fill = GridBagConstraints.BOTH;
		gbc_separator.gridx = 0;
		gbc_separator.gridy = 6;
		pWillkommen.add(separator, gbc_separator);
		
		JLabel lblWillkommen = new JLabel("Willkommen "+ discordClient.getApplicationOwner().getName());
		GridBagConstraints gbc_lblWillkommen = new GridBagConstraints();
		gbc_lblWillkommen.gridwidth = 2;
		gbc_lblWillkommen.insets = new Insets(0, 0, 0, 5);
		gbc_lblWillkommen.gridx = 0;
		gbc_lblWillkommen.gridy = 7;
		pWillkommen.add(lblWillkommen, gbc_lblWillkommen);
		lblWillkommen.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 25));
		
		JPanel pUserVerwalten = new JPanel();
		pSwitch.add(pUserVerwalten, "pUserVerwalten");
		GridBagLayout gbl_pUserVerwalten = new GridBagLayout();
		gbl_pUserVerwalten.columnWidths = new int[]{285, 0, 0};
		gbl_pUserVerwalten.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0};
		gbl_pUserVerwalten.columnWeights = new double[]{1.0, 1.0, Double.MIN_VALUE};
		gbl_pUserVerwalten.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		pUserVerwalten.setLayout(gbl_pUserVerwalten);
		
		cBUserVerwalten = new JComboBox<String>();
		cBUserVerwalten.addPopupMenuListener(new PopupMenuListener() {
			public void popupMenuCanceled(PopupMenuEvent arg0) {
				setUVUserNickname();
				setUVUserStatus();
			}
			public void popupMenuWillBecomeInvisible(PopupMenuEvent arg0) {
				setUVUserNickname();
				setUVUserStatus();
			}
			public void popupMenuWillBecomeVisible(PopupMenuEvent arg0) {
			}
		});
		cBUserVerwalten.addMouseListener(new MouseAdapter() {
			private IDiscordClient discordServer;

			@Override
			public void mouseClicked(MouseEvent arg0) {
				for (IUser iu : discordServer.getUsers()) {
					if(iu.getName() == cBUserVerwalten.getSelectedItem()) {
						discordUser = iu;
					}
				}

			}


		});
		
		JLabel lblServermitglieder = new JLabel("Servermitglieder");
		lblServermitglieder.setFont(new Font("Segoe UI", Font.PLAIN, 20));
		GridBagConstraints gbc_lblServermitglieder = new GridBagConstraints();
		gbc_lblServermitglieder.insets = new Insets(0, 0, 5, 5);
		gbc_lblServermitglieder.gridx = 0;
		gbc_lblServermitglieder.gridy = 0;
		pUserVerwalten.add(lblServermitglieder, gbc_lblServermitglieder);
		
		JButton btnRollenBearbeiten = new JButton("Rollen bearbeiten");
		btnRollenBearbeiten.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				cardLayoutUVSwitch.show(pUVSwitch, "pUVRollenBearbeiten");
				cBUserVerwalten.setEnabled(false);
				test = new Vector<String>();
				collectFullUserRollen();
			}
		});
		GridBagConstraints gbc_btnRollenBearbeiten = new GridBagConstraints();
		gbc_btnRollenBearbeiten.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnRollenBearbeiten.insets = new Insets(0, 0, 5, 0);
		gbc_btnRollenBearbeiten.gridx = 1;
		gbc_btnRollenBearbeiten.gridy = 0;
		pUserVerwalten.add(btnRollenBearbeiten, gbc_btnRollenBearbeiten);
		GridBagConstraints gbc_cBUserVerwalten = new GridBagConstraints();
		gbc_cBUserVerwalten.insets = new Insets(0, 0, 5, 5);
		gbc_cBUserVerwalten.fill = GridBagConstraints.HORIZONTAL;
		gbc_cBUserVerwalten.gridx = 0;
		gbc_cBUserVerwalten.gridy = 1;
		pUserVerwalten.add(cBUserVerwalten, gbc_cBUserVerwalten);
		
		JButton btnUserPm = new JButton("User PM");
		btnUserPm.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				cardLayoutUVSwitch.show(pUVSwitch, "pUVChat");
				cBUserVerwalten.setEnabled(false);
				lblPrivateMessageAn.setText("Privater Chat mit User: "+cBUserVerwalten.getSelectedItem().toString());
			    tFUVChat.setText("");
			}
		});
		GridBagConstraints gbc_btnUserPm = new GridBagConstraints();
		gbc_btnUserPm.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnUserPm.insets = new Insets(0, 0, 5, 0);
		gbc_btnUserPm.gridx = 1;
		gbc_btnUserPm.gridy = 1;
		pUserVerwalten.add(btnUserPm, gbc_btnUserPm);
		
		JButton btnUserKicken = new JButton("User kicken");
		btnUserKicken.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cardLayoutUVSwitch.show(pUVSwitch, "pUVUserKicken");
				cBUserVerwalten.setEnabled(false);
				tFUVKick.setText("");
			}
		});
		
		lblUVStatsNickname = new JLabel("Nickname des Users: ");
		GridBagConstraints gbc_lblUVStatsNickname = new GridBagConstraints();
		gbc_lblUVStatsNickname.insets = new Insets(0, 0, 5, 5);
		gbc_lblUVStatsNickname.gridx = 0;
		gbc_lblUVStatsNickname.gridy = 2;
		pUserVerwalten.add(lblUVStatsNickname, gbc_lblUVStatsNickname);
		GridBagConstraints gbc_btnUserKicken = new GridBagConstraints();
		gbc_btnUserKicken.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnUserKicken.insets = new Insets(0, 0, 5, 0);
		gbc_btnUserKicken.gridx = 1;
		gbc_btnUserKicken.gridy = 2;
		pUserVerwalten.add(btnUserKicken, gbc_btnUserKicken);
		
		JButton btnUserBannen = new JButton("User bannen");
		btnUserBannen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cardLayoutUVSwitch.show(pUVSwitch, "pUVUserBannen");
				cBUserVerwalten.setEnabled(false);
				
			}
		});
		
		lblUVStatsStatus = new JLabel("Status des Users: ");
		GridBagConstraints gbc_lblUVStatsStatus = new GridBagConstraints();
		gbc_lblUVStatsStatus.insets = new Insets(0, 0, 5, 5);
		gbc_lblUVStatsStatus.gridx = 0;
		gbc_lblUVStatsStatus.gridy = 3;
		pUserVerwalten.add(lblUVStatsStatus, gbc_lblUVStatsStatus);
		GridBagConstraints gbc_btnUserBannen = new GridBagConstraints();
		gbc_btnUserBannen.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnUserBannen.insets = new Insets(0, 0, 5, 0);
		gbc_btnUserBannen.gridx = 1;
		gbc_btnUserBannen.gridy = 3;
		pUserVerwalten.add(btnUserBannen, gbc_btnUserBannen);
		
		JButton btnUserNicknamenndern = new JButton("User Nicknamen \u00E4ndern");
		btnUserNicknamenndern.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cardLayoutUVSwitch.show(pUVSwitch, "pUVNicknameChange");
				cBUserVerwalten.setEnabled(false);
				getUserNickname();
				tFUVNickname.setText("");
			}
		});
		GridBagConstraints gbc_btnUserNicknamenndern = new GridBagConstraints();
		gbc_btnUserNicknamenndern.fill = GridBagConstraints.BOTH;
		gbc_btnUserNicknamenndern.insets = new Insets(0, 0, 5, 0);
		gbc_btnUserNicknamenndern.gridx = 1;
		gbc_btnUserNicknamenndern.gridy = 4;
		pUserVerwalten.add(btnUserNicknamenndern, gbc_btnUserNicknamenndern);
		
		JSeparator pSeperatorUserVerwalten = new JSeparator();
		GridBagConstraints gbc_pSeperatorUserVerwalten = new GridBagConstraints();
		gbc_pSeperatorUserVerwalten.insets = new Insets(0, 0, 5, 0);
		gbc_pSeperatorUserVerwalten.fill = GridBagConstraints.BOTH;
		gbc_pSeperatorUserVerwalten.gridwidth = 2;
		gbc_pSeperatorUserVerwalten.gridx = 0;
		gbc_pSeperatorUserVerwalten.gridy = 5;
		pUserVerwalten.add(pSeperatorUserVerwalten, gbc_pSeperatorUserVerwalten);
		
		pUVSwitch = new JPanel();
		GridBagConstraints gbc_pUVSwitch = new GridBagConstraints();
		gbc_pUVSwitch.gridwidth = 2;
		gbc_pUVSwitch.fill = GridBagConstraints.BOTH;
		gbc_pUVSwitch.gridx = 0;
		gbc_pUVSwitch.gridy = 6;
		pUserVerwalten.add(pUVSwitch, gbc_pUVSwitch);
		pUVSwitch.setLayout(cardLayoutUVSwitch);
		
		
		pUVMain = new JPanel();
		pUVSwitch.add(pUVMain, "pUVMain");
		
		pUVRollenBearbeiten = new JPanel();
		pUVSwitch.add(pUVRollenBearbeiten, "pUVRollenBearbeiten");
		GridBagLayout gbl_pUVRollenBearbeiten = new GridBagLayout();
		gbl_pUVRollenBearbeiten.columnWidths = new int[]{0, 0, 0, 0};
		gbl_pUVRollenBearbeiten.rowHeights = new int[]{0, 0, 0, 0, 0};
		gbl_pUVRollenBearbeiten.columnWeights = new double[]{1.0, 1.0, 0.0, Double.MIN_VALUE};
		gbl_pUVRollenBearbeiten.rowWeights = new double[]{0.0, 0.0, 1.0, 0.0, Double.MIN_VALUE};
		pUVRollenBearbeiten.setLayout(gbl_pUVRollenBearbeiten);
		
		JScrollPane scrollPane = new JScrollPane();
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.gridheight = 4;
		gbc_scrollPane.insets = new Insets(0, 0, 0, 5);
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 0;
		gbc_scrollPane.gridy = 0;
		pUVRollenBearbeiten.add(scrollPane, gbc_scrollPane);
		
		tUserRollenAlt = new JTable(dtmUserRollenAlt);
		tUserRollenAlt.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				if(btnRollenUserHinzufuegen.isEnabled()) {
					btnRollenUserHinzufuegen.setEnabled(false);
				}
				if(!btnRollenUserLoeschen.isEnabled()) {
					btnRollenUserLoeschen.setEnabled(true);
				}
			}
		});
		tUserRollenAlt.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scrollPane.setViewportView(tUserRollenAlt);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		GridBagConstraints gbc_scrollPane_1 = new GridBagConstraints();
		gbc_scrollPane_1.gridheight = 4;
		gbc_scrollPane_1.insets = new Insets(0, 0, 0, 5);
		gbc_scrollPane_1.fill = GridBagConstraints.BOTH;
		gbc_scrollPane_1.gridx = 1;
		gbc_scrollPane_1.gridy = 0;
		pUVRollenBearbeiten.add(scrollPane_1, gbc_scrollPane_1);
		
		tUserRollenNeu = new JTable(dtmUserRollenNeu);
		tUserRollenNeu.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				if(btnRollenUserLoeschen.isEnabled()) {
					btnRollenUserLoeschen.setEnabled(false);
				}
				if(!btnRollenUserHinzufuegen.isEnabled()) {
					btnRollenUserHinzufuegen.setEnabled(true);
				}
			}
		});
		scrollPane_1.setViewportView(tUserRollenNeu);
		tUserRollenNeu.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		btnRollenUserHinzufuegen = new JButton("Rolle hinzuf\u00FCgen");
		btnRollenUserHinzufuegen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				boolean x = true;
				test = new Vector<String>();
				setDiscordUser();
				setDiscordRolle(x);
				discordUser.addRole(discordRolle);
				collectFullUserRollen();
				btnRollenUserHinzufuegen.setEnabled(false);
			}
		});
		btnRollenUserHinzufuegen.setEnabled(false);
		GridBagConstraints gbc_btnRollenUserHinzufuegen = new GridBagConstraints();
		gbc_btnRollenUserHinzufuegen.fill = GridBagConstraints.BOTH;
		gbc_btnRollenUserHinzufuegen.insets = new Insets(0, 0, 5, 0);
		gbc_btnRollenUserHinzufuegen.gridx = 2;
		gbc_btnRollenUserHinzufuegen.gridy = 0;
		pUVRollenBearbeiten.add(btnRollenUserHinzufuegen, gbc_btnRollenUserHinzufuegen);
		
		btnRollenUserLoeschen = new JButton("Rolle entfernen");
		btnRollenUserLoeschen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				boolean x = false;
				test = new Vector<String>();
				setDiscordUser();
				setDiscordRolle(x);
				discordUser.removeRole(discordRolle);
				collectFullUserRollen();
				btnRollenUserLoeschen.setEnabled(false);
			}
		});
		btnRollenUserLoeschen.setEnabled(false);
		GridBagConstraints gbc_btnRollenUserLoeschen = new GridBagConstraints();
		gbc_btnRollenUserLoeschen.fill = GridBagConstraints.BOTH;
		gbc_btnRollenUserLoeschen.insets = new Insets(0, 0, 5, 0);
		gbc_btnRollenUserLoeschen.gridx = 2;
		gbc_btnRollenUserLoeschen.gridy = 1;
		pUVRollenBearbeiten.add(btnRollenUserLoeschen, gbc_btnRollenUserLoeschen);
		
		JButton btnUVRollenZurueck = new JButton("Zur\u00FCck zur Userauswahl");
		btnUVRollenZurueck.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cardLayoutUVSwitch.show(pUVSwitch, "pUVMain");
				cBUserVerwalten.setEnabled(true);
			}
		});
		GridBagConstraints gbc_btnUVRollenZurueck = new GridBagConstraints();
		gbc_btnUVRollenZurueck.fill = GridBagConstraints.BOTH;
		gbc_btnUVRollenZurueck.gridx = 2;
		gbc_btnUVRollenZurueck.gridy = 3;
		pUVRollenBearbeiten.add(btnUVRollenZurueck, gbc_btnUVRollenZurueck);
		
		JPanel pUVChat = new JPanel();
		pUVSwitch.add(pUVChat, "pUVChat");
		GridBagLayout gbl_pUVChat = new GridBagLayout();
		gbl_pUVChat.columnWidths = new int[]{0, 0, 0};
		gbl_pUVChat.rowHeights = new int[]{0, 0, 0, 0, 0, 0};
		gbl_pUVChat.columnWeights = new double[]{1.0, 1.0, Double.MIN_VALUE};
		gbl_pUVChat.rowWeights = new double[]{0.0, 0.0, 0.0, 1.0, 0.0, Double.MIN_VALUE};
		pUVChat.setLayout(gbl_pUVChat);
		
		lblPrivateMessageAn = new JLabel();
		GridBagConstraints gbc_lblPrivateMessageAn = new GridBagConstraints();
		gbc_lblPrivateMessageAn.insets = new Insets(0, 0, 5, 0);
		gbc_lblPrivateMessageAn.gridwidth = 2;
		gbc_lblPrivateMessageAn.gridx = 0;
		gbc_lblPrivateMessageAn.gridy = 0;
		pUVChat.add(lblPrivateMessageAn, gbc_lblPrivateMessageAn);
		
		JButton btnBtnpmsenden = new JButton("PM Senden");
		btnBtnpmsenden.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				for (IGuild ig : discordClient.getGuilds()) {
					if(ig.getName() == cBServerListe.getSelectedItem()) {
						for (IUser iu : ig.getUsers()) {
							if(iu.getName() == cBUserVerwalten.getSelectedItem()) {
								discordUser = iu;
							}							
						}
					}
				}
				discordClient.getOrCreatePMChannel(discordUser).sendMessage(tFUVChat.getText());
				
			}
		});
		
		tFUVChat = new JTextField();
		GridBagConstraints gbc_tFUVChat = new GridBagConstraints();
		gbc_tFUVChat.insets = new Insets(0, 0, 5, 5);
		gbc_tFUVChat.fill = GridBagConstraints.HORIZONTAL;
		gbc_tFUVChat.gridx = 0;
		gbc_tFUVChat.gridy = 2;
		pUVChat.add(tFUVChat, gbc_tFUVChat);
		tFUVChat.setColumns(10);
		GridBagConstraints gbc_btnBtnpmsenden = new GridBagConstraints();
		gbc_btnBtnpmsenden.insets = new Insets(0, 0, 5, 0);
		gbc_btnBtnpmsenden.fill = GridBagConstraints.BOTH;
		gbc_btnBtnpmsenden.gridx = 1;
		gbc_btnBtnpmsenden.gridy = 2;
		pUVChat.add(btnBtnpmsenden, gbc_btnBtnpmsenden);
		
		Component verticalStrut_2 = Box.createVerticalStrut(20);
		GridBagConstraints gbc_verticalStrut_2 = new GridBagConstraints();
		gbc_verticalStrut_2.insets = new Insets(0, 0, 5, 0);
		gbc_verticalStrut_2.gridx = 1;
		gbc_verticalStrut_2.gridy = 3;
		pUVChat.add(verticalStrut_2, gbc_verticalStrut_2);
		
		JButton btnUVChatZurueck = new JButton("Zur\u00FCck zur Userauswahl");
		btnUVChatZurueck.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cardLayoutUVSwitch.show(pUVSwitch, "pUVMain");
				cBUserVerwalten.setEnabled(true);
			}
		});
		GridBagConstraints gbc_btnUVChatZurueck = new GridBagConstraints();
		gbc_btnUVChatZurueck.fill = GridBagConstraints.BOTH;
		gbc_btnUVChatZurueck.gridx = 1;
		gbc_btnUVChatZurueck.gridy = 4;
		pUVChat.add(btnUVChatZurueck, gbc_btnUVChatZurueck);
		
		pUVUserKicken = new JPanel();
		pUVSwitch.add(pUVUserKicken, "pUVUserKicken");
		GridBagLayout gbl_pUVUserKicken = new GridBagLayout();
		gbl_pUVUserKicken.columnWidths = new int[]{191, 0, 0};
		gbl_pUVUserKicken.rowHeights = new int[]{0, 0, 0, 0, 0, 0};
		gbl_pUVUserKicken.columnWeights = new double[]{1.0, 1.0, Double.MIN_VALUE};
		gbl_pUVUserKicken.rowWeights = new double[]{0.0, 0.0, 0.0, 1.0, 0.0, Double.MIN_VALUE};
		pUVUserKicken.setLayout(gbl_pUVUserKicken);
		
		JLabel lblLbluserkicken = new JLabel("Kickvote f\u00FCr den User: ");
		GridBagConstraints gbc_lblLbluserkicken = new GridBagConstraints();
		gbc_lblLbluserkicken.insets = new Insets(0, 0, 5, 0);
		gbc_lblLbluserkicken.gridwidth = 2;
		gbc_lblLbluserkicken.gridx = 0;
		gbc_lblLbluserkicken.gridy = 0;
		pUVUserKicken.add(lblLbluserkicken, gbc_lblLbluserkicken);
		
		JLabel lblUserkickenGrund = new JLabel("Bitte geben Sie hier den Grund ein:");
		GridBagConstraints gbc_lblUserkickenGrund = new GridBagConstraints();
		gbc_lblUserkickenGrund.anchor = GridBagConstraints.WEST;
		gbc_lblUserkickenGrund.insets = new Insets(0, 0, 5, 5);
		gbc_lblUserkickenGrund.gridx = 0;
		gbc_lblUserkickenGrund.gridy = 1;
		pUVUserKicken.add(lblUserkickenGrund, gbc_lblUserkickenGrund);
		
		JButton btnBtnuserkicken = new JButton("User Kicken");
		btnBtnuserkicken.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				for (IGuild ig : discordClient.getGuilds()) {
					if(ig.getName() == cBServerListe.getSelectedItem()) {
						for (IUser iu : ig.getUsers()) {
							if(iu.getName() == cBUserVerwalten.getSelectedItem()) {
								String temp = tFUVKick.getText();
								ig.kickUser(iu, temp);
							}							
						}
					}
				}
			}
		});
		
		tFUVKick = new JTextField();
		GridBagConstraints gbc_tFUVKick = new GridBagConstraints();
		gbc_tFUVKick.insets = new Insets(0, 0, 5, 5);
		gbc_tFUVKick.fill = GridBagConstraints.BOTH;
		gbc_tFUVKick.gridx = 0;
		gbc_tFUVKick.gridy = 2;
		pUVUserKicken.add(tFUVKick, gbc_tFUVKick);
		tFUVKick.setColumns(10);
		GridBagConstraints gbc_btnBtnuserkicken = new GridBagConstraints();
		gbc_btnBtnuserkicken.insets = new Insets(0, 0, 5, 0);
		gbc_btnBtnuserkicken.fill = GridBagConstraints.BOTH;
		gbc_btnBtnuserkicken.gridx = 1;
		gbc_btnBtnuserkicken.gridy = 2;
		pUVUserKicken.add(btnBtnuserkicken, gbc_btnBtnuserkicken);
		
		Component verticalStrut_3 = Box.createVerticalStrut(20);
		GridBagConstraints gbc_verticalStrut_3 = new GridBagConstraints();
		gbc_verticalStrut_3.insets = new Insets(0, 0, 5, 0);
		gbc_verticalStrut_3.gridx = 1;
		gbc_verticalStrut_3.gridy = 3;
		pUVUserKicken.add(verticalStrut_3, gbc_verticalStrut_3);
		
		JButton btnUVKickenZurueck = new JButton("Zur\u00FCck zur Userauswahl");
		btnUVKickenZurueck.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cardLayoutUVSwitch.show(pUVSwitch, "pUVMain");
				cBUserVerwalten.setEnabled(true);
			}
		});
		GridBagConstraints gbc_btnUVKickenZurueck = new GridBagConstraints();
		gbc_btnUVKickenZurueck.fill = GridBagConstraints.BOTH;
		gbc_btnUVKickenZurueck.gridx = 1;
		gbc_btnUVKickenZurueck.gridy = 4;
		pUVUserKicken.add(btnUVKickenZurueck, gbc_btnUVKickenZurueck);
		
		pUVUserBannen = new JPanel();
		pUVSwitch.add(pUVUserBannen, "pUVUserBannen");
		GridBagLayout gbl_pUVUserBannen = new GridBagLayout();
		gbl_pUVUserBannen.columnWidths = new int[]{191, 0, 0};
		gbl_pUVUserBannen.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gbl_pUVUserBannen.columnWeights = new double[]{1.0, 0.0, Double.MIN_VALUE};
		gbl_pUVUserBannen.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, Double.MIN_VALUE};
		pUVUserBannen.setLayout(gbl_pUVUserBannen);
		
		JLabel lblUserBannen = new JLabel("Bannvote f\u00FCr den User: ");
		GridBagConstraints gbc_lblUserBannen = new GridBagConstraints();
		gbc_lblUserBannen.gridwidth = 2;
		gbc_lblUserBannen.insets = new Insets(0, 0, 5, 0);
		gbc_lblUserBannen.gridx = 0;
		gbc_lblUserBannen.gridy = 0;
		pUVUserBannen.add(lblUserBannen, gbc_lblUserBannen);
		
		lblUserBannenWertigkeit = new JLabel("Bitte w\u00E4hlen sie aus, wie viel der letzten Nachrichten gel\u00F6scht werden sollen:");
		GridBagConstraints gbc_lblUserBannenWertigkeit = new GridBagConstraints();
		gbc_lblUserBannenWertigkeit.gridwidth = 2;
		gbc_lblUserBannenWertigkeit.insets = new Insets(0, 0, 5, 0);
		gbc_lblUserBannenWertigkeit.gridx = 0;
		gbc_lblUserBannenWertigkeit.gridy = 1;
		pUVUserBannen.add(lblUserBannenWertigkeit, gbc_lblUserBannenWertigkeit);
		
		rdbtnKeine = new JRadioButton("keine");
		GridBagConstraints gbc_rdbtnKeine = new GridBagConstraints();
		gbc_rdbtnKeine.gridwidth = 2;
		gbc_rdbtnKeine.insets = new Insets(0, 0, 5, 0);
		gbc_rdbtnKeine.gridx = 0;
		gbc_rdbtnKeine.gridy = 2;
		pUVUserBannen.add(rdbtnKeine, gbc_rdbtnKeine);
		
		rdbtnLetzteStunden = new JRadioButton("letzte 24 Stunden");
		GridBagConstraints gbc_rdbtnLetzteStunden = new GridBagConstraints();
		gbc_rdbtnLetzteStunden.gridwidth = 2;
		gbc_rdbtnLetzteStunden.insets = new Insets(0, 0, 5, 0);
		gbc_rdbtnLetzteStunden.gridx = 0;
		gbc_rdbtnLetzteStunden.gridy = 3;
		pUVUserBannen.add(rdbtnLetzteStunden, gbc_rdbtnLetzteStunden);
		
		rdbtnLetzteTage = new JRadioButton("letzte 7 Tage");
		GridBagConstraints gbc_rdbtnLetzteTage = new GridBagConstraints();
		gbc_rdbtnLetzteTage.gridwidth = 2;
		gbc_rdbtnLetzteTage.insets = new Insets(0, 0, 5, 0);
		gbc_rdbtnLetzteTage.gridx = 0;
		gbc_rdbtnLetzteTage.gridy = 4;
		pUVUserBannen.add(rdbtnLetzteTage, gbc_rdbtnLetzteTage);
		
		rdbtnAlleNachrichten = new JRadioButton("alle Nachrichten");
		GridBagConstraints gbc_rdbtnAlleNachrichten = new GridBagConstraints();
		gbc_rdbtnAlleNachrichten.gridwidth = 2;
		gbc_rdbtnAlleNachrichten.insets = new Insets(0, 0, 5, 0);
		gbc_rdbtnAlleNachrichten.gridx = 0;
		gbc_rdbtnAlleNachrichten.gridy = 5;
		pUVUserBannen.add(rdbtnAlleNachrichten, gbc_rdbtnAlleNachrichten);
		
		JButton btnBtnUserBannen = new JButton("User Bannen");
		btnBtnUserBannen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				for (IGuild ig : discordClient.getGuilds()) {
					if(ig.getName() == cBServerListe.getSelectedItem()) {
						for (IUser iu : ig.getUsers()) {
							if(iu.getName() == cBUserVerwalten.getSelectedItem()) {
							switch (buttonGroupUserBannen.getSelection().getActionCommand()) {
									case "Nichts":
										ig.banUser(iu, 0);
										System.out.println("User gebannt ohne was zu loeschen");
										break;
									case "24 Stunden":
										ig.banUser(iu, 1);
										break;
									case "7 Tage":
										ig.banUser(iu, 7);
										break;
									case "Alles":
										afterBanCleanup(iu, ig);
										break;
									}
								}
							}							
						}
				}
			
			}
		});
		GridBagConstraints gbc_btnBtnUserBannen = new GridBagConstraints();
		gbc_btnBtnUserBannen.insets = new Insets(0, 0, 5, 0);
		gbc_btnBtnUserBannen.gridwidth = 2;
		gbc_btnBtnUserBannen.fill = GridBagConstraints.BOTH;
		gbc_btnBtnUserBannen.gridx = 0;
		gbc_btnBtnUserBannen.gridy = 6;
		pUVUserBannen.add(btnBtnUserBannen, gbc_btnBtnUserBannen);
		
		Component verticalStrut_4 = Box.createVerticalStrut(20);
		GridBagConstraints gbc_verticalStrut_4 = new GridBagConstraints();
		gbc_verticalStrut_4.insets = new Insets(0, 0, 5, 0);
		gbc_verticalStrut_4.gridx = 1;
		gbc_verticalStrut_4.gridy = 7;
		pUVUserBannen.add(verticalStrut_4, gbc_verticalStrut_4);
		
		JButton btnUVBannenZurueck = new JButton("Zur\u00FCck zur Userauswahl");
		btnUVBannenZurueck.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cardLayoutUVSwitch.show(pUVSwitch, "pUVMain");
				cBUserVerwalten.setEnabled(true);
			}
		});
		GridBagConstraints gbc_btnUVBannenZurueck = new GridBagConstraints();
		gbc_btnUVBannenZurueck.fill = GridBagConstraints.BOTH;
		gbc_btnUVBannenZurueck.gridx = 1;
		gbc_btnUVBannenZurueck.gridy = 8;
		pUVUserBannen.add(btnUVBannenZurueck, gbc_btnUVBannenZurueck);
		
		JPanel pUVNicknameChange = new JPanel();
		pUVSwitch.add(pUVNicknameChange, "pUVNicknameChange");
		GridBagLayout gbl_pUVNicknameChange = new GridBagLayout();
		gbl_pUVNicknameChange.columnWidths = new int[]{0, 0, 0};
		gbl_pUVNicknameChange.rowHeights = new int[]{0, 0, 0, 0, 0, 0};
		gbl_pUVNicknameChange.columnWeights = new double[]{1.0, 1.0, 0.0};
		gbl_pUVNicknameChange.rowWeights = new double[]{0.0, 0.0, 0.0, 1.0, 0.0, Double.MIN_VALUE};
		pUVNicknameChange.setLayout(gbl_pUVNicknameChange);
		
		JLabel lblNicknameDesUsers = new JLabel("Nickname des Users: \u00E4ndern");
		lblNicknameDesUsers.setFont(new Font("Segoe UI", Font.PLAIN, 20));
		GridBagConstraints gbc_lblNicknameDesUsers = new GridBagConstraints();
		gbc_lblNicknameDesUsers.gridwidth = 3;
		gbc_lblNicknameDesUsers.insets = new Insets(0, 0, 5, 0);
		gbc_lblNicknameDesUsers.gridx = 0;
		gbc_lblNicknameDesUsers.gridy = 0;
		pUVNicknameChange.add(lblNicknameDesUsers, gbc_lblNicknameDesUsers);
		
		JLabel lblNicknameAlt = new JLabel("Alter Nickname");
		GridBagConstraints gbc_lblNicknameAlt = new GridBagConstraints();
		gbc_lblNicknameAlt.insets = new Insets(0, 0, 5, 5);
		gbc_lblNicknameAlt.gridx = 0;
		gbc_lblNicknameAlt.gridy = 1;
		pUVNicknameChange.add(lblNicknameAlt, gbc_lblNicknameAlt);
		
		JLabel lblNeuerNickname = new JLabel("Neuer Nickname");
		GridBagConstraints gbc_lblNeuerNickname = new GridBagConstraints();
		gbc_lblNeuerNickname.insets = new Insets(0, 0, 5, 5);
		gbc_lblNeuerNickname.gridx = 1;
		gbc_lblNeuerNickname.gridy = 1;
		pUVNicknameChange.add(lblNeuerNickname, gbc_lblNeuerNickname);
		
		lblNicknameAnzeigen = new JLabel("");
		GridBagConstraints gbc_lblLblnicknameanzeigen = new GridBagConstraints();
		gbc_lblLblnicknameanzeigen.insets = new Insets(0, 0, 5, 5);
		gbc_lblLblnicknameanzeigen.gridx = 0;
		gbc_lblLblnicknameanzeigen.gridy = 2;
		pUVNicknameChange.add(lblNicknameAnzeigen, gbc_lblLblnicknameanzeigen);
		
		tFUVNickname = new JTextField();
		GridBagConstraints gbc_tFUVNickname = new GridBagConstraints();
		gbc_tFUVNickname.insets = new Insets(0, 0, 5, 5);
		gbc_tFUVNickname.fill = GridBagConstraints.BOTH;
		gbc_tFUVNickname.gridx = 1;
		gbc_tFUVNickname.gridy = 2;
		pUVNicknameChange.add(tFUVNickname, gbc_tFUVNickname);
		tFUVNickname.setColumns(10);
		
		JButton btnNicknameBestaetigen = new JButton("Best\u00E4tigen");
		btnNicknameBestaetigen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				changeUserNickname();
			}
		});
		GridBagConstraints gbc_btnNicknameBestaetigen = new GridBagConstraints();
		gbc_btnNicknameBestaetigen.insets = new Insets(0, 0, 5, 0);
		gbc_btnNicknameBestaetigen.fill = GridBagConstraints.BOTH;
		gbc_btnNicknameBestaetigen.gridx = 2;
		gbc_btnNicknameBestaetigen.gridy = 2;
		pUVNicknameChange.add(btnNicknameBestaetigen, gbc_btnNicknameBestaetigen);
		
		Component verticalStrut_5 = Box.createVerticalStrut(20);
		GridBagConstraints gbc_verticalStrut_5 = new GridBagConstraints();
		gbc_verticalStrut_5.insets = new Insets(0, 0, 5, 5);
		gbc_verticalStrut_5.gridx = 1;
		gbc_verticalStrut_5.gridy = 3;
		pUVNicknameChange.add(verticalStrut_5, gbc_verticalStrut_5);
		
		JButton btnZurckZurUserauswahl = new JButton("Zur\u00FCck zur Userauswahl");
		btnZurckZurUserauswahl.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cardLayoutUVSwitch.show(pUVSwitch, "pUVMain");
				cBUserVerwalten.setEnabled(true);
			}
		});
		GridBagConstraints gbc_btnZurckZurUserauswahl = new GridBagConstraints();
		gbc_btnZurckZurUserauswahl.gridx = 2;
		gbc_btnZurckZurUserauswahl.gridy = 4;
		pUVNicknameChange.add(btnZurckZurUserauswahl, gbc_btnZurckZurUserauswahl);
		
		JPanel pChannelVerwalten = new JPanel();
		pSwitch.add(pChannelVerwalten, "pChannelVerwalten");
		GridBagLayout gbl_pChannelVerwalten = new GridBagLayout();
		gbl_pChannelVerwalten.columnWidths = new int[]{511, 0};
		gbl_pChannelVerwalten.rowHeights = new int[]{150, -308, 0};
		gbl_pChannelVerwalten.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_pChannelVerwalten.rowWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		pChannelVerwalten.setLayout(gbl_pChannelVerwalten);
		
		pCVSwitchMenue = new JPanel();
		GridBagConstraints gbc_pCVSwitchMenue = new GridBagConstraints();
		gbc_pCVSwitchMenue.insets = new Insets(0, 0, 5, 0);
		gbc_pCVSwitchMenue.fill = GridBagConstraints.BOTH;
		gbc_pCVSwitchMenue.gridx = 0;
		gbc_pCVSwitchMenue.gridy = 0;
		pChannelVerwalten.add(pCVSwitchMenue, gbc_pCVSwitchMenue);
		pCVSwitchMenue.setLayout(cardLayoutCVSwitchMenue);
		
		pCVAlleChannel = new JPanel();
		pCVSwitchMenue.add(pCVAlleChannel, "pCVAlleChannel");
		GridBagLayout gbl_pCVAlleChannel = new GridBagLayout();
		gbl_pCVAlleChannel.columnWidths = new int[]{0, 0, 0};
		gbl_pCVAlleChannel.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0};
		gbl_pCVAlleChannel.columnWeights = new double[]{1.0, 0.0, Double.MIN_VALUE};
		gbl_pCVAlleChannel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		pCVAlleChannel.setLayout(gbl_pCVAlleChannel);
		
		lblAlleChannel = new JLabel();
		GridBagConstraints gbc_lblAlleChannel = new GridBagConstraints();
		gbc_lblAlleChannel.gridwidth = 2;
		gbc_lblAlleChannel.insets = new Insets(0, 0, 5, 0);
		gbc_lblAlleChannel.gridx = 0;
		gbc_lblAlleChannel.gridy = 0;
		pCVAlleChannel.add(lblAlleChannel, gbc_lblAlleChannel);
		lblAlleChannel.setFont(new Font("Segoe UI", Font.PLAIN, 20));
		
		cBCVAlleChannel = new JComboBox<String>();
		cBCVAlleChannel.setSelectedIndex(-1);
		GridBagConstraints gbc_cBCVAlleChannel = new GridBagConstraints();
		gbc_cBCVAlleChannel.fill = GridBagConstraints.HORIZONTAL;
		gbc_cBCVAlleChannel.insets = new Insets(0, 0, 5, 5);
		gbc_cBCVAlleChannel.gridx = 0;
		gbc_cBCVAlleChannel.gridy = 1;
		pCVAlleChannel.add(cBCVAlleChannel, gbc_cBCVAlleChannel);
		
		btnChannelLoeschen = new JButton("Channel l\u00F6schen");
		btnChannelLoeschen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				for (IGuild ig : discordClient.getGuilds()) {
					if (ig.getName().equals(cBServerListe.getSelectedItem())) {
						for (IChannel ic : ig.getChannels()) {
							if (ic.getName().equals(cBCVAlleChannel.getSelectedItem())) {
								ic.delete();
								fillCBCVAlleChannel();
								break;
							}
						}
					}
				}
				
			}
		});
		GridBagConstraints gbc_btnChannelLoeschen = new GridBagConstraints();
		gbc_btnChannelLoeschen.fill = GridBagConstraints.BOTH;
		gbc_btnChannelLoeschen.insets = new Insets(0, 0, 5, 0);
		gbc_btnChannelLoeschen.gridx = 1;
		gbc_btnChannelLoeschen.gridy = 1;
		pCVAlleChannel.add(btnChannelLoeschen, gbc_btnChannelLoeschen);
		btnChannelLoeschen.setEnabled(false);
		
		btnChannelBearbeiten = new JButton("Channel bearbeiten");
		btnChannelBearbeiten.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				cardLayoutCVSwitchMain.show(pCVSwitchMain, "pCVChannelBearbeiten");
			}
		});
		GridBagConstraints gbc_btnChannelBearbeiten = new GridBagConstraints();
		gbc_btnChannelBearbeiten.fill = GridBagConstraints.BOTH;
		gbc_btnChannelBearbeiten.insets = new Insets(0, 0, 5, 0);
		gbc_btnChannelBearbeiten.gridx = 1;
		gbc_btnChannelBearbeiten.gridy = 2;
		pCVAlleChannel.add(btnChannelBearbeiten, gbc_btnChannelBearbeiten);
		btnChannelBearbeiten.setEnabled(false);
		
		JSeparator separator_5 = new JSeparator();
		GridBagConstraints gbc_separator_5 = new GridBagConstraints();
		gbc_separator_5.fill = GridBagConstraints.BOTH;
		gbc_separator_5.gridwidth = 2;
		gbc_separator_5.insets = new Insets(0, 0, 5, 0);
		gbc_separator_5.gridx = 0;
		gbc_separator_5.gridy = 3;
		pCVAlleChannel.add(separator_5, gbc_separator_5);
		
		JButton btnVoiceChannelsAnzeigen = new JButton("zu Voice Channels");
		GridBagConstraints gbc_btnVoiceChannelsAnzeigen = new GridBagConstraints();
		gbc_btnVoiceChannelsAnzeigen.fill = GridBagConstraints.BOTH;
		gbc_btnVoiceChannelsAnzeigen.insets = new Insets(0, 0, 5, 5);
		gbc_btnVoiceChannelsAnzeigen.gridx = 0;
		gbc_btnVoiceChannelsAnzeigen.gridy = 4;
		pCVAlleChannel.add(btnVoiceChannelsAnzeigen, gbc_btnVoiceChannelsAnzeigen);
		
		JButton btnChannelErstellen = new JButton("Channel erstellen");
		GridBagConstraints gbc_btnChannelErstellen = new GridBagConstraints();
		gbc_btnChannelErstellen.fill = GridBagConstraints.BOTH;
		gbc_btnChannelErstellen.insets = new Insets(0, 0, 5, 0);
		gbc_btnChannelErstellen.gridx = 1;
		gbc_btnChannelErstellen.gridy = 4;
		pCVAlleChannel.add(btnChannelErstellen, gbc_btnChannelErstellen);
		btnChannelErstellen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				cardLayoutCVSwitchMain.show(pCVSwitchMain, "pCVChannelErstellen");
				
				tFCVChannelName.setText("");
				tFCVChannelTopic.setText("");
				chBCVChannelNSWF.setSelected(false);
				chBCVChannelinKategorie.setSelected(false);
				cBCVChannelinKategorie.removeAll();
				for (IGuild ig : discordClient.getGuilds()) {
					if(ig.getName().equals(cBServerListe.getSelectedItem())){
						for (ICategory ic : ig.getCategories()) {
							cBCVChannelinKategorie.addItem(ic.getName());
						}
					}
				}
				slCVPositioninKategorie.setValue(0);
				
				
			}
		});
		
		JSeparator separator_4 = new JSeparator();
		GridBagConstraints gbc_separator_4 = new GridBagConstraints();
		gbc_separator_4.fill = GridBagConstraints.BOTH;
		gbc_separator_4.gridwidth = 2;
		gbc_separator_4.gridx = 0;
		gbc_separator_4.gridy = 5;
		pCVAlleChannel.add(separator_4, gbc_separator_4);
		separator_4.setMinimumSize(new Dimension(0, 2));
		cBCVAlleChannel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				btnChannelLoeschen.setEnabled(true);
				btnChannelBearbeiten.setEnabled(true);
			}
		});
		
		JPanel pCVAlleVoiceChannel = new JPanel();
		pCVSwitchMenue.add(pCVAlleVoiceChannel, "pCVAlleVoiceChannel");
		GridBagLayout gbl_pCVAlleVoiceChannel = new GridBagLayout();
		gbl_pCVAlleVoiceChannel.columnWidths = new int[]{0};
		gbl_pCVAlleVoiceChannel.rowHeights = new int[]{0};
		gbl_pCVAlleVoiceChannel.columnWeights = new double[]{Double.MIN_VALUE};
		gbl_pCVAlleVoiceChannel.rowWeights = new double[]{Double.MIN_VALUE};
		pCVAlleVoiceChannel.setLayout(gbl_pCVAlleVoiceChannel);
		
		pCVSwitchMain = new JPanel();
		GridBagConstraints gbc_pCVSwitchMain = new GridBagConstraints();
		gbc_pCVSwitchMain.fill = GridBagConstraints.BOTH;
		gbc_pCVSwitchMain.gridx = 0;
		gbc_pCVSwitchMain.gridy = 1;
		pChannelVerwalten.add(pCVSwitchMain, gbc_pCVSwitchMain);
		pCVSwitchMain.setLayout(cardLayoutCVSwitchMain);
		
		JPanel pCVHauptFenster = new JPanel();
		pCVSwitchMain.add(pCVHauptFenster, "pCVHauptFenster");
		GridBagLayout gbl_pCVHauptFenster = new GridBagLayout();
		gbl_pCVHauptFenster.columnWidths = new int[]{0, 0};
		gbl_pCVHauptFenster.rowHeights = new int[]{0, 0};
		gbl_pCVHauptFenster.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_pCVHauptFenster.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		pCVHauptFenster.setLayout(gbl_pCVHauptFenster);
		
		JLabel lblChannelVerwalten = new JLabel("Channel Verwalten");
		lblChannelVerwalten.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 20));
		GridBagConstraints gbc_lblChannelVerwalten = new GridBagConstraints();
		gbc_lblChannelVerwalten.gridx = 0;
		gbc_lblChannelVerwalten.gridy = 0;
		pCVHauptFenster.add(lblChannelVerwalten, gbc_lblChannelVerwalten);
		
		JPanel pCVChannelErstellen = new JPanel();
		pCVSwitchMain.add(pCVChannelErstellen, "pCVChannelErstellen");
		GridBagLayout gbl_pCVChannelErstellen = new GridBagLayout();
		gbl_pCVChannelErstellen.columnWidths = new int[]{0, 0, 0, 0};
		gbl_pCVChannelErstellen.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0};
		gbl_pCVChannelErstellen.columnWeights = new double[]{1.0, 1.0, 0.0, Double.MIN_VALUE};
		gbl_pCVChannelErstellen.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		pCVChannelErstellen.setLayout(gbl_pCVChannelErstellen);
		
		JLabel lblChannelErstellen = new JLabel("Channel erstellen");
		lblChannelErstellen.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 20));
		GridBagConstraints gbc_lblChannelErstellen = new GridBagConstraints();
		gbc_lblChannelErstellen.gridwidth = 3;
		gbc_lblChannelErstellen.insets = new Insets(0, 0, 5, 0);
		gbc_lblChannelErstellen.gridx = 0;
		gbc_lblChannelErstellen.gridy = 0;
		pCVChannelErstellen.add(lblChannelErstellen, gbc_lblChannelErstellen);
		
		JLabel lblCVChannelName = new JLabel("Channel Name hier eintragen:");
		GridBagConstraints gbc_lblCVChannelName = new GridBagConstraints();
		gbc_lblCVChannelName.insets = new Insets(0, 0, 5, 5);
		gbc_lblCVChannelName.gridx = 0;
		gbc_lblCVChannelName.gridy = 1;
		pCVChannelErstellen.add(lblCVChannelName, gbc_lblCVChannelName);
		
		tFCVChannelName = new JTextField();
		GridBagConstraints gbc_tFCVChannelName = new GridBagConstraints();
		gbc_tFCVChannelName.insets = new Insets(0, 0, 5, 5);
		gbc_tFCVChannelName.fill = GridBagConstraints.HORIZONTAL;
		gbc_tFCVChannelName.gridx = 1;
		gbc_tFCVChannelName.gridy = 1;
		pCVChannelErstellen.add(tFCVChannelName, gbc_tFCVChannelName);
		tFCVChannelName.setColumns(10);
		
		Component horizontalStrut = Box.createHorizontalStrut(20);
		GridBagConstraints gbc_horizontalStrut = new GridBagConstraints();
		gbc_horizontalStrut.insets = new Insets(0, 0, 5, 0);
		gbc_horizontalStrut.gridx = 2;
		gbc_horizontalStrut.gridy = 1;
		pCVChannelErstellen.add(horizontalStrut, gbc_horizontalStrut);
		
		JLabel lblChannelTopic = new JLabel("Channel Topic:");
		GridBagConstraints gbc_lblChannelTopic = new GridBagConstraints();
		gbc_lblChannelTopic.insets = new Insets(0, 0, 5, 5);
		gbc_lblChannelTopic.gridx = 0;
		gbc_lblChannelTopic.gridy = 2;
		pCVChannelErstellen.add(lblChannelTopic, gbc_lblChannelTopic);
		
		tFCVChannelTopic = new JTextField();
		GridBagConstraints gbc_tFCVChannelTopic = new GridBagConstraints();
		gbc_tFCVChannelTopic.insets = new Insets(0, 0, 5, 5);
		gbc_tFCVChannelTopic.anchor = GridBagConstraints.NORTH;
		gbc_tFCVChannelTopic.fill = GridBagConstraints.HORIZONTAL;
		gbc_tFCVChannelTopic.gridx = 1;
		gbc_tFCVChannelTopic.gridy = 2;
		pCVChannelErstellen.add(tFCVChannelTopic, gbc_tFCVChannelTopic);
		tFCVChannelTopic.setColumns(10);
		
		JLabel lblCVChannelNSFW = new JLabel("Channel ist NSFW?");
		GridBagConstraints gbc_lblCVChannelNSFW = new GridBagConstraints();
		gbc_lblCVChannelNSFW.insets = new Insets(0, 0, 5, 5);
		gbc_lblCVChannelNSFW.gridx = 0;
		gbc_lblCVChannelNSFW.gridy = 3;
		pCVChannelErstellen.add(lblCVChannelNSFW, gbc_lblCVChannelNSFW);
		
		chBCVChannelNSWF = new JCheckBox("Ja");
		GridBagConstraints gbc_chBCVChannelNSWF = new GridBagConstraints();
		gbc_chBCVChannelNSWF.insets = new Insets(0, 0, 5, 5);
		gbc_chBCVChannelNSWF.gridx = 1;
		gbc_chBCVChannelNSWF.gridy = 3;
		pCVChannelErstellen.add(chBCVChannelNSWF, gbc_chBCVChannelNSWF);
		
		JLabel lblCVChannelinKategorie = new JLabel("Channel einer Kategorie hinzuf\u00FCgen?");
		GridBagConstraints gbc_lblCVChannelinKategorie = new GridBagConstraints();
		gbc_lblCVChannelinKategorie.insets = new Insets(0, 0, 5, 5);
		gbc_lblCVChannelinKategorie.gridx = 0;
		gbc_lblCVChannelinKategorie.gridy = 4;
		pCVChannelErstellen.add(lblCVChannelinKategorie, gbc_lblCVChannelinKategorie);
		
		chBCVChannelinKategorie = new JCheckBox("Ja");
		chBCVChannelinKategorie.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(chBCVChannelinKategorie.isSelected()) {
					cBCVChannelinKategorie.setEnabled(true);
					slCVPositioninKategorie.setEnabled(true);
				}else {
					cBCVChannelinKategorie.setEnabled(false);
					slCVPositioninKategorie.setEnabled(false);
				}
			}
		});
		GridBagConstraints gbc_chBCVChannelinKategorie = new GridBagConstraints();
		gbc_chBCVChannelinKategorie.insets = new Insets(0, 0, 5, 5);
		gbc_chBCVChannelinKategorie.gridx = 1;
		gbc_chBCVChannelinKategorie.gridy = 4;
		pCVChannelErstellen.add(chBCVChannelinKategorie, gbc_chBCVChannelinKategorie);
		
		JLabel lblCVKategorien = new JLabel("Kategorie:");
		GridBagConstraints gbc_lblCVKategorien = new GridBagConstraints();
		gbc_lblCVKategorien.insets = new Insets(0, 0, 5, 5);
		gbc_lblCVKategorien.gridx = 0;
		gbc_lblCVKategorien.gridy = 5;
		pCVChannelErstellen.add(lblCVKategorien, gbc_lblCVKategorien);
		
		cBCVChannelinKategorie = new JComboBox<String>();
		cBCVChannelinKategorie.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				for (IGuild ig : discordClient.getGuilds()) {
					if (ig.getName().equals(cBServerListe.getSelectedItem())) {
						for (ICategory ic : ig.getCategories()) {
							if (ic.getName().equals(cBCVChannelinKategorie.getSelectedItem())) {
								slCVPositioninKategorie.setMaximum(ic.getChannels().size());
								System.out.println("LOOLOLIYLI "+ ic.getChannels().size());
							}
						}
					}
				}
			}
		});
		cBCVChannelinKategorie.setEnabled(false);
		GridBagConstraints gbc_cBCVChannelinKategorie = new GridBagConstraints();
		gbc_cBCVChannelinKategorie.insets = new Insets(0, 0, 5, 5);
		gbc_cBCVChannelinKategorie.fill = GridBagConstraints.BOTH;
		gbc_cBCVChannelinKategorie.gridx = 1;
		gbc_cBCVChannelinKategorie.gridy = 5;
		pCVChannelErstellen.add(cBCVChannelinKategorie, gbc_cBCVChannelinKategorie);
		
		JLabel lblCVPositioninKategorie = new JLabel("Position in Kategorie:");
		GridBagConstraints gbc_lblCVPositioninKategorie = new GridBagConstraints();
		gbc_lblCVPositioninKategorie.insets = new Insets(0, 0, 5, 5);
		gbc_lblCVPositioninKategorie.gridx = 0;
		gbc_lblCVPositioninKategorie.gridy = 6;
		pCVChannelErstellen.add(lblCVPositioninKategorie, gbc_lblCVPositioninKategorie);
		
		slCVPositioninKategorie = new JSlider();
		slCVPositioninKategorie.setValue(0);
		slCVPositioninKategorie.setEnabled(false);
		GridBagConstraints gbc_slCVPositioninKategorie = new GridBagConstraints();
		gbc_slCVPositioninKategorie.fill = GridBagConstraints.BOTH;
		gbc_slCVPositioninKategorie.insets = new Insets(0, 0, 5, 5);
		gbc_slCVPositioninKategorie.gridx = 1;
		gbc_slCVPositioninKategorie.gridy = 6;
		pCVChannelErstellen.add(slCVPositioninKategorie, gbc_slCVPositioninKategorie);
		
		JButton btnCVChannelErstellen = new JButton("Channel erstellen");
		btnCVChannelErstellen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				boolean channeltyp = true; 
				int bitrate = 64;
				int userlimit = 0;
				ICategory category = null;
				int position = 0;
				if (chBCVChannelinKategorie.isSelected()) {
					for (IGuild ig : discordClient.getGuilds()) {
						if (ig.getName().equals(cBServerListe.getSelectedItem())) {
							for (ICategory ic : ig.getCategories()) {
								if(ic.getName().equals(cBCVChannelinKategorie.getSelectedItem()));
								category = ic;
							}
						}
					}
					position = slCVPositioninKategorie.getValue();
				}
				
				
				
				for (IGuild ig : discordClient.getGuilds()) {
					if (ig.getName().equals(cBServerListe.getSelectedItem())) {
						lifelifelp.botfuctions.BotFunctions.CreateChannel(ig, tFCVChannelName.getText(), channeltyp, tFCVChannelTopic.getText(), chBCVChannelNSWF.isSelected(), bitrate, userlimit, category, position);
					}
				}
			}
		});
		GridBagConstraints gbc_btnCVChannelErstellen = new GridBagConstraints();
		gbc_btnCVChannelErstellen.fill = GridBagConstraints.BOTH;
		gbc_btnCVChannelErstellen.insets = new Insets(0, 0, 0, 5);
		gbc_btnCVChannelErstellen.gridx = 0;
		gbc_btnCVChannelErstellen.gridy = 7;
		pCVChannelErstellen.add(btnCVChannelErstellen, gbc_btnCVChannelErstellen);
		
		JPanel pCVChannelBearbeiten = new JPanel();
		pCVSwitchMain.add(pCVChannelBearbeiten, "pCVChannelBearbeiten");
		GridBagLayout gbl_pCVChannelBearbeiten = new GridBagLayout();
		gbl_pCVChannelBearbeiten.columnWidths = new int[]{0, 0, 0};
		gbl_pCVChannelBearbeiten.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0};
		gbl_pCVChannelBearbeiten.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		gbl_pCVChannelBearbeiten.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		pCVChannelBearbeiten.setLayout(gbl_pCVChannelBearbeiten);
		
		JLabel lblChannelBearbeiten = new JLabel("Channel: bearbeiten");
		lblChannelBearbeiten.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 20));
		GridBagConstraints gbc_lblChannelBearbeiten = new GridBagConstraints();
		gbc_lblChannelBearbeiten.insets = new Insets(0, 0, 5, 0);
		gbc_lblChannelBearbeiten.gridwidth = 2;
		gbc_lblChannelBearbeiten.gridx = 0;
		gbc_lblChannelBearbeiten.gridy = 0;
		pCVChannelBearbeiten.add(lblChannelBearbeiten, gbc_lblChannelBearbeiten);
		
		JLabel lblCVChannelNameNew = new JLabel("Neuer Channelname:");
		GridBagConstraints gbc_lblCVChannelNameNew = new GridBagConstraints();
		gbc_lblCVChannelNameNew.anchor = GridBagConstraints.EAST;
		gbc_lblCVChannelNameNew.insets = new Insets(0, 0, 5, 5);
		gbc_lblCVChannelNameNew.gridx = 0;
		gbc_lblCVChannelNameNew.gridy = 1;
		pCVChannelBearbeiten.add(lblCVChannelNameNew, gbc_lblCVChannelNameNew);
		
		tFCVChannelNameNew = new JTextField();
		GridBagConstraints gbc_tFCVChannelNameNew = new GridBagConstraints();
		gbc_tFCVChannelNameNew.insets = new Insets(0, 0, 5, 0);
		gbc_tFCVChannelNameNew.fill = GridBagConstraints.BOTH;
		gbc_tFCVChannelNameNew.gridx = 1;
		gbc_tFCVChannelNameNew.gridy = 1;
		pCVChannelBearbeiten.add(tFCVChannelNameNew, gbc_tFCVChannelNameNew);
		tFCVChannelNameNew.setColumns(10);
		
		JLabel lblCVChannelTopicNew = new JLabel("Neue Channel Topic:");
		GridBagConstraints gbc_lblCVChannelTopicNew = new GridBagConstraints();
		gbc_lblCVChannelTopicNew.anchor = GridBagConstraints.EAST;
		gbc_lblCVChannelTopicNew.insets = new Insets(0, 0, 5, 5);
		gbc_lblCVChannelTopicNew.gridx = 0;
		gbc_lblCVChannelTopicNew.gridy = 2;
		pCVChannelBearbeiten.add(lblCVChannelTopicNew, gbc_lblCVChannelTopicNew);
		
		tFCVChannelTopicNew = new JTextField();
		GridBagConstraints gbc_tFCVChannelTopicNew = new GridBagConstraints();
		gbc_tFCVChannelTopicNew.insets = new Insets(0, 0, 5, 0);
		gbc_tFCVChannelTopicNew.fill = GridBagConstraints.HORIZONTAL;
		gbc_tFCVChannelTopicNew.gridx = 1;
		gbc_tFCVChannelTopicNew.gridy = 2;
		pCVChannelBearbeiten.add(tFCVChannelTopicNew, gbc_tFCVChannelTopicNew);
		tFCVChannelTopicNew.setColumns(10);
		
		JLabel lblCVChannelIstNSFW = new JLabel("Channel ist NSFW?");
		GridBagConstraints gbc_lblCVChannelIstNSFW = new GridBagConstraints();
		gbc_lblCVChannelIstNSFW.insets = new Insets(0, 0, 5, 5);
		gbc_lblCVChannelIstNSFW.gridx = 0;
		gbc_lblCVChannelIstNSFW.gridy = 3;
		pCVChannelBearbeiten.add(lblCVChannelIstNSFW, gbc_lblCVChannelIstNSFW);
		
		JCheckBox chBCVChannelIstNSFWNew = new JCheckBox("Ja");
		GridBagConstraints gbc_chBCVChannelIstNSFWNew = new GridBagConstraints();
		gbc_chBCVChannelIstNSFWNew.insets = new Insets(0, 0, 5, 0);
		gbc_chBCVChannelIstNSFWNew.gridx = 1;
		gbc_chBCVChannelIstNSFWNew.gridy = 3;
		pCVChannelBearbeiten.add(chBCVChannelIstNSFWNew, gbc_chBCVChannelIstNSFWNew);
		
		JLabel lblChannelInKategorie = new JLabel("Channel in Kategorie?");
		GridBagConstraints gbc_lblChannelInKategorie = new GridBagConstraints();
		gbc_lblChannelInKategorie.insets = new Insets(0, 0, 5, 5);
		gbc_lblChannelInKategorie.gridx = 0;
		gbc_lblChannelInKategorie.gridy = 4;
		pCVChannelBearbeiten.add(lblChannelInKategorie, gbc_lblChannelInKategorie);
		
		JCheckBox chBCVChannelInKategorieNew = new JCheckBox("Ja");
		GridBagConstraints gbc_chBCVChannelInKategorieNew = new GridBagConstraints();
		gbc_chBCVChannelInKategorieNew.insets = new Insets(0, 0, 5, 0);
		gbc_chBCVChannelInKategorieNew.gridx = 1;
		gbc_chBCVChannelInKategorieNew.gridy = 4;
		pCVChannelBearbeiten.add(chBCVChannelInKategorieNew, gbc_chBCVChannelInKategorieNew);
		
		JLabel lblWelcheKategorie = new JLabel("Welche Kategorie?");
		GridBagConstraints gbc_lblWelcheKategorie = new GridBagConstraints();
		gbc_lblWelcheKategorie.anchor = GridBagConstraints.EAST;
		gbc_lblWelcheKategorie.insets = new Insets(0, 0, 5, 5);
		gbc_lblWelcheKategorie.gridx = 0;
		gbc_lblWelcheKategorie.gridy = 5;
		pCVChannelBearbeiten.add(lblWelcheKategorie, gbc_lblWelcheKategorie);
		
		JComboBox<String> cBCVChannelInKategorieNew = new JComboBox<String>();
		GridBagConstraints gbc_cBCVChannelInKategorieNew = new GridBagConstraints();
		gbc_cBCVChannelInKategorieNew.insets = new Insets(0, 0, 5, 0);
		gbc_cBCVChannelInKategorieNew.fill = GridBagConstraints.HORIZONTAL;
		gbc_cBCVChannelInKategorieNew.gridx = 1;
		gbc_cBCVChannelInKategorieNew.gridy = 5;
		pCVChannelBearbeiten.add(cBCVChannelInKategorieNew, gbc_cBCVChannelInKategorieNew);
		
		JLabel lblCVChannelPositionNew = new JLabel("Welche Position?");
		GridBagConstraints gbc_lblCVChannelPositionNew = new GridBagConstraints();
		gbc_lblCVChannelPositionNew.insets = new Insets(0, 0, 5, 5);
		gbc_lblCVChannelPositionNew.gridx = 0;
		gbc_lblCVChannelPositionNew.gridy = 6;
		pCVChannelBearbeiten.add(lblCVChannelPositionNew, gbc_lblCVChannelPositionNew);
		
		JSlider slCVChannelPositionNew = new JSlider();
		GridBagConstraints gbc_slCVChannelPositionNew = new GridBagConstraints();
		gbc_slCVChannelPositionNew.insets = new Insets(0, 0, 5, 0);
		gbc_slCVChannelPositionNew.fill = GridBagConstraints.HORIZONTAL;
		gbc_slCVChannelPositionNew.gridx = 1;
		gbc_slCVChannelPositionNew.gridy = 6;
		pCVChannelBearbeiten.add(slCVChannelPositionNew, gbc_slCVChannelPositionNew);
		
		JButton btnCVChannelBearbeitungSpeichern = new JButton("Bearbeitungen speichern");
		GridBagConstraints gbc_btnCVChannelBearbeitungSpeichern = new GridBagConstraints();
		gbc_btnCVChannelBearbeitungSpeichern.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnCVChannelBearbeitungSpeichern.gridx = 1;
		gbc_btnCVChannelBearbeitungSpeichern.gridy = 7;
		pCVChannelBearbeiten.add(btnCVChannelBearbeitungSpeichern, gbc_btnCVChannelBearbeitungSpeichern);
		
		JPanel pKategorienVerwalten = new JPanel();
		pSwitch.add(pKategorienVerwalten, "pKategorienVerwalten");
		GridBagLayout gbl_pKategorienVerwalten = new GridBagLayout();
		gbl_pKategorienVerwalten.columnWidths = new int[]{0, 0};
		gbl_pKategorienVerwalten.rowHeights = new int[]{0, 0, 0};
		gbl_pKategorienVerwalten.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_pKategorienVerwalten.rowWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		pKategorienVerwalten.setLayout(gbl_pKategorienVerwalten);
		
		JPanel pKVMenue = new JPanel();
		GridBagConstraints gbc_pKVMenue = new GridBagConstraints();
		gbc_pKVMenue.insets = new Insets(0, 0, 5, 0);
		gbc_pKVMenue.fill = GridBagConstraints.BOTH;
		gbc_pKVMenue.gridx = 0;
		gbc_pKVMenue.gridy = 0;
		pKategorienVerwalten.add(pKVMenue, gbc_pKVMenue);
		GridBagLayout gbl_pKVMenue = new GridBagLayout();
		gbl_pKVMenue.columnWidths = new int[]{0, 0, 0};
		gbl_pKVMenue.rowHeights = new int[]{0, 0, 0, 0, 0};
		gbl_pKVMenue.columnWeights = new double[]{1.0, 0.0, Double.MIN_VALUE};
		gbl_pKVMenue.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		pKVMenue.setLayout(gbl_pKVMenue);
		
		cBKVAlleKategorien = new JComboBox<String>();
		GridBagConstraints gbc_cBKVAlleKategorien = new GridBagConstraints();
		gbc_cBKVAlleKategorien.fill = GridBagConstraints.HORIZONTAL;
		gbc_cBKVAlleKategorien.insets = new Insets(0, 0, 5, 5);
		gbc_cBKVAlleKategorien.gridx = 0;
		gbc_cBKVAlleKategorien.gridy = 0;
		pKVMenue.add(cBKVAlleKategorien, gbc_cBKVAlleKategorien);
		
		JButton btnKategorieLschen = new JButton("Kategorie loeschen");
		btnKategorieLschen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				for (IGuild ig : discordClient.getGuilds()) {
					if (ig.getName().equals(cBServerListe.getSelectedItem())) {
						for (ICategory ic : ig.getCategories()) {
							if (ic.getName().equals(cBKVAlleKategorien.getSelectedItem())) {
								ic.delete();
								fillCBKVAlleKategorien();
								break;
							}
						}
					}
				}
			}
		});
		GridBagConstraints gbc_btnKategorieLschen = new GridBagConstraints();
		gbc_btnKategorieLschen.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnKategorieLschen.insets = new Insets(0, 0, 5, 0);
		gbc_btnKategorieLschen.gridx = 1;
		gbc_btnKategorieLschen.gridy = 0;
		pKVMenue.add(btnKategorieLschen, gbc_btnKategorieLschen);
		
		JButton btnKategorieBearbeiten = new JButton("Kategorie bearbeiten");
		btnKategorieBearbeiten.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				cardLayoutKVSwitchMain.show(pKVSwitch, "pKVKategorieBearbeiten");
			}
		});
		GridBagConstraints gbc_btnKategorieBearbeiten = new GridBagConstraints();
		gbc_btnKategorieBearbeiten.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnKategorieBearbeiten.insets = new Insets(0, 0, 5, 0);
		gbc_btnKategorieBearbeiten.gridx = 1;
		gbc_btnKategorieBearbeiten.gridy = 1;
		pKVMenue.add(btnKategorieBearbeiten, gbc_btnKategorieBearbeiten);
		
		JSeparator separator_6 = new JSeparator();
		GridBagConstraints gbc_separator_6 = new GridBagConstraints();
		gbc_separator_6.fill = GridBagConstraints.BOTH;
		gbc_separator_6.gridwidth = 2;
		gbc_separator_6.insets = new Insets(0, 0, 5, 0);
		gbc_separator_6.gridx = 0;
		gbc_separator_6.gridy = 2;
		pKVMenue.add(separator_6, gbc_separator_6);
		
		JButton btnKategorieErstellen = new JButton("Kategorie erstellen");
		btnKategorieErstellen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				cardLayoutKVSwitchMain.show(pKVSwitch, "pKVKategorieErstellen");
			}
		});
		GridBagConstraints gbc_btnKategorieErstellen = new GridBagConstraints();
		gbc_btnKategorieErstellen.fill = GridBagConstraints.BOTH;
		gbc_btnKategorieErstellen.gridx = 1;
		gbc_btnKategorieErstellen.gridy = 3;
		pKVMenue.add(btnKategorieErstellen, gbc_btnKategorieErstellen);
		
		pKVSwitch = new JPanel();
		GridBagConstraints gbc_pKVSwitch = new GridBagConstraints();
		gbc_pKVSwitch.fill = GridBagConstraints.BOTH;
		gbc_pKVSwitch.gridx = 0;
		gbc_pKVSwitch.gridy = 1;
		pKategorienVerwalten.add(pKVSwitch, gbc_pKVSwitch);
		pKVSwitch.setLayout(cardLayoutKVSwitchMain);
		
		JPanel pKVMain = new JPanel();
		pKVSwitch.add(pKVMain, "pKVMain");
		GridBagLayout gbl_pKVMain = new GridBagLayout();
		gbl_pKVMain.columnWidths = new int[]{0, 0};
		gbl_pKVMain.rowHeights = new int[]{0, 0};
		gbl_pKVMain.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_pKVMain.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		pKVMain.setLayout(gbl_pKVMain);
		
		JLabel lblKategorienVerwalten = new JLabel("Kategorien Verwalten");
		lblKategorienVerwalten.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 20));
		GridBagConstraints gbc_lblKategorienVerwalten = new GridBagConstraints();
		gbc_lblKategorienVerwalten.gridx = 0;
		gbc_lblKategorienVerwalten.gridy = 0;
		pKVMain.add(lblKategorienVerwalten, gbc_lblKategorienVerwalten);
		
		JPanel pKVKategorieErstellen = new JPanel();
		pKVSwitch.add(pKVKategorieErstellen, "pKVKategorieErstellen");
		GridBagLayout gbl_pKVKategorieErstellen = new GridBagLayout();
		gbl_pKVKategorieErstellen.columnWidths = new int[]{0, 0, 0};
		gbl_pKVKategorieErstellen.rowHeights = new int[]{0, 0, 0, 0};
		gbl_pKVKategorieErstellen.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		gbl_pKVKategorieErstellen.rowWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
		pKVKategorieErstellen.setLayout(gbl_pKVKategorieErstellen);
		
		JLabel lblKategorieErstellen = new JLabel("Kategorie erstellen");
		lblKategorieErstellen.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 20));
		GridBagConstraints gbc_lblKategorieErstellen = new GridBagConstraints();
		gbc_lblKategorieErstellen.insets = new Insets(0, 0, 5, 0);
		gbc_lblKategorieErstellen.gridwidth = 2;
		gbc_lblKategorieErstellen.gridx = 0;
		gbc_lblKategorieErstellen.gridy = 0;
		pKVKategorieErstellen.add(lblKategorieErstellen, gbc_lblKategorieErstellen);
		
		JLabel lblKategorieName = new JLabel("Kategorie Name:");
		GridBagConstraints gbc_lblKategorieName = new GridBagConstraints();
		gbc_lblKategorieName.anchor = GridBagConstraints.EAST;
		gbc_lblKategorieName.insets = new Insets(0, 0, 5, 5);
		gbc_lblKategorieName.gridx = 0;
		gbc_lblKategorieName.gridy = 1;
		pKVKategorieErstellen.add(lblKategorieName, gbc_lblKategorieName);
		
				
		tFKVKategorieName = new JTextField();
		GridBagConstraints gbc_tFKVKategorieName = new GridBagConstraints();
		gbc_tFKVKategorieName.insets = new Insets(0, 0, 5, 0);
		gbc_tFKVKategorieName.fill = GridBagConstraints.HORIZONTAL;
		gbc_tFKVKategorieName.gridx = 1;
		gbc_tFKVKategorieName.gridy = 1;
		pKVKategorieErstellen.add(tFKVKategorieName, gbc_tFKVKategorieName);
		tFKVKategorieName.setColumns(10);
		
		JButton btnKategorieErstellen_1 = new JButton("Kategorie erstellen");
		btnKategorieErstellen_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				for (IGuild ig : discordClient.getGuilds()) {
					if (ig.getName().equals(cBServerListe.getSelectedItem())) {
						ICategory ic = ig.createCategory(tFKVKategorieName.getText());
						ic.changeNSFW(true);
					}
				}
			}
		});
		GridBagConstraints gbc_btnKategorieErstellen_1 = new GridBagConstraints();
		gbc_btnKategorieErstellen_1.fill = GridBagConstraints.BOTH;
		gbc_btnKategorieErstellen_1.gridx = 1;
		gbc_btnKategorieErstellen_1.gridy = 2;
		pKVKategorieErstellen.add(btnKategorieErstellen_1, gbc_btnKategorieErstellen_1);
		
		JPanel pKVKategorieBearbeiten = new JPanel();
		pKVSwitch.add(pKVKategorieBearbeiten, "pKVKategorieBearbeiten");
		GridBagLayout gbl_pKVKategorieBearbeiten = new GridBagLayout();
		gbl_pKVKategorieBearbeiten.columnWidths = new int[]{0, 0, 0};
		gbl_pKVKategorieBearbeiten.rowHeights = new int[]{0, 0, 0, 0, 0};
		gbl_pKVKategorieBearbeiten.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		gbl_pKVKategorieBearbeiten.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		pKVKategorieBearbeiten.setLayout(gbl_pKVKategorieBearbeiten);
		
		JLabel lblKVKategorieBearbeiten = new JLabel("Kategorie bearbeiten");
		lblKVKategorieBearbeiten.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 20));
		GridBagConstraints gbc_lblKVKategorieBearbeiten = new GridBagConstraints();
		gbc_lblKVKategorieBearbeiten.insets = new Insets(0, 0, 5, 0);
		gbc_lblKVKategorieBearbeiten.gridwidth = 2;
		gbc_lblKVKategorieBearbeiten.gridx = 0;
		gbc_lblKVKategorieBearbeiten.gridy = 0;
		pKVKategorieBearbeiten.add(lblKVKategorieBearbeiten, gbc_lblKVKategorieBearbeiten);
		
		JLabel lblKVKategorieNameNew = new JLabel("Neuer Kategorie Name:");
		GridBagConstraints gbc_lblKVKategorieNameNew = new GridBagConstraints();
		gbc_lblKVKategorieNameNew.anchor = GridBagConstraints.EAST;
		gbc_lblKVKategorieNameNew.insets = new Insets(0, 0, 5, 5);
		gbc_lblKVKategorieNameNew.gridx = 0;
		gbc_lblKVKategorieNameNew.gridy = 1;
		pKVKategorieBearbeiten.add(lblKVKategorieNameNew, gbc_lblKVKategorieNameNew);
		
		tFKVKategorieNameNew = new JTextField();
		GridBagConstraints gbc_tFKVKategorieNameNew = new GridBagConstraints();
		gbc_tFKVKategorieNameNew.insets = new Insets(0, 0, 5, 0);
		gbc_tFKVKategorieNameNew.fill = GridBagConstraints.HORIZONTAL;
		gbc_tFKVKategorieNameNew.gridx = 1;
		gbc_tFKVKategorieNameNew.gridy = 1;
		pKVKategorieBearbeiten.add(tFKVKategorieNameNew, gbc_tFKVKategorieNameNew);
		tFKVKategorieNameNew.setColumns(10);
		
		JLabel lblKVKategorieNSWF = new JLabel("Ist Kategorie NSFW?");
		GridBagConstraints gbc_lblKVKategorieNSWF = new GridBagConstraints();
		gbc_lblKVKategorieNSWF.insets = new Insets(0, 0, 5, 5);
		gbc_lblKVKategorieNSWF.gridx = 0;
		gbc_lblKVKategorieNSWF.gridy = 2;
		pKVKategorieBearbeiten.add(lblKVKategorieNSWF, gbc_lblKVKategorieNSWF);
		
		JCheckBox chBKVKategorieNSFW = new JCheckBox("Ja");
		GridBagConstraints gbc_chBKVKategorieNSFW = new GridBagConstraints();
		gbc_chBKVKategorieNSFW.insets = new Insets(0, 0, 5, 0);
		gbc_chBKVKategorieNSFW.gridx = 1;
		gbc_chBKVKategorieNSFW.gridy = 2;
		pKVKategorieBearbeiten.add(chBKVKategorieNSFW, gbc_chBKVKategorieNSFW);
		
		JButton btnKVBearbeitungSpeichern = new JButton("Bearbeitung speichern");
		GridBagConstraints gbc_btnKVBearbeitungSpeichern = new GridBagConstraints();
		gbc_btnKVBearbeitungSpeichern.fill = GridBagConstraints.BOTH;
		gbc_btnKVBearbeitungSpeichern.gridx = 1;
		gbc_btnKVBearbeitungSpeichern.gridy = 3;
		pKVKategorieBearbeiten.add(btnKVBearbeitungSpeichern, gbc_btnKVBearbeitungSpeichern);
		
		JPanel pRollenVerwalten = new JPanel();
		pSwitch.add(pRollenVerwalten, "pRollenVerwalten");
		GridBagLayout gbl_pRollenVerwalten = new GridBagLayout();
		gbl_pRollenVerwalten.columnWidths = new int[]{0, 0};
		gbl_pRollenVerwalten.rowHeights = new int[]{37, 0, 0};
		gbl_pRollenVerwalten.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_pRollenVerwalten.rowWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		pRollenVerwalten.setLayout(gbl_pRollenVerwalten);
		
		JPanel pRVMenue = new JPanel();
		GridBagConstraints gbc_pRVMenue = new GridBagConstraints();
		gbc_pRVMenue.insets = new Insets(0, 0, 5, 0);
		gbc_pRVMenue.fill = GridBagConstraints.BOTH;
		gbc_pRVMenue.gridx = 0;
		gbc_pRVMenue.gridy = 0;
		pRollenVerwalten.add(pRVMenue, gbc_pRVMenue);
		GridBagLayout gbl_pRVMenue = new GridBagLayout();
		gbl_pRVMenue.columnWidths = new int[]{0, 0, 0};
		gbl_pRVMenue.rowHeights = new int[]{0, 0, 0, 0, 0, 0};
		gbl_pRVMenue.columnWeights = new double[]{1.0, 0.0, Double.MIN_VALUE};
		gbl_pRVMenue.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		pRVMenue.setLayout(gbl_pRVMenue);
		
		cBRVAlleRollen = new JComboBox<String>();
		GridBagConstraints gbc_cBRVAlleRollen = new GridBagConstraints();
		gbc_cBRVAlleRollen.insets = new Insets(0, 0, 5, 5);
		gbc_cBRVAlleRollen.fill = GridBagConstraints.HORIZONTAL;
		gbc_cBRVAlleRollen.gridx = 0;
		gbc_cBRVAlleRollen.gridy = 0;
		pRVMenue.add(cBRVAlleRollen, gbc_cBRVAlleRollen);
		
		JButton btnRolleLschen = new JButton("Rolle loeschen");
		btnRolleLschen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				for (IGuild ig : discordClient.getGuilds()) {
					if(ig.getName().equals(cBServerListe.getSelectedItem())) {
						for (IRole ir : ig.getRoles()) {
							if (ir.getName().equals(cBRVAlleRollen.getName())) {
								ir.delete();
								fillCBRVAlleRollen();
								break;
							}
						}
					}
				}
			}
		});
		GridBagConstraints gbc_btnRolleLschen = new GridBagConstraints();
		gbc_btnRolleLschen.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnRolleLschen.insets = new Insets(0, 0, 5, 0);
		gbc_btnRolleLschen.gridx = 1;
		gbc_btnRolleLschen.gridy = 0;
		pRVMenue.add(btnRolleLschen, gbc_btnRolleLschen);
		
		JButton btnRolleBearbeiten = new JButton("Rolle bearbeiten");
		btnRolleBearbeiten.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JOptionPane.showMessageDialog(null, "Die Funktion ist in der UI nicht fertig, aber technisch geht es! :D");
			}
		});
		GridBagConstraints gbc_btnRolleBearbeiten = new GridBagConstraints();
		gbc_btnRolleBearbeiten.insets = new Insets(0, 0, 5, 0);
		gbc_btnRolleBearbeiten.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnRolleBearbeiten.gridx = 1;
		gbc_btnRolleBearbeiten.gridy = 1;
		pRVMenue.add(btnRolleBearbeiten, gbc_btnRolleBearbeiten);
		
		JSeparator separator_7 = new JSeparator();
		GridBagConstraints gbc_separator_7 = new GridBagConstraints();
		gbc_separator_7.insets = new Insets(0, 0, 5, 0);
		gbc_separator_7.fill = GridBagConstraints.BOTH;
		gbc_separator_7.gridwidth = 2;
		gbc_separator_7.gridx = 0;
		gbc_separator_7.gridy = 2;
		pRVMenue.add(separator_7, gbc_separator_7);
		
		JButton btnRolleErstellen = new JButton("Rolle erstellen");
		btnRolleErstellen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				cardLayoutRVSwitchMain.show(pRVSwitch, "sPRVRollenErstellen");
			}
		});
		GridBagConstraints gbc_btnRolleErstellen = new GridBagConstraints();
		gbc_btnRolleErstellen.insets = new Insets(0, 0, 5, 0);
		gbc_btnRolleErstellen.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnRolleErstellen.gridx = 1;
		gbc_btnRolleErstellen.gridy = 3;
		pRVMenue.add(btnRolleErstellen, gbc_btnRolleErstellen);
		
		pRVSwitch = new JPanel();
		GridBagConstraints gbc_pRVSwitch = new GridBagConstraints();
		gbc_pRVSwitch.fill = GridBagConstraints.BOTH;
		gbc_pRVSwitch.gridx = 0;
		gbc_pRVSwitch.gridy = 1;
		pRollenVerwalten.add(pRVSwitch, gbc_pRVSwitch);
		pRVSwitch.setLayout(cardLayoutRVSwitchMain);
		
		JPanel pRVRollenMain = new JPanel();
		pRVSwitch.add(pRVRollenMain, "pRVRollenMain");
		GridBagLayout gbl_pRVRollenMain = new GridBagLayout();
		gbl_pRVRollenMain.columnWidths = new int[]{0, 0, 0};
		gbl_pRVRollenMain.rowHeights = new int[]{0, 0};
		gbl_pRVRollenMain.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		gbl_pRVRollenMain.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		pRVRollenMain.setLayout(gbl_pRVRollenMain);
		
		JLabel lblRVRollenVerwalten = new JLabel("Rollen Verwalten");
		lblRVRollenVerwalten.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 20));
		GridBagConstraints gbc_lblRVRollenVerwalten = new GridBagConstraints();
		gbc_lblRVRollenVerwalten.gridwidth = 2;
		gbc_lblRVRollenVerwalten.insets = new Insets(0, 0, 0, 5);
		gbc_lblRVRollenVerwalten.gridx = 0;
		gbc_lblRVRollenVerwalten.gridy = 0;
		pRVRollenMain.add(lblRVRollenVerwalten, gbc_lblRVRollenVerwalten);
		
		sPRVRollenErstellen = new JScrollPane();
		pRVSwitch.add(sPRVRollenErstellen, "sPRVRollenErstellen");
		
		JPanel pRVRollenErstellen = new JPanel();
		sPRVRollenErstellen.setViewportView(pRVRollenErstellen);
		GridBagLayout gbl_pRVRollenErstellen = new GridBagLayout();
		gbl_pRVRollenErstellen.columnWidths = new int[]{0, 0, 0};
		gbl_pRVRollenErstellen.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gbl_pRVRollenErstellen.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		gbl_pRVRollenErstellen.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		pRVRollenErstellen.setLayout(gbl_pRVRollenErstellen);
		
		JLabel label_1 = new JLabel("Rollen erstellen");
		label_1.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 20));
		GridBagConstraints gbc_label_1 = new GridBagConstraints();
		gbc_label_1.gridwidth = 2;
		gbc_label_1.insets = new Insets(0, 0, 5, 0);
		gbc_label_1.gridx = 0;
		gbc_label_1.gridy = 0;
		pRVRollenErstellen.add(label_1, gbc_label_1);
		
		JLabel label_2 = new JLabel("Rollen Name:");
		GridBagConstraints gbc_label_2 = new GridBagConstraints();
		gbc_label_2.insets = new Insets(0, 0, 5, 5);
		gbc_label_2.gridx = 0;
		gbc_label_2.gridy = 1;
		pRVRollenErstellen.add(label_2, gbc_label_2);
		
		textField = new JTextField();
		textField.setColumns(10);
		GridBagConstraints gbc_textField = new GridBagConstraints();
		gbc_textField.insets = new Insets(0, 0, 5, 0);
		gbc_textField.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField.gridx = 1;
		gbc_textField.gridy = 1;
		pRVRollenErstellen.add(textField, gbc_textField);
		
		JLabel label_3 = new JLabel("Rollenfarbe auswaehlen:");
		GridBagConstraints gbc_label_3 = new GridBagConstraints();
		gbc_label_3.insets = new Insets(0, 0, 5, 5);
		gbc_label_3.gridx = 0;
		gbc_label_3.gridy = 2;
		pRVRollenErstellen.add(label_3, gbc_label_3);
		
		JButton button = new JButton("Zur Farbauswahl");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// Erstellung eines JColorChooser Dialoges, 
		        // der eine Farbe zurück gibt
		        Color ausgewaehlteFarbe = JColorChooser.showDialog(null, 
		            "Farbauswahl", null);
		        // Ausgabe der ausgewählten Farbe
		        System.out.println(ausgewaehlteFarbe);
			}
		});
		GridBagConstraints gbc_button = new GridBagConstraints();
		gbc_button.insets = new Insets(0, 0, 5, 0);
		gbc_button.gridx = 1;
		gbc_button.gridy = 2;
		pRVRollenErstellen.add(button, gbc_button);
		
		JLabel label_4 = new JLabel("Farbvorschau:");
		GridBagConstraints gbc_label_4 = new GridBagConstraints();
		gbc_label_4.insets = new Insets(0, 0, 5, 5);
		gbc_label_4.gridx = 0;
		gbc_label_4.gridy = 3;
		pRVRollenErstellen.add(label_4, gbc_label_4);
		
		JLabel label_5 = new JLabel("");
		label_5.setFont(new Font("Tahoma", Font.PLAIN, 15));
		label_5.setBackground(SystemColor.menu);
		GridBagConstraints gbc_label_5 = new GridBagConstraints();
		gbc_label_5.insets = new Insets(0, 0, 5, 0);
		gbc_label_5.gridx = 1;
		gbc_label_5.gridy = 3;
		pRVRollenErstellen.add(label_5, gbc_label_5);
		
		JLabel label_6 = new JLabel("Rolleneinstellungen");
		label_6.setFont(new Font("Segoe UI", Font.PLAIN, 15));
		GridBagConstraints gbc_label_6 = new GridBagConstraints();
		gbc_label_6.gridwidth = 2;
		gbc_label_6.insets = new Insets(0, 0, 5, 0);
		gbc_label_6.gridx = 0;
		gbc_label_6.gridy = 4;
		pRVRollenErstellen.add(label_6, gbc_label_6);
		
		JLabel label_7 = new JLabel("Mitglieder gruppieren");
		GridBagConstraints gbc_label_7 = new GridBagConstraints();
		gbc_label_7.insets = new Insets(0, 0, 5, 5);
		gbc_label_7.gridx = 0;
		gbc_label_7.gridy = 5;
		pRVRollenErstellen.add(label_7, gbc_label_7);
		
		JCheckBox checkBox = new JCheckBox("");
		GridBagConstraints gbc_checkBox = new GridBagConstraints();
		gbc_checkBox.insets = new Insets(0, 0, 5, 0);
		gbc_checkBox.gridx = 1;
		gbc_checkBox.gridy = 5;
		pRVRollenErstellen.add(checkBox, gbc_checkBox);
		
		JLabel label_8 = new JLabel("mit @ erwaehnen");
		GridBagConstraints gbc_label_8 = new GridBagConstraints();
		gbc_label_8.insets = new Insets(0, 0, 5, 5);
		gbc_label_8.gridx = 0;
		gbc_label_8.gridy = 6;
		pRVRollenErstellen.add(label_8, gbc_label_8);
		
		JCheckBox checkBox_1 = new JCheckBox("");
		GridBagConstraints gbc_checkBox_1 = new GridBagConstraints();
		gbc_checkBox_1.insets = new Insets(0, 0, 5, 0);
		gbc_checkBox_1.gridx = 1;
		gbc_checkBox_1.gridy = 6;
		pRVRollenErstellen.add(checkBox_1, gbc_checkBox_1);
		
		JLabel label_9 = new JLabel("Allgemeine Einstellungen");
		label_9.setFont(new Font("Segoe UI", Font.PLAIN, 15));
		GridBagConstraints gbc_label_9 = new GridBagConstraints();
		gbc_label_9.gridwidth = 2;
		gbc_label_9.insets = new Insets(0, 0, 5, 0);
		gbc_label_9.gridx = 0;
		gbc_label_9.gridy = 7;
		pRVRollenErstellen.add(label_9, gbc_label_9);
		
		JLabel label_10 = new JLabel("Administrator");
		GridBagConstraints gbc_label_10 = new GridBagConstraints();
		gbc_label_10.insets = new Insets(0, 0, 5, 5);
		gbc_label_10.gridx = 0;
		gbc_label_10.gridy = 8;
		pRVRollenErstellen.add(label_10, gbc_label_10);
		
		JCheckBox checkBox_2 = new JCheckBox("");
		GridBagConstraints gbc_checkBox_2 = new GridBagConstraints();
		gbc_checkBox_2.insets = new Insets(0, 0, 5, 0);
		gbc_checkBox_2.gridx = 1;
		gbc_checkBox_2.gridy = 8;
		pRVRollenErstellen.add(checkBox_2, gbc_checkBox_2);
		
		JLabel label_11 = new JLabel("Audit-Log anzeigen");
		GridBagConstraints gbc_label_11 = new GridBagConstraints();
		gbc_label_11.insets = new Insets(0, 0, 5, 5);
		gbc_label_11.gridx = 0;
		gbc_label_11.gridy = 9;
		pRVRollenErstellen.add(label_11, gbc_label_11);
		
		JCheckBox checkBox_3 = new JCheckBox("");
		GridBagConstraints gbc_checkBox_3 = new GridBagConstraints();
		gbc_checkBox_3.insets = new Insets(0, 0, 5, 0);
		gbc_checkBox_3.gridx = 1;
		gbc_checkBox_3.gridy = 9;
		pRVRollenErstellen.add(checkBox_3, gbc_checkBox_3);
		
		JLabel label_12 = new JLabel("Server verwalten");
		GridBagConstraints gbc_label_12 = new GridBagConstraints();
		gbc_label_12.insets = new Insets(0, 0, 5, 5);
		gbc_label_12.gridx = 0;
		gbc_label_12.gridy = 10;
		pRVRollenErstellen.add(label_12, gbc_label_12);
		
		JCheckBox checkBox_4 = new JCheckBox("");
		GridBagConstraints gbc_checkBox_4 = new GridBagConstraints();
		gbc_checkBox_4.insets = new Insets(0, 0, 5, 0);
		gbc_checkBox_4.gridx = 1;
		gbc_checkBox_4.gridy = 10;
		pRVRollenErstellen.add(checkBox_4, gbc_checkBox_4);
		
		JLabel label_13 = new JLabel("Rollen verwalten");
		GridBagConstraints gbc_label_13 = new GridBagConstraints();
		gbc_label_13.insets = new Insets(0, 0, 5, 5);
		gbc_label_13.gridx = 0;
		gbc_label_13.gridy = 11;
		pRVRollenErstellen.add(label_13, gbc_label_13);
		
		JCheckBox checkBox_5 = new JCheckBox("");
		GridBagConstraints gbc_checkBox_5 = new GridBagConstraints();
		gbc_checkBox_5.insets = new Insets(0, 0, 5, 0);
		gbc_checkBox_5.gridx = 1;
		gbc_checkBox_5.gridy = 11;
		pRVRollenErstellen.add(checkBox_5, gbc_checkBox_5);
		
		JLabel label_14 = new JLabel("Channel verwalten");
		GridBagConstraints gbc_label_14 = new GridBagConstraints();
		gbc_label_14.insets = new Insets(0, 0, 5, 5);
		gbc_label_14.gridx = 0;
		gbc_label_14.gridy = 12;
		pRVRollenErstellen.add(label_14, gbc_label_14);
		
		JCheckBox checkBox_6 = new JCheckBox("");
		GridBagConstraints gbc_checkBox_6 = new GridBagConstraints();
		gbc_checkBox_6.insets = new Insets(0, 0, 5, 0);
		gbc_checkBox_6.gridx = 1;
		gbc_checkBox_6.gridy = 12;
		pRVRollenErstellen.add(checkBox_6, gbc_checkBox_6);
		
		JLabel label_15 = new JLabel("Mitglieder kicken");
		GridBagConstraints gbc_label_15 = new GridBagConstraints();
		gbc_label_15.insets = new Insets(0, 0, 5, 5);
		gbc_label_15.gridx = 0;
		gbc_label_15.gridy = 13;
		pRVRollenErstellen.add(label_15, gbc_label_15);
		
		JCheckBox checkBox_7 = new JCheckBox("");
		GridBagConstraints gbc_checkBox_7 = new GridBagConstraints();
		gbc_checkBox_7.insets = new Insets(0, 0, 5, 0);
		gbc_checkBox_7.gridx = 1;
		gbc_checkBox_7.gridy = 13;
		pRVRollenErstellen.add(checkBox_7, gbc_checkBox_7);
		
		JLabel lblMitgliederBannen = new JLabel("Mitglieder bannen");
		GridBagConstraints gbc_lblMitgliederBannen = new GridBagConstraints();
		gbc_lblMitgliederBannen.insets = new Insets(0, 0, 5, 5);
		gbc_lblMitgliederBannen.gridx = 0;
		gbc_lblMitgliederBannen.gridy = 14;
		pRVRollenErstellen.add(lblMitgliederBannen, gbc_lblMitgliederBannen);
		
		JCheckBox checkBox_8 = new JCheckBox("");
		GridBagConstraints gbc_checkBox_8 = new GridBagConstraints();
		gbc_checkBox_8.insets = new Insets(0, 0, 5, 0);
		gbc_checkBox_8.gridx = 1;
		gbc_checkBox_8.gridy = 14;
		pRVRollenErstellen.add(checkBox_8, gbc_checkBox_8);
		
		JLabel lblSoforteinladungErstellen = new JLabel("Soforteinladung erstellen");
		GridBagConstraints gbc_lblSoforteinladungErstellen = new GridBagConstraints();
		gbc_lblSoforteinladungErstellen.insets = new Insets(0, 0, 5, 5);
		gbc_lblSoforteinladungErstellen.gridx = 0;
		gbc_lblSoforteinladungErstellen.gridy = 15;
		pRVRollenErstellen.add(lblSoforteinladungErstellen, gbc_lblSoforteinladungErstellen);
		
		JCheckBox chckbxNewCheckBox = new JCheckBox("");
		GridBagConstraints gbc_chckbxNewCheckBox = new GridBagConstraints();
		gbc_chckbxNewCheckBox.insets = new Insets(0, 0, 5, 0);
		gbc_chckbxNewCheckBox.gridx = 1;
		gbc_chckbxNewCheckBox.gridy = 15;
		pRVRollenErstellen.add(chckbxNewCheckBox, gbc_chckbxNewCheckBox);
		
		JLabel lblNicknameAendern = new JLabel("Nickname aendern");
		GridBagConstraints gbc_lblNicknameAendern = new GridBagConstraints();
		gbc_lblNicknameAendern.insets = new Insets(0, 0, 5, 5);
		gbc_lblNicknameAendern.gridx = 0;
		gbc_lblNicknameAendern.gridy = 16;
		pRVRollenErstellen.add(lblNicknameAendern, gbc_lblNicknameAendern);
		
		JCheckBox checkBox_9 = new JCheckBox("");
		GridBagConstraints gbc_checkBox_9 = new GridBagConstraints();
		gbc_checkBox_9.insets = new Insets(0, 0, 5, 0);
		gbc_checkBox_9.gridx = 1;
		gbc_checkBox_9.gridy = 16;
		pRVRollenErstellen.add(checkBox_9, gbc_checkBox_9);
		
		JLabel lblNicknameVerwalten = new JLabel("Nickname verwalten");
		GridBagConstraints gbc_lblNicknameVerwalten = new GridBagConstraints();
		gbc_lblNicknameVerwalten.insets = new Insets(0, 0, 5, 5);
		gbc_lblNicknameVerwalten.gridx = 0;
		gbc_lblNicknameVerwalten.gridy = 17;
		pRVRollenErstellen.add(lblNicknameVerwalten, gbc_lblNicknameVerwalten);
		
		JCheckBox chckbxNewCheckBox_1 = new JCheckBox("");
		GridBagConstraints gbc_chckbxNewCheckBox_1 = new GridBagConstraints();
		gbc_chckbxNewCheckBox_1.insets = new Insets(0, 0, 5, 0);
		gbc_chckbxNewCheckBox_1.gridx = 1;
		gbc_chckbxNewCheckBox_1.gridy = 17;
		pRVRollenErstellen.add(chckbxNewCheckBox_1, gbc_chckbxNewCheckBox_1);
		
		JLabel lblEmojisVerwalten = new JLabel("Emojis verwalten");
		GridBagConstraints gbc_lblEmojisVerwalten = new GridBagConstraints();
		gbc_lblEmojisVerwalten.insets = new Insets(0, 0, 5, 5);
		gbc_lblEmojisVerwalten.gridx = 0;
		gbc_lblEmojisVerwalten.gridy = 18;
		pRVRollenErstellen.add(lblEmojisVerwalten, gbc_lblEmojisVerwalten);
		
		JCheckBox checkBox_10 = new JCheckBox("");
		GridBagConstraints gbc_checkBox_10 = new GridBagConstraints();
		gbc_checkBox_10.insets = new Insets(0, 0, 5, 0);
		gbc_checkBox_10.gridx = 1;
		gbc_checkBox_10.gridy = 18;
		pRVRollenErstellen.add(checkBox_10, gbc_checkBox_10);
		
		JLabel lblWebhooksVerwalten = new JLabel("Webhooks verwalten");
		GridBagConstraints gbc_lblWebhooksVerwalten = new GridBagConstraints();
		gbc_lblWebhooksVerwalten.insets = new Insets(0, 0, 5, 5);
		gbc_lblWebhooksVerwalten.gridx = 0;
		gbc_lblWebhooksVerwalten.gridy = 19;
		pRVRollenErstellen.add(lblWebhooksVerwalten, gbc_lblWebhooksVerwalten);
		
		JCheckBox chckbxNewCheckBox_2 = new JCheckBox("");
		GridBagConstraints gbc_chckbxNewCheckBox_2 = new GridBagConstraints();
		gbc_chckbxNewCheckBox_2.insets = new Insets(0, 0, 5, 0);
		gbc_chckbxNewCheckBox_2.gridx = 1;
		gbc_chckbxNewCheckBox_2.gridy = 19;
		pRVRollenErstellen.add(chckbxNewCheckBox_2, gbc_chckbxNewCheckBox_2);
		
		JLabel lblVoicetextChannelSehenlesen = new JLabel("Voice/Text Channel sehen/lesen");
		GridBagConstraints gbc_lblVoicetextChannelSehenlesen = new GridBagConstraints();
		gbc_lblVoicetextChannelSehenlesen.insets = new Insets(0, 0, 5, 5);
		gbc_lblVoicetextChannelSehenlesen.gridx = 0;
		gbc_lblVoicetextChannelSehenlesen.gridy = 20;
		pRVRollenErstellen.add(lblVoicetextChannelSehenlesen, gbc_lblVoicetextChannelSehenlesen);
		
		JSeparator separator_8 = new JSeparator();
		GridBagConstraints gbc_separator_8 = new GridBagConstraints();
		gbc_separator_8.fill = GridBagConstraints.BOTH;
		gbc_separator_8.gridwidth = 2;
		gbc_separator_8.insets = new Insets(0, 0, 5, 0);
		gbc_separator_8.gridx = 0;
		gbc_separator_8.gridy = 21;
		pRVRollenErstellen.add(separator_8, gbc_separator_8);
		
		JLabel lblTextberechtigungen = new JLabel("Text-Berechtigungen");
		lblTextberechtigungen.setFont(new Font("Segoe UI", Font.PLAIN, 15));
		GridBagConstraints gbc_lblTextberechtigungen = new GridBagConstraints();
		gbc_lblTextberechtigungen.gridwidth = 2;
		gbc_lblTextberechtigungen.insets = new Insets(0, 0, 5, 0);
		gbc_lblTextberechtigungen.gridx = 0;
		gbc_lblTextberechtigungen.gridy = 22;
		pRVRollenErstellen.add(lblTextberechtigungen, gbc_lblTextberechtigungen);
		
		JLabel lblNachrichtenVersenden = new JLabel("Nachrichten versenden");
		GridBagConstraints gbc_lblNachrichtenVersenden = new GridBagConstraints();
		gbc_lblNachrichtenVersenden.insets = new Insets(0, 0, 5, 5);
		gbc_lblNachrichtenVersenden.gridx = 0;
		gbc_lblNachrichtenVersenden.gridy = 23;
		pRVRollenErstellen.add(lblNachrichtenVersenden, gbc_lblNachrichtenVersenden);
		
		JCheckBox checkBox_11 = new JCheckBox("");
		GridBagConstraints gbc_checkBox_11 = new GridBagConstraints();
		gbc_checkBox_11.insets = new Insets(0, 0, 5, 0);
		gbc_checkBox_11.gridx = 1;
		gbc_checkBox_11.gridy = 23;
		pRVRollenErstellen.add(checkBox_11, gbc_checkBox_11);
		
		JLabel lblTtsnachrichten = new JLabel("TTS-Nachrichten verwenden");
		GridBagConstraints gbc_lblTtsnachrichten = new GridBagConstraints();
		gbc_lblTtsnachrichten.insets = new Insets(0, 0, 5, 5);
		gbc_lblTtsnachrichten.gridx = 0;
		gbc_lblTtsnachrichten.gridy = 24;
		pRVRollenErstellen.add(lblTtsnachrichten, gbc_lblTtsnachrichten);
		
		JCheckBox checkBox_12 = new JCheckBox("");
		GridBagConstraints gbc_checkBox_12 = new GridBagConstraints();
		gbc_checkBox_12.insets = new Insets(0, 0, 5, 0);
		gbc_checkBox_12.gridx = 1;
		gbc_checkBox_12.gridy = 24;
		pRVRollenErstellen.add(checkBox_12, gbc_checkBox_12);
		
		JLabel lblNewLabel_1 = new JLabel("Nachrichten verwalten");
		GridBagConstraints gbc_lblNewLabel_1 = new GridBagConstraints();
		gbc_lblNewLabel_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_1.gridx = 0;
		gbc_lblNewLabel_1.gridy = 25;
		pRVRollenErstellen.add(lblNewLabel_1, gbc_lblNewLabel_1);
		
		JCheckBox checkBox_13 = new JCheckBox("");
		GridBagConstraints gbc_checkBox_13 = new GridBagConstraints();
		gbc_checkBox_13.insets = new Insets(0, 0, 5, 0);
		gbc_checkBox_13.gridx = 1;
		gbc_checkBox_13.gridy = 25;
		pRVRollenErstellen.add(checkBox_13, gbc_checkBox_13);
		
		JLabel lblLinksEinbetten = new JLabel("Links einbetten");
		GridBagConstraints gbc_lblLinksEinbetten = new GridBagConstraints();
		gbc_lblLinksEinbetten.insets = new Insets(0, 0, 5, 5);
		gbc_lblLinksEinbetten.gridx = 0;
		gbc_lblLinksEinbetten.gridy = 26;
		pRVRollenErstellen.add(lblLinksEinbetten, gbc_lblLinksEinbetten);
		
		JCheckBox checkBox_14 = new JCheckBox("");
		GridBagConstraints gbc_checkBox_14 = new GridBagConstraints();
		gbc_checkBox_14.insets = new Insets(0, 0, 5, 0);
		gbc_checkBox_14.gridx = 1;
		gbc_checkBox_14.gridy = 26;
		pRVRollenErstellen.add(checkBox_14, gbc_checkBox_14);
		
		JLabel lblDateienAnhangen = new JLabel("Dateien anhangen");
		GridBagConstraints gbc_lblDateienAnhangen = new GridBagConstraints();
		gbc_lblDateienAnhangen.insets = new Insets(0, 0, 5, 5);
		gbc_lblDateienAnhangen.gridx = 0;
		gbc_lblDateienAnhangen.gridy = 27;
		pRVRollenErstellen.add(lblDateienAnhangen, gbc_lblDateienAnhangen);
		
		JCheckBox checkBox_15 = new JCheckBox("");
		GridBagConstraints gbc_checkBox_15 = new GridBagConstraints();
		gbc_checkBox_15.insets = new Insets(0, 0, 5, 0);
		gbc_checkBox_15.gridx = 1;
		gbc_checkBox_15.gridy = 27;
		pRVRollenErstellen.add(checkBox_15, gbc_checkBox_15);
		
		JLabel lblNachrichtenverlaufLesen = new JLabel("Nachrichtenverlauf lesen");
		GridBagConstraints gbc_lblNachrichtenverlaufLesen = new GridBagConstraints();
		gbc_lblNachrichtenverlaufLesen.insets = new Insets(0, 0, 5, 5);
		gbc_lblNachrichtenverlaufLesen.gridx = 0;
		gbc_lblNachrichtenverlaufLesen.gridy = 28;
		pRVRollenErstellen.add(lblNachrichtenverlaufLesen, gbc_lblNachrichtenverlaufLesen);
		
		JCheckBox checkBox_16 = new JCheckBox("");
		GridBagConstraints gbc_checkBox_16 = new GridBagConstraints();
		gbc_checkBox_16.insets = new Insets(0, 0, 5, 0);
		gbc_checkBox_16.gridx = 1;
		gbc_checkBox_16.gridy = 28;
		pRVRollenErstellen.add(checkBox_16, gbc_checkBox_16);
		
		JLabel lblAlleErwaehnen = new JLabel("Alle erwaehnen");
		GridBagConstraints gbc_lblAlleErwaehnen = new GridBagConstraints();
		gbc_lblAlleErwaehnen.insets = new Insets(0, 0, 5, 5);
		gbc_lblAlleErwaehnen.gridx = 0;
		gbc_lblAlleErwaehnen.gridy = 29;
		pRVRollenErstellen.add(lblAlleErwaehnen, gbc_lblAlleErwaehnen);
		
		JCheckBox checkBox_17 = new JCheckBox("");
		GridBagConstraints gbc_checkBox_17 = new GridBagConstraints();
		gbc_checkBox_17.insets = new Insets(0, 0, 5, 0);
		gbc_checkBox_17.gridx = 1;
		gbc_checkBox_17.gridy = 29;
		pRVRollenErstellen.add(checkBox_17, gbc_checkBox_17);
		
		JLabel lblExterneEmojisVerwenden = new JLabel("Externe Emojis verwenden");
		GridBagConstraints gbc_lblExterneEmojisVerwenden = new GridBagConstraints();
		gbc_lblExterneEmojisVerwenden.insets = new Insets(0, 0, 5, 5);
		gbc_lblExterneEmojisVerwenden.gridx = 0;
		gbc_lblExterneEmojisVerwenden.gridy = 30;
		pRVRollenErstellen.add(lblExterneEmojisVerwenden, gbc_lblExterneEmojisVerwenden);
		
		JCheckBox checkBox_18 = new JCheckBox("");
		GridBagConstraints gbc_checkBox_18 = new GridBagConstraints();
		gbc_checkBox_18.insets = new Insets(0, 0, 5, 0);
		gbc_checkBox_18.gridx = 1;
		gbc_checkBox_18.gridy = 30;
		pRVRollenErstellen.add(checkBox_18, gbc_checkBox_18);
		
		JLabel lblReaktionenHinzufuegen = new JLabel("Reaktionen hinzufuegen");
		GridBagConstraints gbc_lblReaktionenHinzufuegen = new GridBagConstraints();
		gbc_lblReaktionenHinzufuegen.insets = new Insets(0, 0, 5, 5);
		gbc_lblReaktionenHinzufuegen.gridx = 0;
		gbc_lblReaktionenHinzufuegen.gridy = 31;
		pRVRollenErstellen.add(lblReaktionenHinzufuegen, gbc_lblReaktionenHinzufuegen);
		
		JCheckBox checkBox_19 = new JCheckBox("");
		GridBagConstraints gbc_checkBox_19 = new GridBagConstraints();
		gbc_checkBox_19.insets = new Insets(0, 0, 5, 0);
		gbc_checkBox_19.gridx = 1;
		gbc_checkBox_19.gridy = 31;
		pRVRollenErstellen.add(checkBox_19, gbc_checkBox_19);
		
		JSeparator separator_9 = new JSeparator();
		GridBagConstraints gbc_separator_9 = new GridBagConstraints();
		gbc_separator_9.gridwidth = 2;
		gbc_separator_9.insets = new Insets(0, 0, 5, 0);
		gbc_separator_9.gridx = 0;
		gbc_separator_9.gridy = 32;
		pRVRollenErstellen.add(separator_9, gbc_separator_9);
		
		JLabel lblSprachberechtigungen = new JLabel("Sprach-Berechtigungen");
		lblSprachberechtigungen.setFont(new Font("Segoe UI", Font.PLAIN, 15));
		GridBagConstraints gbc_lblSprachberechtigungen = new GridBagConstraints();
		gbc_lblSprachberechtigungen.gridwidth = 2;
		gbc_lblSprachberechtigungen.insets = new Insets(0, 0, 5, 0);
		gbc_lblSprachberechtigungen.gridx = 0;
		gbc_lblSprachberechtigungen.gridy = 33;
		pRVRollenErstellen.add(lblSprachberechtigungen, gbc_lblSprachberechtigungen);
		
		JLabel lblVerbinden = new JLabel("Verbinden");
		GridBagConstraints gbc_lblVerbinden = new GridBagConstraints();
		gbc_lblVerbinden.insets = new Insets(0, 0, 5, 5);
		gbc_lblVerbinden.gridx = 0;
		gbc_lblVerbinden.gridy = 34;
		pRVRollenErstellen.add(lblVerbinden, gbc_lblVerbinden);
		
		JCheckBox checkBox_20 = new JCheckBox("");
		GridBagConstraints gbc_checkBox_20 = new GridBagConstraints();
		gbc_checkBox_20.insets = new Insets(0, 0, 5, 0);
		gbc_checkBox_20.gridx = 1;
		gbc_checkBox_20.gridy = 34;
		pRVRollenErstellen.add(checkBox_20, gbc_checkBox_20);
		
		JLabel lblNewLabel_2 = new JLabel("Sprechen");
		GridBagConstraints gbc_lblNewLabel_2 = new GridBagConstraints();
		gbc_lblNewLabel_2.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_2.gridx = 0;
		gbc_lblNewLabel_2.gridy = 35;
		pRVRollenErstellen.add(lblNewLabel_2, gbc_lblNewLabel_2);
		
		JCheckBox checkBox_21 = new JCheckBox("");
		GridBagConstraints gbc_checkBox_21 = new GridBagConstraints();
		gbc_checkBox_21.insets = new Insets(0, 0, 5, 0);
		gbc_checkBox_21.gridx = 1;
		gbc_checkBox_21.gridy = 35;
		pRVRollenErstellen.add(checkBox_21, gbc_checkBox_21);
		
		JLabel lblNewLabel_3 = new JLabel("Mitglieder stummschalten");
		GridBagConstraints gbc_lblNewLabel_3 = new GridBagConstraints();
		gbc_lblNewLabel_3.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_3.gridx = 0;
		gbc_lblNewLabel_3.gridy = 36;
		pRVRollenErstellen.add(lblNewLabel_3, gbc_lblNewLabel_3);
		
		JCheckBox checkBox_22 = new JCheckBox("");
		GridBagConstraints gbc_checkBox_22 = new GridBagConstraints();
		gbc_checkBox_22.insets = new Insets(0, 0, 5, 0);
		gbc_checkBox_22.gridx = 1;
		gbc_checkBox_22.gridy = 36;
		pRVRollenErstellen.add(checkBox_22, gbc_checkBox_22);
		
		JButton btnRVRollenErstellen = new JButton("Rolle erstellen");
		GridBagConstraints gbc_btnRVRollenErstellen = new GridBagConstraints();
		gbc_btnRVRollenErstellen.insets = new Insets(0, 0, 0, 5);
		gbc_btnRVRollenErstellen.gridx = 0;
		gbc_btnRVRollenErstellen.gridy = 37;
		pRVRollenErstellen.add(btnRVRollenErstellen, gbc_btnRVRollenErstellen);
	}
}
