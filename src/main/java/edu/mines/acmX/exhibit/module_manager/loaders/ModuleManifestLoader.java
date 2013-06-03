package edu.mines.acmX.exhibit.module_manager.loaders;

import java.io.InputStream;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import edu.mines.acmX.exhibit.module_manager.metas.DependencyType;
import edu.mines.acmX.exhibit.module_manager.metas.ModuleMetaData;
import edu.mines.acmX.exhibit.module_manager.metas.ModuleMetaDataBuilder;

/**
 * This class loads a single module manifest file and returns a ModuleMetaData object.
 *
 * @author  Andrew DeMaria
 * @author  Austin Diviness
 */

public class ModuleManifestLoader {

    public static final String MANIFEST_PATH = "manifest.xml";

    // takes a path to a module jar and returns a ModuleMetaData object of the module manifest
    /**
     * Creates a ModuleMetaData object from a given path to a module jar.
     *
     * @param   jarPath path to the Module that is to have its manifest loaded
     */
    public static ModuleMetaData load(String jarPath) throws ManifestLoadException {
    	ModuleMetaData toReturn;
    	try {
    		JarFile jar = new JarFile( jarPath );
    		JarEntry manifestEntry = jar.getJarEntry(MANIFEST_PATH);
    		InputStream manifestStream = jar.getInputStream( manifestEntry );

    		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
    		DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
    		Document manifestDocument = docBuilder.parse( manifestStream );

    		manifestDocument.getDocumentElement().normalize();

    		// We are all setup now to parse the document.  call helper functions

    		toReturn = parseManifest( manifestDocument );
    		jar.close();
    	} catch(Exception e) {
    		throw new ManifestLoadException("Could not load manifest" + "\n" + e.toString());
    	}
    	return toReturn;
    }

    private static void checkAttribute( Element node, String attr ) throws ManifestLoadException {
        if( !node.hasAttribute(attr)) {
            throw new ManifestLoadException("Could not find the " + attr + " attribute");
        }
    }


	/**
	 * Helper function that parses the xml file to create a ModuleMetaData object.
	 *
	 * @param	manifest	The xml document to be parsed
	 *
	 * @return				ModuleMetaData object describing the manifest
	 * @throws ManifestLoadException 
	 */
    private static ModuleMetaData parseManifest( Document manifest ) throws ManifestLoadException {
        ModuleMetaDataBuilder builder = new ModuleMetaDataBuilder();
        Element manifestTag = (Element) manifest.getElementsByTagName("manifest").item(0);
        checkAttribute(manifestTag, "package");
        builder.setPackageName(manifestTag.getAttribute("package"));
        checkAttribute(manifestTag, "class");
        builder.setClassName(manifestTag.getAttribute("class"));
        
        NodeList sdk = manifestTag.getElementsByTagName("uses-sdk");
        parseSdkVersion(sdk, builder);

        NodeList moduleInfo = manifestTag.getElementsByTagName("module");
        parseModuleInfo(moduleInfo, builder);

        return builder.build();

    }

	/**
	 * Helper function that parses the manifest sdk version information.
	 *
	 * @param	sdkTag	The NodeList of all instances of the 'uses-sdk' tag
	 * @param	builder	The builder object that is gathering data
	 * @throws ManifestLoadException 
	 */
    private static void parseSdkVersion( NodeList sdkTag, ModuleMetaDataBuilder builder) throws ManifestLoadException {
        Element singleUsesTag = (Element) sdkTag.item(0);
        checkAttribute(singleUsesTag, "minSdkVersion");
        checkAttribute(singleUsesTag, "targetSdkVersion");
        builder.setMinSdkVersion(singleUsesTag.getAttribute("minSdkVersion"));
        builder.setTargetSdkVersion(singleUsesTag.getAttribute("targetSdkVersion"));
    }

	/**
	 * Helper function that parses the manifest module dependency information.
	 *
	 * @param	module	The NodeList of all instances of the 'module' tag
	 * @param	builder	The builder object that is gathering data
	 * @throws ManifestLoadException 
	 */
    private static void parseModuleInfo( NodeList module,  ModuleMetaDataBuilder builder ) throws ManifestLoadException {
        Element singleModuleTag = (Element) module.item(0);
        checkAttribute( singleModuleTag, "icon");
        builder.setIconPath( singleModuleTag.getAttribute("icon"));
        checkAttribute( singleModuleTag, "title");
        builder.setTitle( singleModuleTag.getAttribute("title"));
        checkAttribute( singleModuleTag, "author");
        builder.setAuthor( singleModuleTag.getAttribute("author"));
        checkAttribute( singleModuleTag, "version");
        builder.setVersion( singleModuleTag.getAttribute("version"));
        
        
        parseInputs(singleModuleTag.getElementsByTagName("inputs"), builder);
        parseModuleDependencies(singleModuleTag.getElementsByTagName("requires-module"), builder);
        

    }

	/**
	 * Helper function that parses the manifest for required input services.
	 *
	 * @param	inputs	The NodeList of all instances of the 'required-inputs' tag
	 * @param	builder	The builder object that is gathering data
	 * @throws ManifestLoadException 
	 */
    private static void parseInputs( NodeList inputs, ModuleMetaDataBuilder builder ) throws ManifestLoadException {
        Element singleInputTag = (Element) inputs.item(0);
        NodeList nodeList = singleInputTag.getElementsByTagName("input");
        for( int i = 0; i < nodeList.getLength(); ++i ) {
            parseInput( (Element) nodeList.item(i), builder );
        }

    }

	/**
	 * Helper function that parses an input tag for its attributes.
	 *
	 * @param	input	An  element representing a single input service requirement
	 * @param	builder	The builder object that is gathering data
	 * @throws ManifestLoadException 
	 */
    private static void parseInput( Element input, ModuleMetaDataBuilder builder ) throws ManifestLoadException {
        checkAttribute( input, "input-type" );
    	String inputType = input.getAttribute("input-type").toLowerCase();
        builder.addInputType(inputType, parseDependencyType(input));
    }

	/**
	 * Helper function that parses the manifest for required modules
	 *
	 * @param	nodeList	The NodeList of all instances of the 'required-module' tag
	 * @param	builder		The builder object that is gathering data
	 * @throws ManifestLoadException 
	 */
    private static void parseModuleDependencies(NodeList nodeList, ModuleMetaDataBuilder builder) throws ManifestLoadException {
        Element element = (Element) nodeList.item(0);
		NodeList moduleAll = element.getElementsByTagName("optional-all");
		if (moduleAll.getLength() > 0) {
			builder.setOptionalAll(true);
			return;
		}
        NodeList modules = element.getElementsByTagName("module");
        for( int i = 0; i < modules.getLength(); ++i ) {
            parseModuleDependency( (Element) modules.item(i), builder );
        }
    }

	/**
	 * Helper function that parses a single module dependency tag.
	 *
	 * @param	element	A single module dependency element
	 * @param	builder	The builder object that is gathering data
	 * @throws ManifestLoadException 
	 */
    private static void parseModuleDependency(Element element, ModuleMetaDataBuilder builder) throws ManifestLoadException {
        checkAttribute(element, "package");
        builder.addModuleDependency(element.getAttribute("package"), parseDependencyType(element));
    }

	/**
	 * Helper function that translates a 'optional' attribute to the proper
	 * DependencyType.
	 *
	 * @param	element	element that has an 'optional' attribute
	 *
	 * @return			The parsed DependencyType
	 */
    private static DependencyType parseDependencyType(Element element) {
        boolean optional = false;
        DependencyType dependencyType = DependencyType.REQUIRED;
        if (element.hasAttribute("optional")) {
            optional = Boolean.parseBoolean(element.getAttribute("optional"));
        }
        if (optional) {
        	dependencyType = DependencyType.OPTIONAL;
        }
        return dependencyType; 
    }

}


