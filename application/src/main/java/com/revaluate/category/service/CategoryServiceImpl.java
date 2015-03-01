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
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Validated
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
        return !categoryRepository.findOneByNameAndUserId(name, userId).isPresent();
    }

    @Override
    public CategoryDTO create(CategoryDTO categoryDTO, int userId) throws CategoryException {
        Optional<Category> categoryByName = categoryRepository.findOneByNameAndUserId(categoryDTO.getName(), userId);
        if (categoryByName.isPresent()) {
            throw new CategoryException("Category name is not unique");
        }

        Category category = dozerBeanMapper.map(categoryDTO, Category.class);
        User foundUser = userRepository.findOne(userId);
        category.setUser(foundUser);

        Category savedCategory = categoryRepository.save(category);
        return dozerBeanMapper.map(savedCategory, CategoryDTO.class);
    }

    @Override
    public CategoryDTO update(CategoryDTO categoryDTO, int userId) throws CategoryException {
        Optional<Category> categoryById = categoryRepository.findOneByIdAndUserId(categoryDTO.getId(), userId);
        Category category = categoryById.orElseThrow(() -> new CategoryException("The given category does not exists"));

        //-----------------------------------------------------------------
        // Update the category with given category DTO
        //-----------------------------------------------------------------
        dozerBeanMapper.map(categoryDTO, category, CATEGORY_DTO__UPDATE);

        Category savedCategory = categoryRepository.save(category);
        return dozerBeanMapper.map(savedCategory, CategoryDTO.class);
    }

    @Override
    public List<CategoryDTO> findAllCategoriesFor(int userId) throws CategoryException {
        List<Category> categories = categoryRepository.findAllByUserId(userId);

        return categories.stream().map(category -> dozerBeanMapper.map(category, CategoryDTO.class)).collect(Collectors.toList());
    }

    @Override
    public void remove(int categoryId, int userId) throws CategoryException {
        Optional<Category> categoryById = categoryRepository.findOneByIdAndUserId(categoryId, userId);
        categoryById.orElseThrow(() -> new CategoryException("The given category does not exists"));

        categoryRepository.delete(categoryId);
    }
}