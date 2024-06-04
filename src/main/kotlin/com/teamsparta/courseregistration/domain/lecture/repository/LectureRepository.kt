package com.teamsparta.courseregistration.domain.lecture.repository

import com.teamsparta.courseregistration.domain.lecture.model.Lecture
import org.springframework.data.jpa.repository.JpaRepository

interface LectureRepository : JpaRepository<Lecture, Long> {
    fun findAllByCourseId(courseId: Long): List<Lecture>

//    fun findByCourseIdAndId(courseId : Long, lectureId : Long) : Lecture?
}