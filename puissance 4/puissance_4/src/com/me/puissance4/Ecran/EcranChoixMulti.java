package com.me.puissance4.Ecran;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.me.puissance4.Puissance4;
import com.me.puissance4.UtilMenu;
import com.me.puissance4.Listener.ProcesseurBoutonAndroid;

// TODO: Auto-generated Javadoc
/**
 * The Class ChoixMulti.
 */
public class EcranChoixMulti extends Ecran {

	/** The stage. */
	private Stage stage;

	/** The atlas. */
	private TextureAtlas atlas;

	/** The skin. */
	private Skin skin;

	/** The style b. */
	private TextButtonStyle styleB;

	/**
	 * Instantiates a new choix multi.
	 * 
	 * @param puissance4
	 *            the puissance4
	 */
	public EcranChoixMulti(Puissance4 puissance4) {
		super(puissance4);
		game = puissance4;
		styleB = new TextButtonStyle();
		multiplexer = new InputMultiplexer();
		processeurBoutonAndroid = new ProcesseurBoutonAndroid(this);
		stage = new Stage();
		multiplexer.addProcessor(processeurBoutonAndroid);
		multiplexer.addProcessor(stage);
		Gdx.input.setInputProcessor(multiplexer);
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

		stage.act(delta);
		super.dessineFond(batch);
		batch.begin();

		batch.end();
		stage.draw();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.badlogic.gdx.Screen#resize(int, int)
	 */
	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
		if (stage == null) {
			stage = new Stage(width, height, true);
		}
		stage.clear();

		float y = Gdx.graphics.getHeight() / 2 - 400 / 2;

		UtilMenu.makeStyle(styleB, skin, policeNoir, "bouton", "boutonpress");
		stage.addActor(UtilMenu.makeButton("Créer", styleB, y + 175,
				new InputListener() {
					public boolean touchDown(InputEvent event, float x,
							float y, int pointer, int button) {
						return true;

					}

					public void touchUp(InputEvent event, float x, float y,
							int pointer, int button) {
						game.setScreen(new EcranAttente(game));
					}
				}));

		stage.addActor(UtilMenu.makeButton("Rejoindre", styleB, y + 50,
				new InputListener() {
					public boolean touchDown(InputEvent event, float x,
							float y, int pointer, int button) {
						return true;

					}

					public void touchUp(InputEvent event, float x, float y,
							int pointer, int button) {
						game.setScreen(new EcranRejoindre(game));
					}
				}));
		stage.addActor(UtilMenu.makeButton("Retour", styleB, 0, 0,
				150 * echelleX, 37 * echelleY, new InputListener() {
					public boolean touchDown(InputEvent event, float x,
							float y, int pointer, int button) {
						return true;

					}

					public void touchUp(InputEvent event, float x, float y,
							int pointer, int button) {
						game.setScreen(new EcranMenuPrincipal(game));
					}
				}));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.badlogic.gdx.Screen#show()
	 */
	@Override
	public void show() {
		super.show();
		batch = new SpriteBatch();
		skin = new Skin();
		atlas = new TextureAtlas("Textures/texture.pack");
		skin.addRegions(atlas);
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
		batch.dispose();
		skin.dispose();
		atlas.dispose();
		stage.dispose();
	}

}
