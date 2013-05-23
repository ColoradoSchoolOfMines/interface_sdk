/*
 * ProcessingModule.java
 * Copyright (C) 2013 Andrew DeMaria (muff1nman) <ademaria@mines.edu>
 *
 * All Rights Reserved.
 */

public class ProcessingModule extends PApplet implements ModuleInterface {

    @Override
    protected boolean setNextModuleToLoad( String moduleName ) {
        module.setNextModuleToLoad( moduleName );
    }

    @Override
    public void init() {
        super.init();
    }


    private final Module module;

}


