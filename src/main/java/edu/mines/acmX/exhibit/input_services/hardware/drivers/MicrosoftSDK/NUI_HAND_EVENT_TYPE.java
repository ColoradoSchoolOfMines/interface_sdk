package edu.mines.acmX.exhibit.input_services.hardware.drivers.MicrosoftSDK;

public enum NUI_HAND_EVENT_TYPE implements JnaEnum<NUI_HAND_EVENT_TYPE> {
	NUI_HAND_EVENT_TYPE_NONE(0),
	NUI_HAND_EVENT_TYPE_GRIP(NUI_HAND_EVENT_TYPE_NONE.value + 1),
	NUI_HAND_EVENT_TYPE_GRIPRELEASE(NUI_HAND_EVENT_TYPE_GRIP.value + 1);

	public int value;
	private NUI_HAND_EVENT_TYPE(int value) {
		this.value = value;
	}

	public int getIntValue() {
		return value;
	}

	public NUI_HAND_EVENT_TYPE getForValue(int i) {
		for (NUI_HAND_EVENT_TYPE x : values()) {
			if (x.value == i) {
				return x;
			}
		}
		return null;
	}
}
