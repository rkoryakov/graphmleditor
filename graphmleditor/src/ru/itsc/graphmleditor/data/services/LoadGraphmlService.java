/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.itsc.graphmleditor.data.services;

import javafx.concurrent.Service;
import javafx.concurrent.Task;
import ru.itsc.backend.MetamapService;
import ru.itsc.graphml.Graph;
import ru.itsc.graphml.GraphMLParser;

/**
 *
 * @author Koryakov.RV
 */
public class LoadGraphmlService extends Service<Graph> {

    private String id;
    private String name;
    private String host;
    public static final String MESSAGE_TEXT1 = "Loading data from ";
    public static final String MESSAGE_TEXT2 = "Parsing data into graphical representation...";
    public static final String TITLE_TEXT1 = "Building Metamap";
    
    public LoadGraphmlService(String id, String name, String host) {
        this.id = id;
        this.name = name;
        this.host = host;
    }
    /**
     * Create and return the task for fetching the data. Note that this method
     * is called on the background thread (all other code in this application is
     * on the JavaFX Application Thread!).
     *
     * @return A task
     */
    @Override
    protected Task createTask() {
        return new LoadGraphmlTask(id, name, host);
    }

    public class LoadGraphmlTask extends Task<Graph> {

        private final String id;
        private final String name;
        private final String host;
        
        public LoadGraphmlTask(String id, String name, String host) {
            this.id = id;
            this.name = name;
            this.host = host;
        }
        
        @Override
        protected Graph call() throws Exception {
            updateTitle(TITLE_TEXT1);
            updateMessage(MESSAGE_TEXT1 + host);
            MetamapService service = new MetamapService(host);
            String graphml = service.getMetamapBeanPort().retrieveGraphMlPi(id, name);
            Thread.sleep(500);
            updateMessage(MESSAGE_TEXT2);
            GraphMLParser parser = new GraphMLParser();
            Graph graph = parser.parse(graphml);
            Thread.sleep(500);
            return graph;
        }
    }
}

   