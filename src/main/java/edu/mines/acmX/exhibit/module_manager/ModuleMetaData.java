/**
 * ModuleMetadata.java
 * <p>
 * A data structure to store configuration information found in a Module's
 * manifest file.
 *
 * @author  Andrew DeMaria
 * @author  Austin Diviness
 */

package edu.mines.acmX.exhibit.module_manager;

import java.util.List;
import java.util.Map;

public class ModuleMetaData {
    // manifest top level
    private String packageName;
    private String className;

    // uses-sdk
    private String minSdkVersion, targetSdkVersion;

    // module definition
    private String iconPath, title, author, version;

    // inputs
    // document what boolean means
    Map<String, DependencyType> inputTypes;

    // required modules
    Map<String, DependencyType> moduleDependencies;

    /**
     * Creates a ModuleMetaData object
     *
     * @param   packageName         Package name of the module.
     *                              ex. com.example.app
     * @param   className           Class name of the class that implements one
     *                              of the ModuleInterface(s)
     * @param   minSdkVersion       Lowest sdk version the module can use
     * @param   targetSdkVersion    Sdk version app ideally wants
     * @param   iconPath            File path to icon image
     * @param   title               Title of module
     * @param   author              Author of module
     * @param   version             Module version
     * @param   inputTypes          Map of Input types used by module. Map 
     *                              keys indicate input type, boolean value
     *                              indicates if input type is required
     * @param   moduleDependencies  Other modules required by the module. A 
     *                              map that associates the module package
     *                              names to their optional/required level
     */
	public ModuleMetaData(String packageName, String className, String minSdkVersion,
			String targetSdkVersion, String iconPath, String title,
			String author, String version, Map<String, DependencyType> inputTypes,
			Map<String, DependencyType> moduleDependencies) {
		super();
		this.packageName = packageName;
		this.minSdkVersion = minSdkVersion;
		this.targetSdkVersion = targetSdkVersion;
		this.iconPath = iconPath;
		this.title = title;
		this.author = author;
		this.version = version;
		this.inputTypes = inputTypes;
		this.moduleDependencies = moduleDependencies;
	}

	public String getPackageName() {
		return packageName;
	}

    public String getClassName() {
        return className;
    }

	public String getMinSdkVersion() {
		return minSdkVersion;
	}

	public String getTargetSdkVersion() {
		return targetSdkVersion;
	}

	public String getIconPath() {
		return iconPath;
	}

	public String getTitle() {
		return title;
	}

	public String getAuthor() {
		return author;
	}

	public String getVersion() {
		return version;
	}

	public Map<String, DependencyType> getInputTypes() {
		return inputTypes;
	}

	public Map<String, DependencyType> getModuleDependencies() {
		return moduleDependencies;
	}

	// DEBUG PURPOSES ONLY
	
	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

    public void setClassName(String className) {
        this.className = className;
    }

	public void setMinSdkVersion(String minSdkVersion) {
		this.minSdkVersion = minSdkVersion;
	}

	public void setTargetSdkVersion(String targetSdkVersion) {
		this.targetSdkVersion = targetSdkVersion;
	}

	public void setIconPath(String iconPath) {
		this.iconPath = iconPath;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public void setInputTypes(Map<String, DependencyType> inputTypes) {
		this.inputTypes = inputTypes;
	}

	public void setModuleDependencies(Map<String, DependencyType> moduleDependencies) {
		this.moduleDependencies = moduleDependencies;
	}
}


