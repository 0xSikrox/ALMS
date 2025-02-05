package dev.library.backend.models;

import java.io.Serializable;
import java.util.*;
import java.util.concurrent.CopyOnWriteArraySet;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Data
@Table(name = "books")
public class Book  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String cover;
    private String title;

    @ManyToMany
    @JoinTable(name = "book_category" , joinColumns = @JoinColumn(name="book_id") , inverseJoinColumns = @JoinColumn(name = "category_id"))
    private Set<Category> categories = Collections.unmodifiableSet(new HashSet<>());

    @JsonIgnore
    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BorrowRecord> borrowRecords = new ArrayList<>();

    private String author;
    private String description;
    private String isbn;
    private int copies;
}
