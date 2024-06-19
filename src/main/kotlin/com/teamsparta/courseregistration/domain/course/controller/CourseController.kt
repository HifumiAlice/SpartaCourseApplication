package com.teamsparta.courseregistration.domain.course.controller

import com.teamsparta.courseregistration.domain.course.dto.CourseResponse
import com.teamsparta.courseregistration.domain.course.dto.CreateCourseRequest
import com.teamsparta.courseregistration.domain.course.dto.UpdateCourseRequest
import com.teamsparta.courseregistration.domain.course.service.CourseService
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*

@RequestMapping("/courses")
@RestController
class CourseController(private val courseService: CourseService) {

    @GetMapping("/page")
    @PreAuthorize("hasRole('TUTOR') or hasRole('STUDENT')")
    fun getPaginatedCourseList(
        @PageableDefault(size = 15, sort = ["id"]) pageable: Pageable,
        @RequestParam(value = "status", required = false) status: String?
        ) : ResponseEntity<Page<CourseResponse>> {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(courseService.getPaginatedCourseList(pageable, status))
    }

    @PreAuthorize("hasRole('STUDENT') or hasRole('TUTOR')")
    @GetMapping("/search")
    fun searchTitle(@RequestParam(name = "title") title: String): ResponseEntity<List<CourseResponse>> {
        return ResponseEntity.ok(courseService.searchTitleList(title))
    }


    @PreAuthorize("hasRole('TUTOR') or hasRole('STUDENT')")
    @GetMapping("/{courseId}")
    fun getCourse(@PathVariable courseId: Long): ResponseEntity<CourseResponse> {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(courseService.getCourseById(courseId))
    }

    @PreAuthorize("hasRole('TUTOR') or hasRole('STUDENT')")
    @GetMapping()
    fun getCourseList(): ResponseEntity<List<CourseResponse>> {
        return ResponseEntity.status(HttpStatus.OK).body(courseService.getAllCourseList())
    }

    @PreAuthorize("hasRole('TUTOR')")
    @PostMapping()
    fun createCourse(@RequestBody createCourseRequest: CreateCourseRequest): ResponseEntity<CourseResponse> {
        return ResponseEntity.status(HttpStatus.CREATED).body(courseService.createCourse(createCourseRequest))
    }

    @PreAuthorize("hasRole('TUTOR')")
    @PutMapping("/{courseId}")
    fun updateCourse(
        @PathVariable courseId: Long,
        @RequestBody updateCourseRequest: UpdateCourseRequest
    ): ResponseEntity<CourseResponse> {
        return ResponseEntity.status(HttpStatus.OK).body(courseService.updateCourse(courseId, updateCourseRequest))
    }

    @PreAuthorize("hasRole('TUTOR')")
    @DeleteMapping("/{courseId}")
    fun deleteCourse(@PathVariable courseId: Long): ResponseEntity<Unit> {
        return ResponseEntity
            .status(HttpStatus.NO_CONTENT)
            .body(courseService.deleteCourse(courseId))

    }
}