package modele;

public class Film {
	protected String titre,
					description,
					genre,
					dateDeSortie,
					duree;
	
	protected int id;

	public Film(String titre) {
		super();
		this.titre = titre;
	}
	
	public Film(String titre, String description) {
		super();
		this.titre = titre;
		this.description = description;
	}

	public Film(String titre, String description, String genre) {
		super();
		this.titre = titre;
		this.description = description;
		this.genre = genre;
	}

	public Film(String titre, String description, String genre, String dateDeSortie) {
		super();
		this.titre = titre;
		this.description = description;
		this.genre = genre;
		this.dateDeSortie = dateDeSortie;
	}

	public Film(String titre, String description, String genre, String dateDeSortie, String duree) {
		super();
		this.titre = titre;
		this.description = description;
		this.genre = genre;
		this.dateDeSortie = dateDeSortie;
		this.duree = duree;
	}

	public String getTitre() {
		return titre;
	}

	public void setTitre(String titre) {
		this.titre = titre;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getGenre() {
		return genre;
	}

	public void setGenre(String genre) {
		this.genre = genre;
	}

	public String getDateDeSortie() {
		return dateDeSortie;
	}

	public void setDateDeSortie(String dateDeSortie) {
		this.dateDeSortie = dateDeSortie;
	}

	public String getDuree() {
		return duree;
	}

	public void setDuree(String duree) {
		this.duree = duree;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}	
}
