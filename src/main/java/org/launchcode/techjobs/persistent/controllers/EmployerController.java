package org.launchcode.techjobs.persistent.controllers;

import jakarta.validation.Valid;
import org.launchcode.techjobs.persistent.models.Employer;
import org.launchcode.techjobs.persistent.models.data.EmployerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("employers")
public class EmployerController {

    @Autowired // This annotation injects the EmployerRepository
    private EmployerRepository employerRepository;

//////////////////////////////////////////////////////////////////////////////////////////////////////////
    @GetMapping("/") // This maps to /employers
    public String index(Model model) {
        Iterable<Employer> allEmployers = employerRepository.findAll();

        // Add the list of employers to the model with the attribute name "employers"
        model.addAttribute("employers", allEmployers);

        return "employers/index";
    }

///////////////////////////////////////////////////////////////////////////////////////////////////////////
    @PostMapping("add")
    public String processAddEmployerForm(@ModelAttribute @Valid Employer newEmployer,
                                         Errors errors, Model model) {

        if (errors.hasErrors()) {
            return "employers/add";
        }
        employerRepository.save(newEmployer); // Save the valid employer object


        return "redirect:/employers";
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping("add")
    public String displayAddEmployerForm(Model model) {
        model.addAttribute(new Employer());
        return "employers/add";
    }


    @GetMapping("view/{employerId}")
    public String displayViewEmployer(Model model, @PathVariable int employerId) {

        // Use findById to retrieve the employer based on the ID
        Optional<Employer> optEmployer = employerRepository.findById(employerId);

        if (optEmployer.isPresent()) {
            Employer employer = optEmployer.get();
            model.addAttribute("employer", employer);
            return "employers/view";
        } else {
            return "redirect:../";
        }
    }

}