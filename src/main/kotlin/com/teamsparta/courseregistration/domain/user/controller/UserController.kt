package com.teamsparta.courseregistration.domain.user.controller

import com.teamsparta.courseregistration.domain.user.dto.*
import com.teamsparta.courseregistration.domain.user.service.UserService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

//@RequestMapping("/users")
@RestController
class UserController(private val userService : UserService) {

    @PostMapping("/login")
    fun signUp(@RequestBody request : LoginRequest) : ResponseEntity<LoginResponse> {
        return ResponseEntity.status(HttpStatus.OK).body(userService.login(request))
    }


    @PostMapping("/signup")
    fun signup(@RequestBody signUp : SignUpRequest) : ResponseEntity<UserResponse> {
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(userService.signUp(signUp))
    }

    @PutMapping("/users/{userId}/profile")
    fun updateProfile(@PathVariable userId: Long, @RequestBody updateProfile : UpdateProfileRequest) : ResponseEntity<UserResponse> {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(userService.updateProfile(userId, updateProfile))
    }

    @GetMapping("/users/{userId}")
    fun getUser(@PathVariable userId : Long) : ResponseEntity<UserResponse> {
        return ResponseEntity.status(HttpStatus.OK).body(userService.getUser(userId))
    }

    @GetMapping("/users")
    fun getUsers() : ResponseEntity<List<UserResponse>> {
        return ResponseEntity.status(HttpStatus.OK).body(
            userService.getUserAll()
        )
    }
}
