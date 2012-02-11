package be.butskri.sudoku;

import static junit.framework.Assert.assertEquals;

import java.util.Set;

import org.junit.Test;

import com.google.common.collect.Sets;

public class SudokuTest {
	private static final Set<Integer> ALL_POSSIBLE_VALUES = Sets.newHashSet(1, 2, 3, 4, 5, 6, 7, 8, 9);

	@Test
	public void complexSudokuWithoutOpgaveCanBeSolved() {
		Sudoku<Integer> sudoku = new Sudoku<Integer>(3, 3, ALL_POSSIBLE_VALUES);
		setUpValues(sudoku, 1, null, null, null, null, null, null, null, null, null);
		setUpValues(sudoku, 2, null, null, null, null, null, null, null, null, null);
		setUpValues(sudoku, 3, null, null, null, null, null, null, null, null, null);
		setUpValues(sudoku, 4, null, null, null, null, null, null, null, null, null);
		setUpValues(sudoku, 5, null, null, null, null, null, null, null, null, null);
		setUpValues(sudoku, 6, null, null, null, null, null, null, null, null, null);
		setUpValues(sudoku, 7, null, null, null, null, null, null, null, null, null);
		setUpValues(sudoku, 8, null, null, null, null, null, null, null, null, null);
		setUpValues(sudoku, 9, null, null, null, null, null, null, null, null, null);
		sudoku.solve();
		sudoku.printSolution();
	}

	@Test
	public void complexSudoku1CanBeSolved() {
		Sudoku<Integer> sudoku = new Sudoku<Integer>(3, 3, ALL_POSSIBLE_VALUES);
		setUpValues(sudoku, 1, null, null, null, 9, null, null, null, null, 7);
		setUpValues(sudoku, 2, 6, null, 3, null, 7, 5, null, 1, null);
		setUpValues(sudoku, 3, null, 5, null, 1, 4, null, 6, null, null);
		setUpValues(sudoku, 4, null, 8, 9, null, null, null, 2, null, null);
		setUpValues(sudoku, 5, 3, null, null, null, null, null, null, null, 6);
		setUpValues(sudoku, 6, null, null, 5, null, null, null, 1, 9, null);
		setUpValues(sudoku, 7, null, null, 4, null, 6, 9, null, 8, null);
		setUpValues(sudoku, 8, null, null, null, 5, 8, null, 9, null, 1);
		setUpValues(sudoku, 9, 9, null, null, null, null, 7, null, null, null);
		sudoku.solve();
		sudoku.printSolution();

		assertValues(sudoku, 1, 8, 4, 1, 9, 3, 6, 5, 2, 7);
		assertValues(sudoku, 2, 6, 9, 3, 2, 7, 5, 8, 1, 4);
		assertValues(sudoku, 3, 2, 5, 7, 1, 4, 8, 6, 3, 9);
		assertValues(sudoku, 4, 1, 8, 9, 6, 5, 4, 2, 7, 3);
		assertValues(sudoku, 5, 3, 7, 2, 8, 9, 1, 4, 5, 6);
		assertValues(sudoku, 6, 4, 6, 5, 7, 2, 3, 1, 9, 8);
		assertValues(sudoku, 7, 5, 1, 4, 3, 6, 9, 7, 8, 2);
		assertValues(sudoku, 8, 7, 3, 6, 5, 8, 2, 9, 4, 1);
		assertValues(sudoku, 9, 9, 2, 8, 4, 1, 7, 3, 6, 5);
	}

	@Test
	public void complexSudoku2CanBeSolved() {
		Sudoku<Integer> sudoku = new Sudoku<Integer>(3, 3, ALL_POSSIBLE_VALUES);
		setUpValues(sudoku, 1, null, null, null, null, null, null, null, 6, 2);
		setUpValues(sudoku, 2, null, null, 8, null, 6, 9, 1, null, 5);
		setUpValues(sudoku, 3, null, null, 6, 1, null, null, 9, 3, null);
		setUpValues(sudoku, 4, 8, null, null, 2, null, null, 5, null, null);
		setUpValues(sudoku, 5, null, 1, null, null, 4, null, null, 2, null);
		setUpValues(sudoku, 6, null, null, 4, null, null, 6, null, null, 1);
		setUpValues(sudoku, 7, null, 9, 2, null, null, 8, 7, null, null);
		setUpValues(sudoku, 8, 4, null, 1, 9, 5, null, 2, null, null);
		setUpValues(sudoku, 9, 3, 8, null, null, null, null, null, null, null);
		sudoku.solve();
		sudoku.printSolution();

		assertValues(sudoku, 1, 1, 3, 9, 5, 7, 4, 8, 6, 2);
		assertValues(sudoku, 2, 7, 2, 8, 3, 6, 9, 1, 4, 5);
		assertValues(sudoku, 3, 5, 4, 6, 1, 8, 2, 9, 3, 7);
		assertValues(sudoku, 4, 8, 6, 7, 2, 3, 1, 5, 9, 4);
		assertValues(sudoku, 5, 9, 1, 3, 7, 4, 5, 6, 2, 8);
		assertValues(sudoku, 6, 2, 5, 4, 8, 9, 6, 3, 7, 1);
		assertValues(sudoku, 7, 6, 9, 2, 4, 1, 8, 7, 5, 3);
		assertValues(sudoku, 8, 4, 7, 1, 9, 5, 3, 2, 8, 6);
		assertValues(sudoku, 9, 3, 8, 5, 6, 2, 7, 4, 1, 9);
	}

	private void setUpValues(Sudoku<Integer> sudoku, int row, Integer... values) {
		for (int column = 1; column <= values.length; column++) {
			sudoku.setOpgave(row, column, values[column - 1]);
		}
	}

	private void assertValues(Sudoku<Integer> sudoku, int row, Integer... values) {
		for (int column = 1; column <= values.length; column++) {
			assertEquals("row " + row + ",column " + column + " has wrong value", values[column - 1], sudoku.getValue(row, column));
		}
	}
}
