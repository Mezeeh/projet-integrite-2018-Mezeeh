package accesseur;

import java.util.ArrayList;
import java.util.List;

import modele.Film;

public class FilmDAO {
	
	private List<Film> simulerListerFilms()
	{
		List<Film> listeFilmsTest = new ArrayList<Film>();
		listeFilmsTest.add(new Film("Die Hard",
				"Un policier new-yorkais, John McClane, est s�par� de sa femme Holly, cadre dans une puissante multinationale japonaise, la Nakatomi Corporation. Venu � Los Angeles passer les f�tes avec elle, il se rend � la tour Nakatomi o� le patron donne une grande soir�e. Tandis que John s'isole pour t�l�phoner, un groupe de terroristes allemands, dirig� par Hans Gruber, p�n�tre dans l'immeuble.",
				"�nigme/Thriller",
				"1988",
				"2h 12m"));
		listeFilmsTest.add(new Film("Rambo",
				"Revenu du Vi�tnam, abruti autant par les mauvais traitements que lui ont jadis inflig�s ses tortionnaires que par l'indiff�rence de ses concitoyens, le soldat Rambo, un ancien des commandos d'�lite, tra�ne sa redoutable carcasse de ville en ville. Un sh�rif teigneux lui interdit l'acc�s de sa bourgade. Rambo insiste. Il veut seulement manger. Le sh�rif le met sous les verrous et laisse son adjoint brutaliser ce divertissant clochard.",
				"Drame/Thriller",
				"1982",
				"1h 33m"));
		listeFilmsTest.add(new Film("Rocky",
				"Rocky Balboa travaille pour Tony Gazzo, un usurier, et dispute de temps � autre des combats de boxe pour quelques dizaines de dollars sous l'appellation de l'�talon Italien. Cependant, Mickey, propri�taire du club de boxe o� Rocky a l'habitude de s'entra�ner, d�cide de c�der son casier � un boxeur plus talentueux.",
				"Drame/Sport",
				"1976",
				"2h 2m"));
		listeFilmsTest.add(new Film("Le Seigneur des anneaux : La Communaut� de l'anneau",
				"Un jeune et timide `Hobbit', Frodon Sacquet, h�rite d'un anneau magique. Bien loin d'�tre une simple babiole, il s'agit d'un instrument de pouvoir absolu qui permettrait � Sauron, le `Seigneur des t�n�bres', de r�gner sur la `Terre du Milieu' et de r�duire en esclavage ses peuples. Frodon doit parvenir jusqu'� la `Crevasse du Destin' pour d�truire l'anneau.",
				"fantasy/Action",
				"2001",
				"3h 48m"));
		
		return listeFilmsTest;
	}
	public List<Film> listerFilm()
	{
		return this.simulerListerFilms();
	}
}
