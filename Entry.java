import java.sql.*;
import java.util.Scanner;

public class Entry {
    private static final Scanner S = new Scanner(System.in);

    private static Connection c = null;
    private static ResultSet rs = null;

    public static void main(String[] args) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            c = DriverManager.getConnection("jdbc:mysql://localhost:3306/cmppflem", "root", ""); // ToDo : Specify Connection String !
            Statement s = c.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);

            rs = s.executeQuery("SELECT `code`, `date`, room\n" + " FROM session\n" + " WHERE `date` >= CURRENT_DATE()AND `date` <= DATE_ADD(CURRENT_DATE(), INTERVAL 1 YEAR) AND (room IS NULL);"); // ToDo : Specify SELECT Statement !

            String choice = "";

            do {
                System.out.println("-- MAIN MENU --");
                System.out.println("1 - Browse ResultSet");
                System.out.println("2 - Invoke Procedure");
                System.out.println("Q - Quit");
                System.out.print("Pick : ");

                choice = S.next().toUpperCase();

                switch (choice) {
                    case "1" : {
                        browseResultSet();
                        break;
                    }
                    case "2" : {
                        invokeProcedure();
                        break;
                    }
                }
            } while (!choice.equals("Q"));

            c.close();

            System.out.println("Bye Bye :)");
        }
        catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    private static void browseResultSet() throws Exception {
       if (rs.first()){
          System.out.println("-- BROWSE RESULTSET --");
          
          do{
             System.out.println(String.format("Row : %s   code : %s   date : %s   room : %s", rs.getRow(), rs.getString("code"), rs.getString("date"), rs.getByte("room")));
          } while (rs.next());
       }
       else {
           System.out.println("-- NO DATA --");
           
       
        
        }
        // ToDo : Iterate Through ResultSet's Rows !
    }
    
   
    private static void invokeProcedure() throws Exception {
        
        
         System.out.println("-- INVOKE PROCEDURE --");
        
        CallableStatement cs = c.prepareCall("{CALL assign_schedule(?,?)} ") ;
        System.out.println("Assign the code");
       
       
      
        
        System.out.println("Specify code of the course");
        String course_code = S.next();
        System.out.println("Specify Date of Course");
        Date course_date = Date.valueOf(S.next());
        
        cs.setString("assign_code", course_code);
        cs.setDate("assign_date", course_date);
        
        
       System.out.println("-- Success --");
        
        
        
        cs.executeUpdate();
        // ToDo : Accept Course Code & Start Date !

        // ToDo : Declare, Configure & Invoke CallableStatement !
    }
}
