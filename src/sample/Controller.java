package sample;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;


import java.sql.*;

public class Controller {

    @FXML
    ComboBox comboBoxFrom; // fortæller controller om min

    @FXML
    ComboBox comboBoxTo;

    @FXML
    TextArea rejser;

    @FXML
    ComboBox comboBoxTimer;

    Connection conn;

    // skal ske ved start

    private Connection connect() {
        // SQLite connection string

        String url = "jdbc:sqlite:RejsePlanen123.sqlite";

        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

    public void selectAll() throws SQLException {
      String sql = "SELECT * FROM employees";
        Statement statement = conn.createStatement();
    ResultSet resultSet =  statement.executeQuery("SELECT * FROM stations");
        while (resultSet.next())
        {
           comboBoxFrom.getItems().add(resultSet.getString("name"));
            comboBoxTo.getItems().add(resultSet.getString("name"));
            //System.out.println(resultSet.getString("name"));
       }
    }

    @FXML
    public void setTimes(){
        for (int i = 0; i < 13; i++){
            comboBoxTimer.getItems().add(i + ".00" );
        }
    }

    @FXML
    public void initialize() throws SQLException {
        connect();
        selectAll();
        setTimes();

        //comboBoxTo.getItems().addAll(getAllStations());
        //comboBoxFrom.getItems().addAll(getAllStations());

    }

    public void checkRejse() throws SQLException{
        Statement statement = conn.createStatement();
        String sFrom = String.valueOf(comboBoxFrom.getValue());
        String sTo = String.valueOf(comboBoxTo.getValue());
        ResultSet resultSet =  statement.executeQuery("SELECT * FROM Rejser where fratid > " + comboBoxTimer.getValue() + " AND afgangsStation = '" + sFrom + "' AND ankomstStation = '" + sTo + "'");
        while (resultSet.next()) {
            rejser.setText(rejser.getText() + "Fra " + resultSet.getString(3) + " klokken " + resultSet.getString(4) + " Til " + resultSet.getString(5) + " klokken " + resultSet.getString(6));

        }
        rejser.setWrapText(true);
    }


}



//variable for databasen
//make query to database- make it into a string - return string

