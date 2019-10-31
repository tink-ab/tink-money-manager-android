package com.tink.pfmsdk.collections;

import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import java.util.List;
import java.util.NoSuchElementException;
import se.tink.core.models.provider.Provider;
import se.tink.privacy.Clearable;
import se.tink.privacy.DataWipeManager;
import se.tink.repository.ChangeObserver;
import se.tink.repository.service.ProviderService;

@Deprecated
public class Providers implements ChangeObserver<Provider>, Clearable {

	private static Providers instance;

	private List<Provider> providers = Lists.newArrayList();

	public static Providers getSharedInstance() {
		if (instance == null) {
			instance = new Providers();
		}
		return instance;
	}

	private Providers() {
		DataWipeManager.sharedInstance().register(this);
	}

	@Override
	public void onCreate(List<Provider> items) {
		providers = add(providers, items);
	}

	@Override
	public void onRead(List<Provider> items) {
		providers = items;
	}

	@Override
	public void onUpdate(List<Provider> items) {
		providers = replace(providers, items);
	}

	@Override
	public void onDelete(List<Provider> items) {
		providers = delete(providers, items);
	}

	public static Provider getProvider(final String providerName, List<Provider> items) {
		try {
			return Iterables.find(items, input -> input.getName().equals(providerName));
		} catch (NoSuchElementException ex) {
			return null;
		}
	}

	public Provider getProvider(String providerName) {
		return getProvider(providerName, providers);
	}

	public void removeListener(ProviderService providerService) {
		providerService.unsubscribe(this);
	}

	public void attatchListener(ProviderService providerService) {
		providerService.subscribe(this);
	}

	@Override
	public void clear() {
		providers = Lists.newArrayList();
	}

	public static List<Provider> add(List<Provider> currentProviders, List<Provider> toAddPeriods) {
		List<Provider> modifiedProviders = copyList(currentProviders);
		modifiedProviders.addAll(toAddPeriods);
		return modifiedProviders;
	}

	public static List<Provider> replace(List<Provider> currentProviders,
		List<Provider> toAddPeriods) {
		List<Provider> modifiedProviders = delete(currentProviders, toAddPeriods);
		modifiedProviders.addAll(toAddPeriods);
		return modifiedProviders;
	}

	public static List<Provider> delete(List<Provider> currentProviders, List<Provider> toDelete) {
		List<Provider> modifiedProviders = copyList(currentProviders);
		modifiedProviders.removeAll(toDelete);
		return modifiedProviders;
	}

	private static List<Provider> copyList(List<Provider> currentProviders) {
		List<Provider> modifiedProviders;
		if (currentProviders == null) {
			modifiedProviders = Lists.newArrayList();
		} else {
			modifiedProviders = Lists.newArrayList(currentProviders);
		}
		return modifiedProviders;
	}

}
