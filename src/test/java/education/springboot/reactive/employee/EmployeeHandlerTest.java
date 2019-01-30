package education.springboot.reactive.employee;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureWebTestClient
public class EmployeeHandlerTest {

    @Autowired
    private WebTestClient client;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Before
    public void setUp() throws Exception {
        employeeRepository.save(new Employee("1", "Petros", "Stergioulas")).subscribe();
    }

    @After
    public void tearDown() throws Exception {
        employeeRepository.deleteAll().subscribe();
    }

    @Test
    public void all() {
        client
                .get()
                .uri("/employees")
                .exchange()
                .expectStatus()
                .isOk()
        ;

    }

    @Test
    public void create() {
        client
                .post()
                .uri("/employees")
                .body(Mono.just(new Employee("2", "first", "last")), Employee.class)
                .exchange()
                .expectStatus()
                .isCreated()
        ;
    }

    @Test
    public void find() {
        client
                .get()
                .uri("/employees/{id}", "1")
                .exchange()
                .expectStatus()
                .isOk()
        ;
    }

    @Test
    public void update() {
        client
                .put()
                .uri("/employees/{id}", "1")
                .body(Mono.just(new Employee("1", "Petros", "wut")), Employee.class)
                .exchange()
                .expectStatus()
                .isNoContent()
        ;

    }

    @Test
    public void delete() {
        client
                .delete()
                .uri("/employees/{id}", "1")
                .exchange()
                .expectStatus()
                .isNoContent()
        ;
    }

}