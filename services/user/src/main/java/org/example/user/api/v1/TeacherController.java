package org.example.user.api.v1;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.example.common.model.UserResponse;
import org.example.user.model.request.CreateUserRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("api/users/v1/teacher")
@Log4j2
@AllArgsConstructor
public class TeacherController {
//    @PostMapping
//    public Mono<UserResponse> create(@RequestBody CreateUserRequest request) {
//        log.debug("{}", request);
//        return userService.create(request);
//    }
}
