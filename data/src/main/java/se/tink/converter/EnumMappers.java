package se.tink.converter;

import com.google.common.collect.BiMap;
import com.google.common.collect.ImmutableBiMap;
import se.tink.core.models.authentication.AuthenticationStatus;
import se.tink.core.models.credential.Credential;
import se.tink.core.models.device.AuthenticationMethod;
import se.tink.core.models.transfer.SignableOperation;
import se.tink.core.models.transfer.SignableOperation.Status;
import se.tink.core.models.transfer.TransferDestination;
import se.tink.core.models.transfer.TransferDestination.Type;
import se.tink.core.models.user.BankIdAuthenticationStatus;
import se.tink.core.models.user.PeriodSetting;
import se.tink.grpc.v1.models.PeriodDateBreakType;

public class EnumMappers {

	public static final BiMap<se.tink.grpc.v1.models.BankIdAuthenticationStatus, BankIdAuthenticationStatus> GRPC_TO_MODEL_BANK_ID_AUTHENTICATION_STATUS_MAP =
		ImmutableBiMap.<se.tink.grpc.v1.models.BankIdAuthenticationStatus, BankIdAuthenticationStatus>builder()
			.put(
				se.tink.grpc.v1.models.BankIdAuthenticationStatus.BANK_ID_AUTHENTICATION_STATUS_UNKNOWN,
				BankIdAuthenticationStatus.BANK_ID_AUTHENTICATION_STATUS_UNKNOWN)
			.put(
				se.tink.grpc.v1.models.BankIdAuthenticationStatus.BANK_ID_AUTHENTICATION_STATUS_AUTHENTICATED,
				BankIdAuthenticationStatus.BANK_ID_AUTHENTICATION_STATUS_AUTHENTICATED)
			.put(
				se.tink.grpc.v1.models.BankIdAuthenticationStatus.BANK_ID_AUTHENTICATION_STATUS_AUTHENTICATION_ERROR,
				BankIdAuthenticationStatus.BANK_ID_AUTHENTICATION_STATUS_AUTHENTICATION_ERROR)
			.put(
				se.tink.grpc.v1.models.BankIdAuthenticationStatus.BANK_ID_AUTHENTICATION_STATUS_AWAITING_BANKID_AUTHENTICATION,
				BankIdAuthenticationStatus.BANK_ID_AUTHENTICATION_STATUS_AWAITING_BANKID_AUTHENTICATION)
			.put(
				se.tink.grpc.v1.models.BankIdAuthenticationStatus.BANK_ID_AUTHENTICATION_STATUS_NO_USER,
				BankIdAuthenticationStatus.BANK_ID_AUTHENTICATION_STATUS_NO_USER)
			.build();

	public static final BiMap<se.tink.grpc.v1.models.AuthenticationStatus, AuthenticationStatus> GRPC_TO_MODEL_AUTHENTICATION_STATUS_MAP =
		ImmutableBiMap.<se.tink.grpc.v1.models.AuthenticationStatus, AuthenticationStatus>builder()
			.put(se.tink.grpc.v1.models.AuthenticationStatus.AUTHENTICATION_STATUS_UNKNOWN,
				AuthenticationStatus.AUTHENTICATION_STATUS_UNKNOWN)
			.put(se.tink.grpc.v1.models.AuthenticationStatus.AUTHENTICATION_STATUS_AUTHENTICATED,
				AuthenticationStatus.AUTHENTICATION_STATUS_AUTHENTICATED)
			.put(
				se.tink.grpc.v1.models.AuthenticationStatus.AUTHENTICATION_STATUS_AUTHENTICATION_ERROR,
				AuthenticationStatus.AUTHENTICATION_STATUS_AUTHENTICATION_ERROR)
			.put(se.tink.grpc.v1.models.AuthenticationStatus.AUTHENTICATION_STATUS_NO_USER,
				AuthenticationStatus.AUTHENTICATION_STATUS_NO_USER)
			.put(se.tink.grpc.v1.models.AuthenticationStatus.AUTHENTICATION_STATUS_USER_BLOCKED,
				AuthenticationStatus.AUTHENTICATION_STATUS_USER_BLOCKED)
			.put(se.tink.grpc.v1.models.AuthenticationStatus.AUTHENTICATION_STATUS_AUTHENTICATED_UNAUTHORIZED_DEVICE,
				AuthenticationStatus.AUTHENTICATION_STATUS_AUTHENTICATED_UNAUTHORIZED_DEVICE)
			.build();

	public static final BiMap<se.tink.grpc.v1.models.Credential.Type, Credential.Type> GRPC_TO_MODEL_CREDENTIAL_TYPE_MAP =
		ImmutableBiMap.<se.tink.grpc.v1.models.Credential.Type, Credential.Type>builder()
			.put(se.tink.grpc.v1.models.Credential.Type.TYPE_THIRD_PARTY_AUTHENTICATION, Credential.Type.TYPE_THIRD_PARTY_AUTHENTICATION)
			.put(se.tink.grpc.v1.models.Credential.Type.TYPE_UNKNOWN, Credential.Type.TYPE_UNKNOWN)
			.put(se.tink.grpc.v1.models.Credential.Type.TYPE_PASSWORD,
				Credential.Type.TYPE_PASSWORD)
			.put(se.tink.grpc.v1.models.Credential.Type.TYPE_MOBILE_BANKID,
				Credential.Type.TYPE_MOBILE_BANKID)
			.put(se.tink.grpc.v1.models.Credential.Type.TYPE_KEYFOB, Credential.Type.TYPE_KEYFOB)
			.put(se.tink.grpc.v1.models.Credential.Type.TYPE_FRAUD, Credential.Type.TYPE_FRAUD)
			.build();

	public static final BiMap<PeriodDateBreakType, PeriodSetting.PeriodDateBreakType> PERIOD_SETTINGS_MAP =
		ImmutableBiMap.<PeriodDateBreakType, PeriodSetting.PeriodDateBreakType>builder()
			.put(PeriodDateBreakType.PERIOD_DATE_BREAK_TYPE_MONTHLY,
				PeriodSetting.PeriodDateBreakType.MONTHLY)
			.put(PeriodDateBreakType.PERIOD_DATE_BREAK_TYPE_MONTHLY_ADJUSTED,
				PeriodSetting.PeriodDateBreakType.MONTHLY_ADJUSTED)
			.put(PeriodDateBreakType.PERIOD_DATE_BREAK_TYPE_UNKNOWN,
				PeriodSetting.PeriodDateBreakType.UNKNOWN)
			.build();

	public static final BiMap<se.tink.grpc.v1.models.Account.Type, TransferDestination.Type> GRPC_TO_MODEL_TRANSFER_DESTINATION_TYPE =
		ImmutableBiMap.<se.tink.grpc.v1.models.Account.Type, TransferDestination.Type>builder()
			.put(se.tink.grpc.v1.models.Account.Type.TYPE_CHECKING, Type.TYPE_CHECKING)
			.put(se.tink.grpc.v1.models.Account.Type.TYPE_CREDIT_CARD, Type.TYPE_CREDIT_CARD)
			.put(se.tink.grpc.v1.models.Account.Type.TYPE_DUMMY, Type.TYPE_DUMMY)
			.put(se.tink.grpc.v1.models.Account.Type.TYPE_EXTERNAL, Type.TYPE_EXTERNAL)
			.put(se.tink.grpc.v1.models.Account.Type.TYPE_INVESTMENT, Type.TYPE_INVESTMENT)
			.put(se.tink.grpc.v1.models.Account.Type.TYPE_LOAN, Type.TYPE_LOAN)
			.put(se.tink.grpc.v1.models.Account.Type.TYPE_MORTGAGE, Type.TYPE_MORTGAGE)
			.put(se.tink.grpc.v1.models.Account.Type.TYPE_OTHER, Type.TYPE_OTHER)
			.put(se.tink.grpc.v1.models.Account.Type.TYPE_PENSION, Type.TYPE_PENSION)
			.put(se.tink.grpc.v1.models.Account.Type.TYPE_SAVINGS, Type.TYPE_SAVINGS)
			.put(se.tink.grpc.v1.models.Account.Type.TYPE_UNKNOWN, Type.TYPE_UNKNOWN)
			.build();

	public static final BiMap<se.tink.grpc.v1.models.SignableOperation.Status, SignableOperation.Status> GRPC_TO_MODEL_SIGNABLE_OPERATION_STATUS =
		ImmutableBiMap.<se.tink.grpc.v1.models.SignableOperation.Status, SignableOperation.Status>builder()
		.put(se.tink.grpc.v1.models.SignableOperation.Status.STATUS_UNKNOWN, Status.STATUS_UNKNOWN)
		.put(se.tink.grpc.v1.models.SignableOperation.Status.STATUS_CREATED, Status.STATUS_CREATED)
		.put(se.tink.grpc.v1.models.SignableOperation.Status.STATUS_EXECUTING, Status.STATUS_EXECUTING)
		.put(se.tink.grpc.v1.models.SignableOperation.Status.STATUS_AWAITING_CREDENTIALS, Status.STATUS_AWAITING_CREDENTIALS)
		.put(se.tink.grpc.v1.models.SignableOperation.Status.STATUS_CANCELLED, Status.STATUS_CANCELLED)
		.put(se.tink.grpc.v1.models.SignableOperation.Status.STATUS_FAILED, Status.STATUS_FAILED)
		.put(se.tink.grpc.v1.models.SignableOperation.Status.STATUS_EXECUTED, Status.STATUS_EXECUTED)
		.build();

	public static final BiMap<se.tink.grpc.v1.models.SignableOperation.Type, SignableOperation.Type> GRPC_TO_MODEL_SIGNABLE_OPERATION_TYPE=
		ImmutableBiMap.<se.tink.grpc.v1.models.SignableOperation.Type, SignableOperation.Type>builder()
			.put(se.tink.grpc.v1.models.SignableOperation.Type.TYPE_UNKNOWN, SignableOperation.Type.TYPE_UNKNOWN)
			.put(se.tink.grpc.v1.models.SignableOperation.Type.TYPE_TRANSFER, SignableOperation.Type.TYPE_TRANSFER)
			.put(se.tink.grpc.v1.models.SignableOperation.Type.TYPE_ACCOUNT_CREATE, SignableOperation.Type.TYPE_ACCOUNT_CREATE)
			.build();

	public static final BiMap<se.tink.grpc.v1.models.AuthenticationMethod, AuthenticationMethod> AUTHENTICATION_METHOD_MAP =
		ImmutableBiMap.<se.tink.grpc.v1.models.AuthenticationMethod, AuthenticationMethod>builder()
			.put(se.tink.grpc.v1.models.AuthenticationMethod.AUTHENTICATION_METHOD_UNKNOWN, AuthenticationMethod.AUTHENTICATION_METHOD_UNKNOWN)
			.put(se.tink.grpc.v1.models.AuthenticationMethod.AUTHENTICATION_METHOD_BANKID, AuthenticationMethod.AUTHENTICATION_METHOD_BANKID)
			.put(se.tink.grpc.v1.models.AuthenticationMethod.AUTHENTICATION_METHOD_EMAIL_AND_PASSWORD, AuthenticationMethod.AUTHENTICATION_METHOD_EMAIL_AND_PASSWORD)
			.put(se.tink.grpc.v1.models.AuthenticationMethod.AUTHENTICATION_METHOD_SMS_OTP_AND_PIN6, AuthenticationMethod.AUTHENTICATION_METHOD_SMS_OTP_AND_PIN6)
			.put(se.tink.grpc.v1.models.AuthenticationMethod.AUTHENTICATION_METHOD_PHONE_NUMBER_AND_PIN6, AuthenticationMethod.AUTHENTICATION_METHOD_PHONE_NUMBER_AND_PIN6)
			.put(se.tink.grpc.v1.models.AuthenticationMethod.AUTHENTICATION_METHOD_ABN_AMRO_PIN5, AuthenticationMethod.AUTHENTICATION_METHOD_ABN_AMRO_PIN5)
			.build();
}
