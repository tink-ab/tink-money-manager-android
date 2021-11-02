package com.tink.moneymanagerui;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.tink.moneymanagerui.tracking.AnalyticsSingleton;
import com.tink.moneymanagerui.tracking.ScreenEvent;
import com.tink.moneymanagerui.util.SoftKeyboardUtils;
import com.tink.moneymanagerui.view.SnackbarManager;
import com.tink.moneymanagerui.view.TinkToolbar;

import java.util.Map;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StyleRes;
import androidx.appcompat.view.ContextThemeWrapper;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.Lifecycle.Event;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LifecycleRegistry;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasAndroidInjector;
import dagger.android.support.AndroidSupportInjection;

public abstract class BaseFragment extends Fragment implements HasAndroidInjector {

	public static final String ARG_MONEY_MANAGER_FEATURE_TYPE = "arg_money_manager_feature_type";

	// Make every new fragment above previous ones
	private static int sTranslationZ = 0;
	private int translationZ = sTranslationZ++;

	@Inject
	protected FragmentCoordinator fragmentCoordinator;

	@Inject
	protected TransitionCoordinator transitionCoordinator;

	@Inject
	protected SnackbarManager snackbarManager;

	@Inject
	DispatchingAndroidInjector<Object> androidInjector;

	@Inject
	protected ViewModelFactory viewModelFactory;

	private LifecycleRegistry viewLifecycle;

	protected View view;
	private boolean firstCreation = true;
	private boolean shouldTrackScreen;
	private UICallbackRunner callbacksExecutor;

	@Nullable
	protected TinkToolbar toolbar;

	public abstract int getLayoutId();

	public abstract boolean needsLoginToBeAuthorized();

	@Nullable
	public MoneyManagerFeatureType getMoneyManagerFeatureType() {
		return null;
	}

	@Nullable
	protected ScreenEvent getScreenEvent() {
		return null;
	}

	protected String getTitle() {
		return null;
	}

	protected boolean hasToolbar() {
		return getParentFragment() == null;
	}

	protected int getSoftInputMode() {
		return WindowManager.LayoutParams.SOFT_INPUT_STATE_UNSPECIFIED;
	}

	protected boolean doNotRecreateView() {
		return false;
	}

	protected boolean viewReadyAfterLayout() {
		return true;
	}

	@Override
	public AndroidInjector<Object> androidInjector() {
		return androidInjector;
	}

	@Override
	public final void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		shouldTrackScreen = true;
		//noinspection ConstantConditions - activity is not null in onCreate
		callbacksExecutor = new UICallbackRunner(getLifecycle(),
			getActivity().getApplicationContext());
		authorizedOnCreate(savedInstanceState);
	}

	@Override
	public void onAttach(@NonNull Context context) {
		AndroidSupportInjection.inject(this);
		super.onAttach(context);
	}

	protected void authorizedOnCreate(@Nullable Bundle savedInstanceState) {

	}

	@Nullable
	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		transitionCoordinator.postponeEnterTransition(this);
		viewLifecycle = new LifecycleRegistry(this);

		if (view == null) {
			View inflatedView;
			if (getFeatureSpecificTheme() != null) {
				inflatedView = createFromFeatureSpecificTheme(inflater, container, getFeatureSpecificTheme());
			} else {
				getActivity().getTheme().applyStyle(R.style.TinkFinanceOverviewStyle, false);
				inflatedView = inflater.inflate(getLayoutId(), container, false);
			}

			view = shouldAddToolbar(inflatedView) ? addToolBar(inflatedView) : inflatedView;
			toolbar = view.findViewById(R.id.tink_toolbar);
			ViewCompat.setTranslationZ(view, translationZ);

			if (hasToolbar()) {
				setupToolbar();
			}
		}
		authorizedOnCreateView(inflater, container, savedInstanceState);
		firstCreation = false;

		viewLifecycle.handleLifecycleEvent(Event.ON_CREATE);
		if (viewReadyAfterLayout()) {
			view.post(this::onViewReady);
		}
		return view;
	}

	@Nullable
	@StyleRes
	private Integer getFeatureSpecificTheme() {
		return getMoneyManagerFeatureType() != null ? FinanceOverviewFragment.getFeatureSpecificThemes().get(getMoneyManagerFeatureType()) : null;
	}

	private View createFromFeatureSpecificTheme(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @StyleRes int styleResId) {
		final Context contextThemeWrapper = new ContextThemeWrapper(getActivity(), styleResId);
		LayoutInflater localInflater = inflater.cloneInContext(contextThemeWrapper);
		return localInflater.inflate(getLayoutId(), container, false);
	}

	private boolean shouldAddToolbar(View view) {
		return hasToolbar() && view.findViewById(R.id.tink_toolbar) == null;
	}

	private View addToolBar(View content) {
		LinearLayout parent = new LinearLayout(getContext());
		parent.setOrientation(LinearLayout.VERTICAL);
		parent.setLayoutParams(
			new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
		);

		getLayoutInflater().inflate(R.layout.tink_toolbar_default, parent);
		parent.addView(content);
		return parent;
	}

	private void setupToolbar() {
		if (toolbar == null) {
			throw new IllegalStateException("No toolbar with the supplied ID was found.");
		}

		toolbar.setOnMenuItemClickListener(this::onToolbarMenuItemSelected);
		toolbar.setNavigationOnClickListener(v -> onUpPressed());

		onCreateToolbarMenu(toolbar);
	}

	@Override
	public final void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		super.onCreateOptionsMenu(menu, inflater);
	}

	@Override
	public final void onPrepareOptionsMenu(Menu menu) {
		super.onPrepareOptionsMenu(menu);
	}

	@Override
	public final boolean onOptionsItemSelected(MenuItem item) {
		return super.onOptionsItemSelected(item);
	}

	protected void onCreateToolbarMenu(@NonNull Toolbar toolbar) {
	}

	protected boolean onToolbarMenuItemSelected(@NonNull MenuItem item) {
		return false;
	}

	public void invalidateToolbarMenu() {
		if (toolbar != null) {
			toolbar.getMenu().clear();
			onCreateToolbarMenu(toolbar);
		}
	}

	public void onUpPressed() {
		if (getActivity() != null) {
			SoftKeyboardUtils.closeSoftKeyboard(getActivity());
			fragmentCoordinator.handleBackPress();
		}
	}

	private TinkToolbar getToolbar() {
		if (toolbar != null) {
			return toolbar;
		}
		if (getParentFragment() != null) {
			if (getParentFragment() instanceof BaseFragment) {
				return ((BaseFragment) getParentFragment()).getToolbar();
			}
		}
		return null;
	}

	@Override
	public void setHasOptionsMenu(boolean hasMenu) {
		if (BuildConfig.DEBUG) {
			throw new IllegalStateException("This method should not be used. Instead, let hasToolbar() return true.");
		}
	}

	protected void authorizedOnCreateView(LayoutInflater inflater, ViewGroup container, @Nullable Bundle savedInstanceState) {

	}

	@Override
	public final void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		authorizedOnViewCreated(view, savedInstanceState);
	}

	protected void authorizedOnViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
	}

	@Override
	public final void onStart() {
		super.onStart();

		if (isVisible()) {
			updateToolbar();
		}
		authorizedOnStart();
		viewLifecycle.handleLifecycleEvent(Event.ON_START);
	}

	protected void authorizedOnStart() {

	}

	@Override
	public final void onResume() {
		super.onResume();
		if (getUserVisibleHint()) {
			trackScreenIfNecessary();
		}
		authorizedOnResume();
		viewLifecycle.handleLifecycleEvent(Event.ON_RESUME);
	}

	protected void authorizedOnResume() {

	}

	@Override
	public void onPause() {
		viewLifecycle.handleLifecycleEvent(Event.ON_PAUSE);
		super.onPause();
		shouldTrackScreen = true;
	}

	@Override
	public void onStop() {
		viewLifecycle.handleLifecycleEvent(Event.ON_STOP);
		super.onStop();
	}

	@Override
	public void onDestroyView() {
		viewLifecycle.handleLifecycleEvent(Event.ON_DESTROY);
		super.onDestroyView();
		if (!doNotRecreateView()) {
			view = null;
			firstCreation = true;
		}
		// We can be destroyed when our view is not ready yet
		// Call it here to avoid lock
		onViewReady();
	}

	public boolean onBackPressed() {
		return false;
	}

	protected boolean shouldTrackScreen() {
		return shouldTrackScreen && getScreenEvent() != null;
	}

	public void trackScreenIfNecessary() {
		if (shouldTrackScreen()) {
			trackScreen();
			shouldTrackScreen = false;
		}
	}

	public void trackScreen() {
		if (AnalyticsSingleton.getTracker() != null && getScreenEvent() != null) {
			AnalyticsSingleton.getTracker().track(getScreenEvent());
		}
	}

	protected void updateToolbar() {
		if (getTitle() != null) {
			setTitle(getTitle());
		}
	}

	public void setTitle(String title) {
		TinkToolbar toolbar = getToolbar();
		if (toolbar != null) {
			toolbar.setTitle(title);
		}
	}

	protected boolean isFirstCreation() {
		return firstCreation;
	}

	/**
	 * Must be called when view hierarchy is fully inflated and ready to be displayed.
	 * Currently used to start transition animations.
	 * It's safe to call this method multiple times
	 *
	 * @see #onChildViewReady
	 * @see #viewReadyAfterLayout()
	 */
	protected void onViewReady() {
		transitionCoordinator.startPostponedEnterTransition(this);
		Fragment parent = getParentFragment();
		if (parent instanceof BaseFragment) {
			((BaseFragment) parent).onChildViewReady(this);
		}
	}

	/**
	 * Called when child view is ready.
	 * Can be used by its parent to determine when all children are ready
	 *
	 * @param child - fragment which view is ready now
	 */
	protected void onChildViewReady(BaseFragment child) {

	}

	/**
	 * @return root fragment for this hierarchy.
	 * It's convenient to use to share view model between parent and descendants,
	 * but do not keep it for the app lifetime (as it in fact is if we attach it to the main activity)
	 */
	public BaseFragment getRootFragment() {
		Fragment parent = getParentFragment();
		if (parent instanceof BaseFragment) {
			return ((BaseFragment) parent).getRootFragment();
		}
		return this;
	}

	public LifecycleOwner getViewLifecycle() {
		return () -> viewLifecycle;
	}

	public boolean getMayChangeUI() {
		return getLifecycle().getCurrentState().isAtLeast(Lifecycle.State.STARTED);
	}

	/**
	 * Called when a third party callback uri is received by the app.
	 * Fragments that pass the callback parameters to the service should override this.
	 *
	 * @param state The state string from the callback url
	 * @param parameters The parameters map from the callback url
	 * @return return true if this is handled by the current fragment, false otherwise
	 */
	public boolean handleThirdPartyCallbackResult(String state, Map<String, String> parameters) {
		return false;
	}

	public void runUiDependant(Runnable callback) {
		callbacksExecutor.runUiDependant(callback);
	}
}
