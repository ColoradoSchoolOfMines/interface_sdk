package edu.mines.acmX.exhibit.input_services.hardware;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/*
 * Loads the config file and gives it to the HardwareServiceLoader * 
 */
public class HardwareDriverServiceLoader {
	
	public static HardwareDriverServiceMetaData load(String filename) 
			throws HardwareDriverServiceLoaderException {
		try {
			return load(new FileInputStream(filename));
		} catch (FileNotFoundException e) {
			throw new HardwareDriverServiceLoaderException("File not found "
					+ filename);
		}
		
		
	}
	
	public static HardwareDriverServiceMetaData load(InputStream is)
			throws HardwareDriverServiceLoaderException{
		try {
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			Document manifest = docBuilder.parse(is);
			manifest.getDocumentElement().normalize();
			return parseManifest(manifest);
		} catch (Exception e) {
			throw new HardwareDriverServiceLoaderException("Could not load manifest\n"
					+ e.toString());
		}
	}
	
	private static HardwareDriverServiceMetaData parseManifest(Document manifest)
			throws HardwareDriverServiceLoaderException {
		
		HardwareDriverServiceMetaData hdsmd = new HardwareDriverServiceMetaData();
		Element element = (Element) manifest.getElementsByTagName("manifest")
											.item(0);
		
		NodeList funcTagList = element.getElementsByTagName("functionalities");
		if (funcTagList.getLength() <= 0) {
			throw new HardwareDriverServiceLoaderException("No functionalities tag!");
		}
		NodeList deviceTagList = element.getElementsByTagName("devices");
		if (deviceTagList.getLength() <= 0) {
			throw new HardwareDriverServiceLoaderException("No devices tag!");
		}
		
		parseFunctionalities(funcTagList, hdsmd);
		parseDevices(deviceTagList, hdsmd);
		
		
		return hdsmd;
	}
	
	private static void parseFunctionalities(NodeList funcTagList,
											HardwareDriverServiceMetaData hdsmd)
			throws HardwareDriverServiceLoaderException {
		
		
	}
	
	private static void parseDevices(NodeList devicesTagList,
									 HardwareDriverServiceMetaData hdsmd)
			throws HardwareDriverServiceLoaderException {
		
	}
}
