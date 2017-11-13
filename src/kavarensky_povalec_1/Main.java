/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kavarensky_povalec_1;

import GUI.*;
import DB.*;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;
import static javafx.application.Application.launch;
import javafx.stage.Screen;

/**
 * Hlavní třída aplikace zajišťuje prvotní inicializaci pamatuje si hlavní
 * proměnné nastavuje šířku okna
 *
 * @author netj01
 */
public class Main extends Application {

    private Scene scene;
    private SignUp signUp;
    private SignIn signIn;
    private Confirm confirm;
    private Database database;
    private Dashboard dashboard;
    private User signedUser;
    private static Stage primaryStage;
    private CafeList cafeList;
    private MasterAdmin masterAdmin;
    private CafeRatingList ratingList;

    //finální proměnné, ze kterých se následně určují všechny velikosti ostatních 
    //prvků v programu. Jsou public, tudíš dostupné pro kohokoliv, 
    //kdo importuje balicek "kavarensky_povalec_1"
    public static final double SCREEN_WIDTH = Screen.getPrimary().getVisualBounds().getWidth();
    public static final double SCREEN_HEIGHT = Screen.getPrimary().getVisualBounds().getHeight();
    public static final double WINDOW_WIDTH = SCREEN_WIDTH / 2;
    public static final double WINDOW_HEIGHT = SCREEN_HEIGHT / 2;

    /**
     * spouštěcí metoda javaFx inicializuje program
     *
     * @param primary hlavní stage aplikace
     */
    @Override
    public void start(Stage primary) {

        this.primaryStage = primary;

        FlowPane flowPane = new FlowPane();

        masterAdmin = new MasterAdmin("master", "master");

        signUp = new SignUp(this);
        confirm = new Confirm(this);
        database = Database.getInstance();
        database.setMain(this);
        cafeList = new CafeList(this);
        cafeList.start();
        ratingList = new CafeRatingList(this);
        dashboard = new Dashboard(this);
        signedUser = null;

        scene = new Scene(flowPane);

        flowPane.getChildren().addAll(signUp.getContent());

        primaryStage.setTitle("Kavárenský závislák");
        primaryStage.setScene(scene);
        primaryStage.setWidth(WINDOW_WIDTH);
        primaryStage.setHeight(WINDOW_HEIGHT);
        primaryStage.show();

    }

    /**
     *
     * @return list hodnocení všech kaváren
     */
    public CafeRatingList getRatingList() {
        return ratingList;
    }

    /**
     * vrací instanci třídy masteradmin
     *
     * @return masterAdmin
     */
    public MasterAdmin getMasterAdmin() {
        return masterAdmin;
    }

    /**
     *
     * @return seznam všech instancí kaváren
     */
    public CafeList getCafeList() {
        return cafeList;
    }

    /**
     *
     * @return šířku monitoru
     */
    public static double getSCREEN_WIDTH() {
        return SCREEN_WIDTH;
    }

    /**
     *
     * @return výšku monitoru
     */
    public static double getSCREEN_HEIGHT() {
        return SCREEN_HEIGHT;
    }

    /**
     *
     * @return spočítanou šířku hlavního okna
     */
    public static double getWINDOW_WIDTH() {
        return WINDOW_WIDTH;
    }

    /**
     *
     * @return spočítanou výšku hlavního okna
     */
    public static double getWINDOW_HEIGHT() {
        return WINDOW_HEIGHT;
    }

//    public Database getDatabase() {
//        return database;
//    }
    /**
     *
     * @return třídu, která řídí přihlašovací proces
     */
    public Confirm getConfirm() {
        return confirm;
    }

    /**
     * vrací přihlašovací okno (node)
     *
     * @return
     */
    public SignUp getSignUp() {
        return signUp;
    }

    /**
     *
     * @return registrační okno (node)
     */
    public SignIn getSignIn() {
        return signIn;
    }

    /**
     *
     * @return hlavní dashboard aplikace (node) borderPane
     */
    public Dashboard getDashboard() {
        return dashboard;
    }

    /**
     * nastaví nový dashboard
     *
     * @param dashboard nový dashboard (RESTRICT)
     */
    public void setDashboard(Dashboard dashboard) {
        this.dashboard = dashboard;
    }

    /**
     * vrátí scénu aplikace
     *
     * @return scéna aplikace
     */
    public Scene getScene() {
        return scene;
    }

    /**
     * nastaví novou scénu do stage a následně ji vykreslí
     *
     * @param scene nová scéna
     */
    public void setScene(Scene scene) {
        Stage stage = (Stage) this.scene.getWindow();
        stage.setScene(scene);
        stage.show();
        this.scene = scene;
    }

    /**
     * nastaví přihlášeného uživatele instance třídy User
     *
     * @param id uživatele
     * @param email uživatele
     * @param password uživatele
     * @param admin práva uživatele
     * @param processed stav uživatele
     */
    public void setSignedUser(int id, String email, String password, int admin, int processed) {
        signedUser = new User(id, email, password, admin, processed);
    }

    /**
     *
     * @return přihlášeného uživatele
     */
    public User getSignedUser() {
        return signedUser;
    }

    /**
     * smaže hodnotu proměnné přihlášeného usera
     */
    public void deleteSignedUser() {
        signedUser = null;
    }

    /**
     * Spouštěcí třída aplikace
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
