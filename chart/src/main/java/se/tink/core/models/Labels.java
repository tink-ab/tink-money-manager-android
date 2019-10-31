package se.tink.core.models;


import android.graphics.Typeface;
import java.util.List;

public class Labels {

	private List<String> texts;

	private Typeface typeface1;
	private int textColor1;
	private float textSize1;
	private float characterSpacing1;

	private Typeface typeface2;
	private int textColor2;
	private float textSize2;
	private float characterSpacing2;

	private Typeface typeface3;
	private int textColor3;
	private float textSize3;
	private float characterSpacing3;

	public void setText1Style(Typeface typeface1, int textColor1, float textSize1,
		float characterSpacing1) {
		this.typeface1 = typeface1;
		this.textColor1 = textColor1;
		this.textSize1 = textSize1;
		this.characterSpacing1 = characterSpacing1;
	}

	public void setText2Style(Typeface typeface2, int textColor2, float textSize2,
		float characterSpacing2) {
		this.typeface2 = typeface2;
		this.textColor2 = textColor2;
		this.textSize2 = textSize2;
		this.characterSpacing2 = characterSpacing2;
	}

	public void setText3Style(Typeface typeface3, int textColor3, float textSize3,
		float characterSpacing3) {
		this.typeface3 = typeface3;
		this.textColor3 = textColor3;
		this.textSize3 = textSize3;
		this.characterSpacing3 = characterSpacing3;
	}

	public void setTexts(List<String> texts) {
		this.texts = texts;
	}

	public List<String> getTexts() {
		return texts;
	}

	public Typeface getTypeface1() {
		return typeface1;
	}

	public int getTextColor1() {
		return textColor1;
	}

	public float getTextSize1() {
		return textSize1;
	}

	public Typeface getTypeface2() {
		return typeface2;
	}

	public int getTextColor2() {
		return textColor2;
	}

	public float getTextSize2() {
		return textSize2;
	}

	public Typeface getTypeface3() {
		return typeface3;
	}

	public int getTextColor3() {
		return textColor3;
	}

	public float getTextSize3() {
		return textSize3;
	}

	public float getCharacterSpacing1() {
		return characterSpacing1;
	}

	public float getCharacterSpacing2() {
		return characterSpacing2;
	}

	public float getCharacterSpacing3() {
		return characterSpacing3;
	}

}
