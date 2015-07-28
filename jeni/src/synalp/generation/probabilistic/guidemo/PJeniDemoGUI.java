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
import javax.swing.JCheckBox;

public class PJeniDemoGUI extends JFrame
{

	private JPanel contentPane;


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
		setBounds(100, 100, 328, 406);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		JButton btnConfig = new JButton("Resources config");
		btnConfig.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				ConfigDialog config = new ConfigDialog();
				config.setVisible(true);
			}
		});

		JLabel lblConfigurationSummary = new JLabel("Configuration summary:");

		JTextPane textPane = new JTextPane();
		textPane.setEditable(false);

		JButton btnGenerate = new JButton("Generate");

		//button handlers for opening the corresponding windows 
		btnGenerate.addMouseListener(new MouseAdapter()
		{
			@Override
			public void mouseClicked(MouseEvent e)
			{
				if (btnGenerate.isEnabled())
				{
					ResultWindow resultWin = new ResultWindow();

					resultWin.setVisible(true);
				}

			}
		});
		
		
		
		btnGenerate.setEnabled(false);

		JButton btnInputType = new JButton("Select input");
		btnInputType.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				InputSelectDialog inputSelect = new InputSelectDialog();
				inputSelect.setVisible(true);
			}
		});
		
		JCheckBox chckbxShowMeJust = new JCheckBox("Just show generated text");
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblConfigurationSummary, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addGap(579))
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(btnInputType, GroupLayout.DEFAULT_SIZE, 142, Short.MAX_VALUE)
							.addGap(18)
							.addComponent(btnConfig))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addContainerGap()
							.addComponent(textPane, GroupLayout.PREFERRED_SIZE, 305, GroupLayout.PREFERRED_SIZE)))
					.addGap(426))
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addComponent(btnGenerate)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(chckbxShowMeJust)
					.addContainerGap(79, Short.MAX_VALUE))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(34)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnInputType)
						.addComponent(btnConfig))
					.addGap(18)
					.addComponent(lblConfigurationSummary)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(textPane, GroupLayout.PREFERRED_SIZE, 222, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnGenerate)
						.addComponent(chckbxShowMeJust))
					.addContainerGap(8, Short.MAX_VALUE))
		);
		contentPane.setLayout(gl_contentPane);
	}
}
