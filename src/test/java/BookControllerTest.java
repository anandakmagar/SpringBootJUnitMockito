import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.testing.junits.controller.BookController;
import com.testing.junits.model.Book;
import com.testing.junits.repository.BookRepository;
import com.testing.junits.service.BookService;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.match.MockRestRequestMatchers;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.*;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static sun.nio.cs.Surrogate.is;

@RunWith(MockitoJUnitRunner.class)
public class BookControllerTest {
    private MockMvc mockMvc;

    ObjectMapper objectMapper = new ObjectMapper();
    ObjectWriter objectWriter = objectMapper.writer();

    @Mock
    private BookService bookService;

    @InjectMocks
    private BookController bookController;

    Book book1 = new Book(1L, "World War I", "What happened in WWI", 5);
    Book book2 = new Book(2L, "World War II", "What happened in WWII", 7);
    Book book3 = new Book(3L, "Vietnam War", "What happened in Vietnam War", 6);

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(bookController).build();
    }

    @Test
    public void getAllBooks_success() throws Exception {
        List<Book> bookList = new ArrayList<>(Arrays.asList(book1, book2, book3));

        Mockito.when(bookService.getAllBooks()).thenReturn(bookList);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/book/books")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(3)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[2].name", Matchers.is("Vietnam War")));

    }

    @Test
    public void getBookById_success() throws Exception{
        Mockito.when(bookService.getBookById(book1.getBookId())).thenReturn(book1);
        mockMvc.perform(MockMvcRequestBuilders
                .get("/book/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", notNullValue()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", Matchers.is("World War I")));
    }

    @Test
    public void updateBook_success() throws Exception{
        Book updatedBook = Book.builder()
                .bookId(1L)
                .name("Updated Book Name")
                .summary("Updated Summary")
                .rating(1).build();
        Mockito.when(bookService.getBookById(book1.getBookId())).thenReturn(book1);
        Mockito.when(bookService.updateBook(updatedBook)).thenReturn(updatedBook);
        String updatedContent = objectWriter.writeValueAsString(updatedBook);
        MockHttpServletRequestBuilder mockHttpServletRequestBuilder = MockMvcRequestBuilders.put("/book/update")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(updatedContent);
        mockMvc.perform(mockHttpServletRequestBuilder)
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", notNullValue()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", Matchers.is("Updated Book Name")));
    }

    @Test
    public void deleteBookById_success() throws Exception {
        Mockito.when(bookService.getBookById(book2.getBookId())).thenReturn(book2);
        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/book/2")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void addBook_success() throws Exception{
        Book book = Book.builder()
                .bookId(4L)
                .name("Introduction to C++")
                .summary("Nice programming book for beginners")
                .rating(4)
                .build();
        Mockito.when(bookService.addBook(book)).thenReturn(book);
        String content = objectWriter.writeValueAsString(book);
        MockHttpServletRequestBuilder mockHttpServletRequestBuilder = MockMvcRequestBuilders.post("/book/add")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(content);
        mockMvc.perform(mockHttpServletRequestBuilder)
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", notNullValue()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", Matchers.is("Introduction to C++")));
    }
}

