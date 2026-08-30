package com.ecommerce.model.entity;

import com.ecommerce.model.enums.FileType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Where;

@Entity
@Table(name = "media_files")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Where(clause = "is_deleted = false")
public class MediaFile extends BaseEntity {

    @Column(name = "original_name", nullable = false, length = 255)
    private String originalName;

    @Column(name = "file_name", nullable = false, length = 255)
    private String fileName;

    @Column(name = "file_path", nullable = false, length = 500)
    private String filePath;

    @Column(name = "file_url", nullable = false, length = 500)
    private String fileUrl;

    @Column(name = "mime_type", length = 100)
    private String mimeType;

    @Column(name = "file_size")
    private Long fileSize;

    @Column(name = "file_extension", length = 10)
    private String fileExtension;

    @Enumerated(EnumType.STRING)
    @Column(name = "file_type")
    private FileType fileType;

    @Column(name = "width")
    private Integer width;

    @Column(name = "height")
    private Integer height;

    @Column(name = "alt_text", length = 200)
    private String altText;

    @Column(name = "title", length = 200)
    private String title;

    @Column(name = "description", length = 500)
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "uploaded_by")
    private User uploadedBy;

    @Column(name = "folder", length = 200)
    @Builder.Default
    private String folder = "general";
}
