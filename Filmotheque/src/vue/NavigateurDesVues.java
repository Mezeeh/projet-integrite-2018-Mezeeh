package vue;

import javafx.application.Application;
import javafx.stage.Stage;

public class NavigateurDesVues extends Application{

	private VueAjouterFilm vueAjouterFilm;
	private VueListeFilm vueListeFilm;
	
	public NavigateurDesVues() 
	{
		this.vueAjouterFilm = new VueAjouterFilm();
		this.vueListeFilm = new VueListeFilm();
	}
	
	@Override
	public void start(Stage stade) throws Exception {
		stade.setScene(this.vueListeFilm);
		stade.show();
	}
}
