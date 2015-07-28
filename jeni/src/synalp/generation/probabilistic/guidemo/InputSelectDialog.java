package synalp.generation.probabilistic.guidemo;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JRadioButton;
import javax.swing.JLabel;
import javax.swing.JEditorPane;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JButton;

public class InputSelectDialog extends JFrame
{

	private JPanel contentPane;
	private JTextField textField;


	/**
	 * Create the frame.
	 */
	public InputSelectDialog()
	{
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 445, 387);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		JRadioButton rdbtnFromFile = new JRadioButton("From file...");
		
		JLabel lblChooseYourPrefered = new JLabel("Choose your prefered input method:");
		
		JRadioButton rdbtnUseAPredefined = new JRadioButton("Use a predefined input");
		
		textField = new JTextField();
		textField.setColumns(10);
		
		JRadioButton rdbtnOrTypeThe = new JRadioButton("Or write the desired input");
		
		JEditorPane editorPane = new JEditorPane();
		
		JButton btnCloseAndUse = new JButton("Use chosen input method");
		
		JButton btnNewButton = new JButton("Browse");
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(editorPane, GroupLayout.DEFAULT_SIZE, 428, Short.MAX_VALUE)
						.addComponent(rdbtnFromFile)
						.addComponent(lblChooseYourPrefered)
						.addComponent(rdbtnUseAPredefined)
						.addComponent(rdbtnOrTypeThe)
						.addComponent(btnCloseAndUse, Alignment.TRAILING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(textField, GroupLayout.PREFERRED_SIZE, 336, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(btnNewButton)))
					.addContainerGap())
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblChooseYourPrefered)
					.addGap(8)
					.addComponent(rdbtnFromFile)
					.addGap(1)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(textField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnNewButton))
					.addGap(18)
					.addComponent(rdbtnUseAPredefined)
					.addGap(62)
					.addComponent(rdbtnOrTypeThe)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(editorPane, GroupLayout.PREFERRED_SIZE, 83, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED, 17, Short.MAX_VALUE)
					.addComponent(btnCloseAndUse)
					.addContainerGap())
		);
		contentPane.setLayout(gl_contentPane);
	}
}
