package controleur;

import vue.NavigateurDesVues;

public class ControleurFilm {

	private NavigateurDesVues navigateur;
	
	public ControleurFilm(NavigateurDesVues navigateur)
	{
		this.navigateur = navigateur;
		System.out.println("Initialisation du controleur");
	}
}
