package com.teamsparta.courseregistration.domain.courseapplication.repository

import com.teamsparta.courseregistration.domain.courseapplication.model.CourseApplicants
import org.springframework.data.jpa.repository.JpaRepository

interface CourseApplicationRepository : JpaRepository<CourseApplicants,Long> {
    fun existsByCourseIdAndUserId(courseId : Long, userId: Long) : Boolean

    fun findByCourseIdAndId(courseId: Long, id: Long): CourseApplicants?
}