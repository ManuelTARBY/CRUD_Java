package modele;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Connexion {

	private Connection con;
	private Statement stmt;
	private ResultSet result;
	private String url = "jdbc:mysql://localhost:3306/gestion_apprenant";
	private String user = "manu";
	private String password = "";

	public Connexion() {

		try {
			con = DriverManager.getConnection(url, user, password);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (con != null)
					con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Récupère la connexion
	 * 
	 * @return con La connexion
	 */
	public Connection getConnection() {
		return con;
	}

	/**
	 * Récupère le statement
	 * 
	 * @return stmt Statement
	 */
	public Statement getStatement() {
		return stmt;
	}
	
	/**
	 * Récupère les apprenants
	 * 
	 * @return apprenants Liste des apprenants
	 */
	public ArrayList<Apprenant> getAllApprenant(String tri) {

		ArrayList<Apprenant> apprenants = new ArrayList<Apprenant>();
		
		try {
			con = DriverManager.getConnection(url, user, password);
			if (con != null) {
				stmt = con.createStatement();
				if (stmt != null) {
					try {
						String req = "SELECT * FROM apprenant";
						switch (tri){
						case "nom":
							req += " ORDER BY nom";
							break;
						case "absence":
							req += " ORDER BY nb_absence";
							break;
						case "":
							break;
						default:
							req = "SELECT * from apprenant where promotion = '" + tri +"'";
							break;
						}
						result = stmt.executeQuery(req);
						while (result.next()) {
							Apprenant newApprenant = new Apprenant();
							newApprenant.getApprenant(result);
							apprenants.add(newApprenant);
						}
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (result != null)
					result.close();
				if (stmt != null)
					stmt.close();
				if (con != null)
					con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return apprenants;
	}
	
	public void supprimeApprenant(int id) {
		try {
			con = DriverManager.getConnection(url, user, password);
			if (con != null) {
				stmt = con.createStatement();
				if (stmt != null) {
					try {
						stmt.executeUpdate("DELETE FROM apprenant WHERE id = " + id);
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (result != null)
					result.close();
				if (stmt != null)
					stmt.close();
				if (con != null)
					con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}
	
	/**
	 * Crée un apprenant en BDD
	 * @param app
	 */
	public void creerApprenant(Apprenant app) {
		try {
			con = DriverManager.getConnection(url, user, password);
			if (con != null) {
				stmt = con.createStatement();
				if (stmt != null) {
					try {
						int value = app.isDelegue() ? 1 : 0;
						stmt.executeUpdate("INSERT INTO apprenant(`promotion`, `nom`, `prenom`, `telephone`, `nb_absence`, `delegue`, `mail`) VALUES ('"+app.getPromotion()+"','"+app.getNom()+"','"+app.getPrenom()+"','"+app.getTel()+"','"+app.getNbAbsence()+"','"+value+"','"+app.getEmail()+"')");
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (result != null)
					result.close();
				if (stmt != null)
					stmt.close();
				if (con != null)
					con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}
	
	/**
	 * Crée un apprenant en BDD
	 * @param app
	 */
	public void modifierApprenant(Apprenant app) {
		try {
			con = DriverManager.getConnection(url, user, password);
			if (con != null) {
				stmt = con.createStatement();
				if (stmt != null) {
					try {
						int value = app.isDelegue() ? 1 : 0;
						stmt.executeUpdate("UPDATE `apprenant` SET `promotion`='"+app.getPromotion()+"',`nom`='"+app.getNom()+"',`prenom`='"+app.getPrenom()+"',`telephone`='"+app.getTel()+"',`nb_absence`='"+app.getNbAbsence()+"',`delegue`='"+value+"',`mail`='"+app.getEmail()+"' WHERE id = '"+app.getId()+"'");
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (result != null)
					result.close();
				if (stmt != null)
					stmt.close();
				if (con != null)
					con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}
	
	/**
	 * Récupère les apprenants
	 * 
	 * @return apprenants Liste des apprenants
	 */
	public Apprenant getApprenantById(String id) {

		Apprenant apprenant = new Apprenant();
		
		try {
			con = DriverManager.getConnection(url, user, password);
			if (con != null) {
				stmt = con.createStatement();
				if (stmt != null) {
					try {
						result = stmt.executeQuery("SELECT * FROM apprenant WHERE id = " + id);
						while (result.next()) {
							apprenant.getApprenant(result);
						}
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (result != null)
					result.close();
				if (stmt != null)
					stmt.close();
				if (con != null)
					con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return apprenant;
	}

}
