package controller;

import app.DBmanager;
import com.sun.org.apache.regexp.internal.RE;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;

import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * Created by Administrator on 2017/5/7.
 */
public class breakfast implements Initializable{

    @FXML private ChoiceBox breakfast1;
    @FXML private ChoiceBox service1;
    @FXML private Button next;

    ArrayList<hotellist_control.hid_rid> need=hotellist_control.store;
    public void initialize(URL url, ResourceBundle resourceBundle){

        ObservableList<String> breakfast = FXCollections.observableArrayList();
        ObservableList<String> service = FXCollections.observableArrayList();
        Connection con= DBmanager.getconnection();
        Statement st = null;
        try {
             st=con.createStatement();

            for(int a=0;a<need.size();a++){
                String hid=need.get(a).hot_id;
                ResultSet hid_s=st.executeQuery("select distinct btype from breakfast b1 where b1.hotelid ='"+hid+"'");
                while(hid_s.next()){
                    String temp=hid_s.getString("btype");
                    breakfast.add(temp);
                }
                ResultSet serv=st.executeQuery("select distinct stype from service s1 where s1.hotelid ='"+hid+"'");
                while(serv.next()){
                    String temp=serv.getString("stype");
                    service.add(temp);
                }

                   breakfast1.setItems(breakfast);
                   service1.setItems(service);

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

            next.setOnAction(event -> {

                String brf= (String) breakfast1.getValue();
                String serv= (String) service1.getValue();
                Statement ka=con.createStatement();
                try {
                    Statement ka=con.createStatement();
                    ResultSet aa=ka.executeQuery("select bprice from breakfast b1 where b1.btype='"+brf+"'");
                    ResultSet bb=ka.executeQuery("select scost from service s1 where s1.stype='"+serv+"'");
                    aa.next();bb.next();
                    int bprice= Integer.parseInt(aa.getString("bprice"));
                    int sprice= Integer.parseInt(aa.getString("scost"));






                } catch (SQLException e) {
                    e.printStackTrace();
                }










            });




    }









}
