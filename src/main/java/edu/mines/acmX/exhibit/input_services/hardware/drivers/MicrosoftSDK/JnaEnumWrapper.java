package edu.mines.acmX.exhibit.input_services.hardware.drivers.MicrosoftSDK;

import com.sun.jna.*;

public class JnaEnumWrapper<T extends JnaEnum<T>> implements NativeMapped {
	public JnaEnum value;

	public Object fromNative(Object nativeValue, FromNativeContext context){
		Integer i = (Integer) nativeValue;
		Class targetClass = context.getTargetType();
		if (!JnaEnum.class.isAssignableFrom(targetClass)) {
			return null;
		}

		Object[] enums = targetClass.getEnumConstants();
		if (enums.length == 0) {
			return null;
		}

		// In order to avoid nasty reflective junk and to avoid needing
		// to know about every subclass of JnaEnum, we retrieve the first
		// element of the enum and make IT do the conversion for us.
		JnaEnum instance = (JnaEnum) enums[0];
		value = (JnaEnum) instance.getForValue(i);
		return value;
	}
	/** Convert this object into a supported native type. */
	public Object toNative(){
		if(value == null)
			return null;
		return value.getIntValue();
	}
	/** Indicate the native type used by this converter. */
	public Class nativeType(){
		return Integer.class;
	}

	public JnaEnumWrapper(){
		value = null;
	}

	public JnaEnumWrapper(T value){
		this.value = value;
	}
}

interface JnaEnum<T> {
	public int getIntValue();
	public T getForValue(int i);
}