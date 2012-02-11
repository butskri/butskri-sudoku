package be.butskri.sudoku;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class EducatedGuess<T> implements CellValueDecorator<T> {

	private Cell<T> cell;
	private T guess;
	private Set<Cell<T>> cells;

	public static <U> EducatedGuess<U> make(Set<Cell<U>> cells) {
		Cell<U> cell = findBestSuitedCellForGuessing(cells);
		EducatedGuess<U> result = new EducatedGuess<U>(cell, cells);
		return result;
	}

	private EducatedGuess(Cell<T> cell, Set<Cell<T>> cells) {
		this.cell = cell;
		this.cells = cells;
		this.guess = cell.getAllPossibleValues().iterator().next();
		decorateCells();
		cell.setValue(guess);
	}

	private void decorateCells() {
		for (Cell<T> cell : cells) {
			cell.decorateCellValue(this);
		}
	}

	public void revert() {
		undecorateCells();
		cell.removePossibleValue(guess);
	}

	private void undecorateCells() {
		for (Cell<T> cell : cells) {
			cell.undecorateCellValue(this);
		}
	}

	private static <T> Cell<T> findBestSuitedCellForGuessing(Set<Cell<T>> cells) {
		Cell<T> bestSuitedCell = null;
		for (Cell<T> cell : cells) {
			if (!cell.isSolved()) {
				bestSuitedCell = getBestSuitedCellForGuessing(bestSuitedCell, cell);
				if (bestSuitedCell.getAllPossibleValues().size() == 2) {
					return bestSuitedCell;
				}
			}
		}
		return bestSuitedCell;
	}

	private static <T> Cell<T> getBestSuitedCellForGuessing(Cell<T> cell1, Cell<T> cell2) {
		if (cell1 == null) {
			return cell2;
		}
		if (cell1.getAllPossibleValues().size() <= cell2.getAllPossibleValues().size()) {
			return cell1;
		}
		return cell2;
	}

	public CellValue<T> decorate(CellValue<T> cellValue) {
		if (cellValue.isSolved()) {
			return cellValue;
		}
		return new CellValueForEducatedGuess<T>(this, cellValue);
	}

	@SuppressWarnings("unchecked")
	public CellValue<T> undecorate(CellValue<T> cellValue) {
		if (isDecoratedForCurrentGuess(cellValue)) {
			return CellValueForEducatedGuess.class.cast(cellValue).decoratedObject;
		}
		return cellValue;
	}

	private boolean isDecoratedForCurrentGuess(CellValue<T> cellValue) {
		if (!CellValueForEducatedGuess.class.isInstance(cellValue)) {
			return false;
		}
		return CellValueForEducatedGuess.class.cast(cellValue).educatedGuess == this;
	}

	static class CellValueForEducatedGuess<T> extends AbstractBasicCellValue<T> {

		private EducatedGuess<T> educatedGuess;
		private CellValue<T> decoratedObject;
		private Set<T> removedValues = new HashSet<T>();

		public CellValueForEducatedGuess(EducatedGuess<T> educatedGuess, CellValue<T> decoratedObject) {
			this.educatedGuess = educatedGuess;
			this.decoratedObject = decoratedObject;
		}

		public Set<T> getAllPossibleValues() {
			Set<T> result = new HashSet<T>(decoratedObject.getAllPossibleValues());
			result.removeAll(removedValues);
			return result;
		}

		public void stepToSolution(Collection<Group<T>> groups) {
			removedValues.addAll(getAllSolvedValues(groups));
		}

		public void removePossibleValue(T guess) {
			removedValues.add(guess);
		}

		public void setValue(T value) {
			removedValues = new HashSet<T>(decoratedObject.getAllPossibleValues());
			removedValues.remove(value);
		}

	}
}
