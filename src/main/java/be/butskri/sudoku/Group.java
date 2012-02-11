package be.butskri.sudoku;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class Group<T> {

	private Set<Cell<T>> cells;

	public Group(Collection<Cell<T>> cells) {
		this.cells = new HashSet<Cell<T>>(cells);
		for (Cell<T> cell : cells) {
			cell.addGroup(this);
		}
	}

	public Set<T> getAllSolvedValues() {
		Set<T> result = new HashSet<T>();
		for (Cell<T> cell : cells) {
			if (cell.isSolved()) {
				result.add(cell.getValue());
			}
		}
		return result;
	}

	public boolean isValid() {
		boolean result = countSolvedCells() == getAllSolvedValues().size();
		if (!result) {
			System.out.println("invalid: " + countSolvedCells() + ", all solved values: " + getAllSolvedValues());
		}
		return result;
	}

	private int countSolvedCells() {
		int result = 0;
		for (Cell<T> cell : cells) {
			if (cell.isSolved()) {
				result++;
			}
		}
		return result;
	}

}
