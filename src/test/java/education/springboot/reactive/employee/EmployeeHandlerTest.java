package education.springboot.reactive.employee;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;


// TODO

@SpringBootTest
@WebFluxTest
public class EmployeeHandlerTest {

    @Autowired
    private WebTestClient client;

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void all() {
    }

    @Test
    public void create() {
    }

    @Test
    public void find() {
    }

    @Test
    public void update() {
    }

    @Test
    public void delete() {
    }
}