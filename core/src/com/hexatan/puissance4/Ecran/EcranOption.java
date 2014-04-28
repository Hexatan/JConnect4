package com.hexatan.puissance4.Ecran;

import java.util.Random;

import com.badlogic.gdx.Gdx;
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
import com.hexatan.puissance4.FonctionUtil;
import com.hexatan.puissance4.Puissance4;
import com.hexatan.puissance4.UtilMenu;
import com.hexatan.puissance4.Modele.ColorPicker;
import com.hexatan.puissance4.Modele.Joueur;

// TODO: Auto-generated Javadoc
/**
 * The Class OptionScreen.
 */
public class EcranOption extends Ecran {

	/** The stage. */
	private Stage stage;

	/** The skin. */
	private Skin skin;

	/** The atlas. */
	private TextureAtlas atlas;

	/** The style b. */
	private TextButtonStyle styleB;

	/** The text. */
	private TextField text;

	/** The str. */
	private String str = "";

	/** The selection couleur. */
	private ColorPicker selectionCouleur;

	/**
	 * Instantiates a new option screen.
	 * 
	 * @param puissance4
	 *            the puissance4
	 */
	public EcranOption(Puissance4 puissance4) {
		super(puissance4);
		styleB = new TextButtonStyle();

		text = new TextField(str, new TextFieldStyle(new BitmapFont(
				Gdx.files.internal("Fonts/black.fnt"), false), Color.WHITE,
				null, null, null));
		text.setX(500 - 300 / 2);
		text.setY(Gdx.graphics.getHeight() / 2 - 150 / 2);
		text.setWidth(300);

		selectionCouleur = new ColorPicker(skin);

		if (FonctionUtil.verificationConfigurationJoueur()) {
			selectionCouleur.setSelectedColor(Color.valueOf(Joueur
					.lireParametreJoueur().get(2)));
			text.setText(Joueur.lireParametreJoueur().get(1));
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

		multiplexer.addProcessor(stage);
		Gdx.input.setInputProcessor(multiplexer);
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

		selectionCouleur.setY(50);
		stage.addActor(selectionCouleur);

		TextFieldStyle s = text.getStyle();
		s.background = skin.getDrawable("bouton");
		text.setStyle(s);
		stage.addActor(text);
		UtilMenu.makeStyle(styleB, skin, policeNoir, "bouton", "boutonpress");

		stage.addActor(UtilMenu.makeButton("Valider", styleB, 500 - 300 / 2,
				Gdx.graphics.getHeight() / 2 - 300 / 2, 300, 75,
				new InputListener() {
					public boolean touchDown(InputEvent event, float x,
							float y, int pointer, int button) {
						Random r = new Random();
						Joueur.ecrireXMLJoueur(String.valueOf(r.nextInt()),
								text.getText(), selectionCouleur
										.getSelectedColor().toString(), "0",
								"0");
						return true;
					}

					public void touchUp(InputEvent event, float x, float y,
							int pointer, int button) {
					}
				}));

		UtilMenu.makeStyle(styleB, skin, policeNoir, "bouton", "boutonpress");
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
		stage.dispose();
		skin.dispose();
		atlas.dispose();
	}
}
