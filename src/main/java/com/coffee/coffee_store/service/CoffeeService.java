package com.coffee.coffee_store.service;

import com.coffee.coffee_store.domain.CoffeeEntity;
import com.coffee.coffee_store.execption.CoffeeAlreadyExist;
import com.coffee.coffee_store.execption.CoffeeNotFoundException;
import com.coffee.coffee_store.model.CoffeeDTO;
import com.coffee.coffee_store.repository.CoffeeRepository;
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

    public CoffeeDTO createCoffee(CoffeeDTO coffeeDTO) {
        Optional<CoffeeEntity> existingCoffee = coffeeRepository.findByName(coffeeDTO.getName());
        if (existingCoffee.isPresent()) {
            throw new CoffeeAlreadyExist(coffeeDTO.getName());
        }

        CoffeeEntity coffeeEntity = CoffeeEntity.builder()
                .name(coffeeDTO.getName())
                .price(coffeeDTO.getPrice())
                .build();

        CoffeeEntity savedCoffeeEntity = coffeeRepository.save(coffeeEntity);
        return savedCoffeeEntity.toDTO();
    }

    public CoffeeDTO updateCoffee(CoffeeDTO coffeeDTO) {
        Optional<CoffeeEntity> coffeeOptional = coffeeRepository.findById(coffeeDTO.getId());
        if (coffeeOptional.isPresent()) {
            CoffeeEntity coffeeEntity = coffeeOptional.get();
            coffeeEntity.setName(coffeeDTO.getName());
            coffeeEntity.setPrice(coffeeDTO.getPrice());
            CoffeeEntity updatedCoffeeEntity = coffeeRepository.save(coffeeEntity);
            return updatedCoffeeEntity.toDTO();
        } else {
            throw new CoffeeNotFoundException(coffeeDTO.getId());
        }
    }

    public void deleteCoffee(Long id) {
        coffeeRepository.findById(id)
                .orElseThrow(() -> new CoffeeNotFoundException(id));
        coffeeRepository.deleteById(id);
    }
}
