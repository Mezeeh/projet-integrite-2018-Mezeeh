package vue;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class VueFilm extends Application{

	@Override
	public void start(Stage stade) throws Exception {
		Pane panneau = new Pane();
		GridPane grilleFilm = new GridPane();
		
		Label valeurTitre = new Label("Die Hard");
		grilleFilm.add(new Label("Titre : "), 0, 0);
		grilleFilm.add(valeurTitre, 1, 0);
		
		Label valeurDescription = new Label("Un policier new-yorkais, John McClane, est s�par� de sa femme Holly, cadre dans une puissante multinationale japonaise, la Nakatomi Corporation. Venu � Los Angeles passer les f�tes avec elle, il se rend � la tour Nakatomi o� le patron donne une grande soir�e. Tandis que John s'isole pour t�l�phoner, un groupe de terroristes allemands, dirig� par Hans Gruber, p�n�tre dans l'immeuble.");
		grilleFilm.add(new Label("Description : "), 0, 1);
		grilleFilm.add(valeurDescription, 1, 1);
		
		Label valeurGenre = new Label("�nigme/Thriller");
		grilleFilm.add(new Label("Genre : "), 0, 2);
		grilleFilm.add(valeurGenre, 1, 2);
		
		Label valeurDateDeSortie = new Label("1988");
		grilleFilm.add(new Label("Date de sortie : "), 0, 3);
		grilleFilm.add(valeurDateDeSortie, 1, 3);
		
		Label valeurDuree = new Label("2h 12m");
		grilleFilm.add(new Label("Duree : "), 0, 4);
		grilleFilm.add(valeurDuree, 1, 4);
		
		panneau.getChildren().add(grilleFilm);
		
		stade.setScene(new Scene(panneau, 400, 400));
		
		stade.show();
	}

}
