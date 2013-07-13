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
package edu.mines.acmX.exhibit.module_management.loaders;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import edu.mines.acmX.exhibit.module_management.metas.ModuleManagerMetaData;
import edu.mines.acmX.exhibit.module_management.metas.ModuleManagerMetaDataBuilder;

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
        ModuleManagerMetaDataBuilder builder = new ModuleManagerMetaDataBuilder();
        try {    
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            Document manifestDocument = docBuilder.parse(is);
            manifestDocument.getDocumentElement().normalize();
            parseManifest(manifestDocument, builder);
        } catch (Exception e) {
            throw new ManifestLoadException("Could not load manifest" + "\n" + e.toString());
        }
        return builder.build();
    }

    
    /**
     * Helper function to parse the xml data to create a 
     * ModuleManagerMetaData object.
     *
     * @param   manifestDocument    Document created from the ModuleManager
     *                              manifest file.
     * @param builder 
     *
     * @return                      ModuleManagerMetaData object for the Module Manager.
     */
    private static void parseManifest(Document manifestDocument, ModuleManagerMetaDataBuilder builder) throws ManifestLoadException {
        Element element = (Element) manifestDocument.getElementsByTagName("manifest").item(0);
        element = (Element) element.getElementsByTagName("module-manager").item(0);
        if (!element.hasAttribute("default-module") || !element.hasAttribute("module-path")) {
            throw new ManifestLoadException("Could not load manifest: missing attributes");
        }
        builder.setDefaultModuleName(element.getAttribute("default-module"));
        builder.setPathToModules(element.getAttribute("module-path"));
        parseConfigFiles(element.getElementsByTagName("config-files"), builder);
    }

	private static void parseConfigFiles(NodeList nodeList, ModuleManagerMetaDataBuilder builder) {
		Element configsTag = (Element) nodeList.item(0);
		NodeList configs = configsTag.getElementsByTagName("config-file");
		for( int i = 0; i < configs.getLength(); ++i ) {
			parseSingleConfig(configs.item(i), builder);
		}
		
	}

	private static void parseSingleConfig(Node item, ModuleManagerMetaDataBuilder builder) {
		Element config = (Element) item;
		builder.addConfigFile(
				config.getAttribute("name"),
				config.getAttribute("path"));
	}

}




