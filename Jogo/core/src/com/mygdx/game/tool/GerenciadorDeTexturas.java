package com.mygdx.game.tool;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class GerenciadorDeTexturas { // Gerencia a maioria das texturas presentes no projeto, tendo a função de descartá-las de forma fácil quando necessário.

	public static Texture texturaTile;
	public static Texture texturaCacto;
	public static Texture texturaCoyote;
	public static Texture texturaMola;
	public static Texture texturaPlaca;
	
	public static void create(){ // Inicializa texturas.
		texturaTile = new Texture(Gdx.files.internal("Imagens/Continue Game Over Scenes.png"));
		texturaCacto = new Texture(Gdx.files.internal("Imagens/cactus.png"));
		texturaCoyote = new Texture(Gdx.files.internal("Imagens/WileECoyote.png"));
		texturaMola = new Texture(Gdx.files.internal("Imagens/mola.png"));
		texturaPlaca = new Texture(Gdx.files.internal("Imagens/placaSaida.png"));
	}
	public static void dispose(){ // Descarta as texturas.
		texturaTile.dispose();
		texturaCacto.dispose();
		texturaCoyote.dispose();
		texturaMola.dispose();
		texturaPlaca.dispose();
	}
}
