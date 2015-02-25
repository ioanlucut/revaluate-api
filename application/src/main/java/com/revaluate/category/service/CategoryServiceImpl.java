package com.revaluate.category.service;

import com.revaluate.account.persistence.User;
import com.revaluate.account.persistence.UserRepository;
import com.revaluate.category.domain.CategoryDTO;
import com.revaluate.category.exception.CategoryException;
import com.revaluate.category.persistence.Category;
import com.revaluate.category.persistence.CategoryRepository;
import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl implements CategoryService {

    public static final String CATEGORY_DTO__UPDATE = "CategoryDTO__Update";
    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DozerBeanMapper dozerBeanMapper;

    @Override
    public boolean isUnique(String name, int userId) {
        return categoryRepository.findOneByNameAndUserId(name, userId) == null;
    }

    @Override
    public CategoryDTO create(CategoryDTO categoryDTO, int userId) throws CategoryException {
        if (categoryRepository.findOneByNameAndUserId(categoryDTO.getName(), userId) != null) {
            throw new CategoryException("Name is not unique");
        }

        Category category = dozerBeanMapper.map(categoryDTO, Category.class);
        User foundUser = userRepository.findOne(userId);
        category.setUser(foundUser);

        Category savedCategory = categoryRepository.save(category);
        return dozerBeanMapper.map(savedCategory, CategoryDTO.class);
    }

    @Override
    public CategoryDTO update(CategoryDTO categoryDTO, int userId) throws CategoryException {
        Category foundCategory = categoryRepository.findOneByIdAndUserId(categoryDTO.getId(), userId);
        if (foundCategory == null) {
            throw new CategoryException("The given category does not exists");
        }

        //-----------------------------------------------------------------
        // Update the category with given category DTO
        //-----------------------------------------------------------------
        dozerBeanMapper.map(categoryDTO, foundCategory, CATEGORY_DTO__UPDATE);

        Category savedCategory = categoryRepository.save(foundCategory);
        return dozerBeanMapper.map(savedCategory, CategoryDTO.class);
    }

    @Override
    public void remove(int categoryId, int userId) throws CategoryException {
        Category foundCategory = categoryRepository.findOneByIdAndUserId(categoryId, userId);
        if (foundCategory == null) {
            throw new CategoryException("The given category does not exists");
        }

        categoryRepository.delete(categoryId);
    }
}