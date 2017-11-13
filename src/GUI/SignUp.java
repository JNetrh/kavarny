/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import static javafx.scene.text.TextAlignment.CENTER;
import kavarensky_povalec_1.*;

/**
 * Layout který se zobrazí při spuštění aplikace
 * Příhlášení uživatele nebo jeho přesměrování na SignIn, kde je možná registrace
 * @author Kuba
 */
public class SignUp {
    
    private SplitPane splitPane;
    //label pro komunikaci s uživatelem
    private Label message = new Label("");
    private TextField email;
    private PasswordField passwd_1;
    private Main main;
    private Scene scene_signIn;

    /**
     * konstruktor ve kterám se nastaví proměnná Main Class a spustí metoda,
     * která vytvoří celý layout
     * @param main 
     */
    public SignUp(Main main) {
        this.main = main;
        createRegistration(); 
    }

    /**
     * vytvoří textfieldy a labely pro zadání přihlašovacích údajů
     * nastaví eventHendlers pro tlačítka
     */
    private void createRegistration() {

        VBox vbox = new VBox(20);
        VBox vbox2 = new VBox(20);
        splitPane = new SplitPane();
        
        StackPane sp1 = new StackPane();
        StackPane sp2 = new StackPane();
        
        splitPane.setPrefSize(main.WINDOW_WIDTH, main.WINDOW_HEIGHT);

        vbox.setAlignment(Pos.CENTER);
        vbox2.setAlignment(Pos.CENTER);
        
        //Defining the registration text fields
        email = new TextField();
        passwd_1 = new PasswordField();
        email.setPromptText("Enter your email adress");
        passwd_1.setPromptText("Enter your password");
        
        //Defining the signUp button
        Button signUp = new Button("Přihlásit");
        Text heading1 = new Text("Přihlásit se");
        heading1.setFont(Font.font("Helvetica", FontWeight.BOLD, 20));
       
        //Defining the signIn button
        Button signIn = new Button("Registrovat");
        Text heading2 = new Text("Ještě nejsi členem Kávových závisláků?");
        Text vyzva = new Text("Zaregistruj se a získej přehled\n" + "o nejlepších kavárnách ve městě!\n");
        heading2.setFont(Font.font("Helvetica", FontWeight.BOLD, 20));
        vyzva.setFont(Font.font("Helvetica", FontWeight.MEDIUM, 14));
        vyzva.setTextAlignment(TextAlignment.CENTER);
        vyzva.setLineSpacing(5);
                
        addButtonHandler(signIn, "signIn");
        addButtonHandler(signUp, "signUp");
        
        vbox.setPadding(new Insets(0, (main.WINDOW_WIDTH)/8,0,(main.WINDOW_WIDTH)/8));
        
        
        vbox.getChildren().addAll(heading1, email, passwd_1, message, signUp);
        sp1.getChildren().addAll(vbox);
        vbox2.getChildren().addAll(heading2, vyzva, signIn);
        sp2.getChildren().addAll(vbox2);
        splitPane.getItems().addAll(sp1, sp2);
        splitPane.setDividerPositions(0.5f,0.5f);
        
        
        
    }

    /**
     * 
     * @return layout pro scénu
     */
    public SplitPane getContent() {
        return splitPane;
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
                
                if(action.equals("signIn")){
                    scene_signIn = new Scene(new SignIn(main).getContent(), 300, 250);
                    scene_signIn.getStylesheets().add("styles/signIn.css");
                    main.setScene(scene_signIn);
                    
                }
                else if(action.equals("signUp")){
                    //zkontrluje, zda jsou pole vyplněná
                    if(!email.getText().equals("") || !passwd_1.getText().equals("")){
                        //spustí metodu confirm pro přihlášení
                        boolean oukej = main.getConfirm().confirmSignUp(email.getText(), passwd_1.getText());
                        //přilášení masterAdmina
                        if(email.getText().equals(main.getMasterAdmin().getName()) && passwd_1.getText().equals(main.getMasterAdmin().getPassword())){
                            main.getMasterAdmin().setSigned(true);
                            main.setSignedUser(0, "masterAdmin", "", 100, 100);
                            Scene scene = new Scene(new Dashboard(main).getContent(), 300, 250);
                            main.setScene(scene);
                        }
                        //přihlášení řadového uživatele
                        else if(oukej){
                            Scene scene = new Scene(new Dashboard(main).getContent(), 300, 250);
                            main.setScene(scene);
                            
                        }
                        //uživatel zadal špatné jméno nebo heslo
                        else{
                            message.setText("špatné jméno nebo heslo");
                            message.setAlignment(Pos.BOTTOM_LEFT);
                            message.setTextFill(Color.rgb(210, 39, 30));
                        }
                    }
                    else{
                        message.setText("vyplňte všechna pole");
                        message.setAlignment(Pos.BOTTOM_LEFT);
                        message.setTextFill(Color.rgb(210, 39, 30));
                    }
                    
                }
                
            }
        });
    }
    
   
    
    
}
