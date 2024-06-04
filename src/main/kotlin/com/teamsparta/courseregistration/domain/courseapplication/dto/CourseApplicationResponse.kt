package com.teamsparta.courseregistration.domain.courseapplication.dto

import com.teamsparta.courseregistration.domain.course.dto.CourseResponse
import com.teamsparta.courseregistration.domain.user.dto.UserResponse

data class CourseApplicationResponse(
    val id : Long,
    val user : UserResponse,
    val course : CourseResponse,
    val status : String
)
