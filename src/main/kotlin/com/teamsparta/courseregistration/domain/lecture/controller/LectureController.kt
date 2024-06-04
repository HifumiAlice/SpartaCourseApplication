package com.teamsparta.courseregistration.domain.lecture.controller

import com.teamsparta.courseregistration.domain.course.service.CourseService
import com.teamsparta.courseregistration.domain.lecture.dto.CreateLecture
import com.teamsparta.courseregistration.domain.lecture.dto.LectureResponse
import com.teamsparta.courseregistration.domain.lecture.dto.UpdateLecture
import org.springframework.http.HttpStatus
import org.springframework.http.RequestEntity
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.bind.annotation.RestController

@RequestMapping("/courses/{courseId}/lectures")
@RestController
class LectureController(private val courseService: CourseService) {

    @PostMapping()
    fun createLecture(@PathVariable courseId : Long , @RequestBody createLecture : CreateLecture) : ResponseEntity<LectureResponse> {
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(courseService.createLecture(courseId, createLecture))
    }

    @GetMapping()
    fun getLectureList(@PathVariable courseId : Long) : ResponseEntity<List<LectureResponse>> {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(courseService.getLectureAllList(courseId))
    }

    @GetMapping("/{lectureId}")
    fun getLecture(@PathVariable courseId: Long, @PathVariable lectureId : Long) : ResponseEntity<LectureResponse> {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(courseService.getLectureById(lectureId, courseId))
    }

    @PutMapping("/{lectureId}")
    fun updateLecture(@PathVariable courseId : Long, @PathVariable lectureId : Long, @RequestBody updateLecture : UpdateLecture) : ResponseEntity<LectureResponse> {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(courseService.updateLecture(courseId, lectureId, updateLecture))
    }

    @DeleteMapping("/{lectureId}")
    fun deleteLecture(@PathVariable courseId : Long, @PathVariable lectureId : Long) : ResponseEntity<Unit> {
        return ResponseEntity
            .status(HttpStatus.NO_CONTENT)
            .body(courseService.deleteLecture(lectureId, courseId))
//            .build()

    }
}