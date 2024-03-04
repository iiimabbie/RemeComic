package tw.com.remecomic.forum.model.bean;

import com.fasterxml.jackson.annotation.JsonIgnore;

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
@Table(name="ForumPostPhoto")
public class ForumPostPhoto {
	
	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="postPictureId")
	private Integer postPictureId;
		
//	@JsonIgnoreProperties({"gender","birthDate","registerDate",
//		"userPhoto","badges"})
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="postId")
	@JsonIgnore
	private ForumPost post; //多對一fk
	
	@Column(name="photo")
	private String photo;
}
