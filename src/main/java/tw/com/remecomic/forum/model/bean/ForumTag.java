package tw.com.remecomic.forum.model.bean;

import java.util.Collection;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name="ForumTag")
public class ForumTag {
	
	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="tagId")
	private Integer tagId;
	
	@Column(name="tagName")
	private String tagName;
	
	@OneToMany(mappedBy = "tag",fetch = FetchType.LAZY)
//	@JsonIgnoreProperties({"tag","post"})
	@JsonIgnore
	private Collection<ForumPostTag> posts; //有該tag的貼文
}
