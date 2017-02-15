package MySqlJava;

/**
 * Created by Pedrum on 2/14/2017.
 */
public class dbParams {
    public String ip;
    public String password;
    public String dbName;

    public dbParams(String __ip, String __password, String __dbName){
        ip = __ip;
        password = __password;
        dbName =__dbName;
    }
}
