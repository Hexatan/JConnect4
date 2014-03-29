package com.me.puissance4.Listener;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;

// TODO: Auto-generated Javadoc
/**
 * processeur gérant l'entré de texte dans un TextField dynamiquement.
 *
 * @author gaetan
 */
public class KeyboardProcessor implements InputProcessor {

	/** TextField étant modifier par le processeur. */
	TextField text;
	
	/** String content le texte du TextField. */
	String str;

	/**
	 * Instancie un nouveau KeyboardProcessor.
	 *
	 * @param text            TexteField utilisé par le processeur
	 * @param str            String utlisé par le processeur
	 */
	public KeyboardProcessor(TextField text, String str) {
		this.text = text;
		this.str = str;
		if (str == null) {
			str = "";
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.badlogic.gdx.InputProcessor#touchUp(int, int, int, int)
	 */
	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.badlogic.gdx.InputProcessor#touchDragged(int, int, int)
	 */
	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.badlogic.gdx.InputProcessor#touchDown(int, int, int, int)
	 */
	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.badlogic.gdx.InputProcessor#scrolled(int)
	 */
	@Override
	public boolean scrolled(int amount) {
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.badlogic.gdx.InputProcessor#mouseMoved(int, int)
	 */
	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.badlogic.gdx.InputProcessor#keyUp(int)
	 */
	@Override
	public boolean keyUp(int keycode) {
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.badlogic.gdx.InputProcessor#keyTyped(char)
	 */
	@Override
	public boolean keyTyped(char character) {

		if ((int) character == 47 || (int) character < 46
				|| (int) character > 57) {
			return true;
		}

		if (str.length() > 14) {
			return true;
		}

		final String realTemp = str + character;
		
		Gdx.app.postRunnable(new Runnable() {
			@Override
			public void run() {
				text.setText(realTemp);
				str = realTemp;
			}

		});
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.badlogic.gdx.InputProcessor#keyDown(int)
	 */
	@Override
	public boolean keyDown(int keycode) {

		if (keycode == Keys.ENTER) {
			System.out.print("entrer");
		}

		if (keycode == Keys.BACKSPACE) {
			if (str.length() >= 1) {
				final String tmp = str.substring(0, str.length() - 1);
				Gdx.app.postRunnable(new Runnable() {

					@Override
					public void run() {
						text.setText(tmp);
						str = tmp;
					}

				});
			}
		}
		return true;
	}
}
