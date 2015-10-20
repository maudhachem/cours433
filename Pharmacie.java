package maud;



import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Pharmacie {
	private List<Client> lesClients;
	private List<Medicament> lesMedicaments;

	public Pharmacie() {
		this.lesMedicaments = new ArrayList<>();
		this.lesClients = new ArrayList<>();
	}

	/**
	 * @return the lesClients
	 */
	public List<Client> getLesClients() {
		return lesClients;
	}

	/**
	 * @param lesClients
	 *            the lesClients to set
	 */
	public void setLesClients(List<Client> lesClients) {
		this.lesClients = lesClients;
	}

	/**
	 * @return the lesMedicaments
	 */
	public List<Medicament> getLesMedicaments() {
		return lesMedicaments;
	}

	/**
	 * @param lesMedicaments
	 *            the lesMedicaments to set
	 */
	public void setLesMedicaments(List<Medicament> lesMedicaments) {
		this.lesMedicaments = lesMedicaments;
	}

	public void lireClients() {
		try {
			File monFichier = new File("Clients.txt");
			FileReader lecture = new FileReader(monFichier);
			BufferedReader reader = new BufferedReader(lecture);

			String ligne = null;
			while ((ligne = reader.readLine()) != null) {
				String infos[] = ligne.split(" ");
				String nom = infos[0];
				String prenom = infos[1];
				String NAM = infos[2];
				Client nouveauClient = new Client(NAM, nom, prenom);
				lesClients.add(nouveauClient);
			}
			reader.close();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	public void lireMedicaments() {
		try {
			File monFichier = new File("Medicaments.txt");
			FileReader lecture = new FileReader(monFichier);
			BufferedReader reader = new BufferedReader(lecture);

			String ligne = null;
			while ((ligne = reader.readLine()) != null) {
				Medicament medicament = new Medicament();
				// sur la 1re ligne du fichier on trouvait le nom de la marque
				medicament.setNomMarque(ligne);
				// sur la 2e ligne du fichier on trouvait le nom de la molecule
				ligne = reader.readLine();
				medicament.setNomMolecule(ligne);
				// sur la 3e ligne du fichier on trouvait les usages séparés par des point-virgules
				ligne = reader.readLine();
				medicament.setUsages(ligne.split(";"));
				// sur la 4e ligne du fichier on trouvait les doses séparés par des point-virgules
				ligne = reader.readLine();
				String[] dosesLues = ligne.split(";");
				for (int j = 0; j<dosesLues.length; j++)
					medicament.getDosesPossibles()[j] = Double.parseDouble(dosesLues[j]);
				// sur la 5e ligne du fichier on le format de la dose
				ligne = reader.readLine();
				medicament.setUnite(ligne);
				// sur la 6e ligne du fichier on trouvait les molecules qui causeraient des interactions
				ligne = reader.readLine();
				medicament.setInteractions(ligne.split(";"));
				lesMedicaments.add(medicament);
				
			}
			reader.close();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	public void lirePrescriptions() {
		try {
			File monFichier = new File("prescriptions.txt");
			FileReader lecture = new FileReader(monFichier);
			BufferedReader reader = new BufferedReader(lecture);

			String ligne = null;
			while ((ligne = reader.readLine()) != null) {
				String infos[] = ligne.split(" ");
				String client = infos[0];
				String medicament = infos[1];
				double dose = Double.parseDouble(infos[2]);
				int renouvellements = Integer.parseInt(infos[3]);
				for (Iterator<Client> it = lesClients.iterator(); it.hasNext();) {
					Client itClient = it.next();
					if (itClient.getNAM().equalsIgnoreCase(client)) {
						Prescription nouvellePrescription = new Prescription(
								medicament, dose, renouvellements);
						itClient.getPrescriptions().add(nouvellePrescription);
					}
				}
			}
			reader.close();
		} catch (IOException ex) {
			ex.printStackTrace();
		}

	}
	
	public boolean siClientExiste(String NAM){
		for (Iterator<Client> it = lesClients.iterator(); it.hasNext();) {
			Client itClient = it.next();
			if (itClient.getNAM().equals(NAM)) {
				return true;
			}
		}
		return false;
	}
	
	public void ajouterClient(String NAM, String nom, String prenom){
		this.lesClients.add(new Client(NAM, nom, prenom));
	}
	

	public List<Prescription> getPrescriptionsClient(String NAM) {
		for (Iterator<Client> it = lesClients.iterator(); it.hasNext();) {
			Client itClient = it.next();
			if (itClient.getNAM().equals(NAM)) {
				return itClient.getPrescriptions();
			}
		}
		return null;
	}

	public boolean servirPrescription(String NAM, String medicament) {
		boolean delivree = false;
		for (Iterator<Client> it = lesClients.iterator(); it.hasNext();) {
			Client itClient = it.next();
			if (itClient.getNAM().equals(NAM)) {
				for (Iterator<Prescription> it2 = itClient.getPrescriptions()
						.iterator(); it2.hasNext();) {
					Prescription courante = it2.next();
					if (courante.getMedicamentAPrendre().equalsIgnoreCase(medicament))
						if (courante.getRenouvellements() >=1 ){
							courante.setRenouvellements(courante.getRenouvellements() -1);
							delivree = true;
					}
				}
			}
		}
		return delivree;
	}

	public boolean trouverInteraction(String medicament1, String medicament2) {
		for (Iterator<Medicament> it = lesMedicaments.iterator(); it.hasNext();) {
			Medicament courant = it.next();
			if (courant.getNomMolecule().equalsIgnoreCase(medicament1))
			for(int i = 0; i < courant.getInteractions().length; i++)
				if (courant.getInteractions()[i].equalsIgnoreCase(medicament2))
					return true;
			if (courant.getNomMolecule().equalsIgnoreCase(medicament2))
				for(int i = 0; i < courant.getInteractions().length; i++)
					if (courant.getInteractions()[i].equalsIgnoreCase(medicament1))
						return true;			
		}
		return false;
	}

	public void ecrirePrescriptions() {
		String newLine = System.getProperty("line.separator");
		try {
			FileWriter writer = new FileWriter("Prescriptions.txt");

			for (Iterator<Client> it = lesClients.iterator(); it.hasNext();) {
				Client itClient = it.next();

				for (Iterator<Prescription> it2 = itClient.getPrescriptions()
						.iterator(); it2.hasNext();) {
					Prescription courante = it2.next();
					String texte = "";
					texte += itClient.getNAM() + " " + courante.getMedicamentAPrendre()
							+ " " + courante.getDose() + " " + courante.getRenouvellements();
					texte += newLine;
					writer.write(texte);
				}
			}

			writer.close();

		} catch (IOException ex) {
			ex.printStackTrace();
		}

	}
}
