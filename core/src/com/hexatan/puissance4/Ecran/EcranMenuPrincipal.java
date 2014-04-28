package com.hexatan.puissance4.Ecran;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.hexatan.puissance4.FonctionUtil;
import com.hexatan.puissance4.Puissance4;
import com.hexatan.puissance4.UtilMenu;
import com.hexatan.puissance4.Modele.Jeu;

/**
 * The Class MainMenu.
 */
public class EcranMenuPrincipal extends Ecran {

	/** The stage. */
	private Stage stage;

	/** The atlas. */
	private TextureAtlas atlas;

	/** The skin. */
	private Skin skin;

	/** The batch texte. */
	private SpriteBatch batchTexte;

	boolean config;

	/**
	 * Instantiates a new main menu.
	 * 
	 * @param puissance4
	 *            the puissance4
	 */
	public EcranMenuPrincipal(Puissance4 puissance4) {
		super(puissance4);
		config = true;
		if (!FonctionUtil.verificationConfigurationJoueur()) {
			config = false;
		}
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

		stage.draw();
		if (!config) {
			batch.begin();
			policeBlanche.setColor(Color.RED);
			policeBlanche.drawWrapped(batch, "Config pseudo avant jeu", 600,
					(Gdx.graphics.getHeight() / 2 - 400 / 2) + 200, 200);
			batch.end();
		}
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
			stage = new Stage();
		}
		stage.clear();
		Gdx.input.setInputProcessor(stage);
		TextButtonStyle style = new TextButtonStyle();
		float y = Gdx.graphics.getHeight() / 2 - 400 / 2;
		UtilMenu.makeStyle(style, skin, policeNoir, "bouton", "boutonpress");

		stage.addActor(UtilMenu.makeButton("Jeu solo", style, y + 400,
				new InputListener() {
					public boolean touchDown(InputEvent event, float x,
							float y, int pointer, int button) {
						return true;
					}

					public void touchUp(InputEvent event, float x, float y,
							int pointer, int button) {
						Jeu jeuSolo = new Jeu(true, true);
						game.setScreen(new EcranJeu(game, jeuSolo));
					}
				}));

		stage.addActor(UtilMenu.makeButton("Jeu local", style, y + 275,
				new InputListener() {
					public boolean touchDown(InputEvent event, float x,
							float y, int pointer, int button) {
						return true;
					}

					public void touchUp(InputEvent event, float x, float y,
							int pointer, int button) {
						game.setScreen(new EcranJeu(game));
					}
				}));

		stage.addActor(UtilMenu.makeButton("Jeu reseau", style, y + 150,
				new InputListener() {
					public boolean touchDown(InputEvent event, float x,
							float y, int pointer, int button) {
						return true;

					}

					public void touchUp(InputEvent event, float x, float y,
							int pointer, int button) {
						game.setScreen(new EcranChoixMulti(game));
					}
				}));

		stage.addActor(UtilMenu.makeButton("Option", style, y + 25,
				new InputListener() {
					public boolean touchDown(InputEvent event, float x,
							float y, int pointer, int button) {
						return true;
					}

					public void touchUp(InputEvent event, float x, float y,
							int pointer, int button) {
						game.setScreen(new EcranOption(game));
					}
				}));

		stage.addActor(UtilMenu.makeButton("Quitter", style, y - 100,
				new InputListener() {
					public boolean touchDown(InputEvent event, float x,
							float y, int pointer, int button) {
						return true;

					}

					public void touchUp(InputEvent event, float x, float y,
							int pointer, int button) {
						Gdx.app.exit();
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
		batchTexte = new SpriteBatch();
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
		super.dispose();
		super.resume();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.badlogic.gdx.Screen#dispose()
	 */
	@Override
	public void dispose() {
		batch.dispose();
		skin.dispose();
		atlas.dispose();
		stage.dispose();
		policeBlanche.dispose();
	}

}
