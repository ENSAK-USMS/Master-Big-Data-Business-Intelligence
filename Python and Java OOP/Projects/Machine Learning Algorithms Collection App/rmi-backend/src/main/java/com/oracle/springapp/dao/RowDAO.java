package com.oracle.springapp.dao;

import java.util.ArrayList;

import org.springframework.stereotype.Repository;

@Repository
public interface RowDAO<Row> {

    public ArrayList<Row> getAllRows();

    public Row insertRow(Row row);

    public Row findRowById(String id);

    public void updateRow(Row row);

    public void deleteRowById(String id);

    public ArrayList<Row> findWithCustomQuery(String query);

}
