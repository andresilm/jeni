package synalp.generation.probabilistic.ui;

import java.awt.EventQueue;

public class PJeniGui
{

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
					AppWindow frame = new AppWindow();
					frame.setVisible(true);
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		});

	}
}
