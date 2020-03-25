package se.tink.repository;

//public class TinkNetworkError extends RuntimeException {
//
//	public enum ErrorCode {
//		ABORTED("authentication.mobile_bank_id.aborted", "streaming.aborted"),
//    	ALREADY_EXIST("credentials.already_exist", "transfers.transfer_destinations.already_exists"),
//		DEPRECATED_CLIENT("authentication.deprecated_client"),
//		EXIRED("authentication.authentication_token.expired",
//			"authentication.mobile_bank_id.expired"),
//		GATEWAY_UNVAVILABLE("sms_otp.gateway_unavailable"),
//		INTERNAL_ERROR("authentication.internal_error", "identity.internal_error",
//			"streaming.internal_error"),
//		INVALID("authentication.authentication_token.invalid_status"),
//		INVALID_CHECKSUM("consents.invalid_checksum"),
//		INVALID_EMAIL("validation.invalid_email"),
//		INVALID_LOCALE("validation.invalid_locale"),
//		INVALID_PHONE_NUMBER("validation.invalid_phone_number"),
//		INVALID_PIN_6("validation.invalid_pin_6"),
//		INVALID_REQUEST("credentials.invalid_request"),
//		INVALID_STATUS("sms_otp.invalid_status"),
//		NOT_FOUND("authentication.authentication_token.not_found",
//			"authentication.mobile_bank_id.not_found", "sms_otp.not_found", "account.not_found",
//			"user.not_found", "consents.not_found", "identity.not_found"),
//		PERMISSION_DENIED("transfer.permission_denied", "credentials.permission_denied"),
//		PHONE_NUMBER_BLOCKED("sms_otp.phone_number_blocked"),
//		TIMED_OUT("authentication.mobile_bank_id.timed_out"),
//		UNAUTHENTICATED("authentication.unauthenticated"),
//		UNAUTHORIZED_DEVICE("authentication.unauthorized_device"),
//		UNAVAILABLE("statistic.unavailable", "activities.unavailable", "transactions.unavailable"),
//		UNKNOWN_CATEGORY("transactions.unknown_category"),
//		UNKNOWN_ERROR("credentials.unknown_error"),
//		USER_ALREADY_REGISTERED("authentication.user_already_registered"),
//		UNHANDLED_ERROR;
//
//		private final Set<String> codes;
//
//		ErrorCode(String... codes) {
//			this.codes = new HashSet<>();
//			this.codes.addAll(Arrays.asList(codes));
//		}
//
//		public static ErrorCode fromString(String string) {
//			for (ErrorCode code : ErrorCode.values()) {
//				if (code.codes.contains(string)) {
//					return code;
//				}
//			}
//
//			return UNHANDLED_ERROR;
//		}
//	}
//
//	private static final Metadata.Key<se.tink.grpc.v1.models.ErrorCode> ERROR_CODE_KEY = getKeyFor(
//		"errorcode-bin",
//		se.tink.grpc.v1.models.ErrorCode.getDefaultInstance());
//	private static final Metadata.Key<LocalizedMessage> MESSAGE_KEY = getKeyFor(
//		"google.rpc.localizedmessage-bin", LocalizedMessage.getDefaultInstance());
//
//	private final Status status;
//	private final Metadata trailers;
//
//	public TinkNetworkError(Throwable error) {
//		super(error);
//
//		StatusRuntimeException exception = (StatusRuntimeException) error;
//		this.trailers = exception.getTrailers();
//		this.status = exception.getStatus();
//	}
//
//	public Status.Code getStatusCode() {
//		return this.status.getCode();
//	}
//
//	public ErrorCode getErrorCode() {
//		se.tink.grpc.v1.models.ErrorCode errorCode = trailers.get(ERROR_CODE_KEY);
//
//		return errorCode != null ? ErrorCode.fromString(errorCode.getCode())
//			: ErrorCode.UNHANDLED_ERROR;
//	}
//
//	public @Nullable String getBackendMessage() {
//		LocalizedMessage message = trailers.get(MESSAGE_KEY);
//		return message != null ? message.getMessage() : null;
//	}
//
//	@Override
//	public String getMessage() {
//		LocalizedMessage message = trailers.get(MESSAGE_KEY);
//		return message != null ? message.getMessage() : super.getMessage();
//	}
//
//	private static <T extends MessageLite> Metadata.Key<T> getKeyFor(String name, T instance) {
//		return Metadata.Key.of(
//			name, ProtoLiteUtils.metadataMarshaller(instance)
//		);
//	}
//}

//TODO: Core setup - do we need this? If yes, should be in core?
public class TinkNetworkError extends RuntimeException {

	public TinkNetworkError(Throwable t) {
		super(t);
	}

	public enum ErrorCode {
		UNAUTHENTICATED, UNHANDLED_ERROR
	}

	public class StatusCode {
		public int value() {
			return 0;
		}
	}

	public StatusCode getStatusCode() {
		return new StatusCode();
	}

	public ErrorCode getErrorCode() {
		return ErrorCode.UNAUTHENTICATED;
	}

	public String getBackendMessage() {
		return null;
	}
}
