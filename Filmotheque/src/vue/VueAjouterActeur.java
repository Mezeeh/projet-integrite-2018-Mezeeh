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
import modele.Acteur;
import modele.Film;

public class VueAjouterActeur extends Scene{
	
	protected TextField valeurNom,
						valeurNaissance,
						valeurTaille,
						valeurNationalite;
	
	protected VBox panneau;
	
	protected GridPane grilleActeur;

	private ControleurFilm controleur;
	
	protected Button actionEnregistrerActeur;

	public VueAjouterActeur() {
		super(new VBox(), 400, 400);
		
		panneau = (VBox) this.getRoot();
		grilleActeur = new GridPane();
		
		actionEnregistrerActeur = new Button("Enregistrer");
		actionEnregistrerActeur.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				controleur.notifierEnregistrerNouveauFilm();
			}
		});
		
		valeurNom = new TextField();
		grilleActeur.add(new Label("Nom : "), 0, 0);
		grilleActeur.add(valeurNom, 1, 0);
		
		valeurNaissance = new TextField("");
		grilleActeur.add(new Label("Naissance : "), 0, 1);
		grilleActeur.add(valeurNaissance, 1, 1);
		
		valeurTaille = new TextField("");
		grilleActeur.add(new Label("Taille : "), 0, 2);
		grilleActeur.add(valeurTaille, 1, 2);	
		
		valeurNationalite = new TextField("");
		grilleActeur.add(new Label("Nationalité : "), 0, 3);
		grilleActeur.add(valeurNationalite, 1, 3);	
			
		// Todo : retirer les textes magiques
		panneau.getChildren().add(new Label("Ajouter un acteur")); // Todo : créer un sous-type de Label ou Text pour les titres
		panneau.getChildren().add(grilleActeur);
		panneau.getChildren().add(actionEnregistrerActeur);
	}

	
	public Acteur demanderActeur() {
		Acteur acteur = new Acteur(this.valeurNom.getText(), 
				this.valeurNaissance.getText(), 
				Float.parseFloat(this.valeurTaille.getText()), 
				this.valeurNationalite.getText());
		
		return acteur;
	}
	
	public void setControleur(ControleurFilm controleur) {
		this.controleur = controleur;
	}
}
