package se.tink.core.models.transaction;

import java.util.List;
import se.tink.core.models.misc.ExactNumber;

public class TransactionCluster {

	private String description;
	private List<Transaction> transactions;
	private ExactNumber score;
	private ExactNumber categorizationImprovement;

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<Transaction> getTransactions() {
		return transactions;
	}

	public void setTransactions(List<Transaction> transactions) {
		this.transactions = transactions;
	}

	public ExactNumber getScore() {
		return score;
	}

	public void setScore(ExactNumber score) {
		this.score = score;
	}

	public ExactNumber getCategorizationImprovement() {
		return categorizationImprovement;
	}

	public void setCategorizationImprovement(ExactNumber categorizationImprovement) {
		this.categorizationImprovement = categorizationImprovement;
	}

}
