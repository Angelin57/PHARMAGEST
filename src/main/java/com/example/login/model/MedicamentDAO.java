package com.example.login.model;


import com.example.login.connection;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MedicamentDAO {
    private static final connection databaseConnection = new connection();

    public static Medicament searchMedicament(String empId) throws SQLException, ClassNotFoundException {
        String selectStmt = "SELECT * FROM public.\"Medicaments\" WHERE \"id_medicament\"=" + empId;

        try {
            Connection connection = databaseConnection.getConnection();

            ResultSet rsEmp = connection.createStatement().executeQuery(selectStmt);

            Medicament medicament = getMedicamentFromResultSet(rsEmp);

            return medicament;
        } catch (SQLException e) {
            System.out.println("While searching an employee with " + empId + " id, an error occurred: " + e);
            throw e;
        }
    }

    private static Medicament getMedicamentFromResultSet(ResultSet rs) throws SQLException {
        Medicament medicament = null;
        if (rs.next()) {
            int id_medicament = rs.getInt("id_medicament");
            String nom = rs.getString("nom");
            String forme = rs.getString("forme");
            String famille = rs.getString("famille");
            int prix = rs.getInt("prix");
            int quantite = rs.getInt("quantite");

            medicament = new Medicament(
                    new SimpleIntegerProperty(id_medicament),
                    new SimpleStringProperty(nom),
                    new SimpleStringProperty(forme),
                    new SimpleStringProperty(famille),
                    new SimpleIntegerProperty(prix),
                    new SimpleIntegerProperty(quantite)

            );
        }
        return medicament;
    }


    //*******************************
    //SELECT medicaments
    //*******************************
    public static ObservableList<Medicament> searchMedicaments() throws SQLException, ClassNotFoundException {
        String selectStmt = "SELECT * FROM public.\"Medicaments\"";

        try {
            Connection connection = databaseConnection.getConnection();

            ResultSet rsEmps = connection.createStatement().executeQuery(selectStmt);

            ObservableList<Medicament> empList = getMedicamentList(rsEmps);

            return empList;
        } catch (SQLException e) {
            System.out.println("SQL select operation has been failed: " + e);
            throw e;
        }
    }

    private static ObservableList<Medicament> getMedicamentList(ResultSet rs) throws SQLException, ClassNotFoundException {
        ObservableList<Medicament> medicamentList = FXCollections.observableArrayList();

        while (rs.next()) {
            IntegerProperty idProperty = new SimpleIntegerProperty(rs.getInt("id_medicament"));
            StringProperty nomProperty = new SimpleStringProperty(rs.getString("nom"));
            StringProperty formeProperty = new SimpleStringProperty(rs.getString("forme"));
            StringProperty familleProperty = new SimpleStringProperty(rs.getString("famille"));
            IntegerProperty prixProperty = new SimpleIntegerProperty(rs.getInt("prix"));
            IntegerProperty quantiteProperty = new SimpleIntegerProperty(rs.getInt("quantite"));



            Medicament medicament = new Medicament(idProperty, nomProperty, formeProperty, familleProperty, prixProperty,quantiteProperty);


            medicamentList.add(medicament);
        }


        return medicamentList;
    }

    //*************************************
    //UPDATE an medicament's prix
    //*************************************
    public static void updatePrix (String medId, String medPrix) throws SQLException, ClassNotFoundException {

        String updateStmt =
                "UPDATE public.\"Medicaments\"\n" +
                        "      SET \"prix\" = '" + medPrix + "'\n" +
                        "    WHERE \"id_medicament\" = " + medId + ";\n";


        try {
            connection.dbExecuteUpdate(updateStmt);
        } catch (SQLException e) {
            System.out.print("Error occurred while UPDATE Operation: " + e);
            throw e;
        }
    }

    //*************************************
    //DELETE an medicament
    //*************************************
    public static void deleteMedWithId (String medId) throws SQLException, ClassNotFoundException {

        String updateStmt =
                "   DELETE FROM public.\"Medicaments\"\n" +
                        "         WHERE \"id_medicament\" ="+ medId +";\n";

        try {
            connection.dbExecuteUpdate(updateStmt);
        } catch (SQLException e) {
            System.out.print("Error occurred while DELETE Operation: " + e);
            throw e;
        }
    }

    //*************************************
    //INSERT an medicament
    //*************************************
    public static void insertMed (String nom, String forme, String famille, String prix) throws SQLException, ClassNotFoundException {

        String updateStmt =
                "INSERT INTO public.\"Medicaments\"\n" +
                        "( \"nom\", \"forme\", \"famille\", \"prix\")\n" +
                        "VALUES\n" +
                        "('"+nom+"', '"+forme+"','"+famille+"', '"+prix+"');\n";

        try {
            connection.dbExecuteUpdate(updateStmt);
        } catch (SQLException e) {
            System.out.print("Error occurred while INSERT Operation: " + e);
            throw e;
        }
    }



}
