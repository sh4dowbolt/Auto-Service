package com.agropro.testTask.controller;

import com.agropro.testTask.Entity.Car;
import com.agropro.testTask.Entity.Dealer;
import com.agropro.testTask.Entity.Owner;
import com.agropro.testTask.service.AutoService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/")

public class MainController {
    private final AutoService autoService;
    public MainController(AutoService autoService) {
        this.autoService = autoService;
    }


    @GetMapping("/autoservice/")
    public String showOwnersAndDillers1(Model model) {
        model.addAttribute("owners", autoService.showAllOwner());
        model.addAttribute("dealers", autoService.showAllDealers());

        return "autoservice";
    }

    // панель контроля автомобилями
    @GetMapping("/autoservice/controlPanelOfCars/{id}")
    public String showCarByOwner(@PathVariable("id") Long id, Model model) {
        model.addAttribute("carsById",autoService.showCarByOwnerId(id));
        model.addAttribute("carsByNotId", autoService.showCarByNotOwnerId(id));
        return "controlPanelOfCars";
    }

    @GetMapping("/autoservice/controlPanelOfCars/{id}/addCar/{carId}")
    public String addCarToOwner(@PathVariable("id") Long id,
                                @PathVariable("carId") Long carId, Model model) {
        model.addAttribute("id", id);

        autoService.updateToAddCarToOwner(id, carId);
        return "redirect:/autoservice/controlPanelOfCars/{id}";
    }
    @GetMapping("/autoservice/controlPanelOfCars/{id}/removeCar/{carId}")
    public String removeCarFromOwner(@PathVariable("id") Long id,
                                @PathVariable("carId") Long carId, Model model) {
        model.addAttribute("id", id);

        autoService.updateToRemoveCarFromOwner(id, carId);
        return "redirect:/autoservice/controlPanelOfCars/{id}";
    }

    // панель контроля владельцами
    @GetMapping("/autoservice/controlPanelOfOwners/{id}")
    public String showOwnerByDealer(@PathVariable("id") Long id, Model model) {
        model.addAttribute("ownersById",autoService.showOwnerByDealerId(id));
        model.addAttribute("ownersByNotId", autoService.showOwnerByNotDealerId(id));
        model.addAttribute("carsDealer", autoService.showCarsFromDealer(id));
        return "controlPanelOfOwners";
    }

    @GetMapping("/autoservice/controlPanelOfOwners/{id}/addOwner/{OwnerId}")
    public String addOwnerToDealer(@PathVariable("id") Long id,
                                @PathVariable("OwnerId") Long OwnerId, Model model) {
        model.addAttribute("id", id);

        autoService.updateToAddOwnerToDealer(id, OwnerId);
        return "redirect:/autoservice/controlPanelOfOwners/{id}";
    }
    @GetMapping("/autoservice/controlPanelOfOwners/{id}/removeOwner/{OwnerId}")
    public String removeOwnerFromDealer(@PathVariable("id") Long id,
                                     @PathVariable("OwnerId") Long OwnerId, Model model) {
        model.addAttribute("id", id);

        autoService.updateToRemoveOwnerFromDealer(id, OwnerId);
        return "redirect:/autoservice/controlPanelOfOwners/{id}";
    }


    // создание автомобилей, владельцев, диллеров


    //utils
    @GetMapping("/autoservice/createCar/")
    public String createEntity(Car car) {
        return "createCar";
    }
    @GetMapping("/autoservice/createOwner/")
    public String createEntity(Owner owner) {
        return "createOwner";
    }
    @GetMapping("/autoservice/createDealer/")
    public String createEntity(Dealer dealer) {
        return "createDealer";
    }


    @ModelAttribute("carCommand")
    public Car defaultInstanceofCar() {
        Car car = new Car();
        return car;
    }
    @ModelAttribute("ownerCommand")
    public Owner defaultInstanceofOwner() {
        Owner owner = new Owner();
        return owner;
    }
    @ModelAttribute("dealerCommand")
    public Dealer defaultInstanceofDealer() {
        Dealer dealer = new Dealer();
        return dealer;
    }


    @PostMapping("/autoservice/createCar/")
    public String createCar(@Valid Car newCar, BindingResult bindingResult, Model model)
    {
        model.addAttribute("car", newCar);
        if(bindingResult.hasErrors()) {
            return "createCar";
        }
        autoService.create(newCar);
        return "redirect:/autoservice/createCar/";
    }

    @PostMapping("/autoservice/createOwner/")
    public String createOwner(@Valid Owner newOwner, BindingResult bindingResult, Model model) {
        model.addAttribute("owner", newOwner);
        if (bindingResult.hasErrors()) {
            return "createOwner";
        }
            autoService.create(newOwner);
            return "redirect:/autoservice/createOwner/";
    }
    @PostMapping("/autoservice/createDealer/")
    public String createDealer(@Valid Dealer newDealer,BindingResult bindingResult,Model model) {
        model.addAttribute("dealer", newDealer);
        if (bindingResult.hasErrors()) {
            return "createDealer";
        }
        autoService.create(newDealer);
        return "redirect:/autoservice/createDealer/";
    }


}
