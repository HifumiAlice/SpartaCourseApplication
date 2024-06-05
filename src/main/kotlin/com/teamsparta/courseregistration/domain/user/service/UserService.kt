package com.teamsparta.courseregistration.domain.user.service

import com.teamsparta.courseregistration.domain.user.dto.*
import org.springframework.http.ResponseEntity

interface UserService {
    fun signUp(request : SignUpRequest) : UserResponse

    fun updateProfile(userId : Long, request : UpdateProfileRequest) : UserResponse
    fun getUser(userId : Long): UserResponse
    fun getUserAll() : List<UserResponse>

    fun login(request : LoginRequest) : LoginResponse
}