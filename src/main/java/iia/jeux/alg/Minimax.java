/**
 *
 */

package iia.jeux.alg;

import java.util.ArrayList;
import java.util.Random;

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

    Random rand = new Random();


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
        //Initialisation du comptage de noeuds/feilles pour l'exploration
        nbnoeuds = 0;
        nbfeuilles = 0;
        //récuperation des coups possible pour le joueur ami
        ArrayList<CoupJeu> coupsPossibles = p.coupsPossibles(joueurMax);
        int max = Integer.MIN_VALUE;
        ArrayList<CoupJeu> listeMeilleursCoups = new ArrayList<CoupJeu>();
        for (CoupJeu c : coupsPossibles) {
            PlateauJeu s = p.copy();
            s.joue(joueurMax, c);
            int newVal = minMax(s, 1);
            //System.out.println("max = " + max + " vs " + newVal);
            if (newVal > max) {
                listeMeilleursCoups.clear();
                listeMeilleursCoups.add(c);
                max = newVal;
            } else if (newVal == max) {
                listeMeilleursCoups.add(c);
            }
        }
        //System.out.println(listeMeilleursCoups.toString());
        CoupJeu meilleurCoup = listeMeilleursCoups.get(rand.nextInt(listeMeilleursCoups.size()));

        System.out.println("Le meilleur coup est : " + meilleurCoup);
        System.out.println("Nombre de noeuds parcourus : " + this.nbnoeuds);
        System.out.println("Nombre de noeuds decouvertes : " + this.nbfeuilles);
        return meilleurCoup;
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

    // Minimax est une succession de deux fonctions : maxMin et minMax.

    /**
     *   Evaluation de la valeur MiniMax du noeud ami
     *   @param p le plateau correspondant au noeud
     *   @return Un entier, la valeur max trouvee.
     **/
    private int maxMin(PlateauJeu p, int prof) {
        //System.out.print(prof + " ");
        //System.out.println(p.finDePartie() + " " + (prof >= profMax) + " " + etatFinal(p, prof));
        if (p.finDePartie() || prof >= profMax) {
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
        if (p.finDePartie() || prof >= profMax) {
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
