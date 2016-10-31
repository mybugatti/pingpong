package ping_pong;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class Paddle {

	public int paddleHumber;
	public int x, y, width = 50, height = 300;
	public int score;

	public Paddle(Pong pong, int paddleHumber){

		this.paddleHumber = paddleHumber;

		if(paddleHumber == 1){
			this.x = 0;
		}

		if(paddleHumber == 2){
			this.x = pong.width - width;	
		}

		this.y = pong.height / 2 - this.height / 2;

	}
	
	public int getScore() {
	    return score;
	}

	public void render(Graphics2D g) {
	    
	    if(paddleHumber == 1){
		g.setColor(Color.CYAN);
		g.fillRect(x, y, width, height);
	    } else {
		g.setColor(Color.YELLOW);
		g.fillRect(x, y, width, height);
	    }
	}

	public void move(boolean up) {
	   
	    int speed = 5;
	   
	    if(up){
		
		if(y - speed > 0){
		    y -= speed;
		} else {
		    y = 0;
		}
	    }
	    else {
		
		if(y + height + speed < Pong.pong.height) {
		    y += speed;
		} else {
		    y = Pong.pong.height - height - 15;
		}
		
	    }
	    
	}
}
