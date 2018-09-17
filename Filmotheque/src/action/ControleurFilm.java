package action;

import java.util.ArrayList;
import java.util.List;

import accesseur.FilmDAO;
import modele.Acteur;
import modele.Film;
import vue.NavigateurDesVues;
import vue.VueAjouterFilm;
import vue.VueEditerFilm;
import vue.VueFilm;
import vue.VueListeFilm;

public class ControleurFilm {
	
	private static ControleurFilm instance;

	private NavigateurDesVues navigateur;
	
	private VueAjouterFilm vueAjouterFilm;
	private VueEditerFilm vueEditerFilm;
	private VueListeFilm vueListeFilm;
	private VueFilm vueFilm;
	
	private FilmDAO filmDAO;
	
	public ControleurFilm()
	{
		instance = null;
		vueAjouterFilm = null;
		vueListeFilm = null;
		vueFilm = null;
		
		filmDAO = new FilmDAO();
		
		System.out.println("Initialisation du controleur");
	}
	
	public static ControleurFilm getInstance() {
		if(null == instance)
			instance = new ControleurFilm();
		
		return instance;
	}
	
	public void activerVues(NavigateurDesVues navigateur) {
		this.navigateur = navigateur;
		this.vueAjouterFilm = navigateur.getVueAjouterFilm();
		this.vueEditerFilm = navigateur.getVueEditerFilm();
		this.vueListeFilm = navigateur.getVueListeFilm();
		this.vueFilm = navigateur.getVueFilm();
		
		//// TEST ////
		Film film = new Film("Die Hard",
				"Un policier new-yorkais, John McClane, est sï¿½parï¿½ de sa femme Holly, cadre dans une puissante multinationale japonaise, la Nakatomi Corporation. Venu ï¿½ Los Angeles passer les fï¿½tes avec elle, il se rend ï¿½ la tour Nakatomi oï¿½ le patron donne une grande soirï¿½e. Tandis que John s'isole pour tï¿½lï¿½phoner, un groupe de terroristes allemands, dirigï¿½ par Hans Gruber, pï¿½nï¿½tre dans l'immeuble.",
				"ï¿½nigme/Thriller",
				"1988",
				"2h 12m");
		this.vueFilm.afficherFilm(film);
		this.navigateur.naviguerVersVueFilm();
		
		/// TEST ///
		List<Film> listeFilmsTest = filmDAO.listerFilm();
		
		this.vueListeFilm.afficherListeFilms(this.filmDAO.listerFilm());
		this.navigateur.naviguerVersVueListeFilm();
		
		//this.navigateur.naviguerVersVueAjouterFilm();
		
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
		
 		vueEditerFilm.afficherListeActeur(listeActeurs);
	}
	
	public void notifierAjouterFilm() {
		System.out.println("ControleurFilm.notifierNaviguerAjouterFilm()");
		this.navigateur.naviguerVersVueAjouterFilm();
	}
	
	public void notifierNaviguerEditerFilm(int idFilm){
		System.out.println("ControleurFilm.notifierEditerFilm(" + idFilm + ")");
		
		this.vueEditerFilm.afficherFilm(this.filmDAO.rapporterFilm(idFilm));
		this.navigateur.naviguerVersVueEditerFilm();
	}
	
	public void notifierEnregistrerFilm() {
		System.out.println("ControleurFilm.notifierEnregistrerFilm()");
		
		Film film = this.navigateur.getVueEditerFilm().demanderFilm();
		this.filmDAO.modifierFilm(film);
		this.vueListeFilm.afficherListeFilms(this.filmDAO.listerFilm()); // TODO optimiser
		this.navigateur.naviguerVersVueListeFilm();
	}
	
	public void notifierEnregistrerNouveauFilm() {
		System.out.println("ControleurFilm.notifierEnregistrerNouveauFilm()");
		
		Film film = this.navigateur.getVueAjouterFilm().demanderFilm();
		this.filmDAO.ajouterFilm(film);
		this.vueListeFilm.afficherListeFilms(this.filmDAO.listerFilm());
		this.navigateur.naviguerVersVueListeFilm();
	}
}
