package io.hexlet.spring;

import io.hexlet.spring.dto.TagCreateDTO;
import io.hexlet.spring.dto.TagDTO;
import io.hexlet.spring.dto.TagUpdateDTO;
import io.hexlet.spring.mapper.TagMapper;
import io.hexlet.spring.model.Tag;
import io.hexlet.spring.repository.TagRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tags")
public class TagsController {

    private final TagRepository tagRepository;
    private final TagMapper tagMapper;

    public TagsController(
            TagRepository tagRepository,
            TagMapper tagMapper
    ) {
        this.tagRepository = tagRepository;
        this.tagMapper = tagMapper;
    }

    @GetMapping
    public List<TagDTO> index() {
        return tagRepository.findAll()
                .stream()
                .map(tagMapper::toDTO)
                .toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<TagDTO> show(
            @PathVariable Long id
    ) {
        return tagRepository.findById(id)
                .map(tag -> ResponseEntity.ok(
                        tagMapper.toDTO(tag)
                ))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TagDTO create(
            @RequestBody TagCreateDTO dto
    ) {

        Tag tag = tagMapper.toEntity(dto);

        tagRepository.save(tag);

        return tagMapper.toDTO(tag);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TagDTO> update(
            @PathVariable Long id,
            @RequestBody TagUpdateDTO dto
    ) {

        return tagRepository.findById(id)
                .map(tag -> {

                    tagMapper.updateEntityFromDTO(dto, tag);

                    tagRepository.save(tag);

                    return ResponseEntity.ok(
                            tagMapper.toDTO(tag)
                    );
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(
            @PathVariable Long id
    ) {
        tagRepository.deleteById(id);
    }
}