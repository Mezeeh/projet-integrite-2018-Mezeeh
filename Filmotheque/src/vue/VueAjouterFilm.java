package vue;

import action.ControleurFilm;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import modele.Film;

public class VueAjouterFilm extends Scene{
	
	protected TextField valeurTitre,
						valeurDescription,
						valeurGenre,
						valeurDateDeSortie,
						valeurDuree;
	
	protected VBox panneau;
	
	protected GridPane grilleFilm;

	private ControleurFilm controleur;
	
	protected Button actionEnregistrerFilm;

	public VueAjouterFilm() {
		super(new VBox(), 400, 400);
		
		panneau = (VBox) this.getRoot();
		grilleFilm = new GridPane();
		
		actionEnregistrerFilm = new Button("Enregistrer");
		actionEnregistrerFilm.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				controleur.notifierEnregistrerNouveauFilm();
			}
		});
		
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
		panneau.getChildren().add(new Label("Ajouter un film")); // Todo : créer un sous-type de Label ou Text pour les titres
		panneau.getChildren().add(grilleFilm);
		panneau.getChildren().add(actionEnregistrerFilm);
	}

	
	public Film demanderFilm() {
		Film film = new Film(this.valeurTitre.getText(), 
				this.valeurDescription.getText(), 
				this.valeurGenre.getText(), 
				this.valeurDateDeSortie.getText(), 
				Float.parseFloat(this.valeurDuree.getText()));
		
		return film;
	}
	
	public void setControleur(ControleurFilm controleur) {
		this.controleur = controleur;
	}
}
