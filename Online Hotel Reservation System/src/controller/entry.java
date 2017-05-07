package controller;

import app.DBmanager;
import app.Main;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.*;
import java.util.Calendar;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Administrator on 2017/5/6.
 */
public class entry implements Initializable {

    private @FXML TextField name;
    private @FXML TextField phone;
    private @FXML TextField address;
    private @FXML TextField email;
    private @FXML Button submit;
    private @FXML Button update;

    public void initialize(URL url, ResourceBundle resourceBundle) {


             submit.setOnAction(event -> {

                 Connection con = DBmanager.getconnection();
                 String name_t = name.getText();
                 String phone_t = phone.getText();
                 String address_t = address.getText();
                 String email_t = email.getText();
                 Statement st;

                 if (validemail()&&validphone()&&checkexist()) {

                     try {
                         st = con.createStatement();
                         String get_max_id = "Select MAX(CID) from customer";
                         int cid;

                         ResultSet max = st.executeQuery(get_max_id);
                         max.next();
                         cid = max.getInt("MAX(CID)");
                         cid += 1;


                         try {


                             PreparedStatement sta = con.prepareStatement("insert into customer(name,phone_no,email,cid,address) values (?,?,?,?,?)");


                             sta.setString(1, name_t);
                             sta.setString(2, phone_t);
                             sta.setString(3, email_t);
                             sta.setString(4, "" + cid);
                             sta.setString(5, address_t);
                             sta.executeUpdate();

                         } catch (SQLException e) {
                             e.printStackTrace();
                         }


                     } catch (SQLException e) {

                         e.printStackTrace();
                     }
                 }
             });

             update.setOnAction(event -> {

                 Connection con = DBmanager.getconnection();
                 String name_t = name.getText();
                 String phone_t = phone.getText();
                 String address_t = address.getText();
                 String email_t = email.getText();
                 Statement st= null;
                 try {
                     st = con.createStatement();
                 } catch (SQLException e) {
                     e.printStackTrace();
                 }
                 if(validemail()&&validphone()) {
                  try {
                      String find = "delete from customer where Phone_no=" + phone_t + "";
                      st.execute(find);
                      String get_max_id="Select MAX(CID) from customer";
                      ResultSet max=st.executeQuery(get_max_id);
                      max.next();
                      int cid;
                      try{
                          cid=max.getInt("MAX(CID)");
                          cid+=1;
                      }
                      catch(Exception e){
                          cid=1;
                      }
                      try {


                          PreparedStatement sta = con.prepareStatement("insert into customer(name,phone_no,email,cid,address) values (?,?,?,?,?)");


                          sta.setString(1, name_t);
                          sta.setString(2, phone_t);
                          sta.setString(3, email_t);
                          sta.setString(4, "" + cid);
                          sta.setString(5, address_t);
                          sta.executeUpdate();
                      } catch (SQLException e) {
                          e.printStackTrace();
                      }






                  } catch (SQLException e) {

                      e.printStackTrace();
                  }
              }

             });










    }

private boolean validemail( ){
        Pattern p=Pattern.compile("[a-zA-Z0-9][a-zA-Z0-9._]*@[a-zA-Z0-9]+([.][a-zA-Z]+)+");
    Matcher m=p.matcher(email.getText());
    if(m.find()&&m.group().equals(email.getText())){
        return true;
    }
    else {
        Alert alert=new Alert(Alert.AlertType.WARNING);
        alert.setTitle("vilidata email");
        alert.setHeaderText(null);
        alert.setContentText("please enter valid email");
        alert.showAndWait();
        return false;
    }
}
    private boolean validphone( ){
        Pattern p=Pattern.compile("[0-9]*");
        Matcher m=p.matcher(phone.getText());
        if(m.find()&&m.group().equals(phone.getText())){
            return true;
        }
        else {
            Alert alert=new Alert(Alert.AlertType.WARNING);
            alert.setTitle("vilidata phone");
            alert.setHeaderText(null);
            alert.setContentText("please enter valid phone");
            alert.showAndWait();
            return false;
        }
    }
    private boolean checkexist(){
       try {
           String find_cus = "SELECT count(*) FROM customer WHERE phone_no =" + phone.getText() + "";
           Connection con = DBmanager.getconnection();
           Statement st = con.createStatement();
           ResultSet exi = st.executeQuery(find_cus);
           exi.next();
           int s=exi.getInt("count(*)");
           if(s==0){
               return true;

           }
           else{
               Alert alert=new Alert(Alert.AlertType.WARNING);
               alert.setTitle("you have already register");
               alert.setHeaderText(null);
               alert.setContentText("you have already register");
               alert.showAndWait();
               return false;

           }

       }
       catch (SQLException e){
           e.printStackTrace();
       }

return false;
    }



}
