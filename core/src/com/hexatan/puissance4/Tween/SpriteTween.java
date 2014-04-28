package com.hexatan.puissance4.Tween;

import aurelienribon.tweenengine.TweenAccessor;

import com.badlogic.gdx.graphics.g2d.Sprite;

// TODO: Auto-generated Javadoc
/**
 * The Class SpriteTween.
 */
public class SpriteTween implements TweenAccessor<Sprite> {

	/** The Constant ALPHA. */
	public static final int ALPHA = 1;

	/*
	 * (non-Javadoc)
	 * 
	 * @see aurelienribon.tweenengine.TweenAccessor#getValues(java.lang.Object,
	 * int, float[])
	 */
	@Override
	public int getValues(Sprite target, int tweenType, float[] returnValues) {
		switch (tweenType) {
		case ALPHA:
			;
			returnValues[0] = target.getColor().a;
			return 1;

		default:
			return 0;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see aurelienribon.tweenengine.TweenAccessor#setValues(java.lang.Object,
	 * int, float[])
	 */
	@Override
	public void setValues(Sprite target, int tweenType, float[] newValues) {
		switch (tweenType) {
		case ALPHA:
			target.setColor(1, 1, 1, newValues[0]);
			break;

		default:
			break;
		}
	}

}
