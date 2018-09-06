package vue;

import javafx.application.Application;
import javafx.stage.Stage;

public class NavigateurDesVues extends Application{

	private VueAjouterFilm vueAjouterFilm;
	
	public NavigateurDesVues() 
	{
		this.vueAjouterFilm = new VueAjouterFilm();
	}
	
	@Override
	public void start(Stage stade) throws Exception {
		stade.setScene(this.vueAjouterFilm);
		stade.show();
	}
}
