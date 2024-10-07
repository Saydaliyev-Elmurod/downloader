package org.example.user.model.request;

import java.util.UUID;

public record TeacherRequest(
        UUID subjectId,
        String description,
        Double experience
) {
}
