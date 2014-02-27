package modelmanager;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javax.persistence.Persistence;

import modelmanager.widget.XALSynopticFXPanel;

import org.openepics.model.api.BeamParameterAPI;
import org.openepics.model.api.BeamParameterPropAPI;
import org.openepics.model.api.ElementAPI;
import org.openepics.model.api.ElementPropAPI;
import org.openepics.model.api.ModelAPI;
import org.openepics.model.entity.BeamParameter;
import org.openepics.model.entity.BeamParameterProp;
import org.openepics.model.entity.ElementProp;
import org.openepics.model.entity.Lattice;
import org.openepics.model.entity.Model;
import org.openepics.model.extraEntity.BeamParams;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.JFXPanel;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.util.Callback;
import javafx.util.StringConverter;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import xal.extension.application.XalWindow;
import xal.extension.smf.application.AcceleratorDocument;
import xal.extension.widgets.apputils.SimpleProbeEditor;
import xal.extension.bricks.WindowReference;
import xal.tools.xml.XmlDataAdaptor;
import xal.tools.data.*;
import xal.model.probe.Probe;
import xal.model.probe.traj.EnvelopeProbeState;
import xal.model.probe.traj.IPhaseState;
import xal.sim.scenario.Scenario;
import xal.smf.AcceleratorNode;
import xal.extension.application.*;
import xal.smf.impl.RfCavity;
import xal.smf.impl.qualify.OrTypeQualifier;

public class ModelManagerDocument extends AcceleratorDocument implements DataListener, Initializable  {

 	/** the data adaptor label used for reading and writing this document */
	static public final String DATA_LABEL = "ModelManagerDocument";
	
	/** main window reference */
	final WindowReference WINDOW_REFERENCE;
    
    @FXML
    static CheckBox dbSupport;
    @FXML
    static Button modelParamEditor;
    @FXML
    static ComboBox<String> mSyncMode;
	@FXML
    static Button runModel;
    @FXML
    static Button saveModel;
    @FXML
    Button tagGoldModel;
    @FXML
    static MenuItem quit;
    
    // for data plot
    @FXML
    static private LineChart<Double, Double> modelDataPlot;
    
    // for device setting table    
    @FXML
    static TableView<Device> deviceSettingTable;
    @FXML
    static TableColumn<Device, String> deviceNameCol;
    @FXML
    static TableColumn<Device, String> positionCol;
    @FXML
    static TableColumn<Device, String> propertyNameCol;
    @FXML
    static TableColumn propertyValCol;
    // for model output data
    @FXML
    static TableView<Element> elementTable;
    @FXML
    static TableColumn<Element, String> elementCol;
    @FXML
    static TableColumn<Element, Double> posCol;
    @FXML
    static TableColumn<Element, Double> eCol;
    @FXML
    static TableColumn<Element, Double> phXCol;
    @FXML
    static TableColumn<Element, Double> phXPCol;
    @FXML
    static TableColumn<Element, Double> phYCol;
    @FXML
    static TableColumn<Element, Double> phYPCol;
    @FXML
    static TableColumn<Element, Double> phZCol;
    @FXML
    static TableColumn<Element, Double> phZPCol;
    @FXML
    static TableColumn<Element, Double> betaXCol;
    @FXML 
    static TableColumn<Element, Double> alphaXCol;
    @FXML
    static TableColumn<Element, Double> emitXCol;
    @FXML
    static TableColumn<Element, Double> betaYCol;
    @FXML 
    static TableColumn<Element, Double> alphaYCol;
    @FXML
    static TableColumn<Element, Double> emitYCol;
    @FXML
    static TableColumn<Element, Double> betaZCol;
    @FXML 
    static TableColumn<Element, Double> alphaZCol;
    @FXML
    static TableColumn<Element, Double> emitZCol;
    @FXML
    static TableColumn chromXCol;
    @FXML
    static TableColumn chromXPCol;
    @FXML
    static TableColumn chromYCol;
    @FXML
    static TableColumn chromYPCol;
    @FXML
    static TableColumn dispXCol;
    @FXML
    static TableColumn dispYCol;
    @FXML
    static TableColumn sigmaXCol;
    @FXML
    static TableColumn sigmaYCol;
    @FXML
    static TableColumn bunchLengthCol;
    
    // R-mat table
    @FXML
    static private TableView rMatTable;
    @FXML
    static TableColumn r11Col;
    @FXML
    static TableColumn r12Col;
    @FXML
    static TableColumn r13Col;
    @FXML
    static TableColumn r14Col;
    @FXML
    static TableColumn r15Col;
    @FXML
    static TableColumn r16Col;
    @FXML
    static TableColumn r21Col;
    @FXML
    static TableColumn r22Col;
    @FXML
    static TableColumn r23Col;
    @FXML
    static TableColumn r24Col;
    @FXML
    static TableColumn r25Col;
    @FXML
    static TableColumn r26Col;
    @FXML
    static TableColumn r31Col;
    @FXML
    static TableColumn r32Col;
    @FXML
    static TableColumn r33Col;
    @FXML
    static TableColumn r34Col;
    @FXML
    static TableColumn r35Col;
    @FXML
    static TableColumn r36Col;
    @FXML
    static TableColumn r41Col;
    @FXML
    static TableColumn r42Col;
    @FXML
    static TableColumn r43Col;
    @FXML
    static TableColumn r44Col;
    @FXML
    static TableColumn r45Col;
    @FXML
    static TableColumn r46Col;
    @FXML
    static TableColumn r51Col;
    @FXML
    static TableColumn r52Col;
    @FXML
    static TableColumn r53Col;
    @FXML
    static TableColumn r54Col;
    @FXML
    static TableColumn r55Col;
    @FXML
    static TableColumn r56Col;
    @FXML
    static TableColumn r61Col;
    @FXML
    static TableColumn r62Col;
    @FXML
    static TableColumn r63Col;
    @FXML
    static TableColumn r64Col;
    @FXML
    static TableColumn r65Col;
    @FXML
    static TableColumn r66Col;
    
    @FXML
    static TableView<SimModel> modelListTable;
    @FXML
    static TableColumn modelIdCol;
    @FXML
    static TableColumn dateCol;
    @FXML
    static TableColumn modelSrcCol;
    @FXML
    static TableColumn modeCol;
    @FXML
    static TableColumn commentsCol;
    @FXML
    static TableColumn goldCol;
    @FXML
    static TableColumn refCol;
    @FXML
    static TableColumn selCol;
    
    @FXML
    private TextField statusField;
    @FXML
    static MenuButton plotSelection;
    @FXML
    static Button modelQueryButton;
    @FXML
    static AnchorPane synopticPane;
//    @FXML
    static XALSynopticFXPanel xalSynopticPane;
    @FXML
    static AnchorPane dataDisplayPane;
    @FXML
    static NumberAxis xAxis;
    @FXML 
    static NumberAxis yAxis;
    
    boolean dbInd = false;
    boolean validModel = false;
    
    @FXML
    MenuItem kE;
    @FXML
    MenuItem phX;
    @FXML
    MenuItem phXP;
    @FXML
    MenuItem phY;
    @FXML
    MenuItem phYP;
    @FXML
    MenuItem phZ;
    @FXML
    MenuItem phZP;
    @FXML
    MenuItem betaX;
    @FXML
    MenuItem alphaX;
    @FXML
    MenuItem emitX;
    @FXML
    MenuItem betaY;
    @FXML
    MenuItem alphaY;
    @FXML
    MenuItem emitY;
    @FXML
    MenuItem betaZ;
    @FXML
    MenuItem alphaZ;
    @FXML
    MenuItem emitZ;
    @FXML
    TextField fromDateField;
    @FXML
    TextField toDateField;
    
    ArrayList<org.openepics.model.extraEntity.Device> devices = new ArrayList<>();
    
    // Twiss data
	final ObservableList<Element> twissData = FXCollections.observableArrayList();
	
	// for JPA
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("modelAPIPU");    
    EntityManager em = emf.createEntityManager();

    /** main model */
    static final MachineModel MODEL = new MachineModel();
    
    static String selectedSequenceName = "";
    
	private Probe myProbe;
    
	Calendar fromDate = Calendar.getInstance();

	/** Empty Constructor */
    public ModelManagerDocument() {
        this( null );
    }
    
    /** 
     * Primary constructor 
     * @param url The URL of the file to load into the new document.
     */
    public ModelManagerDocument( final java.net.URL url ) {
        setSource( url );
		
		WINDOW_REFERENCE = getDefaultWindowReference( "MainWindow", this );
                				
		if ( url != null ) {
            System.out.println( "Opening document: " + url.toString() );
            final DataAdaptor documentAdaptor = XmlDataAdaptor.adaptorForUrl( url, false );
            update( documentAdaptor.childAdaptor( dataLabel() ) );
        }		
		
		configureWindow( WINDOW_REFERENCE );		
		
		setHasChanges( false );
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
		
    	// database support radio button
    	assert dbSupport != null : "fx:id=\"dbSupport\" was not injected: check your FXML file 'modelDisplay.fxml'.";
		if (dbSupport != null) {
			dbSupport.selectedProperty().addListener(
					new ChangeListener<Boolean>() {
						public void changed(
								ObservableValue<? extends Boolean> ov,
								Boolean old_val, Boolean new_val) {
							dbInd = new_val;
							if (validModel) {
								saveModel.setDisable(!dbInd);
								tagGoldModel.setDisable(!dbInd);
							}
							modelQueryButton.setDisable(!dbInd);
						}
					});
		}
		
		// probe editor
    	assert modelParamEditor != null : "fx:id=\"modelParamEditor\" was not injected: check your FXML file 'modelDisplay.fxml'.";
		if (modelParamEditor != null) {
            modelParamEditor.setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent event) {
                    //TODO need to update lattice first
    				SimpleProbeEditor spe;//
                    if( MODEL.getSimulator().getScenario() != null ) {
                        // if model has a probe
                        if (MODEL.getSimulator().getScenario().getProbe() != null) {
                            //reset the probe to initial state
                        	MODEL.getSimulator().getScenario().resetProbe();
                            spe = new SimpleProbeEditor(getMainWindow(), MODEL.getSimulator().getScenario().getProbe());
                            
                            myProbe = spe.getProbe();
                            MODEL.getSimulator().getScenario().setProbe(myProbe);
                            //spe.createSimpleProbeEditor(model.getProbe());
                            // if model has no probe
                        }
                    }
                    else {
                        //Sequence has not been selected
                        displayError("Probe Editor Error", "You must select a sequence before attempting to edit the probe.");
                    }
                	
                }
            });
        }
        modelParamEditor.setDisable(true);
		
    	assert modelQueryButton != null : "fx:id=\"dbSupport\" was not injected: check your FXML file 'modelDisplay.fxml'.";
		if (modelQueryButton != null) {
			modelQueryButton.setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent event) {
                	// TODO
                	// query and update model table
                	updateModelTable();
                }
            });
			
		}
		modelQueryButton.setDisable(!dbInd);
    	
    	
    	// mSourceSelection combo box
    	assert mSyncMode != null : "fx:id=\"mSourceSelection\" was not injected: check your FXML file 'modelDisplay.fxml'.";
    	if (mSyncMode != null) {
    		// disable it at init to avoid accidentally click and NullPointerException
    		mSyncMode.setDisable(true);
            mSyncMode.getSelectionModel().selectedItemProperty().addListener(
                    new ChangeListener<String>() {
                         public void changed(ObservableValue<? extends String> ov, 
                             String old_val, String new_val) {
                             
                             //Change the model sync mode according to the selection
                        	 //TODO
                        	 String newMode = Scenario.SYNC_MODE_DESIGN;
                        	 System.out.println(mSyncMode.getSelectionModel().getSelectedItem() + " selected.");
                        	 switch (mSyncMode.getSelectionModel().getSelectedIndex()) {
                        		 case (0):
                        			 break;
                        		 case (1):
                        			 newMode = Scenario.SYNC_MODE_LIVE;
                        			 break;
                        		 case(2):
                        			 newMode = Scenario.SYNC_MODE_RF_DESIGN;
                        			 break;
                        		 default:
                        			 break;
                        	 }
                        			 
                        	 MODEL.getSimulator().getScenario().setSynchronizationMode(newMode);
                     }
                 });
    	}
    	
    	// runModel button
        assert runModel != null : "fx:id=\"runModel\" was not injected: check your FXML file 'modelDisplay.fxml'.";        
        if (runModel != null) {
            runModel.setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent event) {
                    System.out.println( "running the model..." );
                    //TODO need to update lattice first
                    final MachineSimulation simulation = MODEL.runSimulation();
                    // model data is ready
                    validModel = true;
                    // update DB related buttons' availability
                    if (dbInd && validModel) {
                    	saveModel.setDisable(!dbInd);
                    	tagGoldModel.setDisable(!dbInd);
                    }
                    updateData();
                }
            });
        }
        runModel.setDisable(true);
        
        // saveModel button
        assert saveModel != null : "fx:id=\"saveModel\" was not injected: check your FXML file 'modelDisplay.fxml'.";
        if (saveModel != null) {
        	saveModel.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent arg0) {
					statusField.setText("saving model to database...");
					System.out.println("saving model to database...");
					saveModelData2DB();
				}
        		
        	});
        }        
        saveModel.setDisable(true);

        // tagGodlModel button
        assert tagGoldModel != null : "fx:id=\"tagGoldModel\" was not injected: check your FXML file 'modelDisplay.fxml'.";
        if (tagGoldModel != null) {
        	tagGoldModel.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent arg0) {
					System.out.println("saving model to database...");
					// TODO Auto-generated method stub
				}
        		
        	});
        }        
        tagGoldModel.setDisable(true);        
        
        // the "quit" action"
        assert quit != null : "fx:id=\"quit\" was not injected: check your FXML file 'modelDisplay.fxml'.";        
        if (quit != null) {
            quit.setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent event) {
                    System.exit(0);
                }
            });
        }
                

        // initialize device setting table
        assert deviceSettingTable != null : "fx:id=\"deviceSettingTable\" was not injected: check your FXML file 'modelDisplay.fxml'.";
        if (deviceSettingTable != null) {
        	deviceSettingTable.setEditable(true);
        }
        deviceNameCol.setCellValueFactory(
                new PropertyValueFactory<Device, String>("deviceName"));
        positionCol.setCellValueFactory(
                new PropertyValueFactory<Device, String>("pos"));
        propertyNameCol.setCellValueFactory(
                new PropertyValueFactory<Device, String>("propName"));
        propertyValCol.setCellValueFactory(
                new PropertyValueFactory<Device, String>("propVal"));
        propertyValCol.setCellFactory(TextFieldTableCell.forTableColumn());
        propertyValCol.setOnEditCommit(
                new EventHandler<CellEditEvent<Device, String>>() {
                    @Override
                    public void handle(CellEditEvent<Device, String> t) {
                        ((Device) t.getTableView().getItems().get(
                            t.getTablePosition().getRow())
                            ).setPropVal(t.getNewValue());
                    }
                }
            );
       
        // initialize model element table
        assert elementTable != null : "fx:id=\"elementTable\" was not injected: check your FXML file 'modelDisplay.fxml'.";
        if (elementTable != null) {
        	elementTable.setEditable(false);
        }
        
    	assert synopticPane != null: "fx:id=\"synopticPane\" was not injected: check your FXML file 'modelDisplay.fxml'.";
    	if (synopticPane != null) {
//    		synopticPane = new AnchorPane();
    		xalSynopticPane = new XALSynopticFXPanel();
    		synopticPane.getChildren().add(xalSynopticPane);
    	}
        
        elementCol.setCellValueFactory(new PropertyValueFactory<Element, String>("elementName"));
//        elementCol.setMinWidth(180);
        posCol.setCellValueFactory(new PropertyValueFactory<Element, Double>("pos"));
        eCol.setCellValueFactory(new PropertyValueFactory<Element, Double>("energy"));
        phXCol.setCellValueFactory(new PropertyValueFactory<Element, Double>("phX"));
        phXPCol.setCellValueFactory(new PropertyValueFactory<Element, Double>("phXP"));
        phYCol.setCellValueFactory(new PropertyValueFactory<Element, Double>("phY"));
        phYPCol.setCellValueFactory(new PropertyValueFactory<Element, Double>("phYP"));
        phZCol.setCellValueFactory(new PropertyValueFactory<Element, Double>("phZ"));
        phZPCol.setCellValueFactory(new PropertyValueFactory<Element, Double>("phZP"));
        betaXCol.setCellValueFactory(new PropertyValueFactory<Element, Double>("betaX"));
        alphaXCol.setCellValueFactory(new PropertyValueFactory<Element, Double>("alphaX"));
        emitXCol.setCellValueFactory(new PropertyValueFactory<Element, Double>("emitX"));
        betaYCol.setCellValueFactory(new PropertyValueFactory<Element, Double>("betaY"));
        alphaYCol.setCellValueFactory(new PropertyValueFactory<Element, Double>("alphaY"));
        emitYCol.setCellValueFactory(new PropertyValueFactory<Element, Double>("emitY"));
        betaZCol.setCellValueFactory(new PropertyValueFactory<Element, Double>("betaZ"));
        alphaZCol.setCellValueFactory(new PropertyValueFactory<Element, Double>("alphaZ"));
        emitZCol.setCellValueFactory(new PropertyValueFactory<Element, Double>("emitZ"));
        
        // for data display selections
        phX.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
            	updateDataPlot("phX");
            }
        });        
        phXP.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
            	updateDataPlot("phXP");
            }
        });        
        phY.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
            	updateDataPlot("phY");
            }
        });        
        phYP.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
            	updateDataPlot("phYP");
            }
        });        
        phZ.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
            	updateDataPlot("phZ");
            }
        });        
        phZP.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
            	updateDataPlot("phZP");
            }
        });        
        betaX.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
            	updateDataPlot("bataX");
            }
        });        
        alphaX.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
            	updateDataPlot("alphaX");
            }
        });        
        emitX.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
            	updateDataPlot("emitX");
            }
        });        
        betaY.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
            	updateDataPlot("bataY");
            }
        });        
        alphaY.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
            	updateDataPlot("alphaY");
            }
        });        
        emitY.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
            	updateDataPlot("emitY");
            }
        });        
        betaZ.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
            	updateDataPlot("bataZ");
            }
        });        
        alphaZ.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
            	updateDataPlot("alphaZ");
            }
        });        
        emitZ.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
            	updateDataPlot("emitZ");
            }
        });  
        kE.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
            	updateDataPlot("kE");
            }
        });  
        
        Callback<TableColumn<Map, String>, TableCell<Map, String>>
        cellFactoryForMap = new Callback<TableColumn<Map, String>,
        TableCell<Map, String>>() {
        	@Override
        	public TableCell call(TableColumn p) {
        		return new TextFieldTableCell(new StringConverter() {
        			@Override
        			public String toString(Object t) {
        				return t.toString();
        			}
        			@Override
        			public Object fromString(String string) {
        				return string;
        			}                                    
        		});
        	}
        };
        r11Col.setCellFactory(cellFactoryForMap);
        r12Col.setCellFactory(cellFactoryForMap);

        
        // initialize model element table
        assert fromDateField != null : "fx:id=\"fromDateField\" was not injected: check your FXML file 'modelDisplay.fxml'.";
        if (fromDateField != null) {
        	//TODO
        	fromDate.add(Calendar.MONTH, -1);
        	fromDateField.setText(fromDate.getTime().toString());
        }
        
    }
    
    /** configure the main window */
    private void configureWindow( final WindowReference windowReference ) {
    	
    }

    @Override
	public String dataLabel() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void makeMainWindow() {
        mainWindow = (XalWindow)WINDOW_REFERENCE.getWindow();

        initAndShowGUI();
        
        
        setHasChanges( false );		
	}

    private void initAndShowGUI() {
		// This method is invoked on Swing thread
		final JFXPanel fxPanel = new JFXPanel();

		mainWindow.add(fxPanel);

		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				initFX(fxPanel);
			}
		});

	}

    private void initFX(JFXPanel fxPanel) {
        // This method is invoked on JavaFX thread
		final AnchorPane page;
		try {
			page = (AnchorPane) FXMLLoader.load(ModelManagerDocument.class.getResource("modelManager.fxml"));
	        Scene scene = new Scene(page);

	        fxPanel.setScene(scene);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    /** Handle the accelerator changed event by displaying the elements of the accelerator in the main window. */
    public void acceleratorChanged() {
        try {
            MODEL.setSequence( null );
            setHasChanges( true );
        }
        catch ( Exception exception ) {
            exception.printStackTrace();
            displayError( "Error Setting Accelerator", "Simulator Configuration Exception", exception );
        }
	}
    
    /** Handle the selected sequence changed event by displaying the elements of the selected sequence in the main window. */
    public void selectedSequenceChanged() {
        try {
			xalSynopticPane.setAcceleratorSequence(getSelectedSequence());
			
			// temporary scaling and line up, the synoptic display should line up with data, not total width of the chart
//			xalSynopticPane.scaleXProperty().bind(
//					modelDataPlot.widthProperty().divide(
//							xalSynopticPane.getLayoutBounds().getWidth()));
			double ratio = getSelectedSequence().getLength() / xAxis.upperBoundProperty().doubleValue();
			xalSynopticPane.scaleXProperty().bind(
					xAxis.widthProperty().divide(
							xalSynopticPane.getLayoutBounds().getWidth()).multiply(ratio));
			
			ratio = xAxis.widthProperty().divide(
					xalSynopticPane.getLayoutBounds().getWidth()).multiply(ratio).doubleValue();
			
			double shift = (xAxis.widthProperty().doubleValue() - xalSynopticPane.getLayoutBounds().getWidth()*ratio)/2.;
//			
//			xalSynopticPane.layoutXProperty().bind(((
//					xAxis
//							.widthProperty()
//							.subtract(xalSynopticPane.getLayoutBounds()
//											.getWidth())).divide(2)).subtract(shift)
//											);
//			
			xalSynopticPane.layoutXProperty().bind(((
					xAxis
							.widthProperty()
							.subtract(xalSynopticPane.getLayoutBounds()
											.getWidth())).divide(2).add(yAxis.widthProperty().divide(2)))
											);
			
			System.out.println("scaleX = "
					+ xAxis.widthProperty().divide(
							xalSynopticPane.getLayoutBounds().getWidth()).multiply(ratio).doubleValue());

			System.out.println("layoutX = " + xAxis
					.widthProperty()
					.subtract(xalSynopticPane.getLayoutBounds().getWidth()).divide(2).subtract(shift).doubleValue());

			System.out.println("xAxis width = " + xAxis.widthProperty().doubleValue());
			
			System.out.println("xalSynopticPane width = " + xalSynopticPane.getLayoutBounds().getWidth()*ratio);
			
			System.out.println("shifted by " + shift);

			System.out.println("xalSynopticPane xProperty = " + xalSynopticPane.layoutXProperty().doubleValue() + ", yProperty = " + xalSynopticPane.layoutYProperty().doubleValue());

			System.out.println("Selected seq changed: "
					+ getSelectedSequence().getId());
			selectedSequenceName = getSelectedSequence().getId();

			MODEL.setSequence(getSelectedSequence());
			modelParamEditor.setDisable(false);
			mSyncMode.setDisable(false);
			runModel.setDisable(false);
			setHasChanges(true);
			updateLiveDeviceSettings();
		} catch (Exception exception) {
			exception.printStackTrace();
			displayError("Error Setting Sequence",
					"Simulator Configuration Exception", exception);
		}
	}
	
    private void updateModelTable() {
    	// query DB for saved models
    	ModelAPI ma = new ModelAPI();
    	
    	Date start_time = fromDate.getTime();
    	Date end_time = new Date();
    	
    	List<Model> models = ma.getModelsFor(start_time, end_time);
    	
		modelIdCol.setCellValueFactory(new PropertyValueFactory<SimModel, Double>("modelId"));
		dateCol.setCellValueFactory(new PropertyValueFactory<SimModel, Double>("date"));
		modelSrcCol.setCellValueFactory(new PropertyValueFactory<SimModel, Double>("modelSrc"));
		modeCol.setCellValueFactory(new PropertyValueFactory<SimModel, Double>("mode"));
		commentsCol.setCellValueFactory(new PropertyValueFactory<SimModel, Double>("comment"));
		goldCol.setCellValueFactory(new PropertyValueFactory<SimModel, Double>("gold"));
		refCol.setCellValueFactory(new PropertyValueFactory<SimModel, Double>("ref"));
		selCol.setCellValueFactory(new PropertyValueFactory<SimModel, Boolean>("sel"));
		selCol.setCellFactory(new Callback<TableColumn<SimModel, Boolean>, TableCell<SimModel, Boolean>>() {

			public TableCell<SimModel, Boolean> call(TableColumn<SimModel, Boolean> p) {
				return new CheckBoxTableCell<SimModel, Boolean>();
			}
		});
		
    	System.out.println("There are " + models.size() + " saved models between " + start_time.toString() + " and " + end_time.toString());
    	Iterator<Model> it = models.iterator();

    	final ObservableList<SimModel> modelData = FXCollections.observableArrayList();
    	modelListTable.setItems(modelData);

    	// update the table
    	while (it.hasNext()) {
    		Model model = it.next();
    		System.out.println(" model " + model.getModelId() 
    				+ " date: " + model.getCreateDate() 
    				+ ", comment: " + model.getModelDesc());
    		
    		modelData.add(new SimModel(model.getModelId().toString(), model.getCreateDate().toString(), 
    				null, null, model.getModelDesc(), null, false, false));
//    		SimModel md = new SimModel(model.getModelId().toString(), model.getCreateDate().toString(), 
//    				null, null, model.getModelDesc(), null, null, null);
    	}
    	    	
    }
    
    private void updateData() {
    	// update data tables
    	updateLiveTwiss();
    	updateLiveRMat();
    	
    	// update data plot, by default, plotting beta_X
    	updateDataPlot("betaX");
    }
    
    private void updateDBModel(Model model) {
    	
    	updateDBDeviceSettings(model);
    	updateDBTwiss(model);
    	updateDBRMat(model);
    }
    
    private void updateDBDeviceSettings(Model model) {
    	BeamParameterAPI bpa = new BeamParameterAPI();
    	// get the corresponding lattice
    	Lattice lat = model.getLatticeId();    	
    	
    	ElementPropAPI epa = new ElementPropAPI();
    	List<ElementProp> eProps = epa.getAllPropertiesForLattice(lat);
    	
    	// update the table
    	final ObservableList<Device> devData = FXCollections.observableArrayList();

    	for (int i=0; i<eProps.size(); i++) {
    		String eName = eProps.get(i).getElementId().getElementName();
    		double ePos = eProps.get(i).getElementId().getPos();
			devData.add(new Device(eName, Double.toString(ePos), eProps.get(i).getElementPropName(), 
					Double.toString(eProps.get(i).getElementPropDouble())));    		
    	}
    }
    
    private void updateLiveDeviceSettings() {                
    	OrTypeQualifier qualifier;
    	qualifier = new OrTypeQualifier();
    	qualifier.or("emag");
    	qualifier.or("rfcavity");
    	List<AcceleratorNode> nodes = getSelectedSequence().getAllNodesWithQualifier(qualifier);
    	Iterator<AcceleratorNode> it = nodes.iterator();
    	final ObservableList<Device> devData = FXCollections.observableArrayList();
    	deviceSettingTable.setItems(devData);
    	while (it.hasNext()) {
    		AcceleratorNode node = it.next();
    		if (node instanceof RfCavity) {
    			switch (MODEL.getSimulator().getScenario().getSynchronizationMode()) {
    			case "DESIGN":
    				devData.add(new Device(node.getId(), Double.toString(node.getPosition()), "gradient", 
        					Double.toString(node.getDesignPropertyValue("AMPLITUDE"))));
    				devData.add(new Device(node.getId(), Double.toString(node.getPosition()), "phase", 
    						Double.toString(node.getDesignPropertyValue("PHASE"))));
    				break;
    			case "RF_DESIGN":
    				devData.add(new Device(node.getId(), Double.toString(node.getPosition()), "gradient", 
        					Double.toString(node.getDesignPropertyValue("AMPLITUDE"))));
    				devData.add(new Device(node.getId(), Double.toString(node.getPosition()), "phase", 
    						Double.toString(node.getDesignPropertyValue("PHASE"))));
    				break;
    			case "LIVE":
    				//TODO
    				break;
    			}
    		}
    		else {
    			switch (MODEL.getSimulator().getScenario().getSynchronizationMode()) {
    			case "DESIGN":
    				devData.add(new Device(node.getId(), Double.toString(node.getPosition()), "field",
    						Double.toString(node.getDesignPropertyValue("FIELD"))));
    				break;
    			}
    		}
    	}
    	
//        propertyValCol.setEditable(true);
    }
    
    private void updateDBTwiss(Model model) {
    	BeamParameterPropAPI bppa = new BeamParameterPropAPI();
    	
    	// get all elements first
    	ElementAPI eapi = new ElementAPI();
    	Lattice lat = model.getLatticeId();
    	ArrayList<org.openepics.model.entity.Element> elements = eapi.getAllElementsForLattice(lat);
    	
    	// loop through all elements and with the specified model    	
    	for (int i=0; i<elements.size(); i++) {
    		org.openepics.model.entity.Element elem = elements.get(i);
    		    		
    		double w = (double) bppa.getBeamParameterPropFor(model.getModelId().intValue(), elem.getElementName(), "W");
    		double x = (double) bppa.getBeamParameterPropFor(model.getModelId().intValue(), elem.getElementName(), "x");
    		double xp = (double) bppa.getBeamParameterPropFor(model.getModelId().intValue(), elem.getElementName(), "xp");
    		double y = (double) bppa.getBeamParameterPropFor(model.getModelId().intValue(), elem.getElementName(), "y");
    		double yp = (double) bppa.getBeamParameterPropFor(model.getModelId().intValue(), elem.getElementName(), "yp");
    		double z = (double) bppa.getBeamParameterPropFor(model.getModelId().intValue(), elem.getElementName(), "z");
    		double zp = (double) bppa.getBeamParameterPropFor(model.getModelId().intValue(), elem.getElementName(), "zp");
    		double beta_x = (double) bppa.getBeamParameterPropFor(model.getModelId().intValue(), elem.getElementName(), "x_beta");
    		double alpha_x = (double) bppa.getBeamParameterPropFor(model.getModelId().intValue(), elem.getElementName(), "x_alpha");
    		double emit_x = (double) bppa.getBeamParameterPropFor(model.getModelId().intValue(), elem.getElementName(), "x_emittance");
    		double beta_y = (double) bppa.getBeamParameterPropFor(model.getModelId().intValue(), elem.getElementName(), "y_beta");
    		double alpha_y = (double) bppa.getBeamParameterPropFor(model.getModelId().intValue(), elem.getElementName(), "y_alpha");
    		double emit_y = (double) bppa.getBeamParameterPropFor(model.getModelId().intValue(), elem.getElementName(), "y_emittance");
    		double beta_z = (double) bppa.getBeamParameterPropFor(model.getModelId().intValue(), elem.getElementName(), "z_beta");
    		double alpha_z = (double) bppa.getBeamParameterPropFor(model.getModelId().intValue(), elem.getElementName(), "z_alpha");
    		double emit_z = (double) bppa.getBeamParameterPropFor(model.getModelId().intValue(), elem.getElementName(), "z_emittance");
    		
//    		List<BeamParameterProp> bpps = bpa.getBeamParameterForElementAndModel(elem, model);
    		
    		twissData.add(new Element(elem.getElementName(), elem.getPos(),
    				w,
    				x,
    				xp,
    				y,
    				yp,
    				z,
    				zp,
    				beta_x,
    				alpha_x,
    				emit_x,
    				beta_y,
    				alpha_y,
    				emit_y,
    				beta_z,
    				alpha_z,
    				emit_z
    				));
    	}
    }
    
    private void updateLiveTwiss() {
    	
    	elementTable.setItems(twissData);
    	
    	List<MachineSimulationRecord> states = MODEL.getSimulation().getSimulationRecords();
    	for (int i=0; i<states.size(); i++) {
    		MachineSimulationRecord state = states.get(i);
    		twissData.add(new Element(state.getElementID(), state.getPosition(), 
    				state.getKineticEnergy(),
    				state.getPhaseCoordinates().getx(),
    				state.getPhaseCoordinates().getxp(),
    				state.getPhaseCoordinates().gety(),
    				state.getPhaseCoordinates().getyp(),
    				state.getPhaseCoordinates().getz(),
    				state.getPhaseCoordinates().getzp(),
    				state.getTwissParameters()[0].getBeta(),
    				state.getTwissParameters()[0].getAlpha(),
    				state.getTwissParameters()[0].getEmittance(),
    				state.getTwissParameters()[1].getBeta(),
    				state.getTwissParameters()[1].getAlpha(),
    				state.getTwissParameters()[1].getEmittance(),
    				state.getTwissParameters()[2].getBeta(),
    				state.getTwissParameters()[2].getAlpha(),
    				state.getTwissParameters()[2].getEmittance()
    				));
    	}
    }
    
    private void updateDBRMat(Model model) {
    	BeamParameterAPI bpa = new BeamParameterAPI();
    	List<BeamParameter> bps = bpa.getAllBeamParametersForModel(model);
    	
    }
    
    private void updateLiveRMat() {
    	rMatTable.getColumns().clear();
//    	rMatTable = new TableView(generateDataInMap());
    	rMatTable.setItems(generateDataInMap());
    }
    
    private ObservableList<Map> generateDataInMap() {
        int max = 10;
        ObservableList<Map> allData = FXCollections.observableArrayList();
        
    	List<MachineSimulationRecord> states = MODEL.getSimulation().getSimulationRecords();
    	for (int i=0; i<states.size(); i++) {
    		Map<String, Double> dataRow = new HashMap<>();
    		MachineSimulationRecord state = states.get(i);
///    		dataRow.put(state.getElementID(), state.getResponseMatrix().getElem(0, 0)); // R11

    		allData.add(dataRow);
    	}
        
        return allData;
    }

    
    private void updateDataPlot(String paramName) {
        //defining a series
        XYChart.Series<Double, Double> series = new XYChart.Series<>();
    	// reset the plot
//        modelDataPlot.getData().clear();
        modelDataPlot.getData().remove(series);

        // model data
    	List<MachineSimulationRecord> states = MODEL.getSimulation().getSimulationRecords();
        
		switch (paramName) {
		// X
		case "phX":
//	        series.setName("X");
	        modelDataPlot.setTitle("X");
	        for (int i = 0; i < states.size(); i++) {
				series.getData().add(new XYChart.Data<>(posCol.getCellData(i),phXCol.getCellData(i)));
			}
//			series.getData().add(new XYChart.Data<>(posCol, phXCol));
			break;
		case "phXP":
//	        series.setName("X'");
	        modelDataPlot.setTitle("X'");
			for (int i = 0; i < states.size(); i++) {
				series.getData().add(new XYChart.Data<>(posCol.getCellData(i),phXPCol.getCellData(i)));
			}
			break;
		case "phY":
//	        series.setName("Y");
	        modelDataPlot.setTitle("Y");
			for (int i = 0; i < states.size(); i++) {
				series.getData().add(new XYChart.Data<>(posCol.getCellData(i),phYCol.getCellData(i)));
			}
			break;
		case "phYP":
//	        series.setName("Y'");
	        modelDataPlot.setTitle("Y'");
			for (int i = 0; i < states.size(); i++) {
				series.getData().add(new XYChart.Data<>(posCol.getCellData(i),phYPCol.getCellData(i)));
			}
			break;
		case "phZ":
//	        series.setName("Z");
	        modelDataPlot.setTitle("Z");
			for (int i = 0; i < states.size(); i++) {
				series.getData().add(new XYChart.Data<>(posCol.getCellData(i),phZCol.getCellData(i)));
			}
//			series.getData().add(new XYChart.Data<>(posCol, phXCol));
			break;
		case "phZP":
//	        series.setName("Z'");
	        modelDataPlot.setTitle("Z'");
			for (int i = 0; i < states.size(); i++) {
				series.getData().add(new XYChart.Data<>(posCol.getCellData(i),phZPCol.getCellData(i)));
			}
			break;
		case "betaX":
//	        series.setName("Beta_X");
	        modelDataPlot.setTitle("Beta_X");
			for (int i = 0; i < states.size(); i++) {
				series.getData().add(new XYChart.Data<>(posCol.getCellData(i), betaXCol.getCellData(i)));
			}
			break;
		case "alphaX":
//	        series.setName("Alpha_X");
	        modelDataPlot.setTitle("Alpha_X");
			for (int i = 0; i < states.size(); i++) {
				series.getData().add(new XYChart.Data<>(posCol.getCellData(i), alphaXCol.getCellData(i)));
			}
			break;
		case "emitX":
//	        series.setName("emit_X");
	        modelDataPlot.setTitle("Emit_X");
			for (int i = 0; i < states.size(); i++) {
				series.getData().add(new XYChart.Data<>(posCol.getCellData(i), emitXCol.getCellData(i)));
			}
			break;
		case "betaY":
//	        series.setName("Beta_Y");
	        modelDataPlot.setTitle("Beta_Y");
			for (int i = 0; i < states.size(); i++) {
				series.getData().add(new XYChart.Data<>(posCol.getCellData(i), betaYCol.getCellData(i)));
			}
			break;
		case "alphaY":
//	        series.setName("Alpha_Y");
	        modelDataPlot.setTitle("Alpha_Y");
			for (int i = 0; i < states.size(); i++) {
				series.getData().add(new XYChart.Data<>(posCol.getCellData(i), alphaYCol.getCellData(i)));
			}
			break;
		case "emitY":
//	        series.setName("emit_Y");
	        modelDataPlot.setTitle("Emit_Y");
			for (int i = 0; i < states.size(); i++) {
				series.getData().add(new XYChart.Data<>(posCol.getCellData(i), emitYCol.getCellData(i)));
			}
			break;
		case "betaZ":
//	        series.setName("Beta_Z");
	        modelDataPlot.setTitle("Beta_Z");
			for (int i = 0; i < states.size(); i++) {
				series.getData().add(new XYChart.Data<>(posCol.getCellData(i), betaZCol.getCellData(i)));
			}
			break;
		case "alphaZ":
//	        series.setName("Alpha_Z");
	        modelDataPlot.setTitle("Alpha_Z");
			for (int i = 0; i < states.size(); i++) {
				series.getData().add(new XYChart.Data<>(posCol.getCellData(i), alphaZCol.getCellData(i)));
			}
			break;
		case "emitZ":
//	        series.setName("emit_Z");
	        modelDataPlot.setTitle("Emit_Z");
			for (int i = 0; i < states.size(); i++) {
				series.getData().add(new XYChart.Data<>(posCol.getCellData(i), emitZCol.getCellData(i)));
			}
			break;
		case "kE":
//	        series.setName("emit_Z");
	        modelDataPlot.setTitle("Kinetic Energy");
			for (int i = 0; i < states.size(); i++) {
				series.getData().add(new XYChart.Data<>(posCol.getCellData(i), eCol.getCellData(i)));
			}
			break;
		default: 
//	        series.setName("Beta_X");
	        modelDataPlot.setTitle("Beta_X");
			for (int i = 0; i < states.size(); i++) {
				series.getData().add(new XYChart.Data<>(posCol.getCellData(i), betaXCol.getCellData(i)));
			}
			break;
			
		}
        
        modelDataPlot.setTitle("Model Data");
        if (modelDataPlot.getData().size() > 0 ) {
        	modelDataPlot.getData().remove(0);
        	
        }

    	modelDataPlot.getData().add(series);

        modelDataPlot.createSymbolsProperty();
        modelDataPlot.getXAxis().setAutoRanging(true);
        
        System.out.println("Axis upper bound = " + xAxis.upperBoundProperty().doubleValue() + ", lower bound = " + xAxis.lowerBoundProperty().doubleValue());
        System.out.println("sequence length = " + MODEL.getSequence().getLength());

        //TODO scaling the synoptic display correctly according to the data
//        xalSynopticPane.scaleXProperty().bind(
//				modelDataPlot.getXAxis().widthProperty().divide(
//						xalSynopticPane.getLayoutX()));
        //TODO line up the synoptic display correctly according to the data
//		xalSynopticPane.layoutXProperty().bind(
//				modelDataPlot
//						.widthProperty()
//						.subtract(
//								xalSynopticPane.getLayoutBounds()
//										.getWidth()).divide(2));
		double ratio = MODEL.getSequence().getLength() / xAxis.upperBoundProperty().doubleValue();
		xalSynopticPane.scaleXProperty().bind(
				xAxis.widthProperty().divide(
						xalSynopticPane.getLayoutBounds().getWidth()).multiply(ratio));
		
		ratio = xAxis.widthProperty().divide(
				xalSynopticPane.getLayoutBounds().getWidth()).multiply(ratio).doubleValue();
		
		System.out.println("modelDataPlot width = " + modelDataPlot.widthProperty().doubleValue());
		System.out.println("xAxis xProperty = " + xAxis.getLayoutX() + ", yAxis xProperty = " + yAxis.layoutXProperty().doubleValue());
		System.out.println("xalSynopticPane xProperty = " + xalSynopticPane.layoutXProperty().doubleValue() + ", yProperty = " + xalSynopticPane.layoutYProperty().doubleValue());
		System.out.println("xAxis width = " + xAxis.getWidth() + ", yAxis width = " + yAxis.getWidth());
		System.out.println("xAxis lower bound = " + xAxis.getLowerBound() + ", yAxis lower bound = " + yAxis.getLowerBound());
		double constant = xAxis.layoutBoundsProperty().get().getMinX() + xAxis.layoutXProperty().doubleValue();
		System.out.println("%%% constant = " + constant);
		
		double shift = (xAxis.widthProperty().doubleValue() - xalSynopticPane.getLayoutBounds().getWidth()*ratio)/2.;
		
//		xalSynopticPane.layoutXProperty().bind(((
//				xAxis
//						.widthProperty()
//						.subtract(xalSynopticPane.getLayoutBounds()
//										.getWidth())).divide(2)).subtract(shift)
//										);
		xalSynopticPane.layoutXProperty().bind(((
				xAxis
						.widthProperty()
						.subtract(xalSynopticPane.getLayoutBounds()
										.getWidth())).divide(2).add(yAxis.widthProperty().divide(2)))
										);
		System.out.println("shifted by = " + shift);

    }
    
    private void saveModelData2DB() {
    	ModelAPI theModel = new ModelAPI();
        // model data
    	List<MachineSimulationRecord> states = MODEL.getSimulation().getSimulationRecords();
    	
    	// preparing devices
        for (int i=0; i<states.size(); i++) {
        	org.openepics.model.extraEntity.Device dev = new org.openepics.model.extraEntity.Device();
//        	String myName = getSelectedSequence().getId();
//        	System.out.println("^^^" + myName);
        	dev.setBeamlineSequenceName(selectedSequenceName);
///        	dev.setElementName(states.get(i).getElementId());
///        	dev.setPos(states.get(i).getPosition());
        	BeamParams beamParams = new BeamParams();

        	ArrayList<BeamParameterProp> beamParameterPropCollection = new ArrayList<>();
        	//TODO
        	BeamParameterProp x = new BeamParameterProp();
        	x.setPropertyName("x");
        	x.setBeamParameterDouble(phXCol.getCellData(i));        	
        	BeamParameterProp xp = new BeamParameterProp();
        	xp.setPropertyName("xp");
        	xp.setBeamParameterDouble(phXPCol.getCellData(i));        	
        	BeamParameterProp y = new BeamParameterProp();
        	y.setPropertyName("y");
        	y.setBeamParameterDouble(phYCol.getCellData(i));        	
        	BeamParameterProp yp = new BeamParameterProp();
        	yp.setPropertyName("yp");
        	yp.setBeamParameterDouble(phYPCol.getCellData(i));        	
        	BeamParameterProp z = new BeamParameterProp();
        	z.setPropertyName("z");
        	z.setBeamParameterDouble(phYCol.getCellData(i));        	
        	BeamParameterProp zp = new BeamParameterProp();
        	zp.setPropertyName("zp");
        	zp.setBeamParameterDouble(phZPCol.getCellData(i));        	
        	
        	BeamParameterProp beta_x = new BeamParameterProp();
        	beta_x.setPropertyName("x_beta");
        	beta_x.setBeamParameterDouble(betaXCol.getCellData(i));
//        	beta_x.setBeamParameterDouble(states.get(i).getTwiss()[0].getBeta());
        	beamParameterPropCollection.add(beta_x);
        	BeamParameterProp alpha_x = new BeamParameterProp();
        	alpha_x.setPropertyName("x_alpha");
//        	alpha_x.setBeamParameterDouble(states.get(i).getTwiss()[0].getAlpha());
        	alpha_x.setBeamParameterDouble(alphaXCol.getCellData(i));
        	beamParameterPropCollection.add(alpha_x);
        	BeamParameterProp emit_x = new BeamParameterProp();
        	emit_x.setPropertyName("x_emittance");
//        	emit_x.setBeamParameterDouble(states.get(i).getTwiss()[0].getEmittance());
        	emit_x.setBeamParameterDouble(emitXCol.getCellData(i));
        	beamParameterPropCollection.add(emit_x);
        	BeamParameterProp beta_y = new BeamParameterProp();
        	beta_y.setPropertyName("y_beta");
        	beta_y.setBeamParameterDouble(betaXCol.getCellData(i));
        	beamParameterPropCollection.add(beta_y);
        	BeamParameterProp alpha_y = new BeamParameterProp();
        	alpha_y.setPropertyName("y_alpha");
        	alpha_y.setBeamParameterDouble(alphaXCol.getCellData(i));
        	beamParameterPropCollection.add(alpha_y);
        	BeamParameterProp emit_y = new BeamParameterProp();
        	emit_y.setPropertyName("y_emittance");
        	emit_y.setBeamParameterDouble(emitXCol.getCellData(i));
        	beamParameterPropCollection.add(emit_y);
        	BeamParameterProp beta_z = new BeamParameterProp();
        	beta_z.setPropertyName("z_beta");
        	beta_z.setBeamParameterDouble(betaXCol.getCellData(i));
        	beamParameterPropCollection.add(beta_z);
        	BeamParameterProp alpha_z = new BeamParameterProp();
        	alpha_z.setPropertyName("z_alpha");
        	alpha_z.setBeamParameterDouble(alphaXCol.getCellData(i));
        	beamParameterPropCollection.add(alpha_z);
        	BeamParameterProp emit_z = new BeamParameterProp();
        	emit_z.setPropertyName("z_emittance");
        	emit_z.setBeamParameterDouble(emitXCol.getCellData(i));
        	beamParameterPropCollection.add(emit_z);
        	
        	beamParams.setBeamParameterPropCollection(beamParameterPropCollection);
        	
        	dev.setBeamParams(beamParams);
        	
        	// device settings
        	ArrayList<ElementProp> elementPropCollection = new ArrayList<>();
        	//TODO slice ID
        	
        	//TODO device settings as element properties
        	
        	dev.setElementPropCollection(elementPropCollection);
        	
        	devices.add(dev);
        }
        
        // saving model data
        theModel.setModel("XAL Model", selectedSequenceName, devices);
        System.out.println("Data saving done.");
        statusField.setText("Data saving done.");
    }

    @Override
	public void saveDocumentAs(URL url) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(DataAdaptor adaptor) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void write(DataAdaptor adaptor) {
		// TODO Auto-generated method stub
		
	}
		
}
