package be.butskri.sudoku;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Stack;

import com.google.common.collect.Sets;

public class Sudoku<T> {

	public static final Set<Object> ALL_POSSIBLE_VALUES = Sets.newHashSet((Object) 1, 2, 3, 4, 5, 6, 7, 8, 9);
	private static final int ROWS = 3;
	private static final int COLUMNS = 3;

	private int rows;
	private int columns;
	private List<Cell<T>> allCells;
	private List<Group<T>> groups;
	private Stack<EducatedGuess<T>> educatedGuesses = new Stack<EducatedGuess<T>>();

	public Sudoku(int rowsPerCell, int columnsPerCell, Set<T> possibleValues) {
		System.out.println("creating sudoku...");
		this.rows = rowsPerCell * columnsPerCell;
		this.columns = rows;
		setupCells(rowsPerCell * columnsPerCell, possibleValues);
		setupGroups(rowsPerCell, columnsPerCell);
	}

	public void solve() {
		while (!isSolved()) {
			takeNextStepToSolution();
		}
	}

	private boolean isSolved() {
		for (Cell<T> cell : getAllCells()) {
			if (!cell.isSolved()) {
				System.out.println("sudoku is not solved yet");
				return false;
			}
		}
		System.out.println("sudoku is already solved");
		return true;
	}

	private Set<Cell<T>> getAllCells() {
		return new HashSet<Cell<T>>(allCells);
	}

	public int getRows() {
		return ROWS;
	}

	public int getColumns() {
		return COLUMNS;
	}

	private void setupCells(int totalNumberOfRows, Set<T> possibleValues) {
		allCells = new ArrayList<Cell<T>>();
		for (int column = 1; column <= totalNumberOfRows; column++) {
			for (int row = 1; row <= totalNumberOfRows; row++) {
				allCells.add(new Cell<T>(row, column, possibleValues));
			}
		}
	}

	private void setupGroups(int rowsPerCell, int columnsPerCell) {
		groups = new ArrayList<Group<T>>();
		groups.addAll(setupHorizontalGroups(rowsPerCell * columnsPerCell));
		groups.addAll(setupVerticalGroups(rowsPerCell * columnsPerCell));
		groups.addAll(setupCubeGroups(rowsPerCell, columnsPerCell));
	}

	private Collection<Group<T>> setupHorizontalGroups(int numberOfRows) {
		Collection<Group<T>> result = new ArrayList<Group<T>>();
		for (int row = 1; row <= numberOfRows; row++) {
			result.add(new Group<T>(getAllCellsByRow(row)));
		}
		return result;
	}

	private Collection<Group<T>> setupVerticalGroups(int numberOfColumns) {
		Collection<Group<T>> result = new ArrayList<Group<T>>();
		for (int column = 1; column <= numberOfColumns; column++) {
			result.add(new Group<T>(getAllCellsByColumn(column)));
		}
		return result;
	}

	private Collection<Group<T>> setupCubeGroups(int rowsPerCell, int columnsPerCell) {
		Collection<Group<T>> result = new ArrayList<Group<T>>();
		for (int cubicRowIndex = 0; cubicRowIndex < columnsPerCell; cubicRowIndex++) {
			for (int cubicColumnIndex = 0; cubicColumnIndex < rowsPerCell; cubicColumnIndex++) {
				int baseRow = cubicRowIndex * rowsPerCell + 1;
				int baseColumn = cubicColumnIndex * columnsPerCell + 1;
				result.add(new Group<T>(getAllCellsByMinMaxRowAndColumn(baseRow, baseRow + rowsPerCell - 1, baseColumn, baseColumn + columnsPerCell
						- 1)));
			}
		}
		return result;
	}

	private Collection<Cell<T>> getAllCellsByMinMaxRowAndColumn(int minRow, int maxRow, int minColumn, int maxColumn) {
		Collection<Cell<T>> result = new ArrayList<Cell<T>>();
		for (Cell<T> cell : allCells) {
			if (isBetween(cell.getRow(), minRow, maxRow) && isBetween(cell.getColumn(), minColumn, maxColumn)) {
				result.add(cell);
			}
		}
		return result;
	}

	private boolean isBetween(int value, int min, int max) {
		return value >= min && value <= max;
	}

	private void takeNextStepToSolution() {
		System.out.println("taking next step to solution...");
		boolean closerToSolution = removeImpossibleValuesFromCells();
		revertLastGuessIfNecessary();
		if (!closerToSolution) {
			educatedGuesses.push(EducatedGuess.make(getAllCells()));
		}
		revertLastGuessIfNecessary();
	}

	private void revertLastGuessIfNecessary() {
		if (!isValid()) {
			System.out.println("reverting last guess...");
			educatedGuesses.pop().revert();
		}
	}

	private boolean isValid() {
		for (Cell<T> cell : allCells) {
			if (!cell.isValid()) {
				return false;
			}
		}
		for (Group<T> group : groups) {
			if (!group.isValid()) {
				return false;
			}
		}
		return true;
	}

	private boolean removeImpossibleValuesFromCells() {
		boolean result = false;
		for (Cell<T> cell : getAllCells()) {
			result = result || cell.stepToSolution();
		}
		return result;
	}

	public Object getValue(int row, int column) {
		return getCell(row, column).getValue();
	}

	public void setOpgave(int row, int column, T value) {
		getCell(row, column).setOpgave(value);
	}

	private Cell<T> getCell(int row, int column) {
		for (Cell<T> cell : allCells) {
			if (cell.getRow() == row && cell.getColumn() == column) {
				return cell;
			}
		}
		return null;
	}

	private Collection<Cell<T>> getAllCellsByRow(int row) {
		Collection<Cell<T>> result = new ArrayList<Cell<T>>();
		for (Cell<T> cell : allCells) {
			if (cell.getRow() == row) {
				result.add(cell);
			}
		}
		return result;
	}

	private Collection<Cell<T>> getAllCellsByColumn(int column) {
		Collection<Cell<T>> result = new ArrayList<Cell<T>>();
		for (Cell<T> cell : allCells) {
			if (cell.getColumn() == column) {
				result.add(cell);
			}
		}
		return result;
	}

	public void printSolution() {
		System.out.println("Solution: ");
		for (int row = 1; row <= rows; row++) {
			for (int column = 1; column <= columns; column++) {
				System.out.print(getValue(row, column));
				System.out.print(" ");
			}
			System.out.println();
		}

	}

}
