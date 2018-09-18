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
	private Connection connection;

	public FilmDAO() {
		connection = BaseDeDonnees.getInstance().getConnection();
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
