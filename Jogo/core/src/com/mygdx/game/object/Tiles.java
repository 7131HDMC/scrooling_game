package com.mygdx.game.object;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.mygdx.game.tool.GerenciadorDeTexturas;

public class Tiles extends ObjetoDoJogo { // Relacionada ao cenário em si, definindo os blocos que constituem o cenário, através de suas proporções, textura e posição no mapa.

	Rectangle hitBox;
	Sprite sprite;
	
	public Tiles(int x, int y){ // Define a caixa de colisão, o sprite, e chama a função referente a posição x e y do Tile.
		hitBox = new Rectangle(x,y,64,64);
		sprite = new Sprite(GerenciadorDeTexturas.texturaTile,12,150,64,64);
		setPosition(x,y);
	}
	
	@Override
	public int hits(Rectangle r) { // Retorna a condição de colisão.
		return 0;
	}

	@Override
	public void action(int type, float x, float y) {
	}

	@Override
	public void update(float delta) {
	}

	@Override
	public void setPosition(float x, float y) { // Define as posições x e y as quais os Tiles do mapa serão renderizados na tela.
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
	public Rectangle getHitBox() { // Retorna valores referentes à caixa de colisão.
		
		return hitBox;
	}

	@Override
	public int hitAction(int side) { // Retorna o valor da ação de colisão referente ao Tile.
		
		return 1;
	}

}
