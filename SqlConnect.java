package MySqlJava;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Pedrum on 1/14/2017.
 */
public class SqlConnect {
    public Statement stmt;
    private DbParams dbParams;
    private Connection connection;
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

    public SqlConnect(DbParams __dbParams ) throws SQLException {
        dbParams = __dbParams;
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


    //__row is zero based
    public Object getValAtIndex(ResultSet __rs, String __column, int __row, TYPE __type ) throws SQLException{
        Object _output;

        for (int i = 0 ; i < __row; i ++){
            __rs.next();
        }
        if (__type == TYPE.DATE){
           // rs.getDate(__column);
          //  return _output;
        }
        else if (__type == TYPE.TIMESTAMP){
            _output=__rs.getTimestamp(__column);
            return _output;
        }

        else if (__type == TYPE.INT ){
            _output=__rs.getInt(__column);
            return _output;
        }

    return null;

    }

    public ResultSet executeQuery(String __query) throws SQLException{
        ResultSet _output;
        _output =  stmt.executeQuery(__query);
        return _output;
    }



    public void updateQuery(String __string) throws SQLException {

        stmt.execute(__string);

    }
    public void close() throws SQLException {
        stmt.close();
        connection.close();
    }

    public static List<String> getColumns(ResultSet __resultSet) throws SQLException {
        ResultSetMetaData _md = __resultSet.getMetaData();
        List<String> _output = new ArrayList<>();

        for (int i = 0; i < _md.getColumnCount();i++){
            _output.add(_md.getColumnName(i+1));
        }
        return _output;
    }

    public static List<String> getColumnTypes(ResultSet __resultSet) throws SQLException {
        ResultSetMetaData _md = __resultSet.getMetaData();
        List<String> _output = new ArrayList<>();

        for (int i = 0; i < _md.getColumnCount();i++){
            _output.add(_md.getColumnTypeName(i+1));
        }
        return _output;
    }

    public String insertQuery(ResultSet __resultSet, String __id, String __tableSource, String __tableDest) throws SQLException {
        String _query = "insert into " +dbParams.dbName + "." + __tableDest + "(";
        List<String> _columns = SqlConnect.getColumns(__resultSet);

        for (int i = 0; i < _columns.size();i++){
            if (!_columns.get(i).equals(__id)){
                _query += _columns.get(i) + ", ";
            }
        }
        _query = _query.substring(0, _query.length()-2);
        _query += ") ";
        _query += " select " ;
        for (int i = 0; i < _columns.size();i++){
            if (!_columns.get(i).equals(__id)){
                _query += _columns.get(i) + ", ";
            }
        }
        _query =  _query.substring(0, _query.length()-2);
        _query += " from " + dbParams.dbName + "." + __tableSource;
        return _query;
    }

    public String insertSingleQuery(ResultSet __resultSet, String __id, String __tableSource, String __tableDest) throws SQLException {
        String _query = "insert into " +dbParams.dbName + "." + __tableDest + "(";
        List<String> _columns = SqlConnect.getColumns(__resultSet);
        List<String> _types = SqlConnect.getColumnTypes(__resultSet);

        for (int i = 0; i < _columns.size();i++){
            if (!_columns.get(i).equals(__id)){
                _query += _columns.get(i) + ", ";
            }
        }
        _query = _query.substring(0, _query.length()-2);
        _query += ") ";
        _query += " values( " ;
        for (int i = 0; i < _columns.size();i++){
            if (!_columns.get(i).equals(__id)){
                if (_types.get(i).equals("VARCHAR")){
                    _query += "\"" +__resultSet.getObject(i+1) + "\"" + ", ";
                }
                else{
                    _query += __resultSet.getObject(i+1) + ", ";

                }
            }
        }
        _query =  _query.substring(0, _query.length()-2);
        _query += ")";
        return _query;

    }
}
