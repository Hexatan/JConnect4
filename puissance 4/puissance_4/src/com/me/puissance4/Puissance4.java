package com.me.puissance4;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.me.puissance4.Ecran.EcranDemarrage;

/**
 * The Class Puissance4.
 */
public class Puissance4 extends Game implements ApplicationListener,
		InputProcessor {

	/** The Constant VERSION. */
	public static final String VERSION = "0.0.2.1";

	/** The Constant LOG. */
	public static final String LOG = "Puissance 4";

	public static String hote;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.badlogic.gdx.ApplicationListener#create()
	 */
	@Override
	public void create() {
		setScreen(new EcranDemarrage(this));
		Gdx.app.setLogLevel(Application.LOG_DEBUG);
		Gdx.input.setCatchBackKey(true);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.badlogic.gdx.Game#dispose()
	 */
	@Override
	public void dispose() {
		super.dispose();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.badlogic.gdx.Game#render()
	 */
	@Override
	public void render() {
		super.render();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.badlogic.gdx.Game#resize(int, int)
	 */
	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.badlogic.gdx.Game#pause()
	 */
	@Override
	public void pause() {
		super.pause();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.badlogic.gdx.Game#resume()
	 */
	@Override
	public void resume() {
		super.resume();
	}

	@Override
	public boolean keyDown(int keycode) {
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		return false;
	}
}
