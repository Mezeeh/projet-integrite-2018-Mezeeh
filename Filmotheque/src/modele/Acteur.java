package modele;

public class Acteur {
	protected int id,
					idFilm;
	
	protected float taille;
	
	protected String nom,
					 naissance,
					 nationalite;
	
	public Acteur(String nom, String nationalite) {
		super();
		this.nom = nom;
		this.nationalite = nationalite;
	}

	public Acteur(String nom, String naissance, float taille, String nationalite) {
		super();
		this.nom = nom;
		this.naissance = naissance;
		this.taille = taille;		
		this.nationalite = nationalite;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public float getTaille() {
		return taille;
	}

	public void setTaille(float taille) {
		this.taille = taille;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getNaissance() {
		return naissance;
	}

	public void setNaissance(String naissance) {
		this.naissance = naissance;
	}

	public String getNationalite() {
		return nationalite;
	}

	public void setNationalite(String nationalite) {
		this.nationalite = nationalite;
	}

	public int getIdFilm() {
		return idFilm;
	}

	public void setIdFilm(int idFilm) {
		this.idFilm = idFilm;
	}
}
