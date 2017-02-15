package MySqlJava;

import MySqlJava.dbParams;

import java.sql.*;

/**
 * Created by Pedrum on 1/14/2017.
 */
public class SqlConnect {
    public Statement stmt;
    private Connection connection;
    private ResultSet rs;
    public enum SQLRET{
        SUCCESS,
        FAIL
    }

    public enum TYPE{
        DATE,
        INT,
        STRING,
        TIMESTAMP
    }


    public SqlConnect(dbParams __dbParams ) throws SQLException {

        try{
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://" + __dbParams.ip + ":3306/" +__dbParams.dbName+"?useSSL=false", "root", __dbParams.password);
            stmt = connection.createStatement();
            stmt.setQueryTimeout(360000);
        }
        catch(java.sql.SQLException ex) {
            System.out.println("Database not connected!");
            throw ex;
        }
        catch(ClassNotFoundException ex){
            System.out.println(ex);

        }
    }



    public Object getValAtIndex(String __column, int __row, TYPE __type ) throws SQLException{
        Object _output;
      //  if (rs.next()==false){
        //    return null;
        //}
        //_output = Calendar.getInstance() ;
        for (int i = 0 ; i < __row; i ++){
            rs.next();
        }
        if (__type == TYPE.DATE){
           // rs.getDate(__column);
          //  return _output;
        }
        else if (__type == TYPE.TIMESTAMP){
            _output=rs.getTimestamp(__column);
            return _output;
        }

return null;

    }

    public ResultSet getQuery(String __query) throws SQLException{
        ResultSet _output;
        _output =  stmt.executeQuery(__query);
        return _output;
    }

    public SQLRET execGetQueryIndex(String __query){

        try{
            rs =  stmt.executeQuery(__query);
            if(rs.next()){
                return SQLRET.SUCCESS;
            }
            else{
                return SQLRET.FAIL;
            }

        }
        catch(java.sql.SQLException ex) {
            System.out.println(ex);
            return SQLRET.FAIL;
        }
    }

    public SQLRET updateQuery(String __string){


            try{
                 stmt.execute(__string);
                return SQLRET.SUCCESS;
            }
            catch(java.sql.SQLException ex) {
                System.out.println(ex);
                return SQLRET.FAIL;
            }

    }
    public void close() throws SQLException {

        stmt.close();
        connection.close();


    }
}
