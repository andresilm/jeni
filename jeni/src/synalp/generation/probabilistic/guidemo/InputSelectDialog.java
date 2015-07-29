package synalp.generation.probabilistic.guidemo;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.ButtonGroup;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JRadioButton;
import javax.swing.JLabel;
import javax.swing.JEditorPane;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JButton;
import javax.swing.JComboBox;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class InputSelectDialog extends JFrame
{

	private JPanel contentPane;
	private JTextField textField;


	/**
	 * Create the frame.
	 */
	public InputSelectDialog()
	{
		setTitle("Select input method");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 445, 387);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		JRadioButton rdbtnFromFile = new JRadioButton("From file...");
		
		JLabel lblChooseYourPrefered = new JLabel("Choose your prefered input method:");
		
		JRadioButton rdbtnUseAPredefined = new JRadioButton("Use a predefined input");
		
		textField = new JTextField();
		textField.setEditable(false);
		textField.setColumns(10);
		
		JRadioButton rdbtnOrTypeThe = new JRadioButton("Or write the input");
		
		//group radio buttons
		ButtonGroup group = new ButtonGroup();
		group.add(rdbtnOrTypeThe);
		group.add(rdbtnUseAPredefined);
		group.add(rdbtnFromFile);
		
		JEditorPane editorPane = new JEditorPane();
		
		JButton btnCloseAndUse = new JButton("Save & close");
		btnCloseAndUse.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				//pasat datos de configuracoion
				
				setVisible(false);
			}
		});
		
		JButton btnNewButton = new JButton("Browse");
		
		JComboBox comboBox = new JComboBox();
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(editorPane, GroupLayout.PREFERRED_SIZE, 424, Short.MAX_VALUE)
						.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
							.addComponent(rdbtnFromFile)
							.addComponent(lblChooseYourPrefered)
							.addComponent(rdbtnOrTypeThe)
							.addComponent(btnCloseAndUse, Alignment.TRAILING)
							.addComponent(rdbtnUseAPredefined)
							.addGroup(gl_contentPane.createSequentialGroup()
								.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
									.addGroup(gl_contentPane.createSequentialGroup()
										.addGap(29)
										.addComponent(comboBox, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
									.addComponent(textField, GroupLayout.PREFERRED_SIZE, 307, GroupLayout.PREFERRED_SIZE))
								.addPreferredGap(ComponentPlacement.RELATED)
								.addComponent(btnNewButton))))
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
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(comboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addGap(23)
					.addComponent(rdbtnOrTypeThe)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(editorPane, GroupLayout.PREFERRED_SIZE, 83, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED, 15, Short.MAX_VALUE)
					.addComponent(btnCloseAndUse)
					.addContainerGap())
		);
		contentPane.setLayout(gl_contentPane);
	}
}
