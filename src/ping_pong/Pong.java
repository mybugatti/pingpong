package ping_pong;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.Timer;

public class Pong implements ActionListener , KeyListener {

    public static Pong pong;
    public Paddle player1, player2;
    public int width = 800;
    public int height = 700;
    public Render render;
    public boolean bol = false, selectingDifficulty;
    public boolean w, s, up, down;
    final static float dash1[] = {10.0f};
    public Timer timer;
    public Ball ball;
    public int gameStatut = 0, scoreLimit = 10, playerWon; // stop = 0; pause = 1; play = 2; over = 3
    public Random rand;
    public int botDifficulty, botMoves, botCoolDown = 0;

    public Pong(){

	timer = new Timer(20, this);
	JFrame jframe = new JFrame("Pong");
	rand = new Random();

	render = new Render();

	jframe.setSize(width, height);
	jframe.setResizable(false);
	jframe.setVisible(true);
	jframe.add(render);
	jframe.addKeyListener(this);

	timer.start();

    }

    public void start(){

	gameStatut = 2;

	player1 = new Paddle(this,1);
	player2 = new Paddle(this,2);

	ball = new Ball(this);
    }

    public void update(){

	if(player1.score >= scoreLimit) {
	    playerWon = 1;
	    gameStatut = 3;
	}

	if(player2.score >= scoreLimit) {
	    playerWon = 2;
	    gameStatut = 3;
	}

	if(s){
	    player1.move(false);
	}

	if(w){
	    player1.move(true);
	}

	if(!bol){
	    if(up){
		player2.move(true);
	    }

	    if(down){
		player2.move(false);
	    }
	} else {

	    if(botCoolDown > 0){

		botCoolDown--;

		if(botCoolDown == 0){
		    botCoolDown = 0;
		}
	    }

	    if(botMoves < 10){

		if(player2.y + player2.height /2 < ball.y ){
		    player2.move(false);
		    botMoves++;
		}

		if(player2.y + player2.height /2 > ball.y ){
		    player2.move(true);
		    botMoves++;
		}
		if(botDifficulty == 0) {
		    botCoolDown = 15;
		}

		if(botDifficulty == 1) {
		    botCoolDown = 10;
		}

		if(botDifficulty == 2) {
		    botCoolDown = 5;
		}
	    }

	}

	ball.update(player1, player2);

    }

    public void render(Graphics2D g){

	g.setColor(Color.black);
	g.fillRect(0, 0, width, height);
	g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
		RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

	if(gameStatut == 0) {
	    g.setColor(Color.GREEN);
	    g.setFont(new Font("Arial", 12, 32));
	    g.drawString("Game Pong", this.width/2 - 80,this.height/2 -85);

	    if(!selectingDifficulty){
		g.setColor(Color.YELLOW);
		g.setFont(new Font("Arial", 12, 30));

		g.drawString("Press Space To Play", this.width/2 - 125,this.height/2);	
		g.drawString("Press Esc To Return", this.width/2 - 125,this.height/2 -35);
		g.drawString("<< Score Limit : " + scoreLimit + " >>", this.width/2 - 125,this.height/2 +65);
	    }

	}

	if(selectingDifficulty) {

	    String string = botDifficulty == 0 ? "Easy" : (botDifficulty == 1 ? "Medium" : "Hard");
	    g.setColor(Color.YELLOW);
	    g.setFont(new Font("Arial", 12, 30));

	    g.drawString("Difficulty " + string, this.width/2 - 125,this.height/2 - 42); 
	    g.drawString("Press Space To Play", this.width/2 - 125,this.height/2);	

	}

	if(gameStatut == 1 || gameStatut == 2) {

	    g.setColor(Color.WHITE);
	    g.setStroke(new BasicStroke(1.0f,
		    BasicStroke.CAP_BUTT,
		    BasicStroke.JOIN_MITER,
		    10.0f, dash1, 0.0f));
	    g.drawOval(this.width/2 - 100, this.height/2 - 120, width-600, height-450);

	    g.drawLine(width/2, 0, width/2, height);

	    player1.render(g);
	    player2.render(g);
	    
	    g.setColor(Color.WHITE);
	    g.setFont(new Font("Arial", 12, 30));
	    g.drawString(String.valueOf(player2.getScore()), width/2 + 35, height/5 - 100);
	    g.drawString(String.valueOf(player1.getScore()), width/2 - 55, height/5 - 100);

	    ball.render(g);		    
	}

	if(gameStatut == 1 ) {
	    g.setColor(Color.WHITE);
	    g.setStroke(new BasicStroke(1.0f,
		    BasicStroke.CAP_BUTT,
		    BasicStroke.JOIN_MITER,
		    10.0f, dash1, 0.0f));
	    g.setFont(new Font("Arial", 2, 40));
	    g.drawString("Pause", this.width/2 - 60,this.height/2 - 55);

	}


	if(gameStatut == 3 ) {
	    g.setColor(Color.WHITE);
	    g.setStroke(new BasicStroke(1.0f,
		    BasicStroke.CAP_BUTT,
		    BasicStroke.JOIN_MITER,
		    10.0f, dash1, 0.0f));
	    g.setFont(new Font("Arial", 1, 40));

	    if(bol) {

		g.drawString("Press Space To Play Again", this.width/2 - 235,this.height/2);	

	    } else {


		g.setColor(Color.BLUE);
		g.drawString("Player " + playerWon + " Wins!", this.width/2 - 125,this.height/2 - 42); 
	    }
	}

    }

    public static void main(String[] args) {

	pong = new Pong();
    }

    @Override
    public void actionPerformed(ActionEvent evt) {

	if(gameStatut == 2){
	    update();
	}

	render.repaint();
    }

    @Override
    public void keyPressed(KeyEvent evt) {

	int code = evt.getKeyCode();


	if (code == KeyEvent.VK_W){
	    w = true;
	} 

	else if(code == KeyEvent.VK_S){
	    s = true;
	}

	else if(code == KeyEvent.VK_UP){
	    up = true;
	}

	else if(code == KeyEvent.VK_DOWN){
	    down = true;
	}

	else if (code == KeyEvent.VK_RIGHT) {

	    if(selectingDifficulty) {

		if(botDifficulty < 2) {
		    botDifficulty++;
		} else {
		    botDifficulty = 0;
		}
	    } else if(gameStatut == 0 ) {

		scoreLimit++;
	    }

	}

	else if (code == KeyEvent.VK_LEFT) {

	    if(selectingDifficulty) {

		if(botDifficulty > 0) {
		    botDifficulty--;
		} else {
		    botDifficulty = 2;
		}	
	    } else if (gameStatut == 0 && scoreLimit > 1) {

		scoreLimit--;
	    }

	}

	else if (code == KeyEvent.VK_ESCAPE && (gameStatut == 2 || gameStatut == 3 )) {
	    gameStatut = 0;
	}

	else if (code == KeyEvent.VK_SHIFT && gameStatut == 0) {
	    bol = true;
	    selectingDifficulty = true;

	} 

	else if(code == KeyEvent.VK_SPACE){

	    if(gameStatut == 0 || gameStatut == 3){

		if(!selectingDifficulty) {
		    bol = false;
		} else {
		    selectingDifficulty = false;
		}

		start();

	    }

	    else if(gameStatut == 1 ){ // 2
		gameStatut = 2;
	    }

	    else if(gameStatut == 2){ // 1
		gameStatut = 1;
	    }
	}

    }


    public void keyReleased(KeyEvent evt) {

	int code = evt.getKeyCode();

	if(code == KeyEvent.VK_S){
	    s = false;
	}

	else if(code == KeyEvent.VK_W){
	    w = false;
	}

	else if(code == KeyEvent.VK_UP){
	    up = false;
	}

	else if(code == KeyEvent.VK_DOWN){
	    down = false;
	}

    }

    @Override
    public void keyTyped(KeyEvent arg0) {
	// TODO Auto-generated method stub

    }

}
