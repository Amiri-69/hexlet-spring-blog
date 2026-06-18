package io.hexlet.spring;

import io.hexlet.spring.dto.UserPatchDTO;
import io.hexlet.spring.dto.UserUpdateDTO;
import io.hexlet.spring.model.User;
import io.hexlet.spring.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import io.hexlet.spring.dto.UserDTO;
import io.hexlet.spring.mapper.UserMapper;
import io.hexlet.spring.dto.UserCreateDTO;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserController(UserRepository userRepository,
                          UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @GetMapping
    public List<UserDTO> index() {
        return userRepository.findAll()
                .stream()
                .map(userMapper::toDTO)
                .toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> show(@PathVariable Long id) {
        return userRepository.findById(id)
                .map(user -> ResponseEntity.ok(
                        userMapper.toDTO(user)
                ))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<UserDTO> create(
            @Valid @RequestBody UserCreateDTO dto){

        User user = userMapper.toEntity(dto);

        User saved = userRepository.save(user);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(userMapper.toDTO(saved));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> update(
            @PathVariable Long id,
            @Valid @RequestBody UserUpdateDTO dto) {

        return userRepository.findById(id)
                .map(user -> {

                    userMapper.updateEntityFromDTO(
                            dto,
                            user
                    );

                    User saved = userRepository.save(user);

                    return ResponseEntity.ok(
                            userMapper.toDTO(saved)
                    );
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {

        if (!userRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        userRepository.deleteById(id);

        return ResponseEntity.noContent().build();
    }
    @PatchMapping("/{id}")
    public ResponseEntity<UserDTO> patch(
            @PathVariable Long id,
            @RequestBody UserPatchDTO dto) {

        return userRepository.findById(id)
                .map(user -> {

                    userMapper.updateFromPatch(
                            dto,
                            user
                    );

                    User saved =
                            userRepository.save(user);

                    return ResponseEntity.ok(
                            userMapper.toDTO(saved)
                    );
                })
                .orElse(
                        ResponseEntity.notFound()
                                .build()
                );
    }

}