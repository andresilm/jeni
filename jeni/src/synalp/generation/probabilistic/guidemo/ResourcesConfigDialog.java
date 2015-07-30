package synalp.generation.probabilistic.guidemo;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.FileDialog;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JLabel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.FilenameFilter;

public class ResourcesConfigDialog extends JDialog
{

	private final JPanel contentPanel = new JPanel();
	private JTextField grammarTextField;
	private JTextField lexiconTextField;
	private JTextField testsuiteTextField;
	
	//Config options
	private String grammarPath;
	private String lexiconPath;
	private String testsuitePath;


	/**
	 * Create the dialog.
	 */
	public ResourcesConfigDialog()
	{
		setTitle("Choose resources for PJeni generator");
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		
		
		grammarTextField = new JTextField();
		grammarTextField.setText("*.xml");
		grammarTextField.setEditable(false);
		grammarTextField.setColumns(10);
		
		lexiconTextField = new JTextField();
		lexiconTextField.setText("*.lex");
		lexiconTextField.setEditable(false);
		lexiconTextField.setColumns(10);
		
		testsuiteTextField = new JTextField();
		testsuiteTextField.setText("*.geni");
		testsuiteTextField.setEditable(false);
		testsuiteTextField.setColumns(10);
		
		JLabel lblGrammarFile = new JLabel("Grammar");
		
		JLabel lblLexiconFile = new JLabel("Lexicon");
		
		JLabel lblTestSuite = new JLabel("Testsuite");
		
		JButton btnBrowse = new JButton("Browse");
		ResourcesConfigDialog thisDialog = this;
		btnBrowse.addActionListener(new ActionListener() {


			@Override
			public void actionPerformed(ActionEvent e)
			{
				// TODO Auto-generated method stub
				System.out.println("File dialog for grammar should open now.");
				FileDialog fd = new FileDialog(thisDialog, "Choose a grammar file", FileDialog.LOAD);
				
				fd.setVisible(true);
				
				
				setGrammarPath(fd.getDirectory() + fd.getFile());
				grammarTextField.setText(getGrammarPath());			}
		});
		
		JButton button = new JButton("Browse");
		
		button.addActionListener(new ActionListener() {


			@Override
			public void actionPerformed(ActionEvent e)
			{
				// TODO Auto-generated method stub
				System.out.println("File dialog for lexicon should open now.");
				FileDialog fd = new FileDialog(thisDialog, "Choose a lexicon file", FileDialog.LOAD);
				
				fd.setVisible(true);
				
				
				setLexiconPath(fd.getDirectory() + fd.getFile());
				lexiconTextField.setText(getLexiconPath());			}
		});
		
	
		
		JButton button_1 = new JButton("Browse");
		
		button_1.addActionListener(new ActionListener() {


			@Override
			public void actionPerformed(ActionEvent e)
			{
				// TODO Auto-generated method stub
				System.out.println("File dialog for testsuite should open now.");
				FileDialog fd = new FileDialog(thisDialog, "Choose a lexicon file", FileDialog.LOAD);
				
				fd.setVisible(true);
				
				
				setTestsuitePath(fd.getDirectory() + fd.getFile());
				testsuiteTextField.setText(getTestsuitePath());			}
		});
		
		GroupLayout gl_contentPanel = new GroupLayout(contentPanel);
		gl_contentPanel.setHorizontalGroup(
			gl_contentPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPanel.createSequentialGroup()
							.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING, false)
								.addComponent(lexiconTextField, GroupLayout.DEFAULT_SIZE, 321, Short.MAX_VALUE)
								.addComponent(grammarTextField)
								.addComponent(testsuiteTextField))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
								.addComponent(button_1, GroupLayout.PREFERRED_SIZE, 88, GroupLayout.PREFERRED_SIZE)
								.addComponent(button, GroupLayout.PREFERRED_SIZE, 88, GroupLayout.PREFERRED_SIZE)
								.addComponent(btnBrowse)))
						.addComponent(lblGrammarFile)
						.addComponent(lblLexiconFile)
						.addComponent(lblTestSuite))
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		gl_contentPanel.setVerticalGroup(
			gl_contentPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addGap(17)
					.addComponent(lblGrammarFile)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(grammarTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnBrowse))
					.addGap(24)
					.addComponent(lblLexiconFile)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lexiconTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(button))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(lblTestSuite)
					.addGap(4)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(testsuiteTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(button_1))
					.addContainerGap(28, Short.MAX_VALUE))
		);
		contentPanel.setLayout(gl_contentPanel);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("Close");
				okButton.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						setVisible(false);
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
		}
	}


	String getGrammarPath()
	{
		return grammarPath;
	}


	void setGrammarPath(String grammarPath)
	{
		this.grammarPath = grammarPath;
	}


	String getLexiconPath()
	{
		return lexiconPath;
	}


	void setLexiconPath(String lexiconPath)
	{
		this.lexiconPath = lexiconPath;
	}


	String getTestsuitePath()
	{
		return testsuitePath;
	}


	void setTestsuitePath(String testsuitePath)
	{
		this.testsuitePath = testsuitePath;
	}
}