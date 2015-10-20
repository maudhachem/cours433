package maud;



import java.util.Iterator;
import java.util.Scanner;

public class Menu {


	public static void traiterChoix(int choix, Scanner scan, Pharmacie petitePharma) {

			int combinaisons[] = null;
			Medicament itMedic = null;
			boolean trouve = false;
			System.out.println("Entrez le nom du médicament ou de la molécule:");
			String medicamentOuMolecule = scan.next();
			for (Iterator<Medicament> it = petitePharma.getLesMedicaments().iterator();it.hasNext() && !trouve;) {
				itMedic = it.next();
				if (itMedic.getNomMarque().equals(medicamentOuMolecule)
						|| itMedic.getNomMolecule().equals(medicamentOuMolecule)) {
					System.out.println("Entrez la quantité en : " + itMedic.getUnite());
					double doseVoulue = scan.nextDouble();
					combinaisons = itMedic.trouverCombinaisonDoses(doseVoulue);
					trouve = true;
				}
			}
			if (trouve){
				System.out.println("Il faut donner: ");
				for(int i = 0; i < combinaisons.length; i++)
					if (combinaisons[i] > 0)
						System.out.println(combinaisons[i] + " pillule(s) de " 
								+ itMedic.getDosesPossibles()[i] + " " + itMedic.getUnite());
			}
	}
}
