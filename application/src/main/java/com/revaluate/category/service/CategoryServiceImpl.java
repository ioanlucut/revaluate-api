package com.revaluate.category.service;

import com.revaluate.account.persistence.User;
import com.revaluate.account.persistence.UserRepository;
import com.revaluate.category.exception.CategoryException;
import com.revaluate.category.persistence.Category;
import com.revaluate.category.persistence.CategoryRepository;
import com.revaluate.domain.category.CategoryDTO;
import com.revaluate.expense.persistence.ExpenseRepository;
import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Validated
public class CategoryServiceImpl implements CategoryService {

    public static final String CATEGORY_DTO__UPDATE = "CategoryDTO__Update";

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ExpenseRepository expenseRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DozerBeanMapper dozerBeanMapper;

    @Override
    public boolean isUnique(String name, int userId) {
        return !categoryRepository.findOneByNameIgnoreCaseAndUserId(name, userId).isPresent();
    }

    @Override
    public CategoryDTO create(CategoryDTO categoryDTO, int userId) throws CategoryException {
        Optional<Category> categoryByName = categoryRepository.findOneByNameIgnoreCaseAndUserId(categoryDTO.getName(), userId);
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
    public List<CategoryDTO> bulkCreate(List<CategoryDTO> categoryDTOs, int userId) throws CategoryException {
        //-----------------------------------------------------------------
        // Categories have to be unique between them
        //-----------------------------------------------------------------
        Map<String, List<CategoryDTO>> abc = categoryDTOs.stream().collect(Collectors.groupingBy(CategoryDTO::getName));
        if (abc.size() != categoryDTOs.size()) {
            throw new CategoryException("Categories are not unique between them");
        }

        //-----------------------------------------------------------------
        // Categories have to be unique (not existing into database)
        //-----------------------------------------------------------------
        if (categoryDTOs.stream().anyMatch(categoryDTO -> categoryRepository.findOneByNameIgnoreCaseAndUserId(categoryDTO.getName(), userId).isPresent())) {
            throw new CategoryException("Not all category names are unique");
        }

        User foundUser = userRepository.findOne(userId);
        List<Category> categories = categoryDTOs.stream()
                .map(categoryDTO -> {
                    Category category = dozerBeanMapper.map(categoryDTO, Category.class);
                    category.setUser(foundUser);

                    return category;
                })
                .collect(Collectors.toList());

        List<Category> savedCategories = categoryRepository.save(categories);

        return savedCategories.stream()
                .map(categoryDTO -> dozerBeanMapper.map(categoryDTO, CategoryDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public void bulkDelete(List<CategoryDTO> categoryDTOs, int userId) throws CategoryException {
        //-----------------------------------------------------------------
        // Categories have to exist for this user.
        //-----------------------------------------------------------------
        if (!categoryDTOs.stream().allMatch(categoryDTO -> categoryRepository.findOneByIdAndUserId(categoryDTO.getId(), userId).isPresent())) {
            throw new CategoryException("One or more category is invalid.");
        }

        List<Category> categories = categoryDTOs.stream()
                .map(categoryDTO -> categoryRepository.findOneByIdAndUserId(categoryDTO.getId(), userId).get())
                .collect(Collectors.toList());

        categoryRepository.delete(categories);
    }

    @Override
    public CategoryDTO update(CategoryDTO categoryDTO, int userId) throws CategoryException {
        Optional<Category> categoryById = categoryRepository.findOneByIdAndUserId(categoryDTO.getId(), userId);
        Category category = categoryById.orElseThrow(() -> new CategoryException("The given category does not exists"));

        //-----------------------------------------------------------------
        // Means that the category name is changed
        //-----------------------------------------------------------------
        if (!category.getName().equalsIgnoreCase(categoryDTO.getName()) && !isUnique(categoryDTO.getName(), userId)) {

            throw new CategoryException("The given category name exists already");
        }

        //-----------------------------------------------------------------
        // Update the category with given category DTO
        //-----------------------------------------------------------------
        dozerBeanMapper.map(categoryDTO, category, CATEGORY_DTO__UPDATE);

        Category savedCategory = categoryRepository.save(category);
        return dozerBeanMapper.map(savedCategory, CategoryDTO.class);
    }

    @Override
    public List<CategoryDTO> findAllCategoriesFor(int userId) {
        List<Category> categories = categoryRepository.findAllByUserId(userId);

        return categories.stream().map(category -> dozerBeanMapper.map(category, CategoryDTO.class)).collect(Collectors.toList());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void remove(int categoryId, int userId) throws CategoryException {
        Optional<Category> categoryById = categoryRepository.findOneByIdAndUserId(categoryId, userId);
        categoryById.orElseThrow(() -> new CategoryException("The given category does not exists"));

        //-----------------------------------------------------------------
        // We delete all expenses of this category
        //-----------------------------------------------------------------
        expenseRepository.removeByCategoryId(categoryId);

        categoryRepository.delete(categoryId);
    }
}