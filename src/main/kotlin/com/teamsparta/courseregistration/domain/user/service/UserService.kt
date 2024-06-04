package com.teamsparta.courseregistration.domain.user.service

import com.teamsparta.courseregistration.domain.user.dto.SignUpRequest
import com.teamsparta.courseregistration.domain.user.dto.UpdateProfileRequest
import com.teamsparta.courseregistration.domain.user.dto.UserResponse
import org.springframework.http.ResponseEntity

interface UserService {
    fun signUp(request : SignUpRequest) : UserResponse

    fun updateProfile(userId : Long, request : UpdateProfileRequest) : UserResponse
    fun getUser(userId : Long): UserResponse
    fun getUserAll() : List<UserResponse>
}