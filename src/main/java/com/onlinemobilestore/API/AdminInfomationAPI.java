package com.onlinemobilestore.API;

import com.onlinemobilestore.entity.*;
import com.onlinemobilestore.repository.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin("*")
@RestController
@RequestMapping("/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminInfomationAPI {
    @Autowired
    ProductRepository proDAO;
    @Autowired
    CategoryRepository cateDAO;
    @Autowired
    ColorRepository colDAO;
    @Autowired
    StorageRepository stoDAO;
    @Autowired
    TrademarkRepository traDAO;

    @Autowired
    ImageRepository imgDAO;

    @GetMapping("/color/{id}")
    public ResponseEntity<Color> getProductById(@PathVariable Integer id) {
        Optional<Color> color = colDAO.findById(id);
        return color.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/color")
    public ResponseEntity<Color> createProduct(@RequestBody Color color) {
        Color createColor = colDAO.save(color);
        return ResponseEntity.status(HttpStatus.CREATED).body(createColor);
    }

    @PutMapping("/color/{id}")
    public ResponseEntity<Color> updateProduct(@PathVariable Integer id, @RequestBody Color updateColor) {
        Optional<Color> existingColor = colDAO.findById(id);
        if (existingColor.isPresent()) {
            updateColor.setId(id);
            Color savedColor = colDAO.save(updateColor);
            return ResponseEntity.ok(savedColor);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/color/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Integer id) {
        Optional<Color> product = colDAO.findById(id);
        if (product.isPresent()) {
            colDAO.deleteById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Category
    @GetMapping("/categories")
    public ResponseEntity<List<Category>> getAllCategories() {
        List<Category> categories = cateDAO.findAll();
        return ResponseEntity.ok(categories);
    }

    @GetMapping("/category/{id}")
    public ResponseEntity<Category> getCategoryById(@PathVariable int id) {
        Optional<Category> category = cateDAO.findById(id);
        return category.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/category")
    public ResponseEntity<Category> createCategory(@RequestBody Category category) {
        Category createdCategory = cateDAO.save(category);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdCategory);
    }

    @PutMapping("/category/{id}")
    public ResponseEntity<Category> updateCategory(@PathVariable int id, @RequestBody Category updatedCategory) {
        Optional<Category> existingCategory = cateDAO.findById(id);
        if (existingCategory.isPresent()) {
            updatedCategory.setId(id);
            Category savedCategory = cateDAO.save(updatedCategory);
            return ResponseEntity.ok(savedCategory);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/category/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable int id) {
        Optional<Category> category = cateDAO.findById(id);
        if (category.isPresent()) {
            cateDAO.deleteById(id);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Storage
    @GetMapping("/storages")
    public ResponseEntity<List<Storage>> getAllStorages() {
        List<Storage> storages = stoDAO.findAll();
        return ResponseEntity.ok(storages);
    }

    @GetMapping("/storage/{id}")
    public ResponseEntity<Storage> getStorageById(@PathVariable int id) {
        Optional<Storage> storage = stoDAO.findById(id);
        return storage.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/storage")
    public ResponseEntity<Storage> createStorage(@RequestBody Storage storage) {
        Storage createdStorage = stoDAO.save(storage);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdStorage);
    }

    @PutMapping("/storage/{id}")
    public ResponseEntity<Storage> updateStorage(@PathVariable int id, @RequestBody Storage updatedStorage) {
        Optional<Storage> existingStorage = stoDAO.findById(id);
        if (existingStorage.isPresent()) {
            updatedStorage.setId(id);
            Storage savedStorage = stoDAO.save(updatedStorage);
            return ResponseEntity.ok(savedStorage);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/storage/{id}")
    public ResponseEntity<Void> deleteStorage(@PathVariable int id) {
        Optional<Storage> storage = stoDAO.findById(id);
        if (storage.isPresent()) {
            stoDAO.deleteById(id);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    //Trademark
    @GetMapping("/trademarks")
    public ResponseEntity<List<Trademark>> getAllTrademarks() {
        List<Trademark> trademarks = traDAO.findAll();
        return ResponseEntity.ok(trademarks);
    }

    @GetMapping("/trademark/{id}")
    public ResponseEntity<Trademark> getTrademarkById(@PathVariable int id) {
        Optional<Trademark> trademark = traDAO.findById(id);
        return trademark.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/trademark")
    public ResponseEntity<Trademark> createTrademark(@RequestBody Trademark trademark) {
        Trademark createdTrademark = traDAO.save(trademark);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdTrademark);
    }

    @PutMapping("/trademark/{id}")
    public ResponseEntity<Trademark> updateTrademark(@PathVariable int id, @RequestBody Trademark updatedTrademark) {
        Optional<Trademark> existingTrademark = traDAO.findById(id);
        if (existingTrademark.isPresent()) {
            updatedTrademark.setId(id);
            Trademark savedTrademark = traDAO.save(updatedTrademark);
            return ResponseEntity.ok(savedTrademark);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/trademark/{id}")
    public ResponseEntity<Void> deleteTrademark(@PathVariable int id) {
        Optional<Trademark> trademark = traDAO.findById(id);
        if (trademark.isPresent()) {
            traDAO.deleteById(id);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }



}