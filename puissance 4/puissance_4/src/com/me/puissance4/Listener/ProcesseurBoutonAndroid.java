package com.me.puissance4.Listener;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.me.puissance4.Puissance4;
import com.me.puissance4.Ecran.Ecran;

public class ProcesseurBoutonAndroid implements InputProcessor {

	Ecran ecran;

	public ProcesseurBoutonAndroid(Ecran ecran) {
		this.ecran = ecran;
	}

	@Override
	public boolean keyDown(int keycode) {
		if (keycode == Keys.BACK) {
			Gdx.app.setLogLevel(Application.LOG_DEBUG);
			Gdx.app.log(Puissance4.LOG, "back");
			return true;
		}
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
