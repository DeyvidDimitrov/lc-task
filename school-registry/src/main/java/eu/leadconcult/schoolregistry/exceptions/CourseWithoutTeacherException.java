package eu.leadconcult.schoolregistry.exceptions;

import org.springframework.http.HttpStatus;

public class CourseWithoutTeacherException extends BaseException {
    private static final String ERROR_CODE = "COURSE_WITHOUT_TEACHER";

    public CourseWithoutTeacherException(String message) {
        super(message, ERROR_CODE, HttpStatus.CONFLICT);
    }
}
