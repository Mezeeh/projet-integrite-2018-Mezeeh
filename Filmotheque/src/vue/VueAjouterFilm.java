package vue;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import modele.Film;

public class VueAjouterFilm extends Application{
	
	protected TextField valeurTitre,
						valeurDescription,
						valeurGenre,
						valeurDateDeSortie,
						valeurDuree;
	
	protected VBox panneau;
	
	protected GridPane grilleFilm;

	@Override
	public void start(Stage stade) throws Exception {
		panneau = new VBox();
		grilleFilm = new GridPane();
		
		valeurTitre = new TextField();
		grilleFilm.add(new Label("Titre : "), 0, 0);
		grilleFilm.add(valeurTitre, 1, 0);
		
		valeurDescription = new TextField("");
		grilleFilm.add(new Label("Description : "), 0, 1);
		grilleFilm.add(valeurDescription, 1, 1);
		
		valeurGenre = new TextField("");
		grilleFilm.add(new Label("Genre : "), 0, 2);
		grilleFilm.add(valeurGenre, 1, 2);	
		
		valeurDateDeSortie = new TextField("");
		grilleFilm.add(new Label("Date de sortie : "), 0, 3);
		grilleFilm.add(valeurDateDeSortie, 1, 3);		
		
		valeurDuree = new TextField("");
		grilleFilm.add(new Label("Duree : "), 0, 4);
		grilleFilm.add(valeurDuree, 1, 4);	
			
		// Todo : retirer les textes magiques
		panneau.getChildren().add(new Label("Ajouter un mouton")); // Todo : créer un sous-type de Label ou Text pour les titres
		panneau.getChildren().add(grilleFilm);
		panneau.getChildren().add(new Button("Enregistrer"));
		stade.setScene(new Scene(panneau, 400, 400));
		stade.show();
	}

	
	public Film demanderFilm() {
		return null;
	}
}
