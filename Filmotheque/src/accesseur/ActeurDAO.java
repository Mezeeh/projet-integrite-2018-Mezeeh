package accesseur;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import modele.Acteur;
import modele.Film;

public class ActeurDAO implements ActeurSQL{
	private Connection connection;
	
	public ActeurDAO() {
		connection = BaseDeDonnees.getInstance().getConnection();
	}
	
	public List<Acteur> listerActeursParFilm(int idFilm){
		List<Acteur> listeActeurs = new ArrayList<Acteur>();
		
		PreparedStatement requeteListeActeurs;
	
		try {			
			requeteListeActeurs = connection.prepareStatement(SQL_LISTER_ACTEUR_PAR_FILM);
			requeteListeActeurs.setInt(1, idFilm);
			
			ResultSet curseurListeActeurs = requeteListeActeurs.executeQuery();
			
			while(curseurListeActeurs.next()){
				int id = curseurListeActeurs.getInt("id");
				
				float taille = curseurListeActeurs.getFloat("taille");
				
				String nom = curseurListeActeurs.getString("nom");
				String naissance = curseurListeActeurs.getString("naissance");								
				String nationalite = curseurListeActeurs.getString("nationalite");	
				
				System.out.println("Acteur " + nom + " est ne le " + naissance);
				
				Acteur acteur = new Acteur(nom, nationalite);
				acteur.setId(id);
				acteur.setNom(nom);
				acteur.setNaissance(naissance);
				acteur.setTaille(taille);
				
				listeActeurs.add(acteur);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return listeActeurs;
	}
	
	public void ajouterActeur(Acteur acteur){
		System.out.println("ActeurDAO.ajouterActeur()");
		PreparedStatement requeteAjouterActeur;
		try {
			requeteAjouterActeur = connection.prepareStatement(SQL_AJOUTER_ACTEUR);
			requeteAjouterActeur.setString(1, acteur.getNom());
			requeteAjouterActeur.setString(2, acteur.getNaissance());
			requeteAjouterActeur.setString(3, acteur.getNationalite());
			requeteAjouterActeur.setString(4, Float.toString(acteur.getTaille()));
			requeteAjouterActeur.setInt(5, acteur.getIdFilm());
			
			System.out.println("SQL : " + SQL_AJOUTER_ACTEUR);
			
			requeteAjouterActeur.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void supprimerActeur(int idActeur) {
		System.out.println("FilmDAO.supprimerActeur()");
		PreparedStatement requeteSupprimerActeur;
		try {
			requeteSupprimerActeur = connection.prepareStatement(SQL_SUPPRIMER_ACTEUR);
			requeteSupprimerActeur.setInt(1, idActeur);
			
			System.out.println("SQL : " + SQL_SUPPRIMER_ACTEUR);
			
			requeteSupprimerActeur.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public Acteur rapporterActeur(int idActeur) {
		PreparedStatement requeteActeur;
		try {
			requeteActeur = connection.prepareStatement(SQL_RAPPORTER_ACTEUR);
			requeteActeur.setInt(1, idActeur);

			System.out.println(SQL_RAPPORTER_ACTEUR);
			
			ResultSet curseurActeur = requeteActeur.executeQuery();
			curseurActeur.next();
			
			int id = curseurActeur.getInt("id");
			String nom = curseurActeur.getString("nom");
			String naissance = curseurActeur.getString("naissance");
			float taille = curseurActeur.getFloat("taille");
			String nationalite = curseurActeur.getString("nationalite");
			int id_film = curseurActeur.getInt("id_film");
			
			Acteur acteur = new Acteur(nom, naissance, taille, nationalite);
			acteur.setId(id);
			acteur.setIdFilm(id_film);
			
			return acteur;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}
}
