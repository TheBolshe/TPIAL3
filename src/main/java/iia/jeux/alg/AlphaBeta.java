/**
 *
 */

package iia.jeux.alg;

import java.util.ArrayList;

import iia.jeux.modele.CoupJeu;
import iia.jeux.modele.PlateauJeu;
import iia.jeux.modele.joueur.Joueur;

public class AlphaBeta implements AlgoJeu {

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
    public AlphaBeta(Heuristique h, Joueur joueurMax, Joueur joueurMin) {
        this(h,joueurMax,joueurMin,PROFMAXDEFAUT);
    }

    public AlphaBeta(Heuristique h, Joueur joueurMax, Joueur joueurMin, int profMaxi) {
        this.h = h;
        this.joueurMin = joueurMin;
        this.joueurMax = joueurMax;
        profMax = profMaxi;
//		System.out.println("Initialisation d'un AlphaBeta de profondeur " + profMax);
    }

   // -------------------------------------------
  // Méthodes de l'interface AlgoJeu
  // -------------------------------------------
  /**
  *   Methode du choix du meilleur coup possible pour le joueur AMI sur un plateau donne avec calcul d'heuristique grace a AlphaBeta
  *   @param p le plateau de jeu, noeud de l'arbre dont on veut choisir le meilleur coup parmi les noeud-enfants
  *   @return Le meilleur coup parmi la liste des coups possibles a partir de l'etat p pour le joueur AMI
  */
  public CoupJeu meilleurCoup(PlateauJeu p) {

    /* A vous de compléter le corps de ce fichier */

    // (c,s) <- debut(coupsPossibles) On initialse les tableau de coups et des scores correspondants
    ArrayList<CoupJeu> coups_possibles = p.lesCoupsPossibles(p.joueurMax);
    ArrayList<Integer> scores = new ArrayList<Integer>;

    // On initialise par le premier coup par defaut.
    CoupJeu meilleur_coup = coups_possibles.get(0);

    // Ca va etre la valeur heuristique correspondante au meilleur coup.
    // C'est aussi ce qui va nous permettre de comparer pour effectivement savoir si un copu est mieux qu'un autre
    int max = this.minMax(meilleur_coup, 0, Integer.MAX_VALUE, Integer.MIN_VALUE);

    for (CoupJeu c : coups_possibles) {
      PlateauDominos nouvelle_copie = p.copy();
      nouvelle_copie.joue(this.joueurMax, c);
      int nouvelle_valeur_max = this.minMax(nouvelle_copie, 0,  Integer.MAX_VALUE, Integer.MIN_VALUE);
      scores.add(nouvelle_valeur_max);
      if (nouvelle_valeur_max > max) {
        meilleur_coup = c;
        max = nouvelle_valeur_max;
      }
    }

    // Affichage des informations importantes

    System.out.println("Liste des coups possibles");
    for (CoupJeu c: coups_possibles) {
      System.out.println(c.toString());
    }
    System.out.println("Le meilleur coup est : " + meilleur_coup);
    System.out.println("Nombre de noeuds parcourus : " + this.nbnoeuds);
    System.out.println("Nombre de noeuds decouvertes : " + this.nbfeuilles);

    return meilleur_coup;
   }

   // -------------------------------------------
   // Méthodes publiques
   // -------------------------------------------
   public String toString() {
     return "AlphaBeta(ProfMax="+profMax+")";
   }



  // -------------------------------------------
  // Méthodes internes
  // -------------------------------------------

    //A vous de jouer pour implanter AlphaBeta

    /**
    *   Indique si un plateau correspond a une fin de Partie
    *   @param p le plateau qu'on evalue
    *   @return Le plateau correspond a une situation de fin de partie ou pas
    **/
    public boolean endGame(PlateauJeu p){
      p.finDePartie();
    }

    // AlphaBeta est une succession de deux fonctions : maxMin et minMax.

    /**
    *   Evaluation de la valeur AlphaBeta du noeud AMI
    *   @param p le plateau correspondant au noeud
    *   @return Un entier, la valeur max trouvee.
    **/
    public int maxMin(PlateauJeu p, int profondeur, int alpha, int beta){
      if (profondeur >= this.profMax || p.finDePartie() == true) {
        // Astuce pour compter le nombre de feuilles
        this.nbfeuilles++;
        return this.h.eval(p, this.joueurMax);
      }
      else {
        // Astuce pour compter le nombre de noeuds
        this.nbnoeuds++;
        ArrayList<CoupJeu> coups_possibles = p.lesCoupsPossibles(p.joueurMax);
        for (CoupJeu c : coups_possibles) {
          PlateauDominos new_copy = p.copy();
          new_copy.joue(this.joueurMax,c);
          alpha = Math.max(alpha, minMax(new_copy, profondeur++, alpha, beta));
          if (alpha >= beta) {
            return beta;
          }
        }
      }
      return alpha;
    }

    /**
    *   Evaluation de la valeur AlphaBeta du noeud ennemi
    *   @param p le plateau correspondant au noeud
    *   @return Un entier, la valeur max trouvee.
    **/
    public int minMax(PlateauJeu p, int profondeur, int alpha, int beta){
      // A SUPPRIMER SI ON CHANGE LA LIGNE 177 ET 178
      // int min = 0;
      // L'ennemi est en fin de partie (plateau = feuille; joueur = ennemi)
      if (profondeur >= this.profMax || p.finDePartie() == true) {
        // Astuce pour compter le nombre de feuilles
        this.nbfeuilles++;
        // DEMANDER AU PROF S'IL N'Y A PAS UNE ERREUR DANS LE POLY !!!! Et remplacer par la ligne suivante
        // return this.h.eval(p. this.joueurMin);
        min = this.h.eval(p. this.joueurMin);
      }
      // On simule le coup de ennemi, il doit faire son meilleur coup
      else {
        // Astuce pour compter le nombre de noeuds parcourus
        this.nbnoeuds++;
        min = Integer.MIN_VALUE;
        ArrayList<CoupJeu> coups_possibles = p.lesCoupsPossibles(p.joueurMin);
        for (CoupJeu c: coups_possibles) {
          PlateauDominos new_copy = p.copy();
          new_copy.joue(this.joueurMin,c);
          beta = Math.min(beta, maxMin(new_copy, profondeur++, alpha, beta));
          if (alpha >= beta) {
            return alpha;
          }
          return beta;
        }
      }
    }

}
