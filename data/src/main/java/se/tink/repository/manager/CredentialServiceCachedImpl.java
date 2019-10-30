package se.tink.repository.manager;

import androidx.annotation.NonNull;
import com.google.common.collect.Lists;
import java.util.List;
import java.util.Map;
import javax.inject.Inject;
import se.tink.core.models.credential.Credential;
import se.tink.core.models.misc.Field;
import se.tink.repository.ChangeObserver;
import se.tink.repository.DefaultMutationHandler;
import se.tink.repository.MutationHandler;
import se.tink.repository.cache.WritableCacheRepository;
import se.tink.repository.service.CredentialService;
import se.tink.repository.service.StreamingService;

public class CredentialServiceCachedImpl implements CredentialService {

	private final CredentialService service;
	private final StreamingService streamingService;
	private final WritableCacheRepository<Credential> cache;
	private final List<ChangeObserver<Credential>> changeObserverers;

	@Inject
	public CredentialServiceCachedImpl(
		final CredentialService service,
		StreamingService streamingService,
		final WritableCacheRepository<Credential> cache
	) {
		this.service = service;
		this.streamingService = streamingService;
		this.cache = cache;
		this.changeObserverers = Lists.newArrayList();

		addCacheAsStreamingObserver();
		setupStreamingService();
	}

	@Override
	public void list(final MutationHandler<List<Credential>> handler) {
		service.list(handler);
	}

	@Override
	public void create(Credential credential, MutationHandler<Credential> handler) {
		service.create(credential, handler);
	}

	@Override
	public void delete(Credential credential, MutationHandler<Void> handler) {
		service.delete(credential, handler);
	}

	@Override
	public void enableCredential(Credential credential, MutationHandler<Void> handler) {
		service.enableCredential(credential, handler);
	}

	@Override
	public void disableCredential(Credential credential, MutationHandler<Void> handler) {
		service.disableCredential(credential, handler);
	}

	@Override
	public void update(String credentialId, List<Field> fields, MutationHandler<Credential> handler) {
		service.update(credentialId, fields, handler);
	}

	@Override
	public void refresh(List<String> credentialIds, MutationHandler<Void> handler) {
		service.refresh(credentialIds, handler);
	}

	@Override
	public void supplementInformation(Credential credential, MutationHandler<Void> handler) {
		service.supplementInformation(credential, handler);
	}

	@Override
	public void cancelSupplementalInformation(@NonNull Credential credential,
		MutationHandler<Void> handler) {
		service.cancelSupplementalInformation(credential, handler);
	}

	@Override
	public void subscribe(ChangeObserver<Credential> listener) {
		changeObserverers.add(listener);
		readFromCache(listener);
	}

	private void readFromCache(final ChangeObserver<Credential> listener) {
		cache.read(listener);
	}

	@Override
	public void unsubscribe(ChangeObserver<Credential> listener) {
		changeObserverers.remove(listener);
	}

	@Override
	public void thirdPartyCallback(String state, Map<String, String> parameters,
		MutationHandler<Void> handler) {
		service.thirdPartyCallback(state, parameters, handler);
	}

	private void addCacheAsStreamingObserver() {
		changeObserverers.add(new ChangeObserver<Credential>() {
			@Override
			public void onCreate(List<Credential> items) {
				cache.add(items, new DefaultMutationHandler<>());
			}

			@Override
			public void onRead(List<Credential> items) {
				cache.reset(items, new DefaultMutationHandler<>());
			}

			@Override
			public void onUpdate(List<Credential> items) {
				cache.update(items, new DefaultMutationHandler<>());
			}

			@Override
			public void onDelete(List<Credential> items) {
				cache.delete(items, new DefaultMutationHandler<>());
			}
		});
	}

	private void setupStreamingService() {
		streamingService.subscribeForCredentials(new ChangeObserver<Credential>() {
			@Override
			public void onCreate(List<Credential> items) {
				for (int i = changeObserverers.size() - 1; i >= 0; i--) {
					ChangeObserver<Credential> changeObserver = changeObserverers.get(i);
					changeObserver.onCreate(Lists.newArrayList(items));
				}
			}

			@Override
			public void onRead(List<Credential> items) {
				for (int i = changeObserverers.size() - 1; i >= 0; i--) {
					ChangeObserver<Credential> changeObserver = changeObserverers.get(i);
					changeObserver.onRead(Lists.newArrayList(items));
				}
			}

			@Override
			public void onUpdate(List<Credential> items) {
				for (int i = changeObserverers.size() - 1; i >= 0; i--) {
					ChangeObserver<Credential> changeObserver = changeObserverers.get(i);
					changeObserver.onUpdate(Lists.newArrayList(items));
				}
			}

			@Override
			public void onDelete(List<Credential> items) {
				for (int i = changeObserverers.size() - 1; i >= 0; i--) {
					ChangeObserver<Credential> changeObserver = changeObserverers.get(i);
					changeObserver.onDelete(Lists.newArrayList(items));
				}
			}
		});
	}
}
