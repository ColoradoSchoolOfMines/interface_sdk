package edu.mines.acmX.exhibit.module_manager.loaders;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import edu.mines.acmX.exhibit.module_manager.metas.ModuleManagerMetaData;

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
     * @param   path File path to a manifest for a module manager.
     */
    public static ModuleManagerMetaData load(String path)
            throws ManifestLoadException {
        try {
            //return load(new FileInputStream(new File(path)));
            return load(new FileInputStream(new File(path)));
        } catch (FileNotFoundException e) {
            // TODO logging
            e.printStackTrace();
            throw new ManifestLoadException("Could not find the manifest file");
        }
    }

    /**
     * Creates a ModuleManagerMetaData object
     *
     * @param   is  InputStream for a manifest for a module manager.
     */
    public static ModuleManagerMetaData load(InputStream is) throws ManifestLoadException {
        ModuleManagerMetaData data;
        try {    
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            Document manifestDocument = docBuilder.parse(is);
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
     * @param   manifestDocument    Document created from the ModuleManager
     *                              manifest file.
     *
     * @return                      ModuleManagerMetaData object for the Module Manager.
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




