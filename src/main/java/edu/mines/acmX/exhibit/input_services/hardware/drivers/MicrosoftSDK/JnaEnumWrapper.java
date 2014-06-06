/**
 * Copyright (C) 2013 Colorado School of Mines
 *
 * This file is part of the Interface Software Development Kit (SDK).
 *
 * The InterfaceSDK is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * The InterfaceSDK is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with the InterfaceSDK.  If not, see <http://www.gnu.org/licenses/>.
 */
package edu.mines.acmX.exhibit.input_services.hardware.drivers.MicrosoftSDK;

import com.sun.jna.*;
import java.lang.reflect.ParameterizedType;

public class JnaEnumWrapper<T extends JnaEnum<T>> implements NativeMapped {
	public T value;

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