package se.tink.core.models.transaction;

import java.util.List;
import se.tink.core.models.misc.ExactNumber;

public class SuggestTransactionsResponse {

	private ExactNumber categorizationImprovement;
	private ExactNumber categorizationLevel;
	private List<TransactionCluster> clusters;

	public ExactNumber getCategorizationImprovement() {
		return categorizationImprovement;
	}

	public void setCategorizationImprovement(ExactNumber categorizationImprovement) {
		this.categorizationImprovement = categorizationImprovement;
	}

	public ExactNumber getCategorizationLevel() {
		return categorizationLevel;
	}

	public void setCategorizationLevel(ExactNumber categorizationLevel) {
		this.categorizationLevel = categorizationLevel;
	}

	public List<TransactionCluster> getClusters() {
		return clusters;
	}

	public void setClusters(List<TransactionCluster> clusters) {
		this.clusters = clusters;
	}

}
