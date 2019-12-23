package com.mygdx.game.object;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.mygdx.game.tool.GerenciadorDeTexturas;

public class Cacto extends ObjetoDoJogo{ // Classe relacionada ao objeto Cacto que aparece ao longo do mapa. Causa dano ao jogador quando ele o colide, sendo um obstáculo do cenário.

	Rectangle hitBox;
	Sprite sprite;
	
	public Cacto(int x, int y){ // Define a caixa de colisão, o sprite, e chama a função referente a posição x e y do objeto Cacto.
		hitBox = new Rectangle(x,y,86,68);
		sprite = new Sprite(GerenciadorDeTexturas.texturaCacto,0,0,86,68);
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
	public void setPosition(float x, float y) { // Define as posições x e y as quais o Cacto irá ser renderizado na tela.
		
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
	public void draw(SpriteBatch batch) { // Renderiza o sprite referente ao Cacto.
		sprite.draw(batch);
		
	}

	@Override
	public void jump() {
	}

	@Override
	public Rectangle getHitBox() { // Retorna valores referentes a sua caixa de colisão.
		return hitBox;
	}

	@Override
	public int hitAction(int side) { // Retorna o valor da ação de colisão referente ao Cacto.
		return 2;
	}

}
