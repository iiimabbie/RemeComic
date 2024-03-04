package tw.com.remecomic.forum.model.bean;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name="ForumPostTag")
public class ForumPostTag {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="postTagId")
	private Integer postTagId;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="tagId")
	@JsonIgnoreProperties({"posts"})
	private ForumTag tag; //多對多fk
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="postId")
	@JsonIgnore
	private ForumPost post; //多對多fk
	
}
