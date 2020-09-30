package app.controllers;

import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import app.models.Client;
import app.repositories.ClientRepository;
import app.repositories.CommandeRepository;

@Controller
//@SessionAttributes({ "connectedCli", "cmdRequest","panier" })
//@SessionAttributes({ "connectedCli" })
public class UserController {
	@Autowired
	ClientRepository cr;
	@Autowired
	CommandeRepository cmdR;

	@GetMapping({ "/deconnexion" })
	public String getDeconnexion(HttpSession session) {
		session.removeAttribute("connectedCli");	 	
		 
		return "redirect:/accueil";
	}
	
	@GetMapping({ "/login" })
	public String getLoginForm(@ModelAttribute("client") Client curClient, HttpSession session) {
		session.removeAttribute("connectedCli");
		return "viewLogin";
	}

	@PostMapping({ "/login" })
	public String checkLogin(@ModelAttribute("client") Client curClient, HttpSession session, RedirectAttributes ra) {

		//session.removeAttribute("connectedCli");	
				
		List<Client> matches = cr.getClientByName(curClient.getNom());	
		
		boolean isCltTrouve = false;
		for (Client cltInDb : matches) {
			if (cltInDb.getPassword().equals(curClient.getPassword())) {
				isCltTrouve = true;
				
				session.setAttribute("connectedCli", cltInDb);
				
				if (session.getAttribute("cmdRequest") != null && session.getAttribute("cmdRequest").equals("1")) {
					
					return "redirect:/panier";
				} else
					return "redirect:/accueil";
			}
		}

		if (!isCltTrouve) {
			ra.addFlashAttribute("client", curClient);
			ra.addFlashAttribute("msglogin", "Nom ou le mot de passe incorrects! ");
			//return "redirect:/login";
		}

		return "redirect:/login";
	}

	

	@GetMapping({ "/createclient" })
	public String getClientCreationFrm(@ModelAttribute("client") Client newClient) {
		return "viewNewClient";
	}

	@PostMapping({ "/createclient" })
	public String saveClient(@ModelAttribute("client") @Valid Client client, BindingResult bindingRes,
			RedirectAttributes ra, HttpSession session) {
		
		if (bindingRes.hasErrors()) {
			String msgErr = "";
			for (ObjectError elt : bindingRes.getAllErrors()) {
				msgErr += elt.toString();
			}
			System.out.println(" les erreurs dans la saisie: " + msgErr);
			return "viewNewClient";
		}

		List<Client> matches = cr.getClientByName(client.getNom());
		if (matches.size() > 0) {			
			ra.addFlashAttribute("client", client);
			ra.addFlashAttribute("msgnom", "Utilisateur avec ce nom exist déjà");
			return "redirect:/createclient";
		}

		cr.save(client);

		session.setAttribute("connectedCli", client);
		if (session.getAttribute("cmdRequest") != null && session.getAttribute("cmdRequest").equals("1")) {		
			return "redirect:/panier";	// redirection vers le panier pour finaliser la commande
		} else
			return "redirect:/accueil";

	}

	

	@GetMapping({"/clientModif"})
	public String getModifCompteFrm(Model model, HttpSession session) {
		Client connectedCli=(Client) session.getAttribute("connectedCli");		
		if (connectedCli != null) {
			model.addAttribute("connectedCli", connectedCli);
			return "viewModifClient";
		}
		
		return "redirect:/accueil";		
	}
	
	@PostMapping({"/clientModif"})
	public String saveModifClient(Model model,@ModelAttribute(name = "connectedCli") @Valid Client clientToUpdate, BindingResult bindingRes,
			 HttpSession session) {
		
		if (bindingRes.hasErrors()) {
			String msgErr = "";
			for (ObjectError elt : bindingRes.getAllErrors()) {
				msgErr += elt.toString();
			}
			System.out.println(" les erreurs dans la saisie: " + msgErr);
			return "viewModifClient";
		}

		List<Client> matches = cr.getClientByName(clientToUpdate.getNom(),clientToUpdate.getId());
		if (matches.size() > 0) {
			model.addAttribute("m_msg", "Utilisateur avec ce nom exist déjà");
			return "viewModifClient";//"redirect:/clientModif";
		}

		cr.save(clientToUpdate);

		session.setAttribute("connectedCli", clientToUpdate);
		return "redirect:/accueil";
	}
	
	@GetMapping({ "/clientSuppr" })
	public String getSupprCompteFrm(Model model, HttpSession session) {
		Client connectedCli=(Client) session.getAttribute("connectedCli");		
		if (connectedCli != null) {
			model.addAttribute("connectedCli", connectedCli);
			return "viewDeleteClient";
		}
		return "redirect:/accueil";
	}
	
	@PostMapping({ "/clientSuppr" })
	public String supprCompte(HttpSession session) {
		Client connectedCli=(Client) session.getAttribute("connectedCli");		
		if (connectedCli != null) {
			cmdR.deleteCommandesClient(connectedCli.getId());
			cr.delete(connectedCli);
			return "redirect:/deconnexion";
		}
		return "redirect:/accueil";
	}
}
