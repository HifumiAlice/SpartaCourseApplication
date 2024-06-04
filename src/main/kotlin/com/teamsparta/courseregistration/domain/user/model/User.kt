package com.teamsparta.courseregistration.domain.user.model

import com.teamsparta.courseregistration.domain.courseapplication.model.CourseApplicants
import com.teamsparta.courseregistration.domain.user.dto.UserResponse
import jakarta.persistence.*


@Entity
@Table(name = "app_user")
class User (

    @Column(name = "email", nullable = false)
    val email: String,

    @Column(name = "password", nullable = false)
    val password: String,

    @Embedded
    var profile: Profile,

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    val role: UserRole,

    @OneToMany(mappedBy = "user", cascade = [(CascadeType.ALL)], fetch = FetchType.LAZY, orphanRemoval = true)
    val courseApplications : MutableList<CourseApplicants> = mutableListOf()
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null
}

fun User.toResponse() : UserResponse = UserResponse(id = id!!, email = email, nickname = profile.nickname, role = role.name )
