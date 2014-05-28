package edu.mines.acmX.exhibit.module_management.module_executors;

import edu.mines.acmX.exhibit.module_management.modules.ModuleFrame;
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
    final private static Stack<ProcessingModule> moduleStack = new Stack<ProcessingModule>();

    protected ModuleFrame moduleFrame;

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
            // Freeze active module, if any
            /*if(!moduleStack.empty()){
                moduleStack.peek().pause();
            }*/
            // Disallow more than 6 JFrames on the stack
            if(moduleStack.size() > 6){
                return;
            }
            // Create a new instance of incoming module
            Class modClass = Class.forName(fullyQualifiedModuleName);
            moduleStack.push((ProcessingModule) modClass.newInstance());
            moduleFrame = new ModuleFrame(moduleStack.peek());
            //moduleStack.peek().frame = moduleFrame;
            final Semaphore executorSemaphore = new Semaphore(1);
            executorSemaphore.acquire();
            moduleFrame.addWindowListener(new WindowListener() {
                @Override
                public void windowOpened(WindowEvent e) {
                    // nop?
                }

                @Override
                public void windowClosing(WindowEvent e) {
                    // nop
                }

                @Override
                public void windowClosed(WindowEvent e) {
                    moduleStack.pop();
                    if(!moduleStack.empty()){
                        moduleStack.peek().execute();
                    }
                    if(moduleStack.empty()){
                        executorSemaphore.release();
                    }
                }

                @Override
                public void windowIconified(WindowEvent e) {
                    // nop
                }

                @Override
                public void windowDeiconified(WindowEvent e) {
                    // nop
                }

                @Override
                public void windowActivated(WindowEvent e) {
                    // nop
                }

                @Override
                public void windowDeactivated(WindowEvent e) {
                    // nop
                }
            });
            moduleStack.peek().execute();
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

    public void close() {
        moduleFrame.setVisible(false);
        moduleFrame.dispose();
    }

}
