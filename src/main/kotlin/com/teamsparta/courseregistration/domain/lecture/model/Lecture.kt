package com.teamsparta.courseregistration.domain.lecture.model

import com.teamsparta.courseregistration.domain.course.model.Course
import com.teamsparta.courseregistration.domain.lecture.dto.LectureResponse
import jakarta.persistence.*


@Entity
@Table(name = "lecture")
class Lecture(

    @Column(name = "title", nullable = false)
    var title : String,

    @Column(name = "video_url", nullable = false)
    var videoUrl : String,

    // --------------------
    // 양방향
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "course_id", nullable = false)
//    var course : Course

    // ----------
    // N : 1 단방향
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id", nullable = false)
    var course : Course


) {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id : Long? = null


}

fun Lecture.toResponse () : LectureResponse {
    return LectureResponse(
        id = id!!,
        title = title,
        videoUrl = videoUrl
    )
}