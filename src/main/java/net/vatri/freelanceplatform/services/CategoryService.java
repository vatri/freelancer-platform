package net.vatri.freelanceplatform.services;

import net.vatri.freelanceplatform.models.Category;
import net.vatri.freelanceplatform.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

        @Autowired
        CategoryRepository categoryRepository;

        public Category get(long id){
            return categoryRepository.findOne(id);
        }

        public Category add(Category category){
            return categoryRepository.save(category);
        }

        public List<Category> list(){
            return categoryRepository.findAll();
        }


}
