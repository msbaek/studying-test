package pe.msbaek.studyingtest.testcontainers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/api/posts")
@RestController
public class PostController {
    private final PostRepository repository;

    @GetMapping("")
    public List<Post> getPosts() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public Post getPostById(@PathVariable Integer id) {
        return repository.findById(id)
                .orElseThrow(PostNotFoundException::new);
    }

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public Post createPost(@RequestBody @Valid Post post) {
        return repository.save(post);
    }

    @PutMapping("/{id}")
    public Post updatePost(@PathVariable Integer id, @RequestBody @Valid Post post) {
        Post found = repository.findById(id)
                .orElseThrow(PostNotFoundException::new);
        found.setUserId(post.getUserId())
                .setTitle(post.getTitle())
                .setBody(post.getBody());
        return repository.save(found);
    }
}