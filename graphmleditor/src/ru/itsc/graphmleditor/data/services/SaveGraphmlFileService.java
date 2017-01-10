/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.itsc.graphmleditor.data.services;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;

import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.scene.Group;
import ru.itsc.graphml.GraphMLGenerator;

/**
 * Save GraphML to local 
 * @author koryakov.rv
 */
public class SaveGraphmlFileService extends Service<String> {

    private final File file;
    private final Group group;
    
    public static final String MESSAGE_TEXT1 = "Preparing data to save...";
    public static final String MESSAGE_TEXT2 = "Saving data...";
    public static final String MESSAGE_CANCELLED = "Cancelled";
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
    protected Task<String> createTask() {
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
            try {
            	Thread.sleep(500);
            } catch(InterruptedException ie) {
            	if (this.isCancelled()) {
            		this.updateMessage(MESSAGE_CANCELLED);
            	}
            	return graph;
            }
            
            updateMessage(MESSAGE_TEXT2);
            
            try (OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream(file), Charset.forName("utf-8"))) {
            	osw.write(graph);
            	osw.close();
            }
            
            try {
            	Thread.sleep(500);
            } catch(InterruptedException ie) {
            	if (this.isCancelled()) {
            		this.updateMessage(MESSAGE_CANCELLED);
            	}
            	return graph;
            }
            
            return graph;
        }
    }
}
