package accesseur;

public interface FilmSQL {

	public static final String SQL_LISTER_FILM = "SELECT * FROM film;";
	public static final String SQL_AJOUTER_FILM = "INSERT INTO film(titre, description, genre, date_de_sortie, duree) VALUES(?, ?, ?, ?, ?);";
	public static final String SQL_MODIFIER_FILM = "UPDATE film SET titre = ?, description = ?, genre = ?, date_de_sortie = ?, duree = ? WHERE id = ?;";
	public static final String SQL_SUPPRIMER_FILM = "DELETE FROM film WHERE id = ?;";
	public static final String SQL_RAPPORTER_FILM = "SELECT * FROM film WHERE id = ?;";
	
}
