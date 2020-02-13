package jeux.dominos;

import iia.jeux.alg.Heuristique;
import iia.jeux.modele.PlateauJeu;
import iia.jeux.modele.joueur.Joueur;


public class HeuristiquesDominos{

	public static Heuristique hblanc = new Heuristique(){

		public int eval(PlateauJeu op, Joueur j){
			PlateauDominos p = ((PlateauDominos) op);
			if (p.isJoueurBlanc(j)) {
				if (p.nbCoupsBlanc() == 0) {
					return Integer.MIN_VALUE;
				}
				if (p.nbCoupsNoir() == 0) {
					return Integer.MAX_VALUE;
				}
				return p.nbCoupsBlanc() - p.nbCoupsNoir();
			}
			else {
				if (p.nbCoupsNoir() == 0) {
					return Integer.MAX_VALUE;
				}
				if (p.nbCoupsBlanc() == 0) {
					return Integer.MIN_VALUE;
				}
				return p.nbCoupsBlanc() - p.nbCoupsNoir();
			}
		}
	};

	public static  Heuristique hnoir = new Heuristique(){

		public int eval(PlateauJeu op, Joueur j){
			PlateauDominos p = ((PlateauDominos) op);
			if (p.isJoueurNoir(j)) {
				if (p.nbCoupsBlanc() == 0) {
					return Integer.MAX_VALUE;
				}
				if (p.nbCoupsNoir() == 0) {
					return Integer.MIN_VALUE;
				}
				return p.nbCoupsNoir() - p.nbCoupsBlanc();
			}
			else {
				if (p.nbCoupsNoir() == 0) {
					return Integer.MIN_VALUE;
				}
				if (p.nbCoupsBlanc() == 0) {
					return Integer.MAX_VALUE;
				}
				return p.nbCoupsNoir() - p.nbCoupsBlanc();
			}
	}
};

}
