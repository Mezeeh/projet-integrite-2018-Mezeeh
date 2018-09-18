package accesseur;

public interface ActeurSQL {

	String SQL_LISTER_ACTEUR_PAR_FILM = "SELECT * FROM acteur WHERE id_film = ?;";
	
}
