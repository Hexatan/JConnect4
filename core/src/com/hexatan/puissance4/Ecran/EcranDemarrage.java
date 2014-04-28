package com.hexatan.puissance4.Ecran;

import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenCallback;
import aurelienribon.tweenengine.TweenEquations;
import aurelienribon.tweenengine.TweenManager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.hexatan.puissance4.Puissance4;
import com.hexatan.puissance4.Tween.SpriteTween;

// TODO: Auto-generated Javadoc
/**
 * The Class SplashScreen.
 */
public class EcranDemarrage extends Ecran {

	/** The splash texture. */
	Texture splashTexture;

	/** The splash srpite. */
	Sprite splashSrpite;

	/** The t manager. */
	TweenManager tManager;

	/**
	 * Instantiates a new splash screen.
	 * 
	 * @param puissance4
	 *            the puissance4
	 */
	public EcranDemarrage(Puissance4 puissance4) {
		super(puissance4);
		game = puissance4;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.badlogic.gdx.Screen#render(float)
	 */
	@Override
	public void render(float delta) {
		super.render(delta);
		Gdx.gl20.glClearColor(0, 0, 0, 1);
		Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);
		tManager.update(delta);
		super.dessineFond(batch);
		batch.begin();

		splashSrpite.draw(batch);
		batch.end();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.badlogic.gdx.Screen#resize(int, int)
	 */
	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.badlogic.gdx.Screen#show()
	 */
	@Override
	public void show() {
		super.show();
		splashTexture = new Texture(
				Gdx.files.internal("Textures/SplashScreen2.png"));
		splashTexture.setFilter(TextureFilter.Linear, TextureFilter.Linear);

		splashSrpite = new Sprite(splashTexture);
		splashSrpite.setColor(1, 1, 1, 0);

		batch = new SpriteBatch();

		Tween.registerAccessor(Sprite.class, new SpriteTween());

		tManager = new TweenManager();

		TweenCallback cb = new TweenCallback() {

			@Override
			public void onEvent(int type, BaseTween<?> source) {
				tweenCompleted();
			}
		};

		Tween.to(splashSrpite, SpriteTween.ALPHA, 2f).target(1)
				.ease(TweenEquations.easeInSine).repeatYoyo(1, 0.5f)
				.setCallback(cb).setCallbackTriggers(TweenCallback.COMPLETE)
				.start(tManager);

	}

	/**
	 * Tween completed.
	 */
	private void tweenCompleted() {
		game.setScreen(new EcranMenuPrincipal(game));
		this.dispose();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.badlogic.gdx.Screen#hide()
	 */
	@Override
	public void hide() {
		super.hide();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.badlogic.gdx.Screen#pause()
	 */
	@Override
	public void pause() {
		super.pause();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.badlogic.gdx.Screen#resume()
	 */
	@Override
	public void resume() {
		super.resume();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.badlogic.gdx.Screen#dispose()
	 */
	@Override
	public void dispose() {
		super.dispose();
	}

}
