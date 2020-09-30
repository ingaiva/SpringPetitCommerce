package app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import app.models.Produit;
import app.repositories.ProduitRepository;

@SpringBootApplication
public class SpringClientCmdProductsApplication implements CommandLineRunner  {
	@Autowired
	private ProduitRepository products;
	
	public static void main(String[] args)  {
		SpringApplication.run(SpringClientCmdProductsApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		int cpt=products.findAll().size();
		if (cpt==0) {
			Produit pOrdi1 = new Produit("Ordinateur portable","HP 250",75,379F);
			products.save(pOrdi1);
			Produit pOrdi2 = new Produit("Ordinateur portable","Ordinateur portable tactile Pavilion x360 14-dh0037nf",100,999F);
			products.save(pOrdi2);
			Produit pOrdi3 = new Produit("Ordinateur portable","Aspire 3 a317 - 17,3``",50,429F);
			products.save(pOrdi3);
			Produit pSouris = new Produit("Souris sans fil","HP Souris sans fil Noir ",75,18F);
			products.save(pSouris);
			Produit pClavier = new Produit("Clavier sans fil","XTREME MAC - Clavier sans fil XWH-MAK-13 (bluetooth, WiFi)",75,71F);
			products.save(pClavier);
			Produit pSacoche = new Produit("Sacoche pour portable","Sacoche HUXTON pour portable 13,3``",75,22F);
			products.save(pSacoche);
			Produit pSourisEL = new Produit("Souris","Souris sans fil Noir édition limitée",3,22F);
			products.save(pSourisEL);
			
		}
	}

}
