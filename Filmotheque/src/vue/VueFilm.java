package vue;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import modele.Film;

public class VueFilm extends Scene{
	
	protected Pane panneau;
	
	protected GridPane grilleFilm;
	
	protected Label valeurTitre,
					valeurDescription,
					valeurGenre,
					valeurDateDeSortie,
					valeurDuree;

	public VueFilm() {
		super(new Pane(), 400, 400);
		panneau = (Pane) this.getRoot();
		grilleFilm = new GridPane();
		
		valeurTitre = new Label();
		grilleFilm.add(new Label("Titre : "), 0, 0);
		grilleFilm.add(valeurTitre, 1, 0);
		
		valeurDescription = new Label();
		grilleFilm.add(new Label("Description : "), 0, 1);
		grilleFilm.add(valeurDescription, 1, 1);
		
		valeurGenre = new Label();
		grilleFilm.add(new Label("Genre : "), 0, 2);
		grilleFilm.add(valeurGenre, 1, 2);
		
		valeurDateDeSortie = new Label();
		grilleFilm.add(new Label("Date de sortie : "), 0, 3);
		grilleFilm.add(valeurDateDeSortie, 1, 3);
		
		valeurDuree = new Label();
		grilleFilm.add(new Label("Duree : "), 0, 4);
		grilleFilm.add(valeurDuree, 1, 4);
		
		panneau.getChildren().add(grilleFilm);
	}

	public void afficherFilm(Film film) {
		this.valeurTitre.setText(film.getTitre());
		this.valeurDescription.setText(film.getDescription());
		this.valeurGenre.setText(film.getGenre());
		this.valeurDateDeSortie.setText(film.getDateDeSortie());
		this.valeurDuree.setText(film.getDuree());
	}
}
