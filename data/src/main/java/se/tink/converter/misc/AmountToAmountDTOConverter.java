package se.tink.converter.misc;

import se.tink.converter.ModelConverter;
import com.tink.model.misc.Amount;
import se.tink.grpc.v1.models.CurrencyDenominatedAmount;
import se.tink.grpc.v1.models.ExactNumber;
import se.tink.modelConverter.AbstractConverter;

public class AmountToAmountDTOConverter extends
	AbstractConverter<Amount, CurrencyDenominatedAmount> {

	private ModelConverter modelConverter;

	public AmountToAmountDTOConverter(ModelConverter modelConverter) {

		this.modelConverter = modelConverter;
	}

	@Override
	public CurrencyDenominatedAmount convert(Amount source) {
		CurrencyDenominatedAmount.Builder amount = CurrencyDenominatedAmount.newBuilder();
		amount.setValue(modelConverter.map(source.getValue(), ExactNumber.class));
		amount.setCurrencyCode(source.getCurrencyCode());
		return amount.build();
	}

	@Override
	public Class<Amount> getSourceClass() {
		return Amount.class;
	}

	@Override
	public Class<CurrencyDenominatedAmount> getDestinationClass() {
		return CurrencyDenominatedAmount.class;
	}
}
