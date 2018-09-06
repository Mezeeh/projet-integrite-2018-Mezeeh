package action;

import java.util.List;

import accesseur.FilmDAO;
import modele.Film;
import vue.NavigateurDesVues;
import vue.VueAjouterFilm;
import vue.VueFilm;
import vue.VueListeFilm;

public class ControleurFilm {
	
	private static ControleurFilm instance;

	private NavigateurDesVues navigateur;
	
	private VueAjouterFilm vueAjouterFilm;
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
		this.vueListeFilm = navigateur.getVueListeFilm();
		this.vueFilm = navigateur.getVueFilm();
		
		//// TEST ////
		Film film = new Film("Die Hard",
				"Un policier new-yorkais, John McClane, est s�par� de sa femme Holly, cadre dans une puissante multinationale japonaise, la Nakatomi Corporation. Venu � Los Angeles passer les f�tes avec elle, il se rend � la tour Nakatomi o� le patron donne une grande soir�e. Tandis que John s'isole pour t�l�phoner, un groupe de terroristes allemands, dirig� par Hans Gruber, p�n�tre dans l'immeuble.",
				"�nigme/Thriller",
				"1988",
				"2h 12m");
		this.vueFilm.afficherFilm(film);
		this.navigateur.naviguerVersVueFilm();
		
		/// TEST ///
		List<Film> listeFilmsTest = filmDAO.listerFilm();
		
		this.vueListeFilm.afficherListeFilms(listeFilmsTest);
		this.navigateur.naviguerVersVueListeFilm();
		
		this.navigateur.naviguerVersVueAjouterFilm();
	}
	
	public void notifierEnregistrerFilm() {
		System.out.println("ControleurFilm.notifierEnregistrerFilm()");
		
		Film film = this.navigateur.getVueAjouterFilm().demanderFilm();
		this.filmDAO.ajouterFilm(film);
		this.navigateur.naviguerVersVueListeFilm();
	}
}
