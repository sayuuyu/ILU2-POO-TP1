package villagegaulois;

import personnages.Chef;
import personnages.Gaulois;

public class Village {
	private String nom;
	private Chef chef;
	private Gaulois[] villageois;
	private int nbVillageois = 0;
	private Marche marche;

	public Village(String nom, int nbVillageoisMaximum, int nbEtals) {
		this.nom = nom;
		this.marche=new Marche(nbEtals);
		villageois = new Gaulois[nbVillageoisMaximum];
	}

	public String getNom() {
		return nom;
	}

	public void setChef(Chef chef) {
		this.chef = chef;
	}

	public void ajouterHabitant(Gaulois gaulois) {
		if (nbVillageois < villageois.length) {
			villageois[nbVillageois] = gaulois;
			nbVillageois++;
		}
	}

	public Gaulois trouverHabitant(String nomGaulois) {
		if (nomGaulois.equals(chef.getNom())) {
			return chef;
		}
		for (int i = 0; i < nbVillageois; i++) {
			Gaulois gaulois = villageois[i];
			if (gaulois.getNom().equals(nomGaulois)) {
				return gaulois;
			}
		}
		return null;
	}

	public String afficherVillageois() {
		StringBuilder chaine = new StringBuilder();
		if (nbVillageois < 1) {
			chaine.append("Il n'y a encore aucun habitant au village du chef "
					+ chef.getNom() + ".\n");
		} else {
			chaine.append("Au village du chef " + chef.getNom()
					+ " vivent les lÃ©gendaires gaulois :\n");
			for (int i = 0; i < nbVillageois; i++) {
				chaine.append("- " + villageois[i].getNom() + "\n");
			}
		}
		return chaine.toString();
	}
	
	public String installerVendeur(Gaulois vendeur, String produit,int nbProduit) {
		StringBuilder resultat = new StringBuilder(vendeur.getNom());
		resultat.append(" cherche un endroit pour vendre ").append(nbProduit).append(" produit.\n");
		int iEtalInstall=this.marche.trouverEtalLibre();
		if( iEtalInstall != -1) {
			this.marche.utiliserEtal(iEtalInstall, vendeur, produit, nbProduit);
		}
		resultat.append("Le vendeur ").append(vendeur.getNom()).append(" vend des ").append(produit).append(" à l'étal n°").append(iEtalInstall).append(".\n");
		return resultat.toString();
	}
	
	 public String rechercherVendeursProduit(String produit) {
		 
	 }
	
	private static class Marche{
		private Etal[] etals;
		
		private Marche(int nbEtals) {
			this.etals= new Etal[nbEtals];
			for(int i = 0;i<nbEtals;i++) {
				this.etals[i]=new Etal();
			}
		}
		
		private void utiliserEtal(int indiceEtal, Gaulois vendeur, String produit, int nbProduit) {
			this.etals[indiceEtal].occuperEtal(vendeur, produit, nbProduit);
		}
		
		private int trouverEtalLibre() {
			for(int i = 0;i<this.etals.length;i++)
				if(!etals[i].isEtalOccupe())
					return i+1;
			return -1;
		}
		
		private Etal[] trouverEtals(String produit) {
			int nbEtalProduit=0;
			for(int i = 0;i<this.etals.length;i++) {
				if(this.etals[i].contientProduit(produit))nbEtalProduit++;
			}
			Etal[] etalProduit=new Etal[nbEtalProduit];
			nbEtalProduit=0;
			for(int i = 0;i<this.etals.length;i++) {
				if(this.etals[i].contientProduit(produit)) {
					etalProduit[nbEtalProduit]=this.etals[i];
					nbEtalProduit++;
				}
			}
			return etalProduit;
		}
		
		private Etal trouverVendeur(Gaulois gaulois) {
			for(int i = 0;i<this.etals.length;i++) {
				if(this.etals[i].getVendeur().getNom().equals(gaulois.getNom())) {
					return this.etals[i];
				}
			}
			return null;
		}
		
		private String afficherMarche() {
			StringBuilder resultat=new StringBuilder();
			int nbEtalsLibres=0;
			for(int i = 0;i<this.etals.length;i++) {
				if(this.etals[i].afficherEtal().equals("L'Ã©tal est libre"))
					nbEtalsLibres++;
				else {
					resultat.append(this.etals[i].afficherEtal());
				}
			}
			if(nbEtalsLibres>0) {
				resultat.append("Il reste ").append(nbEtalsLibres).append("non utilisés dans le marché.\n");
				}
			return resultat.toString();
		}
	}
}