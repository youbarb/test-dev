package fr.orsys.banque.control;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import fr.orsys.banque.entity.Compte;
import fr.orsys.banque.repository.CompteDao;
import fr.orsys.banque.service.Banque;

@RequestMapping("/hello")
@Controller
public class HelloBanqueController {
	
	@Autowired
	Banque banque;
	
	@Autowired
	CompteDao compteDao;
	
	@RequestMapping("/lesComptes")  // url = http://localhost:8080/banque/hello/lesComptes.spring
	public String helloBanque(Model model) {
		
		List<Compte> comptes = banque.rechercherTousLesComptes();
		model.addAttribute("comptes", comptes);
		return "hello";
	}

}
