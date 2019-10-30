package se.tink.converter.settings;

import se.tink.converter.ModelConverter;
import se.tink.core.models.user.NotificationSettings;
import se.tink.grpc.v1.models.NotificationType;
import se.tink.grpc.v1.models.NotificationType.Builder;
import se.tink.grpc.v1.rpc.UpdateNotificationSettingsRequest;
import se.tink.modelConverter.AbstractConverter;

public class NotificationSettingsUpdateRequestConverter extends
	AbstractConverter<NotificationSettings, UpdateNotificationSettingsRequest> {

	public NotificationSettingsUpdateRequestConverter(ModelConverter modelConverter) {
	}

	@Override
	public UpdateNotificationSettingsRequest convert(NotificationSettings source) {
		UpdateNotificationSettingsRequest.Builder destination = UpdateNotificationSettingsRequest
			.newBuilder();

		Builder income = NotificationType.newBuilder();
		income.setKey("income");
		income.setEnabled(source.isIncome());
		destination.addNotificationTypes(income.build());

		Builder transaction = NotificationType.newBuilder();
		transaction.setKey("transaction");
		transaction.setEnabled(source.isTransaction());
		destination.addNotificationTypes(transaction.build());

		Builder leftToSpend = NotificationType.newBuilder();
		leftToSpend.setKey("left_to_spend");
		leftToSpend.setEnabled(source.isLeftToSpend());
		destination.addNotificationTypes(leftToSpend.build());

		Builder eInvoices = NotificationType.newBuilder();
		eInvoices.setKey("e_invoices");
		eInvoices.setEnabled(source.isEinvoices());
		destination.addNotificationTypes(eInvoices.build());

		Builder balance = NotificationType.newBuilder();
		balance.setKey("balance");
		balance.setEnabled(source.isBalance());
		destination.addNotificationTypes(balance.build());

		Builder doubleCharge = NotificationType.newBuilder();
		doubleCharge.setKey("double_charge");
		doubleCharge.setEnabled(source.isDoubleCharge());
		destination.addNotificationTypes(doubleCharge.build());

		Builder largeExpense = NotificationType.newBuilder();
		largeExpense.setKey("large_expense");
		largeExpense.setEnabled(source.isLargeExpense());
		destination.addNotificationTypes(largeExpense.build());

		Builder unusualCategory = NotificationType.newBuilder();
		unusualCategory.setKey("unusual_category");
		unusualCategory.setEnabled(source.isUnusualCategory());
		destination.addNotificationTypes(unusualCategory.build());

		Builder unusualAccount = NotificationType.newBuilder();
		unusualAccount.setKey("unusual_account");
		unusualAccount.setEnabled(source.isUnusualAccount());
		destination.addNotificationTypes(unusualAccount.build());

		Builder fraud = NotificationType.newBuilder();
		fraud.setKey("fraud");
		fraud.setEnabled(source.isFraud());
		destination.addNotificationTypes(fraud.build());

		Builder summaryWeekly = NotificationType.newBuilder();
		summaryWeekly.setKey("summary_weekly");
		summaryWeekly.setEnabled(source.isSummaryWeekly());
		destination.addNotificationTypes(summaryWeekly.build());

		Builder summaryMonthly = NotificationType.newBuilder();
		summaryMonthly.setKey("summary_monthly");
		summaryMonthly.setEnabled(source.isSummaryMonthly());
		destination.addNotificationTypes(summaryMonthly.build());

		Builder budget = NotificationType.newBuilder();
		budget.setKey("budget");
		budget.setEnabled(source.isSummaryMonthly());
		destination.addNotificationTypes(budget.build());

		return destination.build();
	}

	@Override
	public Class<NotificationSettings> getSourceClass() {
		return NotificationSettings.class;
	}

	@Override
	public Class<UpdateNotificationSettingsRequest> getDestinationClass() {
		return UpdateNotificationSettingsRequest.class;
	}
}
