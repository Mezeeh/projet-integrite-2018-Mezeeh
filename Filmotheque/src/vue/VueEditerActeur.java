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

public class VueEditerActeur extends Scene{
	protected TextField valeurNom,
						valeurNaissance,
						valeurTaille,
						valeurNationalite,
						valeurDuree;
	
	protected int idActeur,
					idFilm;
	
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
				controleur.notifierEnregistrerActeur();
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
		grilleActeur.add(new Label("Nationalite : "), 0, 3);
		grilleActeur.add(valeurNationalite, 1, 3);
		
		// Todo : retirer les textes magiques
		panneau.getChildren().add(new Label("Editer un acteur")); // Todo : créer un sous-type de Label ou Text pour les titres
		panneau.getChildren().add(this.grilleActeur);
		panneau.getChildren().add(this.actionEnregistrerActeur);
	}
	
	public void afficherActeur(Acteur acteur) {
		this.idActeur = acteur.getId();
		this.valeurNom.setText(acteur.getNom());
		this.valeurNaissance.setText(acteur.getNaissance());
		this.valeurTaille.setText(Integer.toString(acteur.getTaille()));
		this.valeurNationalite.setText(acteur.getNationalite());
		this.idFilm = acteur.getIdFilm();
	}
	
	public Acteur demanderActeur(){
		Acteur acteur = new Acteur(this.valeurNom.getText(), 
						this.valeurNaissance.getText(), 
						Integer.parseInt(this.valeurTaille.getText()), 
						this.valeurNationalite.getText());
		acteur.setId(idActeur);
		acteur.setIdFilm(idFilm);
		
		return acteur;
	}
	
	public void setControleur(ControleurFilm controleur) {
		this.controleur = controleur;
	}
}
