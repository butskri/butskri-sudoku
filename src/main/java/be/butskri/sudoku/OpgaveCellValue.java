package be.butskri.sudoku;

import java.util.Collection;
import java.util.Set;

import com.google.common.collect.Sets;

public class OpgaveCellValue<T> implements CellValue<T> {

	private T value;

	public OpgaveCellValue(T value) {
		this.value = value;
	}

	@Override
	@SuppressWarnings("unchecked")
	public Set<T> getAllPossibleValues() {
		return Sets.newHashSet(value);
	}

	@Override
	public T getValue() {
		return value;
	}

	@Override
	public boolean isOpgave() {
		return true;
	}

	@Override
	public boolean isSolved() {
		return true;
	}

	@Override
	public void stepToSolution(Collection<Group<T>> groups) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void removePossibleValue(T guess) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setValue(T value) {
		throw new UnsupportedOperationException();
	}

}
