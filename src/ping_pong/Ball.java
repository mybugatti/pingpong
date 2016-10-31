package ping_pong;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

public class Ball {

    public int x, y, width = 20, height = 20;
    public int xMotion, yMotion;
    public Random rand;
    private Pong pong;
    public int amountOfHit;

    public Ball(Pong pong){
	
	rand = new Random();
	this.pong = pong;
	
	this.spawn();
    }
    
    public void spawn(){
	
	this.amountOfHit = 0;

	this.x = pong.width / 2 - 10;
	this.y = pong.height / 2;
	
	this.yMotion = -2 + rand.nextInt(4);

	if(yMotion == 0) {

	    yMotion = 1;
	}

	if(rand.nextBoolean()){

	    xMotion = 1;

	} else {

	    xMotion = -1;
	}
    }

    public void update(Paddle player1, Paddle player2){

	int speed = 2;
	
	this.x += xMotion * speed;
	this.y += yMotion * speed;
	
	if(checkCollision(player1) == 1 ) {
	    
	    this.xMotion = 1 + (amountOfHit/5);
	    this.yMotion = -2 + rand.nextInt(4);
	    
	    if(yMotion == 0) {
		
		yMotion = 0;
	    }
	    amountOfHit++;
	}
	
	else if (checkCollision(player2) == 1) {
	    
	    this.xMotion = -1 - (amountOfHit/5);
	    this.yMotion = -2 + rand.nextInt(4);
	    
	    if(yMotion == 0) {
		
		yMotion = 0;
	    }
	    amountOfHit++;
	}
	
	if(checkCollision(player1) == 2) {
	    
	    player2.score++;
	    spawn();
	    
	} else if (checkCollision(player2) == 2) {
	    
	    player1.score++;
	    spawn();
	}
	
	if(this.y + height - yMotion > pong.height || this.y +yMotion < 0) {

	    if(this.yMotion < 0) {
		
		this.y = 0;
		this.yMotion = rand.nextInt(4);
		
		    if(yMotion == 0) {

			yMotion = 1;
		    }
		
	    } else {
		
		this.yMotion = -rand.nextInt(4); 
		this.y = pong.height - height;
		
		    if(yMotion == 0) {

			yMotion = 1;
		    }
		
	    }

	}

 
    }

    public int checkCollision(Paddle paddle) {

	if(this.x < paddle.x + paddle.width && this.x + width > paddle.x && this.y < paddle.y + paddle.height && this.y + height > paddle.y) {

	    return 1; // hit

	} else if ((paddle.x > x && paddle.paddleHumber ==1) || (paddle.x < x - width && paddle.paddleHumber == 2)) {

	    return 2; // score

	}

	return 0; // Nothing

    }

    public void render(Graphics g) {
	g.setColor(Color.white);
	g.fillOval(this.x, this.y, this.width, this.height);
    }

}
