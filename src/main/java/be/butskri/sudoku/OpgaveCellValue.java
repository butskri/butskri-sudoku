package be.butskri.sudoku;

import java.util.Collection;
import java.util.Set;

import com.google.common.collect.Sets;

public class OpgaveCellValue<T> implements CellValue<T> {

	private T value;

	public OpgaveCellValue(T value) {
		this.value = value;
	}

	@SuppressWarnings("unchecked")
	public Set<T> getAllPossibleValues() {
		return Sets.newHashSet(value);
	}

	public T getValue() {
		return value;
	}

	public boolean isOpgave() {
		return true;
	}

	public boolean isSolved() {
		return true;
	}

	public void stepToSolution(Collection<Group<T>> groups) {
		throw new UnsupportedOperationException();
	}

	public void removePossibleValue(T guess) {
		throw new UnsupportedOperationException();
	}

	public void setValue(T value) {
		throw new UnsupportedOperationException();
	}

}
