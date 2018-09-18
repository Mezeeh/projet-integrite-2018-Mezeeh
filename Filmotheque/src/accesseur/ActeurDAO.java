package accesseur;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import modele.Acteur;

public class ActeurDAO {
	private Connection connection;
	
	public ActeurDAO() {
		connection = BaseDeDonnees.getInstance().getConnection();
	}
	
	public List<Acteur> listerActeurs(int idFilm){
		List<Acteur> listeActeurs = new ArrayList<Acteur>();
		Statement requeteListeActeurs;
		
		try {
			requeteListeActeurs = connection.createStatement();
			ResultSet curseurListeActeurs = requeteListeActeurs.executeQuery("SELECT * FROM acteur WHERE id_film =" + idFilm);
			
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
