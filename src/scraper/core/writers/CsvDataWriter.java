package scraper.core.writers;

import scraper.core.CsvCharacters;

import java.io.FileNotFoundException;

public class CsvDataWriter extends FileDataWriter {

	private final int valuesPerLine;
	private int valueIndex;

	public CsvDataWriter(String filePath, int valuesPerLine) throws FileNotFoundException {
		super(filePath);
		this.valuesPerLine = valuesPerLine;
		valueIndex = 0;
	}

	@Override
	public void write(String data) {
		String formatted = applyFormat(data);
		super.write(formatted);
	}

	private String applyFormat(String data) {
		String dataWithQuotesChecked = withQuotesWhenNeeded(data);
		updateValueIndex();
		return withSeparatorOrNewLine(dataWithQuotesChecked);
	}

	private String withQuotesWhenNeeded(String data) {
		boolean quotesNeeded = containsSeparator(data);

		if (quotesNeeded) {
			return CsvCharacters.QUOTE + data + CsvCharacters.QUOTE;
		}

		return data;
	}

	private boolean containsSeparator(String data) {
		String separator = Character.toString(CsvCharacters.SEPARATOR);
		return data.contains(separator);
	}

	private void updateValueIndex() {
		valueIndex = (valueIndex + 1) % valuesPerLine;
	}

	private String withSeparatorOrNewLine(String data) {
		return isLastValueInLine()
			? data + '\n'
			: data + CsvCharacters.SEPARATOR;
	}

	private boolean isLastValueInLine() {
		return valueIndex == 0;
	}

}
