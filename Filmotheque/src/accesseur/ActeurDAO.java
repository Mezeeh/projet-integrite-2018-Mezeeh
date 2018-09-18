package accesseur;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import modele.Acteur;

public class ActeurDAO {

	private static String BASEDEDONNEES_DRIVER = "org.postgresql.Driver";
	private static String BASEDEDONNEES_URL = "jdbc:postgresql://localhost:5432/filmotheque";
	private static String BASEDEDONNEES_USAGER = "postgres";
	private static String BASEDEDONNEES_MOTDEPASSE = "sudoroot";
	
	private Connection connection;
	
	public ActeurDAO() {
		try {
			Class.forName(BASEDEDONNEES_DRIVER);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		try {
			connection = DriverManager.getConnection(BASEDEDONNEES_URL, BASEDEDONNEES_USAGER, BASEDEDONNEES_MOTDEPASSE);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public List<Acteur> listerActeurs(){
		List<Acteur> listeActeurs = new ArrayList<Acteur>();
		Statement requeteListeActeurs;
		
		try {
			requeteListeActeurs = connection.createStatement();
			ResultSet curseurListeActeurs = requeteListeActeurs.executeQuery("SELECT * FROM acteur WHERE id_film = 1");
			
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
