/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import DB.Database;
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
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import kavarensky_povalec_1.*;

/**
 * layout pro registraci nového užiatele
 * Vytvoří labels a Text fields pro zadní nového emailu a hesla
 * @author Kuba
 */
public class SignIn {
    
    private Main main;
    private SplitPane splitPane;
    private TextField email;
    private PasswordField passwd_1;
    private PasswordField passwd_2;
    private Label message = new Label("");

    /**
     * konstruktor pro inicializaci registrace
     * spustí metodu pro vytvoření zobrazení
     * @param main 
     */
    public SignIn(Main main) {
        this.main = main;
        createRegistration();
    }

    /**
     * vytvoří textfieldy a labely pro zadání přihlašovacích údajů
     * nastaví eventHendlers pro tlačítka
     */
    private void createRegistration() {
        VBox vbox = new VBox(20);
        splitPane = new SplitPane();
        
        StackPane sp1 = new StackPane();
        StackPane sp2 = new StackPane();
        
        
        splitPane.setPrefSize(main.WINDOW_WIDTH, main.WINDOW_HEIGHT);

        
        vbox.setAlignment(Pos.CENTER);
        
                
        //Defining the registration text fields
        email = new TextField();
        passwd_1 = new PasswordField();
        passwd_2 = new PasswordField();
        
        FlowPane buttons = new FlowPane();
        //Defining the Submit button
        Button signIn = new Button("Registrovat");
        Button signUp = new Button("Zpět na přihlášení");
        
        
        email.setPromptText("Enter your email adress");
        passwd_1.setPromptText("Enter your new password");
        passwd_2.setPromptText("Confirm your password here");
        
        
        addButtonHandler(signIn, "signIn");
        addButtonHandler(signUp, "signUp");
        
        vbox.setPadding(new Insets(0, (main.WINDOW_WIDTH)/8,0,(main.WINDOW_WIDTH)/8));
        
        buttons.getChildren().addAll(signIn, signUp);
        
        vbox.getChildren().addAll(email, passwd_1, passwd_2, buttons, message);
        sp1.getChildren().addAll(vbox);
        sp2.getChildren().add(vbox);
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
    private void addButtonHandler(Button submit, String action) {
        Button btn = submit;
        
         btn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(action.equals("signIn")){
                    if(email.getText().equals("") || passwd_1.getText().equals("") || passwd_2.getText().equals("")){
                            message.setText("vyplňte všechna pole formuláře");
                            message.setAlignment(Pos.BOTTOM_LEFT);
                            message.setTextFill(Color.rgb(210, 39, 30));
                    }
                    else if(passwd_1.getText().equals(passwd_2.getText())){
                        String signed = main.getConfirm().confirmSignIn(email.getText(), passwd_1.getText());
                        if(signed.equals("")){
                            //pokud jsou hesla stejná a email ještě není v db, přejdi na dashboard
                            int userId_db = Database.getInstance().getUserMailById(email.getText());
                            main.setSignedUser(userId_db, email.getText(), passwd_1.getText(), 0, 0);
                            Scene scene = new Scene(new Dashboard(main).getContent(), 300, 250);
                            main.setScene(scene);
                        }
                        else{
                            message.setText("email je již obsazený");
                            message.setAlignment(Pos.BOTTOM_LEFT);
                            message.setTextFill(Color.rgb(210, 39, 30));
                        }
                    }
                    else{
                        message.setText("hesla nesouhlasí");
                        message.setAlignment(Pos.BOTTOM_LEFT);
                        message.setTextFill(Color.rgb(210, 39, 30));
                    }
                }
                if(action.equals("signUp")){
                    Scene scene_signUp = new Scene(new SignUp(main).getContent(), 300, 250);
                    scene_signUp.getStylesheets().add("styles/signIn.css");
                    main.setScene(scene_signUp);
                }
                
            }
        });
    }
    
   
    
    
}
