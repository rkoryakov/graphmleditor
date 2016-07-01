/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.itsc.graphmleditor.data.services;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import ru.itsc.backend.MetamapService;
import ru.itsc.graphml.Graph;
import ru.itsc.graphml.GraphMLParser;


/**
 * The background service contains different Tasks (invocations of WS operations) to creating 
 * @author Koryakov.RV
 */
public class LoadGraphmlService extends Service<Graph> {

    private StringProperty id = new SimpleStringProperty(this, "id");
    private StringProperty name = new SimpleStringProperty(this, "name");
    private StringProperty host = new SimpleStringProperty(this, "host");
    
    public static final String MESSAGE_TEXT1 = "Loading data from ";
    public static final String MESSAGE_TEXT2 = "Parsing data into graphical representation...";
    public static final String MESSAGE_CANCELLED = "Cancelled";
    public static final String TITLE_TEXT1 = "Building Metamap";
    
    /**
     * Create and return the task for fetching the data. Note that this method
     * is called on the background thread (all other code in this application is
     * on the JavaFX Application Thread!).
     *
     * @return A task
     */
    @Override
    protected Task<Graph> createTask() {
        return new MetamapServiceTask();
    }

    /**
     * Web Service MetamapService invocation 
     * Task background implementation of WS operation retrieveGraphMlPi and parse its result to represent Graph object
     */
    public class MetamapServiceTask extends Task<Graph> {
        
        @Override
        protected Graph call() throws Exception {
            updateTitle(TITLE_TEXT1);
            updateMessage(MESSAGE_TEXT1 + host);
            MetamapService service = new MetamapService(getHost());
            String graphml = service.getMetamapBeanPort().retrieveGraphMlPi(getId(), getName());
            
            if (sleep(500, this)) return null;
            
            updateMessage(MESSAGE_TEXT2);
            GraphMLParser parser = new GraphMLParser();
            Graph graph = parser.parse(graphml);
            
            sleep(500, this);
            
            return graph;
        }
    }

	/**
	 * Stop task for several millis 
	 * @param millis
	 * @param task
	 * @return true if task has been cancelled, false otherwise
	 */
	private boolean sleep(int millis, Task<?> task) {
		try {
        	Thread.sleep(millis);
        } catch(InterruptedException ie) {
        	if (task.isCancelled()) {
        		return true;
        	}
        }
		return false;
	}
	
	public final StringProperty idProperty() {
		return this.id;
	}
	

	public final String getId() {
		return this.idProperty().get();
	}
	

	public final void setId(final String id) {
		this.idProperty().set(id);
	}
	

	public final StringProperty nameProperty() {
		return this.name;
	}
	

	public final String getName() {
		return this.nameProperty().get();
	}
	

	public final void setName(final String name) {
		this.nameProperty().set(name);
	}
	

	public final StringProperty hostProperty() {
		return this.host;
	}
	

	public final String getHost() {
		return this.hostProperty().get();
	}
	

	public final void setHost(final String host) {
		this.hostProperty().set(host);
	}
	
}

   