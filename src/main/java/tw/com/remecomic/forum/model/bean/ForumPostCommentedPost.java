package tw.com.remecomic.forum.model.bean;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name="ForumPostCommentedPost")
public class ForumPostCommentedPost {
	
	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="commentConnectionId")
	private Integer commentConnectionId;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="childPostId")
	@JsonIgnore
	private ForumPost childPost; //多對一fk 對該貼文的評論(=針對該貼文做評論的底下貼文)
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="parentPostId")
//	@JsonIgnoreProperties({"comments"})
	@JsonIgnore
	private ForumPost parentPost; //多對一fk 被評論的貼文

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        ForumPostCommentedPost that = (ForumPostCommentedPost) obj;
        return Objects.equals(commentConnectionId, that.commentConnectionId);
	}

	@Override
	public int hashCode() {
		return Objects.hash(commentConnectionId);
	}
	
}
