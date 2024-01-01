package com.geekydroid.savestmentbackend.service.investment;

import com.geekydroid.savestmentbackend.domain.expenditure.CategoryRespnose;
import com.geekydroid.savestmentbackend.domain.expenditure.UpdateCategoryRequest;
import com.geekydroid.savestmentbackend.domain.investment.CreateInvestmentCategoryRequest;
import com.geekydroid.savestmentbackend.domain.investment.InvestmentType;
import com.geekydroid.savestmentbackend.repository.investment.InvestmentRepository;
import com.geekydroid.savestmentbackend.repository.investment.InvestmentTypeRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.BadRequestException;
import java.time.LocalDateTime;
import java.util.List;

@ApplicationScoped
@Transactional
public class InvestmentTypeServiceImpl implements InvestmentTypeService{

    @Inject
    InvestmentTypeRepository repository;

    @Inject
    InvestmentRepository investmentRepository;

    @Override
    public List<CategoryRespnose> getAllInvestmentCategories() {
        return repository.getAllInvestmentCategories();
    }

    @Override
    public CategoryRespnose create(CreateInvestmentCategoryRequest request) {
        if (request.getCategoryName() == null || request.getCategoryName().trim().isEmpty()) {
            throw new BadRequestException("Investment category/name cannot be emtpy");
        }
        InvestmentType duplicate = repository.getByName(request.getCategoryName());
        if (duplicate != null) {
            throw new BadRequestException("Investment category already exists");
        }

        InvestmentType newInvestmentType = new InvestmentType(
                request.getCategoryName(),
                false,
                request.getCreatedBy(),
                LocalDateTime.now(),
                LocalDateTime.now());
        newInvestmentType = repository.create(newInvestmentType);
        return new CategoryRespnose(newInvestmentType.getInvestmentTypeId(), newInvestmentType.getInvestmentName());

    }

    @Override
    public CategoryRespnose update(UpdateCategoryRequest request) {
        if (request.getCategoryName() == null) {
            throw new BadRequestException("Category name cannot be empty");
        }
        InvestmentType investmentType = repository.getById(request.getCategoryId());
        if (investmentType == null) {
            throw new BadRequestException("Category doesn't exist");
        }
        if (investmentType.isCommon()) {
            throw new BadRequestException("Common category cannot be updated");
        }
        investmentType.setInvestmentName(request.getCategoryName());
        investmentType.setUpdatedOn(LocalDateTime.now());
        investmentType = repository.update(investmentType);
        return new CategoryRespnose(investmentType.getInvestmentTypeId(), investmentType.getInvestmentName());
    }

    @Override
    public CategoryRespnose delete(Long id) {
        InvestmentType type = repository.getById(id);
        if (type == null) {
            throw new BadRequestException("Investment type doesn't exist");
        }
        if (type.isCommon()) {
            throw new BadRequestException("Default category cannot be deleted");
        }
        type.getInvestments().forEach(investmentItem -> {
            investmentRepository.delete(investmentItem);
        });
        repository.delete(type);
        return new CategoryRespnose(type.getInvestmentTypeId(), type.getInvestmentName());
    }
}
