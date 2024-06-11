package com.teamsparta.courseregistration.domain.course.repository

import com.teamsparta.courseregistration.domain.course.model.Course
import com.teamsparta.courseregistration.domain.course.model.QCourse
import com.teamsparta.courseregistration.infra.QueryDslSupport
import org.springframework.stereotype.Repository

@Repository
class CourseRepositoryImpl: CustomQueryRepository, QueryDslSupport()  {
    private val course = QCourse.course

    override fun searchCourseListByTitle(title: String) : List<Course> {
        return queryFactory.select(course).from(course)
            .where(course.title.containsIgnoreCase(title))
            .fetch()
    }

}