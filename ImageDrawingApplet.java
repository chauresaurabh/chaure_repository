import java.awt.*;
import javax.swing.*;


public class ImageDrawingApplet extends JApplet   
{
	public JCheckBox asCheckBox, buildingCheckBox, studentCheckBox;
	public JRadioButton wholeRegionRadioButton,rangeQueryRadioButton,pointQueryRadioButton,surroundingStudentRadioButton,
	emergencyQueryRadioButton;
	public ButtonGroup radioButtonGroup;
	public JTextArea queryDisplayArea;
	public JButton submitQueryButton;
	JPanel controlPanel ;
	JLabel queryLabel, featureTypeLabel;
	public MapComponent mapComponent;
	public String mapFile;
	public JScrollPane scrollPane;

	public JTextField mouseLocation;
	public ImageDrawingApplet(String mapFile){
		this.mapFile = mapFile;
	}

	public void start() 
	{
		createComponents();
	}

	public void createComponents() 
	{
		setLayout(new BorderLayout());
		setSize(new Dimension(1100,650));

		createRadioButtons();
		createCheckBoxes();
		createLabels();
		createTextArea();
		createMouseLocation();
		createScrollPane();
		createButtons();    
		mapComponent = new MapComponent();
		mapComponent.repaint();

		createControlPanel();
		addListeners();

	}
	public void addListeners(){
		this.addMouseListener(new PanelMouseEventsListener(this, mapComponent));
		this.addMouseMotionListener(new PanelMouseEventsListener(this, mapComponent));
		submitQueryButton.addActionListener(new QueryButtonListener(this, mapComponent));

		wholeRegionRadioButton.addItemListener(new RadioButtonListener(this, mapComponent));
		rangeQueryRadioButton.addItemListener(new RadioButtonListener(this, mapComponent));
		pointQueryRadioButton.addItemListener(new RadioButtonListener(this, mapComponent));
		surroundingStudentRadioButton.addItemListener(new RadioButtonListener(this, mapComponent));
		emergencyQueryRadioButton.addItemListener(new RadioButtonListener(this, mapComponent));

	}
	public void createMouseLocation(){
		mouseLocation = new JTextField();
	}
	public void createScrollPane(){
		scrollPane = 	new JScrollPane(queryDisplayArea);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setPreferredSize(new Dimension(800,40));
		add("South",scrollPane);
	}
	public void createControlPanel(){
		controlPanel 	= 	new JPanel();
		controlPanel.setLayout(new GridLayout(0, 1)); 

		controlPanel.add(asCheckBox);
		controlPanel.add(buildingCheckBox);
		controlPanel.add(studentCheckBox);
		controlPanel.add(mouseLocation);

		controlPanel.add(wholeRegionRadioButton);
		controlPanel.add(rangeQueryRadioButton);
		controlPanel.add(pointQueryRadioButton);
		controlPanel.add(surroundingStudentRadioButton);
		controlPanel.add(emergencyQueryRadioButton);
		controlPanel.add(submitQueryButton);
		add("West",mapComponent);
		add("East",controlPanel);

	}
	public void createButtons(){
		submitQueryButton  =  new JButton("Submit Query"); 

	}
	public void createRadioButtons(){

		wholeRegionRadioButton = new JRadioButton("Whole Region" , true);
		pointQueryRadioButton = new JRadioButton("Point Query" , false);
		rangeQueryRadioButton =  new JRadioButton("Range Query" , false);
		surroundingStudentRadioButton  = new JRadioButton("Surrounding Student", false);
		emergencyQueryRadioButton = new JRadioButton("Emergency Query", false);

		radioButtonGroup = new ButtonGroup();
		radioButtonGroup.add(wholeRegionRadioButton);
		radioButtonGroup.add(pointQueryRadioButton);
		radioButtonGroup.add(rangeQueryRadioButton);
		radioButtonGroup.add(surroundingStudentRadioButton); 
		radioButtonGroup.add(emergencyQueryRadioButton);      

	}

	public void createTextArea(){
		queryDisplayArea=new JTextArea(1000,200);
		queryDisplayArea.setEditable(false);
		queryDisplayArea.setLineWrap( true );
		queryDisplayArea.setWrapStyleWord( true );
	}
	public void createCheckBoxes(){
		asCheckBox 				= 	new JCheckBox("AS" , true);
		buildingCheckBox  			= 	new JCheckBox("Building" , true);
		studentCheckBox    			= 	new JCheckBox("Students" , true);
	}

	public void createLabels(){
		queryLabel 		= 	new JLabel("Query", JLabel.LEFT);
		featureTypeLabel 		= 	new JLabel("Active Feature Type", JLabel.LEFT);
	}
}



