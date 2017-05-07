package controller;

import app.DBmanager;
import app.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * Created by Administrator on 2017/5/6.
 */
public class hotellist_control implements Initializable{

      public class hid_rid{
           public String hot_id;
           public String room_no;
       }

       public static ArrayList<hid_rid> store=new ArrayList<hid_rid>();

@FXML private Pane pane;
@FXML private Button book;


          ResultSet need=HomeController.room;

    public void initialize(URL url, ResourceBundle resourceBundle){
        CheckBox[] checkBoxes=new CheckBox[30];
        GridPane grid=new GridPane();
        pane.getChildren().add(grid);
        ResultSetMetaData rsm= null;
        int columnsNumber=0;
        try {
            rsm = need.getMetaData();
            columnsNumber=rsm.getColumnCount();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        int counter=0;
        try {
            while(need.next()){
                String temp="";
                String hot_id=need.getString("hotelid");
                String room_no=need.getString("room_no");
                Connection con= DBmanager.getconnection();
                Statement sta=con.createStatement();
                String check_discount="select * from offer_room o where o.hotelid='"+hot_id+"'and o.room_no='"+room_no+"'and (o.sdate<'"+HomeController.to_d+"' and o.edate >'"+HomeController.fro+"')";
                ResultSet st=sta.executeQuery(check_discount);st.next();
                String boo="";
                try{ boo= st.getString("discount");}
                catch (Exception e){System.out.print("empty set");}
                for (int i = 1; i <= columnsNumber; i++) {
                    if (i > 1) temp+=",";
                        temp+= need.getString(i);
                    temp+=rsm.getColumnName(i);

                    }
                if(boo!=""){
                    temp+="  this room has discount for %"+boo+"";
                }
                checkBoxes[counter]=new CheckBox(temp);
                grid.addRow(counter,checkBoxes[counter]);
                counter++;

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        book.setOnAction((ActionEvent event) -> {

             for(int i=0;i<30;i++){
                 if(checkBoxes[i]==null){
                     continue;
                 }
                 if(checkBoxes[i].isSelected()){
                     String tmp=checkBoxes[i].getText();
                     tmp.toCharArray();
                     String hot_id="";
                     String room_no="";
                     for(int a=0;a<tmp.length();a++){
                         if(tmp.charAt(a)!='H'){
                           hot_id+=tmp.charAt(a);
                         }
                         else
                             break;
                     }
                        int trigger=0;
                     for(int a=0;a<tmp.length();a++){
                         if(tmp.charAt(a)==','){
                             trigger=1;
                             continue;
                         }
                         if(trigger==1&&tmp.charAt(a)!='R'){
                             room_no+=tmp.charAt(a);
                             continue;
                         }
                         if(tmp.charAt(a)=='R'){
                             break;
                         }

                     }


                     hid_rid suck=new hid_rid();
                     suck.hot_id=hot_id;
                     suck.room_no=room_no;
                     store.add(suck);

                 }





             }

            try {

                 Scene lp=new Scene(FXMLLoader.load(getClass().getResource("/view/breakfast.fxml")));
                Main.windows.setScene(lp);
                Main.windows.show();
             } catch (IOException e) {
                e.printStackTrace();
            }




        });





    }








}




