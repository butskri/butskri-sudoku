package be.butskri.sudoku;

import java.util.Collection;
import java.util.Set;

public interface CellValue<T> {

	boolean isSolved();

	boolean isOpgave();

	T getValue();

	void setValue(T value);

	Set<T> getAllPossibleValues();

	void stepToSolution(Collection<Group<T>> groups);

	void removePossibleValue(T guess);

}
