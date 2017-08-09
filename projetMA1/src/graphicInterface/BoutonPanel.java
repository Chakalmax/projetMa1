package graphicInterface;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;


public class BoutonPanel extends JPanel {

	private JButton buttonStart;
	private JButton buttonRestart;
	private JButton buttonStop;
	private MainFrame mainFrame;
	
	private static final long serialVersionUID = 1L;
	public BoutonPanel(MainFrame mainFrame)
	{
		//super.setBackground(new Color(100, 255, 100));
		buttonStart = new JButton("Resume");
		buttonRestart = new JButton("Start");
		buttonStop = new JButton("Stop");
		this.mainFrame = mainFrame;
		
		buttonStart.addActionListener(new BoutonResume());
		buttonStop.addActionListener(new BoutonStop());
		buttonRestart.addActionListener(new BoutonStart());
	}

	public void paintComponent(Graphics g){
		super.paintComponent(g);
		

		this.add(buttonStop,BorderLayout.CENTER);
		this.add(buttonStart,BorderLayout.EAST);
		this.add(buttonRestart,BorderLayout.WEST);
		
		
		
	}
	
	private class BoutonResume implements ActionListener{
	    public void actionPerformed(ActionEvent arg0) {   	
	    	if(Options.running)
	    		if(Options.stop)
	    			Options.stop=false;
	      }
	    }  
	
	private class BoutonStart implements ActionListener {
		public void actionPerformed(ActionEvent arg0){
			if(mainFrame.getKB() != null)
			if(Options.running){
			
			int option = JOptionPane.showConfirmDialog(null, 
			        "Voulez vous recommencer l'algo depuis le début?", 
			        "Restart", 
			        JOptionPane.YES_NO_OPTION, 
			        JOptionPane.QUESTION_MESSAGE);
			
			if(option == JOptionPane.OK_OPTION){
					changeBoutonToRestart();
					mainFrame.restartAlgo();
				
				
			}}
			else{
				changeBoutonToRestart();
				mainFrame.startAlgo();
				Options.running = true;
			}
			else
				JOptionPane.showMessageDialog(null, "Ouvrir une Kb avant", "", JOptionPane.INFORMATION_MESSAGE);
		}

		
	}
	
	private class BoutonStop implements ActionListener{
		public void actionPerformed(ActionEvent arg0){
			if(Options.running)
				if(!Options.stop)
					Options.stop = true;
		}
	}
	
	public void changeBoutonToStart(){
		String nom = buttonRestart.getText();
        if (nom.equals("Restart")) {
        	buttonRestart.setText("Start");
            buttonRestart.revalidate();
            buttonRestart.setOpaque(true);
            buttonRestart.setEnabled(true);
            buttonRestart.revalidate(); // peut-être même pas obligatoire
        }
	}
	
	public void changeBoutonToRestart() {
		String nom = buttonRestart.getText();
		if(nom.equals("Start")){
        	buttonRestart.setText("Restart");
            buttonRestart.revalidate();
            buttonRestart.setOpaque(true);
            buttonRestart.setEnabled(true);
            buttonRestart.revalidate();
        }
	}
	
	public void restart(){
		changeBoutonToStart();
	}
}
