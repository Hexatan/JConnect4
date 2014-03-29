package com.me.puissance4.Ecran;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.me.puissance4.Puissance4;
import com.me.puissance4.UtilMenu;
import com.me.puissance4.Listener.SourisJeuProcesseur;
import com.me.puissance4.Modele.Jeu;
import com.me.puissance4.Modele.Joueur;
import com.me.puissance4.Modele.Pion;

/**
 * The Class EcranJeu.
 */
public class EcranJeu extends Ecran {

	/** The atlas. */
	private TextureAtlas atlas;

	/** The skin. */
	private Skin skin;

	/** The batch. */
	private SpriteBatch batch;

	/** The texturepion. */
	private Texture texturepion;

	/** The sprite pion. */
	private Sprite spritePion;

	/** The souris jeu processeur */
	private SourisJeuProcesseur sourisJeuProcesseur;

	/** The jeu. */
	private Jeu jeu;

	/** The texture case. */
	private Texture textureCase;

	/** The stage. */
	private Stage stage;

	/** The style b. */
	private TextButtonStyle styleB;

	private int largeurCaseFinal;

	private int origineGrille[] = new int[2];

	/**
	 * Instantiates a new ecran jeu.
	 * 
	 * @param puissance4
	 *            the puissance4
	 */
	public EcranJeu(Puissance4 puissance4) {
		super(puissance4);
		calculTailleGrille();
		jeu = new Jeu(true);
		ajoutPion();
		styleB = new TextButtonStyle();
		sourisJeuProcesseur = new SourisJeuProcesseur(jeu, this);
		stage = new Stage();
		multiplexer.addProcessor(sourisJeuProcesseur);
		multiplexer.addProcessor(stage);
		Gdx.input.setInputProcessor(multiplexer);
	}

	public EcranJeu(Puissance4 puissance4, Jeu jeu) {
		super(puissance4);
		calculTailleGrille();
		this.jeu = jeu;
		ajoutPion();
		styleB = new TextButtonStyle();
		sourisJeuProcesseur = new SourisJeuProcesseur(jeu, this);
		stage = new Stage();
		multiplexer.addProcessor(sourisJeuProcesseur);
		multiplexer.addProcessor(stage);
		Gdx.input.setInputProcessor(multiplexer);
	}

	public void calculTailleGrille() {
		int largeurCase = (largeurEcran - 352) / 7;
		int largeurCase2 = (hauteurEcran - 216) / 6;

		largeurCaseFinal = largeurCase < largeurCase2 ? largeurCase
				: largeurCase2;

		int largeurGrille = largeurCaseFinal * 7;
		int hauteurGrille = largeurCaseFinal * 6;

		origineGrille[0] = (largeurEcran - largeurGrille) / 2;

		origineGrille[1] = (hauteurEcran - hauteurGrille) / 2;
	}

	public void ajoutPion() {
		texturepion = new Texture(Gdx.files.internal("Textures/pionB.png"));
		spritePion = new Sprite(texturepion, 0, 0, 64, 64);

		jeu.getJoueurLocal().setPion(
				new Pion(spritePion, jeu.getJoueurLocal().getCouleur(),
						56 * echelleX, 450 * echelleY, largeurCaseFinal));
		if (!jeu.isSolo() || !jeu.isMulti()) {
			jeu.getJoueurDistant().setPion(
					new Pion(spritePion, jeu.getJoueurDistant().getCouleur(),
							680 * echelleX, 450 * echelleY, largeurCaseFinal));
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
		batch.begin();
		afficheDonnees(policeNoir);
		jeu.getGrille().dessine(batch, largeurCaseFinal, origineGrille[0],
				origineGrille[1]);

		if (jeu.joueurActif()) {
			jeu.getTabJoueurs().get(0).getPion()
					.dessine(batch, largeurCaseFinal);
		} else {
			if (!jeu.isSolo()) {
				jeu.getTabJoueurs().get(1).getPion()
						.dessine(batch, largeurCaseFinal);
			}
		}
		afficheGagnant(policeNoir);
		batch.end();
		stage.draw();
	}

	/**
	 * Affiche donnees.
	 * 
	 * @param police
	 *            the police
	 */
	public void afficheDonnees(BitmapFont police) {
		Joueur local = jeu.getJoueurLocal();
		Joueur distant = jeu.getJoueurDistant();
		police.setScale(echelleY);
		police.draw(batch, local.getNom(), 100, hauteurEcran - 50);
		police.draw(batch, distant.getNom(), largeurEcran - 300,
				hauteurEcran - 50);
		police.setScale(1);
	}

	public void afficheGagnant(BitmapFont police) {
		if (jeu.isGagnant()) {
			batch.end();
			super.dessineFond(batch);
			String nomGagnant = jeu.getJoueurGagnant().getNom();
			police.setScale(echelleY);
			batch.begin();
			police.draw(batch, nomGagnant + " gagne la partie", 100,
					hauteurEcran - 50);
			police.setScale(1);
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
			stage = new Stage(width, height, true);
		}
		super.dessineFond(batch);

		UtilMenu.makeStyle(styleB, skin, policeNoir, "bouton", "boutonpress");

		stage.addActor(UtilMenu.makeButton("Retour", styleB, 0, 0,
				150 * echelleX, 37 * echelleY, new InputListener() {
					public boolean touchDown(InputEvent event, float x,
							float y, int pointer, int button) {
						game.setScreen(new EcranMenuPrincipal(game));
						return true;
					}

					public void touchUp(InputEvent event, float x, float y,
							int pointer, int button) {

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
		textureCase = new Texture(Gdx.files.internal("Textures/vide.png"));
		spritePion = new Sprite(new Texture(
				Gdx.files.internal("Textures/pionB.png")));
		policeNoir = new BitmapFont(Gdx.files.internal("Fonts/black.fnt"),
				false);
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
		policeNoir.dispose();
		stage.dispose();
	}

	public int getLargeurCaseFinal() {
		return largeurCaseFinal;
	}

	public void setLargeurCaseFinal(int largeurCaseFinal) {
		this.largeurCaseFinal = largeurCaseFinal;
	}

}
