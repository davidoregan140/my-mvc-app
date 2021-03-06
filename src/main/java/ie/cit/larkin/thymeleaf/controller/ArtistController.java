package ie.cit.larkin.thymeleaf.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import ie.cit.larkin.thymeleaf.entity.Artist;
import ie.cit.larkin.thymeleaf.service.ArtistService;

/**
 * Handles requests for the application home page.
 */
@Controller
@RequestMapping("/artist")
public class ArtistController {

	@Autowired
	ArtistService artistService;

	@RequestMapping("/")
	public String list(Model model) {
		
		Iterable<Artist> a= artistService.findAllMales();
		model.addAttribute("artistsMale", a);
		
		Iterable<Artist> b= artistService.findAllFemales();		
		model.addAttribute("artistsFemale", b);

		return "artist/list";
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public String view(Model model, @PathVariable("id") int id) {
		
		Artist a= artistService.findById(id);
		
		model.addAttribute("artist", a);
		
		return "artist/view";
	}


    @GetMapping("/create")
    public String createForm(Model model) {

    	model.addAttribute("artist", new Artist());
    	
    	return "artist/create";
    
    }
	
    @Secured("ROLE_ADMIN")
    @PostMapping("/")
    public String createSubmit(@Valid Artist artist, BindingResult result, Model model) {

    	if (result.hasErrors()) {
    		return "artist/create";
    	}
    	
    	artistService.save(artist);
    	
    	model.addAttribute("artist", artist);
    	
    	return "redirect:/artist/";
    	
    }
	
}
