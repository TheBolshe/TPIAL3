package jeux.dominos;

import iia.jeux.alg.Heuristique;
import iia.jeux.modele.PlateauJeu;
import iia.jeux.modele.joueur.Joueur;


public class HeuristiquesDominos{

	public static Heuristique hblanc = new Heuristique(){

		public int eval(PlateauJeu p, Joueur j){
			if (p.isJoueurBlanc(j)) {
				if (p.nbCoupsBlanc() == 0) {
					return Integer.MIN_VALUE;
				}
				if (p.nbCoupsNoir() == 0) {
					return Integer.MAX_VALUE;
				}
				return ((PlateauDominos) p).nbCoupsBlanc() - ((PlateauDominos) p).nbCoupsNoir();
			}
			else {
				if (p.nbCoupsNoir() == 0) {
					return MAX_VALUE;
				}
				if (p.nbCoupsBlanc() == 0) {
					return MIN_VALUE;
				}
				return ((PlateauDominos) p).nbCoupsBlanc() - ((PlateauDominos) p).nbCoupsNoir();
			}
		}
	};

	public static  Heuristique hnoir = new Heuristique(){

		public int eval(PlateauJeu p, Joueur j){
			if (p.isJoueurNoir(j)) {
				if (p.nbCoupsBlanc() == 0) {
					return Integer.MAX_VALUE;
				}
				if (p.nbCoupsNoir() == 0) {
					return Integer.MIN_VALUE;
				}
				return ((PlateauDominos) p).nbCoupsNoir() - ((PlateauDominos) p).nbCoupsBlanc();
			}
			else {
				if (p.nbCoupsNoir() == 0) {
					return MIN_VALUE;
				}
				if (p.nbCoupsBlanc() == 0) {
					return MAX_VALUE;
				}
				return ((PlateauDominos) p).nbCoupsNoir() - ((PlateauDominos) p).nbCoupsBlanc();
			}
	}
};

}
