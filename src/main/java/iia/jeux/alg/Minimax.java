/**
 *
 */

package iia.jeux.alg;

import java.util.ArrayList;

import iia.jeux.modele.CoupJeu;
import iia.jeux.modele.PlateauJeu;
import iia.jeux.modele.joueur.Joueur;

public class Minimax implements AlgoJeu {

    /** La profondeur de recherche par défaut
     */
    private final static int PROFMAXDEFAUT = 4;


    // -------------------------------------------
    // Attributs
    // -------------------------------------------

    /**  La profondeur de recherche utilisée pour l'algorithme
     */
    private int profMax = PROFMAXDEFAUT;

     /**  L'heuristique utilisée par l'algorithme
      */

      private Heuristique h;
    /** Le joueur Min
     *  (l'adversaire) */
    private Joueur joueurMin;

    /** Le joueur Max
     * (celui dont l'algorithme de recherche adopte le point de vue) */
    private Joueur joueurMax;

    /**  Le nombre de noeuds développé par l'algorithme
     * (intéressant pour se faire une idée du nombre de noeuds développés) */
       private int nbnoeuds;

    /** Le nombre de feuilles évaluées par l'algorithme
     */
    private int nbfeuilles;


  // -------------------------------------------
  // Constructeurs
  // -------------------------------------------
    public Minimax(Heuristique h, Joueur joueurMax, Joueur joueurMin) {
        this(h,joueurMax,joueurMin,PROFMAXDEFAUT);
    }

    public Minimax(Heuristique h, Joueur joueurMax, Joueur joueurMin, int profMaxi) {
        this.h = h;
        this.joueurMin = joueurMin;
        this.joueurMax = joueurMax;
        profMax = profMaxi;
//		System.out.println("Initialisation d'un MiniMax de profondeur " + profMax);
    }

   // -------------------------------------------
  // Méthodes de l'interface AlgoJeu
  // -------------------------------------------
   public CoupJeu meilleurCoup(PlateauJeu p) {
        /* A vous de compléter le corps de ce fichier */
        return null;

    }
  // -------------------------------------------
  // Méthodes publiques
  // -------------------------------------------
    public String toString() {
        return "MiniMax(ProfMax="+profMax+")";
    }



  // -------------------------------------------
  // Méthodes internes
  // -------------------------------------------

    //A vous de jouer pour implanter Minimax

    /**
    *   Indique si un plateau correspond a une fin de Partie
    *   @param p le plateau qu'on evalue
    *   @return Le plateau correspond a une situation de fin de partie ou pas
    **/
    public boolean endGame(PlateauJeu p){
      p.finDePartie();
    }

    // Minimax est une succession de deux fonctions.
    /**
    *   Evaluation de la valeur MiniMax du noeud ami
    *   @param p le plateau correspondant au noeud
    *   @return Un entier, la valeur max trouvee.
    **/
    public int maxMin(PlateauJeu p, int profondeur){
      // Astuce pour compter le nombre de noeuds
      this.nbnoeuds++;
      if (profondeur >= this.profMax || p.finDePartie() == true) {
        // Astuce pour compter le nombre de feuilles
        this.nbfeuilles++;
        return this.h.eval(p, this.joueurMax);
      }
      else {
        int max = String.MIN_VALUE;
        ArrayList<CoupJeu> coupsPossibles = p.lesCoupsPossibles(p.joueurMax);
        for (CoupJeu c : coupsPossibles) {
          PlateauDominos new_copy = p.copy();
          new_copy.joue(this.joueurMax,c);
          max = Math.max(max, minMax(new_copy));
        }
        return max;
      }
    }

    /**
    *   Evaluation de la valeur MiniMax du noeud ennemi
    *   @param p le plateau correspondant au noeud
    *   @return Un entier, la valeur max trouvee.
    **/
    public int minMax(PlateauJeu p, int profondeur){
      // Astuce pour compter le nombre de noeuds parcourus
      this.nbnoeuds++;
      int min = 0;
      // L'ennemi est en fin de partie (plateau = feuille; joueur = ennemi)
      if (profondeur >= this.profMax || p.finDePartie() == true) {
        // Astuce pour compter le nombre de feuilles
        this.nbfeuilles++;
        min = this.h.eval(p. this.joueurMin);
      }
      // On simule le coup de ennemi, il doit faire
      else {
        ArrayList<CoupJeu> coupsPossibles = p.lesCoupsPossibles(p.joueurMin);
        for (CoupJeu c: coupsPossibles) {
          PlateauDominos new_copy = p.copy();
          new_copy.joue(this.joueurMin,c);
          min = Math.min(min, maxMin(new_copy, profondeur));
        }
        return min
      }
    }

}
