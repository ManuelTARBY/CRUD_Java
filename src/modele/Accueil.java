package modele;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Dictionary;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class Accueil extends JFrame {

	private Connexion _con;
	private ArrayList<Apprenant> allApprenant;
	private Apprenant apprenant;
	private JTextField fieldIdApprenant;
	private JTextField promo;
	private JTextField prenom;
	private JTextField nom;
	private JTextField email;
	private JTextField telephone;
	private JCheckBox delegue;
	private JTextField absence;

	public Accueil(Connexion con) {

		// Connexion
		_con = con;

		// Configurer la fenêtre
		setTitle("Gestion des apprenants");
		setSize(800, 600);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(null);
		setVisible(true);

		afficheChampsSaisieAjout();

		// Bouton créer un apprenant
		JButton creeApprenant = new JButton("Créer apprenant");
		creeApprenant.setBounds(440, 250, 150, 40);
		creeApprenant.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (verifAntiDoublon()) {
					if (verifChamps()) {
						_con.creerApprenant(creerApprenant());
						viderChamps();
						allApprenant.clear();
						allApprenant = _con.getAllApprenant("");
					}
				}
			}
		});
		add(creeApprenant);

		// Bouton modifier un apprenant
		JButton modifierApprenant = new JButton("Modifier apprenant");
		modifierApprenant.setBounds(620, 250, 150, 40);
		modifierApprenant.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (verifChamps()) {
					Apprenant app = new Apprenant();
					app = creerApprenant();
					app.setId(Integer.parseInt(fieldIdApprenant.getText()));
					_con.modifierApprenant(app);
					viderChamps();
					allApprenant.clear();
					allApprenant = _con.getAllApprenant("");
				}
			}
		});
		add(modifierApprenant);

		// Bouton affiche tous les apprenants
		JButton btnTsApprenant = new JButton("Afficher tous les apprenants");
		btnTsApprenant.setBounds(70, 40, 240, 40);
		btnTsApprenant.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				afficheTousLesApprenants();
			}
		});
		add(btnTsApprenant);

		// Bouton affiche tous les apprenants par nom
		JButton btnTsApprenantNom = new JButton("Trier apprenants par nom");
		btnTsApprenantNom.setBounds(70, 100, 240, 40);
		btnTsApprenantNom.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				allApprenant.clear();
				allApprenant = _con.getAllApprenant("nom");
				afficheTousLesApprenants();
			}
		});
		add(btnTsApprenantNom);

		// Bouton affiche tous les apprenants par absence
		JButton btnTsApprenantAbs = new JButton("Trier apprenants par absence");
		btnTsApprenantAbs.setBounds(70, 160, 240, 40);
		btnTsApprenantAbs.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				allApprenant.clear();
				allApprenant = _con.getAllApprenant("absence");
				afficheTousLesApprenants();
			}
		});
		add(btnTsApprenantAbs);

		// Bouton affiche tous les apprenants par absence
		JButton btnTsApprenantPromo = new JButton("Trier apprenants par promo");
		btnTsApprenantPromo.setBounds(70, 220, 240, 40);
		btnTsApprenantPromo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (promo.getText().equals("")) {
					System.out.println("Aucune promo de saisie.");
					return;
				}
				allApprenant.clear();
				allApprenant = _con.getAllApprenant(promo.getText());
				afficheTousLesApprenants();
			}
		});
		add(btnTsApprenantPromo);

		// Bouton affiche un apprenant
		JButton btnApprenant = new JButton("Afficher l'apprenant par ID");
		btnApprenant.setBounds(70, 340, 240, 40);
		btnApprenant.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				apprenant = getApprenantById();
				if (apprenant == null) {
					System.out.println("Pas d'apprenant avec cet id !");
				} else {
					apprenant.afficheApprenant();
				}
			}
		});
		add(btnApprenant);

		// Bouton affiche un apprenant
		JButton btnMajApprenant = new JButton("Afficher un apprenant par ID");
		btnMajApprenant.setBounds(500, 450, 200, 40);
		btnMajApprenant.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				apprenant = getApprenantById();
				if (apprenant == null) {
					System.out.println("Pas d'apprenant avec cet id !");
				} else {
					afficheApprenantDansFenetre(apprenant);
				}
			}
		});
		add(btnMajApprenant);

		JLabel lblId = new JLabel("Id: ");
		lblId.setBounds(2, 280, 40, 40);
		add(lblId);

		fieldIdApprenant = new JTextField();
		fieldIdApprenant.setBackground(Color.WHITE);
		fieldIdApprenant.setBounds(20, 280, 50, 40);
		add(fieldIdApprenant);

		// Bouton supprime un apprenant
		JButton btnDeleteApprenant = new JButton("Supprimer un apprenant par ID");
		btnDeleteApprenant.setBounds(70, 280, 300, 40);
		btnDeleteApprenant.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				apprenant = getApprenantById();
				if (apprenant == null) {
					System.out.println("Pas d'apprenant avec cet id !");
				} else {
					if (apprenant.peutEtreSupprime()) {
						System.out.println("Peut être supprimé");
						_con.supprimeApprenant(apprenant.getId());
						apprenant.confirmeSuppression();

						// MAJ liste des apprenants
						allApprenant.clear();
						allApprenant = _con.getAllApprenant("");
					} else {
						System.out.println("L'apprenant est délégué et ne peut pas être supprimé.");
					}
				}
			}
		});
		btnDeleteApprenant.setVisible(true);
		add(btnDeleteApprenant);

		allApprenant = _con.getAllApprenant("");

		repaint();

	}

	/**
	 * Vérifie la cohérence des champs
	 * 
	 * @return
	 */
	public boolean verifChamps() {
		if (promo.getText().equals("")) {
			return false;
		}
		if (nom.getText().equals("")) {
			return false;
		}
		if (prenom.getText().equals("")) {
			return false;
		}
		if (telephone.getText().equals("")) {
			return false;
		}
		if (email.getText().equals("")) {
			return false;
		}
		if (absence.getText().equals("")) {
			return false;
		}

		return true;
	}

	/**
	 * Vider les champs
	 */
	public void viderChamps() {
		promo.setText("");
		nom.setText("");
		prenom.setText("");
		email.setText("");
		telephone.setText("");
		absence.setText("");
		delegue.setSelected(false);
	}

	/**
	 * Affiche l'apprenant dans la fenêtre
	 */
	public void afficheApprenantDansFenetre(Apprenant app) {
		promo.setText(app.getPromotion());
		nom.setText(app.getNom());
		prenom.setText(app.getPrenom());
		email.setText(app.getEmail());
		;
		telephone.setText(app.getTel());
		;
		absence.setText(Integer.toString(app.getNbAbsence()));
		delegue.setSelected(app.isDelegue());
	}

	/**
	 * Génère un apprenant à partir des données saisies
	 */
	public Apprenant creerApprenant() {
		Apprenant app = new Apprenant();
		app.setPromotion(promo.getText());
		app.setNom(nom.getText());
		app.setPrenom(prenom.getText());
		app.setEmail(email.getText());
		app.setTel(telephone.getText());
		app.setNbAbsence(Integer.parseInt(absence.getText()));
		app.setDelegue(delegue.isEnabled());

		return app;
	}

	/**
	 * Vérifie que l'apprenant n'existe pas déjà
	 * 
	 * @return
	 */
	public boolean verifAntiDoublon() {
		for (Apprenant apprenant : allApprenant) {
			if (apprenant.getNom().equals(nom.getText()) || apprenant.getPrenom().equals(prenom.getText())
					|| apprenant.getEmail().equals(email.getText())) {
				return false;
			}
		}

		return true;
	}

	/**
	 * Affiche toutes les zones de saisie pour l'ajout d'un apprenant
	 */
	public void afficheChampsSaisieAjout() {
		JLabel lblPromo = new JLabel("Promotion : ");
		lblPromo.setBounds(440, 20, 80, 25);
		add(lblPromo);
		promo = new JTextField();
		promo.setBounds(530, 20, 70, 25);
		add(promo);
		JLabel lblPrenom = new JLabel("Prénom : ");
		lblPrenom.setBounds(440, 50, 80, 25);
		add(lblPrenom);
		prenom = new JTextField();
		prenom.setBounds(530, 50, 120, 25);
		add(prenom);
		JLabel lblNom = new JLabel("Nom : ");
		lblNom.setBounds(440, 80, 80, 25);
		add(lblNom);
		nom = new JTextField();
		nom.setBounds(530, 80, 120, 25);
		add(nom);
		JLabel lblEmail = new JLabel("Email : ");
		lblEmail.setBounds(440, 110, 80, 25);
		add(lblEmail);
		email = new JTextField();
		email.setBounds(530, 110, 200, 25);
		add(email);
		JLabel lblTel = new JLabel("Tel : ");
		lblTel.setBounds(440, 140, 80, 25);
		add(lblTel);
		telephone = new JTextField();
		telephone.setBounds(530, 140, 80, 25);
		add(telephone);
		JLabel lblNbAbsence = new JLabel("Absences : ");
		lblNbAbsence.setBounds(440, 170, 80, 25);
		add(lblNbAbsence);
		absence = new JTextField();
		absence.setBounds(530, 170, 50, 25);
		add(absence);
		JLabel lblDelegue = new JLabel("Délégué : ");
		lblDelegue.setBounds(440, 200, 80, 25);
		add(lblDelegue);
		delegue = new JCheckBox();
		delegue.setBounds(530, 200, 50, 25);
		add(delegue);

	}

	/**
	 * Affiche les apprenants
	 */
	public void afficheTousLesApprenants() {
		for (Apprenant app : allApprenant) {
			app.afficheApprenant();
		}
	}

	/**
	 * Récupère un apprenant par son id
	 * 
	 * @return
	 */
	public Apprenant getApprenantById() {
		if (fieldIdApprenant.getText().equals("")) {
			return null;
		} else {
			return _con.getApprenantById(fieldIdApprenant.getText());
		}
	}

}
