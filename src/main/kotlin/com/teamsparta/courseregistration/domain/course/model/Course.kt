package com.teamsparta.courseregistration.domain.course.model

import com.teamsparta.courseregistration.domain.course.dto.CourseResponse
import com.teamsparta.courseregistration.domain.courseapplication.model.CourseApplicants
import com.teamsparta.courseregistration.domain.lecture.model.Lecture
import jakarta.persistence.*

@Entity
@Table(name = "course")
class Course(
    @Column(name = "title", nullable = false)
    var title: String,

    @Column(name = "description", nullable = true)
    var description: String? = null,

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    var status : CourseStatus = CourseStatus.OPEN,

    @Column(name = "max_applicants", nullable = false)
    val maxApplicants : Int = 30,

    @Column(name = "num_applicants", nullable = false)
    var numApplicants : Int = 0,

    // ---------------------------
    // 양방향 설정
//    @OneToMany(mappedBy = "course", fetch = FetchType.LAZY, cascade = [CascadeType.ALL], orphanRemoval = true)
//    var lectures : MutableList<Lecture> = mutableListOf(),
    // --------------------------------

    // ----------------------
    // 1 : N 단방향 설정
//    @OneToMany(fetch = FetchType.LAZY, cascade = [CascadeType.ALL], orphanRemoval = true)
//    @JoinColumn(name = "course_id")
//    var lectures : MutableList<Lecture> = mutableListOf(),

    @OneToMany(mappedBy = "course", cascade = [CascadeType.ALL], orphanRemoval = true, fetch = FetchType.LAZY)
    var courseApplicants : MutableList<CourseApplicants> = mutableListOf()


) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id : Long? = null

    fun isFull() : Boolean {
        return numApplicants >= maxApplicants
    }

    fun isClosed(): Boolean {
        return status == CourseStatus.CLOSED
    }

    fun close() {
        status = CourseStatus.CLOSED
    }

    fun addApplicant() {
        numApplicants += 1
    }

//    fun addLecture(lecture: Lecture) {
//        lectures.add(lecture)
//    }
//
//    fun removeLecture(lecture: Lecture) {
//        lectures.remove(lecture)
//    }

    fun addCourseApplication(courseApplication: CourseApplicants) {
        courseApplicants.add(courseApplication)
    }



}

fun Course.toResponse(): CourseResponse {
    return CourseResponse(
        id = id!!,
        title = title,
        description = description,
        status = status.name,
        maxApplicants = maxApplicants,
        numApplicants = numApplicants
    )
}