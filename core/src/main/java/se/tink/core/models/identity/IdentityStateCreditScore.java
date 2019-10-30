package se.tink.core.models.identity;

public class IdentityStateCreditScore {

	private int maxScore;
	private int score;
	private String text;

	public void setMaxScore(int maxScore) {
		this.maxScore = maxScore;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public void setText(String text) {
		this.text = text;
	}

	public int getMaxScore() {
		return maxScore;
	}

	public int getScore() {
		return score;
	}

	public String getText() {
		return text;
	}
}
