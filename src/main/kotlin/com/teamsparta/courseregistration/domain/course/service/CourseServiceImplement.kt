package com.teamsparta.courseregistration.domain.course.service

import com.teamsparta.courseregistration.domain.course.dto.CourseResponse
import com.teamsparta.courseregistration.domain.course.dto.CreateCourseRequest
import com.teamsparta.courseregistration.domain.course.dto.UpdateCourseRequest
import com.teamsparta.courseregistration.domain.course.model.Course
import com.teamsparta.courseregistration.domain.course.model.CourseStatus
import com.teamsparta.courseregistration.domain.course.model.toResponse
import com.teamsparta.courseregistration.domain.course.repository.CourseRepository
import com.teamsparta.courseregistration.domain.courseapplication.dto.ApplyCourseRequest
import com.teamsparta.courseregistration.domain.courseapplication.dto.CourseApplicationResponse
import com.teamsparta.courseregistration.domain.courseapplication.dto.UpdateApplicationStatusRequest
import com.teamsparta.courseregistration.domain.courseapplication.model.CourseApplicants
import com.teamsparta.courseregistration.domain.courseapplication.model.CourseApplicationStatus
import com.teamsparta.courseregistration.domain.courseapplication.model.toResponse
import com.teamsparta.courseregistration.domain.courseapplication.repository.CourseApplicationRepository
import com.teamsparta.courseregistration.domain.exception.ErrorResponse
import com.teamsparta.courseregistration.domain.exception.ModelNotFoundException
import com.teamsparta.courseregistration.domain.lecture.dto.CreateLecture
import com.teamsparta.courseregistration.domain.lecture.dto.LectureResponse
import com.teamsparta.courseregistration.domain.lecture.dto.UpdateLecture
import com.teamsparta.courseregistration.domain.lecture.model.Lecture
import com.teamsparta.courseregistration.domain.lecture.model.toResponse
import com.teamsparta.courseregistration.domain.lecture.repository.LectureRepository
import com.teamsparta.courseregistration.domain.user.repository.UserRepository
import com.teamsparta.courseregistration.infra.aop.StopWatch
import jakarta.transaction.Transactional
import org.springframework.data.repository.findByIdOrNull
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.ExceptionHandler

@Service
class CourseServiceImplement(
    private val courseRepository: CourseRepository,
    private val lectureRepository: LectureRepository,
    private val courseApplicationRepository: CourseApplicationRepository,
    private val userRepository: UserRepository
) : CourseService {

//    @StopWatch
    override fun getAllCourseList(): List<CourseResponse> {
        // TODO : DB에서 모든 Course를 가져와서 CourseResponse로 변환 후 반환

        return courseRepository.findAll().map { it.toResponse() }
    }

//    @StopWatch
    override fun getCourseById(courseId: Long): CourseResponse {
        // TODO: 만약 courseId에 해당하는 Course가 없다면 throw ModelNotFoundException
        // TODO : DB에서 courseId에 해당하는 Course를 가져와서 CourseResponse로 변환 후 반환

        val course = courseRepository.findByIdOrNull(courseId) ?: throw ModelNotFoundException("Course",courseId)
        return course.toResponse()
//        return courseRepository.findByIdOrNull(courseId)?.toResponse() ?: throw ModelNotFoundException("Course",courseId)
    }

    @Transactional
    override fun createCourse(request: CreateCourseRequest): CourseResponse {
        // TODO : request를 Course로 변환 후 DB에 저장

        return courseRepository.save(
            Course(
                title = request.title,
                description = request.description,
                status = CourseStatus.OPEN
            )
        ).toResponse()

    }

    @Transactional
    override fun updateCourse(courseId: Long, request: UpdateCourseRequest): CourseResponse {
        // TODO: 만약 courseId에 해당하는 Course가 없다면 throw ModelNotFoundException
        // TODO : courseId로 DB에서 찾아서 request로 업데이트

        val course = courseRepository.findByIdOrNull(courseId) ?: throw ModelNotFoundException("Course",courseId)
        val (title,description) = request

        course.title = title
        course.description = description

        return courseRepository.save(course).toResponse()


    }

    @Transactional
    override fun deleteCourse(courseId: Long) {
        // TODO: 만약 courseId에 해당하는 Course가 없다면 throw ModelNotFoundException
        // TODO : DB에서 courseId에 해당하는 Course를 삭제, 연관된 CourseApplication, Lecture 모두 삭제

        val course : Course = courseRepository.findByIdOrNull(courseId) ?: throw ModelNotFoundException("Course",courseId)

//        courseRepository.deleteById(course.id!!)
        courseRepository.delete(course)

    }

    @Transactional
    override fun createLecture(courseId: Long, request: CreateLecture): LectureResponse {
        // TODO : 만약 courseId에 해당하는 Course가 없다면 throw ModelNotFoundException
        // TODO: DB에서 courseId에 해당하는 Course를 가져와서 Lecture를 추가 후 DB에 저장, 결과를을 LectureResponse로 변환 후 반환

        val course = courseRepository.findByIdOrNull(courseId) ?: throw ModelNotFoundException("Course",courseId)

        val lecture = Lecture(
            title = request.title,
            videoUrl = request.videoUrl,
            course = course
        )

        lectureRepository.save(lecture)
        // Course에 Lecture 추가
//        course.addLecture(lecture) // N:1 단방향에서만 안씀
        // Lecture에 영속성을 전파
//        courseRepository.save(course)
        return lecture.toResponse()

    }

    override fun getLectureAllList(courseId: Long): List<LectureResponse> {
        // TODO: 만약 courseId에 해당하는 Course가 없다면 throw ModelNotFoundException
        // TODO: DB에서 courseId에 해당하는 Course목록을 가져오고, 하위 lecture들을 가져온 다음, LectureResopnse로 변환해서 반환

        // N:1 단방향에서 못씀
//        val course = courseRepository.findByIdOrNull(courseId) ?: throw ModelNotFoundException("Course", courseId)
//        return course.lectures.map { it.toResponse() }

        return lectureRepository.findAllByCourseId(courseId).map {it.toResponse()}

    }

    override fun getLectureById(lectureId: Long, courseId: Long): LectureResponse {
        // TODO: 만약 courseId, lectureId에 해당하는 Lecture가 없다면 throw ModelNotFoundException
        // TODO: DB에서 courseId, lectureId에 해당하는 Lecture를 가져와서 LectureResponse로 변환 후 반환

        // ========================
        // 양방향
//        val lecture = lectureRepository.findByCourseIdAndId(courseId,lectureId) ?: throw ModelNotFoundException("Lecutre",lectureId)
        // ========================
        val lecture = lectureRepository.findByIdOrNull(lectureId) ?: throw ModelNotFoundException("Lecture",lectureId)

        return lecture.toResponse()
    }

    @Transactional
    override fun updateLecture(courseId: Long, lectureId: Long, request: UpdateLecture): LectureResponse {
        // TODO: 만약 courseId, lectureId에 해당하는 Lecture가 없다면 throw ModelNotFoundException
        /* TODO: DB에서 courseId, lectureId에 해당하는 Lecture를 가져와서
            request로 업데이트 후 DB에 저장, 결과를을 LectureResponse로 변환 후 반환 */

        // ========================
        // 양방향
//        val lecture = lectureRepository.findByCourseIdAndId(courseId, lectureId) ?: throw ModelNotFoundException("Lecture",lectureId)
        // ======================
        val lecture = lectureRepository.findByIdOrNull(lectureId) ?: throw ModelNotFoundException("Lecture",lectureId)
        lecture.title = request.title
        lecture.videoUrl = request.videoUrl

        lectureRepository.save(lecture)
        return lecture.toResponse()
    }

    @Transactional
    override fun deleteLecture(lectureId: Long, courseId: Long) {
        // TODO: 만약 courseId에 해당하는 Course가 없다면 throw ModelNotFoundException
        // TODO: DB에서 courseId, lectureId에 해당하는 Lecture를 가져오고, 삭제

        val course = courseRepository.findByIdOrNull(courseId) ?: throw ModelNotFoundException("Course",courseId)
        val lecture = lectureRepository.findByIdOrNull(lectureId) ?: throw ModelNotFoundException("Lecture",lectureId)
        lectureRepository.delete(lecture)
        lectureRepository.save(lecture)
//        course.removeLecture(lecture)
//        courseRepository.save(course)
    }

    @Transactional
    override fun applyCourse(courseId: Long, request: ApplyCourseRequest): CourseApplicationResponse {
        // TODO: 만약 courseId에 해당하는 Course가 없다면 throw ModelNotFoundException
        // TODO: 만약 course가 이미 마감됐다면, throw IllegalStateException
        // TODO: 이미 신청했다면, throw IllegalStateException

        val user = userRepository.findByIdOrNull(request.userId) ?: throw ModelNotFoundException("User", request.userId)
        val course = courseRepository.findByIdOrNull(courseId) ?: throw ModelNotFoundException("Course",courseId)

        if(course.isClosed()){
            throw IllegalStateException("Course is already closed. courseId: $courseId")
        }

        if (courseApplicationRepository.existsByCourseIdAndUserId(courseId, request.userId)) {
            throw IllegalStateException("Already applied. courseId: $courseId, userId: ${request.userId}")
        }

        val courseApplication = CourseApplicants (
            course = course,
            user = user
        )

        course.addCourseApplication(courseApplication)
        courseApplicationRepository.save(courseApplication)
        courseRepository.save(course)
        return courseApplication.toResponse()

    }

    override fun getCourseApplication(courseId: Long, applicationId: Long): CourseApplicationResponse {
        // TODO: 만약 courseId, applicationId에 해당하는 CourseApplication이 없다면 throw ModelNotFoundException
        // TODO: DB에서 courseId, applicationId에 해당하는 CourseApplication을 가져와서 CourseApplicationResponse로 변환 후 반환

        val application = courseApplicationRepository.findByCourseIdAndId(courseId, applicationId)
            ?: throw ModelNotFoundException("CourseApplication", applicationId)

        return application.toResponse()
    }

    override fun getCourseApplicationList(courseId: Long): List<CourseApplicationResponse> {
        // TODO: 만약 courseId에 해당하는 Course가 없다면 throw ModelNotFoundException
        // TODO: DB에서 courseId에 해당하는 Course를 가져오고, 하위 courseApplication들을 CourseApplicationResponse로 변환 후 반환

        val course = courseRepository.findByIdOrNull(courseId) ?: throw ModelNotFoundException("Course", courseId)

        return course.courseApplicants.map { it.toResponse() }
    }

    @Transactional
    override fun updateCourseApplicationStatus(
        courseId: Long,
        applicationId: Long,
        request: UpdateApplicationStatusRequest
    ): CourseApplicationResponse {
        // TODO: 만약 courseId, applicationId에 해당하는 CourseApplication이 없다면 throw ModelNotFoundException
        // TODO: 만약 status가 이미 변경된 상태면 throw IllegalStateException
        // TODO: Course의 status가 CLOSED상태 일시 throw IllegalStateException
        // TODO: 승인을 하는 케이스일 경우, course의 numApplicants와 maxApplicants가 동일하면, course의 상태를 CLOSED로 변경
        // TODO: DB에서 courseApplication을 가져오고, status를 request로 업데이트 후 DB에 저장, 결과를 CourseApplicationResponse로 변환 후 반환

        val course = courseRepository.findByIdOrNull(courseId) ?: throw ModelNotFoundException("Course", courseId)
        val application = courseApplicationRepository.findByCourseIdAndId(courseId, applicationId)
            ?: throw ModelNotFoundException("CourseApplication", applicationId)

        // 이미 승인 혹은 거절된 신청건인지 체크
        if (application.isProceeded()) {
            throw IllegalStateException("Application is already proceeded. applicationId: $applicationId")
        }

        // Course 마감여부 체크
        if (course.isClosed()) {
            throw IllegalStateException("Course is already closed. courseId: $courseId")
        }

        // 승인 / 거절 따른 처리
        when (request.status) {
            // 승인 일때
            CourseApplicationStatus.ACCEPTED.name -> {
                // 승인 처리
                application.accept()
                // Course의 신청 인원 늘려줌
                course.addApplicant()
                // 만약 신청 인원이 꽉 찬다면 마감 처리
                if (course.isFull()) {
                    course.close()
                }
                courseRepository.save(course)
            }

            // 거절 일때
            CourseApplicationStatus.REJECTED.name -> {
                // 거절 처리
                application.reject()
            }
            // 승인 거절이 아닌 다른 인자가 들어올 경우 에러 처리
            else -> {
                throw IllegalArgumentException("Invalid status: ${request.status}")
            }
        }

        return courseApplicationRepository.save(application).toResponse()
    }


}