package com.mygdx.game;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.StringTokenizer;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.object.Cacto;
import com.mygdx.game.object.Coletaveis;
import com.mygdx.game.object.Mola;
import com.mygdx.game.object.ObjetoDoJogo;
import com.mygdx.game.object.PersonagemControlavel;
import com.mygdx.game.object.Tiles;
import com.mygdx.game.tool.GerenciadorDeTexturas;

public class DesertDemolition extends ApplicationAdapter { // Classe principal do projeto, a qual gerencia fun��es relacionadas principalmente � execu��o do jogo (sua abertura, loop e fechamento).
	private SpriteBatch batch;
	private Texture telaVitoria;
	private Texture menuInicial;
	private Texture background;
	private Sprite telaVitoriaSprite;
	private Sprite backgroundSprite;
	private Sprite menuInicialSprite;
	private Sprite placaSprite;
	private Texture gameOver;
	private Sprite gameOverSprite;
	private Texture instrucoes;
	private Sprite instrucoesSprite;
	
	private float time,tempoTela;
	
	private NumberFormat formatarFloat= new DecimalFormat("0");
	private String tempoFormatado;  
	
	private Music somDePassos;
	private Music somDeCorrida;
	private Music somDeTempoAcabando;
	private Music musicaDeVitoria;
	
	private boolean personagemCorrendo;
	

	
	private OrthographicCamera camera;
	
	private PersonagemControlavel playerCoyote;
	
	private Texture pilulas;
	private Texture coletavelAcme;
	private Texture coletavelVida;
	private Sprite coletavelAcmeSprite;
	private Sprite coletavelVidaSprite;
	
	private int qtdVida, qtdContinues, qtdColetaveis, condicaoInicio;
	private BitmapFont font;
	
	private ArrayList<ObjetoDoJogo> lista = new ArrayList<ObjetoDoJogo>();
	private ArrayList<ObjetoDoJogo> deleteIsso = new ArrayList<ObjetoDoJogo>();
	
	@Override
	public void create () { // Inicializa todas as vari�veis necess�rias.
		time=0;
		tempoTela=0;
		
		GerenciadorDeTexturas.create();
		
		font = new BitmapFont();
		font.setColor(Color.WHITE);
		
		condicaoInicio=1;
		
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 640, 480);
		
		telaVitoria = new Texture(Gdx.files.internal("Imagens/telaFinal.png"));	
		telaVitoriaSprite = new Sprite(telaVitoria,60,0,900,576);
		telaVitoriaSprite.setSize(640, 480);
		telaVitoriaSprite.setPosition(0,0);
		
		instrucoes = new Texture(Gdx.files.internal("Imagens/instrucoesJogo.JPG"));
		instrucoesSprite = new Sprite(instrucoes,0,0,640,480);
		instrucoesSprite.setPosition(0,0);
		
		menuInicial = new Texture(Gdx.files.internal("Imagens/mainmenu.jpg"));	
		background = new Texture(Gdx.files.internal("Imagens/fundoBackground.jpg"));	
		backgroundSprite = new Sprite(background);
		backgroundSprite.setSize(640, 480);
		
		menuInicialSprite = new Sprite(menuInicial);
		menuInicialSprite.setSize(640, 480);
		menuInicialSprite.setPosition(0,0);
		
		gameOver = new Texture(Gdx.files.internal("Imagens/game over screen.jpg"));
		gameOverSprite = new Sprite(gameOver,200,0,900,600);
		gameOverSprite.setSize(640, 480);
		gameOverSprite.setPosition(0,0);
		
		playerCoyote = new PersonagemControlavel();
		playerCoyote.setPosition(100, 70);
		
		pilulas = new Texture(Gdx.files.internal("Imagens/DD_Pills.png"));	
		coletavelAcme = new Texture(Gdx.files.internal("Imagens/DD_Stamp.png"));
		coletavelVida = new Texture(Gdx.files.internal("Imagens/DD_WCHead.png"));
		
		coletavelAcmeSprite = new Sprite(coletavelAcme);
		coletavelAcmeSprite.setSize(42, 37);
		
		coletavelVidaSprite = new Sprite(coletavelVida);
		coletavelVidaSprite.setSize(43, 46);
		
		placaSprite = new Sprite(GerenciadorDeTexturas.texturaPlaca);
		placaSprite.setSize(54, 62);
		placaSprite.setPosition(5952, 64);

		somDePassos = Gdx.audio.newMusic(Gdx.files.internal("Sons/passos.mp3"));
		somDeCorrida = Gdx.audio.newMusic(Gdx.files.internal("Sons/correndo.mp3"));
		somDeTempoAcabando = Gdx.audio.newMusic(Gdx.files.internal("Sons/tempo acabando.mp3"));
		musicaDeVitoria = Gdx.audio.newMusic(Gdx.files.internal("Sons/musicaVitoria.mp3"));
		
		batch = new SpriteBatch();
		
		adicionarNaLista();
		
		qtdVida=playerCoyote.getVida();
		qtdContinues = playerCoyote.getContinues();
		qtdColetaveis = playerCoyote.getColetaveis();
		
	
	}
	
	
	public void adicionarNaLista(){ // L� o arquivo txt referente ao mapa do jogo, verifica seu conte�do e o insere em uma lista, de acordo com suas propriedades.
		FileHandle arquivo = Gdx.files.internal("Fase/fase1.txt");
		
		StringTokenizer tokens = new StringTokenizer(arquivo.readString());
		
		while(tokens.hasMoreTokens()){
			String type = tokens.nextToken();
			if(type.equals("Tiles")){
				lista.add(new Tiles(Integer.parseInt(tokens.nextToken()),Integer.parseInt(tokens.nextToken())));
			}else if(type.equals("Mola")){
				lista.add(new Mola(Integer.parseInt(tokens.nextToken()),Integer.parseInt(tokens.nextToken())));
			}else if(type.equals("Cacto")){
				lista.add(new Cacto(Integer.parseInt(tokens.nextToken()),Integer.parseInt(tokens.nextToken())));
			}else if(type.equals("Coletaveis1")){
				lista.add(new Coletaveis(coletavelAcme,Integer.parseInt(tokens.nextToken()),Integer.parseInt(tokens.nextToken()),Integer.parseInt(tokens.nextToken()),Integer.parseInt(tokens.nextToken()),Integer.parseInt(tokens.nextToken())));
			}else if(type.equals("Coletaveis2") && playerCoyote.getPrimeiraPartida()==true){
				lista.add(new Coletaveis(coletavelVida,Integer.parseInt(tokens.nextToken()),Integer.parseInt(tokens.nextToken()),Integer.parseInt(tokens.nextToken()),Integer.parseInt(tokens.nextToken()),Integer.parseInt(tokens.nextToken())));
			}else if(type.equals("Coletaveis3")){
				lista.add(new Coletaveis(pilulas,Integer.parseInt(tokens.nextToken()),Integer.parseInt(tokens.nextToken()),Integer.parseInt(tokens.nextToken()),Integer.parseInt(tokens.nextToken()),Integer.parseInt(tokens.nextToken())));
			}
		}
	}

	
    public void renderBackground() { // Renderiza a tela de fundo, os colet�veis e a placa de "Exit" no cen�rio.
    	backgroundSprite.setPosition(camera.position.x-320,0);
        backgroundSprite.draw(batch);
        coletavelAcmeSprite.setPosition(camera.position.x+230,430);
        coletavelAcmeSprite.draw(batch);
        coletavelVidaSprite.setPosition(camera.position.x-290,430);
        coletavelVidaSprite.draw(batch);
        placaSprite.draw(batch);
    }
    
	
	
	@Override
	public void render () { // Fun��o que cuida do loop do jogo, a qual verifica em qual tela o jogo se encontra(se � na de menu inicial, de gameplay, Game Over ou vit�ria), al�m de alter�-la quando necess�rio, atrav�s da vari�vel "condicaoInicio".
		
		if(condicaoInicio==1){
			menuInicial();
		}else
		if(condicaoInicio==5){
			telaInstrucoes();
		}
		if(condicaoInicio==2){
			jogoPrincipal();
		}
		if(condicaoInicio==3){
			telaGameOver();
		}
		if(condicaoInicio==4){
			vitoria();
		}

			
	}
	
	public void renderizaTextoNaTela(){ // Renderiza informa��es em texto na tela de jogo.
		font.draw(batch, "Barra de vida = " + qtdVida, camera.position.x-170, 470);
		font.draw(batch, "X " + qtdContinues, camera.position.x-280, 420);
		font.draw(batch, "X " + qtdColetaveis, camera.position.x+240, 420);
		font.draw(batch, "Tempo: "+ tempoFormatado , camera.position.x, 20);
	}
	
	
	public void tempoDeJogo(){ // Calcula o tempo de jogo apartir do momento o qual a fun��o � chamada, al�m de tamb�m realizar uma convers�o desse n�mero (float) para decimal (Isso � feito para que a informa��o da quantidade de tempo renderizada na tela seja a mais simplificada e reduzida poss�vel, mostrando apenas os segundos).
		time+=Gdx.graphics.getDeltaTime();
		tempoFormatado = formatarFloat.format(time); 
	}
	
	
	
	public void efeitosSonoros(){ // Define condi��es para a m�sica do jogo ser tocada. Assim como no jogo original, a m�sica s� toca enquanto o jogador anda, e se ele para de andar a m�sica pausa.
		if(playerCoyote.getPodePular()==true && playerCoyote.getVelocityX()!=0  && personagemCorrendo==false){
			somDeCorrida.stop();
			somDePassos.play();
		}
		if(playerCoyote.getPodePular()==true && playerCoyote.getVelocityX()!=0 && personagemCorrendo==true){
			somDePassos.stop();
			somDeCorrida.play();
		}
		if(playerCoyote.getVelocityX()==0){
			somDePassos.pause();
			somDeCorrida.stop();
		}
	}
	
	public void pararSonsESmusicas(){ // Tem o objetivo de parar os sons do jogo.
		somDeCorrida.stop();
		somDePassos.stop();
		somDeTempoAcabando.stop();
		musicaDeVitoria.stop();
	}
	
	
	public void limiteDaTela(){ // Define o limite da tela o qual o jogador pode caminhar, que � entre as posi��es X de 0 at� 6020, de acordo com o tamanho do mapa renderizado.
		if (playerCoyote.getX() < 0){
			playerCoyote.setX(0);
		}
		
		if (playerCoyote.getX() > 6020){
			playerCoyote.setX(6020);
		}
	}
	
	
	public void gameplay(){ // Define �s teclas usadas na gameplay do jogo e suas determinadas fun��es. Para andar para a direita: seta direita ou tecla D; Para andar para a esquerda: seta esquerda ou tecla A; Para correr: tecla Z ou SHIFT esquerdo; e para Pular: tecla X ou Espa�o.
		
		if(Gdx.input.isKeyPressed(Input.Keys.Z) || Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT)){
			personagemCorrendo=true;
			playerCoyote.setVelocityX(400,Gdx.graphics.getDeltaTime());
		}else{
			personagemCorrendo=false;
			playerCoyote.setVelocityX(230,Gdx.graphics.getDeltaTime());
		}
		if(Gdx.input.isKeyPressed(Input.Keys.RIGHT) || Gdx.input.isKeyPressed(Input.Keys.D)){
				playerCoyote.moveRight(Gdx.graphics.getDeltaTime());
		}else
		if(Gdx.input.isKeyPressed(Input.Keys.LEFT) || Gdx.input.isKeyPressed(Input.Keys.A)){
				playerCoyote.moveLeft(Gdx.graphics.getDeltaTime());

		}else{
			playerCoyote.setVelocityX(0, Gdx.graphics.getDeltaTime());
		}
		if(Gdx.input.isKeyPressed(Input.Keys.X) || Gdx.input.isKeyPressed(Input.Keys.SPACE)){
			playerCoyote.jump();
		}
		
	}
	
	
	public void jogoPrincipal(){ // � a fun��o onde ocorre o loop pricipal de gameplay, o qual chama fun��es referentes a renderiza��o do mapa, do cen�rio de fundo, player, e tudo o que aparece na tela de gameplay, al�m de tamb�m verificar o estado do jogador.

		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		
		renderBackground();
		
		
		playerCoyote.draw(batch);
		for(ObjetoDoJogo obj:lista){
			obj.draw(batch);
		}
		
		tempoDeJogo();

		renderizaTextoNaTela();
		
		batch.end();
		
		limiteDaTela();
		
		
		somDePassos.setLooping(true);
		efeitosSonoros();
		
		
		
		playerCoyote.update(Gdx.graphics.getDeltaTime());

		colisao();
		
		
		playerCoyote.setVida(qtdVida);
		playerCoyote.setContinues(qtdContinues);
		playerCoyote.setColetaveis(qtdColetaveis);
		
		
		
		while(!deleteIsso.isEmpty()){
			lista.remove(deleteIsso.get(0));
			deleteIsso.remove(0);
		}
		
		updateCamera();
		
		
		gameplay();
		condicaoDeVida();
	}
	
	
	public void colisao(){ // Verifica a colis�o do jogador com o cen�rio, obst�culos, e todos os itens colet�veis.
		for(ObjetoDoJogo obj:lista){
			switch(playerCoyote.hits(obj.getHitBox())){
			case 1:	
				switch(obj.hitAction(1)){
				case 1:
					playerCoyote.action(1,0,obj.getHitBox().y + obj.getHitBox().height);
					break;
				case 2:
					playerCoyote.action(6,0,obj.getHitBox().y + obj.getHitBox().height);
					qtdVida=playerCoyote.getVida()-10;
					break;
				
				case 3:
					qtdColetaveis=playerCoyote.getColetaveis()+1;
					deleteIsso.add(obj);
					
					break;
				case 4:
					playerCoyote.action(5,0,obj.getHitBox().y + obj.getHitBox().height);
					playerCoyote.setCondicaoMola(1);
					break;
					
				case 5:
					qtdContinues = playerCoyote.getContinues()+1;
					deleteIsso.add(obj);
					
					break;
				case 6:
					qtdVida=100;
					deleteIsso.add(obj);
					
					break;
				}
				break;
			case 2:
				
				switch(obj.hitAction(2)){
				case 1:
					playerCoyote.action(2,obj.getHitBox().x + obj.getHitBox().width+1,0);
					break;
				case 2:
					qtdVida=playerCoyote.getVida()-10;
					playerCoyote.action(6,0,obj.getHitBox().y + obj.getHitBox().height);
					break;
				case 3:
					qtdColetaveis=playerCoyote.getColetaveis()+1;
					deleteIsso.add(obj);
					
					break;
				case 5:
					qtdContinues = playerCoyote.getContinues()+1;
					deleteIsso.add(obj);
					
					break;
				case 6:
					qtdVida=100;
					deleteIsso.add(obj);
					
					break;
				}
				break;
			case 3:
				
				switch(obj.hitAction(3)){
				case 1:
					playerCoyote.action(3,obj.getHitBox().x - playerCoyote.getHitBox().width-1,0);
					break;
				case 2:
					qtdVida=playerCoyote.getVida()-10;
					playerCoyote.action(6,0,obj.getHitBox().y + obj.getHitBox().height);
					break;
				case 3:
					qtdColetaveis=playerCoyote.getColetaveis()+1;
					deleteIsso.add(obj);
					
					break;
				case 5:
					qtdContinues = playerCoyote.getContinues()+1;
					deleteIsso.add(obj);
					
					break;
				case 6:
					qtdVida=100;
					deleteIsso.add(obj);
					
					break;
				}
				break;
			case 4:
				
				switch(obj.hitAction(4)){
				case 1:
					playerCoyote.action(4,0,obj.getHitBox().y - playerCoyote.getHitBox().height);
					break;

				case 3:
					qtdColetaveis=playerCoyote.getColetaveis()+1;
					deleteIsso.add(obj);
					
					break;
				case 5:
					qtdContinues = playerCoyote.getContinues()+1;
					deleteIsso.add(obj);
					
					break;
				case 6:
					qtdVida=100;
					deleteIsso.add(obj);
					
					break;
				}
				break;
				
			}
		}
	}
	
	
	public void condicaoDeVida(){ // Verifica a vida e quantos "continues" restam ao jogador. Se o jogador perder toda a vida, chegando em zero, ele perde um continue. Se os continues chegarem a menos que zero, a fun��o referente a tela de game over � chamada. O jogo possui um limite de tempo de 60 segundos, o qual se ultrapassado, o jogador perde um continue. Caso o jogador chegue no ponto X de 5952 (o qual � indicado pela placa de "Exit") em menos de 60 segundos, a tela de vit�ria � renderizada. Existe tamb�m uma verifica��o se a partida atual � a primeira, com o intuito de que, se o jogador coletar um continue no cen�rio e acabar morrendo de alguma forma, o continue n�o ir� ser renderizado novamente enquanto n�o houver o fim do jogo (Game Over ou Vit�ria), para que assim n�o ocorra o caso de se ter continues infinitos.
		if(qtdVida==0 && qtdContinues>=0){
			qtdContinues--;
			playerCoyote.setPrimeiraPartida(false);
			adicionarNaLista();
			qtdColetaveis=0;
			time=0;
			playerCoyote.setPosition(100, 70);
			qtdVida=100;
			playerCoyote.setVida(qtdVida);
			playerCoyote.setContinues(qtdContinues);
			System.out.println(playerCoyote.getContinues());
		}
		if(qtdContinues<0){
			time=0;
			System.out.println("GAME OVER");
			condicaoInicio=3;
		}
		if(time>=60 && qtdContinues>=0){
			time=0;
			qtdContinues--;
			playerCoyote.setPrimeiraPartida(false);
			adicionarNaLista();
			qtdColetaveis=0;
			playerCoyote.setPosition(100, 70);
			qtdVida=100;
			playerCoyote.setVida(qtdVida);
			playerCoyote.setContinues(qtdContinues);
			System.out.println(playerCoyote.getContinues());
		}
		if(playerCoyote.getY()<=0){
			qtdContinues = playerCoyote.getContinues()-1;
			playerCoyote.setPrimeiraPartida(false);
			adicionarNaLista();
			qtdColetaveis=0;
			time=0;
			playerCoyote.setPosition(100, 70);
			qtdVida=100;
		}
		if(time>=50){
			somDeTempoAcabando.play();
		}
		if(playerCoyote.getX()>=5952 && time<60){
			pararSonsESmusicas();
			condicaoInicio=4;
		}
	}
	
	
	public void vitoria(){ // Renderiza uma tela de parabeniza��o quando o jogador chega no final da fase, a qual pode ser fechada usando o clique do mouse ou a tecla Enter, ou se fecha sozinha epois de 19 segundos. Depois de fechada, o jogo volta ao menu inicial.
		tempoTela+=Gdx.graphics.getDeltaTime();
		
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		telaVitoriaSprite.draw(batch);
		musicaDeVitoria.setLooping(false);
		musicaDeVitoria.play();
		batch.end();
		
		camera.position.x = 320;
		camera.position.y = 240;
		camera.update();
		
		if(Gdx.input.isTouched() || (Gdx.input.isTouched() || Gdx.input.isKeyPressed(Input.Keys.ENTER)) || tempoTela>=19){
			pararSonsESmusicas();
			create();
		}
	}
	
	
	public void menuInicial(){ // Renderiza a imagem referente ao menu inicial do jogo. O jogo pode ser iniciado tanto com o clique do mouse, quanto com o uso da tecla Enter.
		playerCoyote.setPrimeiraPartida(true);
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		menuInicialSprite.draw(batch);
		batch.end();
		
		camera.position.x = 320;
		camera.position.y = 240;
		camera.update();
		
		if(Gdx.input.isTouched() || Gdx.input.isKeyPressed(Input.Keys.ENTER)){
			condicaoInicio = 5;	
		}
		
	}
	
	public void telaInstrucoes(){ // Renderiza a imagem referente a Tela de instru��es. O jogo pode ser iniciado tanto com o clique do mouse, quanto com o uso da tecla Enter.
		tempoTela+=Gdx.graphics.getDeltaTime();
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		instrucoesSprite.draw(batch);
		batch.end();
		
		camera.position.x = 320;
		camera.position.y = 240;
		camera.update();
		
		if(tempoTela>=3){
			condicaoInicio = 2;
		}
		
	}
	
	public void telaGameOver(){ // Renderiza uma imagem de Game Over na tela, a qual pode ser fechada ao pressionar a tecla Enter, ou com o clique do bot�o esquerdo do mouse, ou tamb�m � poss�vel simplesmente esperar ela se fechar automaticamente depois de 6 segundos. Ap�s fechada, o jogo � redirecionado ao menu inicial.
		tempoTela+=Gdx.graphics.getDeltaTime();
		
		somDeCorrida.dispose();
		somDePassos.dispose();
		font.dispose();
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		gameOverSprite.draw(batch);
		batch.end();
		
		camera.position.x = 320;
		camera.position.y = 240;
		camera.update();
		
		if(Gdx.input.isTouched() || Gdx.input.isKeyPressed(Input.Keys.ENTER) || tempoTela>=6){
			create();
		}
		
	}
	
	
	
	public void updateCamera(){ // Relacionada ao movimento da c�mera no cen�rio, a qual acompanha a posi��o x do jogador. Ela inicia na posi��o X de 320, a qual � referente ao centro da tela vis�vel pela c�mera (a tela do jogo em si possui 800 de largura e 600 de altura, por�m a c�mera captura uma regi�o com uma menor resolu��o, realizando um zoom de 640 em largura por 480 em altura), e s� come�a a seguir o jogador quando ele chega nessa posi��o. Ela faz a mesma coisa quando o jogador est� se aproximando do final do mapa, verificando se ele atingiu o ponto X de 5760, se sim, ela trava nessa posi��o e para de seguir o jogador. Isso � feito com o intuito da c�mera s� mostrar o que h� na regi�o do mapa renderizado.
		if(playerCoyote.getX()>=320 && playerCoyote.getX()<=5760){
			camera.position.x = playerCoyote.getX();
		}else
		if(playerCoyote.getX()>5760){
			camera.position.x = 5760;
		}else{
			camera.position.x = 320;
		}

		camera.update();
	}
	
	
	@Override
	public void dispose () { // Descarta imagens, sons, e fontes de texto do projeto, causando o seu fechamento.
		GerenciadorDeTexturas.dispose();
		telaVitoria.dispose();
		menuInicial.dispose();
		background.dispose();
		pilulas.dispose();
		coletavelAcme.dispose();
		coletavelVida.dispose();
		somDeCorrida.dispose();
		somDePassos.dispose();
		somDeTempoAcabando.dispose();
		musicaDeVitoria.dispose();
		font.dispose();
		gameOver.dispose();
		batch.dispose();
	}
}
