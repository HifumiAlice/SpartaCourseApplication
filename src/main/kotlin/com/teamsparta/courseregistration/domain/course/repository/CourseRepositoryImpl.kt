package com.teamsparta.courseregistration.domain.course.repository

import com.querydsl.core.BooleanBuilder
import com.querydsl.core.types.Expression
import com.querydsl.core.types.Order
import com.querydsl.core.types.OrderSpecifier
import com.querydsl.core.types.dsl.EntityPathBase
import com.querydsl.core.types.dsl.PathBuilder
import com.teamsparta.courseregistration.domain.course.model.Course
import com.teamsparta.courseregistration.domain.course.model.CourseStatus
import com.teamsparta.courseregistration.domain.course.model.QCourse
import com.teamsparta.courseregistration.infra.QueryDslSupport
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Repository

@Repository
class CourseRepositoryImpl : CustomCourseRepository, QueryDslSupport() {
    private val course = QCourse.course

    override fun searchCourseListByTitle(title: String): List<Course> {
        return queryFactory.select(course).from(course)
            .where(course.title.containsIgnoreCase(title))
            .fetch()
    }

    override fun findByPageableAndStatus(pageable: Pageable, status: CourseStatus?): Page<Course> {
        val whereClause = BooleanBuilder()
        status?.let { whereClause.and(course.status.eq(status)) }

        val totalCount = queryFactory.select(course.count()).from(course).where(whereClause).fetchOne() ?: 0L

        val query = queryFactory.select(course).from(course).where(whereClause).offset(pageable.offset)
            .limit(pageable.pageSize.toLong())

//        if (pageable.sort.isSorted) {
//            when(pageable.sort.first()?.property) {
//                "id" -> query.orderBy(course.id.asc())
//                "title" -> query.orderBy(course.title.asc())
//                else -> query.orderBy(course.id.asc())
//            }
//        } else {
//            query.orderBy(course.id.asc())
//        }

//        val contents = query.fetch()

        val contents = queryFactory.selectFrom(course)
            .where(whereClause)
            .offset(pageable.offset)
            .limit(pageable.pageSize.toLong())
            .orderBy(*getOrderSpecifier(pageable, course))
            .fetch()

        return PageImpl(contents, pageable, totalCount)

    }

    private fun getOrderSpecifier(pageable: Pageable, path: EntityPathBase<*>): Array<OrderSpecifier<*>> {
        val pathBuilder = PathBuilder(path.type, path.metadata)

        return pageable.sort.toList().map { order ->
            OrderSpecifier(
                if (order.isAscending) Order.ASC else Order.DESC,
                pathBuilder.get(order.property) as Expression<Comparable<*>>
            )
        }.toTypedArray()
    }

}