package se.tink.converter.settings;

import java.util.Objects;
import se.tink.converter.ModelConverterImpl;
import se.tink.core.models.user.NotificationSettings;
import se.tink.grpc.v1.models.NotificationGroup;
import se.tink.grpc.v1.models.NotificationType;
import se.tink.modelConverter.AbstractConverter;

public class NotificationSettingsConverter extends
	AbstractConverter<se.tink.grpc.v1.models.NotificationSettings, NotificationSettings> {

	private final ModelConverterImpl modelConverter;

	public NotificationSettingsConverter(ModelConverterImpl modelConverter) {
		this.modelConverter = modelConverter;
	}

	@Override
	public NotificationSettings convert(se.tink.grpc.v1.models.NotificationSettings source) {
		NotificationSettings destination = new NotificationSettings();

		for (NotificationGroup group : source.getGroupsList()) {

			for (NotificationType type : group.getNotificationTypesList()) {

				if (Objects.equals(type.getKey(), "income")) {
					destination.setIncome(type.getEnabled());

				} else if (Objects.equals(type.getKey(), "transaction")) {
					destination.setTransaction(type.getEnabled());

				} else if (Objects.equals(type.getKey(), "left_to_spend")) {
					destination.setLeftToSpend(type.getEnabled());

				} else if (Objects.equals(type.getKey(), "e_invoices")) {
					destination.setEinvoices(type.getEnabled());

				} else if (Objects.equals(type.getKey(), "balance")) {
					destination.setBalance(type.getEnabled());

				} else if (Objects.equals(type.getKey(), "double_charge")) {
					destination.setDoubleCharge(type.getEnabled());

				} else if (Objects.equals(type.getKey(), "large_expense")) {
					destination.setLargeExpense(type.getEnabled());

				} else if (Objects.equals(type.getKey(), "unusual_category")) {
					destination.setUnusualCategory(type.getEnabled());

				} else if (Objects.equals(type.getKey(), "unusual_account")) {
					destination.setUnusualAccount(type.getEnabled());

				} else if (Objects.equals(type.getKey(), "fraud")) {
					destination.setFraud(type.getEnabled());

				} else if (Objects.equals(type.getKey(), "summary_weekly")) {
					destination.setSummaryWeekly(type.getEnabled());

				} else if (Objects.equals(type.getKey(), "summary_monthly")) {
					destination.setSummaryMonthly(type.getEnabled());

				} else if (Objects.equals(type.getKey(), "budget")) {
					destination.setBudget(type.getEnabled());
				}
			}
		}

		return destination;
	}

	@Override
	public Class<se.tink.grpc.v1.models.NotificationSettings> getSourceClass() {
		return se.tink.grpc.v1.models.NotificationSettings.class;
	}

	@Override
	public Class<NotificationSettings> getDestinationClass() {
		return NotificationSettings.class;
	}

}
