
package ru.itsc.graphmleditor.config;


import java.net.URL;
import java.net.URLConnection;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Window;
import ru.itsc.commons.message.MessageBox;
import ru.itsc.commons.Utils;
import ru.itsc.graphmleditor.GraphMLEditor;

public class SettingsDialog extends VBox {
    private final Button saveBtn;
    private final Window owner;
    private final EndPointPanel endPointPanel;
    private final ProxyPanel proxyPanel;
    private final Tab proxyTab;
    private final Tab wsdlTab;
    private final TabPane options;

    public SettingsDialog(final Window owner) {
        this.owner = owner;
        
        setId("SettingsDialog");
        setSpacing(10);
        setMaxSize(530, USE_PREF_SIZE);
        // block mouse clicks
        setOnMouseClicked((MouseEvent t) -> {
            t.consume();
        });

        Text explanation = new Text("Network connection is required to "
                + "access graph data");
        explanation.setWrappingWidth(400);

        BorderPane explPane = new BorderPane();
        VBox.setMargin(explPane, new Insets(5, 5, 5, 5));
        explPane.setCenter(explanation);
        BorderPane.setMargin(explanation, new Insets(5, 5, 5, 5));

        // create title
        Label title = new Label("Application settings");
        title.setId("title");
        title.setMaxWidth(Double.MAX_VALUE);
        title.setAlignment(Pos.CENTER);
        getChildren().add(title);

        proxyTab = new Tab("Set Proxy");
        proxyPanel = new ProxyPanel();
        proxyTab.setContent(proxyPanel);

        wsdlTab = new Tab("Set Endpoint");
        endPointPanel = new EndPointPanel();
        wsdlTab.setContent(endPointPanel);

        options = new TabPane();
        options.getStyleClass().add(TabPane.STYLE_CLASS_FLOATING);
        options.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);
        options.getTabs().addAll(wsdlTab, proxyTab);

        Button cancelBtn = new Button("Cancel");
        cancelBtn.setId("cancelButton");
        cancelBtn.setOnAction((ActionEvent actionEvent) -> {
            GraphMLEditor.getController().hideModalMessage();
        });
        
        cancelBtn.setMinWidth(74);
        cancelBtn.setPrefWidth(74);
        HBox.setMargin(cancelBtn, new Insets(0, 8, 0, 0));
        saveBtn = new Button("Save");
        saveBtn.setId("saveButton");
        saveBtn.setDefaultButton(true);
        saveBtn.setDisable(true);
        saveBtn.setOnAction((ActionEvent actionEvent) -> {
            try {
                saveSettings();
            } catch (Exception ex) {
                Logger.getLogger(SettingsDialog.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
            }
            GraphMLEditor.getController().hideModalMessage();
        });
        saveBtn.setMinWidth(74);
        saveBtn.setPrefWidth(74);

        HBox bottomBar = new HBox(0);
        bottomBar.setAlignment(Pos.BASELINE_RIGHT);
        bottomBar.getChildren().addAll(cancelBtn, saveBtn);
        VBox.setMargin(bottomBar, new Insets(20, 5, 5, 5));
        getChildren().addAll(explPane, options, bottomBar);
    }
    
    public final void saveSettings() throws Exception {
        final Properties settings = new Properties();
        String host = proxyPanel.getHostNameBox().getText();
        String port = proxyPanel.getPortBox().getText();
        String wsdl = endPointPanel.getEndPoinLocation();
        String metamapId = endPointPanel.getIdParam();
        String metamapName = endPointPanel.getNameParam();
        
        settings.put(PropertiesManager.PROXY_ADDRESS, host == null ? "" : host);
        settings.put(PropertiesManager.PROXY_PORT, port == null ? "" : port);
        settings.put(PropertiesManager.SERVER_HOST, wsdl == null ? "" : wsdl);
        settings.put(PropertiesManager.METAMAP_ID, metamapId == null ? "" : metamapId);
        settings.put(PropertiesManager.METAMAP_NAME, metamapName == null ? "" : metamapName);
        setWebProxy(proxyPanel.getHostNameBox().getText(), proxyPanel.getPortBox().getText());
        PropertiesManager.saveProperties(settings);
    }
    
    /**
     * Load settings from properties
     */
    public void loadSettings() {
        proxyPanel.hostNameBox.setText(PropertiesManager.getPropertyValue(PropertiesManager.PROXY_ADDRESS));
        proxyPanel.portBox.setText(PropertiesManager.getPropertyValue(PropertiesManager.PROXY_PORT));
        endPointPanel.setEndPointLocation(PropertiesManager.getPropertyValue(PropertiesManager.SERVER_HOST));
        endPointPanel.setIdParam(PropertiesManager.getPropertyValue(PropertiesManager.METAMAP_ID));
        endPointPanel.setNameParam(PropertiesManager.getPropertyValue(PropertiesManager.METAMAP_NAME));
        
        setWebProxy(proxyPanel.hostNameBox.getText(), proxyPanel.portBox.getText());
    }

    public void setWebProxy(String hostName, String port) {
        if (hostName != null) {
            System.setProperty("http.proxyHost", hostName);
            if (port != null) {
                System.setProperty("http.proxyPort", port);
            }
        }
    }

    private class ProxyPanel extends GridPane {
        TextField hostNameBox;
        TextField portBox;

        public ProxyPanel() {
            setPadding(new Insets(8));
            setHgap(5.0F);
            setVgap(5.0F);

            int rowIndex = 0;

            Label label2 = new Label("Host Name");
            GridPane.setConstraints(label2, 0, rowIndex);

            Label label3 = new Label("Port");
            GridPane.setConstraints(label3, 1, rowIndex);
            getChildren().addAll(label2, label3);

            rowIndex++;
            hostNameBox = new TextField();
            hostNameBox.setPromptText("proxy.mycompany.com");
            hostNameBox.setPrefColumnCount(20);
            GridPane.setConstraints(hostNameBox, 0, rowIndex);

            portBox = new TextField();
            portBox.setPromptText("8080");
            portBox.setPrefColumnCount(10);
            GridPane.setConstraints(portBox, 1, rowIndex);

            ChangeListener<String> textListener = (ObservableValue<? extends String> ov, String t, String t1) -> {
                saveBtn.setDisable(
                        hostNameBox.getText() == null || hostNameBox.getText().isEmpty()
                                || portBox.getText() == null || portBox.getText().isEmpty());
            };
            hostNameBox.textProperty().addListener(textListener);
            portBox.textProperty().addListener(textListener);

            getChildren().addAll(hostNameBox, portBox);
        }

        public TextField getHostNameBox() {
            return hostNameBox;
        }

        public TextField getPortBox() {
            return portBox;
        }
    }

    private class EndPointPanel extends GridPane {
        private final TextField host;
        private final TextField userName;
        private final PasswordField userPassword;
        private final TextField id;
        private final TextField name;
        
        private static final String WRONG_FIELD_STYLE = "-fx-background-color: linear-gradient(to bottom right, #FFFFFF, #ff6666, #FFFFFF)";
        private static final String CORRECT_FIELD_STYLE = "-fx-background-color: #FFFFFF";
        
        public EndPointPanel() {
            setPadding(new Insets(8));
            setHgap(5.0F);
            setVgap(5.0F);

            int rowIndex = 0;
            Label endPointLabel = new Label("Endpoint configuration");
            endPointLabel.setId("parent-dir-label");
            GridPane.setConstraints(endPointLabel, 0, rowIndex, 3, 1);
            getChildren().add(endPointLabel);

            rowIndex ++;
            Label hostLabel = new Label("Host&port:");
            GridPane.setConstraints(hostLabel, 0, rowIndex);
            
            host = new TextField();
            host.setPrefColumnCount(25);
            host.setPromptText("http://example.com");
            GridPane.setConstraints(host, 1, rowIndex, 1, 1);
            host.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
               saveBtn.setDisable(host.getText() == null || host.getText().isEmpty() || !Utils.isCorrectUrl(host.getText()));
               if (Utils.isCorrectUrl(host.getText())) {
                   host.setStyle(CORRECT_FIELD_STYLE);
               } else {
                   host.setStyle(WRONG_FIELD_STYLE);
               }
            });
            
            Button ping = new Button("Test");
            GridPane.setConstraints(ping, 2, rowIndex);
            ping.setOnAction((ActionEvent actionEvent) -> {
                try {
                    URL url = new URL(host.getText());
                    URLConnection urlConnection = url.openConnection();
                    urlConnection.connect();

                    if (urlConnection.getContent() != null) {
                        MessageBox.show(owner,
                                "The connection was established",
                                "Information", MessageBox.ICON_INFORMATION | MessageBox.OK);
                    } 
                } catch (Exception e) {
                    MessageBox.show(owner,
                            "The connection wasn't established!\n"
                            + e.getLocalizedMessage(),
                            "Information", MessageBox.ICON_WARNING | MessageBox.OK);
                    Logger.getLogger(SettingsDialog.class.toString()).log(Level.SEVERE, e.getMessage());
                }
            });
            getChildren().addAll(hostLabel, host, ping);
            
            rowIndex ++;
            Label userLabel = new Label("User:");
            GridPane.setConstraints(userLabel, 0, rowIndex);
            userName = new TextField();
            userName.setPromptText("Guest");
            GridPane.setConstraints(userName, 1, rowIndex);
            getChildren().addAll(userLabel, userName);
            
            rowIndex ++;
            Label passwordLabel = new Label("Password:");
            GridPane.setConstraints(passwordLabel, 0, rowIndex);
            userPassword = new PasswordField();
            userPassword.setPromptText("User Password");
            GridPane.setConstraints(userPassword, 1, rowIndex);
            getChildren().addAll(passwordLabel, userPassword);
            
            rowIndex ++;
            Label paramsLabel = new Label("Metamap invocation parameters:");
            GridPane.setConstraints(paramsLabel, 0, rowIndex, 3, 1);
            getChildren().add(paramsLabel);
            
            rowIndex ++;
            Label idLabel = new Label("ID:");
            GridPane.setConstraints(idLabel, 0, rowIndex);
            id = new TextField();
            id.setPromptText("Id value");
            GridPane.setConstraints(id, 1, rowIndex);
            getChildren().addAll(idLabel, id);
                    
            rowIndex ++;
            Label nameLabel = new Label("Name:");
            GridPane.setConstraints(nameLabel, 0, rowIndex);
            name = new TextField();
            name.setPromptText("Name value");
            GridPane.setConstraints(name, 1, rowIndex);
            getChildren().addAll(nameLabel, name); 
        }

        public String getEndPoinLocation() {
            return host.getText();
        }
        
        public void setEndPointLocation(String location) {
            host.setText(location);
        }
        
        public String getIdParam() {
            return id.getText();
        }
        
        public String getNameParam() {
            return name.getText();
        }
        
        public void setIdParam(String id) {
            this.id.setText(id);
        }
        
        public void setNameParam(String name) {
            this.name.setText(name);
        }
    }
}
