/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package forms;

import DB.Database;
import java.awt.Desktop;
import java.io.File;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import kavarensky_povalec_1.Cafe;
import kavarensky_povalec_1.InsertCafe;
import kavarensky_povalec_1.Main;

/**
 * Třída si velmi podobná s NewCafeForm
 * rozdíl je v tom, že se při inicializaci do polí vypíší aktuální
 * hodnoty, které má kavárna uložena a v následném procesu Updatu,
 * který je proveden smazáním staré kavárny a založení úplně
 * nové s novými hodnotami
 * @author Kuba
 */
public class UpdateCafeForm {
    private final Main main;
    private int cafeId;
    
    //label pro komunikaci s uživatelem
    private Label message;
    
    //boolean zda uživatel nahrál nové logo i lístek (NECESARRY FOR CONTINUOUS PROCESS)
    private boolean isChangedLogo;
    private boolean isChangedListek;
    
    private Cafe cafe;
    
    private FlowPane flow;
    private StackPane sp1;
    private StackPane sp2;
    
    private File file_1;
    private File file_2;
    
    private Label lblLogo;
    private Label lblListek;

    private TextArea taDescription;
    private CheckBox cbWifi;
    private CheckBox cbAccess;
    
    //mapa, ve které se nachází textfieldy
    //následně se prochází při submitu a berou se z jednotlivých fieldů hodnoty
    private final Map<String, TextField> textInputs = new HashMap<>();
    
    //pole labelů, dle kterých se následně vytvoří formulářové prvky
    private final String[] lblNamesLeft = {"Název kavárny", "Ulice", "Město", "Popis"};
    private final String[] lblNamesRight = {"Otevírací doba", "GPS souřadnice", "Nahrajte nové logo kvavárny", "Nahrajte nový nápojový lístek"};
        
    /**
     * konstruktor
     * @param main Hlavní třída aplikace, ve které se drží většina potřebných proměnných
     * @param id třídy, jejíž hodnoty se mají vypsat do Text fieldů formuláře
     */
    public UpdateCafeForm(Main main, int id){
        this.main = main;
        this.cafeId = id;
        isChangedListek = false;
        isChangedLogo = false;
        initForm();
    }
    
    /**
     * inicializace všech potřebných záležitostí 
     * pro vytvoření samotného formuláře
     */
    private void initForm(){
        flow = new FlowPane();
        
        flow.setPrefSize((main.WINDOW_WIDTH/6)*5, (main.WINDOW_HEIGHT/6)*5);
        
        lblLogo = new Label();
        lblListek = new Label();
        
        message = new Label();
        
        FlowPane flowPane = new FlowPane();
        flowPane.setPrefWidth((main.WINDOW_WIDTH/6)*5);
        
        Text heading = new Text("Úprava stávající kavárny");
        flowPane.getChildren().add(heading);
        
        //najde id kavárny dastupných v CafeList a uloží 
        //instanci teto kavárny do proměnné cafe
        for (Cafe contempCafe : main.getCafeList().getSeznamKavaren()) {
            int elementId = contempCafe.getId();
            if(elementId == cafeId){
                cafe = contempCafe;
                //kavárna nalezena, můžeme pryč
                break;
            }
        }
        
        flow.getChildren().addAll(message, flowPane);
        
        createForm();
    }
    
    /**
     * Vytvoří formulář, zarovná textová pole, vypíše labels
     * zapíše tyto do mapy
     * vyplní formulářové prvky daty z upravované kavárny
     */
    private void createForm() {
        
        sp1 = new StackPane();
        sp2 = new StackPane();
        sp1.setPrefWidth((main.WINDOW_WIDTH/6)*2);
        sp2.setPrefWidth((main.WINDOW_WIDTH/6)*2);
        VBox vbox_L = new VBox();
        VBox vbox_R = new VBox();
        vbox_L.setPadding(new Insets(10, 10, 0, 10));
        vbox_R.setPadding(new Insets(10, 0, 0, 10));
        
        
        
        //prochází pole labels a vytvoří levý blok formuláře
        for (String fieldName : lblNamesLeft) {
            Label label = new Label(fieldName);            
            
            label.setAlignment(Pos.BOTTOM_LEFT);
            label.setPadding(new Insets(10, 0, 5, 10));
            vbox_L.getChildren().add(label);
            
            //popis je Textarea, nemůže být tedy vytvořena metodou createTextField
            //je nutné ji tedy vyjmout a inicializovat zvlášť
            if(fieldName.equals("Popis")){
                taDescription = new TextArea();
                taDescription.setPrefWidth((main.WINDOW_WIDTH/6));
                taDescription.setPrefHeight(40);
                taDescription.setText(cafe.getPopis());
                vbox_L.getChildren().add(taDescription);
            }
            else {
                vbox_L.getChildren().add(createTextField(fieldName));
            }
        }
        
        //button pro uložení změn
        Button submitForm = new Button("uložit");
        vbox_L.getChildren().add(submitForm);
        addButtonHandler(submitForm, "submitForm");
        
        cbWifi = new CheckBox("wifi");
        cbAccess = new CheckBox();
        cbAccess.setText("Bezbariérový přístup");
        
        if(cafe.isBezbarierova()){
            cbAccess.selectedProperty().setValue(Boolean.TRUE);
        }
        if(cafe.isWifi()){
            cbWifi.selectedProperty().setValue(Boolean.TRUE);
        }
        
        /*
        prochází pole labels a vytvoří pravý blok formuláře
        celý pravý blok formuláře je specifický, přesto pro 
        přehlednost je vytvářen cyklem
        */
        for (String string : lblNamesRight) {
            Label label = new Label(string);
            label.setAlignment(Pos.BOTTOM_LEFT);
            label.setPadding(new Insets(10, 0, 5, 10));
            vbox_R.getChildren().add(label);
            
            if(string.equals("Otevírací doba")){
                GridPane grid = new GridPane();
                Text from = new Text("od");
                Text to = new Text("do");
                TextField tfFrom = createTextField("od");
                tfFrom.setPrefWidth(50);
                TextField tfTo = createTextField("do");
                tfTo.setPrefWidth(50);
                grid.add(from, 0, 0);
                grid.add(tfFrom, 1, 0);
                grid.add(to, 2, 0);
                grid.add(tfTo, 3, 0);
                grid.setHgap(20);
                
                tfFrom.setText(cafe.getDobaOd());
                tfTo.setText(cafe.getDobaDo());
                
                vbox_R.getChildren().add(grid);
            }
            else if(string.equals("GPS souřadnice")){
                GridPane grid = new GridPane();
                TextField tfFrom = createTextField("gpsX");
                tfFrom.setPrefWidth(50);
                TextField tfTo = createTextField("gpsY");
                tfTo.setPrefWidth(50);
                grid.add(tfFrom, 1, 0);
                grid.add(tfTo, 3, 0);
                grid.setHgap(20);
                
                tfFrom.setText(cafe.getGpsX());
                tfTo.setText(cafe.getGpsY());
                
                vbox_R.getChildren().add(grid);
            }
            else if(string.equals("Nahrajte nové logo kvavárny")){
                Button uploadLogo = new Button("Nahrát logo");
                addButtonHandler(uploadLogo, "Nahrát logo");
                vbox_R.getChildren().add(uploadLogo);
                vbox_R.getChildren().add(lblLogo);
                lblLogo.setGraphic(resizeImage(new ImageView(cafe.getLogo()), 100));
            }
            else if(string.equals("Nahrajte nový nápojový lístek")){
                Button uploadBeverage = new Button("Nahrát lístek");
                vbox_R.getChildren().add(uploadBeverage);
                vbox_R.getChildren().add(lblListek);
                addButtonHandler(uploadBeverage, "Nahrát lístek");
                lblListek.setGraphic(resizeImage(new ImageView(cafe.getListek()), 100));
            }
            
            
                      
        }
        
        vbox_R.getChildren().addAll(cbWifi, cbAccess);
        
        sp1.getChildren().add(vbox_L);
        sp2.getChildren().add(vbox_R);
        flow.getChildren().addAll(sp1, sp2);
        
    }
    
    /**
     * vytvoří nový textfield a vrátí ho jako parametr
     * zároveň ho vlaží do mapy, kterou je následně možné procházet
     * @param controlName název pro textfield
     * @return result Textfield, který se následně zobrazí
     */
    private TextField createTextField(String controlName) {
        TextField result = new TextField();
        result.setPrefWidth((main.WINDOW_WIDTH/6));
        
        if(controlName.equals("Název kavárny")){
            result.setText(cafe.getNazev());
        }
        else if(controlName.equals("Ulice")){
            result.setText(cafe.getAdresa());
        }
        else if(controlName.equals("Město")){
            result.setText(cafe.getMesto());
        }
        
        
        textInputs.put(controlName, result);
        return result;
    }
    
    /**
     * podle zadaného parametru action přiřadí sadu příkazů,
     * které se mají provést po stisknutí tlačítka
     * @param submit tlačítko, kterému se pořířadí eventhandler
     * @param action typ akce pro rozlišení činnosti
     */
    private void addButtonHandler(Button submit,String action) {
        Button btn = submit;
        
         btn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                
                if(action.equals("submitForm")){
                  //  Cafe cafe = new Cafe();
                    for (String label : textInputs.keySet()) {
                        TextField txtField = textInputs.get(label);
                        switch (label) {
                            case "Název kavárny":
                                cafe.setNazev(txtField.getText());
                                break;
                            case "Ulice":
                                cafe.setAdresa(txtField.getText());
                                break;
                            case "Město":
                                cafe.setMesto(txtField.getText());
                                break;
                            case "od":
                                cafe.setDobaOd(txtField.getText());
                                break;
                            case "do":
                                cafe.setDobaDo(txtField.getText());
                                break;
                            case "gpsX":
                                cafe.setGpsX(txtField.getText());
                                break;
                            case "gpsY":
                                cafe.setGpsY(txtField.getText());
                                break;
                            default:
                                break;
                        }
                    }
                    /*
                    Není vytvořena nová kavárna, pouze jsou upraveny údaje v té stávající
                    toto je platné do ukončení programu, kdy je nenávratně zapomenuta a 
                    její "pravá" aktualizovaná verze se znovu načte z databáze
                    */
                    cafe.setPopis(taDescription.getText());
                    cafe.setMisto("");
                    
                    if(isChangedLogo){
                        try {
                            cafe.setLogo(new Image(file_1.toURI().toURL().toExternalForm(), 100, 100, false, false));
                        } catch (MalformedURLException ex) {
                            Logger.getLogger(NewCafeForm.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                    else{
                        message.setText("musíte vybrat nové logo");
                        message.setTextFill(Color.rgb(210, 39, 30));
                    }
                    if(isChangedListek){
                        try {
                            cafe.setListek(new Image(file_2.toURI().toURL().toExternalForm(), 100, 100, false, false));
                        } catch (MalformedURLException ex) {
                            Logger.getLogger(NewCafeForm.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                    else{
                        message.setText("musíte vybrat nový nápojový lístek");
                        message.setTextFill(Color.rgb(210, 39, 30));
                    }
                    if(isChangedListek && isChangedLogo){
                        Database.getInstance().databazovaFunkce("UPDATE", "DELETE FROM ad_74e3927ac2e12c0.cafes WHERE id=" + cafeId);
                        Thread t = new InsertCafe(cafe, file_1, file_2);
                        t.start();
                        isChangedListek = false;
                        isChangedLogo = false;
                        message.setText("musíte vybrat nový nápojový lístek");
                        message.setTextFill(Color.rgb(39, 210, 30));
                    }
                       
                }
                else if(action.equals("Nahrát logo")){
                    isChangedLogo = true;
                    FileChooser fileChooser = new FileChooser();
                    file_1 = fileChooser.showOpenDialog(null);
                    Image image;
                    try {
                        image = new Image(file_1.toURI().toURL().toExternalForm(), 100, 100, false, false);
                        lblLogo.setGraphic(new ImageView(image));
                    } catch (MalformedURLException ex) {
                        Logger.getLogger(NewCafeForm.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    message.setText("");
                    
                }
                else if(action.equals("Nahrát lístek")){
                    isChangedListek = true;
                    FileChooser fileChooser = new FileChooser();
                    file_2 = fileChooser.showOpenDialog(null);
                    Image image;
                    try {
                        image = new Image(file_2.toURI().toURL().toExternalForm(), 100, 100, false, false);
                        lblListek.setGraphic(new ImageView(image));
                    } catch (MalformedURLException ex) {
                        Logger.getLogger(NewCafeForm.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    message.setText("");
                }
                
            }
        });
    }
    /**
     * 
     * @return hotový formulář
     */
    public FlowPane getForm() {
        return flow;
    }
    
    /**
     * metoda nastaví novou šířku objektu a výška zůstane ve správném
     * poměru k původnímu poměru objektu
     * @param imageView kterému se nastaví šířka
     * @param width která má být nastavena
     * @return 
     */
    private ImageView resizeImage(ImageView imageView, double width) {
        ImageView result = imageView;
        result.setFitWidth(width);
        result.setPreserveRatio(true);
        
        return result;
    }
}
