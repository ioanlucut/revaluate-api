package com.revaluate.category.service;

import com.revaluate.account.persistence.User;
import com.revaluate.account.persistence.UserRepository;
import com.revaluate.category.domain.CategoryDTO;
import com.revaluate.category.exception.CategoryException;
import com.revaluate.category.persistence.Category;
import com.revaluate.category.persistence.CategoryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl implements CategoryService {

    protected static final Logger LOGGER = LoggerFactory.getLogger(CategoryService.class);

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public boolean isUnique(String name) {
        return categoryRepository.findByName(name).isEmpty();
    }

    @Override
    public CategoryDTO create(CategoryDTO categoryDTO, int userId) throws CategoryException {
        if (!categoryRepository.findByName(categoryDTO.getName()).isEmpty()) {
            throw new CategoryException("Name is not unique");
        }
        Category category = new Category();
        BeanUtils.copyProperties(categoryDTO, category);

        User foundUser = userRepository.findOne(userId);
        category.setUser(foundUser);

        Category savedCategory = categoryRepository.save(category);
        CategoryDTO savedCategoryDTO = new CategoryDTO();
        BeanUtils.copyProperties(savedCategory, savedCategoryDTO);

        return savedCategoryDTO;
    }

    @Override
    public CategoryDTO update(CategoryDTO categoryDTO, int userId) throws CategoryException {
        if (!categoryRepository.exists(categoryDTO.getId())) {
            throw new CategoryException("No category found with the given id");
        }

        Category foundCategory = categoryRepository.findOne(categoryDTO.getId());
        if (foundCategory.getUser().getId() != userId) {
            throw new CategoryException("Not authorized");
        }

        BeanUtils.copyProperties(categoryDTO, foundCategory);
        Category savedCategory = categoryRepository.save(foundCategory);
        CategoryDTO savedCategoryDTO = new CategoryDTO();
        BeanUtils.copyProperties(savedCategory, savedCategoryDTO);

        return savedCategoryDTO;
    }

    @Override
    public void remove(int categoryId, int userId) throws CategoryException {
        if (!categoryRepository.exists(categoryId)) {
            throw new CategoryException("No category found with the given id");
        }

        Category foundCategory = categoryRepository.findOne(categoryId);
        if (foundCategory.getUser().getId() != userId) {
            throw new CategoryException("Not authorized");
        }

        categoryRepository.delete(categoryId);
    }
}