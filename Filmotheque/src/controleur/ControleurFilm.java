package controleur;

import java.util.ArrayList;
import java.util.List;

import com.sun.glass.ui.TouchInputSupport;

import modele.Film;
import vue.NavigateurDesVues;
import vue.VueAjouterFilm;
import vue.VueFilm;
import vue.VueListeFilm;

public class ControleurFilm {
	
	private static ControleurFilm instance;

	private NavigateurDesVues navigateur;
	
	private VueAjouterFilm vueAjouterFilm;
	private VueListeFilm vueListeFilm;
	private VueFilm vueFilm;
	
	public ControleurFilm()
	{
		instance = null;
		vueAjouterFilm = null;
		vueListeFilm = null;
		vueFilm = null;
		
		System.out.println("Initialisation du controleur");
	}
	
	public static ControleurFilm getInstance() {
		if(null == instance)
			instance = new ControleurFilm();
		
		return instance;
	}
	
	public void activerVues(NavigateurDesVues navigateur) {
		this.navigateur = navigateur;
		this.vueAjouterFilm = navigateur.getVueAjouterFilm();
		this.vueListeFilm = navigateur.getVueListeFilm();
		this.vueFilm = navigateur.getVueFilm();
		
		//// TEST ////
		Film film = new Film("Die Hard",
				"Un policier new-yorkais, John McClane, est séparé de sa femme Holly, cadre dans une puissante multinationale japonaise, la Nakatomi Corporation. Venu à Los Angeles passer les fêtes avec elle, il se rend à la tour Nakatomi où le patron donne une grande soirée. Tandis que John s'isole pour téléphoner, un groupe de terroristes allemands, dirigé par Hans Gruber, pénètre dans l'immeuble.",
				"énigme/Thriller",
				"1988",
				"2h 12m");
		this.vueFilm.afficherFilm(film);
		this.navigateur.naviguerVersVueFilm();
		
		/// TEST ///
		List<Film> listeFilmsTest = new ArrayList<Film>();
		listeFilmsTest.add(new Film("Die Hard",
				"Un policier new-yorkais, John McClane, est séparé de sa femme Holly, cadre dans une puissante multinationale japonaise, la Nakatomi Corporation. Venu à Los Angeles passer les fêtes avec elle, il se rend à la tour Nakatomi où le patron donne une grande soirée. Tandis que John s'isole pour téléphoner, un groupe de terroristes allemands, dirigé par Hans Gruber, pénètre dans l'immeuble.",
				"énigme/Thriller",
				"1988",
				"2h 12m"));
		listeFilmsTest.add(new Film("Rambo",
				"Revenu du Viêtnam, abruti autant par les mauvais traitements que lui ont jadis infligés ses tortionnaires que par l'indifférence de ses concitoyens, le soldat Rambo, un ancien des commandos d'élite, traîne sa redoutable carcasse de ville en ville. Un shérif teigneux lui interdit l'accès de sa bourgade. Rambo insiste. Il veut seulement manger. Le shérif le met sous les verrous et laisse son adjoint brutaliser ce divertissant clochard.",
				"Drame/Thriller",
				"1982",
				"1h 33m"));
		listeFilmsTest.add(new Film("Rocky",
				"Rocky Balboa travaille pour Tony Gazzo, un usurier, et dispute de temps à autre des combats de boxe pour quelques dizaines de dollars sous l'appellation de l'Étalon Italien. Cependant, Mickey, propriétaire du club de boxe où Rocky a l'habitude de s'entraîner, décide de céder son casier à un boxeur plus talentueux.",
				"Drame/Sport",
				"1976",
				"2h 2m"));
		listeFilmsTest.add(new Film("Le Seigneur des anneaux : La Communauté de l'anneau",
				"Un jeune et timide `Hobbit', Frodon Sacquet, hérite d'un anneau magique. Bien loin d'être une simple babiole, il s'agit d'un instrument de pouvoir absolu qui permettrait à Sauron, le `Seigneur des ténèbres', de régner sur la `Terre du Milieu' et de réduire en esclavage ses peuples. Frodon doit parvenir jusqu'à la `Crevasse du Destin' pour détruire l'anneau.",
				"fantasy/Action",
				"2001",
				"3h 48m"));
		this.vueListeFilm.afficherListeFilms(listeFilmsTest);
		this.navigateur.naviguerVersVueListeFilm();
		
		this.navigateur.naviguerVersVueAjouterFilm();
	}
}
