package be.butskri.sudoku;

public interface CellValueDecorator<T> {

	CellValue<T> decorate(CellValue<T> cellValue);

	CellValue<T> undecorate(CellValue<T> cellValue);
}
