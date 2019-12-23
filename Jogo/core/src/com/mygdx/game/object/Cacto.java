package com.mygdx.game.object;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.mygdx.game.tool.GerenciadorDeTexturas;

public class Cacto extends ObjetoDoJogo{ // Classe relacionada ao objeto Cacto que aparece ao longo do mapa. Causa dano ao jogador quando ele o colide, sendo um obst�culo do cen�rio.

	Rectangle hitBox;
	Sprite sprite;
	
	public Cacto(int x, int y){ // Define a caixa de colis�o, o sprite, e chama a fun��o referente a posi��o x e y do objeto Cacto.
		hitBox = new Rectangle(x,y,86,68);
		sprite = new Sprite(GerenciadorDeTexturas.texturaCacto,0,0,86,68);
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
	public void setPosition(float x, float y) { // Define as posi��es x e y as quais o Cacto ir� ser renderizado na tela.
		
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
	public Rectangle getHitBox() { // Retorna valores referentes a sua caixa de colis�o.
		return hitBox;
	}

	@Override
	public int hitAction(int side) { // Retorna o valor da a��o de colis�o referente ao Cacto.
		return 2;
	}

}
