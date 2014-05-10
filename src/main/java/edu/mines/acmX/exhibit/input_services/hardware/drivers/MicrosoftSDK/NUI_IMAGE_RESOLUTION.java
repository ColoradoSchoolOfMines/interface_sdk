package edu.mines.acmX.exhibit.input_services.hardware.drivers.MicrosoftSDK;

public enum NUI_IMAGE_RESOLUTION implements JnaEnum<NUI_IMAGE_RESOLUTION>  {
	NUI_IMAGE_RESOLUTION_INVALID(-1),
	NUI_IMAGE_RESOLUTION_80x60(0),
	NUI_IMAGE_RESOLUTION_320x240( NUI_IMAGE_RESOLUTION_80x60.value + 1 ) ,
	NUI_IMAGE_RESOLUTION_640x480( NUI_IMAGE_RESOLUTION_320x240.value + 1 ) ,
	NUI_IMAGE_RESOLUTION_1280x960( NUI_IMAGE_RESOLUTION_640x480.value + 1 );

	public int value;
	private NUI_IMAGE_RESOLUTION(int value) {
		this.value = value;
	}

	public int getIntValue() {
		return value;
	}

	public NUI_IMAGE_RESOLUTION getForValue(int i) {
		for (NUI_IMAGE_RESOLUTION x : values()) {
			if (x.value == i) {
				return x;
			}
		}
		return null;
	}
}
