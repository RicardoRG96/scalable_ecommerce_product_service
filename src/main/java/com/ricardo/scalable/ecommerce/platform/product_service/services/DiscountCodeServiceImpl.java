package com.ricardo.scalable.ecommerce.platform.product_service.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ricardo.scalable.ecommerce.platform.libs_common.entities.Discount;
import com.ricardo.scalable.ecommerce.platform.product_service.entities.DiscountCode;
import com.ricardo.scalable.ecommerce.platform.product_service.repositories.DiscountCodeRepository;
import com.ricardo.scalable.ecommerce.platform.product_service.repositories.DiscountRepository;
import com.ricardo.scalable.ecommerce.platform.product_service.repositories.dto.DiscountCodeDto;

@Service
public class DiscountCodeServiceImpl  implements DiscountCodeService{

    @Autowired
    private DiscountCodeRepository discountCodeRepository;

    @Autowired
    private DiscountRepository discountRepository;

    @Override
    public Optional<DiscountCode> findById(Long id) {
        return discountCodeRepository.findById(id);
    }

    @Override
    public Optional<DiscountCode> findByCode(String code) {
        return discountCodeRepository.findByCode(code);
    }

    @Override
    public Optional<List<DiscountCode>> findByDiscountId(Long discountId) {
        return discountCodeRepository.findByDiscountId(discountId);
    }

    @Override
    public Optional<DiscountCode> findByCodeAndDiscountId(String code, Long discountId) {
        return discountCodeRepository.findByCodeAndDiscountId(code, discountId);
    }

    @Override
    public Optional<List<DiscountCode>> findByUsageLimit(Integer usageLimit) {
        return discountCodeRepository.findByUsageLimit(usageLimit);
    }

    @Override
    public Optional<List<DiscountCode>> findByUsedCount(Integer usedCount) {
        return discountCodeRepository.findByUsedCount(usedCount);
    }

    @Override
    public List<DiscountCode> findAll() {
        return (List<DiscountCode>) discountCodeRepository.findAll();
    }

    @Override
    public Optional<DiscountCode> save(DiscountCodeDto discountCode) {
        Optional<Discount> discount = discountRepository.findById(discountCode.getDiscountId());

        if (discount.isPresent()) {
            DiscountCode newDiscountCode = new DiscountCode();
            newDiscountCode.setCode(discountCode.getCode());
            newDiscountCode.setDiscount(discount.orElseThrow());
            newDiscountCode.setUsageLimit(discountCode.getUsageLimit());
            newDiscountCode.setUsedCount(0);

            return Optional.of(discountCodeRepository.save(newDiscountCode));
        }
        return Optional.empty();
    }

    @Override
    public Optional<DiscountCode> update(DiscountCodeDto discountCode, Long id) {
        Optional<DiscountCode> discountCodeToUpdate = discountCodeRepository.findById(id);
        Optional<Discount> discount = discountRepository.findById(discountCode.getDiscountId());

        if (discountCodeToUpdate.isPresent() && discount.isPresent()) {
            DiscountCode dbDiscountCode = discountCodeToUpdate.orElseThrow();
            dbDiscountCode.setCode(discountCode.getCode());
            dbDiscountCode.setDiscount(discount.orElseThrow());
            dbDiscountCode.setUsageLimit(discountCode.getUsageLimit());

            return Optional.of(discountCodeRepository.save(dbDiscountCode));
        }

        return Optional.empty();
    }

    @Override
    public void delete(Long id) {
        discountCodeRepository.deleteById(id);
    }

}
