package com.laboschqpa.server.entity.usergeneratedcontent;

import com.laboschqpa.server.enums.converter.attributeconverter.ObjectiveTypeAttributeConverter;
import com.laboschqpa.server.enums.ugc.ObjectiveType;
import com.laboschqpa.server.enums.ugc.UserGeneratedContentTypeValues;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.time.Instant;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "objective")
@DiscriminatorValue(value = UserGeneratedContentTypeValues.OBJECTIVE)
public class Objective extends UserGeneratedContent {
    @Column(name = "title", columnDefinition = "text", nullable = false)
    private String title;

    @Column(name = "description", columnDefinition = "text")
    private String description;

    /**
     * Indicates if team members can submit a {@link Submission} to this objective.
     */
    @Column(name = "submittable", nullable = false)
    private Boolean submittable;

    @Column(name = "deadline", columnDefinition = "datetime", nullable = false)
    private Instant deadline;

    /**
     * Submissions are only visible to everyone after this time. If {@code null}, they are always visible.
     */
    @Column(name = "hide_submissions_before", columnDefinition = "datetime")
    private Instant hideSubmissionsBefore;

    @Convert(converter = ObjectiveTypeAttributeConverter.class)
    @Column(name = "objective_type", columnDefinition = "tinyint not null", nullable = false)
    private ObjectiveType objectiveType;
}
