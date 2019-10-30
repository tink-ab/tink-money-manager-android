package se.tink.converter.identity;

import androidx.annotation.NonNull;
import java.util.ArrayList;
import java.util.List;
import org.joda.time.DateTime;
import se.tink.converter.ModelConverterImpl;
import se.tink.core.models.identity.IdentityAnswerKey;
import se.tink.core.models.identity.IdentityEvent;
import se.tink.core.models.identity.IdentityEventAnswer;
import se.tink.core.models.identity.IdentityEventDocumentation;
import se.tink.modelConverter.AbstractConverter;
import se.tink.core.models.transaction.Transaction;

public class IdentityEventConverter extends
	AbstractConverter<se.tink.grpc.v1.models.IdentityEvent, IdentityEvent> {

	private final ModelConverterImpl modelConverter;

	public IdentityEventConverter(ModelConverterImpl modelConverter) {
		this.modelConverter = modelConverter;
	}

	@Override
	public IdentityEvent convert(se.tink.grpc.v1.models.IdentityEvent source) {
		return IdentityEvent.builder()
			.withId(source.getId())
			.withDate(modelConverter.map(source.getDate(), DateTime.class))
			.withDescription(source.getDescription())
			.withPotentialAnswers(
				modelConverter.map(source.getPotentialAnswersList(), IdentityEventAnswer.class))
			.withDescription(source.getDescription())
			.withSeen(source.getSeen())
			.withQuestion(source.getQuestion())
			.withAnswer(
				modelConverter.map(source.getAnswer().getAnswer(),
					IdentityAnswerKey.class))
			.withDocumentation(
				modelConverter.map(source.getDocumentation(),
					IdentityEventDocumentation.class))
			.withTransactions(getTransactions(source))
			.build();
	}

	@NonNull
	private List<Transaction> getTransactions(se.tink.grpc.v1.models.IdentityEvent identityEvent) {
		List<Transaction> transactions = new ArrayList<>();
		if (identityEvent.getTransactionsList() != null) {
			List<Transaction> toAdd = modelConverter.map(
				identityEvent.getTransactionsList(),
				Transaction.class);

			transactions.addAll(toAdd);
		}

		return transactions;
	}

	@Override
	public Class<se.tink.grpc.v1.models.IdentityEvent> getSourceClass() {
		return se.tink.grpc.v1.models.IdentityEvent.class;
	}

	@Override
	public Class<IdentityEvent> getDestinationClass() {
		return IdentityEvent.class;
	}
}
