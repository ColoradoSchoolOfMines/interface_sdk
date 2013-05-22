/*
 * ModuleManagerMetaData.java
 * Copyright (C) 2013 Andrew DeMaria (muff1nman) <ademaria@mines.edu>
 *
 * All Rights Reserved.
 */

package edu.mines.acmX.exhibit.module_manager;

public class ModuleManagerMetaData {
    private String defaultModuleName;
    
	public ModuleManagerMetaData(String defaultModuleName) {
        this.defaultModuleName = defaultModuleName;
	}

    public String getDefaultModuleName() {
        return defaultModuleName;
    }

    // DEBUG USE ONLY
    
    public void setDefaultModuleName(String name) {
        defaultModuleName = name;
    }
}


