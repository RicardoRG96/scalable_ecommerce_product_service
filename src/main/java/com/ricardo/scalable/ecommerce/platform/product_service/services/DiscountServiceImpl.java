package com.ricardo.scalable.ecommerce.platform.product_service.services;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ricardo.scalable.ecommerce.platform.libs_common.entities.ProductSku;
import com.ricardo.scalable.ecommerce.platform.libs_common.enums.DiscountType;
import com.ricardo.scalable.ecommerce.platform.libs_common.entities.Discount;
import com.ricardo.scalable.ecommerce.platform.product_service.repositories.DiscountRepository;
import com.ricardo.scalable.ecommerce.platform.product_service.repositories.ProductSkuRepository;
import com.ricardo.scalable.ecommerce.platform.product_service.repositories.dto.DiscountDto;

@Service
public class DiscountServiceImpl implements DiscountService {

    @Autowired
    private DiscountRepository discountRepository;

    @Autowired
    private ProductSkuRepository productSkuRepository;

    @Override
    public Optional<Discount> findById(Long id) {
        return discountRepository.findById(id);
    }

    @Override
    public Optional<List<Discount>> findByProductSkuId(Long productSkuId) {
        return discountRepository.findByProductSkuId(productSkuId);
    }

    @Override
    public Optional<List<Discount>> findByDiscountType(String discountType) {
        return discountRepository.findByDiscountType(discountType);
    }

    @Override
    public Optional<List<Discount>> findByDiscountValue(Double discountValue) {
        return discountRepository.findByDiscountValue(discountValue);
    }

    @Override
    public Optional<Discount> verifyValidityPeriod(Long discountId) {
        return discountRepository.verifyValidityPeriod(discountId);
    }

    @Override
    public int checkOverlappingDiscount(Long productSkuId, LocalDateTime startDate, LocalDateTime endDate) {
        return discountRepository.checkOverlappingDiscount(productSkuId, startDate, endDate);
    }

    @Override
    public List<Discount> findAll() {
        return (List<Discount>) discountRepository.findAll();
    }

    @Override
    public Optional<Discount> save(DiscountDto discount) {
        List<Optional<ProductSku>> productSkus = discount.getProductSkuIds().stream()
                    .map(prodSkuId -> productSkuRepository.findById(prodSkuId))
                    .collect(Collectors.toList());

        boolean areAllProductSkusPresent = productSkus.stream().allMatch(Optional::isPresent);
        boolean isProductSkusListEmpty = productSkus.isEmpty();

        if (areAllProductSkusPresent && !isProductSkusListEmpty) {
            Discount newDiscount = new Discount();
            newDiscount.setDiscountType(DiscountType.valueOf(discount.getDiscountType().toUpperCase()));
            newDiscount.setDiscountValue(discount.getDiscountValue());
            newDiscount.setStartDate(Timestamp.valueOf(discount.getStartDate()));
            newDiscount.setEndDate(Timestamp.valueOf(discount.getEndDate()));
            newDiscount.setIsActive(discount.getIsActive());
            newDiscount.setProductSkus(productSkus.stream().map(Optional::orElseThrow).collect(Collectors.toList()));

            return Optional.of(discountRepository.save(newDiscount));
        }
        return Optional.empty();
    }

    @Override
    public Optional<Discount> update(DiscountDto discount, Long id) {
        Optional<Discount> discountToUpdate = discountRepository.findById(id);
        List<Optional<ProductSku>> productSkus = discount.getProductSkuIds().stream()
                    .map(prodSkuId -> productSkuRepository.findById(prodSkuId))
                    .collect(Collectors.toList());

        boolean isDiscountToUpdatePresent = discountToUpdate.isPresent();
        boolean areAllProductSkusPresent = productSkus.stream().allMatch(Optional::isPresent);
        boolean isProductSkusListEmpty = productSkus.isEmpty();

        if (isDiscountToUpdatePresent && 
            areAllProductSkusPresent && 
            !isProductSkusListEmpty
        ) {
            Discount dbDiscount = discountToUpdate.orElseThrow();
            dbDiscount.setDiscountType(DiscountType.valueOf(discount.getDiscountType().toUpperCase()));
            dbDiscount.setDiscountValue(discount.getDiscountValue());
            dbDiscount.setStartDate(Timestamp.valueOf(discount.getStartDate()));
            dbDiscount.setEndDate(Timestamp.valueOf(discount.getEndDate()));
            dbDiscount.setIsActive(discount.getIsActive());
            dbDiscount.setProductSkus(productSkus.stream().map(Optional::orElseThrow).collect(Collectors.toList()));

            return Optional.of(discountRepository.save(dbDiscount));
        }
        return Optional.empty();
    }

    @Override
    public void delete(Long id) {
        discountRepository.deleteById(id);
    }

}
