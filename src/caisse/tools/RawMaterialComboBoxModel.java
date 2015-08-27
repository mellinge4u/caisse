package caisse.tools;

import javax.swing.ComboBoxModel;
import javax.swing.event.ListDataListener;

import caisse.Model;
import caisse.product.RawMaterial;

public class RawMaterialComboBoxModel implements ComboBoxModel<RawMaterial>{

	protected Model model;
	
	public RawMaterialComboBoxModel(Model model) {
		this.model= model;
	}

	@Override
	public void addListDataListener(ListDataListener arg0) {
		// TODO Auto-generated method stub
	}

	@Override
	public RawMaterial getElementAt(int arg0) {
		return model.getAllMarerials().get(arg0);
	}

	@Override
	public int getSize() {
		return model.getAllMarerials().size();
	}

	@Override
	public void removeListDataListener(ListDataListener arg0) {
		// TODO Auto-generated method stub
	}

	@Override
	public Object getSelectedItem() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setSelectedItem(Object anItem) {
		// TODO Auto-generated method stub
		
	}

}
