package se.tink.repository.service;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.protobuf.StringValue;
import se.tink.converter.ModelConverter;
import se.tink.core.models.user.DeleteAccountReason;
import se.tink.core.models.user.EmailAndPasswordAuthenticationResponse;
import se.tink.core.models.user.GetProfileResponse;
import se.tink.core.models.user.InitiateBankIdAuthenticationResponse;
import se.tink.core.models.user.LogoutResponse;
import se.tink.core.models.user.RegisterResponse;
import se.tink.core.models.user.UpdateEmailResponse;
import se.tink.grpc.v1.models.RateAppStatus;
import se.tink.grpc.v1.rpc.CollectBankIdAuthenticationRequest;
import se.tink.grpc.v1.rpc.CollectBankIdAuthenticationResponse;
import se.tink.grpc.v1.rpc.DeleteUserRequest;
import se.tink.grpc.v1.rpc.DeleteUserResponse;
import se.tink.grpc.v1.rpc.EmailAndPasswordAuthenticationRequest;
import se.tink.grpc.v1.rpc.ForgotPasswordRequest;
import se.tink.grpc.v1.rpc.ForgotPasswordResponse;
import se.tink.grpc.v1.rpc.GetProfileRequest;
import se.tink.grpc.v1.rpc.InitiateBankIdAuthenticationRequest;
import se.tink.grpc.v1.rpc.InitiateBankIdAuthenticationRequest.Builder;
import se.tink.grpc.v1.rpc.LoginRequest;
import se.tink.grpc.v1.rpc.LoginResponse;
import se.tink.grpc.v1.rpc.LogoutRequest;
import se.tink.grpc.v1.rpc.RateAppRequest;
import se.tink.grpc.v1.rpc.RateAppResponse;
import se.tink.grpc.v1.rpc.RegisterRequest;
import se.tink.grpc.v1.rpc.UpdateEmailRequest;
import se.tink.grpc.v1.rpc.UpdatePasswordRequest;
import se.tink.grpc.v1.rpc.UpdatePasswordResponse;
import se.tink.grpc.v1.services.AuthenticationServiceGrpc;
import se.tink.grpc.v1.services.AuthenticationServiceGrpc.AuthenticationServiceStub;
import se.tink.grpc.v1.services.EmailAndPasswordAuthenticationServiceGrpc.EmailAndPasswordAuthenticationServiceStub;
import se.tink.grpc.v1.services.MobileBankIdAuthenticationServiceGrpc;
import se.tink.grpc.v1.services.MobileBankIdAuthenticationServiceGrpc.MobileBankIdAuthenticationServiceStub;
import se.tink.grpc.v1.services.UserServiceGrpc.UserServiceStub;
import se.tink.repository.MutationHandler;
import se.tink.repository.SimpleStreamObserver;

public class UserServiceImpl implements UserService {


	private final AuthenticationServiceGrpc.AuthenticationServiceStub service;
	private final MobileBankIdAuthenticationServiceGrpc.MobileBankIdAuthenticationServiceStub bankIdService;
	private final EmailAndPasswordAuthenticationServiceStub emailAndPasswordService;
	private final ModelConverter converter;
	private final UserServiceStub userService;

	public UserServiceImpl(AuthenticationServiceStub service,
		MobileBankIdAuthenticationServiceStub bankIdService,
		EmailAndPasswordAuthenticationServiceStub emailAndPasswordService,
		UserServiceStub stub, ModelConverter converter) {
		this.service = service;
		this.bankIdService = bankIdService;
		this.emailAndPasswordService = emailAndPasswordService;
		this.converter = converter;
		this.userService = stub;
	}

	public void login(String authenticationToken,
		final MutationHandler<se.tink.core.models.user.LoginResponse> handler) {
		LoginRequest request = LoginRequest.newBuilder().setAuthenticationToken(authenticationToken)
			.build();

		service.login(request, new SimpleStreamObserver<LoginResponse>(handler) {
			@Override
			public void onNext(LoginResponse value) {
				handler.onNext(converter.map(value, se.tink.core.models.user.LoginResponse.class));
			}
		});
	}

	@Override
	public void collectBankIdAuthentication(String authenticationToken,
		final MutationHandler<se.tink.core.models.user.CollectBankIdAuthenticationResponse> handler) {
		CollectBankIdAuthenticationRequest request = CollectBankIdAuthenticationRequest.newBuilder()
			.setAuthenticationToken(authenticationToken).build();

		bankIdService.collectBankIdAuthentication(request,
			new SimpleStreamObserver<CollectBankIdAuthenticationResponse>(handler) {
				@Override
				public void onNext(CollectBankIdAuthenticationResponse value) {
					handler.onNext(converter
						.map(value,
							se.tink.core.models.user.CollectBankIdAuthenticationResponse.class));
				}
			});
	}


	@Override
	public void emailAndPasswordAuthentication(String email, String password, String market,
		final MutationHandler<EmailAndPasswordAuthenticationResponse> handler) {
		EmailAndPasswordAuthenticationRequest request = EmailAndPasswordAuthenticationRequest
			.newBuilder().setEmail(email).setPassword(password).setMarketCode(market).build();
		emailAndPasswordService.emailAndPasswordAuthentication(request,
			new SimpleStreamObserver<se.tink.grpc.v1.rpc.EmailAndPasswordAuthenticationResponse>(
				handler) {
				@Override
				public void onNext(
					se.tink.grpc.v1.rpc.EmailAndPasswordAuthenticationResponse value) {
					handler
						.onNext(converter.map(value, EmailAndPasswordAuthenticationResponse.class));
				}
			});
	}

	@Override
	public void changeEmail(String email, final MutationHandler<UpdateEmailResponse> handler) {
		UpdateEmailRequest request = UpdateEmailRequest.newBuilder()
			.setEmail(StringValue.newBuilder().setValue(email).build())
			.build();
		emailAndPasswordService.updateEmail(request,
			new SimpleStreamObserver<se.tink.grpc.v1.rpc.UpdateEmailResponse>(handler) {
				@Override
				public void onNext(se.tink.grpc.v1.rpc.UpdateEmailResponse value) {
					handler.onNext(converter.map(value, UpdateEmailResponse.class));
				}
			});
	}

	@Override
	public void changePassword(String oldPassword, String newPassword,
		final MutationHandler<Void> handler) {
		final UpdatePasswordRequest request = UpdatePasswordRequest.newBuilder()
			.setOldPassword(oldPassword)
			.setNewPassword(newPassword)
			.build();

		emailAndPasswordService.updatePassword(request,
			new SimpleStreamObserver<UpdatePasswordResponse>(handler) {
				@Override
				public void onNext(UpdatePasswordResponse value) {
					handler.onNext(null);
				}
			});
	}

	@Override
	public void initiateBankIdAuthentication(@Nullable String authenticationToken, @Nullable String nationalId, @NonNull String market,
		final MutationHandler<InitiateBankIdAuthenticationResponse> handler) {
		Builder builder = InitiateBankIdAuthenticationRequest
			.newBuilder();
		if(authenticationToken != null && !authenticationToken.isEmpty()) {
			builder.setAuthenticationToken(authenticationToken);
		} else if(nationalId != null && !nationalId.isEmpty()){
			builder.setNationalId(nationalId);
		}
		InitiateBankIdAuthenticationRequest request = builder
			.setMarketCode(market)
			.build();

		bankIdService.initiateBankIdAuthentication(request,
			new SimpleStreamObserver<se.tink.grpc.v1.rpc.InitiateBankIdAuthenticationResponse>(
				handler) {
				@Override
				public void onNext(se.tink.grpc.v1.rpc.InitiateBankIdAuthenticationResponse value) {
					handler.onNext(converter.map(value,
						se.tink.core.models.user.InitiateBankIdAuthenticationResponse.class));
				}
			});
	}

	@Override
	public void register(String authenticationToken, String email, String locale,
		final MutationHandler<RegisterResponse> handler) {
		RegisterRequest request = RegisterRequest.newBuilder()
			.setAuthenticationToken(authenticationToken).setEmail(email).setLocale(locale).build();

		service.register(request,
			new SimpleStreamObserver<se.tink.grpc.v1.rpc.RegisterResponse>(handler) {
				@Override
				public void onNext(se.tink.grpc.v1.rpc.RegisterResponse value) {
					handler
						.onNext(
							converter.map(value, se.tink.core.models.user.RegisterResponse.class));
				}
			});
	}

	@Override
	public void register(String authenticationToken, String locale,
		final MutationHandler<RegisterResponse> handler) {
		RegisterRequest request = RegisterRequest.newBuilder()
			.setAuthenticationToken(authenticationToken).setLocale(locale).build();

		service.register(request,
			new SimpleStreamObserver<se.tink.grpc.v1.rpc.RegisterResponse>(handler) {
				@Override
				public void onNext(se.tink.grpc.v1.rpc.RegisterResponse value) {
					handler
						.onNext(
							converter.map(value, se.tink.core.models.user.RegisterResponse.class));
				}
			});
	}

	@Override
	public void logout(boolean autologout, final MutationHandler<LogoutResponse> handler) {
		LogoutRequest request = LogoutRequest.newBuilder().setAutologout(autologout).build();

		service
			.logout(request, new SimpleStreamObserver<se.tink.grpc.v1.rpc.LogoutResponse>(handler) {
				@Override
				public void onNext(se.tink.grpc.v1.rpc.LogoutResponse value) {
					handler.onNext(new LogoutResponse());
				}
			});
	}

	@Override
	public void deleteUser(DeleteAccountReason reason,
		MutationHandler<DeleteUserResponse> mutationHandler) {
		userService.deleteUser(converter.map(reason, DeleteUserRequest.class),
			new SimpleStreamObserver<DeleteUserResponse>(mutationHandler) {
				@Override
				public void onNext(DeleteUserResponse value) {
					mutationHandler.onNext(null);
				}
			});
	}

	@Override
	public void getProfile(final MutationHandler<GetProfileResponse> mutationHandler) {
		GetProfileRequest request = GetProfileRequest.newBuilder().build();
		userService.getProfile(request,
			new SimpleStreamObserver<se.tink.grpc.v1.rpc.GetProfileResponse>(mutationHandler) {
				@Override
				public void onNext(se.tink.grpc.v1.rpc.GetProfileResponse value) {
					mutationHandler.onNext(converter.map(value, GetProfileResponse.class));
				}
			});
	}

	@Override
	public void fortgotPassword(String email, final MutationHandler<Void> handler) {
		emailAndPasswordService
			.forgotPassword(ForgotPasswordRequest.newBuilder().setEmail(email).build(),
				new SimpleStreamObserver<ForgotPasswordResponse>(handler) {
					@Override
					public void onNext(ForgotPasswordResponse value) {
						handler.onNext(null);
					}
				});
	}

	@Override
	public void rateThisApp(boolean rated, final MutationHandler<Void> handler) {
		RateAppStatus status = rated ? RateAppStatus.RATE_APP_STATUS_CLICKED_RATE_IN_STORE
			: RateAppStatus.RATE_APP_STATUS_CLICKED_IGNORE;
		RateAppRequest request = RateAppRequest.newBuilder().setStatus(status).build();
		userService.rateApp(request, new SimpleStreamObserver<RateAppResponse>(handler) {
			@Override
			public void onNext(RateAppResponse value) {
				handler.onNext(null);
			}
		});

	}

}

