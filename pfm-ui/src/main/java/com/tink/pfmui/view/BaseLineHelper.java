package com.tink.pfmui.view;

class BaseLineHelper {

	private final float materialStep;

	BaseLineHelper(float materialStep) {
		this.materialStep = materialStep;
	}

	int putHeightOnGrid(int height) {
		float offsetFromGrid = height % materialStep;
		if (offsetFromGrid > 0) {
			return (int) (materialStep - Math.floor(offsetFromGrid));
		}
		return 0;
	}


	int getCompoundTopPaddingToPutBaselineOnGrid(int baseLine) {
		float offsetFromGrid = baseLine % materialStep;
		if (offsetFromGrid > 0) {
			return (int) (materialStep - Math.ceil(offsetFromGrid));
		}
		return 0;
	}
}
