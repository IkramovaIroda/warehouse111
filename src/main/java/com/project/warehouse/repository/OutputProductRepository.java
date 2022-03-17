package com.project.warehouse.repository;

import com.project.warehouse.entity.Output;
import com.project.warehouse.entity.OutputProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

public interface OutputProductRepository extends JpaRepository<OutputProduct, Long> {
    List<OutputProduct> findAllByOutput_ActiveTrue();
    List<OutputProduct> findAllByOutput_ActiveTrueAndOutput_DateBetween(LocalDate output_date, LocalDate output_date2);

    @Query(value =
            "select output_product.id, p.id as product_id, sum(amount) as amount, sum(price*output_product.amount) as price, output_product.output_id from output_product" +
                    "            join output o on o.id = output_product.output_id" +
                    "            join product p on p.id = output_product.product_id" +
                    "            where (o.date between ?1 and ?2)" +
                    "            group by p.id, output_product.id order by (amount*price) desc limit ?3",
            nativeQuery = true
    )
    List<OutputProduct> getOutputProductWithLimit(LocalDate from, LocalDate to, int limit);
    List<OutputProduct> findAllByOutput_Id(Long id);
    List<OutputProduct> findAllByOutput_IdAndOutputActive(Long id, boolean output_active);
}