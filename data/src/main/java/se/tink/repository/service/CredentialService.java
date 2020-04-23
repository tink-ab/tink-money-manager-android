package se.tink.repository.service;

import androidx.annotation.NonNull;
import java.util.List;
import java.util.Map;
import se.tink.core.models.credential.Credential;
import se.tink.core.models.misc.Field;
import se.tink.repository.ChangeObserver;
import se.tink.repository.MutationHandler;

public interface CredentialService extends TinkService {

	void supplementInformation(Credential credential, MutationHandler<Void> handler);

	void cancelSupplementalInformation(@NonNull Credential credential,
		MutationHandler<Void> handler);

	void subscribe(ChangeObserver<Credential> listener);

	void unsubscribe(ChangeObserver<Credential> listener);

	void refresh(List<String> credentialIds, final MutationHandler<Void> handler);

	void list(MutationHandler<List<Credential>> handler);

	void update(String credentialId, List<Field> fields, final MutationHandler<Credential> handler);

	void create(Credential credential, final MutationHandler<Credential> handler);

	void delete(Credential credential, final MutationHandler<Void> handler);

	void enableCredential(Credential credential, final MutationHandler<Void> handler);

	void disableCredential(Credential credential, final MutationHandler<Void> handler);

    void thirdPartyCallback(String state, Map<String, String> parameters,
		final MutationHandler<Void> handler);
}

