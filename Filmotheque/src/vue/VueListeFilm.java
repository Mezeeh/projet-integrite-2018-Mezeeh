package vue;

import java.util.List;

import action.ControleurFilm;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import modele.Film;

public class VueListeFilm extends Scene{
	
	protected GridPane grilleFilms;
	private ControleurFilm controleur;
	
	private Button actionNaviguerAjouterFilm;

	public VueListeFilm() {
		super(new GridPane(), 400, 400);
		grilleFilms = (GridPane) this.getRoot();
		
		actionNaviguerAjouterFilm = new Button("Ajouter un film");
	}

	public void afficherListeFilms(List<Film> listeFilmsTest) {
		this.grilleFilms.getChildren().clear();
		
		int numero = 0;
		
		this.grilleFilms.add(new Label("Titre : "), 0, numero);
		this.grilleFilms.add(new Label("Description : "), 1, numero);
		this.grilleFilms.add(new Label("Genre : "), 2, numero);
		this.grilleFilms.add(new Label("Date de sortie : "), 3, numero);
		this.grilleFilms.add(new Label("Duree : "), 4, numero);
		
		for(Film film : listeFilmsTest) {
			Button actionEditerFilm = new Button("Editer");
			actionEditerFilm.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent arg0) {
					controleur.notifierNaviguerEditerFilm(film.getId());
				}});
			
			numero++;
			this.grilleFilms.add(new Label(film.getTitre()), 0, numero);
			this.grilleFilms.add(new Label(film.getDescription()), 1, numero);
			this.grilleFilms.add(new Label(film.getGenre()), 2, numero);
			this.grilleFilms.add(new Label(film.getDateDeSortie()), 3, numero);
			this.grilleFilms.add(new Label(film.getDuree()), 4, numero);
			this.grilleFilms.add(actionEditerFilm, 5, numero);
		}
		
		this.actionNaviguerAjouterFilm.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				controleur.notifierAjouterFilm();
			}
		});
		
		this.grilleFilms.add(this.actionNaviguerAjouterFilm, 1, ++numero);
	}

	public void setControleur(ControleurFilm controleur) {
		this.controleur = controleur;
	}
}
