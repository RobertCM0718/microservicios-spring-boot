package com.quetzalcode.items.controller;

import com.quetzalcode.items.dto.Item;
import com.quetzalcode.items.service.IItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ItemController {

    @Autowired
    @Qualifier("serviceFeign")
    private IItemService iItemService;

    @GetMapping("/listar")
    public List<Item> listar(){
        return iItemService.findAll();
    }
    @GetMapping("/ver/{id}/cantidad/{cantidad}")
    public Item detalle(@PathVariable Long id,@PathVariable Integer cantidad){
        return iItemService.findById(id, cantidad);
    }


}
