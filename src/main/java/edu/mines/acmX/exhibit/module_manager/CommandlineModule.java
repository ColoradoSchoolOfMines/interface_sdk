package edu.mines.acmX.exhibit.module_manager;

public abstract class CommandlineModule implements ModuleInterface {

    private final ModuleHelper module;

    public CommandlineModule() {
        super();
        module = new ModuleHelper();
    }

    public boolean setNextModuleToLoad( String moduleName ) {
        return module.setNextModuleToLoad( moduleName );
    }

    public void init() {
    	System.out.println("This is super");
    }

}


