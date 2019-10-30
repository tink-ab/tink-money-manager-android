package se.tink.core.models.identity;

import java.util.List;
import org.joda.time.DateTime;
import se.tink.core.models.transaction.Transaction;

public class IdentityEvent {

	private String id;
	private DateTime date;
	private String description;
	private boolean seen;
	private String question;
	private List<IdentityEventAnswer> potentialAnswers;
	private IdentityAnswerKey answer;
	private IdentityEventDocumentation documentation;
	private List<Transaction> transactions;


	public IdentityEvent() {
		// Required constructor for model mapper
	}

	private IdentityEvent(
		String id,
		DateTime date,
		String description,
		boolean seen,
		String question,
		List<IdentityEventAnswer> potentialAnswers,
		IdentityAnswerKey answer,
		IdentityEventDocumentation documentation,
		List<Transaction> transactions) {
		this.id = id;
		this.date = date;
		this.description = description;
		this.seen = seen;
		this.question = question;
		this.potentialAnswers = potentialAnswers;
		this.answer = answer;
		this.documentation = documentation;
		this.transactions = transactions;
	}

	public List<Transaction> getTransactions() {
		return transactions;
	}

	public String getId() {
		return id;
	}

	public DateTime getDate() {
		return date;
	}

	public String getDescription() {
		return description;
	}

	public boolean isSeen() {
		return seen;
	}

	public String getQuestion() {
		return question;
	}

	public List<IdentityEventAnswer> getPotentialAnswers() {
		return potentialAnswers;
	}

	public IdentityAnswerKey getAnswer() {
		return answer;
	}

	public IdentityEventDocumentation getDocumentation() {
		return documentation;
	}

	public static Builder builder() {
		return new Builder();
	}

	public static class Builder {

		private String id;
		private DateTime date;
		private String description;
		private boolean seen;
		private String question;
		private List<IdentityEventAnswer> potentialAnswers;
		private IdentityAnswerKey answer;
		private IdentityEventDocumentation documentation;
		private List<Transaction> transactions;

		private Builder() {
		}

		public Builder withId(String id) {
			this.id = id;
			return this;
		}

		public Builder withDate(DateTime date) {
			this.date = date;
			return this;
		}

		public Builder withDescription(String description) {
			this.description = description;
			return this;
		}

		public Builder withSeen(boolean seen) {
			this.seen = seen;
			return this;
		}

		public Builder withQuestion(String question) {
			this.question = question;
			return this;
		}

		public Builder withPotentialAnswers(List<IdentityEventAnswer> potentialAnswers) {
			this.potentialAnswers = potentialAnswers;
			return this;
		}

		public Builder withAnswer(IdentityAnswerKey answer) {
			this.answer = answer;
			return this;
		}

		public Builder withDocumentation(IdentityEventDocumentation documentation) {
			this.documentation = documentation;
			return this;
		}

		public Builder withTransactions(List<Transaction> transactions) {
			this.transactions = transactions;
			return this;
		}

		public IdentityEvent build() {
			return new IdentityEvent(
				id,
				date,
				description,
				seen,
				question,
				potentialAnswers,
				answer,
				documentation,
				transactions);
		}
	}
}
