package maud;


public class Medicament {
private String nomMolecule;			// le nom de la molécule chimique active
private String nomMarque;			// le nom de la marque déposée
private String [] usages;			// pour quels traitements
private double [] dosesPossibles;	// les formats possibles d'une dose
private String unite;				// l'unité d'une dose (mg, g, etc.)
private String [] interactions;		// les molécules avec lesquelles peut interagir ce médicament

public Medicament() {
	this.nomMolecule = "";
	this.nomMarque = "";
	this.dosesPossibles = new double[8];
	this.unite = "";
}

/**
 * @return the usages
 */
public String [] getUsages() {
	return usages;
}
/**
 * @param usages the usages to set
 */
public void setUsages(String [] usages) {
	this.usages = usages;
}
/**
 * @return the nomMarque
 */
public String getNomMarque() {
	return nomMarque;
}
/**
 * @param nomMarque the nomMarque to set
 */
public void setNomMarque(String nomMarque) {
	this.nomMarque = nomMarque;
}
/**
 * @return the nomMolecule
 */
public String getNomMolecule() {
	return nomMolecule;
}
/**
 * @param nomMolecule the nomMolecule to set
 */
public void setNomMolecule(String nomMolecule) {
	this.nomMolecule = nomMolecule;
}

/**
 * @return the dosesPossibles
 */
public double [] getDosesPossibles() {
	return dosesPossibles;
}
/**
 * @param dosesPossibles the dosesPossibles to set
 */
public void setDosesPossibles(double [] dosesPossibles) {
	this.dosesPossibles = dosesPossibles;
}
/**
 * @return the unite
 */
public String getUnite() {
	return unite;
}
/**
 * @param unite the unite to set
 */
public void setUnite(String unite) {
	this.unite = unite;
}
/**
 * @return the interactions
 */
public String [] getInteractions() {
	return interactions;
}
/**
 * @param interactions the interactions to set
 */
public void setInteractions(String [] interactions) {
	this.interactions = interactions;
}

public int[] trouverCombinaisonDoses(double doseTotale){
		int[] combinaison = new int[8];
		int nbDoses = 0;
		for (int i = this.dosesPossibles.length-1; i >= 0 ; i--)
			if (doseTotale >= this.dosesPossibles[i]) {
				combinaison[nbDoses] = (int) (doseTotale / dosesPossibles[i]);
				doseTotale -= combinaison[nbDoses] * dosesPossibles[i];
				nbDoses++;
			}
		if (doseTotale > 0) // s'il reste une quantite dans la dose qui n'a pas
							// pu etre distribuee
			combinaison = null;
		return combinaison;
	}
}
