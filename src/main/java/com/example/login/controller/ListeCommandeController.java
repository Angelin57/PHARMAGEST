package com.example.login.controller;

import com.example.login.model.ListeCommande;
import com.example.login.model.ListeCommandeDAO;
import com.example.login.model.Medicament;
import com.example.login.model.MedicamentDAO;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.sql.SQLException;
import java.util.Date;

public class ListeCommandeController {
    @FXML
    private TableColumn<ListeCommande, Date> dateRecuColumn;

    @FXML
    private TableColumn<ListeCommande, String> fournisseurColumn;

    @FXML
    private TableColumn<ListeCommande, Integer> idApproColumn;

    @FXML
    private TableColumn<ListeCommande, String> medColumn;

    @FXML
    private TableColumn<ListeCommande, Integer> prixFournisseurColumn;

    @FXML
    private TableColumn<ListeCommande, Integer> prixVenteColumn;

    @FXML
    private TableColumn<ListeCommande, Integer> qttCommandeColumn;

    @FXML
    private TableColumn<ListeCommande, Integer> qttInitColumn;

    @FXML
    private TableColumn<ListeCommande, Integer> qttRecueColumn;

    @FXML
    private TableColumn<ListeCommande, String> statusColumn;
    @FXML
    private TableView<ListeCommande> listeTable;

    private Medicament medicament;


    public void initialize() throws SQLException, ClassNotFoundException{
        idApproColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getIdAppro()).asObject());
        medColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNomMed()));
        dateRecuColumn.setCellValueFactory(cellData -> {
            Date date = cellData.getValue().getDateRecue(); // Supposons que vous avez une méthode getDateRecue() pour obtenir la date
            return new SimpleObjectProperty<>(date);
        });
        fournisseurColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getFournisseur()));
        prixFournisseurColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getPrixFournisseur()).asObject());
        prixVenteColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getPrixUnit()).asObject());
        qttInitColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getQttInitial()).asObject());
        qttRecueColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getQttRecue()).asObject());
        qttCommandeColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getQttCommande()).asObject());
        statusColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getStatus()));

        viewAllListe(null);

    }

    @FXML
    void viewAllListe(ActionEvent event) throws SQLException, ClassNotFoundException {
        try {
            ObservableList<ListeCommande> users = FXCollections.observableArrayList(ListeCommandeDAO.getAllCommande());
            listeTable.setItems(users);
        } catch (SQLException e) {
            throw e;
        }


    }

}
