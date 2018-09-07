package accesseur;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import modele.Film;

public class FilmDAO {

	private static String BASEDEDONNEES_DRIVER = "org.postgresql.Driver";
	private static String BASEDEDONNEES_URL = "jdbc:postgresql://localhost:5432/filmotheque";
	private static String BASEDEDONNEES_USAGER = "postgres";
	private static String BASEDEDONNEES_MOTDEPASSE = "sudoroot";

	private Connection connection;

	public FilmDAO() {
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

	private List<Film> simulerListerFilms() {
		List<Film> listeFilmsTest = new ArrayList<Film>();

		listeFilmsTest.add(new Film("Die Hard",
				"Un policier new-yorkais, John McClane, est s�par� de sa femme Holly, cadre dans une puissante multinationale japonaise, la Nakatomi Corporation. Venu � Los Angeles passer les f�tes avec elle, il se rend � la tour Nakatomi o� le patron donne une grande soir�e. Tandis que John s'isole pour t�l�phoner, un groupe de terroristes allemands, dirig� par Hans Gruber, p�n�tre dans l'immeuble.",
				"�nigme/Thriller",
				"1988",
				"2h 12m"));
		listeFilmsTest.add(new Film("Rambo",
				"Revenu du Vi�tnam, abruti autant par les mauvais traitements que lui ont jadis inflig�s ses tortionnaires que par l'indiff�rence de ses concitoyens, le soldat Rambo, un ancien des commandos d'�lite, tra�ne sa redoutable carcasse de ville en ville. Un sh�rif teigneux lui interdit l'acc�s de sa bourgade. Rambo insiste. Il veut seulement manger. Le sh�rif le met sous les verrous et laisse son adjoint brutaliser ce divertissant clochard.",
				"Drame/Thriller",
				"1982",
				"1h 33m"));
		listeFilmsTest.add(new Film("Rocky",
				"Rocky Balboa travaille pour Tony Gazzo, un usurier, et dispute de temps � autre des combats de boxe pour quelques dizaines de dollars sous l'appellation de l'�talon Italien. Cependant, Mickey, propri�taire du club de boxe o� Rocky a l'habitude de s'entra�ner, d�cide de c�der son casier � un boxeur plus talentueux.",
				"Drame/Sport",
				"1976",
				"2h 2m"));
		listeFilmsTest.add(new Film("Le Seigneur des anneaux : La Communaut� de l'anneau",
				"Un jeune et timide `Hobbit', Frodon Sacquet, h�rite d'un anneau magique. Bien loin d'�tre une simple babiole, il s'agit d'un instrument de pouvoir absolu qui permettrait � Sauron, le `Seigneur des t�n�bres', de r�gner sur la `Terre du Milieu' et de r�duire en esclavage ses peuples. Frodon doit parvenir jusqu'� la `Crevasse du Destin' pour d�truire l'anneau.",
				"fantasy/Action",
				"2001",
				"3h 48m"));

		return listeFilmsTest;
	}

	public List<Film> listerFilm(){
		List<Film> listeFilms =  new ArrayList<Film>();
		Statement requeteListeFilms;

		try {
			requeteListeFilms = connection.createStatement();
			ResultSet curseurListeFilms = requeteListeFilms.executeQuery("SELECT * FROM film");
			
			while(curseurListeFilms.next()) {
				int id = curseurListeFilms.getInt("id");
				
				String titre = curseurListeFilms.getString("titre");
				String description = curseurListeFilms.getString("description");
				String genre = curseurListeFilms.getString("genre");
				String dateDeSortie = curseurListeFilms.getString("date_de_sortie");
				String duree = curseurListeFilms.getString("duree");
				
				System.out.println("Le film " + titre + " qui presente " + description + " sortie le " + dateDeSortie + "est un film de " + genre + " qui dure " + duree);
				
				Film film = new Film(titre, description, genre, dateDeSortie, duree);
				film.setId(id);
				listeFilms.add(film);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return listeFilms;
	}

	public void ajouterFilm(Film film){
		System.out.println("FilmDAO.ajouterFilm()");
		try {
			Statement requeteAjouterFilm = connection.createStatement();

			String sqlAjouterFilm = "INSERT INTO film(titre, description, genre, date_de_sortie, duree) VALUES('" + film.getTitre() + "','" + film.getDescription() + "','" + film.getGenre() + "','" + film.getDateDeSortie() + "','" + film.getDuree()+ "');";
			System.out.println("SQL : " + sqlAjouterFilm);
			
			requeteAjouterFilm.execute(sqlAjouterFilm);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void modifierFilm(Film film) {
		System.out.println("FilmDAO.modifierFilm()");
		try {
			Statement requeteModifierFilm = connection.createStatement();
			
			String SQL_MODIFIER_FILM = "UPDATE film SET titre = '" + film.getTitre() + "', description = '" + film.getDescription() + "', genre = '" + film.getGenre() + "', date_de_sortie = '" + film.getDateDeSortie() + "', duree = '" + film.getDuree() + "' WHERE id = " + film.getId();
			System.out.println("SQL : " + SQL_MODIFIER_FILM);
			
			requeteModifierFilm.execute(SQL_MODIFIER_FILM);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public Film rapporterFilm(int idFilm) {
		Statement requeteFilm;
		try {
			requeteFilm = connection.createStatement();
			// TODO factoriser chaines magiques dans des constantes - si possible interfaces
			// TODO changer pour requete preparee
			String SQL_RAPPORTER_FILM = "SELECT * FROM film WHERE id = " + idFilm;
			System.out.println(SQL_RAPPORTER_FILM);
			
			ResultSet curseurFilm = requeteFilm.executeQuery(SQL_RAPPORTER_FILM);
			curseurFilm.next();
			
			int id = curseurFilm.getInt("id");
			String titre = curseurFilm.getString("titre");
			String description = curseurFilm.getString("description");
			String genre = curseurFilm.getString("genre");
			String dateDeSortie = curseurFilm.getString("date_de_sortie");
			String duree = curseurFilm.getString("duree");
			
			System.out.println("Le film " + titre + " qui presente " + description + " sortie le " + dateDeSortie + "est un film de " + genre + " qui dure " + duree);
			
			Film film = new Film(titre, description, genre, dateDeSortie, duree);
			film.setId(id);
			
			return film;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}
}
