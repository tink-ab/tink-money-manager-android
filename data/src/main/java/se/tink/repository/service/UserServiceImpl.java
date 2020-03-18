package se.tink.repository.service;

import se.tink.converter.ModelConverter;
import se.tink.core.models.user.GetProfileResponse;
import se.tink.grpc.v1.rpc.GetProfileRequest;
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

}

