import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;

public class jsonToJava {

    public static void main(String[] args) throws ClassNotFoundException, SQLException, IOException {

        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection conn;
        conn = null;
        conn=DriverManager.getConnection("jdbc:mysql://localhost:3306/Business", "root", "root");
        customerDetails c=new customerDetails();
        ArrayList<customerDetails> a=new ArrayList<customerDetails>();

        //object of statement class will execute sql queries
        Statement st=conn.createStatement();
        ResultSet rs=st.executeQuery("select * from CustomerInfo where purchasedDate=CURDATE() and Location ='Asia';");
/*        rs.next(); //setting pointer to a row
        rs.getString(1);
        rs.getString(2);
        rs.getInt(3);
        rs.getString(4);
        rs.next(); //setting pointer to a row
        rs.getString(1);
        rs.getString(2);
        rs.getInt(3);
        rs.getString(4);
        rs.next(); //setting pointer to a row
        rs.getString(1);
        rs.getString(2);
        rs.getInt(3);
        rs.getString(4);*/

        //get all rows

        while(rs.next()) {

            //System.out.println(rs.getString(1));
            c.setCourseName(rs.getString(1));
            //System.out.println(rs.getString(2));
            c.setPurchasedDate(rs.getString(2));
            //System.out.println(rs.getInt(3));
            c.setAmount(rs.getInt(3));
            //System.out.println(rs.getString(4));
            c.setLocation(rs.getString(4));

            a.add(c);


            System.out.println(c.getCourseName());
            System.out.println(c.getPurchasedDate());
            System.out.println(c.getAmount());
            System.out.println(c.getLocation());
       }
        for(int i=0;i<a.size();i++) {
            ObjectMapper o = new ObjectMapper();
            o.writeValue(new File("C:\\Users\\Owner\\IdeaProjects\\JsonJava_PojoPractice\\customerInfo"+i+".json"), a.get(i));
        }
        conn.close();



    }
}
