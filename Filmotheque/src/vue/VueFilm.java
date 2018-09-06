package vue;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import modele.Film;

public class VueFilm extends Application{
	
	protected Pane panneau;
	
	protected GridPane grilleFilm;
	
	protected Label valeurTitre,
					valeurDescription,
					valeurGenre,
					valeurDateDeSortie,
					valeurDuree;

	@Override
	public void start(Stage stade) throws Exception {
		panneau = new Pane();
		grilleFilm = new GridPane();
		
		valeurTitre = new Label("Die Hard");
		grilleFilm.add(new Label("Titre : "), 0, 0);
		grilleFilm.add(valeurTitre, 1, 0);
		
		valeurDescription = new Label("Un policier new-yorkais, John McClane, est séparé de sa femme Holly, cadre dans une puissante multinationale japonaise, la Nakatomi Corporation. Venu à Los Angeles passer les fêtes avec elle, il se rend à la tour Nakatomi où le patron donne une grande soirée. Tandis que John s'isole pour téléphoner, un groupe de terroristes allemands, dirigé par Hans Gruber, pénètre dans l'immeuble.");
		grilleFilm.add(new Label("Description : "), 0, 1);
		grilleFilm.add(valeurDescription, 1, 1);
		
		valeurGenre = new Label("énigme/Thriller");
		grilleFilm.add(new Label("Genre : "), 0, 2);
		grilleFilm.add(valeurGenre, 1, 2);
		
		valeurDateDeSortie = new Label("1988");
		grilleFilm.add(new Label("Date de sortie : "), 0, 3);
		grilleFilm.add(valeurDateDeSortie, 1, 3);
		
		valeurDuree = new Label("2h 12m");
		grilleFilm.add(new Label("Duree : "), 0, 4);
		grilleFilm.add(valeurDuree, 1, 4);
		
		panneau.getChildren().add(grilleFilm);
		
		stade.setScene(new Scene(panneau, 400, 400));
		
		stade.show();
		
		
		Film film = new Film("Die Hard",
				"Un policier new-yorkais, John McClane, est séparé de sa femme Holly, cadre dans une puissante multinationale japonaise, la Nakatomi Corporation. Venu à Los Angeles passer les fêtes avec elle, il se rend à la tour Nakatomi où le patron donne une grande soirée. Tandis que John s'isole pour téléphoner, un groupe de terroristes allemands, dirigé par Hans Gruber, pénètre dans l'immeuble.",
				"énigme/Thriller",
				"1988",
				"2h 12m");
		this.afficherFilm(film);
	}

	public void afficherFilm(Film film) {
		
	}
}
