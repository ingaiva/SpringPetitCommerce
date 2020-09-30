package app.controllers;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import app.models.Client;
import app.models.Produit;
import app.repositories.CommandeRepository;
import app.repositories.ProduitRepository;



@Controller
//@SessionAttributes({ "connectedCli", "panier"  })
public class HomeController {
	@Autowired
	ProduitRepository prodR;
	
	@Autowired
	CommandeRepository cmdR;
	
	@GetMapping({ "/","/accueil" })
	public String getAcceuilFrm(Model model, HttpSession session) {		
		session.removeAttribute("cmdRequest");
		
		List<Produit> lst = prodR.findAll();
		model.addAttribute("lstProd", lst);	
		
		HashMap<Produit, Integer> panier=(HashMap<Produit, Integer>) session.getAttribute("panier");
		
		if (panier == null) {			
			panier = new HashMap<Produit, Integer>();
			session.setAttribute("panier",panier);	
		}
		model.addAttribute("panier", panier);
				
		Client connectedCli=(Client) session.getAttribute("connectedCli");		
		model.addAttribute("connectedCli", connectedCli);		 
		 
		return "viewAccueil";
	}
	
}
