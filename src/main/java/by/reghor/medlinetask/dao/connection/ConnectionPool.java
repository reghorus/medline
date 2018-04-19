package by.reghor.medlinetask.dao.connection;

import by.reghor.medlinetask.exception.MedlineException;
import by.reghor.medlinetask.util.Constants;
import by.reghor.medlinetask.util.PropertiesUtil;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public final class ConnectionPool {

    private static BlockingQueue<PooledConnection> connectionPool;

    private static volatile transient ConnectionPool instance;

    private ConnectionPool() {

    }

    public static ConnectionPool getInstance() {
        ConnectionPool localRefIntance = instance;
        if (localRefIntance == null) {
            synchronized (ConnectionPool.class) {
                localRefIntance = instance;
                if (localRefIntance == null) {
                    localRefIntance = initPool();
                    instance = localRefIntance;
                }
            }
        }
        return localRefIntance;
    }

    private static ConnectionPool initPool() {
        ConnectionPool localInstance = new ConnectionPool();
        int poolSize = Integer.parseInt(PropertiesUtil.getProperty(Constants.DB_POOL_SIZE_PROP));
        String driverClassName = PropertiesUtil.getProperty(Constants.DB_CLASS_NAME_PROP);
        try {
            Class.forName(driverClassName);
        } catch (ClassNotFoundException e) {
            throw new MedlineException(String.format("Can not load class ", driverClassName), e);
        }
        String username = PropertiesUtil.getProperty(Constants.DB_USERNAME_PROP);
        String password = PropertiesUtil.getProperty(Constants.DB_PASSWORD_PROP);
        String url = PropertiesUtil.getProperty(Constants.DB_URL_PROP);

        connectionPool = new ArrayBlockingQueue<>(poolSize);
        for (int i = 0; i < poolSize; i++) {
            Connection jdbcConnection = null;
            try {
                jdbcConnection = DriverManager.getConnection(url, username, password);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            PooledConnection pooledConnection = new PooledConnection(jdbcConnection, localInstance);
            connectionPool.offer(pooledConnection);
        }
        return localInstance;
    }

    public void put(PooledConnection connection) {
        connectionPool.offer(connection);
    }

    public void shutDown() {
        for (PooledConnection pooledConnection : connectionPool) {
            try {
                pooledConnection.realClose();
            } catch (Exception e) {

            }
        }
    }

    public PooledConnection getConnection() {
        PooledConnection connection;
        try {
            connection = connectionPool.take();
        } catch (Exception e) {
            throw new MedlineException("Can not get a pooled connection", e);
        }
        return connection;
    }

}
