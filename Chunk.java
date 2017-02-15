package MySqlJava;
import java.sql.*;

public class Chunk {
    private SqlConnect sqlConnect;
    private int CHUNK;
    private ResultSet resultSet;
    private int chunkIndex;
    private String query;

    public Chunk(dbParams __dbParams, String __query, int __chunkSize) throws SQLException{
        sqlConnect = new SqlConnect(__dbParams);
        CHUNK=__chunkSize;
        chunkIndex =0;
        query = __query;
        resultSet = getChunk();
    }

    //gets the ith chunk of values from the main table
    public ResultSet next() throws SQLException{
        if (!resultSet.next()){
            resultSet = getChunk();
            if (!resultSet.next()){
                return null;
            }
        }
       return resultSet;
    }

    //gets the ith chunk of values from the main table
    private ResultSet getChunk() throws SQLException{
        ResultSet _resultSet;
        String _query = query + " limit " + CHUNK*chunkIndex + " , " + CHUNK;
        _resultSet = sqlConnect.executeQuery(_query);
        chunkIndex++;
        return _resultSet;
    }
}
