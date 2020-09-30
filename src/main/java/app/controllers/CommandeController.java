package app.controllers;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import app.models.Client;
import app.models.Commande;
import app.models.Produit;
import app.repositories.CommandeRepository;
import app.repositories.ProduitRepository;

@Controller
//@SessionAttributes({ "connectedCli", "cmdRequest","panier" })
//@SessionAttributes({  "cmdRequest" })
public class CommandeController {

	@Autowired
	CommandeRepository cmdR;

	@Autowired
	ProduitRepository prodR;

	@GetMapping({ "/cmdList" })
	public String afficherCmdList(Model model, HttpSession session) {

		Client connectedCli = (Client) session.getAttribute("connectedCli");
		if (connectedCli != null) {		
			
			connectedCli.setCommandes(cmdR.getCommandesClient(connectedCli.getId()));	
					
		}
		model.addAttribute("connectedCli", connectedCli);
		return "viewCmd";
	}

	@PostMapping({ "/creerCmd" })
	@GetMapping({ "/creerCmd" })
	public String ajouterCmd(Model model, HttpSession session) {
		HashMap<Produit, Integer> panier = (HashMap<Produit, Integer>) session.getAttribute("panier");
		Client connectedCli = (Client) session.getAttribute("connectedCli");
		
		if (connectedCli != null && panier != null && panier.size() > 0) {
			for (Produit produit : panier.keySet()) {
				Integer qte = panier.get(produit);
				if (qte > 0) {					
					Commande newCmd = new Commande();
					newCmd.setClient(connectedCli);
					newCmd.setProduit(produit.getLibelle());
					newCmd.setNombre(qte);
					newCmd.setPrix((float) (produit.getPrix() * qte));
					newCmd.setDate(new Date());					

					Produit prodInBdd = prodR.getOne(produit.getId_produit());
					if (prodInBdd != null) {
						cmdR.save(newCmd);
						prodInBdd.setStock(prodInBdd.getStock() - qte);
						prodR.save(prodInBdd);
						
					}
				}
			}
			
			panier.clear();
			session.setAttribute("panier", panier);
			session.removeAttribute("cmdRequest");
			//model.addAttribute("connectedCli", connectedCli);
			//model.addAttribute("panier", panier);
			return "redirect:/cmdList";

		} else {
			if ( panier != null && panier.size() > 0) {
				session.setAttribute("cmdRequest", "1");
				return "redirect:/login";
			}
			else
				return "redirect:/panier";
		}		
	}

}
