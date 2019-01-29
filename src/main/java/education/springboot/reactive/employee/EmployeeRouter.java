package education.springboot.reactive.employee;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class EmployeeRouter {


    @Bean
    public RouterFunction<ServerResponse> routes(EmployeeHandler handler) {
        return route(GET("/employees"), handler::all)
                .andRoute(POST("/employees"), handler::create)
                .andRoute(GET("/employees/{id}"), handler::find)
                .andRoute(PUT("/employees/{id}"), handler::update)
                .andRoute(DELETE("/employees/{id}"), handler::delete)
                ;
    }
}
