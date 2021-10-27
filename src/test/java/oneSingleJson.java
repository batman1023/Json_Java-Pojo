import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import org.apache.commons.text.StringEscapeUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;

public class oneSingleJson {

    public static void main(String[] args) throws ClassNotFoundException, SQLException, IOException {

        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection conn;
        conn = null;
        conn=DriverManager.getConnection("jdbc:mysql://localhost:3306/Business", "root", "root");

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
            customerDetails c=new customerDetails();
            //System.out.println(rs.getString(1));
            c.setCourseName(rs.getString(1));
            //System.out.println(rs.getString(2));
            c.setPurchasedDate(rs.getString(2));
            //System.out.println(rs.getInt(3));
            c.setAmount(rs.getInt(3));
            //System.out.println(rs.getString(4));
            c.setLocation(rs.getString(4));

            a.add(c);


            /*System.out.println(c.getCourseName());
            System.out.println(c.getPurchasedDate());
            System.out.println(c.getAmount());
            System.out.println(c.getLocation());*/
       }
        JSONArray js = new JSONArray();
        for(int i = 0; i<a.size(); i++)
        {
            ObjectMapper o = new ObjectMapper();
            o.writeValue(new File("C:\\Users\\Owner\\IdeaProjects\\JsonJava_PojoPractice\\customerInfo"+i+".json"), a.get(i));
            Gson g=new Gson();
            String jsonString=g.toJson(a.get(i));
            js.add(jsonString);

        }

        JSONObject jo=new JSONObject();
        jo.put("data",js);
        System.out.println(jo.toJSONString());
        String unescapeString=StringEscapeUtils.unescapeJava(jo.toJSONString());
        System.out.println(unescapeString);
        String string1=unescapeString.replace("\"{", "{");
        String finalString=string1.replace("}\"","}");
        System.out.println(finalString);

        try(FileWriter file = new FileWriter("C:\\Users\\Owner\\IdeaProjects\\JsonJava_PojoPractice\\SingleJson.json")) {
            file.write(finalString);
        }






        conn.close();



    }
}
