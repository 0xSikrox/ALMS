package dev.library.backend.controllers;

import dev.library.backend.dto.mappers.BookMapperService;
import dev.library.backend.dto.requests.BookRequestDto;
import dev.library.backend.dto.response.BookResponseDto;
import dev.library.backend.models.Book;
import dev.library.backend.repositories.BookRepository;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import dev.library.backend.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/books")
public class BookController {
    private final BookService bookService;
    @Autowired
    public BookController(BookService bookService) {
       this.bookService = bookService;
    }
    @GetMapping("/")
    public ResponseEntity<Page<BookResponseDto>> getBooks(
            @RequestParam(defaultValue = "0") int page ,
            @RequestParam(defaultValue = "10") int size ,
            @RequestParam(defaultValue = "title") String sortBy,
            @RequestParam(defaultValue = "asc") String direction
    ) {
        return new ResponseEntity<>(this.bookService.getBooks(page , size , sortBy , direction), HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<BookResponseDto> getBook(@PathVariable Long id) {
        return new ResponseEntity<>(bookService.getBook(id), HttpStatus.OK);
    }
    @GetMapping("/search={search}")
    public ResponseEntity<List<BookResponseDto>> getBookBySearch(@PathVariable String search) {
        return new ResponseEntity<>(this.bookService.getBooksBySearch(search), HttpStatus.OK);
    }
    @PreAuthorize("hasRole('LIBRARIAN')")
    @PostMapping(value = "/create" , consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<BookResponseDto> createBook(
            @ModelAttribute BookRequestDto bookRequestDto ,
            @RequestPart("file") MultipartFile file
    ) throws IOException {

        if (file.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(this.bookService.createBook(bookRequestDto , file) , HttpStatus.CREATED);
    }
//    @PreAuthorize("hasRole('LIBRARIAN')")
//    @PutMapping("/update/{id}")
//    public Book updateBook( @RequestBody Book book) {
//        return this.bookService.updateBook(book);
//    }
//    @PreAuthorize("hasRole('LIBRARIAN')")
//    @DeleteMapping("/delete/{id}")
//    public void deleteBook(@PathVariable Long id) {
//        this.bookService.deleteBook(id);
//    }
}
