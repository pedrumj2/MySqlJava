package MySqlJava; /**
 * Created by Pedrum on 1/15/2017.
 */
import java.sql.*;

public class Chunk {
    private SqlConnect sqlConnect;
    private int CHUNK = 1000;
    private ResultSet resultSet;
    private int chunkIndex;

    public Chunk(dbParams __dbParams) throws SQLException{
        sqlConnect = new SqlConnect(__dbParams);
        chunkIndex =0;
        resultSet = getChunk();

    }

    //gets the ith chunk of values from the main table
    private ResultSet getNext(int __ith) throws SQLException{
        if (resultSet.next()==false){
            resultSet = getChunk();
            if (resultSet.next() ==false){
                return null;
            }
        }
       return resultSet;
    }

    //gets the ith chunk of values from the main table
    private ResultSet getChunk() throws SQLException{
        ResultSet _resultSet;
        String _query = "SELECT * FROM D12.labels limit " + CHUNK*chunkIndex + " , " + CHUNK;
        _resultSet = sqlConnect.getQuery(_query);
        chunkIndex++;
        return _resultSet;
    }





}
