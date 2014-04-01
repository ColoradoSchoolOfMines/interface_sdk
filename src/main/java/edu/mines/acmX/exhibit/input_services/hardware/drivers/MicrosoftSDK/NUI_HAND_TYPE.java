package edu.mines.acmX.exhibit.input_services.hardware.drivers.MicrosoftSDK;

public enum NUI_HAND_TYPE implements JnaEnum<NUI_HAND_TYPE> {

	NUI_HAND_TYPE_NONE(0),
	NUI_HAND_TYPE_LEFT(NUI_HAND_TYPE_NONE.value+1),
	NUI_HAND_TYPE_RIGHT(NUI_HAND_TYPE_LEFT.value+1);

	public int value;
	private NUI_HAND_TYPE(int value) {
		this.value = value;
	}

	public int getIntValue() {
		return value;
	}

	public NUI_HAND_TYPE getForValue(int i) {
		for (NUI_HAND_TYPE x : values()) {
			if (x.value == i) {
				return x;
			}
		}
		return null;
	}
}
