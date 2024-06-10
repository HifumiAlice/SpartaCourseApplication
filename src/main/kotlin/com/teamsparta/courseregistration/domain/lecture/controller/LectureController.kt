package com.teamsparta.courseregistration.domain.lecture.controller

import com.teamsparta.courseregistration.domain.course.service.CourseService
import com.teamsparta.courseregistration.domain.lecture.dto.CreateLecture
import com.teamsparta.courseregistration.domain.lecture.dto.LectureResponse
import com.teamsparta.courseregistration.domain.lecture.dto.UpdateLecture
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*

@RequestMapping("/courses/{courseId}/lectures")
@RestController
class LectureController(private val courseService: CourseService) {
    @PreAuthorize("hasRole('TUTOR')")
    @PostMapping()
    fun createLecture(
        @PathVariable courseId: Long,
        @RequestBody createLecture: CreateLecture
    ): ResponseEntity<LectureResponse> {
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(courseService.createLecture(courseId, createLecture))
    }

    @PreAuthorize("hasRole('TUTOR') or hasRole('STUDENT')")
    @GetMapping()
    fun getLectureList(@PathVariable courseId: Long): ResponseEntity<List<LectureResponse>> {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(courseService.getLectureAllList(courseId))
    }

    @PreAuthorize("hasRole('TUTOR') or hasRole('STUDENT')")
    @GetMapping("/{lectureId}")
    fun getLecture(@PathVariable courseId: Long, @PathVariable lectureId: Long): ResponseEntity<LectureResponse> {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(courseService.getLectureById(lectureId, courseId))
    }

    @PreAuthorize("hasRole('TUTOR')")
    @PutMapping("/{lectureId}")
    fun updateLecture(
        @PathVariable courseId: Long,
        @PathVariable lectureId: Long,
        @RequestBody updateLecture: UpdateLecture
    ): ResponseEntity<LectureResponse> {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(courseService.updateLecture(courseId, lectureId, updateLecture))
    }

    @PreAuthorize("hasRole('TUTOR')")
    @DeleteMapping("/{lectureId}")
    fun deleteLecture(@PathVariable courseId: Long, @PathVariable lectureId: Long): ResponseEntity<Unit> {
        return ResponseEntity
            .status(HttpStatus.NO_CONTENT)
            .body(courseService.deleteLecture(lectureId, courseId))
//            .build()

    }
}