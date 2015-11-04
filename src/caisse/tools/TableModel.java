package caisse.tools;

import javax.swing.table.AbstractTableModel;

public abstract class TableModel extends AbstractTableModel {

	protected String[] colNames;
	protected Class<?>[] colClass;
	protected Boolean[] colEdit;
	
	public CellRender getColumnModel (int col) {
		return new CellRender(colClass[col], colEdit[col], false);
	}
	
	@Override
	public int getColumnCount() {
		return colNames.length;
	}

	@Override
	public String getColumnName(int columnIndex) {
		return colNames[columnIndex];
	}

	@Override
	public Class<?> getColumnClass(int columnIndex) {
		return colClass[columnIndex];
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return colEdit[columnIndex];
	}

}
