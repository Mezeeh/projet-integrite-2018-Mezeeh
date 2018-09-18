package accesseur;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
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
	
	public List<Acteur> simulerListeActeurs(){
		// TEST multiplicite
		List<Acteur> listeActeurs = new ArrayList<Acteur>();
		Acteur personne;
		
		personne = new Acteur("Leonardo DiCaprio", "Américain");
		listeActeurs.add(personne);
		
		personne = new Acteur("Clint Eastwood", "Américain");
		listeActeurs.add(personne);
		
		personne = new Acteur("Brad Pitt", "Américain");
		listeActeurs.add(personne);
		
		personne = new Acteur("Robert De Niro", "Américain/Italien");
		listeActeurs.add(personne);
		
		return listeActeurs;
	}
}
