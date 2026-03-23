package com.fashionapp.controller;

import com.fashionapp.model.Cloth;
import com.fashionapp.service.ClothService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clothes")
public class ClothController {

    @Autowired
    private ClothService clothService;

    @PostMapping
    public Cloth addCloth(@RequestBody Cloth cloth) {
        return clothService.addCloth(cloth);
    }

    @GetMapping
    public List<Cloth> getAllClothes() {
        return clothService.getAllClothes();
    }

    @GetMapping("/{id}")
    public  Cloth getClothById(@PathVariable Long id) {
        return clothService.getClothById(id);
    }

    @DeleteMapping("/{id}")
    public String deleteClothById(@PathVariable Long id) {
        clothService.deleteCloth(id);
        return "Cloth deleted successfully";
    }
}
