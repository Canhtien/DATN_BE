package com.alibou.security.service.JPA;

import com.alibou.security.entity.DiscountApplication;
import com.alibou.security.repository.DiscountApplicationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class DiscountApplicationServiceJPA {

    @Autowired
    private DiscountApplicationRepository discountApplicationRepository;

    public DiscountApplication createDiscountApplication(DiscountApplication discountApplication) {
        return discountApplicationRepository.save(discountApplication);
    }
}
