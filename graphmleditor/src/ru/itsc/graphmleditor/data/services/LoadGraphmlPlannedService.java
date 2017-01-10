/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.itsc.graphmleditor.data.services;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.LongProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import ru.itsc.backend.plannedmap.PlannedMetamap;
import ru.itsc.backend.plannedmap.PlannedMetamapService;
import ru.itsc.backend.plannedmap.PlannedMetamapService_Service;
import ru.itsc.graphml.Graph;
import ru.itsc.graphml.GraphMLParser;

/**
 * This background service is getting data from WS PlannedMetamapService. LoadGraphmlPlannedService class 
 * has several properties, such a startDate, endDate, id, rows.. All these members are input parameters of 
 * Web Service PlannedMetamapService (see {@link PlannedMetamapService}). There are all the tasks implementations 
 * for each WS operation here.
 * 
 * @author Koryakov.RV
 */
public class LoadGraphmlPlannedService extends Service<Graph> {
	public static final String MESSAGE_TEXT1 = "Loading data from ";
    public static final String MESSAGE_TEXT2 = "Parsing data into graphical representation...";
    public static final String MESSAGE_CANCELLED = "Cancelled";
    public static final String TITLE_TEXT1 = "Building Metamap";
    
    // WS LoadGraphmlPlannedService's input parameters
	private ObjectProperty<LocalDate> startDate = new SimpleObjectProperty<>(this, "startDate");
	private ObjectProperty<LocalDate> endDate = new SimpleObjectProperty<>(this, "endDate");
	private LongProperty id = new SimpleLongProperty(this, "id");
	private IntegerProperty rows = new SimpleIntegerProperty(this, "rows");
	
	private final ObjectProperty<PlannedMetamapOperation> operation = new SimpleObjectProperty<>();
	private final ObjectProperty<URI> host = new SimpleObjectProperty<>();
	
	
	/**
     * Create and return the task for fetching the data. Note that this method
     * is called on the background thread (all other code in this application is
     * on the JavaFX Application Thread!).
     *
     * @return A task
     */
	@Override
	protected Task<Graph> createTask() {
		Task<Graph> task = null;
		// TODO: create corresponding method for each task
		switch (getOperation()) {
		case LAST_MAP:
			task = getLastPlannedMetamapTask();
			break;
		case BY_PERIOD:
			break;
		case BY_LIMIT:
			break;
		case BY_ID:
			break;
		default:
			
		}
		
		return task;
	}
    
	private Task<Graph> getLastPlannedMetamapTask() {
		return new LastPlannedMetamapTask();
	}
	
	/**
	 * Task background implementation of WS operation getLastPlanedMetamap
	 */
	public class LastPlannedMetamapTask extends Task<Graph> {
		@Override
		/**
		 * @return parsed graph object
		 */
		protected Graph call() throws Exception {
			updateTitle(TITLE_TEXT1);
			updateMessage(MESSAGE_TEXT1 + host);
			PlannedMetamapService_Service service = new PlannedMetamapService_Service(host.get().toString());
			PlannedMetamapService port = service.getPlanedMetamapPort();
			PlannedMetamap map = port.getLastPlanedMetamap();
			
			if (sleep(500, this)) return null;
			
			updateMessage(MESSAGE_TEXT2);
			GraphMLParser parser = new GraphMLParser();
            Graph graph = parser.parse(map.getMap());
           
            sleep(500, this);  

			return graph;
		}
	}
	
	/**
	 * From this id by rows limit
	 * Task background implementation of WS operation getPlanedMetamapsByLimit
	 */
	public class MetamapsByLimitTask extends Task<List<PlannedMetamap>> {

		@Override
		protected List<PlannedMetamap> call() throws Exception {
			if (getId() <= 0 || getRows() <= 0) {
				throw new IllegalArgumentException("id = " + id + ", rows = " + rows);
			}
			updateTitle(TITLE_TEXT1);
			updateMessage(MESSAGE_TEXT1 + getHost().getHost());
			PlannedMetamapService_Service service = new PlannedMetamapService_Service(getHost().getHost());
			PlannedMetamapService port = service.getPlanedMetamapPort();
			List<PlannedMetamap> result = port.getPlanedMetamapsByLimit(getId(), getRows());
			sleep(500, this); 
			updateMessage(MESSAGE_TEXT2);
            sleep(500, this);  
            
			return result;
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
	
	/**
	 *  WS operations
	 */
	public enum PlannedMetamapOperation {
		LAST_MAP,
		BY_PERIOD,
		BY_LIMIT,
		BY_ID,
		BY_TEMPL
	}

	public final ObjectProperty<LocalDate> startDateProperty() {
		return this.startDate;
	}
	

	public final LocalDate getStartDate() {
		return this.startDateProperty().get();
	}
	

	public final void setStartDate(final LocalDate startDate) {
		this.startDateProperty().set(startDate);
	}
	

	public final ObjectProperty<LocalDate> endDateProperty() {
		return this.endDate;
	}
	

	public final LocalDate getEndDate() {
		return this.endDateProperty().get();
	}
	

	public final void setEndDate(final LocalDate endDate) {
		this.endDateProperty().set(endDate);
	}
	

	public final LongProperty idProperty() {
		return this.id;
	}
	

	public final long getId() {
		return this.idProperty().get();
	}
	

	public final void setId(final long id) {
		this.idProperty().set(id);
	}
	

	public final IntegerProperty rowsProperty() {
		return this.rows;
	}
	

	public final int getRows() {
		return this.rowsProperty().get();
	}
	

	public final void setRows(final int rows) {
		this.rowsProperty().set(rows);
	}
	

	public final ObjectProperty<PlannedMetamapOperation> operationProperty() {
		return this.operation;
	}
	

	public final PlannedMetamapOperation getOperation() {
		return this.operationProperty().get();
	}
	

	public final void setOperation(final PlannedMetamapOperation operation) {
		this.operationProperty().set(operation);
	}
	

	public final ObjectProperty<URI> hostProperty() {
		return this.host;
	}
	

	public final URI getHost() {
		return this.hostProperty().get();
	}
	

	public final void setHost(final URI host) {
		this.hostProperty().set(host);
	}
	
}
