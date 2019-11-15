package com.tink.pfmsdk.theme;

import android.content.Context;
import android.graphics.Typeface;
import androidx.core.content.ContextCompat;
import com.tink.pfmsdk.R;
import com.tink.pfmsdk.util.FontUtils;
import com.tink.pfmsdk.view.TinkTextView.Theme;
import se.tink.commons.extensions.ContextUtils;

public class AxisLabel implements Theme {

	private final Context context;

	public AxisLabel(Context context) {
		this.context = context;
	}

	@Override
	public int getTextColor() {
		return ContextUtils.getColorFromAttr(context, R.attr.tink_chartAxisLabelColor);
	}

	@Override
	public Typeface getFont() {
		return FontUtils.getTypeface(FontUtils.REGULAR_FONT, context);
	}

	@Override
	public float getTextSize() {
		return context.getResources().getDimension(R.dimen.chart_label_text_size);
	}

	@Override
	public float getLineHeight() {
		return context.getResources().getDimension(R.dimen.chart_label_line_height);
	}

	@Override
	public float getSpacing() {
		return 0;
	}

	@Override
	public boolean toUpperCase() {
		return false;
	}
}
