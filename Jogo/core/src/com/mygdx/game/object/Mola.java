package com.mygdx.game.object;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.mygdx.game.tool.GerenciadorDeTexturas;

public class Mola extends ObjetoDoJogo{ // Relacionada ao ojeto Mola, o qual se o jogador subir em cima, ele ir� pular mais alto que o normal, podendo assim alcan�ar locais altos.

	Rectangle hitBox;
	Sprite sprite;

	
	public Mola(int x, int y){ // Define a caixa de colis�o, o sprite, e chama a fun��o referente a posi��o x e y da Mola.
		hitBox = new Rectangle(0,0,93,26);
		sprite = new Sprite(GerenciadorDeTexturas.texturaMola,0,0,93,26);
		setPosition(x, y);
	}
	
	@Override
	public int hits(Rectangle r) { // Retorna a condi��o de colis�o.
		
		return -1;
	}

	@Override
	public void action(int type, float x, float y) {
	}

	@Override
	public void update(float delta) {
	}

	@Override
	public void setPosition(float x, float y) { // Define as posi��es x e y as quais a Mola ser� renderizada na tela.
		
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
	public Rectangle getHitBox() { // Retorna valores referentes � caixa de colis�o. 
		return hitBox;
	}

	@Override
	public int hitAction(int side) { // Retorna o valor da a��o de colis�o referente a Mola. No caso da mola, se os lados direito ou esquerdo sofrerem colis�o, retorna 1 (com isso, o jogador ir� colidir com a mola como se fosse um bloco comum do cen�rio), mas se o lado que sofreu for a parte de cima da mola, retorna 4 (assim, o jogador ir� colidir de forma a qual ele ficar� pulando em cima da mola).
		
		if(side==1){
			return 4;
		}
		
		return 1;
	}

}
