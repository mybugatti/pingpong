package ping_pong;

import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

public class Render extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public Render(){
		
	}
	
	protected void paintComponent(Graphics g){
		super.paintComponent(g);
		Pong.pong.render((Graphics2D) g);
	}

}
