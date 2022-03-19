package com.project.warehouse;

import com.project.warehouse.entity.*;
import com.project.warehouse.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.DispatcherServlet;

@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {

    @Value("${spring.sql.init.mode}")
    private String initialMode;

    final CategoryRepository categoryRepository;
    final WarehouseRepository warehouseRepository;
    final MeasurementRepository measurementRepository;
    final ProductRepository productRepository;
    final CurrencyRepository currencyRepository;
    final SupplierRepository supplierRepository;
    final ClientRepository clientRepository;
    final UserRepository userRepository;

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
            User user=new User();
            user.setFirstName("Umidjon");
            user.setLastName("Tojiboyev");
            user.setPassword("911368169umid");
            user.setPhoneNumber("+998990472436");
            user.setCode(1L);
            userRepository.save(user);
        }

    }

}
