package accesseur;

public interface ActeurSQL {

	public static final String SQL_LISTER_ACTEUR_PAR_FILM = "SELECT * FROM acteur WHERE id_film = ?;";
	
}
