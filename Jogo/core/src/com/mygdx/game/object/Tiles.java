package com.mygdx.game.object;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.mygdx.game.tool.GerenciadorDeTexturas;

public class Tiles extends ObjetoDoJogo { // Relacionada ao cen�rio em si, definindo os blocos que constituem o cen�rio, atrav�s de suas propor��es, textura e posi��o no mapa.

	Rectangle hitBox;
	Sprite sprite;
	
	public Tiles(int x, int y){ // Define a caixa de colis�o, o sprite, e chama a fun��o referente a posi��o x e y do Tile.
		hitBox = new Rectangle(x,y,64,64);
		sprite = new Sprite(GerenciadorDeTexturas.texturaTile,12,150,64,64);
		setPosition(x,y);
	}
	
	@Override
	public int hits(Rectangle r) { // Retorna a condi��o de colis�o.
		return 0;
	}

	@Override
	public void action(int type, float x, float y) {
	}

	@Override
	public void update(float delta) {
	}

	@Override
	public void setPosition(float x, float y) { // Define as posi��es x e y as quais os Tiles do mapa ser�o renderizados na tela.
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
	public void draw(SpriteBatch batch) { // Renderiza o sprite referente ao Tile.
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
	public int hitAction(int side) { // Retorna o valor da a��o de colis�o referente ao Tile.
		
		return 1;
	}

}
