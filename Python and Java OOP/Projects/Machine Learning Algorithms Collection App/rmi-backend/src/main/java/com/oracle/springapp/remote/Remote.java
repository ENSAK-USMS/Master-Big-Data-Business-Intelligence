package com.oracle.springapp.remote;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import com.oracle.springapp.service.JpaService;

public class Remote<T> extends UnicastRemoteObject implements RemoteInterface<T> {

    JpaService<T> myService;

    public Remote(JpaService<T> Service) throws RemoteException {
        super();
        this.myService = Service;
    }

    @Override
    public T findRowById(String username) throws RemoteException {
        T acc = myService.findRowById(username);
        return acc;
    }

    @Override
    public T insertRow(T row) throws RemoteException {
        return myService.insertRow(row);
    }

    @Override
    public void updateRow(T row) throws RemoteException {
        myService.updateRow(row);
    }

    @Override
    public void deleteRowById(String id) throws RemoteException {
        myService.deleteRowById(id);
    }

    @Override
    public java.util.ArrayList<T> getAllRows() throws RemoteException {
        return myService.getAllRows();
    }

    @Override
    public java.util.ArrayList<T> findWithCustomQuery(String query) throws RemoteException {
        return myService.findWithCustomQuery(query);
    }

}
