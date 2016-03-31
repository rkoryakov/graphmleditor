/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.itsc.graphmleditor.data.services;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.scene.Group;
import ru.itsc.backend.MetamapService;
import ru.itsc.graphml.Graph;
import ru.itsc.graphml.GraphMLGenerator;
import ru.itsc.graphml.GraphMLParser;

/**
 * Save GraphML to local 
 * @author koryakov.rv
 */
public class SaveGraphmlFileService extends Service<String> {

    private final File file;
    private final Group group;
    
    public static final String MESSAGE_TEXT1 = "Preparing data to save...";
    public static final String MESSAGE_TEXT2 = "Saving data...";
    public static final String TITLE_TEXT1 = "Save GraphMl";
    
    public SaveGraphmlFileService(File out, Group group) {
        this.file = out;
        this.group = group;
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
        return new SaveGraphmlTask(file, group);
    }

    public class SaveGraphmlTask extends Task<String> {

        private final File file;
        private final Group group;
        
        public SaveGraphmlTask(File out, Group group) {
            this.file = out;
            this.group = group;
        }
        
        @Override
        protected String call() throws Exception {
            updateTitle(TITLE_TEXT1);
            updateMessage(MESSAGE_TEXT1);
            
            String graph = GraphMLGenerator.getGraphMl(group);
            Thread.sleep(500);
            
            updateMessage(MESSAGE_TEXT2);
            OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream(file));
            osw.write(graph);
            osw.close();
            Thread.sleep(500);
            
            return graph;
        }
    }
}
