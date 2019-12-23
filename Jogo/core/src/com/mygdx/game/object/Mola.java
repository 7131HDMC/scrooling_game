package com.mygdx.game.object;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.mygdx.game.tool.GerenciadorDeTexturas;

public class Mola extends ObjetoDoJogo{ // Relacionada ao ojeto Mola, o qual se o jogador subir em cima, ele irá pular mais alto que o normal, podendo assim alcançar locais altos.

	Rectangle hitBox;
	Sprite sprite;

	
	public Mola(int x, int y){ // Define a caixa de colisão, o sprite, e chama a função referente a posição x e y da Mola.
		hitBox = new Rectangle(0,0,93,26);
		sprite = new Sprite(GerenciadorDeTexturas.texturaMola,0,0,93,26);
		setPosition(x, y);
	}
	
	@Override
	public int hits(Rectangle r) { // Retorna a condição de colisão.
		
		return -1;
	}

	@Override
	public void action(int type, float x, float y) {
	}

	@Override
	public void update(float delta) {
	}

	@Override
	public void setPosition(float x, float y) { // Define as posições x e y as quais a Mola será renderizada na tela.
		
		hitBox.x = x;
		hitBox.y = y;
		sprite.setPosition(x, y);
		
	}

	@Override
	public void moveLeft(float delta) {
	}

	@Override
	public void moveRight(float delta) {
	}

	@Override
	public void draw(SpriteBatch batch) { // Renderiza o sprite referente a Mola.
		sprite.draw(batch);
		
	}

	@Override
	public void jump() {
	}

	@Override
	public Rectangle getHitBox() { // Retorna valores referentes à caixa de colisão. 
		return hitBox;
	}

	@Override
	public int hitAction(int side) { // Retorna o valor da ação de colisão referente a Mola. No caso da mola, se os lados direito ou esquerdo sofrerem colisão, retorna 1 (com isso, o jogador irá colidir com a mola como se fosse um bloco comum do cenário), mas se o lado que sofreu for a parte de cima da mola, retorna 4 (assim, o jogador irá colidir de forma a qual ele ficará pulando em cima da mola).
		
		if(side==1){
			return 4;
		}
		
		return 1;
	}

}
