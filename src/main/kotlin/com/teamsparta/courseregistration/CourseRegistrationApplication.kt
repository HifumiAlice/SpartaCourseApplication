package com.teamsparta.courseregistration

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.EnableAspectJAutoProxy

@EnableAspectJAutoProxy // 심화에서 추가
@SpringBootApplication
class CourseRegistrationApplication

fun main(args: Array<String>) {
    runApplication<CourseRegistrationApplication>(*args)
}
