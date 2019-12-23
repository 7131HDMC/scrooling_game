package com.mygdx.game.object;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.mygdx.game.tool.GerenciadorDeTexturas;

public class PersonagemControlavel extends ObjetoDoJogo { // Relacionada ao personagem controlável pelo jogador, tendo funções referentes a sua movimentação, colisão, animação, posição no cenário, dentre outras.
	private Rectangle bottom, left, right, top, full;
	private Sprite spritesCoyote;
	
	private float time;
	
	private int vida, qtdContinues, qtdColetaveis;
	private float velocityY, velocityX;
	private boolean direction;
	private int condicaoMola=0,condicaoSom;
	
	private Sound somDePulo;
	private Sound somDeImpactoNoSolo;
	
	private boolean podePular;
	private float posX, posY;
	
	private boolean primeiraPartida;
	
	public PersonagemControlavel(){ // Função construtora da classe, e tem o objetivo de inicializar as variáveis.
		primeiraPartida=true;
		time=0;
		direction = true;
		full = new Rectangle(0,0,50,75);
		bottom = new Rectangle(0,0,50,0);
		left = new Rectangle(0,5,25,70);
		right = new Rectangle(25,5,25,70);
		top = new Rectangle(0,50,50,5);
		
		posX=Gdx.graphics.getWidth()/2;
		posY=Gdx.graphics.getHeight()/2;
		
		
		somDePulo = Gdx.audio.newSound(Gdx.files.internal("Sons/pulo.mp3"));
		somDeImpactoNoSolo = Gdx.audio.newSound(Gdx.files.internal("Sons/cair no chao.mp3"));
		spritesCoyote = new Sprite(GerenciadorDeTexturas.texturaCoyote,0,0,50,75);
		posX=100;
		posY=70;
		velocityY = 0;
		velocityX = 0;
		vida=100;
		qtdContinues=3;
		qtdColetaveis=0;
		
		
		podePular=false;
	}
	
	public int hits(Rectangle r){ // Retorna a condição de colisão.
		
		if(left.overlaps(r)){
			return 2;
		}
		
		if(right.overlaps(r)){
			return 3;
		}
		
		if(bottom.overlaps(r)){
			return 1;
		}
		
		if(top.overlaps(r)){
			return 4;
		}
		return -1;
	}
	
	public void action(int type, float x, float y){ // Define as consequências ao colidir com determinados objetos e seus lados.
		if(type==1){
			
			if(podePular==false){
				condicaoSom=0;
			}
			setPosition(bottom.x, y);
			velocityY = 0;
			podePular=true;
			
			if(condicaoSom==0){
				somDeImpactoNoSolo.play();
				condicaoSom=1;
			}
		}else
		if(type==4 && podePular==false && velocityY>0){
			setPosition(bottom.x, y);
			velocityY = 0;
		}
		if(type==2){
			velocityY = 0;
			setPosition(x, bottom.y);
		}else
		if(type==3){
			velocityY = 0;
			setPosition(x, bottom.y);
		}
		if(type==5){
			somDePulo.play();
			velocityY = 19;
			setPosition(bottom.x, y);
		}
		if(type==6){
			velocityY = 9;
			setPosition(bottom.x, y);
		}
	}
	
	public void update(float delta){ // Tem o objetivo de atualizar o jogador na tela, sua animação, sua posição no cenário, e também define a gravidade do jogo ("velocityY -= 30*delta").
		animacaoSprites();
		velocityY -= 30*delta;
		bottom.y += velocityY;
		top.y+=velocityY;
		spritesCoyote.setPosition(bottom.x, bottom.y);
		
	}
	
	public boolean getPrimeiraPartida(){ // Retorna false se nessa partida o jogador coletou um "continue", senão, retorna true.
		return primeiraPartida;
	}
	
	public void setPrimeiraPartida(boolean p){ // Define um valor true ou false para a variável "primeiraPartida".
		primeiraPartida=p;
	}
	
	public float getVelocityX(){ // Retorna a velocidade X do jogador.
		return velocityX;
	}
	
	public float getVelocityY(){ // Retorna a velocidade Y do jogador.
		return velocityY;
	}
	
	public void setVelocityX(float vX, float delta){ // Define o valor para a velocidade X do jogador.
		velocityX=vX*delta;
	}
	
	public void setVelocityY(float vY,float delta){ // Define o valor para a velocidade Y do jogador.
		velocityY=vY*delta;
	}
	
	
	public int getVida(){ // Retorna a quantidade de vida do jogador.
		return vida;
	}
	
	public void setVida(int v){ // Define um valor para a quantidade de vida do jogador.
		vida=v;
	}
	
	public int getContinues(){ // Retorna a quantidade de continues do jogador.
		return qtdContinues;
	}
	
	public void setContinues(int c){ // Define um valor para a quantidade de continues do jogador, e se ela diminuir, o jogador é teleportado para a sua posição inicial.
		if(qtdContinues>c){
			setPosition(100, 70);
		}
		qtdContinues=c;
	}
	
	
	public int getColetaveis(){ // Retorna a quantidade de coletáveis que o jogador possui.
		return qtdColetaveis;
	}

	
	public void setColetaveis(int c){ // Define um valor para a quantidade de coletáveis que o jogador possui.
		qtdColetaveis=c;
	}
	
	public boolean getPodePular(){ // Retorna true se o jogador pode executar um pulo, senão, retorna false.
		return podePular;
	}
	
	
	public void setPosition(float x, float y){ // Define as posições X e Y do jogador no cenário.
		left.x=x;
		left.y=y+5;
		right.x=x+25;
		right.y=y+5;
		full.x=x;
		full.y=y;
		bottom.x = x;
		bottom.y = y;
		top.x=x;
		top.y=y+50;
		
		posX=x;
		posY=y;
		
		spritesCoyote.setPosition(x,y);
	}
	
	public void moveLeft(float delta){ // Movimenta o jogador para a esquerda, e define em que direção ele está virado, para que seja possível inverter a imagem referente a sua textura se necessário.
		if(direction==true){
			direction = false;
		}

		bottom.x -= velocityX;

		posX=bottom.x;
		spritesCoyote.setPosition(bottom.x, bottom.y);
	}
	
	public void moveRight(float delta){ // Tem o mesmo objeto da função "moveLeft()", porém nesse caso, o jogador se movimenta para a direita.
		if(direction==false){
			direction = true;
		}

		bottom.x += velocityX;
		
		posX=bottom.x;
		spritesCoyote.setPosition(bottom.x, bottom.y);
	}
	
	public void draw(SpriteBatch batch){ // Renderiza o sprite do jogador na tela.
		spritesCoyote.draw(batch);
		
	}
	
	public void setCondicaoMola(int condicaoMola){ // Define um valor para a variável referente à colisão com o objeto "mola" no cenário.
		condicaoMola=this.condicaoMola;
	}
	
	public void jump(){ // Executa o movimento de pulo do jogador quando chamada, verificando se é possível pular. Também verifica se o jogador subiu em cima de uma mola, se sim, ele pula mais alto que o normal.
		if(podePular==true && condicaoMola==0){
			velocityY = 14;
			somDePulo.play();
			podePular=false;
		}
		if(condicaoMola==1){
			podePular=false;
			condicaoMola=0;
		}
		
	}

	@Override
	public Rectangle getHitBox() { // Retorna valores referentes à caixa de colisão do jogador.
		return full;
	}

	@Override
	public int hitAction(int side) { // Retorna o valor da ação de colisão referente ao Personagem controlável.
		return 0;
	}

	public float getX() { // Retorna a posição X do jogador.
		posX=bottom.x;
		return posX;
	}

	public float getY() { // Retorna a posição Y do jogador.
		posY=bottom.y;
		return posY;
	}
	
	public void setX(float x) { // Define uma posição X para o jogador.
		bottom.x = x;
	}

	public void setY(float y) { // Define uma posição Y para o jogador.
		bottom.y = y;
	}
	
	
	public void animacaoSprites(){ // Realiza a animação dos sprites do jogador, de acordo com determinadas situações. A animação é devidamente controlada através do tempo.
		time+=Gdx.graphics.getDeltaTime();
		if(podePular==false){
			
			if(time>=0){
				spritesCoyote = new Sprite(GerenciadorDeTexturas.texturaCoyote,0,75,48,82);
			}
			if(time>=0.2f){
				spritesCoyote = new Sprite(GerenciadorDeTexturas.texturaCoyote,40,75,50,82);
			}
			if(time>=0.3f){
				spritesCoyote = new Sprite(GerenciadorDeTexturas.texturaCoyote,90,75,53,75);
			}
			if(time>=0.5f){
				spritesCoyote = new Sprite(GerenciadorDeTexturas.texturaCoyote,150,75,50,75);
			}
			if(time>=0.6f){
				spritesCoyote = new Sprite(GerenciadorDeTexturas.texturaCoyote,210,75,53,75);
			}
			if(time>=0.7f){
				spritesCoyote = new Sprite(GerenciadorDeTexturas.texturaCoyote,260,75,57,84);
			}
		}else
		if(velocityX==0){
			spritesCoyote = new Sprite(GerenciadorDeTexturas.texturaCoyote,0,0,50,75);
			time=0;
		}else if(velocityX>0){
			if(time>=0){
				spritesCoyote = new Sprite(GerenciadorDeTexturas.texturaCoyote,0,160,50,75);
			}
			if(time>=0.1f){
				spritesCoyote = new Sprite(GerenciadorDeTexturas.texturaCoyote,50,160,50,75);
			}
			if(time>=0.2f){
				spritesCoyote = new Sprite(GerenciadorDeTexturas.texturaCoyote,87,160,50,75);
			}
			if(time>=0.3f){
				spritesCoyote = new Sprite(GerenciadorDeTexturas.texturaCoyote,140,160,50,75);
			}
			if(time>=0.4f){
				spritesCoyote = new Sprite(GerenciadorDeTexturas.texturaCoyote,189,160,50,75);
			}
			if(time>=0.5f){
				spritesCoyote = new Sprite(GerenciadorDeTexturas.texturaCoyote,230,160,50,75);
				time=0;
			}
		}
		if(direction==false){
			spritesCoyote.flip(true, false);
		}
	}
}
