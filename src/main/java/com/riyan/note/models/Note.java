package com.riyan.note.models;

import lombok.*;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.util.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "note")
public class Note {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "note_id")
    public long id;
    public String title;
    public String description;
    public byte[] image;
    public String imageName;
    @Enumerated(EnumType.STRING)
    public NoteType noteType;
    @OneToMany(cascade = {CascadeType.ALL}, mappedBy = "note", fetch = FetchType.EAGER)
    public Set<CheckBox> checkBoxes;
    public Date createdAt;
    public Date updatedAt;
    public String createdBy;

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("{");
        sb.append("\"id\":")
                .append(id);
        sb.append(",\"title\":\"")
                .append(Objects.toString(title, "")).append('\"');
        sb.append(",\"description\":\"")
                .append(Objects.toString(description, "")).append('\"');
        sb.append(",\"image\":")
                .append(Arrays.toString(image));
        sb.append(",\"imageName\":\"")
                .append(Objects.toString(imageName, "")).append('\"');
        sb.append(",\"noteType\":")
                .append(noteType);
        sb.append(",\"checkBoxes\":");
        if ((checkBoxes) != null && !(checkBoxes).isEmpty()) {
            sb.append("[");
            for (Object collectionValue : checkBoxes) {
                sb.append("\"").append(Objects.toString(collectionValue, "")).append("\",");
            }
            sb.replace(sb.length() - 1, sb.length(), "]");
        } else {
            sb.append("[]");
        }
        sb.append(",\"createdAt\":\"")
                .append(Objects.toString(createdAt, "")).append('\"');
        sb.append(",\"updatedAt\":\"")
                .append(Objects.toString(updatedAt, "")).append('\"');
        sb.append(",\"createdBy\":\"")
                .append(Objects.toString(createdBy, "")).append('\"');
        sb.append('}');
        return sb.toString();
    }
}
