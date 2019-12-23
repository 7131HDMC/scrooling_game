package com.mygdx.game.object;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public abstract class ObjetoDoJogo { // Classe abstrata do projeto, a qual reúne várias funções úteis para diferentes classes.
	public abstract int hits(Rectangle r);
	public abstract void action(int type, float x, float y);
	public abstract void update(float delta);
	public abstract void setPosition(float x, float y);
	public abstract void moveLeft(float delta);
	public abstract void moveRight(float delta);
	public abstract void draw(SpriteBatch batch);
	public abstract void jump();
	public abstract Rectangle getHitBox();
	public abstract int hitAction(int side);
}
