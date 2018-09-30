package accesseur;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import modele.Film;

public class FilmDAO implements FilmSQL{
	private Connection connection;

	public FilmDAO() {
		connection = BaseDeDonnees.getInstance().getConnection();
	}

	public List<Film> listerFilm(){
		List<Film> listeFilms =  new ArrayList<Film>();
		Statement requeteListeFilms;

		try {
			requeteListeFilms = connection.createStatement();
			ResultSet curseurListeFilms = requeteListeFilms.executeQuery(SQL_LISTER_FILM);
			
			while(curseurListeFilms.next()) {
				int id = curseurListeFilms.getInt("id");
				
				String titre = curseurListeFilms.getString("titre");
				String description = curseurListeFilms.getString("description");
				String genre = curseurListeFilms.getString("genre");
				String dateDeSortie = curseurListeFilms.getString("date_de_sortie");
				int duree = curseurListeFilms.getInt("duree");
				
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
		PreparedStatement requeteAjouterFilm;
		try {
			requeteAjouterFilm = connection.prepareStatement(SQL_AJOUTER_FILM);
			requeteAjouterFilm.setString(1, film.getTitre());
			requeteAjouterFilm.setString(2, film.getDescription());
			requeteAjouterFilm.setString(3, film.getGenre());
			requeteAjouterFilm.setString(4, film.getDateDeSortie());
			requeteAjouterFilm.setInt(5, film.getDuree());
			
			System.out.println("SQL : " + SQL_AJOUTER_FILM);
			
			requeteAjouterFilm.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void modifierFilm(Film film) {
		System.out.println("FilmDAO.modifierFilm()");
		PreparedStatement requeteModifierFilm;
		try {
			requeteModifierFilm = connection.prepareStatement(SQL_MODIFIER_FILM);
			requeteModifierFilm.setString(1, film.getTitre());
			requeteModifierFilm.setString(2, film.getDescription());
			requeteModifierFilm.setString(3, film.getGenre());
			requeteModifierFilm.setString(4, film.getDateDeSortie());
			requeteModifierFilm.setInt(5, film.getDuree());
			requeteModifierFilm.setInt(6, film.getId());
			
			System.out.println("SQL : " + SQL_MODIFIER_FILM);
			
			requeteModifierFilm.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void supprimerFilm(int idFilm) {
		System.out.println("FilmDAO.supprimerFilm()");
		PreparedStatement requeteSupprimerFilm;
		try {
			requeteSupprimerFilm = connection.prepareStatement(SQL_SUPPRIMER_FILM);
			requeteSupprimerFilm.setInt(1, idFilm);
			
			System.out.println("SQL : " + SQL_MODIFIER_FILM);
			
			requeteSupprimerFilm.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public Film rapporterFilm(int idFilm) {
		PreparedStatement requeteFilm;
		try {
			requeteFilm = connection.prepareStatement(SQL_RAPPORTER_FILM);
			requeteFilm.setInt(1, idFilm);

			System.out.println(SQL_RAPPORTER_FILM);
			
			ResultSet curseurFilm = requeteFilm.executeQuery();
			curseurFilm.next();
			
			int id = curseurFilm.getInt("id");
			String titre = curseurFilm.getString("titre");
			String description = curseurFilm.getString("description");
			String genre = curseurFilm.getString("genre");
			String dateDeSortie = curseurFilm.getString("date_de_sortie");
			int duree = curseurFilm.getInt("duree");
			
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
