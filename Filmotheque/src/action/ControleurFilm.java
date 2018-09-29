package action;

import java.util.ArrayList;
import java.util.List;

import accesseur.ActeurDAO;
import accesseur.FilmDAO;
import modele.Acteur;
import modele.Film;
import vue.NavigateurDesVues;
import vue.VueAjouterActeur;
import vue.VueAjouterFilm;
import vue.VueEditerActeur;
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
	
	private VueAjouterActeur vueAjouterActeur;
	private VueEditerActeur vueEditerActeur;
	
	private FilmDAO filmDAO;
	private ActeurDAO acteurDAO;
	
	public ControleurFilm()
	{
		instance = null;
		vueAjouterFilm = null;
		vueListeFilm = null;
		vueFilm = null;
		
		filmDAO = new FilmDAO();
		acteurDAO = new ActeurDAO();
		
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
		
		this.vueAjouterActeur = navigateur.getVueAjouterActeur();
		this.vueEditerActeur = navigateur.getVueEditerActeur();
		
		this.vueListeFilm.afficherListeFilms(this.filmDAO.listerFilm());
		this.navigateur.naviguerVersVueListeFilm();
		
		//this.navigateur.naviguerVersVueAjouterFilm();
	}
	
	public void notifierAjouterFilm() {
		System.out.println("ControleurFilm.notifierNaviguerAjouterFilm()");
		this.navigateur.naviguerVersVueAjouterFilm();
	}
	
	public void notifierAjouterActeur(int idFilm) {
		System.out.println("ControleurFilm.notifierNaviguerAjouterActeur()");
		this.vueAjouterActeur.setIdFilm(idFilm);
		this.navigateur.naviguerVersVueAjouterActeur();
	}
	
	public void notifierNaviguerEditerFilm(int idFilm){
		System.out.println("ControleurFilm.notifierEditerFilm(" + idFilm + ")");
		
		this.vueEditerFilm.afficherFilm(this.filmDAO.rapporterFilm(idFilm));
		this.vueEditerFilm.afficherListeActeur(this.acteurDAO.listerActeursParFilm(idFilm));
		this.navigateur.naviguerVersVueEditerFilm();
	}
	
	public void notifierNaviguerEditerActeur(int idActeur, int idFilm){
		System.out.println("ControleurFilm.notifierEditerActeur(" + idActeur + ")");
		
		this.vueEditerActeur.afficherActeur(this.acteurDAO.rapporterActeur(idActeur));
		//this.vueEditerActeur.afficherListeActeur(this.acteurDAO.listerActeursParFilm(idFilm));
		this.navigateur.naviguerVersVueEditerActeur();
	}
	
	public void notifierNaviguerSupprimerFilm(int idFilm){
		System.out.println("ControleurFilm.notifierSupprimerFilm(" + idFilm + ")");
		
		this.filmDAO.supprimerFilm(idFilm);
		this.vueListeFilm.afficherListeFilms(this.filmDAO.listerFilm());
		this.navigateur.naviguerVersVueListeFilm();
	}
	
	public void notifierNaviguerSupprimerActeur(int idActeur, int idFilm){
		System.out.println("ControleurFilm.notifierSupprimerActeur(" + idActeur + ")");
		
		this.acteurDAO.supprimerActeur(idActeur);
		this.vueEditerFilm.afficherListeActeur(this.acteurDAO.listerActeursParFilm(idFilm));
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
	
	public void notifierEnregistrerNouveauActeur(int idFilm) {
		System.out.println("ControleurFilm.notifierEnregistrerNouveauActeur");
		
		Acteur acteur = this.navigateur.getVueAjouterActeur().demanderActeur();
		acteur.setIdFilm(idFilm);
		this.acteurDAO.ajouterActeur(acteur);
		this.vueEditerFilm.afficherListeActeur(this.acteurDAO.listerActeursParFilm(idFilm));
		this.navigateur.naviguerVersVueEditerFilm();
	}
}
