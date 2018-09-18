package accesseur;

public interface FilmSQL {

	String SQL_LISTER_FILM = "SELECT * FROM film;";
	String SQL_AJOUTER_FILM = "INSERT INTO film(titre, description, genre, date_de_sortie, duree) VALUES(?, ?, ?, ?, ?);";
	String SQL_MODIFIER_FILM = "UPDATE film SET titre = ?, description = ?, genre = ?, date_de_sortie = ?, duree = ? WHERE id = ?;";
	String SQL_RAPPORTER_FILM = "SELECT * FROM film WHERE id = ?";
	
}
