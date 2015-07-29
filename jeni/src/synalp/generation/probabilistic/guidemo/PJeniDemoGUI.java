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

import javax.swing.ButtonGroup;
import javax.swing.JCheckBox;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JEditorPane;
import javax.swing.JRadioButton;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.JMenu;

public class PJeniDemoGUI extends JFrame
{

	private JPanel contentPane;
	private JTextField textField;
	
	DisplayOptions displayOpt;
	ResourcesConfigDialog resConfig;
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args)
	{
		EventQueue.invokeLater(new Runnable()
		{
			public void run()
			{
				try
				{
					PJeniDemoGUI frame = new PJeniDemoGUI();
					frame.setVisible(true);
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		});
	}


	/**
	 * Create the frame.
	 */
	public PJeniDemoGUI()
	{
		setTitle("Probabilistic Jeni Generator Demo");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 500, 410);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnNewMenu = new JMenu("Configuration");
		menuBar.add(mnNewMenu);
		
		JMenuItem mntmResourcesSuite = new JMenuItem("Sources");
		resConfig = new ResourcesConfigDialog();
		
		mntmResourcesSuite.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				
				resConfig.setVisible(true);
				
				
				
			}
		});
		
		mnNewMenu.add(mntmResourcesSuite);
		
		//create windows
		displayOpt = new DisplayOptions();
		
		JMenuItem mntmDisplayOptions = new JMenuItem("Output");
		mntmDisplayOptions.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				
				displayOpt.setVisible(true);
				
				
			}
		});

		mnNewMenu.add(mntmDisplayOptions);
		
		JMenuItem mntmSaveCurrentConfig = new JMenuItem("Save current config");
		mnNewMenu.add(mntmSaveCurrentConfig);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		JButton btnGenerate = new JButton("Generate");
		
		//button handlers for opening the corresponding windows 
		btnGenerate.addMouseListener(new MouseAdapter()
		{
			@Override
			public void mouseClicked(MouseEvent e)
			{
				//if (btnGenerate.isEnabled())
				//{
					ResultWindow resultWin = new ResultWindow();

					resultWin.setVisible(true);
				//}

			}
		});
		
		
		
		btnGenerate.setEnabled(false);
		
		JPanel panel = new JPanel();
		panel.setBorder(new EmptyBorder(5, 5, 5, 5));
		
		JEditorPane editorPane = new JEditorPane();
		
		JRadioButton radioButton = new JRadioButton("From file...");
		
		JLabel label = new JLabel("Choose your prefered input method:");
		
		JRadioButton radioButton_1 = new JRadioButton("Or write the input");
		
		JRadioButton radioButton_2 = new JRadioButton("Use a predefined input");
		
	    ButtonGroup group = new ButtonGroup();
	    
	    group.add(radioButton);
	    group.add(radioButton_1);
	    group.add(radioButton_2);
	    

		
		JComboBox comboBox = new JComboBox();
		
		textField = new JTextField();
		textField.setEditable(false);
		textField.setColumns(10);
		
		JButton button_1 = new JButton("Browse");
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addComponent(editorPane, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(radioButton)
						.addComponent(label)
						.addComponent(radioButton_1)
						.addComponent(radioButton_2)
						.addGroup(gl_panel.createSequentialGroup()
							.addGroup(gl_panel.createParallelGroup(Alignment.TRAILING)
								.addGroup(gl_panel.createSequentialGroup()
									.addGap(29)
									.addComponent(comboBox, 0, 339, Short.MAX_VALUE))
								.addComponent(textField, GroupLayout.PREFERRED_SIZE, 331, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(button_1)))
					.addContainerGap())
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap()
					.addComponent(label)
					.addGap(8)
					.addComponent(radioButton)
					.addGap(1)
					.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(textField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(button_1))
					.addGap(18)
					.addComponent(radioButton_2)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(comboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addGap(23)
					.addComponent(radioButton_1)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(editorPane, GroupLayout.PREFERRED_SIZE, 83, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		panel.setLayout(gl_panel);
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addComponent(panel, GroupLayout.DEFAULT_SIZE, 484, Short.MAX_VALUE))
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(341)
					.addComponent(btnGenerate, GroupLayout.DEFAULT_SIZE, 143, Short.MAX_VALUE)
					.addContainerGap())
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.TRAILING)
				.addGroup(Alignment.LEADING, gl_contentPane.createSequentialGroup()
					.addComponent(panel, GroupLayout.PREFERRED_SIZE, 319, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED, 8, Short.MAX_VALUE)
					.addComponent(btnGenerate))
		);
		contentPane.setLayout(gl_contentPane);
	}
}
