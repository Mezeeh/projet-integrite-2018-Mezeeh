package vue;

import javafx.application.Application;
import javafx.stage.Stage;

public class NavigateurDesVues extends Application{

	private VueAjouterFilm vueAjouterFilm;
	private VueListeFilm vueListeFilm;
	private VueFilm vueFilm;
	
	public NavigateurDesVues() 
	{
		this.vueAjouterFilm = new VueAjouterFilm();
		this.vueListeFilm = new VueListeFilm();
		this.vueFilm = new VueFilm();
	}
	
	@Override
	public void start(Stage stade) throws Exception {
		stade.setScene(this.vueFilm);
		stade.show();
	}
}
