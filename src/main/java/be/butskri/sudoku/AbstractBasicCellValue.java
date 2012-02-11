package be.butskri.sudoku;

import java.util.Collection;
import java.util.HashSet;

public abstract class AbstractBasicCellValue<T> implements CellValue<T> {

	public T getValue() {
		if (isSolved()) {
			return getAllPossibleValues().iterator().next();
		}
		return null;
	}

	public boolean isOpgave() {
		return false;
	}

	public boolean isSolved() {
		return getAllPossibleValues().size() == 1;
	}

	Collection<T> getAllSolvedValues(Collection<Group<T>> groups) {
		Collection<T> result = new HashSet<T>();
		for (Group<T> group : groups) {
			result.addAll(group.getAllSolvedValues());
		}
		return result;
	}
}
