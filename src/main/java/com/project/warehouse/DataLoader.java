package com.project.warehouse;

import com.project.warehouse.entity.*;
import com.project.warehouse.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {

    @Value("${spring.sql.init.mode}")
    private String initialMode;

    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    WarehouseRepository warehouseRepository;
    @Autowired
    MeasurementRepository measurementRepository;
    @Autowired
    ProductRepository productRepository;
    @Autowired
    CurrencyRepository currencyRepository;
    @Autowired
    SupplierRepository supplierRepository;
    @Autowired
    ClientRepository clientRepository;

    @Override
    public void run(String... args) throws Exception {
        if (initialMode.equals("always")) {
            Category category = categoryRepository.save(
                    new Category(1L, true, "Products", null));
            Measurement measurement = measurementRepository.save(
                    new Measurement(1L, true, "ta"));
            productRepository.save(new Product(1L, true, 2L, "Nimadir", category, measurement));
            currencyRepository.save(new Currency(1L, true, "Dollar"));
            warehouseRepository.save(new Warehouse(1L, true, "Omborxona"));
            supplierRepository.save(new Supplier(1L, true, "Umidjon", "+998990472436"));
            clientRepository.save(new Client(1L, "Umidjon", "+998903723909"));
        }
    }
}
