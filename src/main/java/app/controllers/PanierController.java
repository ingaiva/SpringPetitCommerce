package app.controllers;

import java.util.HashMap;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import app.models.Client;
import app.models.Produit;
import app.repositories.ProduitRepository;

//@SessionAttributes({ "connectedCli", "panier"  })
//@SessionAttributes({  "panier"  })
@Controller
public class PanierController {
	@Autowired
	ProduitRepository prodR;

	@GetMapping(value = "/panier")
	public String getViewPanier(Model model,HttpSession session) {
		HashMap<Produit, Integer> panier=(HashMap<Produit, Integer>) session.getAttribute("panier");	
		if (panier == null) {
			panier = new HashMap<Produit, Integer>();
		}
		model.addAttribute("panier", panier);
		Client connectedCli=(Client) session.getAttribute("connectedCli");		
		model.addAttribute("connectedCli", connectedCli);
		return "viewPanier";
	}
			
	
	@GetMapping(value = "/ajoutPanier")
	public String ajoutPanier(Model model, 
			@RequestParam(name = "idProduit")Long curIdProduit, HttpSession session) {
		
		
		HashMap<Produit, Integer> panier=(HashMap<Produit, Integer>) session.getAttribute("panier");		
		
		if (panier == null)
			panier = new HashMap<Produit, Integer>();
		Integer qte = 1;
		
		if (curIdProduit != null) {
			Produit curProduit = prodR.getOne(curIdProduit);
			if (curProduit != null) {
				Integer stock = curProduit.getStock() - qte;

				if (stock <= 0) {
					System.out.println("Le stock de produit " + curProduit.getLibelle() + " est insuffisant");
					model.addAttribute("msgStock", "Le stock insuffisant");
				} else {
					//curProduit.setStock(stock);
					boolean isExist = false;
					for (Produit elt : panier.keySet()) {
						if (elt.getId_produit().equals(curIdProduit)) {
							isExist = true;
							Integer previousQte = panier.get(elt);
							panier.put(elt, previousQte + qte);
						}
					}

					if (!isExist)
						panier.put(curProduit, qte);

					session.setAttribute("panier",panier);						
				}
			}
		}
		return "redirect:/accueil";
	}

	@GetMapping(value = "/supprPanier")
	public String supprPanier(
			@RequestParam(name = "idProduit") Long curIdProduit, HttpSession session, RedirectAttributes ra) {

		HashMap<Produit, Integer> panier=(HashMap<Produit, Integer>) session.getAttribute("panier");		
		
		Integer qte = -1;

		if (panier!=null && curIdProduit != null) {
			Produit curProduit = prodR.getOne(curIdProduit);
			if (curProduit != null) {
								
				Integer stock = curProduit.getStock() - qte;
				//curProduit.setStock(stock);

				for (Produit elt : panier.keySet()) {
										
					if (elt.getId_produit().equals(curIdProduit)) {
					
						Integer previousQte = panier.get(elt);
						if (previousQte + qte <= 0) {
							panier.remove(elt);							
							break;
						} else {							
							panier.put(elt, previousQte + qte);}
					}
				}
				
				session.setAttribute("panier",panier);	
				ra.addFlashAttribute("panier", panier);				
				//curProduit.setStock(stock);
			}
		}
		return "redirect:/panier";
	}
	
}
