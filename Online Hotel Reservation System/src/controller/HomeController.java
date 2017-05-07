package controller;


import app.DBmanager;
import app.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import sun.util.resources.LocaleData;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class HomeController implements Initializable{


   public static LocalDate fro;
   public static LocalDate to_d;
    public static ResultSet room;
    @FXML private Button login;
     @FXML private Button register;
     @FXML private ChoiceBox country;
     @FXML private ChoiceBox state;
    @FXML private ChoiceBox type;
    @FXML private DatePicker from;
    @FXML private DatePicker to;
    @FXML private Button search;
    @FXML private Pane pane;
     @Override
        public void initialize(URL url,ResourceBundle resourceBundle){

         register.setOnAction(event -> {

             Scene regi= null;
             try {
                 regi = new Scene(FXMLLoader.load(getClass().getResource("/view/entry.fxml")));
             } catch (IOException e) {
                 e.printStackTrace();
             }
             Main.windows.setScene(regi);
             Main.windows.show();


         });
    try {
        ObservableList<String> count = FXCollections.observableArrayList();
        Connection con = DBmanager.getconnection();
        Statement st = con.createStatement();
         ResultSet cou = st.executeQuery("select distinct country from hotel");
         while(cou.next()){
             String temp=cou.getString("country");
             count.add(temp);
         }
         country.setItems(count);

        ObservableList<String> sta = FXCollections.observableArrayList();
        ResultSet stat = st.executeQuery("select distinct state from hotel");
        while(stat.next()){
            String temp=stat.getString("state");
            sta.add(temp);
        }
        state.setItems(sta);

        ObservableList<String> typ = FXCollections.observableArrayList();
        ResultSet ty = st.executeQuery("select distinct Type from room");
        while(ty.next()){
            String temp=ty.getString("Type");
            typ.add(temp);
        }
        type.setItems(typ);

        search.setOnAction(event -> {
            fro=from.getValue();
            to_d=to.getValue();
           if(!to_d.isAfter(fro)){
               Alert alert=new Alert(Alert.AlertType.WARNING);
               alert.setTitle("valid date");
               alert.setHeaderText(null);
               alert.setContentText("please enter valid date period");
               alert.showAndWait();
           }
           String country_s= (String) country.getValue();
           String state_s= (String) state.getValue();
           String type_s= (String) type.getValue();


         String check=  "select * from room r1 where r1.HotelID=(select HotelID from hotel h1 where h1.Country='"+country_s+"'  and h1.State='"+state_s+"') and r1.type='"+type_s+"' and (r1.HotelID,r1.Room_no) not in (select HotelID,Room_no from reserves s1 where s1.HotelID=r1.HotelID and s1.Room_no=r1.Room_no and  !(s1.InDate<'"+to_d+"' or s1.OutDate >'"+fro+"'))";
            try {


                room=st.executeQuery(check);
                ResultSetMetaData rsm=room.getMetaData();
                int columnsNumber=rsm.getColumnCount();
                Scene hotlist=new Scene(FXMLLoader.load(getClass().getResource("/view/hotellist.fxml")));
                Main.windows.setScene(hotlist);
                Main.windows.show();

 } catch (SQLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }


        });





    }catch (SQLException e){
        e.printStackTrace();

    }










     }


}


