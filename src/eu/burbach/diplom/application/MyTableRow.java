package eu.burbach.diplom.application;

import javafx.beans.property.SimpleStringProperty;

public class MyTableRow {
	
	SimpleStringProperty[] values;
    int columns;	
	
	public MyTableRow(int n) {
		columns=n;
		values= new SimpleStringProperty[n];
		for (int i=0; i<n; i++)
			values[i]= new SimpleStringProperty("0.0");
	}

	public SimpleStringProperty getValue(int c) {
		return values[c];
	}

	public void setValue(SimpleStringProperty v, int c) {
		values[c]= v;
	}

}
