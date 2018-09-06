package vue;

import java.util.ArrayList;

import java.util.List;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import modele.Film;

public class VueListeFilm extends Scene{
	
	protected Pane panneau;
	
	protected GridPane grilleFilms;

	public VueListeFilm() {
		super(new Pane(), 400, 400);
		panneau = (Pane) this.getRoot();	
		grilleFilms = new GridPane();
		
		panneau.getChildren().add(grilleFilms);
	}

	public void afficherListeFilms(List<Film> listeFilmsTest) {
		int numero = 0;
		
		this.grilleFilms.add(new Label("Titre : "), 0, numero);
		this.grilleFilms.add(new Label("Description : "), 1, numero);
		this.grilleFilms.add(new Label("Genre : "), 2, numero);
		this.grilleFilms.add(new Label("Date de sortie : "), 3, numero);
		this.grilleFilms.add(new Label("Duree : "), 4, numero);
		
		for(Film film : listeFilmsTest) {
			numero++;
			this.grilleFilms.add(new Label(film.getTitre()), 0, numero);
			this.grilleFilms.add(new Label(film.getDescription()), 1, numero);
			this.grilleFilms.add(new Label(film.getGenre()), 2, numero);
			this.grilleFilms.add(new Label(film.getDateDeSortie()), 3, numero);
			this.grilleFilms.add(new Label(film.getDuree()), 4, numero);
		}
	}

}
