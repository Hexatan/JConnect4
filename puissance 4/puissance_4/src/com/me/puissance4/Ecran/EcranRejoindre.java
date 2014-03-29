package com.me.puissance4.Ecran;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;

import org.apache.commons.validator.routines.InetAddressValidator;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.TextField.TextFieldStyle;
import com.me.puissance4.Puissance4;
import com.me.puissance4.UtilMenu;
import com.me.puissance4.Listener.KeyboardProcessor;
import com.me.puissance4.Modele.Jeu;
import com.me.puissance4.Modele.Joueur;
import com.me.puissance4.Reseau.ClientPartage;
import com.me.puissance4.Reseau.Protocole;

/**
 * The Class MultiRejoindre.
 */
public class EcranRejoindre extends Ecran implements ApplicationListener {

	/** The stage. */
	private Stage stage;

	/** The atlas. */
	private TextureAtlas atlas;

	/** The skin. */
	private Skin skin;

	/** The text. */
	private TextField text;

	/** The multi plexer. */
	private InputMultiplexer multiPlexer;

	/** The keyboardprocesor. */
	private KeyboardProcessor keyboardprocesor;

	/** The str. */
	private String str = "";

	/** The style. */
	private TextFieldStyle style;

	/** The style b. */
	private TextButtonStyle styleB;

	private InetAddressValidator validateur;

	private Jeu jeu;

	/**
	 * Instantiates a new multi rejoindre.
	 * 
	 * @param puissance4
	 *            the puissance4
	 */
	public EcranRejoindre(Puissance4 puissance4) {
		super(puissance4);
		style = new TextFieldStyle();
		styleB = new TextButtonStyle();
		UtilMenu.makeStyle(style, policeNoir);

		text = new TextField(str, new TextField.TextFieldStyle(new BitmapFont(
				Gdx.files.internal("Fonts/black.fnt"), false), Color.WHITE,
				null, null, null));
		text.setX(Gdx.graphics.getWidth() / 2 - 300 / 2);
		text.setY(Gdx.graphics.getHeight() / 2 + 50);
		text.setWidth(300);

		validateur = InetAddressValidator.getInstance();

		keyboardprocesor = new KeyboardProcessor(text, str);
		multiPlexer = new InputMultiplexer();
		multiPlexer.addProcessor(keyboardprocesor);
		stage = new Stage();
		multiPlexer.addProcessor(stage);
		Gdx.input.setInputProcessor(multiPlexer);
		jeu = new Jeu(false);
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

		text.setText(str);
		TextFieldStyle s = text.getStyle();
		s.background = skin.getDrawable("bouton");
		text.setStyle(s);
		stage.addActor(text);
		UtilMenu.makeStyle(styleB, skin, policeNoir, "bouton", "boutonpress");

		InetAddress adresseLocal;
		try {
			adresseLocal = InetAddress.getLocalHost();
			String adresse = adresseLocal.toString().substring(
					adresseLocal.toString().indexOf("/") + 1);
			text.setText(adresse);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		text.setText("10.1.44.217");

		stage.addActor(UtilMenu.makeButton("Valider", styleB,
				Gdx.graphics.getWidth() / 2 - 300 / 2,
				Gdx.graphics.getHeight() / 2 - 75 / 2, 300, 75,
				new InputListener() {
					public boolean touchDown(InputEvent event, float x,
							float y, int pointer, int button) {

						if (validateur.isValid(text.getText())) {
							Puissance4.hote = text.getText();
							final ClientPartage client = ClientPartage
									.getInstance(text.getText(), jeu);
							Gdx.app.setLogLevel(Application.LOG_DEBUG);
							Gdx.app.log(Puissance4.LOG, client.toString());
							Thread processusReseau = new Thread(new Runnable() {
								public void run() {
									client.recevoirMessages();
								}
							});
							processusReseau.start();
							ArrayList<String> infos = Joueur
									.lireParametreJoueur();
							client.envoyerMessage(Protocole
									.preparerMessageInitialisationJoueur(
											Integer.parseInt(infos.get(0)),
											infos.get(1),
											Color.valueOf(infos.get(2)),
											Integer.parseInt(infos.get(3)),
											Integer.parseInt(infos.get(4))));
						}
						return true;
					}

					public void touchUp(InputEvent event, float x, float y,
							int pointer, int button) {

					}
				}));

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
		UtilMenu.makeStyle(style, skin, policeNoir, "bouton");
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.badlogic.gdx.ApplicationListener#create()
	 */
	@Override
	public void create() {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.badlogic.gdx.ApplicationListener#render()
	 */
	@Override
	public void render() {

	}

}
