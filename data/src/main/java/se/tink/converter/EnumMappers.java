package se.tink.converter;

import com.google.common.collect.BiMap;
import com.google.common.collect.ImmutableBiMap;
import se.tink.core.models.authentication.AuthenticationStatus;
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

	public static final BiMap<PeriodDateBreakType, PeriodSetting.PeriodDateBreakType> PERIOD_SETTINGS_MAP =
		ImmutableBiMap.<PeriodDateBreakType, PeriodSetting.PeriodDateBreakType>builder()
			.put(PeriodDateBreakType.PERIOD_DATE_BREAK_TYPE_MONTHLY,
				PeriodSetting.PeriodDateBreakType.MONTHLY)
			.put(PeriodDateBreakType.PERIOD_DATE_BREAK_TYPE_MONTHLY_ADJUSTED,
				PeriodSetting.PeriodDateBreakType.MONTHLY_ADJUSTED)
			.put(PeriodDateBreakType.PERIOD_DATE_BREAK_TYPE_UNKNOWN,
				PeriodSetting.PeriodDateBreakType.UNKNOWN)
			.build();

}
