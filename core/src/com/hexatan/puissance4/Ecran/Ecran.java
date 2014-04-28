package com.hexatan.puissance4.Ecran;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.hexatan.puissance4.Puissance4;
import com.hexatan.puissance4.Listener.ProcesseurBoutonAndroid;

/**
 * The Class Ecran.
 */
public class Ecran extends ScreenAdapter {

	/** The fond texture. */
	protected Texture fondTexture;
	/** The batch. */
	protected SpriteBatch batch;

	/** The black. */
	protected BitmapFont policeNoir;

	/** The police blanche. */
	protected BitmapFont policeBlanche;

	/** The game. */
	protected Puissance4 game;

	protected int hauteurEcran;

	protected int largeurEcran;

	protected float echelleX;

	protected float echelleY;

	protected ProcesseurBoutonAndroid processeurBoutonAndroid;

	/** The multiplexer. */
	protected InputMultiplexer multiplexer;

	/**
	 * Instantiates a new ecran.
	 * 
	 * @param puissance4
	 *            the puissance4
	 */
	public Ecran(Puissance4 puissance4) {
		game = puissance4;
		fondTexture = new Texture(Gdx.files.internal("Textures/fond.png"));

		hauteurEcran = Gdx.graphics.getHeight();
		largeurEcran = Gdx.graphics.getWidth();
		echelleY = (hauteurEcran / 600f);
		echelleX = (largeurEcran / 800f);
		multiplexer = new InputMultiplexer();
		processeurBoutonAndroid = new ProcesseurBoutonAndroid(this);
		multiplexer.addProcessor(processeurBoutonAndroid);
		Gdx.input.setInputProcessor(multiplexer);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.badlogic.gdx.ScreenAdapter#render(float)
	 */
	@Override
	public void render(float delta) {
		super.render(delta);
		Gdx.gl20.glClearColor(0, 0, 0, 1);
		Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		batch.end();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.badlogic.gdx.ScreenAdapter#resize(int, int)
	 */
	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.badlogic.gdx.ScreenAdapter#show()
	 */
	@Override
	public void show() {
		super.show();
		batch = new SpriteBatch();
		fondTexture = new Texture(Gdx.files.internal("Textures/fond.png"));
		policeNoir = new BitmapFont(Gdx.files.internal("Fonts/black.fnt"),
				false);
		policeBlanche = new BitmapFont(Gdx.files.internal("Fonts/font.fnt"),
				false);
	}

	/**
	 * Dessine fond.
	 * 
	 * @param batch
	 *            the batch
	 */
	public void dessineFond(SpriteBatch batch) {
		int x = Gdx.graphics.getWidth();
		int y = Gdx.graphics.getHeight();
		batch.begin();
		batch.draw(fondTexture, 0, 0, 0, 0, x, y, echelleX, echelleY, 0, 0, 0,
				800, 600, false, false);
		batch.end();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.badlogic.gdx.ScreenAdapter#hide()
	 */
	@Override
	public void hide() {
		super.hide();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.badlogic.gdx.ScreenAdapter#pause()
	 */
	@Override
	public void pause() {
		super.pause();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.badlogic.gdx.ScreenAdapter#resume()
	 */
	@Override
	public void resume() {
		super.resume();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.badlogic.gdx.ScreenAdapter#dispose()
	 */
	@Override
	public void dispose() {
		super.dispose();
	}

	/**
	 * Gets the fond texture.
	 * 
	 * @return the fond texture
	 */
	public Texture getFondTexture() {
		return fondTexture;
	}

	/**
	 * Sets the fond texture.
	 * 
	 * @param fondTexture
	 *            the new fond texture
	 */
	public void setFondTexture(Texture fondTexture) {
		this.fondTexture = fondTexture;
	}

	public float getEchelleX() {
		return echelleX;
	}

	public void setEchelleX(float echelleX) {
		this.echelleX = echelleX;
	}

	public float getEchelleY() {
		return echelleY;
	}

	public void setEchelleY(float echelleY) {
		this.echelleY = echelleY;
	}

	public int getLargeurEcran() {
		return largeurEcran;
	}

	public void setLargeurEcran(int largeurEcran) {
		this.largeurEcran = largeurEcran;
	}

}
