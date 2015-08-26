package caisse.view;

import java.awt.GridLayout;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import caisse.Model;

public class RestockageView extends JPanel implements Observer {

	protected Model model;
	
	private JPanel panelLeft;
	
	//TODO AJOUTER LES ITEMS

	private JPanel panelRight;
	private JButton accept;
	private JButton cancel;
	private JLabel prixAnnonce;
	private JLabel prixAnnonce2;
	private JLabel prixReel;
	private JTextArea prixReel2;
	
	public RestockageView(Model model){
		this.model = model;
		model.addObserver(this);
		this.panelLeft = new JPanel();
		//TODO ce quil y a a ajouter
		this.panelRight = new JPanel();
		accept = new JButton("Accepter");
		cancel = new JButton("Annuler l'opération");
		prixAnnonce = new JLabel("Prix annoncé : ");
		prixAnnonce2 = new JLabel(" 0,0 ");
		prixReel = new JLabel("Prix à l'achat (réel) : ");
		prixReel2 = new JTextArea();
		this.setLayout(new GridLayout(1, 2));
		this.add(panelLeft);
		this.add(panelRight);
		panelLeft.setLayout(new GridLayout(1, 2));
		panelLeft.add(accept);
		panelLeft.add(cancel);
		
		panelRight.setLayout(new GridLayout(2, 2));
		panelRight.add(prixAnnonce);
		panelRight.add(prixAnnonce2);
		panelRight.add(prixReel);
		panelRight.add(prixReel2);
	}

	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		
	}
	
}
