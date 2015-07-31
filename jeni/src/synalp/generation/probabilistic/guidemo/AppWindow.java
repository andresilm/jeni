package synalp.generation.probabilistic.guidemo;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JLabel;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JTextPane;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.swing.ButtonGroup;
import javax.swing.JCheckBox;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JEditorPane;
import javax.swing.JRadioButton;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.JMenu;

import synalp.generation.configuration.GeneratorConfiguration;
import synalp.generation.configuration.GeneratorConfigurations;

public class AppWindow extends JFrame
{

	private JPanel contentPane;
	private JTextField textField;
	//different configuration dialogs
	OutputOptionsWindow displayOpt;
	GeneratorConfigDialog resConfig;

	AppConfiguration appConfig;


	/**
	 * Create the frame.
	 */
	public AppWindow()
	{
		appConfig = new AppConfiguration();
		//Hardcoded configuration
		//appConfig.createConfigFromTestsuite("probabilistic");

		setTitle("Probabilistic Jeni Generator Demo");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 500, 410);

		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		JMenu AppMenu = new JMenu("Application");
		menuBar.add(AppMenu);
		resConfig = new GeneratorConfigDialog(appConfig);

		//create windows
		displayOpt = new OutputOptionsWindow(appConfig);
		AppWindow thisWindow = this;
		JMenuItem mntmQuit = new JMenuItem("Quit");
		mntmQuit.addMouseListener(new MouseAdapter()
		{
			@Override
			public void mousePressed(MouseEvent e)
			{

				thisWindow.dispose();
			}
		});
		AppMenu.add(mntmQuit);

		JMenu optionsMenu = new JMenu("Options");
		menuBar.add(optionsMenu);

		JMenuItem mntmDisplayOptions = new JMenuItem("Customize output");
		optionsMenu.add(mntmDisplayOptions);
		mntmDisplayOptions.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{

				displayOpt.setVisible(true);

			}
		});

		JMenuItem mntmResourcesSuite = new JMenuItem("Generator config");
		optionsMenu.add(mntmResourcesSuite);

		mntmResourcesSuite.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{

				resConfig.setVisible(true);

			}
		});
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		JButton btnGenerate = new JButton("Use this configuration");

		//button handlers for opening the corresponding windows 
		btnGenerate.addMouseListener(new MouseAdapter()
		{
			@Override
			public void mousePressed(MouseEvent e)
			{
				if (btnGenerate.isEnabled())
				{

					GenerationWindow resultWin = new GenerationWindow(appConfig);

					resultWin.setVisible(true);
					
					//resultWin.startGeneration();

				}

			}
		});

		JPanel panel = new JPanel();
		panel.setBorder(new EmptyBorder(5, 5, 5, 5));

		JEditorPane editorPane = new JEditorPane();

		JRadioButton rdbtnFileOfInput = new JRadioButton("File of input items");

		JLabel lblGenerateFrom = new JLabel("Generate from...");

		JRadioButton rdbtnUserGivenInput = new JRadioButton("User given input");

		JRadioButton rdbtnPreparatedInputItems = new JRadioButton("Preparated input items");

		ButtonGroup group = new ButtonGroup();

		group.add(rdbtnFileOfInput);
		group.add(rdbtnUserGivenInput);
		group.add(rdbtnPreparatedInputItems);

		JComboBox comboBox = new JComboBox();

		textField = new JTextField();
		textField.setEditable(false);
		textField.setColumns(10);

		JButton button_1 = new JButton("Browse");
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
									gl_panel.createParallelGroup(Alignment.LEADING)
											.addGroup(gl_panel	.createSequentialGroup()
																.addContainerGap()
																.addGroup(gl_panel	.createParallelGroup(Alignment.LEADING)
																					.addComponent(editorPane, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
																					.addComponent(rdbtnFileOfInput)
																					.addComponent(lblGenerateFrom)
																					.addComponent(rdbtnUserGivenInput)
																					.addComponent(rdbtnPreparatedInputItems)
																					.addGroup(gl_panel	.createSequentialGroup()
																										.addGroup(gl_panel	.createParallelGroup(Alignment.TRAILING)
																															.addGroup(gl_panel	.createSequentialGroup()
																																				.addGap(29)
																																				.addComponent(	comboBox, 0, 339,
																																								Short.MAX_VALUE))
																															.addComponent(	textField, GroupLayout.PREFERRED_SIZE,
																																			331,
																																			GroupLayout.PREFERRED_SIZE))
																										.addPreferredGap(ComponentPlacement.RELATED)
																										.addComponent(button_1)))
																.addContainerGap()));
		gl_panel.setVerticalGroup(
									gl_panel.createParallelGroup(Alignment.LEADING)
											.addGroup(gl_panel	.createSequentialGroup()
																.addContainerGap()
																.addComponent(lblGenerateFrom)
																.addGap(8)
																.addComponent(rdbtnFileOfInput)
																.addGap(1)
																.addGroup(gl_panel	.createParallelGroup(Alignment.BASELINE)
																					.addComponent(	textField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
																									GroupLayout.PREFERRED_SIZE)
																					.addComponent(button_1))
																.addGap(18)
																.addComponent(rdbtnPreparatedInputItems)
																.addPreferredGap(ComponentPlacement.UNRELATED)
																.addComponent(comboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
																.addGap(23)
																.addComponent(rdbtnUserGivenInput)
																.addPreferredGap(ComponentPlacement.UNRELATED)
																.addComponent(editorPane, GroupLayout.PREFERRED_SIZE, 83, GroupLayout.PREFERRED_SIZE)
																.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
		panel.setLayout(gl_panel);
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(282)
					.addComponent(btnGenerate, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addGap(59))
				.addGroup(Alignment.LEADING, gl_contentPane.createSequentialGroup()
					.addComponent(panel, GroupLayout.PREFERRED_SIZE, 488, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(44, Short.MAX_VALUE))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(3)
					.addComponent(panel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addComponent(btnGenerate)
					.addContainerGap())
		);
		contentPane.setLayout(gl_contentPane);

	}


	void updateApplicationConfiguration()
	{

	}
}
