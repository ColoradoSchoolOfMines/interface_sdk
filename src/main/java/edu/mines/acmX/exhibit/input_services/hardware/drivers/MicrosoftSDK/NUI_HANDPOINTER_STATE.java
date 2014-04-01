package edu.mines.acmX.exhibit.input_services.hardware.drivers.MicrosoftSDK;

public enum NUI_HANDPOINTER_STATE implements JnaEnum<NUI_HANDPOINTER_STATE> {

	NUI_HANDPOINTER_STATE_NOT_TRACKED(0x00),
	NUI_HANDPOINTER_STATE_TRACKED(0x01),           // Hand is tracked
	NUI_HANDPOINTER_STATE_ACTIVE(0x02),            // Hand is active within or near interaction region. In this state it is a candidate to become primary hand for user.
	NUI_HANDPOINTER_STATE_INTERACTIVE(0x04),       // Hand is in interaction region
	NUI_HANDPOINTER_STATE_PRESSED(0x08),           // Press happened in interaction region. Only possible in InteractionAdjuseHandPointers.
	NUI_HANDPOINTER_STATE_PRIMARY_FOR_USER(0x10);   // This hand is the primary hand for user.

	public int value;
	private NUI_HANDPOINTER_STATE(int value) {
		this.value = value;
	}

	public int getIntValue() {
		return value;
	}

	public NUI_HANDPOINTER_STATE getForValue(int i) {
		for (NUI_HANDPOINTER_STATE x : values()) {
			if (x.value == i) {
				return x;
			}
		}
		return null;
	}
}
