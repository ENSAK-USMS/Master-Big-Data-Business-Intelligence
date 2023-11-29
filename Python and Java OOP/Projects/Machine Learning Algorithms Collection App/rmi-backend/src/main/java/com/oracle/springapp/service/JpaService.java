package com.oracle.springapp.service;

import java.util.ArrayList;

public interface JpaService<Row> {

    public ArrayList<Row> getAllRows();

    public Row insertRow(Row row);

    public Row findRowById(String id);

    public void updateRow(Row row);

    public void deleteRowById(String id);

    public ArrayList<Row> findWithCustomQuery(String query);

}