package graphique.client;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.util.Calendar;
import java.util.Date;
import java.util.Hashtable;
import java.util.Map;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.SpinnerDateModel;

import logiqueMetier.Serveur;
import objets.Repas;
import objets.TypeVehicule;
import objets.Vehicule;
import objets.Ville;
import tools.DateTools;

/**
 * Panel permettant d'entrer les informations de recherche sur un trajet
 * 
 * @author Fauvel-jaeger Olivier, Tanguy Arnaud, Ceschel Marvin, Kruck Nathan
 * @version 2012.01.29
 */

public class TrajetPanel extends JPanel {
    private static final long serialVersionUID = 1L;

    private JComboBox villeDepartCombo;
    private JComboBox villeArriveeCombo;
    private JSpinner dateDepartSpinner;
    private JComboBox transport;
    private JList repas;
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

        repas = new JList(new DefaultListModel());
        add(new JLabel("Repas"));
        JScrollPane listScroller = new JScrollPane(repas);
        add(listScroller);

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
            repas.removeAll();
            TypeVehicule t = (TypeVehicule) transport.getSelectedItem();
            Vehicule v = null;
            try {
                v = serveur.creerVehicule("", t, -1);
            } catch (Exception e) {
                e.printStackTrace();
            }
            if(v != null) {
                for(Repas r: v.getRepas()) {
                    ((DefaultListModel)repas.getModel()).addElement(r.toString());
                }
            }

        }

    }

    public boolean getCouchette() {
        return couchette.isSelected();
    }

    public boolean getModifiable() {
        return modifiable.isSelected();
    }

    public Map<String, Boolean> getRepas() {
        System.out.println("get repas");
        Hashtable<String, Boolean> tab = new Hashtable<String, Boolean>();
        DefaultListModel model = (DefaultListModel)repas.getModel();
        for(int i=0; i<model.getSize(); i++) {
            System.out.println(model.get(i)+" "+repas.isSelectedIndex(i));
            tab.put((String)model.get(i), repas.isSelectedIndex(i));
        }
        return tab;
    }

    public boolean getDirect() {
        return direct.isSelected();
    }

    public boolean getPremiereClasse() {
        return false;
    }
}
