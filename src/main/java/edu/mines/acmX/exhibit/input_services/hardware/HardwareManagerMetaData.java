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
package edu.mines.acmX.exhibit.input_services.hardware;

import java.util.List;
import java.util.Map;

/**
 * A POJO for managing the various aspects for a set of functionalities and
 * devices the HardwareManager should account for.
 *  
 * @author Aakash Shah
 * @author Ryan Stauffer
 *
 * @see
 * 	{@link HardwareManager} {@link HardwareManagerManifestLoader}
 */
public class HardwareManagerMetaData {
	private Map<String, String> functionalities; // functionalities->interface
	private Map<String, String> devices; // device->driver class
	private Map<String, List<String>> deviceSupports; // device->functionalities
	
	public Map<String, String> getFunctionalities() {
		return functionalities;
	}
	public void setFunctionalities(Map<String, String> functionalities) {
		this.functionalities = functionalities;
	}
	public Map<String, String> getDevices() {
		return devices;
	}
	public void setDevices(Map<String, String> devices) {
		this.devices = devices;
	}
	public Map<String, List<String>> getDeviceSupports() {
		return deviceSupports;
	}
	public void setDeviceSupports(Map<String, List<String>> deviceSupports) {
		this.deviceSupports = deviceSupports;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((deviceSupports == null) ? 0 : deviceSupports.hashCode());
		result = prime * result + ((devices == null) ? 0 : devices.hashCode());
		result = prime * result
				+ ((functionalities == null) ? 0 : functionalities.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof HardwareManagerMetaData)) {
			return false;
		}
		HardwareManagerMetaData other = (HardwareManagerMetaData) obj;
		if (deviceSupports == null) {
			if (other.deviceSupports != null) {
				return false;
			}
		} else if (!deviceSupports.equals(other.getDeviceSupports())) {
			return false;
		}
		if (devices == null) {
			if (other.devices != null) {
				return false;
			}
		} else if (!devices.equals(other.getDevices())) {
			return false;
		}
		if (functionalities == null) {
			if (other.functionalities != null) {
				return false;
			}
		} else if (!functionalities.equals(other.getFunctionalities())) {
			return false;
		}
		return true;
	}

	
}
