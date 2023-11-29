package com.oracle.springapp.remote;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface RemoteInterface<T> extends Remote {
    public ArrayList<T> getAllRows() throws RemoteException;

    public T insertRow(T row) throws RemoteException;

    public T findRowById(String id) throws RemoteException;

    public void updateRow(T row) throws RemoteException;

    public void deleteRowById(String id) throws RemoteException;

    public ArrayList<T> findWithCustomQuery(String query) throws RemoteException;
}
