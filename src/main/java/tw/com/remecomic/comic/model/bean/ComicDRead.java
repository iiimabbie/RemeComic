package tw.com.remecomic.comic.model.bean;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;
import tw.com.remecomic.userA.model.bean.UserA;

@Data
@Entity
@NoArgsConstructor
@Table(name = "ComicDRead")
@IdClass(ComicDReadPK.class)
public class ComicDRead {
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer readId;

	@Id
	private Integer pageId;

	@Id
	private Integer userId;
	private Integer isBookMarked;

//	@JsonBackReference(value = "read-user")
	@Id
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "userId", insertable = false, updatable = false)
	private UserA user;

	public ComicDRead(Integer userId, Integer pageId, Integer isBookMarked) {
		this.userId = userId;
		this.pageId = pageId;
		this.isBookMarked = isBookMarked;
	}
	
	
	
	


	
}
