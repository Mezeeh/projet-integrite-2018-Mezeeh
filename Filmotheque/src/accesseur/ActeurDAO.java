package accesseur;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import modele.Acteur;

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
}
