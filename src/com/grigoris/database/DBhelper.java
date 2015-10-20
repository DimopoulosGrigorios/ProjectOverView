package com.grigoris.database;



import java.sql.Connection;

import java.sql.PreparedStatement;

import java.sql.ResultSet;

import java.sql.SQLException;

import java.sql.Statement;

import javax.naming.InitialContext;

import javax.sql.DataSource;

 

import com.vaadin.external.org.slf4j.Logger;

import com.vaadin.external.org.slf4j.LoggerFactory;

 

public class DBhelper {

       private static final String DATASOURCE = "java:jboss/datasources/GNT_TA";

       private static final String testQuery = "SELECT 1 FROM DUAL";

       private static DataSource datasource;

       private final static Logger logger = LoggerFactory.getLogger(DBhelper.class);

      

       static{

              try {

                     InitialContext ic = new InitialContext();

                     datasource = (DataSource)ic.lookup(DATASOURCE);

              } catch (Exception e) {

                     e.printStackTrace();

              }

       }

      

 

       public static void rollConnBack(Connection conn, String classFunctionName) {

              try {

                     conn.rollback();

              } catch (SQLException e) {

                     logger.error("Error occurred during the rollback procedure in the database @ " + classFunctionName + " \n", e);

              }

       }

 

       /**

        * Method returns a Connection object so as to connect to database

        *

        * @return Connection

        */

       public static Connection getConnection() {

             

              Connection connection = null;

             

              if (datasource == null) {

                     logger.error("Datasource is null");

              } else {

                     try {

                           connection = datasource.getConnection();

                     } catch (SQLException e) {

                           logger.error("Could not retrieve Connection Object from DataSource.", e);

                     }

              }

 

              return connection;

       }

 

       public static boolean validate(Object o) {

              try {

                     if (o == null) return false;

                     Connection con = (Connection) o;

                     if (con.isClosed()) return false;

 

                     Statement stat = con.createStatement();

                     stat.executeQuery(testQuery);

                     return true;

              } catch (SQLException e) {

                     logger.error("DBHelper.validate error", e);

                     return false;

              }

       }

 

       /**

        * Method releases the specific Resource (Connection,PreparedStatement or ResultSet)

        *

        * @param obj The resource to be closed.

        */

       public static void close(Object obj)

       {

              if (obj != null) {

                     if (obj instanceof Connection) {

                           try {

                                  ((Connection) obj).close();

                           } catch (SQLException e) {

                                  logger.error("Close connection error", e);

                           }

                     } else if (obj instanceof PreparedStatement) {

 

                           try {

                                  ((PreparedStatement) obj).close();

                           } catch (SQLException e) {

                                  logger.error("Close PreparedStatement error", e);

                           }

                     } else if (obj instanceof ResultSet) {

 

                           try {

                                  ((ResultSet) obj).close();

                           } catch (SQLException e) {

                                  logger.error("Close ResultSet error", e);

                           }

                     } else if (obj instanceof Statement) {

 

                           try {

                                  ((Statement) obj).close();

                           } catch (SQLException e) {

                                  logger.error("Close Statement error", e);

                           }

                     }

              }

       }

}

 
