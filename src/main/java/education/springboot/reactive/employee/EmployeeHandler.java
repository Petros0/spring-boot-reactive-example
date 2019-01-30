package education.springboot.reactive.employee;


import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.time.Duration;

@Component
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class EmployeeHandler {

    private final EmployeeRepository repository;

    public Mono<ServerResponse> all(ServerRequest request) {
        Flux<Employee> employeeFlux = this.repository.findAll();
        return ServerResponse.ok().body(employeeFlux, Employee.class);
    }

    public Mono<ServerResponse> create(ServerRequest request) {
        Mono<Employee> employeeMono = request.bodyToMono(Employee.class);
        return employeeMono
                .flatMap(this.repository::save)
                .flatMap(employee -> ServerResponse.created(URI.create("/employees/" + employee.getId())).build());
    }

    public Mono<ServerResponse> find(ServerRequest req) {
        String id = req.pathVariable("id");
        return this.repository
                .findById(id)
                .flatMap(employee -> ServerResponse.ok().body(Mono.just(employee), Employee.class))
                .switchIfEmpty(ServerResponse.notFound().build())
                ;
    }


    public Mono<ServerResponse> update(ServerRequest req) {
        return Mono.zip(
                (data) -> {
                    Employee db = (Employee) data[0];
                    Employee e2 = (Employee) data[1];
                    db.setFirstName(e2.getFirstName());
                    db.setLastName(e2.getLastName());
                    return db;
                },
                // those are passed as parameters to the zip function
                this.repository.findById(req.pathVariable("id")), // data[0]
                req.bodyToMono(Employee.class) // data[1]
        ).cast(Employee.class) // zip returns a Mono<Employee> (the merged one)
                .flatMap(this.repository::save) // save the employee
                .flatMap(e -> ServerResponse.noContent().build())
                ;

    }

    public Mono<ServerResponse> delete(ServerRequest req) {
        return ServerResponse.noContent().build(this.repository.deleteById(req.pathVariable("id")));
    }

    public Mono<ServerResponse> sse(ServerRequest req) {
        return ServerResponse.ok()
                .contentType(MediaType.TEXT_EVENT_STREAM)
                .body(repository.findAll()
                        .delayElements(Duration.ofSeconds(1)), Employee.class);
    }

}
