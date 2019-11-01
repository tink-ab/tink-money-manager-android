package com.tink.pfmsdk.overview.charts;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import butterknife.BindView;
import com.tink.pfmsdk.BaseFragment;
import com.tink.pfmsdk.R;
import com.tink.pfmsdk.analytics.AnalyticsScreen;
import com.tink.pfmsdk.view.TinkIcon;
import com.tink.pfmsdk.view.TinkTextView;
import javax.inject.Inject;


public class LeftToSpendTutorialFragment extends BaseFragment {

	@Inject
	Theme fragmentTheme;

	ImageView imageView;

	ViewGroup container;

	TinkTextView tutorialDescription;

	TinkTextView tutorialStepOneNumber;

	TinkTextView tutorialStepTwoNumber;

	TinkTextView tutorialStepThreeNumber;

	TinkTextView tutorialStepOneDescription;

	TinkTextView tutorialStepTwoDescription;

	TinkTextView tutorialStepThreeDescription;

	Button close;

	@Override
	public int getLayoutId() {
		return R.layout.left_to_spend_tutorial_fragment;
	}

	@Override
	public boolean needsLoginToBeAuthorized() {
		return false;
	}

	@Override
	protected Theme getTheme() {
		return fragmentTheme;
	}

	@Override
	protected AnalyticsScreen getAnalyticsScreen() {
		return AnalyticsScreen.LEFT_TO_SPEND_TUTORIAL;
	}

	@Override
	protected boolean hasToolbar() {
		return false;
	}

	@Override
	protected boolean doNotRecreateView() {
		return true;
	}

	@Override
	protected void applyTheme(BaseFragment.Theme theme) {
		super.applyTheme(theme);

		imageView.setImageDrawable(fragmentTheme.getChartDrawable());
		//close.setTheme(fragmentTheme.getCloseTheme());
	}

	public interface Theme extends BaseFragment.Theme {

		Drawable getChartDrawable();

		// TODO: PFMSDK: Don't use custom icon view and theme
		//TinkIconView.Theme getCloseTheme();
	}

	@Override
	public void authorizedOnCreateView(LayoutInflater inflater, ViewGroup container,
		Bundle savedInstanceState) {
		super.authorizedOnCreateView(inflater, container, savedInstanceState);

		// TODO: PFMSDK: Don't use butterknife and using data binding after making this fragment kotlinized
		imageView = view.findViewById(R.id.chart_image);
		container = view.findViewById(R.id.container);
		tutorialDescription = view.findViewById(R.id.tutorial_description);
		tutorialStepOneNumber = view.findViewById(R.id.tutorial_step_one_number);
		tutorialStepTwoNumber = view.findViewById(R.id.tutorial_step_two_number);
		tutorialStepThreeNumber = view.findViewById(R.id.tutorial_step_three_number);
		tutorialStepOneDescription = view.findViewById(R.id.tutorial_step_one_description);
		tutorialStepTwoDescription = view.findViewById(R.id.tutorial_step_two_description);
		tutorialStepThreeDescription = view.findViewById(R.id.tutorial_step_three_description);
		close = view.findViewById(R.id.close);
		setupContent();
		setupClose();
	}

	private void setupClose() {
		close.setOnClickListener(v -> {
			if (getActivity() != null) {
				getActivity().onBackPressed();
			}
		});
	}

	private void setupContent() {
		tutorialDescription.setText(getString(R.string.left_to_spend_tutorial_description));
		tutorialStepOneNumber.setText("1");
		tutorialStepTwoNumber.setText("2");
		tutorialStepThreeNumber.setText("3");
		tutorialStepOneDescription
			.setText(getString(R.string.left_to_spend_tutorial_description_one));
		tutorialStepTwoDescription
			.setText(getString(R.string.left_to_spend_tutorial_description_two));
		tutorialStepThreeDescription
			.setText(getString(R.string.left_to_spend_tutorial_description_three));

		close.setText(TinkIcon.CLOSE);
	}
}
