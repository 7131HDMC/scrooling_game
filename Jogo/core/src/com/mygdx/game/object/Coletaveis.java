package com.mygdx.game.object;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class Coletaveis extends ObjetoDoJogo{ // Classe relacionada aos objetos coletáveis do jogo, que são as pilulas que aumentam a barra de vida, os continues, que aumentam a quantidade de chances que o jogador tem para concluir a fase, e também os colecionáveis Acme.

	Rectangle hitBox;
	Sprite sprite;
	Texture textura;
	private int condicao;
	private Sound somDePegarItens;
	
	public Coletaveis(Texture t, int x, int y, int w, int h, int c){ // Define a caixa de colisão, o sprite, a textura, uma condição referente ao tipo de coletável, inicializa o efeito sonoro referente ao som que toca ao ser colidido, e chama a função referente a posição x e y.
		somDePegarItens = Gdx.audio.newSound(Gdx.files.internal("Sons/som de coletar itens.mp3"));
		hitBox = new Rectangle(x,y,w,h);
		textura = t;
		sprite = new Sprite(textura,0,0,w,h);
		setPosition(x, y);
		condicao=c;
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
	public void setPosition(float x, float y) { // Define as posições x e y as quais os Coletáveis serão renderizados na tela.
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
	public void draw(SpriteBatch batch) { // Renderiza os coletáveis na tela.
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
	public int hitAction(int side) { // Retorna o valor da ação de colisão referente ao coletável.
		somDePegarItens.play(2);
		return condicao;
	}

}
