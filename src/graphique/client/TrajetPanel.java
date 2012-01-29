package graphique.client;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.util.Calendar;
import java.util.Date;
import java.util.Hashtable;
import java.util.Map;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerDateModel;

import logiqueMetier.Serveur;
import objets.TypeVehicule;
import objets.Ville;
import tools.DateTools;

/**
 * @author Fauvel-jaeger Olivier, Tanguy Arnaud, Ceschel Marvin, Kruck Nathan
 * @version 2012.01.29
 */

public class TrajetPanel extends JPanel {
    private JComboBox villeDepartCombo;
    private JComboBox villeArriveeCombo;
    private JSpinner dateDepartSpinner;
    private JComboBox transport;
    private JComboBox repas;
    private JCheckBox modifiable;
    private JCheckBox couchette;
    private JCheckBox direct;
    private JCheckBox premiereClasse;

    private Serveur serveur;

    public TrajetPanel(Serveur s) {
        super();
        serveur = s;
        build();
    }

    private void build() {
        setBorder(BorderFactory
                .createTitledBorder("Où et quand souhaitez-vous partir ?"));
        setLayout(new GridLayout(0, 2));

        villeDepartCombo = new JComboBox();
        for (Ville v : serveur.getVilles()) {
            villeDepartCombo.addItem(v);
        }
        add(new JLabel("Ville de départ "));
        add(villeDepartCombo);

        add(new JLabel("Ville d'arrivée"));
        villeArriveeCombo = new JComboBox();
        add(villeArriveeCombo);

        DepartComboAction departComboAction = new DepartComboAction();
        villeDepartCombo.setAction(departComboAction);
        departComboAction.actionPerformed(null);

        // Create a SpinnerDateModel with current date as the initial value.
        SpinnerDateModel model = new SpinnerDateModel();

        add(new JLabel("Date de départ "));
        dateDepartSpinner = new JSpinner(model);
        add(dateDepartSpinner);
        
        transport = new JComboBox();
        for(TypeVehicule type:TypeVehicule.values()) {
        	transport.addItem(type);
        }
        add(new JLabel("Moyen de transport"));
        add(transport);
        
        repas = new JComboBox();
        add(new JLabel("Repas"));
        add(repas);
       
        TransportComboAction transportComboAction = new TransportComboAction();
        transport.setAction(transportComboAction);
        transportComboAction.actionPerformed(null);
        
        add(new JLabel("Trajet modifiable"));
        modifiable = new JCheckBox();
        add(modifiable);

        add(new JLabel("Avec couchette"));
        couchette = new JCheckBox();
        add(couchette);
        
        add(new JLabel("Trajet direct"));
        direct = new JCheckBox();
        direct.setSelected(true);
        add(direct);
        
        add(new JLabel("Première classe"));
        premiereClasse = new JCheckBox();
        add(premiereClasse);
    }

    public Ville getVilleDepart() {
        return (Ville) villeDepartCombo.getSelectedItem();
    }

    public Ville getVilleArrivee() {
        return (Ville) villeArriveeCombo.getSelectedItem();
    }

    public Calendar getDateDepart() {
        return DateTools.dateToCalendar((Date) dateDepartSpinner.getModel()
                .getValue());
    }

    private class DepartComboAction extends AbstractAction {
		private static final long serialVersionUID = 1L;

		public DepartComboAction() {
        }

        @Override
        public void actionPerformed(ActionEvent arg0) {
            villeArriveeCombo.removeAllItems();
            for (Ville v : serveur.getVillesArrivee((Ville) villeDepartCombo
                    .getSelectedItem())) {
                villeArriveeCombo.addItem(v);
            }
        }

    }
    
    private class TransportComboAction extends AbstractAction {
		private static final long serialVersionUID = 1L;

		public TransportComboAction() {
        }

        @Override
        public void actionPerformed(ActionEvent arg0) {
            repas.removeAllItems();
            // FIXME : TypeVehicule does not contain info on repas, Vehicule does
          /*  for (Repas r : ((Vehicule)transport.getSelectedItem()).getRepas())  {
            	repas.addItem(r);
            } */
        }

    }

    public boolean getCouchette() {
        return couchette.isSelected();
    }

    public boolean getModifiable() {
        return modifiable.isSelected();
    }

	public Map<String, Boolean> getRepas() {
		return new Hashtable<String, Boolean>();
	}

	public boolean getDirect() {
		return direct.isSelected();
	}

	public boolean getPremiereClasse() {
		return false;
	}
}
