package seagrass_Model_V1.View;

import java.awt.Component;
import java.awt.Dimension;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import seagrass_Model_V1.Model.Attribute;

public class FieldPanel extends JPanel {
	
	private JLabel textDisplayLabel;
	private JTextField textBox;
	private Attribute ea;
	
	public FieldPanel(Attribute ea, double currentValue) {
		this.ea = ea;
		this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		textDisplayLabel = new JLabel(ea.getName());
		
		//create text label
		JPanel textPanel = new JPanel();
		textPanel.setPreferredSize(new Dimension(100,22));
		textPanel.setMaximumSize(new Dimension(100,22));
		textPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
		textPanel.add(textDisplayLabel);
		
		//create value box
		textBox = new JTextField();
		textBox.setAlignmentX(Component.CENTER_ALIGNMENT);
		textBox.setHorizontalAlignment(SwingConstants.CENTER);
		textBox.setMaximumSize(new Dimension(50,22));
		textBox.setPreferredSize(new Dimension(50,22));
		textBox.setText("" + currentValue);
		
		this.add(textPanel);
		this.add(textBox);
	}
	
	public double getDoubleFromBox() throws Exception{
		String inputText = textBox.getText().trim();
		if(inputText.equals("")){
			throw new Exception("No Number Entered");
		} else {
			return Double.parseDouble(textBox.getText().trim());
		}
	}
	
	public int getIntFromBox() throws Exception{
		String inputText = textBox.getText().trim();
		if(inputText.equals("")){
			throw new Exception("No Number Entered");
		} else {
			return Integer.parseInt(textBox.getText());
		}
	}
	
	public void setTextBoxString(String newText){
		textBox.setText(newText);
	}
	
	public Attribute getEditableAttributes(){
		return ea;
	}

}

