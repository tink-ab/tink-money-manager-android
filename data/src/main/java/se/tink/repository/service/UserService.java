package se.tink.repository.service;

import se.tink.core.models.user.GetProfileResponse;
import se.tink.repository.MutationHandler;

public interface UserService extends TinkService {

	void getProfile(final MutationHandler<GetProfileResponse> mutationHandler);
}
