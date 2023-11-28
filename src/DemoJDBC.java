
import java.net.SocketOption;
import java.sql.*;
public class DemoJDBC {

    public static void printName(String url,String userName,String password,String sqlQuery) throws SQLException{

        ResultSet rs;

        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        Connection conn;
        try {
            conn = DriverManager.getConnection(url, userName, password);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        try {
            Statement st = conn.createStatement();
            rs=st.executeQuery(sqlQuery);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        rs.next(); // used to initiate the postgres Pointer
        String name=rs.getString("name");
        System.out.println("Name of the Student is "+name);

        try {
            conn.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public static void printAllNames(String url,String userName,String password,String sqlQuery) throws SQLException{

        ResultSet rs;
        Connection conn = DriverManager.getConnection(url, userName, password);
        Statement st=conn.createStatement();
        rs=st.executeQuery(sqlQuery);

        while(rs.next()){
            System.out.println(rs.getInt(1)+" : "+rs.getString(2)+" : "+rs.getInt(3));
        }

    }
    public static void main(String[] args) throws SQLException {

        String url = "jdbc:postgresql://localhost:5432/demo";
        String userName = "postgres";
        String password = "root";

        String sqlQuery1="select name from student where sid=2";
        String sqlQuery2="select * from student";

        printName(url,userName,password,sqlQuery1);
        printAllNames(url,userName,password,sqlQuery2);
    }
}
