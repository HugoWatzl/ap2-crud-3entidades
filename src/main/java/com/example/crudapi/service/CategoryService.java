package com.example.crudapi.service;

import com.example.crudapi.dto.CategoryRequestDTO;
import com.example.crudapi.exception.BusinessException;
import com.example.crudapi.exception.ResourceNotFoundException;
import com.example.crudapi.model.Category;
import com.example.crudapi.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    @Transactional(readOnly = true)
    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Category findById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Categoria não encontrada com id: " + id));
    }

    @Transactional
    public Category create(CategoryRequestDTO dto) {
        if (categoryRepository.existsByNameIgnoreCase(dto.getName())) {
            throw new BusinessException("Já existe uma categoria com o nome: " + dto.getName());
        }
        Category category = Category.builder()
                .name(dto.getName())
                .description(dto.getDescription())
                .build();
        return categoryRepository.save(category);
    }

    @Transactional
    public Category update(Long id, CategoryRequestDTO dto) {
        Category category = findById(id);
        category.setName(dto.getName());
        category.setDescription(dto.getDescription());
        return categoryRepository.save(category);
    }

    @Transactional
    public void delete(Long id) {
        Category category = findById(id);
        categoryRepository.delete(category);
    }
}
