package chnu.edu.websecurity26.controller;

import chnu.edu.websecurity26.model.Item;
import chnu.edu.websecurity26.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/*
- @author katia
- @project web-security
- @class ItemRestController
- @version 1.1.0
- @since 27/09/2026
 */
@RestController
@RequestMapping("/api/v1/items")
@RequiredArgsConstructor
public class ItemRestController {

    private final ItemService itemService;

    @GetMapping
    public List<Item> getAll() {
        return itemService.getAllItems();
    }

    @GetMapping("/{id}")
    public Item getOne(@PathVariable String id) {
        return itemService.getItem(id);
    }

    @PostMapping
    public Item create(@RequestBody Item item) {
        return itemService.createItem(item);
    }

    @PutMapping
    public Item update(@RequestBody Item item) {
        return itemService.updateItem(item);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        itemService.deleteItem(id);
    }

    @GetMapping("/hello/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public String helloAdmin() {
        return "Hello Admin!";
    }

    @GetMapping("/hello/unknown")
    @PreAuthorize("permitAll()")
    public String helloUnknown() {
        return "Hello Unknown!";
    }

    @GetMapping("/hello/user")
    @PreAuthorize("hasRole('USER')")
    public String helloUser() {
        return "Hello User!";
    }

    @GetMapping("/hello/man")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN', 'SUPERADMIN')")
    public String helloMan() {
        return "Hello, man!";
    }
}