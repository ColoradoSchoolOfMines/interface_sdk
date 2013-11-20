package edu.mines.acmX.exhibit.module_management.modules;

import java.rmi.RemoteException;
import java.util.concurrent.CountDownLatch;

/**
 * Created with IntelliJ IDEA.
 * User: andrew
 * Date: 11/20/13
 * Time: 11:30 AM
 */
public interface ModuleLatchInterface extends ModuleInterface {

	public void init(CountDownLatch waitForModule) throws RemoteException;

	public void finishExecution() throws RemoteException;
}
