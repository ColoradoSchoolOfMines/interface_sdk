package edu.mines.acmX.exhibit.module_manager;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * This class loads a single manifest for a ModuleManager.
 *
 * @author  Andrew DeMaria
 * @author  Austin Diviness
 */

public class ModuleManagerManifestLoader {

    /**
     * Creates a ModuleManagerMetaData object
     *
     * @param   path path to a manifest for a module manager.
     */
    public static ModuleManagerMetaData load(String path) throws ManifestLoadException {
        ModuleManagerMetaData data;
        try {    
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            Document manifestDocument = docBuilder.parse(new File(path));
            manifestDocument.getDocumentElement().normalize();
            data = parseManifest(manifestDocument);
        } catch (Exception e) {
            throw new ManifestLoadException("Could not load manifest" + "\n" + e.toString());
        }
        return data;
    }
    
	/**
	 * Helper function to parse the xml data to create a 
	 * ModuleManagerMetaData object.
	 *
	 * @param	manifestDocument	Document created from the ModuleManager
	 *								manifest file.
	 *
	 * @return						ModuleManagerMetaData object for the Module Manager.
	 */
    private static ModuleManagerMetaData parseManifest(Document manifestDocument) throws ManifestLoadException {
        Element element = (Element) manifestDocument.getElementsByTagName("manifest").item(0);
        element = (Element) element.getElementsByTagName("module-manager").item(0);
        if (!element.hasAttribute("default-module") || !element.hasAttribute("module-path")) {
        	throw new ManifestLoadException("Could not load manifest: missing attributes");
        }
        String defaultModule = element.getAttribute("default-module");
        String modulePath = element.getAttribute("module-path");
        return new ModuleManagerMetaData(defaultModule, modulePath);
    }

}




