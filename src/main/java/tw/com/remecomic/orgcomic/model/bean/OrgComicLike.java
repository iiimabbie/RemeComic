package tw.com.remecomic.orgcomic.model.bean;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import tw.com.remecomic.userA.model.bean.UserA;

@Data
@Entity
@Table(name = "OrgComicLike")
@AllArgsConstructor
@NoArgsConstructor
@IdClass(OrgComicLikePK.class)
public class OrgComicLike {

//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Integer id;
	@Id
	private Integer comicId;

	@JsonBackReference(value = "comic-like")
	@ManyToOne
	@JoinColumn(name = "comicId", insertable = false, updatable = false)
	private OrgComic comic;

	@Id
	private Integer userId;

//    @JsonBackReference(value="user-like")
	@ManyToOne
	@JoinColumn(name = "userId", insertable = false, updatable = false)
	private UserA user;

	public OrgComicLike(Integer comicId, Integer userId) {
		this.comicId = comicId;
		this.userId = userId;
	}

}