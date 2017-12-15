package eu.burbach.diplom.application;

import java.util.Random;

import javax.security.auth.login.CredentialExpiredException;

import eu.burbach.diplom.algorithms.Berkowitz;
import eu.burbach.diplom.algorithms.Csanky;
import eu.burbach.diplom.algorithms.Gauss;
import eu.burbach.diplom.algorithms.Leibniz;
import eu.burbach.diplom.common.Matrix;
import javafx.beans.InvalidationListener;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.util.Callback;

public class MainController {
	@FXML TextField n;
	@FXML TableView<MyTableRow> table;
	@FXML TextField det;
	@FXML BorderPane borderPane;
	Random random= new Random(3094);
	Matrix mat;

	public void buttonGauss() {
		det.setText(new Double(Gauss.det(table2Matrix())).toString());
	}
	
	public void buttonLeibniz() {
		det.setText(new Double(Leibniz.det(table2Matrix())).toString());
	}
	
	public void buttonCsanky() {
		det.setText(new Double(Csanky.det(table2Matrix())).toString());
	}
	
	public void buttonBerkowitz() {
		det.setText(new Double(Berkowitz.det(table2Matrix())).toString());
	}
	
	private Matrix table2Matrix() {
		int nint=0;
		try {nint= Integer.parseInt(n.getText());} catch (NumberFormatException e) {}
		
		Matrix res= mat.copy();
//		for (int r=0; r<nint; r++) {
//			for(int c=0; c<nint; c++) {
//				try {
////					res.set(r, c, Double.parseDouble(table.getItems().get(r).getValue(c).get()));
////					res.set(r, c, Math.round(random.nextDouble()*10));
//				} catch (NumberFormatException e) {
//					res.set(r, c, 0.0);
//				}
//				System.out.print(res.get(r, c)); System.out.print(" ");
//			}
//			System.out.println();
//		}
		return res;
	}
	
	public void buttonExit() {
		System.exit(0);
	}
	
	public void textFieldEditedN() {
		int nint=0;
		try {nint= Integer.parseInt(n.getText());} catch (NumberFormatException e) {}
		
		mat=new Matrix(nint,nint);
		for (int r=0; r<nint; r++)
			for (int c=0; c<nint; c++)
				mat.set(r, c, Math.round(random.nextDouble()*10+1));
		for (int r=0; r<nint; r++) {
			for (int c=0; c<nint; c++) {
				System.out.print(mat.get(r, c)); 
				System.out.print("  ");
			}
			System.out.println();
		}

		TableView<MyTableRow> t= new TableView<MyTableRow>();
		for (int c=0; c<nint; c++) {
		    Callback<TableColumn, TableCell> editableFactory = new Callback<TableColumn, TableCell>() {
		        @Override
		        public TableCell call(TableColumn p) {
		            return new EditingCell();
		        }
		    };
			TableColumn<MyTableRow,String> col= new TableColumn<MyTableRow,String>();
			final int fc= c;
			col.setCellValueFactory(new Callback<CellDataFeatures<MyTableRow,String>,ObservableValue<String>>(){		
				@Override
				public ObservableValue<String> call(CellDataFeatures<MyTableRow, String> p) {
					if (p.getValue()!=null)
						return p.getValue().getValue(fc);
					else
						return new SimpleStringProperty();
				}
			});
			col.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<MyTableRow,String>>() {				
				@Override
				public void handle(CellEditEvent<MyTableRow, String> t) {
					t.getRowValue().setValue(new SimpleStringProperty(t.getNewValue()), fc);
				}
			});
			col.setSortable(false);
			col.setEditable(true);
			t.getColumns().add(col);
			
			final ObservableList<MyTableRow> list= FXCollections.observableArrayList();
			for (int r=0; r<nint; r++)
				list.add(new MyTableRow(nint));
			t.setItems(list);
		}
		table= t;
		t.setEditable(true);
		borderPane.setCenter(t);
	}
}
