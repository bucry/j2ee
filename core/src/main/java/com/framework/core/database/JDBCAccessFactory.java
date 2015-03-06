package com.framework.core.database;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.sql.DataSource;

/**
 * 
 * @author bfc
 *
 */
public class JDBCAccessFactory {

    private  JDBCAccess masterAccess;
    
    private  List<JDBCAccess> slaveAccesses = new ArrayList<JDBCAccess>();

    private DataSource masterDataSource;
    
    private DataSource[] slaveDatasources;

    public JDBCAccessFactory(DataSource masterDataSource, DataSource ... slaveDatasources) {
        this.masterDataSource = masterDataSource;
        this.slaveDatasources = slaveDatasources;
    }
    
    @PostConstruct
    public void initialize() {
        slaveAccesses.clear();
        masterAccess = new JDBCAccess();
        masterAccess.setDataSource(masterDataSource);
        if (null !=slaveDatasources && slaveDatasources.length > 0) {
            for (int i =0; i < slaveDatasources.length; i++) {
                JDBCAccess slaveJDBCAccess = new JDBCAccess();
                slaveJDBCAccess.setDataSource(slaveDatasources[i]);
                slaveAccesses.add(slaveJDBCAccess);
            }
        }
    }
    
    public JDBCAccess getMasterJDBCAccess() {
        return masterAccess;
    } 

    public JDBCAccess getSlaveJDBCAccess() {
        return slaveAccesses.get(random(slaveAccesses.size()));
    }
    
    private int random(int length) {
        int radom = (int)(Math.random()*10);
        if (length == 1) return  0;
        if(radom < length) {
            return radom;
        }
        return random(length);
    }

}