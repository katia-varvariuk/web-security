package chnu.edu.websecurity26.service;

import chnu.edu.websecurity26.model.Item;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/*
 * @author katia
 * @project web-security
 * @class ItemService
 * @version 1.0.0
 * @since 27/09/2026
 */
@Service
public class ItemService {

    private List<Item> items = new ArrayList<>();

    @PostConstruct
    void init() {
        items.add(new Item("1", "name1", "description1"));
        items.add(new Item("2", "name2", "description2"));
        items.add(new Item("3", "name3", "description3"));
    }

    public List<Item> getAllItems() {
        return items;
    }

    public Item createItem(Item item) {
        items.add(item);
        return item;
    }

    public Item getItem(String id) {
        return items.stream()
                .filter(item -> item.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    public Item updateItem(Item item) {
        Item oldItem = getItem(item.getId());
        if (oldItem != null) {
            items.remove(oldItem);
            items.add(item);
        }
        return item;
    }

    public void deleteItem(String id) {
        Item item = getItem(id);
        if (item != null) {
            items.remove(item);
        }
    }
}