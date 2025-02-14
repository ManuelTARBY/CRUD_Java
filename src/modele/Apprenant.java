package modele;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Apprenant {

	private int id;
	private String nom;
	private String prenom;
	private String email;
	private String promotion;
	private String tel;
	private int nbAbsence;
	private boolean delegue;
	
	
	public Apprenant() {
		
	}
	
	/**
	 * Vérifie si l'apprenant peut-être supprimé
	 * @return
	 */
	public boolean peutEtreSupprime() {
		return !isDelegue();
	}

	
	/**
	 * Récupère un apprenant
	 */
	public Apprenant getApprenant(ResultSet datas) {
		try {
			setId(datas.getInt("id"));
			setPromotion(datas.getString("promotion"));
			setNom(datas.getString("nom"));
			setPrenom(datas.getString("prenom"));
			setNbAbsence(datas.getInt("nb_absence"));
			setEmail(datas.getString("mail"));
			setTel(datas.getString("telephone"));
			setDelegue(datas.getBoolean("delegue"));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return this;
	}
	
	public void confirmeSuppression() {
		System.out.println("L'apprenant a été supprimé.");
	}

	public void afficheApprenant() {
		if(getId() == 0) {
			System.out.println("Pas d'apprenant avec cet id !");
			return;
		}
		System.out.println("Id : " + getId());
		System.out.println("Promotion : " + getPromotion());
		System.out.println("Prénom : " + getPrenom());
		System.out.println("Nom : " + getNom());
		System.out.println("Téléphone : " + getTel());
		System.out.println("Email : " + getEmail());
		System.out.println("Nombre d'absences : " + getNbAbsence());
		System.out.println("Delegue : " + isDelegue());
		System.out.println("");
	}

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}



	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}



	/**
	 * @return the nom
	 */
	public String getNom() {
		return nom;
	}



	/**
	 * @param nom the nom to set
	 */
	public void setNom(String nom) {
		this.nom = nom;
	}



	/**
	 * @return the prenom
	 */
	public String getPrenom() {
		return prenom;
	}



	/**
	 * @param prenom the prenom to set
	 */
	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}



	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}



	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}



	/**
	 * @return the promotion
	 */
	public String getPromotion() {
		return promotion;
	}



	/**
	 * @param promotion the promotion to set
	 */
	public void setPromotion(String promotion) {
		this.promotion = promotion;
	}



	/**
	 * @return the tel
	 */
	public String getTel() {
		return tel;
	}



	/**
	 * @param tel the tel to set
	 */
	public void setTel(String tel) {
		this.tel = tel;
	}



	/**
	 * @return the nbAbsence
	 */
	public int getNbAbsence() {
		return nbAbsence;
	}



	/**
	 * @param nbAbsence the nbAbsence to set
	 */
	public void setNbAbsence(int nbAbsence) {
		this.nbAbsence = nbAbsence;
	}



	/**
	 * @return the delegue
	 */
	public boolean isDelegue() {
		return delegue;
	}



	/**
	 * @param delegue the delegue to set
	 */
	public void setDelegue(boolean delegue) {
		this.delegue = delegue;
	}

}
