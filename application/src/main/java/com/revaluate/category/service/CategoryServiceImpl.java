package com.revaluate.category.service;

import com.revaluate.account.persistence.User;
import com.revaluate.account.persistence.UserRepository;
import com.revaluate.category.exception.CategoryException;
import com.revaluate.category.persistence.Category;
import com.revaluate.category.persistence.CategoryRepository;
import com.revaluate.domain.category.CategoryDTO;
import com.revaluate.expense.persistence.ExpenseRepository;
import com.revaluate.goals.persistence.GoalRepository;
import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.validation.ConstraintViolationException;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Validated
public class CategoryServiceImpl implements CategoryService {

    public static final String CATEGORY_DTO__UPDATE = "CategoryDTO__Update";
    public static final String CATEGORIES_CACHE = "categories";

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ExpenseRepository expenseRepository;

    @Autowired
    private GoalRepository goalRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DozerBeanMapper dozerBeanMapper;

    @Override
    public boolean isUnique(String name, int userId) {
        return !categoryRepository.findOneByNameIgnoreCaseAndUserId(name, userId).isPresent();
    }

    @Override
    @CacheEvict(value = CATEGORIES_CACHE, allEntries = true)
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
    @CacheEvict(value = CATEGORIES_CACHE, allEntries = true)
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
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(value = CATEGORIES_CACHE, allEntries = true)
    public List<CategoryDTO> setupBulkCreateCategories(List<CategoryDTO> categoryDTOs, int userId) throws CategoryException {
        categoryRepository.removeByUserId(userId);

        return bulkCreate(categoryDTOs, userId);
    }

    @Override
    @CacheEvict(value = CATEGORIES_CACHE, allEntries = true)
    public void bulkDelete(List<CategoryDTO> categoryDTOs, int userId) throws CategoryException {
        //-----------------------------------------------------------------
        // Categories have to exist for this user.
        //-----------------------------------------------------------------
        List<Category> categories = categoryDTOs
                .stream()
                .map(categoryDTO -> {
                    Category found = categoryRepository
                            .findOneByIdAndUserId(categoryDTO.getId(), userId)
                            .orElseThrow(() -> new ConstraintViolationException("One or more category is invalid.", new HashSet<>()));
                    return found;
                })
                .collect(Collectors.toList());

        categoryRepository.delete(categories);
    }

    @Override
    @CacheEvict(value = CATEGORIES_CACHE, allEntries = true)
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
    @Cacheable(CATEGORIES_CACHE)
    public List<CategoryDTO> findAllCategoriesFor(int userId) {
        List<Category> categories = categoryRepository.findAllByUserId(userId);

        return categories.stream().map(category -> dozerBeanMapper.map(category, CategoryDTO.class)).collect(Collectors.toList());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(value = CATEGORIES_CACHE, allEntries = true)
    public void remove(int categoryId, int userId) throws CategoryException {
        categoryRepository
                .findOneByIdAndUserId(categoryId, userId)
                .orElseThrow(() -> new CategoryException("The given category does not exists"));

        //-----------------------------------------------------------------
        // We delete all expenses of this category
        //-----------------------------------------------------------------
        expenseRepository.removeByCategoryId(categoryId);

        //-----------------------------------------------------------------
        // We delete all goals of this category
        //-----------------------------------------------------------------
        goalRepository.removeByCategoryId(categoryId);

        categoryRepository.delete(categoryId);
    }
}