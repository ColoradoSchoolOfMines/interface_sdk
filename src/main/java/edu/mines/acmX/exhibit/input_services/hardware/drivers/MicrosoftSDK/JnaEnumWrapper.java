package edu.mines.acmX.exhibit.input_services.hardware.drivers.MicrosoftSDK;

import com.sun.jna.*;
import java.lang.reflect.ParameterizedType;

public class JnaEnumWrapper<T extends JnaEnum<T>> implements NativeMapped {
	public JnaEnum value;

	@SuppressWarnings("unchecked")
	public Object fromNative(Object nativeValue, FromNativeContext context){
		Integer i = (Integer) nativeValue;
		Class targetClass = context.getTargetType();

		if (!JnaEnumWrapper.class.isAssignableFrom(targetClass)) {
			return null;
		}

		Class<?> enumClass;
		if(context instanceof StructureReadContext) {
			StructureReadContext readContext = (StructureReadContext) context;

			ParameterizedType type = (ParameterizedType) readContext.getField().getGenericType();
			String s = type.getActualTypeArguments()[0].toString().substring(6);  // remove "class "

			try {
				enumClass = Class.forName(s);
			} catch (ClassNotFoundException e) {
				enumClass = null;
			}
		} else {
		    // HACK
			// The only time we get here is the array in NUI_SKELETON_DATA so we know what the class should be
			// if we expand the array into all of its individual elements (20 of them) then this is no longer needed
			enumClass = NUI_SKELETON_POSITION_TRACKING_STATE.class;
		}

		if(enumClass == null)
			return null;

		Object[] enums = enumClass.getEnumConstants();
		if (enums == null || enums.length == 0) {
			return null;
		}

		// In order to avoid nasty reflective junk and to avoid needing
		// to know about every subclass of JnaEnum, we retrieve the first
		// element of the enum and make IT do the conversion for us.
		JnaEnum instance = (JnaEnum) enums[0];
		return new JnaEnumWrapper((T) instance.getForValue(i));
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