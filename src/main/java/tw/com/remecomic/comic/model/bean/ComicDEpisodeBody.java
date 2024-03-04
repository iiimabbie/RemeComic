package tw.com.remecomic.comic.model.bean;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@Table(name="ComicDEpisodeBody")
public class ComicDEpisodeBody {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer pageId;
	
	private String pagePhoto;
	private Integer pageNum;

	@Column(name="episodeId")
	private Integer episodeId;
	
//	@JsonBackReference(value="episode-bodies")
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="episodeId",insertable = false, updatable = false)
	public ComicDEpisodeUpdate episode;
	
	@JsonManagedReference(value="page-comments")
	@OneToMany(mappedBy="page")
	public List<ComicDComment> pageComments;
	
	/*@JsonManagedReference
	@JsonIgnoreProperties({"episodeByEpisodeBodyEpisodeId"})
	@ManyToMany(fetch=FetchType.LAZY, cascade=CascadeType.ALL)
	@JoinTable(name="ComicDRead",
		joinColumns = {@JoinColumn(name="pageId", referencedColumnName="pageId")},
		inverseJoinColumns = {@JoinColumn(name="userId", referencedColumnName="userId")})
	private List<UserA> userAByComicDRead = new ArrayList<>();*/
	

}
