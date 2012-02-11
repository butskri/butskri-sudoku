package be.butskri.sudoku;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

public class Cell<T> {

	private int row;
	private int column;
	private List<Group<T>> groups = new ArrayList<Group<T>>();
	private CellValue<T> cellValue;

	public Cell(int row, int column, Collection<T> allPossibleValues) {
		this.row = row;
		this.column = column;
		this.cellValue = new BasicCellValue<T>(allPossibleValues);
	}

	public int getRow() {
		return row;
	}

	public int getColumn() {
		return column;
	}

	public boolean isSolved() {
		return cellValue.isSolved();
	}

	public boolean isOpgave() {
		return cellValue.isOpgave();
	}

	public T getValue() {
		return cellValue.getValue();
	}

	public void decorateCellValue(CellValueDecorator<T> cellValueDecorator) {
		this.cellValue = cellValueDecorator.decorate(cellValue);
	}

	public void undecorateCellValue(CellValueDecorator<T> cellValueDecorator) {
		this.cellValue = cellValueDecorator.undecorate(cellValue);
	}

	protected Set<T> getAllPossibleValues() {
		return cellValue.getAllPossibleValues();
	}

	public void setOpgave(T value) {
		if (value != null) {
			this.cellValue = new OpgaveCellValue<T>(value);
		}
	}

	public void addGroup(Group<T> group) {
		groups.add(group);
	}

	public boolean stepToSolution() {
		if (isSolved()) {
			return false;
		}
		int numberOfPossibleValuesBefore = cellValue.getAllPossibleValues().size();
		cellValue.stepToSolution(groups);
		int numberOfPossibleValuesRemoved = numberOfPossibleValuesBefore - cellValue.getAllPossibleValues().size();
		System.out.println("Row " + row + " , Column " + column + ": Removed " + numberOfPossibleValuesRemoved + " values");
		return numberOfPossibleValuesRemoved > 0;
	}

	public void removePossibleValue(T guess) {
		cellValue.removePossibleValue(guess);
	}

	public void setValue(T value) {
		if (!cellValue.getAllPossibleValues().contains(value)) {
			throw new IllegalArgumentException(String.format("value $1 is not allowed", value));
		}
		cellValue.setValue(value);
	}

	public boolean isValid() {
		return getAllPossibleValues().size() > 0;
	}
}
