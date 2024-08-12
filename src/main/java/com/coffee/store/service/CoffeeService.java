package com.coffee.store.service;

import com.coffee.store.domain.CoffeeEntity;
import com.coffee.store.model.execption.CoffeeAlreadyExist;
import com.coffee.store.model.execption.CoffeeNotFoundException;
import com.coffee.store.model.CoffeeDTO;
import com.coffee.store.model.request.CoffeeCreate;
import com.coffee.store.model.request.CoffeeUpdate;
import com.coffee.store.repository.CoffeeRepository;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class CoffeeService {
    private final CoffeeRepository coffeeRepository;

    public List<CoffeeDTO> getAllCoffees(int page, int size) {
        Page<CoffeeEntity> coffeePage = coffeeRepository.findAll(PageRequest.of(page, size));
        return coffeePage.stream()
                .map(CoffeeEntity::toDTO)
                .toList();
    }

    public CoffeeDTO getCoffeeById(Long id) {
        return coffeeRepository.findById(id)
                .map(CoffeeEntity::toDTO).orElseThrow(() -> new CoffeeNotFoundException(id));
    }

    public CoffeeDTO createCoffee(CoffeeCreate create) {
        Optional<CoffeeEntity> existingCoffee = coffeeRepository.findByName(create.getName());
        if (existingCoffee.isPresent()) {
            throw new CoffeeAlreadyExist(create.getName());
        }

        CoffeeEntity coffeeEntity = CoffeeEntity.builder()
                .name(create.getName())
                .price(create.getPrice())
                .build();

        CoffeeEntity savedCoffeeEntity = coffeeRepository.save(coffeeEntity);
        return savedCoffeeEntity.toDTO();
    }

    public CoffeeDTO updateCoffee(@Valid CoffeeUpdate coffeeUpdate) {
        CoffeeEntity coffeeEntity = coffeeRepository.findById(coffeeUpdate.getId()).orElseThrow(
                () -> new CoffeeNotFoundException(coffeeUpdate.getId()));

        coffeeEntity.setName(coffeeUpdate.getName());
        coffeeEntity.setPrice(coffeeUpdate.getPrice());
        CoffeeEntity updatedCoffeeEntity = coffeeRepository.save(coffeeEntity);
        return updatedCoffeeEntity.toDTO();

    }

    public void deleteCoffee(Long id) {
        coffeeRepository.findById(id)
                .orElseThrow(() -> new CoffeeNotFoundException(id));
        coffeeRepository.deleteById(id);
    }
}
