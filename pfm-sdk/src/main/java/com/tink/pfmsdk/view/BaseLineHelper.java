package com.tink.pfmsdk.view;

public class BaseLineHelper {

	private final float materialStep;

	public BaseLineHelper(float materialStep) {
		this.materialStep = materialStep;
	}

	public int putHeightOnGrid(int height) {
		float offsetFromGrid = height % materialStep;
		if (offsetFromGrid > 0) {
			return (int) (materialStep - Math.floor(offsetFromGrid));
		}
		return 0;
	}


	public int getCompoundTopPaddingToPutBaselineOnGrid(int baseLine) {
		float offsetFromGrid = baseLine % materialStep;
		if (offsetFromGrid > 0) {
			return (int) (materialStep - Math.ceil(offsetFromGrid));
		}
		return 0;
	}
}
