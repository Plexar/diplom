package eu.burbach.diplom.application;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import eu.burbach.diplom.algorithms.Berkowitz;
import eu.burbach.diplom.algorithms.Csanky;
import eu.burbach.diplom.algorithms.Gauss;
import eu.burbach.diplom.algorithms.Leibniz;
import eu.burbach.diplom.common.Matrix;

public class MainController {
	@FXML TextField n;
	@FXML TableView<Row> table;
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
		//int nint=0;
		//try {nint= Integer.parseInt(n.getText());} catch (NumberFormatException e) {}
		
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
		
		//fülle die Matrix mit Zufallswerten:
		mat=new Matrix(nint,nint);
		for (int r=0; r<nint; r++)
			for (int c=0; c<nint; c++)
				mat.set(r, c, Math.round(random.nextDouble()*10+1));
		/*
		for (int r=0; r<nint; r++) {
			for (int c=0; c<nint; c++) {
				System.out.print(mat.get(r, c)); 
				System.out.print("  ");
			}
			System.out.println();
		}
		*/
		
		//zeige die Matrix an:
		table.getColumns().clear();
	     for (int m = 0; m < nint; m++)
	     {
	            TableColumn<Row, String> column = new TableColumn<>(Integer.toString(m));
	            column.setCellValueFactory(param -> {
//	                int index = Integer.parseInt(param.getTableColumn().getText());
	                int index = param.getTableView().getColumns().indexOf(param.getTableColumn());
	                List<Cell> cells = param.getValue().getCells();
	                return new SimpleStringProperty(cells.size() > index ? cells.get(index).toString() : null);
	            });
	            table.getColumns().add(column);
	     }	     
	     List<Row> rows= new ArrayList<>();
	     for (int r=0; r<nint; r++) {
				Row e= new Row();				
				for (int c=0; c<nint; c++) {
					e.getCells().add(new Cell(Double.toString(mat.get(r, c)))); 
				}
				rows.add(e);
	     }
	     table.getItems().clear();
	     table.getItems().addAll(rows);
	}
}
