package tw.com.remecomic.orgcomic.model.bean;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "OrgComicBody")
public class OrgComicBody {
	
	
//	@JsonBackReference(value = "comic-body")
//	@ManyToOne
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	@Column(name = "comicId")
	private Integer comicId;
	
	
//	@Id
	@Column(name = "comicEpisode")
	private Integer comicEpisode;
	
	
	@Column(name = "comicBodyPhoto")
	private String comicBodyPhoto;

	
	public OrgComicBody() {
    }
	

	public OrgComicBody(Integer comicId, Integer comicEpisode, String comicBodyPhoto) {
		this.comicId = comicId;
		this.comicEpisode = comicEpisode;
		this.comicBodyPhoto = comicBodyPhoto;
	}
	
	
	
}
