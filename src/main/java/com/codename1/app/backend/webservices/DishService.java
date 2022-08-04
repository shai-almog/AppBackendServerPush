package com.codename1.app.backend.webservices;

import com.codename1.app.backend.data.CategoryEntity;
import com.codename1.app.backend.data.CategoryRepository;
import com.codename1.app.backend.data.DishEntity;
import com.codename1.app.backend.data.DishRepository;
import com.codename1.app.backend.data.RestaurantEntity;
import com.codename1.app.backend.data.RestaurantRepository;
import com.codename1.app.backend.service.dao.AppVersion;
import com.codename1.app.backend.service.dao.Dish;
import com.codename1.app.backend.service.dao.DishContainer;
import com.codename1.app.backend.service.dao.RestaurantRequest;
import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/dish")
public class DishService {
    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private DishRepository dishRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @RequestMapping(method=RequestMethod.GET)
    public @ResponseBody DishContainer fetchDishes(@RequestParam(value="stamp", required=true) long stamp,
            @RequestParam(value="appId", required=true) String appId) {
        RestaurantEntity r = restaurantRepository.findOne(appId);
        if(r.getDishListUpdateTimestamp() != stamp) {
            Set<DishEntity> dd = r.getDishes();
            Dish[] a = new Dish[dd.size()];
            int pos = 0;
            for(DishEntity e : dd) {
                a[pos] = e.toDish();
                pos++;
            }
            Set<CategoryEntity> cats = r.getCategories();
            Iterator<CategoryEntity> catIterator = cats.iterator();
            String[] categories = new String[cats.size()];
            for(int iter = 0 ; iter < categories.length ; iter++) {
                categories[iter] = catIterator.next().getName();
            }
            return new DishContainer("" + r.getDishListUpdateTimestamp(), a, categories);
        }
        return new DishContainer(null, new Dish[0], new String[0]);
    }
    
    @RequestMapping(method = RequestMethod.PUT)
    public @ResponseBody String update(@RequestBody(required = true) Dish d) throws IOException {
        RestaurantEntity e = restaurantRepository.findBySecret(d.getSecret());
        DishEntity de = null;
        if(d.getId() == null) {
            de = new DishEntity();
        } else {
            for(DishEntity cde : e.getDishes()) {
                if(cde.getId().toString().equals(d.getId())) {
                    de = cde;
                    break;
                }
            }
        }
        // de can be null in case of a security exploit in which case a null pointer exception will
        // block the attack
        de.setDescription(d.getDescription());
        de.setName(d.getName());
        de.setPrice(d.getPrice());

        CategoryEntity ce = null;
        for(CategoryEntity current : e.getCategories()) {
            if(current.getName().equals(d.getCategory())) {
                ce = current;
                break;
            }
        }
        if(ce == null && d.getCategory() != null) {
            ce = new CategoryEntity(d.getCategory());
            categoryRepository.save(ce);
        }
        
        CategoryEntity oe = de.getCategory();
        de.setCategory(ce);

        dishRepository.save(de);
        if(d.getId() == null) {
            Set<DishEntity> dishes = new HashSet<>(e.getDishes());
            dishes.add(de);
            e.setDishes(dishes);
            restaurantRepository.save(e);
        }
        
        if(oe != null) {
            cullCategory(e, oe);
        }
        
        return de.getId().toString();
    }
    
    private void cullCategory(RestaurantEntity e, CategoryEntity ce) {
        for(DishEntity de : e.getDishes()) {
            if(ce.equals(de.getCategory())) {
                return;
            }
        }
        categoryRepository.delete(ce);
    }
    
    @RequestMapping(method = RequestMethod.DELETE)
    public @ResponseBody String delete(@RequestBody(required = true) Dish dsh) throws IOException {
        RestaurantEntity e = restaurantRepository.findBySecret(dsh.getSecret());
        for(DishEntity d : e.getDishes()) {
            if(d.getId().equals(dsh.getId())) {
                HashSet<DishEntity> de = new HashSet<>();
                de.addAll(e.getDishes());
                de.remove(d);
                e.setDishes(de);
                long time = System.currentTimeMillis();
                e.setDishListUpdateTimestamp(time);
                restaurantRepository.save(e);
                d.setDeleteTime(time);
                
                if(d.getCategory() != null) {
                    CategoryEntity ce = d.getCategory();
                    d.setCategory(null);
                    dishRepository.save(d);
                    cullCategory(e, ce);
                } else {
                    dishRepository.save(d);
                }
                                
                return "OK";
            }
        }
        return "Not found";
    }

    @RequestMapping(method = RequestMethod.POST)
    public @ResponseBody String updateImage(@RequestParam(name="secret", required = true) String secret,
            @RequestParam(name="img", required = true) MultipartFile img,
            @RequestParam(name="d", required = true) String d) throws IOException {
        RestaurantEntity e = restaurantRepository.findBySecret(secret);
        DishEntity de = null;
        for(DishEntity cde : e.getDishes()) {
            if(cde.getId().toString().equals(d)) {
                de = cde;
                break;
            }
        }
        
        de.setImageData(img.getBytes());
        
        dishRepository.save(de);
        
        return de.getId().toString();
    }
    
}