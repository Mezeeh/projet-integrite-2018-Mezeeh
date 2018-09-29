package vue;

import java.util.List;

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

public class VueEditerActeur extends Scene{
	protected TextField valeurNom,
						valeurNaissance,
						valeurTaille,
						valeurNationalite,
						valeurDuree;
	protected int idActeur;
	
	private VBox panneau;
	
	private GridPane grilleActeur;
	
	private ControleurFilm controleur;
	
	protected Button actionEnregistrerActeur;
	
	public VueEditerActeur()  {
		super(new VBox(), 400, 400);
		
		panneau = (VBox) this.getRoot();
		grilleActeur = new GridPane();
	
		this.actionEnregistrerActeur = new Button("Enregistrer");
		this.actionEnregistrerActeur.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
			controleur.notifierEnregistrerFilm();
			}
		});
	
		valeurNom = new TextField();
		grilleActeur.add(new Label("Titre : "), 0, 0);
		grilleActeur.add(valeurNom, 1, 0);
		
		valeurNaissance = new TextField("");
		grilleActeur.add(new Label("Description : "), 0, 1);
		grilleActeur.add(valeurNaissance, 1, 1);
		
		valeurTaille = new TextField("");
		grilleActeur.add(new Label("Genre : "), 0, 2);
		grilleActeur.add(valeurTaille, 1, 2);	
		
		valeurNationalite = new TextField("");
		grilleActeur.add(new Label("Date de sortie : "), 0, 3);
		grilleActeur.add(valeurNationalite, 1, 3);		
		
		valeurDuree = new TextField("");
		grilleActeur.add(new Label("Duree : "), 0, 4);
		grilleActeur.add(valeurDuree, 1, 4);
		
		// Todo : retirer les textes magiques
		panneau.getChildren().add(new Label("Editer un acteur")); // Todo : créer un sous-type de Label ou Text pour les titres
		panneau.getChildren().add(this.grilleActeur);
		panneau.getChildren().add(this.actionEnregistrerActeur);
	}
	
	public void afficherActeur(Acteur acteur) {
		this.idActeur = acteur.getId();
		this.valeurNom.setText(acteur.getNom());
		this.valeurNaissance.setText(acteur.getNaissance());
		this.valeurTaille.setText(Float.toString(acteur.getTaille()));
		this.valeurNationalite.setText(acteur.getNationalite());
	}
	
	public Acteur demanderActeur(){
		Acteur acteur = new Acteur(this.valeurNom.getText(), 
						this.valeurNaissance.getText(), 
						Float.parseFloat(this.valeurTaille.getText()), 
						this.valeurNationalite.getText());
		acteur.setId(idActeur);
		
		return acteur;
	}
	
	public void setControleur(ControleurFilm controleur) {
		this.controleur = controleur;
	}
}
