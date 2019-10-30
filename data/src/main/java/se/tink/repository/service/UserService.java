package se.tink.repository.service;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import se.tink.core.models.user.CollectBankIdAuthenticationResponse;
import se.tink.core.models.user.DeleteAccountReason;
import se.tink.core.models.user.EmailAndPasswordAuthenticationResponse;
import se.tink.core.models.user.GetProfileResponse;
import se.tink.core.models.user.InitiateBankIdAuthenticationResponse;
import se.tink.core.models.user.LoginResponse;
import se.tink.core.models.user.LogoutResponse;
import se.tink.core.models.user.RegisterResponse;
import se.tink.core.models.user.UpdateEmailResponse;
import se.tink.grpc.v1.rpc.DeleteUserResponse;
import se.tink.repository.MutationHandler;

public interface UserService extends TinkService {

	void login(String authenticationId, final MutationHandler<LoginResponse> handler);

	void emailAndPasswordAuthentication(String email, String password, String market,
		final MutationHandler<EmailAndPasswordAuthenticationResponse> handler);

	void changeEmail(String email, final MutationHandler<UpdateEmailResponse> handler);

	void changePassword(String oldPassword, String newPassword,
		final MutationHandler<Void> handler);

	void collectBankIdAuthentication(String authenticationId,
		final MutationHandler<CollectBankIdAuthenticationResponse> handler);

	void deleteUser(DeleteAccountReason reason,
		MutationHandler<DeleteUserResponse> mutationHandler);

	void initiateBankIdAuthentication(@Nullable String authenticationToken, @Nullable String nationalId, @NonNull String market,
		final MutationHandler<InitiateBankIdAuthenticationResponse> handler);

	void register(String authenticationToken, String email, String locale,
		final MutationHandler<RegisterResponse> handler);

	void register(String authenticationToken, String locale,
		MutationHandler<RegisterResponse> handler);

	void logout(boolean autologout, final MutationHandler<LogoutResponse> handler);

	void getProfile(final MutationHandler<GetProfileResponse> mutationHandler);

	void fortgotPassword(String email, MutationHandler<Void> handler);

	void rateThisApp(boolean rated, MutationHandler<Void> handler);
}
