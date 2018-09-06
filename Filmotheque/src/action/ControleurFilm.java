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
	
	public ControleurFilm()
	{
		instance = null;
		vueAjouterFilm = null;
		vueListeFilm = null;
		vueFilm = null;
		
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
				"Un policier new-yorkais, John McClane, est séparé de sa femme Holly, cadre dans une puissante multinationale japonaise, la Nakatomi Corporation. Venu à Los Angeles passer les fêtes avec elle, il se rend à la tour Nakatomi où le patron donne une grande soirée. Tandis que John s'isole pour téléphoner, un groupe de terroristes allemands, dirigé par Hans Gruber, pénètre dans l'immeuble.",
				"énigme/Thriller",
				"1988",
				"2h 12m");
		this.vueFilm.afficherFilm(film);
		this.navigateur.naviguerVersVueFilm();
		
		/// TEST ///
		FilmDAO filmDAO = new FilmDAO();
		List<Film> listeFilmsTest = filmDAO.listerFilm();
		
		this.vueListeFilm.afficherListeFilms(listeFilmsTest);
		this.navigateur.naviguerVersVueListeFilm();
		
		//this.navigateur.naviguerVersVueAjouterFilm();
	}
}
