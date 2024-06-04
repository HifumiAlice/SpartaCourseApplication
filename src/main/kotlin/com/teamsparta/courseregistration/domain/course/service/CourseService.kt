package com.teamsparta.courseregistration.domain.course.service

import com.teamsparta.courseregistration.domain.course.dto.*
import com.teamsparta.courseregistration.domain.courseapplication.dto.*
import com.teamsparta.courseregistration.domain.lecture.dto.*


interface CourseService {

    fun getAllCourseList() : List<CourseResponse>

    fun getCourseById(courseId : Long) : CourseResponse

    fun createCourse(request : CreateCourseRequest) : CourseResponse

    fun updateCourse (courseId : Long, request : UpdateCourseRequest) : CourseResponse

    fun deleteCourse(courseId : Long)

    fun createLecture(courseId : Long, request : CreateLecture) : LectureResponse

    fun getLectureAllList(courseId : Long) : List<LectureResponse>

    fun getLectureById(lectureId : Long, courseId : Long) : LectureResponse

    fun updateLecture(courseId : Long, lectureId : Long, request : UpdateLecture) : LectureResponse

    fun deleteLecture (lectureId : Long, courseId : Long)

    fun applyCourse(courseId: Long, request: ApplyCourseRequest): CourseApplicationResponse

    fun getCourseApplication(
        courseId: Long,
        applicationId: Long
    ): CourseApplicationResponse

    fun getCourseApplicationList(courseId: Long): List<CourseApplicationResponse>

    fun updateCourseApplicationStatus(
        courseId: Long,
        applicationId: Long,
        request: UpdateApplicationStatusRequest
    ): CourseApplicationResponse

}