package vue;

import java.util.ArrayList;
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

public class VueEditerFilm extends Scene{
	protected TextField valeurTitre,
						valeurDescription,
						valeurGenre,
						valeurDateDeSortie,
						valeurDuree;
	protected int idFilm;
	
	private VBox panneau;
	
	private GridPane grilleFilm,
					 grilleListeActeurs;
	
	private ControleurFilm controleur;
	
	protected Button actionEnregistrerFilm,
					 actionAjouterActeur,
					 actionModifierActeur,
					 actionSupprimerActeur;
	
	public VueEditerFilm()  {
		super(new VBox(), 400, 400);
		
		panneau = (VBox) this.getRoot();
		grilleFilm = new GridPane();
		grilleListeActeurs = new GridPane();
		
		this.actionAjouterActeur = new Button("Ajouter un acteur");
		this.actionAjouterActeur.setOnAction(new EventHandler<ActionEvent>() {	
			@Override
			public void handle(ActionEvent event) {
				controleur.notifierAjouterActeur();
			}
		});
		
		this.actionEnregistrerFilm = new Button("Enregistrer");
		this.actionEnregistrerFilm.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				controleur.notifierEnregistrerFilm();
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
		panneau.getChildren().add(new Label("Editer un film")); // Todo : créer un sous-type de Label ou Text pour les titres
		panneau.getChildren().add(this.grilleFilm);
		panneau.getChildren().add(this.actionEnregistrerFilm);
		panneau.getChildren().add(this.grilleListeActeurs);
		panneau.getChildren().add(this.actionAjouterActeur);
	}
	
	public void afficherFilm(Film film) {
		this.idFilm = film.getId();
		this.valeurTitre.setText(film.getTitre());
		this.valeurDescription.setText(film.getDescription());
		this.valeurGenre.setText(film.getGenre());
		this.valeurDateDeSortie.setText(film.getDateDeSortie());
		this.valeurDuree.setText(film.getDuree());
	}
	
	public void afficherListeActeur(List<Acteur> listeActeurs) {
		grilleListeActeurs.getChildren().clear();
		int item = 0;
		for(Acteur acteur : listeActeurs){
			actionModifierActeur = new Button("Éditer");
			actionModifierActeur.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					System.out.println("vueEditerFilm.afficherListeActeur() actionModifierActeur");
				}
			});
			
			actionSupprimerActeur = new Button("Effacer");
			actionSupprimerActeur.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					System.out.println("vueEditerFilm.afficherListeActeur() actionSupprimerActeur");
				}
			});
			
			this.grilleListeActeurs.add(new Label(acteur.getNom()), 0, item);
			this.grilleListeActeurs.add(new Label(acteur.getNationalite()), 1, item);
			this.grilleListeActeurs.add(actionModifierActeur, 2, item);
			this.grilleListeActeurs.add(actionSupprimerActeur, 3, item);
			item++;
		}
	}
	
	public Film demanderFilm()
	{
		Film film = new Film(this.valeurTitre.getText(), 
							this.valeurDescription.getText(), 
							this.valeurGenre.getText(), 
							this.valeurDateDeSortie.getText(), 
							this.valeurDuree.getText());
		film.setId(idFilm);
		
		return film;
	}
	
	public void setControleur(ControleurFilm controleur) {
		this.controleur = controleur;
	}
}
