package vue;

import action.ControleurFilm;
import javafx.application.Application;
import javafx.stage.Stage;

public class NavigateurDesVues extends Application{
	
	private Stage stade;

	private VueAjouterFilm vueAjouterFilm;
	private VueListeFilm vueListeFilm;
	private VueFilm vueFilm;
	
	private ControleurFilm controleur;
	
	public NavigateurDesVues() 
	{		
		this.vueAjouterFilm = new VueAjouterFilm();
		this.vueListeFilm = new VueListeFilm();
		this.vueFilm = new VueFilm();
	}
	
	@Override
	public void start(Stage stade) throws Exception {
		this.stade = stade;
		stade.setScene(null);
		stade.show();
		
		this.controleur = ControleurFilm.getInstance();
		this.controleur.activerVues(this);
		
		this.vueAjouterFilm.setControleur(controleur);
		this.vueListeFilm.setControleur(controleur);
		this.vueFilm.setControleur(controleur);
	}

	public VueAjouterFilm getVueAjouterFilm() {
		return vueAjouterFilm;
	}

	public VueListeFilm getVueListeFilm() {
		return vueListeFilm;
	}

	public VueFilm getVueFilm() {
		return vueFilm;
	}

	public void naviguerVersVueAjouterFilm() {
		stade.setScene(this.vueAjouterFilm);
		stade.show();
	}

	public void naviguerVersVueFilm() {
		stade.setScene(this.vueFilm);
		stade.show();
	}

	public void naviguerVersVueListeFilm() {
		stade.setScene(this.vueListeFilm);
		stade.show();
	}
}
