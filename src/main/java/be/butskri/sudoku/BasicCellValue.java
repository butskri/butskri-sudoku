package be.butskri.sudoku;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import com.google.common.collect.Sets;

public class BasicCellValue<T> extends AbstractBasicCellValue<T> {

	private Set<T> allPossibleValues;

	public BasicCellValue(Collection<T> allPossibleValues) {
		this.allPossibleValues = new HashSet<T>(allPossibleValues);
	}

	public Set<T> getAllPossibleValues() {
		return Collections.unmodifiableSet(allPossibleValues);
	}

	public void stepToSolution(Collection<Group<T>> groups) {
		allPossibleValues.removeAll(getAllSolvedValues(groups));
	}

	public void removePossibleValue(T guess) {
		allPossibleValues.remove(guess);

	}

	@SuppressWarnings("unchecked")
	public void setValue(T value) {
		allPossibleValues = Sets.<T> newHashSet(value);
	}

}
