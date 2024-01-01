package com.geekydroid.savestmentbackend.service.expenditure;

import com.geekydroid.savestmentbackend.domain.expenditure.*;
import com.geekydroid.savestmentbackend.repository.expenditure.ExpenditureCategoryRepository;
import com.geekydroid.savestmentbackend.repository.expenditure.ExpenditureRepository;
import com.geekydroid.savestmentbackend.repository.expenditure.ExpenditureTypeRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.BadRequestException;
import java.time.LocalDateTime;
import java.util.List;

@ApplicationScoped
@Transactional
public class ExpenditureCategoryServiceImpl implements ExpenditureCategoryService {

    @Inject
    ExpenditureCategoryRepository repository;

    @Inject
    ExpenditureTypeRepository expenditureTypeRepository;

    @Inject
    ExpenditureService expenditureService;

    @Inject
    ExpenditureRepository expenditureRepository;

    @Override
    public CategoryRespnose create(CreateExpenditureCategoryRequest request) {
        if (request.getExpenditureTypeId() == null || request.getCreatedBy() == null) {
            throw new BadRequestException("Invalid Request");
        }
        ExpenditureType expenditureType = expenditureTypeRepository.getById(request.getExpenditureTypeId());
        if (expenditureType == null) {
            throw new BadRequestException("Expenditure type doesn't exist");
        }

        ExpenditureCategory duplicate = repository.getByName(request.getExpenditureTypeId(), request.getCategoryName().toLowerCase(), request.getCreatedBy());
        if (duplicate != null) {
            throw new BadRequestException("Expenditure category already exists");
        }

        LocalDateTime now = LocalDateTime.now();
        ExpenditureCategory newExpenditureCategory = new ExpenditureCategory(
                expenditureType,
                request.getCategoryName(),
                false,
                request.getCreatedBy(),
                now,
                now
        );

        newExpenditureCategory = repository.create(newExpenditureCategory);

        return new CategoryRespnose(newExpenditureCategory.getExpenditureCategoryId(), newExpenditureCategory.getCategoryName());
    }

    @Override
    public ExpenditureCategory getExpenditureCategoryByName(String expenditureCategoryStr) {
        return repository.getExpenditureCategoryName(expenditureCategoryStr);
    }

    @Override
    public List<CategoryRespnose> getExpenditureCategories(Long expenditureTypeId, String userId) {
        return repository.getExpenditureCategoryResponse(expenditureTypeId, userId);
    }

    @Override
    public CategoryRespnose delete(Long categoryId) {
        ExpenditureCategory category = repository.getById(categoryId);
        if (category == null) {
            throw new  BadRequestException("Category doesn't exist");
        }
        if (category.isCommon()) {
            throw new  BadRequestException("Default Category cannot be deleted");
        }
        category.getExpenditures().forEach(expenditure -> expenditureRepository.delete(expenditure));
        repository.delete(category);
        return new CategoryRespnose(categoryId, category.getCategoryName());
    }


    @Override
    public CategoryRespnose update(UpdateCategoryRequest request) {
        if (request.getCategoryName() == null) {
            throw new BadRequestException("Category name cannot be empty");
        }
        ExpenditureCategory category = repository.getById(request.getCategoryId());
        if (category == null) {
           throw new BadRequestException("Category doesn't exists");
        }
        if (category.isCommon()) {
            throw new BadRequestException("Default category cannot be edited");
        }
        category.setCategoryName(request.getCategoryName());
        category.setUpdatedOn(LocalDateTime.now());
        category = repository.update(category);
        return new CategoryRespnose(category.getExpenditureCategoryId(),category.getCategoryName());
    }

}
