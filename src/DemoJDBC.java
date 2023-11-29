
import java.net.SocketOption;
import java.sql.*;
import java.util.Scanner;

public class DemoJDBC {

    public static void printName(String url,String userName,String password,String sqlQuery) throws SQLException{

        ResultSet rs;

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
        
        Connection conn = DriverManager.getConnection(url, userName, password);
        Statement st=conn.createStatement();
        ResultSet rs=st.executeQuery(sqlQuery);

        while(rs.next()){
            System.out.println(rs.getInt(1)+" : "+rs.getString(2)+" : "+rs.getInt(3));
        }

    }

    public static void addDetails(String url,String userName,String password,String sqlQuery) throws SQLException{
        Connection conn=DriverManager.getConnection(url,userName,password);
        Statement st=conn.createStatement();
        st.execute(sqlQuery);
    }

    private static void updateDetails(String url, String userName, String password, String sqlQuery) throws SQLException{
        Connection conn=DriverManager.getConnection(url,userName,password);
        Statement st=conn.createStatement();
        st.execute(sqlQuery);
    }

    private static void deleteDetails(String url, String userName, String password, String sqlQuery) throws SQLException{
        Connection conn=DriverManager.getConnection(url,userName,password);
        Statement st=conn.createStatement();
        st.execute(sqlQuery);
    }


    public static void main(String[] args) throws SQLException {

        Scanner sc=new Scanner(System.in);

        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        String url = "jdbc:postgresql://localhost:5432/demo";
        String userName = "postgres";
        String password = "root";

        System.out.println("Connection Created");

        while(true){

            System.out.println("1. Add Details");
            System.out.println("2. Read Details");
            System.out.println("3. Update Details");
            System.out.println("4. Delete Details");

            int choice=sc.nextInt();

            if(choice==1){
                System.out.println("Enter sid Number");
                int newSid=sc.nextInt();
                System.out.println("Enter the name");
                String newName=sc.next();
                System.out.println("Enter the mark");
                int newMark=sc.nextInt();
                String query = "insert into student values(" + newSid + ", '" + newName + "', " + newMark + ")";
                addDetails(url,userName,password,query);
                System.out.println("Successfully Details Added");
            }
            else if(choice==2){
                System.out.println("All the details are..");
                String sqlQuery="select * from student";
                printAllNames(url,userName,password,sqlQuery);
            }
            else if(choice==3){
                System.out.println("Enter the name to change");
                String newName=sc.next();
                System.out.println("Enter the respective sid Number");
                int sId=sc.nextInt();
                String query = "update student set name='" + newName + "' where sid=" + sId;
                updateDetails(url,userName,password,query);
                System.out.println("Details Succesfully Updated");
            }
            else if(choice ==4){
                System.out.println("Enter the sid Number to delete the details");
                int Sid=sc.nextInt();
                String query="delete from student where sid="+Sid;
                deleteDetails(url,userName,password,query);
                System.out.println("Details Succesfully deleted");
            }else break;

        }

    }

}
