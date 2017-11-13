/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import DB.Database;
import forms.NewCafeForm;
import forms.NewFilterShit;
import forms.UpdateCafeForm;
import java.util.ArrayList;
import java.util.Map;
import java.util.Optional;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import kavarensky_povalec_1.Cafe;
import kavarensky_povalec_1.CafeRating;
import kavarensky_povalec_1.InsertNewRating;
import kavarensky_povalec_1.Main;
import kavarensky_povalec_1.User;

/**
 * Třída, která se zobrazí po přihlášení obsahuje kromě formulářů všechna
 * pohledová okna nastavuje nástrojové postranní lišty
 *
 * @author Kuba
 */
public class Dashboard {

    private final BorderPane borderPane;
    private final Main main;
    private Scene scene_signOut;
    private TableView<Cafe> tabulkaKavaren;
    private ObservableList<Cafe> seznamKavaren;
    private final TextArea ratingArea;
    private ComboBox comboBox;
    private final ScrollPane scroll;
    private final Label message;
    private NewFilterShit filterOption;
    private FlowPane filterArea;
    private FlowPane writableArea;

    /**
     * konstruktor inicializuje prvotní náhled po přihlášení
     *
     * @param main
     */
    public Dashboard(Main main) {

        this.main = main;
        borderPane = new BorderPane();
        /*
        měnící se zobrazené panely jsou ve scrollu
        jinak by se výška aplikace táhla donekonečna
         */

        scroll = new ScrollPane();
        scroll.setHbarPolicy(ScrollBarPolicy.NEVER);
        scroll.setVbarPolicy(ScrollBarPolicy.AS_NEEDED);
        scroll.setFitToWidth(true);

        //text area k psaní textového hodnocení
        ratingArea = new TextArea();
        ratingArea.setId("-1");
        ratingArea.setPrefSize(150, 80);

        //hodnocení 1-5
        comboBox = new ComboBox();

        message = new Label();
        message.setText("");
        message.setAlignment(Pos.CENTER_LEFT);

        // pokud existuje přihlášený user, má cenu vytvářet dashboard
        if (main.getSignedUser() != null) {
            scroll.setContent(createMainDashboard(0, "", ""));
            borderPane.setCenter(scroll);
            borderPane.setTop(createTopBar());
            borderPane.setLeft(createLeftBar());
        }
    }

    /**
     * vrací celý dashboard
     *
     * @return borderpane, ve kterém je dashboard
     */
    public BorderPane getContent() {
        return borderPane;
    }

    /**
     * vytváří horní lištu s názvem aplikace a jménem přihlášeného uživatele
     *
     * @return herní lišta (node) Hbox
     */
    private HBox createTopBar() {
        //horizontální zobrazení
        HBox hbox = new HBox(20);
        hbox.setAlignment(Pos.CENTER_LEFT);
        hbox.setPrefHeight(main.WINDOW_HEIGHT / 10);

        Text caption = new Text();
        //Text userEmail = new Text();

        caption.setText("CafeAdmin");
        caption.setFont(Font.font("Helvetica", FontWeight.BOLD, 18));

        Button signOut = new Button("Odhlásit");
        //signOut.setPadding(new Insets(0, 0, 0, 20));

        addButtonHandler(signOut, "signOut");

        hbox.getChildren().addAll(caption, signOut);

        if (main.getSignedUser().getProcessed() < 2) {
            Button requestAdmin = new Button("Žádost o správcování");
            addButtonHandler(requestAdmin, "requestAdmin");
            hbox.getChildren().addAll(requestAdmin);
        }

        //new dnyz
        hbox.setPadding(new Insets(0, 0, 0, 10));
        return hbox;
    }

    /**
     * vytváří zobrazení seznamu všech dostupných kaváren
     *
     * @return seznam kaváren (node) FlowPane
     */
    private FlowPane createMainDashboard(int param, String type, String value) {
        FlowPane flow = new FlowPane();
        seznamKavaren = FXCollections.observableArrayList(main.getCafeList().getSeznamKavaren());
        ObservableList<Cafe> iterableCafes = FXCollections.observableArrayList();
        //vybere všechny kavárny
        if (param == 1) {
            for (Cafe cafe : seznamKavaren) {
                if (type.equals("name")) {
                    if (cafe.getNazev().toLowerCase().equals(value.toLowerCase())) {
                        iterableCafes.add(cafe);
                    }
                } else if (type.equals("city")) {
                    if (cafe.getMesto().toLowerCase().equals(value.toLowerCase())) {
                        iterableCafes.add(cafe);
                    }
                } else if (type.equals("rating")) {
                    int numberVal = Integer.parseInt(value);
                    int suma = 0;
                    Map<String, CafeRating> ratingMap = main.getRatingList().getHodnoceni();

                    //provede se v případě, že nějaké hodnocení existuje
                    if (ratingMap.containsKey(Integer.toString(cafe.getId()))) {
                        CafeRating rating = ratingMap.get(Integer.toString(cafe.getId()));
                        for (String hodnoceniInt : rating.getRatingInt()) {
                            int jednoHodnoceni = Integer.parseInt(hodnoceniInt);
                            suma += jednoHodnoceni;
                        }
                    }
                    if (suma >= numberVal) {
                        iterableCafes.add(cafe);
                    }
                }
            }

        } else {
            iterableCafes = seznamKavaren;
        }

        //pro každou kavárnu se vytvoří blok, ve kterém se zobrazí vybrané informace
        for (Cafe cafe : iterableCafes) {
            BorderPane border = new BorderPane();
            border.setPrefWidth((main.WINDOW_WIDTH / 6) * 4.5);
            Text heading = new Text(cafe.getNazev());
            //new dnyz
            heading.setFont(Font.font("Helvetica", FontWeight.BOLD, 14));
            border.setTop(heading);

            AnchorPane anchor = new AnchorPane();

            Text adresa = new Text("Adresa: ");
            Text gps = new Text("GPS: ");
            Text oteviracka = new Text("Otevírací doba: ");

            Text valAdresa = new Text(cafe.getAdresa() + ", " + cafe.getMesto());
            Text valGps = new Text(cafe.getGpsX() + " | " + cafe.getGpsY());
            Text valOteviracka = new Text("od: " + cafe.getDobaOd() + "  do: " + cafe.getDobaDo());

            Button details = new Button("detail");
            addButtonHandler(details, "details");
            
            //nastaví tlačítku id, které je identické pro kavárnu
            details.setId(Integer.toString(cafe.getId()));

            GridPane grid = new GridPane();
            grid.add(adresa, 0, 0);
            grid.add(gps, 0, 1);
            grid.add(oteviracka, 0, 2);

            grid.add(valAdresa, 1, 0);
            grid.add(valGps, 1, 1);
            grid.add(valOteviracka, 1, 2);

            //new dnyz
            grid.add(details, 0, 4);

            //new dnyz
            grid.setVgap(7);
            grid.setPadding(new Insets(10, 0, 10, 0));

            Line line = new Line(0, 2, ((main.WINDOW_WIDTH / 6) * 4.5), 2);

            anchor.getChildren().add(grid);
            anchor.setPrefWidth(((main.WINDOW_WIDTH / 6) * 4.5));

            border.setCenter(anchor);
            border.setBottom(line);
            ImageView logo = resizeImage(new ImageView(cafe.getLogo()), 100);

            border.setRight(logo);

            border.setPadding(new Insets(10, 0, 0, 10));

            flow.getChildren().add(border);
        }

        return flow;

    }

    /**
     * vytváří detail zvolené kavárny
     *
     * @param Cafeid kavárny, pro kterou se má detail vytvořit
     * @return náhled kavárny (node) FlowPane
     */
    private FlowPane createCafeDetail(int Cafeid) {
        int id = Cafeid;
        FlowPane flow = new FlowPane();
        GridPane grid = new GridPane();
        AnchorPane anchor = new AnchorPane();
        //scrollRating ve skutečnosti není scrollovací (zatím, možná)
        AnchorPane scrollRating = new AnchorPane();

        grid.setPrefWidth((main.WINDOW_WIDTH / 6) * 4.5);
        anchor.setPrefSize(150, 40);
        scrollRating.setPrefWidth((main.WINDOW_WIDTH / 6) * 4.5);
        scrollRating.setPrefHeight(100);

        ArrayList<Cafe> kavarny = main.getCafeList().getSeznamKavaren();
        Cafe cafe = null;

        //nalezení správné kavárny v seznamu dostupných kaváren
        for (Cafe kavarna : kavarny) {
            if (kavarna.getId() == id) {
                cafe = kavarna;
                break;
            }
        }

        Text heading = new Text(cafe.getNazev());

        Text valAdresa = new Text("Adresa: " + cafe.getAdresa() + ", " + cafe.getMesto());
        Text valPopis = new Text("Popis: " + cafe.getPopis());
        Text valGps = new Text("GPS: " + cafe.getGpsX() + " | " + cafe.getGpsY());
        Text valOteviracka = new Text("Otevírací doba: " + "od: " + cafe.getDobaOd() + "  do: " + cafe.getDobaDo());
        Text wifi = new Text("Wifi: " + (cafe.isWifi() ? "Ano" : "Ne"));
        Text access = new Text("Bezbariérová: " + (cafe.isBezbarierova() ? "Ano" : "Ne"));

        ImageView logo = new ImageView(cafe.getLogo());
        logo = resizeImage(logo, 150);

        Label labelRating = new Label("vložte slovní hodnocení");

        ratingArea.setId(Integer.toString(id));
        anchor.getChildren().add(ratingArea);

        Button submitRating = new Button("odeslat hodnocení");
        submitRating.setId(Integer.toString(id));
        addButtonHandler(submitRating, "rating");

        //pro adminy - možnost upravit kavárnu
        if (main.getSignedUser().getAdmin() > 0) {
            Button updateCafe = new Button("upravit informace");
            updateCafe.setId(Integer.toString(id));
            addButtonHandler(updateCafe, "updateCafe");

            grid.add(updateCafe, 1, 11);
        }

        comboBox = new ComboBox();
        //čísla pro hodnocení kavárny
        comboBox.getItems().addAll("1", "2", "3", "4", "5");
        comboBox.setId(Integer.toString(id));
        comboBox.setValue(null);

        //Mapa hodnocení kaváren
        Map<String, CafeRating> ratingMap = main.getRatingList().getHodnoceni();

        FlowPane ratingFlow = new FlowPane();
        int numberRating = 0;
        int iterations = -1;
        //provede se v případě, že nějaké hodnocení existuje
        if (ratingMap.containsKey(Integer.toString(id))) {
            iterations++;
            CafeRating rating = ratingMap.get(Integer.toString(id));
            for (String hodnoceni : rating.getRatingText()) {
                Text hodnoceniText = new Text(hodnoceni);
                Line line = new Line(0, 1, ((main.WINDOW_WIDTH / 6) * 2), 1);
                ratingFlow.getChildren().addAll(hodnoceniText, line);
            }
            scrollRating.getChildren().add(ratingFlow);

            for (String hodnoceniInt : rating.getRatingInt()) {
                int jednoHodnoceni = Integer.parseInt(hodnoceniInt);
                numberRating += jednoHodnoceni;
                iterations++;
            }
        }

        grid.add(message, 0, 0);
        grid.add(logo, 0, 1);
        grid.add(heading, 0, 2);
        grid.add(valAdresa, 0, 3);
        grid.add(valGps, 0, 4);
        grid.add(valOteviracka, 0, 5);
        grid.add(valPopis, 0, 6);
        grid.add(wifi, 0, 7);
        grid.add(access, 0, 8);
        grid.add(labelRating, 0, 9);
        grid.add(anchor, 0, 10);
        grid.add(submitRating, 0, 11);

        grid.add(new Label("Hodnocení uživatelů: "), 1, 2);
        grid.add(scrollRating, 1, 1);
        grid.add(new Label("Hodnocení 1-5: "), 1, 3);
        grid.add(new Text(Integer.toString(numberRating / iterations)), 1, 4);
        grid.add(comboBox, 1, 10);
        grid.setVgap(7);

        flow.getChildren().addAll(grid);
        flow.setPadding(new Insets(0, 0, 0, 10));
        

        return flow;
    }

    /**
     * metoda která vytvoří novou třídu formuláře pro úpravu kavárny
     *
     * @param cafeId pro kterou kavárnu má formulář vytvořit
     * @return hotový formulář
     */
    private FlowPane createCafeUpdate(int cafeId) {
        int id = cafeId;
        UpdateCafeForm updateForm = new UpdateCafeForm(main, id);

        return updateForm.getForm();
    }

    /**
     * vytvoří novou třídu formuláře pro založení nové kavárny
     *
     * @return hotový formulář
     */
    private FlowPane createCafeForm() {
        return new NewCafeForm(main).getForm();
    }

    /**
     * bordelhegeš
     *
     * @return okno users, kteří požádali o správcování
     */
    private FlowPane showAdminRequests() {
        FlowPane flowPane = new FlowPane();
        Text heading = new Text("Seznam žádostí o správcování");
        heading.setFont(Font.font("Helvetica", FontWeight.BOLD, 14));
        flowPane.getChildren().addAll(heading);
        flowPane.setPadding(new Insets(5, 5, 5, 10));

        ArrayList<User> processedUsers = Database.getInstance().getProcessedUsers();

        GridPane grid = new GridPane();

        grid.setPrefWidth((main.WINDOW_WIDTH / 6) * 5);
        grid.setPadding(new Insets(10, 10, 5, 0));

        grid.add(new Text("id uživatele"), 0, 0);
        grid.add(new Text("email uživatele"), 1, 0);
        grid.add(new Text("potvrdit možnost správcování"), 2, 0);

        int iterator = 1;

        for (User processedUser : processedUsers) {
            Button confirm = new Button("Potvrdit");
            confirm.setId(Integer.toString(processedUser.getId()));
            addButtonHandler(confirm, "Nové žádosti");
            Text id = new Text(Integer.toString(processedUser.getId()));
            Text email = new Text(processedUser.getEmail());

            grid.add(id, 0, iterator);
            grid.add(email, 1, iterator);
            grid.add(confirm, 2, iterator);
            grid.setHgap(10);
            grid.setVgap(10);

            iterator++;
        }

        flowPane.getChildren().addAll(grid);
        return flowPane;
    }

    /**
     * vytvoří okno, kde se dají kavárny filtrovat le přednastavených kritérií
     *
     * @return hotové okno k filtraci
     */
    private FlowPane createFilter() {
        FlowPane flowPane = new FlowPane();
        filterOption = new NewFilterShit(main);
        filterArea = new FlowPane();
        writableArea = new FlowPane();
        HBox buttonRow = new HBox();
        filterArea.setPrefWidth((main.WINDOW_WIDTH / 6) * 5);
        writableArea.setPrefWidth((main.WINDOW_WIDTH / 6) * 5);
        buttonRow.setPrefWidth((main.WINDOW_WIDTH / 6) * 5);

        Text heading = new Text("Filtrování kaváren");
        heading.setFont(Font.font("Helvetica", FontWeight.BOLD, 14));

        buttonRow.setPadding(new Insets(10, 10, 10, 0));
        buttonRow.setSpacing(10);

        Button processedFilter = new Button("Vyhledat");
        addButtonHandler(processedFilter, "processedFilter");

        Button filterName = new Button("Dle názvu");
        Button filterCity = new Button("Dle města");
        Button filterRating = new Button("Dle hodnocení");

        addButtonHandler(filterName, "filterName");
        addButtonHandler(filterCity, "filterCity");
        addButtonHandler(filterRating, "filterRating");

        buttonRow.getChildren().addAll(filterName, filterCity, filterRating);

        flowPane.getChildren().addAll(heading, message, buttonRow, filterArea, processedFilter, writableArea);
        flowPane.setPadding(new Insets(10, 10, 10, 10));

        return flowPane;
    }

    /**
     * levá zobrazovací lišta obsahující navigaci pro uživatele
     *
     * @return levá nástrojová lišta (node) GridPane
     */
    private GridPane createLeftBar() {
        GridPane grid = new GridPane();

        grid.setPrefWidth(main.WINDOW_WIDTH / 6);
        grid.setPadding(new Insets(10, 10, 10, 10));

        Button button_1 = new Button("Dashboard");
        button_1.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        addButtonHandler(button_1, "Dashboard");

        if (main.getSignedUser().getAdmin() > 0) {
            Button button_2 = new Button("Nová kavárna");
            button_2.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
            addButtonHandler(button_2, "Kavárny");

            grid.add(button_2, 0, 2);
        }

        if (main.getMasterAdmin().isSigned()) {
            Button button_3 = new Button("Žádosti o správcování");
            button_3.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
            addButtonHandler(button_3, "requests");

            grid.add(button_3, 0, 4);
        }

        Button button_4 = new Button("Filtrace kaváren");
        button_4.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        addButtonHandler(button_4, "showFilter");

        /* roztažení na výšku
        for (int row = 0 ; row < 3; row++ ){
          RowConstraints rc = new RowConstraints();
          rc.setFillHeight(true);
          rc.setVgrow(Priority.ALWAYS);
          grid.getRowConstraints().add(rc);}
         */
        
        //roztažení na šířku všeho, co je uvnitř
        for (int col = 0; col < 1; col++) {
            ColumnConstraints cc = new ColumnConstraints();
            cc.setFillWidth(true);
            cc.setHgrow(Priority.ALWAYS);
            grid.getColumnConstraints().add(cc);
        }

        Text userEmail = new Text();
        Label userLabel = new Label("Uživatel:");
        userLabel.setFont(Font.font("Helvetica", FontWeight.BOLD, 13));
        String email = main.getSignedUser().getEmail();
        userEmail.setText(email);

        grid.add(button_1, 0, 3);
        grid.add(button_4, 0, 5);
        grid.add(userLabel, 0, 0);
        grid.add(userEmail, 0, 1);

        grid.setVgap(10);

        return grid;
    }

    /**
     * podle 
     * aného parametru action přiřadí sadu příkazů, které se mají
     * provést po stisknutí tlačítka
     *
     * @param submit tlačítko, kterému se pořířadí eventhandler
     * @param action typ akce pro rozlišení činnosti
     */
    private void addButtonHandler(Button submit, String action) {
        Button btn = submit;

        btn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                //odhlásí uživatele a nastaví přihlašovací obrazovku
                if (action.equals("signOut")) {
                    main.getMasterAdmin().setSigned(false);
                    main.deleteSignedUser();
                    scene_signOut = new Scene(new SignUp(main).getContent());
                    scene_signOut.getStylesheets().add("styles/signIn.css");
                    main.setScene(scene_signOut);
                } //zobrazí seznam dostupných kaváren
                else if (action.equals("Dashboard")) {
                    scroll.setContent(null);
                    scroll.setContent(createMainDashboard(0, "", ""));

                } //zabrazí formulář pro vytvoření nové kavárny
                else if (action.equals("Kavárny")) {
                    scroll.setContent(null);
                    scroll.setContent(createCafeForm());
                    message.setText("");
                } //bordelhegeš^2
                else if (action.equals("requests")) {
                    scroll.setContent(null);
                    scroll.setContent(showAdminRequests());
                    message.setText("");
                } //zobrazí detail kavárny
                else if (action.equals("details")) {
                    int id = Integer.parseInt(btn.getId());
                    scroll.setContent(null);
                    scroll.setContent(createCafeDetail(id));
                    message.setText("");
                } //zobrazí formulář pro úpravy stávající kavárny
                else if (action.equals("updateCafe")) {
                    int id = Integer.parseInt(btn.getId());
                    scroll.setContent(null);
                    scroll.setContent(createCafeUpdate(id));
                    message.setText("");

                } //zabrazí okno pro možnost filtrování kaváren
                else if (action.equals("showFilter")) {
                    scroll.setContent(null);
                    scroll.setContent(createFilter());
                    message.setText("");
                } //zabrazí možnost filtrace
                else if (action.equals("filterName")) {
                    FlowPane result = filterOption.getNameOption();
                    filterArea.getChildren().clear();
                    filterArea.getChildren().add(result);
                } //zabrazí možnost filtrace
                else if (action.equals("filterCity")) {
                    FlowPane result = filterOption.getCityOption();
                    filterArea.getChildren().clear();
                    filterArea.getChildren().add(result);
                } //zabrazí možnost filtrace
                else if (action.equals("filterRating")) {
                    FlowPane result = filterOption.getRatingOption();
                    filterArea.getChildren().clear();
                    filterArea.getChildren().add(result);
                } //submitne filtraci vyžádanou uživatelem
                else if (action.equals("processedFilter")) {
                    String active = filterOption.getActive();
                    String value = "";

                    if (active.equals("name")) {
                        value = filterOption.getFilterName().getText();
                    } else if (active.equals("city")) {
                        value = filterOption.getComboBox().getValue().toString();
                    } else if (active.equals("rating")) {
                        value = filterOption.getFilterRating().getText();
                        int intVal = Integer.parseInt(value);
                        if (intVal < 0 || intVal > 5) {
                            message.setText("Je možno zadat pouze hodnocení v rozmezí 1-5");
                            message.setTextFill(Color.rgb(210, 39, 30));
                            filterOption.getFilterRating().setText("");
                            value = "";
                        }
                    } else if (active.equals("NaN")) {
                        message.setText("Musíte vybrat filtr, dle kterého chcete vyhledávat");
                        message.setTextFill(Color.rgb(210, 39, 30));
                    }

                    if (!value.equals("")) {
                        writableArea.getChildren().clear();
                        writableArea.getChildren().add(createMainDashboard(1, active, value));
                        message.setText("");
                    }
                } //submitne nový rating pro kavárnu
                else if (action.equals("rating")) {
                    int id2 = Integer.parseInt(ratingArea.getId());
                    String textRating = ratingArea.getText();
                    message.setText("");

                    //obě hodnoty hodnocení musí být vyplněné
                    if (!textRating.equals("") && comboBox.getValue() != null) {
                        InsertNewRating insertRating = new InsertNewRating(id2);
                        insertRating.setTextRating(textRating);
                        insertRating.setIntRating(Integer.parseInt(comboBox.getValue().toString()));
                        if (insertRating.confirm()) {
                            message.setText("Hodnocení úspěšně zaznamenáno");
                            message.setTextFill(Color.rgb(39, 210, 30));
                        } else {
                            message.setText("Hodnocení se nepodařilo zapsat");
                            message.setTextFill(Color.rgb(210, 39, 30));
                        }

                        //rozřazení, zda již kavárna má hodnocení. Pokud ne, vytvoří se nová instance CafeRating, pokud
                        //ano, přidá se hodnocení do stávající
                        if (main.getRatingList().getHodnoceni().containsKey(ratingArea.getId())) {
                            CafeRating cafe = main.getRatingList().getHodnoceni().get(ratingArea.getId());
                            cafe.getRatingText().add(textRating);
                            cafe.getRatingInt().add(comboBox.getValue().toString());
                            cafe.setCafeId(id2);
                        } else {
                            CafeRating cafe = new CafeRating(id2);
                            main.getRatingList().getHodnoceni().put(ratingArea.getId(), cafe);

                            cafe.getRatingText().add(textRating);
                            cafe.getRatingInt().add(comboBox.getValue().toString());
                        }

                        comboBox.setValue(null);
                        comboBox.setId("-1");
                        ratingArea.setId("-1");
                        ratingArea.setText("");
                    } else {
                        message.setText("Vyplňte oba typy hodnocení");
                        message.setTextFill(Color.rgb(210, 39, 30));
                    }

                }
                //požádá o možnost správce
                else if (action.equals("requestAdmin")) {
                    Alert alert = new Alert(AlertType.CONFIRMATION);
                    alert.setTitle("Žádost správce");
                    //uživatel již požádal o možnost správcování
                    if (main.getSignedUser().getProcessed() == 1) {
                        alert.setHeaderText("Již jste požádali o možnost správcování");
                        alert.setContentText("Vyčkejte, dokud master neschválí vaši žádost");
                        alert.showAndWait();
                    } //uživatel již má možnost správcování
                    else if (main.getSignedUser().getProcessed() == 2) {
                        alert.setHeaderText("Již máte možnost správcování");
                        alert.setContentText("Feel free to administrate");
                        alert.showAndWait();
                    } //pokud uživatel stiskne OK, do db se propíše žádost o správcování
                    else {
                        alert.setHeaderText("Tímto požádáte o možnost správcování");
                        alert.setContentText("Opravdu se chcete stát správcem?");
                        Optional<ButtonType> result = alert.showAndWait();

                        if (result.get() == ButtonType.OK) {
                            Database db = Database.getInstance();
                            db.databazovaFunkce("UPDATE", "UPDATE users SET processed=1 where id=" + main.getSignedUser().getId());
                            main.getSignedUser().setProcessed(1);
                        }
                    }

                } //umožní uživatel správcovat
                else if (action.equals("confirmAdmin")) {
                    Database db = Database.getInstance();
                    db.databazovaFunkce("UPDATE", "UPDATE users SET processed=2, admin=1 where id=" + Integer.parseInt(btn.getId()));
                }

            }
        });
    }

    /**
     * metoda nastaví novou šířku objektu a výška zůstane ve správném poměru k
     * původnímu poměru objektu
     *
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
