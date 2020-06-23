package eu.burbach.diplom.application;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import eu.burbach.diplom.algorithms.BGH;
import eu.burbach.diplom.algorithms.Berkowitz;
import eu.burbach.diplom.algorithms.Csanky;
import eu.burbach.diplom.algorithms.Gauss;
import eu.burbach.diplom.algorithms.Leibniz;
import eu.burbach.diplom.algorithms.Pan;
import eu.burbach.diplom.common.BigComputable;
import eu.burbach.diplom.common.Matrix;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;

public class MainController {
	@FXML TextField n;
	@FXML TableView<Row> table;
	@FXML TextField det;
	@FXML BorderPane borderPane;
	Random random= new Random(3094);
	Matrix<BigComputable,BigDecimal> mat;

	public void buttonGauss() {
		try {
			det.setText(Double.valueOf(new Gauss<BigComputable,BigDecimal>().det(table2Matrix()).getDouble()).toString());
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
		}
	}
	
	public void buttonLeibniz() {
		try {
			det.setText(Double.valueOf(new Leibniz<BigComputable,BigDecimal>().det(table2Matrix()).getDouble()).toString());
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
		}
	}
	
	public void buttonCsanky() {
		try {
			det.setText(Double.valueOf(new Csanky<BigComputable,BigDecimal>().det(table2Matrix()).getDouble()).toString());
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
		}
	}
	
	public void buttonBerkowitz() {
		try {
			det.setText(new Berkowitz<BigComputable,BigDecimal>().det(table2Matrix()).get().toString());
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
		}
	}
	
	public void buttonBGH() {
		try {
			det.setText(Double.valueOf(new BGH<BigComputable,BigDecimal>().det(table2Matrix()).getDouble()).toString());
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException e) {
			// TODO Auto-generated catch block
		}
	}
	
	public void buttonPan() {
		try {
			det.setText(Double.valueOf(new Pan<BigComputable,BigDecimal>().det(table2Matrix()).getDouble()).toString());
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
		}
	}
	
	private Matrix<BigComputable,BigDecimal> table2Matrix() throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		//int nint=0;
		//try {nint= Integer.parseInt(n.getText());} catch (NumberFormatException e) {}
		
		Matrix<BigComputable,BigDecimal> res= mat.copy();
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
	
	public void textFieldEditedN() throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		int nint=0;
		try {nint= Integer.parseInt(n.getText());} catch (NumberFormatException e) {}
		
		//fülle die Matrix mit Zufallswerten:
		mat=new Matrix<BigComputable,BigDecimal>(BigComputable.class,nint,nint);
		for (int r=0; r<nint; r++)
			for (int c=0; c<nint; c++)
				mat.set(r, c, new BigComputable(new BigDecimal(Math.round(random.nextDouble()*10+1))));
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
					e.getCells().add(new Cell(mat.get(r, c).get().toString())); 
				}
				rows.add(e);
	     }
	     table.getItems().clear();
	     table.getItems().addAll(rows);
	}
}
