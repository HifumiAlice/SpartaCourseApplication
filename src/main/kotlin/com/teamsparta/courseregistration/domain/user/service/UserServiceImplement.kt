package com.teamsparta.courseregistration.domain.user.service

import com.teamsparta.courseregistration.domain.exception.ModelNotFoundException
import com.teamsparta.courseregistration.domain.user.dto.SignUpRequest
import com.teamsparta.courseregistration.domain.user.dto.UpdateProfileRequest
import com.teamsparta.courseregistration.domain.user.dto.UserResponse
import com.teamsparta.courseregistration.domain.user.repository.UserRepository
import com.teamsparta.courseregistration.domain.user.model.*
import jakarta.transaction.Transactional
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class UserServiceImplement (private val userRepository : UserRepository) : UserService {
    @Transactional
    override fun signUp(request: SignUpRequest): UserResponse {
        // TODO: Email이 중복되는지 확인, 중복된다면 throw IllegalStateException
        // TODO: request를 User로 변환 후 DB에 저장, 비밀번호는 저장시 암호화

        if(userRepository.existsByEmail(request.email)){
            throw IllegalStateException("Email already in use")
        }

        return userRepository.save(
            User(
                email = request.email,
                password = request.password,
                profile = Profile(
                    nickname = request.nickname
                ),
                role = when (request.role) {
                    UserRole.STUDENT.name, "student" -> UserRole.STUDENT
                    UserRole.TUTOR.name, "tutor" -> UserRole.TUTOR
                    else -> throw IllegalArgumentException("Invalid role")
                }
            )
        ).toResponse()

    }

    @Transactional
    override fun updateProfile(userId: Long, request: UpdateProfileRequest): UserResponse {
        // TODO: 만약 userId에 해당하는 User가 없다면 throw ModelNotFoundException
        // TODO: DB에서 userId에 해당하는 User를 가져와서 updateUserProfileRequest로 업데이트 후 DB에 저장, 결과를 UserResponse로 변환 후 반환

        val user = userRepository.findByIdOrNull(userId) ?: throw ModelNotFoundException("User",userId)
        user.profile = Profile (
            nickname = request.nickname
        )

        return user.toResponse()

    }

    override fun getUser(userId: Long): UserResponse {
        val user = userRepository.findByIdOrNull(userId) ?: throw ModelNotFoundException("User",userId)

        return user.toResponse()
    }

    override fun getUserAll() : List<UserResponse> {
        return userRepository.findAll().map { it.toResponse() }
    }

}