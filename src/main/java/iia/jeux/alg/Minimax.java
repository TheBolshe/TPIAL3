/**
 *
 */

package iia.jeux.alg;

import java.util.ArrayList;

import iia.jeux.modele.CoupJeu;
import iia.jeux.modele.PlateauJeu;
import iia.jeux.modele.joueur.Joueur;
import jeux.dominos.PlateauDominos;

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
        this(h, joueurMax, joueurMin, PROFMAXDEFAUT);
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

    /**
     *   Methode du choix du meilleur coup possible pour le joueur AMI sur un plateau donne avec calcul d'heuristique grace a MiniMax
     *   @param p le plateau de jeu, noeud de l'arbre dont on veut choisir le meilleur coup parmi les noeud-enfants
     *   @return Le meilleur coup parmi la liste des coups possibles a partir de l'etat p pour le joueur AMI
     */
    public CoupJeu meilleurCoup(PlateauJeu p) {
        nbnoeuds = 0;
        nbfeuilles = 0;
        ArrayList<CoupJeu> coupsPossibles = p.coupsPossibles(joueurMax);
        int max = Integer.MIN_VALUE;
        CoupJeu meilleurCoup = coupsPossibles.get(0);
        for (CoupJeu i : coupsPossibles) {
            PlateauJeu s = p.copy();
            s.joue(joueurMax, i);
            int newVal = minMax(s, 1);
            System.out.println("max = " + max + " vs " + newVal);
            if (newVal > max) {
                meilleurCoup = i;
                max = newVal;
            }
        }
        System.out.println("Le meilleur coup est : " + meilleurCoup);
        System.out.println("Nombre de noeuds parcourus : " + this.nbnoeuds);
        System.out.println("Nombre de noeuds decouvertes : " + this.nbfeuilles);
        return meilleurCoup;
    }

    public CoupJeu meilleurCoupOld(PlateauJeu p) {

        /* A vous de compléter le corps de ce fichier */
        nbfeuilles = 0;
        nbnoeuds = 0;
        // (c,s) <- debut(coupsPossibles) On initialse les tableau de coups et des scores correspondants
        ArrayList<CoupJeu> coups_possibles = p.coupsPossibles(joueurMax);

        // On initialise par le premier coup par defaut.
        CoupJeu meilleur_coup = null;

        // Ca va etre la valeur heuristique correspondante au meilleur coup.
        // C'est aussi ce qui va nous permettre de comparer pour effectivement savoir si un copu est mieux qu'un autre
        PlateauJeu s = p.copy();
        //s.joue(joueurMax, meilleur_coup);
        //int max = this.minMax(s, 0);
        int max = Integer.MIN_VALUE;

        for (CoupJeu c : coups_possibles) {
            s.joue(this.joueurMax, c);
            int nouvelle_valeur_max = this.minMax(s, 0);
            if (nouvelle_valeur_max > max) {
                meilleur_coup = c;
                max = nouvelle_valeur_max;
            }
        }

        // Affichage des informations importantes

        System.out.println("Liste des coups possibles");
        for (CoupJeu c : coups_possibles) {
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
        return "MiniMax(ProfMax=" + profMax + ")";
    }


    // -------------------------------------------
    // Méthodes internes
    // -------------------------------------------

    //A vous de jouer pour implanter Minimax

    private boolean etatFinal (PlateauJeu p, int prof) {
        return (p.finDePartie() || prof >= profMax);
    }

    // Minimax est une succession de deux fonctions : maxMin et minMax.

    /**
     *   Evaluation de la valeur MiniMax du noeud ami
     *   @param p le plateau correspondant au noeud
     *   @return Un entier, la valeur max trouvee.
     **/
    private int maxMin(PlateauJeu p, int prof) {
        //System.out.print(prof + " ");
        //System.out.println(p.finDePartie() + " " + (prof >= profMax) + " " + etatFinal(p, prof));
        if (etatFinal(p, prof)) {
            nbfeuilles++;
            int eval = h.eval(p, joueurMax);
            //System.out.println(eval);
            return eval;
        } else {
            nbnoeuds++;
            int max = Integer.MIN_VALUE;
            for (CoupJeu c : p.coupsPossibles(joueurMax)) {
                PlateauJeu s = p.copy();
                s.joue(joueurMax, c);
                max = Math.max(max, minMax(s, prof++));
            }
            //System.out.println(max);
            return max;
        }
    }

    /**
     *   Evaluation de la valeur MiniMax du noeud ennemi
     *   @param p le plateau correspondant au noeud
     *   @return Un entier, la valeur max trouvee.
     **/
    private int minMax(PlateauJeu p, int prof) {
        //System.out.print(prof + " ");
        //System.out.println(p.finDePartie() + " " + (prof >= profMax) + " " + etatFinal(p, prof));
        if (etatFinal(p, prof)) {
            nbfeuilles++;
            int eval = h.eval(p, joueurMin);
            //System.out.println(eval);
            return eval;
        } else {
            nbnoeuds++;
            int min = Integer.MAX_VALUE;
            for (CoupJeu c : p.coupsPossibles(joueurMin)) {
                PlateauJeu s = p.copy();
                s.joue(joueurMin, c);
                min = Math.min(min, maxMin(s, prof++));
            }
            //.out.println(min);
            return min;
        }
    }

}
