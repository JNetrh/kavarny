/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package forms;

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
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import kavarensky_povalec_1.Cafe;
import kavarensky_povalec_1.InsertCafe;
import kavarensky_povalec_1.Main;
import javafx.stage.FileChooser;

/**
 * Třída slouží pro vytvoření formuláře pro založení nové kavárny
 * @author Kuba
 */
public class NewCafeForm {
    private final Main main;
    
    //grafické prvky
    private FlowPane flow;
    private StackPane sp1;
    private StackPane sp2;
    
    //message label pro komunikaci s uživatelem
    private Label message;
    
    //soubory, do kterých se po výběru uloží obrázky od uživatele
    private File file_1;
    private File file_2;
    
    //labely pro náhledy loga a nápojáku
    private Label lblLogo;
    private Label lblListek;
    
    
    //Area pro popis kavárny
    private TextArea taDescription;
    //checkboxy 
    private CheckBox cbWifi;
    private CheckBox cbAccess;
    
    private final Map<String, TextField> textInputs = new HashMap<>();
    
    //pole Stringů, které představují labels pro textFields
    private final String[] lblNamesLeft = {"Název kavárny", "Ulice", "Město", "Popis"};
    private final String[] lblNamesRight = {"Otevírací doba", "GPS souřadnice", "Nahrajte logo kvavárny", "Nahrajte nápojový lístek"};
        
    /**
     * inicializuje novou třídu
     * @param main Hlavní třída, ve které se nachází většina globálních 
     * proměnných
     */
    public NewCafeForm(Main main){
        this.main = main;
        initForm();
    }
    
    /**
     * inicializuje vše potřebné pro vytvoření formuláře
     */
    private void initForm(){
        flow = new FlowPane();
         
        flow.setPrefSize((main.WINDOW_WIDTH/6)*5, (main.WINDOW_HEIGHT/6)*5);
        
        //labels pro obrázky nahrané uživatelem
        lblLogo = new Label();
        lblListek = new Label();
        
        //message label pro komunikaci s uživatelem
        message = new Label("");
        
        FlowPane flowPane = new FlowPane();
        flowPane.setPrefWidth((main.WINDOW_WIDTH/6)*5);
        
        Text heading = new Text("Přidání nové kavárny");
        heading.setFont(Font.font("Helvetica", FontWeight.BOLD, 14));
        flowPane.getChildren().add(heading); 
        flowPane.setPadding(new Insets(5, 5, 5, 10));
        
        
        //vloží nadpis a message label
        flow.getChildren().addAll(message, flowPane);
        
        createForm();
    }
    
    /**
     * 
     * @return hotový formulář
     */
    public FlowPane getForm(){
        return flow;
    }
    /**
     * Vytvoří formulář, zarovná textová pole, vypíše labels
     * zapíše tyto do mapy
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
        for (String string : lblNamesLeft) {
            Label label = new Label(string);            
            
            label.setAlignment(Pos.BOTTOM_LEFT);
            label.setPadding(new Insets(10, 0, 5, 10));
            vbox_L.getChildren().add(label);
            
            if(string.equals("Popis")){
                taDescription = new TextArea();
                taDescription.setPrefWidth((main.WINDOW_WIDTH/6));
                taDescription.setPrefHeight(40);
                vbox_L.getChildren().add(taDescription);
            }
            else {
                vbox_L.getChildren().add(createTextField(string));
            }
        }
        
        //tlačítko pro submit
        Button submitForm = new Button("uložit");
        addButtonHandler(submitForm, "submitForm");
        vbox_L.getChildren().add(submitForm);
        
        cbWifi = new CheckBox("wifi");
        cbAccess = new CheckBox();
        cbAccess.setText("Bezbariérový přístup");
        
        //prochází pole labels a vytvoří pravý blok formuláře
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
                
                vbox_R.getChildren().add(grid);
            }
            else if(string.equals("Nahrajte logo kvavárny")){
                Button uploadLogo = new Button("Nahrát logo");
                vbox_R.getChildren().add(uploadLogo);
                vbox_R.getChildren().add(lblLogo);
                addButtonHandler(uploadLogo, "Nahrát logo");
            }
            else if(string.equals("Nahrajte nápojový lístek")){
                Button uploadBeverage = new Button("Nahrát lístek");
                vbox_R.getChildren().add(uploadBeverage);
                vbox_R.getChildren().add(lblListek);
                addButtonHandler(uploadBeverage, "Nahrát lístek");
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
     * @param controlId název pro textfield
     * @return result Textfield, který se následně zobrazí
     */
    private TextField createTextField(String controlId) {
        TextField result = new TextField();
        result.setPrefWidth((main.WINDOW_WIDTH/6));
        textInputs.put(controlId, result);
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
                
                //provede všechny úkony spojené se submitem formuláře
                if(action.equals("submitForm")){
                    Cafe cafe = new Cafe();
                    //přiřazení hodnot z textfields do příslušných
                    //atributů nové třídy cafe
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
                    //textArea má zvláštní místo, protože není v poli Labels
                    cafe.setPopis(taDescription.getText());
                    //místo se nikde nepoužívá, proto je do DB zapsáno ""
                    cafe.setMisto("");
                    //přiřazení checkBox hodnot (wifi, bezbariér)
                    cafe.setWifi(cbWifi.selectedProperty().getValue());
                    cafe.setBezbarierova(cbAccess.selectedProperty().getValue());
                    
                    //vyčíštění label 
                    message.setText("");
                    
                    //
                    try {
                        cafe.setLogo(new Image(file_1.toURI().toURL().toExternalForm(), 100, 100, false, false));
                        cafe.setListek(new Image(file_2.toURI().toURL().toExternalForm(), 100, 100, false, false));
                        //vložení kavárny do listu, který si drž
                        main.getCafeList().getSeznamKavaren().add(cafe);
                        //vložení kavárny do databáze
                        Thread t = new InsertCafe(cafe, file_1, file_2);
                        t.start();
                        //hláška je vidět po dobu zápisu do db
                        while(t.isAlive()){
                            if(message.getText().equals("")){
                                message.setText("Počkejte, dokud se kavárna řádně neuloží");
                                message.setTextFill(Color.rgb(210, 39, 30));
                            }
                        }
                        message.setText("Kavárna úspěšně vytvořena");
                        message.setTextFill(Color.rgb(39, 210, 30));
                        
                        //vyčištění polí po úspěšném procesu
                        for (String label : textInputs.keySet()) {
                            TextField txtField = textInputs.get(label);
                            txtField.setText("");
                        }
                        taDescription.setText("");
                        lblLogo.setGraphic(null);
                        lblListek.setGraphic(null);
                        cbWifi.selectedProperty().setValue(false);
                        cbAccess.selectedProperty().setValue(false);
                    } catch (MalformedURLException ex) {
                        Logger.getLogger(NewCafeForm.class.getName()).log(Level.SEVERE, null, ex);
                        message.setText("Nepodařilo se zpracovat vytvoření kavárny");
                        message.setTextFill(Color.rgb(210, 39, 30));
                    }
                    
                    
                        

                }
                else if(action.equals("Nahrát logo")){
                    message.setText("");
                    //vytvoří systémové okno pro procházení souborů počítače
                    FileChooser fileChooser = new FileChooser();
                    file_1 = fileChooser.showOpenDialog(null);
                    Image image;
                    try {
                        image = new Image(file_1.toURI().toURL().toExternalForm(), 100, 100, false, false);
                        //zobrazí nahraný obrázek
                        lblLogo.setGraphic(new ImageView(image));
                        message.setText("logo úspěšně nahráno");
                        //zelená
                        message.setTextFill(Color.rgb(39, 210, 30));
                    } catch (MalformedURLException ex) {
                        Logger.getLogger(NewCafeForm.class.getName()).log(Level.SEVERE, null, ex);
                        message.setText("logo se nepodařilo nahrát");
                        //červená
                        message.setTextFill(Color.rgb(210, 39, 30));
                    }
                    
                }
                else if(action.equals("Nahrát lístek")){
                    message.setText("");
                    FileChooser fileChooser = new FileChooser();
                    file_2 = fileChooser.showOpenDialog(null);
                    Image image;
                    try {
                        image = new Image(file_2.toURI().toURL().toExternalForm(), 100, 100, false, false);
                        lblListek.setGraphic(new ImageView(image));
                        message.setText("lístek úspěšně nahrán");
                        message.setTextFill(Color.rgb(39, 210, 30));
                    } catch (MalformedURLException ex) {
                        Logger.getLogger(NewCafeForm.class.getName()).log(Level.SEVERE, null, ex);
                        message.setText("lístek se nepodařil nahrát");
                        message.setTextFill(Color.rgb(210, 39, 30));
                    }
                }
                
            }
        });
    }
    
   
}
