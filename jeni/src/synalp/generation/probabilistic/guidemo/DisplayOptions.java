package synalp.generation.probabilistic.guidemo;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JRadioButton;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JCheckBox;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class DisplayOptions extends JDialog
{

	private final JPanel contentPanel = new JPanel();


	/**
	 * Launch the application.
	 */
	public static void main(String[] args)
	{
		try
		{
			DisplayOptions dialog = new DisplayOptions();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}


	/**
	 * Create the dialog.
	 */
	public DisplayOptions()
	{
		setTitle("Output options");
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		
		JRadioButton rdbtnFullOutput = new JRadioButton("Full output");
		
		JRadioButton rdbtnShowJustSentences = new JRadioButton("Show just sentences with:");
		
		JCheckBox chckbxProbabilities = new JCheckBox("Probabilities");
		
		JCheckBox chckbxTimeElapsed = new JCheckBox("Time elapsed");
		
	    ButtonGroup group = new ButtonGroup();
	    group.add(rdbtnFullOutput);
	    group.add(rdbtnShowJustSentences);
	    
		
		JCheckBox chckbxDebugInfo = new JCheckBox("Debug info");
		GroupLayout gl_contentPanel = new GroupLayout(contentPanel);
		gl_contentPanel.setHorizontalGroup(
			gl_contentPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
						.addComponent(rdbtnFullOutput)
						.addComponent(rdbtnShowJustSentences)
						.addGroup(gl_contentPanel.createSequentialGroup()
							.addGap(29)
							.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
								.addComponent(chckbxTimeElapsed)
								.addComponent(chckbxProbabilities)
								.addComponent(chckbxDebugInfo))))
					.addContainerGap(239, Short.MAX_VALUE))
		);
		gl_contentPanel.setVerticalGroup(
			gl_contentPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addContainerGap()
					.addComponent(rdbtnFullOutput)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(rdbtnShowJustSentences)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(chckbxProbabilities)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(chckbxTimeElapsed)
					.addGap(18)
					.addComponent(chckbxDebugInfo)
					.addContainerGap(54, Short.MAX_VALUE))
		);
		contentPanel.setLayout(gl_contentPanel);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton btnClose = new JButton("Close");
				btnClose.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						setVisible(false);
					}
				});
				btnClose.setActionCommand("Cancel");
				buttonPane.add(btnClose);
			}
		}
	}
}
