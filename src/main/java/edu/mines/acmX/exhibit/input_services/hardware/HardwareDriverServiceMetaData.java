package edu.mines.acmX.exhibit.input_services.hardware;

import java.util.List;
import java.util.Map;

public class HardwareDriverServiceMetaData {
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
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof HardwareDriverServiceMetaData)) {
			return false;
		}
		HardwareDriverServiceMetaData other = (HardwareDriverServiceMetaData) obj;
		if (deviceSupports == null) {
			if (other.deviceSupports != null) {
				return false;
			}
		} else if (!deviceSupports.equals(other.deviceSupports)) {
			return false;
		}
		if (devices == null) {
			if (other.devices != null) {
				return false;
			}
		} else if (!devices.equals(other.devices)) {
			return false;
		}
		if (functionalities == null) {
			if (other.functionalities != null) {
				return false;
			}
		} else if (!functionalities.equals(other.functionalities)) {
			return false;
		}
		return true;
	}

	
}
