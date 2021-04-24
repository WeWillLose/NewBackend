package com.diploma.Backend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.JsonNode;
import com.vladmihalcea.hibernate.type.json.JsonStringType;
import lombok.*;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(of = {"id", "reportName","data"})
@EqualsAndHashCode(callSuper = false, of = {"id","reportName","data"})
@TypeDef(
    typeClass = JsonStringType.class,
    defaultForType = JsonNode.class
)
@Entity
@Table(name = "report")
public class Report extends AbstractAuditingEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String reportName;

    @Column(columnDefinition = "json")
    private JsonNode data;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User author;

    @Column
    @Enumerated(EnumType.STRING)
    private EReportStatus status;

}
