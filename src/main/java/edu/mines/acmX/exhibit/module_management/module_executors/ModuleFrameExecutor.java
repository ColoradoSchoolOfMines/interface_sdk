package edu.mines.acmX.exhibit.module_management.module_executors;

import edu.mines.acmX.exhibit.module_management.modules.ModuleFrame;
import edu.mines.acmX.exhibit.module_management.modules.ModuleInterface;
import edu.mines.acmX.exhibit.module_management.modules.ProcessingModule;
import processing.core.PApplet;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Stack;
import java.util.concurrent.Semaphore;

public class ModuleFrameExecutor extends ModuleExecutor{

    protected volatile ModuleFrame moduleFrame;
    protected volatile ModuleInterface module;
    final Semaphore executorSemaphore = new Semaphore(1);

    public ModuleFrameExecutor(String fullyQualifiedModuleName, String jarPath) {
        super(fullyQualifiedModuleName, jarPath);
        try{
            Method method = URLClassLoader.class.getDeclaredMethod("addURL", new Class[]{URL.class});
            method.setAccessible(true);
            method.invoke(ClassLoader.getSystemClassLoader(), new Object[]{new File(jarPath).toURI().toURL()});
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() throws ModuleRuntimeException {
        try {
            // Create a new instance of incoming module
            Class modClass = Class.forName(fullyQualifiedModuleName);
            module = ((ModuleInterface) modClass.newInstance());
            moduleFrame = new ModuleFrame((ProcessingModule)module);
            executorSemaphore.acquire();
            ((ProcessingModule)module).execute();
            executorSemaphore.acquire();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
            System.err.println("Semaphore failed to synchronize executor; abandoning module runtime");
        }
    }

    public synchronized void close() {
        if(moduleFrame != null) {
            moduleFrame.setVisible(false);
            moduleFrame.dispose();
            executorSemaphore.release();
        }
    }

}
