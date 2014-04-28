package com.hexatan.puissance4.Ecran;

import java.util.concurrent.Exchanger;

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
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.hexatan.puissance4.Puissance4;
import com.hexatan.puissance4.UtilMenu;
import com.hexatan.puissance4.Modele.Jeu;
import com.hexatan.puissance4.Reseau.ServeurPartage;

/**
 * The Class Attente.
 */
public class EcranAttente extends Ecran {

	/** The stage. */
	private Stage stage;

	/** The atlas. */
	private TextureAtlas atlas;

	/** The skin. */
	private Skin skin;

	/** The style b. */
	private TextButtonStyle styleB;

	/** The text. */
	private TextField text;

	/** The adresse. */
	private TextField adresse;

	private Jeu jeu;

	private Exchanger<Jeu> echangeur;

	/**
	 * Instantiates a new attente.
	 * 
	 * @param puissance4
	 *            the puissance4
	 */
	public EcranAttente(Puissance4 puissance4) {
		super(puissance4);
		jeu = new Jeu(false);
		echangeur = new Exchanger<Jeu>();
		ServeurPartage serveur = ServeurPartage.getInstance(jeu);
		Thread TServeur = new Thread(serveur);
		TServeur.start();
		styleB = new TextButtonStyle();
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
			stage = new Stage();
		}
		stage.clear();
		Gdx.input.setInputProcessor(stage);
		UtilMenu.makeStyle(styleB, skin, policeNoir, "bouton", "boutonpress");

		text = new TextField("Attente de votre adversaire",
				new TextField.TextFieldStyle(policeNoir, Color.WHITE, null,
						null, null));
		text.setX(Gdx.graphics.getWidth() / 2 - 600 / 2);
		text.setY(Gdx.graphics.getHeight() / 2 - 150 / 2);
		text.setWidth(700);
		stage.addActor(text);

		adresse = new TextField("Vos adresses IP sont : "
				+ ServeurPartage.recupAdresseIP(),
				new TextField.TextFieldStyle(policeNoir, Color.WHITE, null,
						null, null));
		adresse.setX(Gdx.graphics.getWidth() / 2 - 600 / 2);
		adresse.setY(Gdx.graphics.getHeight() / 2 - 300 / 2);
		adresse.setWidth(700);
		stage.addActor(adresse);

		stage.addActor(UtilMenu.makeButton("Retour", styleB, 0, 0,
				150 * echelleX, 37 * echelleY, new InputListener() {
					public boolean touchDown(InputEvent event, float x,
							float y, int pointer, int button) {
						return true;

					}

					public void touchUp(InputEvent event, float x, float y,
							int pointer, int button) {
						game.setScreen(new EcranChoixMulti(game));
					}
				}));
		TextButtonStyle style = new TextButtonStyle();
		float y = Gdx.graphics.getHeight() / 2 - 400 / 2;

		stage.addActor(UtilMenu.makeButton("Jouer", styleB, y - 100,
				new InputListener() {
					public boolean touchDown(InputEvent event, float x,
							float y, int pointer, int button) {
						return true;
					}

					public void touchUp(InputEvent event, float x, float y,
							int pointer, int button) {
						game.setScreen(new EcranJeu(game, jeu));
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
