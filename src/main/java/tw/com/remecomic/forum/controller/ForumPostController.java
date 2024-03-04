package tw.com.remecomic.forum.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.transaction.Transactional;
import tw.com.remecomic.forum.model.bean.ForumGroup;
import tw.com.remecomic.forum.model.bean.ForumPost;
import tw.com.remecomic.forum.model.bean.ForumPostCollect;
import tw.com.remecomic.forum.model.bean.ForumPostCommentedPost;
import tw.com.remecomic.forum.model.bean.ForumPostForward;
import tw.com.remecomic.forum.model.bean.ForumPostLike;
import tw.com.remecomic.forum.model.bean.ForumPostReport;
import tw.com.remecomic.forum.model.bean.ForumReason;
import tw.com.remecomic.forum.model.bean.ForumReportVerify;
import tw.com.remecomic.forum.model.bean.ForumUser;
import tw.com.remecomic.forum.model.dto.ForumPostDto;
import tw.com.remecomic.forum.model.dto.ForumReportForPostDto;
import tw.com.remecomic.forum.model.dto.ForumReportFormatDto;
import tw.com.remecomic.forum.model.dto.ForumReportSingleDto;
import tw.com.remecomic.forum.model.dto.ForumTagDto2;
import tw.com.remecomic.forum.service.ForumPostInteractiveService;
import tw.com.remecomic.forum.service.ForumPostService;
import tw.com.remecomic.forum.service.ForumUserService;

@RestController
public class ForumPostController {

	@Autowired
	private ForumPostService postService;
	@Autowired
	private ForumUserService userAService;
	@Autowired
	private ForumPostInteractiveService postInteractiveService;
	@Autowired
	private ForumUserService forumUserService;
	
	@GetMapping("/post/verify/result") // read post
	public ResponseEntity<List<ForumPost>> postAllWithVerifyResult() {//
		List<ForumPost> posts = postService.findAllPost();
		return ResponseEntity.ok(posts);
	}
	
//////////////////////////// show post ///////////////////////////////
	
	//////////////// all (n+h) (show post) /////////////////
	// show post by page
	@GetMapping("/post/show/new") // read post
	public ResponseEntity<PageImpl<ForumPostDto>> postNewShow(@RequestParam(value="pageNumber",defaultValue = "1")Integer pageNumber
																,@RequestParam Integer userId) {//
		Optional<ForumUser> findById = userAService.findById(userId);
		if(findById.isPresent()) {
			PageImpl<ForumPostDto> pageImp = postService.getNewPost(pageNumber,findById.get());
			if (!pageImp.isEmpty()) {
				return ResponseEntity.ok(pageImp);
			}else {
				return ResponseEntity.noContent().build();
			}
		}
		return ResponseEntity.notFound().build();
	}
	@GetMapping("/post/show/hot") // read post
	public ResponseEntity<Page<ForumPostDto>> postHotShow(@RequestParam(value="pageNumber",defaultValue = "1")Integer pageNumber,
			@RequestParam(defaultValue = "1") Integer periodNumber,
			@RequestParam Integer userId) {//
		
		System.out.println("periodNumber="+periodNumber);
		
		Optional<ForumUser> findById = userAService.findById(userId);
		if(findById.isPresent()) {
			Page<ForumPostDto> pageImp = postService.getHotPostInTimePeriod(pageNumber,periodNumber,findById.get());
			if (!pageImp.isEmpty()) {
				return ResponseEntity.ok(pageImp);
			}else {
				return ResponseEntity.noContent().build();
			}
		}
		return ResponseEntity.notFound().build();
	}
	@GetMapping("/post/show/hot2") // read post
	public ResponseEntity<Page<Map<String, Object[]>>> postHotShowTryFast(@RequestParam(value="pageNumber",defaultValue = "1")Integer pageNumber,
														@RequestParam(defaultValue = "1") Integer periodNumber,
														@RequestParam Integer userId) {//
		Optional<ForumUser> findById = userAService.findById(userId);
		if(findById.isPresent()) {
			Page<Map<String, Object[]>> pageImp = postService.getHotPostInTimePeriodTryFast(pageNumber,periodNumber,findById.get());
			if (!pageImp.isEmpty()) {
				return ResponseEntity.ok(pageImp);
			}else {
				return ResponseEntity.noContent().build();
			}
		}
		return ResponseEntity.notFound().build();
	}
	//////////////// tag (n+h) (show post) /////////////////
	// show post by page
	@GetMapping("/post/tag/show/new") // read post
	public ResponseEntity<PageImpl<ForumPostDto>> postTagNewShow(@RequestParam(value="pageNumber",defaultValue = "1")Integer pageNumber
																,@RequestParam Integer userId
																,@RequestParam Integer tagId) {//
		Optional<ForumUser> findById = userAService.findById(userId);
		if(findById.isPresent()) {
			PageImpl<ForumPostDto> pageImp = postService.getNewPostByTag(pageNumber,findById.get(),tagId);
			if (!pageImp.isEmpty()) {
				return ResponseEntity.ok(pageImp);
			}else {
				return ResponseEntity.noContent().build();
			}
		}
		return ResponseEntity.notFound().build();
	}
	@GetMapping("/post/tag/show/hot") // read post
	public ResponseEntity<Page<ForumPostDto>> postTagHotShow(@RequestParam(value="pageNumber",defaultValue = "1")Integer pageNumber,
															@RequestParam(defaultValue = "1") Integer periodNumber,
															@RequestParam Integer userId,
															@RequestParam Integer tagId) {//
		System.out.println("進入到show hot tag,pageNumber="+pageNumber+";periodNumber="+periodNumber+";userId="+userId+";tagId="+tagId);
		
		Optional<ForumUser> findById = userAService.findById(userId);
		if(findById.isPresent()) {
			Page<ForumPostDto> pageImp = postService.getHotPostInTimePeriodByTag(pageNumber,periodNumber,findById.get(),tagId);
			if (!pageImp.isEmpty()) {
				return ResponseEntity.ok(pageImp);
			}else {
				return ResponseEntity.noContent().build();
			}
		}
		return ResponseEntity.notFound().build();
	}
	
	//////////////// filter by follows (n) (show post) /////////////////
	// post/comment 、 like 、 forward
	// show post by page
	@GetMapping("/post/follow/show/new") // read post
	public ResponseEntity<PageImpl<ForumPostDto>> postShowWithFollow(@RequestParam("userId") Integer userId,
														@RequestParam(value="pageNumber",defaultValue = "1")Integer pageNumber) {//
		System.out.println("pageNumber="+pageNumber);
		Optional<ForumUser> findUserResult = userAService.findById(userId);
		if(findUserResult.isPresent()) {
			ForumUser user = findUserResult.get();
			PageImpl<ForumPostDto> pageImp = postService.getNewPostWithFollow(pageNumber,user);
			if (!pageImp.isEmpty()) {
				return ResponseEntity.ok(pageImp);
			}else {
				return ResponseEntity.noContent().build();
			}
		}
		return ResponseEntity.notFound().build();
	}
	
	//////////////// filter by users owner and forward (n) (show post) /////////////////
	@GetMapping("/post/profile/show") // read post
	public ResponseEntity<PageImpl<ForumPostDto>> postShowInProfileOwnerAndForward(
										@RequestParam Integer loginUserId,@RequestParam Integer profileUserId,
										@RequestParam(value="pageNumber",defaultValue = "1")Integer pageNumber) {//
		Optional<ForumUser> findLogUserResult = userAService.findById(loginUserId);
		Optional<ForumUser> findProfileUserResult = userAService.findById(profileUserId);
		if(findLogUserResult.isPresent()&&findProfileUserResult.isPresent()) {
			ForumUser loginUser = findLogUserResult.get();
			ForumUser profileUser = findProfileUserResult.get();
			PageImpl<ForumPostDto> pageImp = postService.getPostInProfileOwnerAndForward(pageNumber,loginUser,profileUser);
			if (!pageImp.isEmpty()) {
				return ResponseEntity.ok(pageImp);
			}else {
				return ResponseEntity.noContent().build();
			}
		}
		return ResponseEntity.notFound().build();
	}
	
	//////////////// filter by users media (n) (show post) /////////////////
	@GetMapping("/post/profile/show/photo") // read post
	public ResponseEntity<PageImpl<ForumPostDto>> postShowInProfileOwnerHavePhoto(
										@RequestParam Integer loginUserId,@RequestParam Integer profileUserId,
										@RequestParam(value="pageNumber",defaultValue = "1")Integer pageNumber) {//
		Optional<ForumUser> findLogUserResult = userAService.findById(loginUserId);
		Optional<ForumUser> findProfileUserResult = userAService.findById(profileUserId);
		if(findLogUserResult.isPresent()&&findProfileUserResult.isPresent()) {
			ForumUser loginUser = findLogUserResult.get();
			ForumUser profileUser = findProfileUserResult.get();
			PageImpl<ForumPostDto> pageImp = postService.getPostInProfileOwnerHavePhoto(pageNumber,loginUser,profileUser);
			if (!pageImp.isEmpty()) {
				return ResponseEntity.ok(pageImp);
			}else {
				return ResponseEntity.noContent().build();
			}
		}
		return ResponseEntity.notFound().build();
	}
	
	//////////////// filter by users comment (n) (show post) /////////////////
	@GetMapping("/post/profile/show/comment") // read post
	public ResponseEntity<PageImpl<ForumPostDto>> postShowInProfileOwnerIsComment(
										@RequestParam Integer loginUserId,@RequestParam Integer profileUserId,
										@RequestParam(value="pageNumber",defaultValue = "1")Integer pageNumber) {//
		Optional<ForumUser> findLogUserResult = userAService.findById(loginUserId);
		Optional<ForumUser> findProfileUserResult = userAService.findById(profileUserId);
		if(findLogUserResult.isPresent()&&findProfileUserResult.isPresent()) {
			ForumUser loginUser = findLogUserResult.get();
			ForumUser profileUser = findProfileUserResult.get();
			PageImpl<ForumPostDto> pageImp = postService.getPostInProfileOwnerIsComment(pageNumber,loginUser,profileUser);
			if (!pageImp.isEmpty()) {
				return ResponseEntity.ok(pageImp);
			}else {
				return ResponseEntity.noContent().build();
			}
		}
		return ResponseEntity.notFound().build();
	}
	
	//////////////// filter by users like (n) (show post) /////////////////
	@GetMapping("/post/profile/show/like") // read post
	public ResponseEntity<PageImpl<ForumPostDto>> postShowInProfileUserLike(
										@RequestParam Integer loginUserId,@RequestParam Integer profileUserId,
										@RequestParam(value="pageNumber",defaultValue = "1")Integer pageNumber) {//
		Optional<ForumUser> findLogUserResult = userAService.findById(loginUserId);
		Optional<ForumUser> findProfileUserResult = userAService.findById(profileUserId);
		if(findLogUserResult.isPresent()&&findProfileUserResult.isPresent()) {
			ForumUser loginUser = findLogUserResult.get();
			ForumUser profileUser = findProfileUserResult.get();
			PageImpl<ForumPostDto> pageImp = postService.getPostInProfileUserLike(pageNumber,loginUser,profileUser);
			if (!pageImp.isEmpty()) {
				return ResponseEntity.ok(pageImp);
			}else {
				return ResponseEntity.noContent().build();
			}
		}
		return ResponseEntity.notFound().build();
	}
	
	//////////////// filter by user collect (n) (show post) /////////////////
	@GetMapping("/post/profile/show/collect") // read post
	public ResponseEntity<PageImpl<ForumPostDto>> postShowInProfileUserCollect(
										@RequestParam Integer loginUserId,@RequestParam Integer profileUserId,
										@RequestParam(value="pageNumber",defaultValue = "1")Integer pageNumber) {//
		Optional<ForumUser> findLogUserResult = userAService.findById(loginUserId);
		Optional<ForumUser> findProfileUserResult = userAService.findById(profileUserId);
		if(findLogUserResult.isPresent()&&findProfileUserResult.isPresent()) {
			ForumUser loginUser = findLogUserResult.get();
			ForumUser profileUser = findProfileUserResult.get();
			PageImpl<ForumPostDto> pageImp = postService.getPostInProfileUserCollect(pageNumber,loginUser,profileUser);
			if (!pageImp.isEmpty()) {
				return ResponseEntity.ok(pageImp);
			}else {
				return ResponseEntity.noContent().build();
			}
		}
		return ResponseEntity.notFound().build();
	}
	////////////////filter by user private (n) (show post) /////////////////
	@GetMapping("/post/show/private") // read post
	public ResponseEntity<PageImpl<ForumPostDto>> postShowInProfileUserCollect(
								@RequestParam Integer loginUserId,@RequestParam(value="pageNumber",defaultValue = "1")Integer pageNumber) {//
	Optional<ForumUser> findLogUserResult = userAService.findById(loginUserId);
	if(findLogUserResult.isPresent()) {
	ForumUser loginUser = findLogUserResult.get();
	PageImpl<ForumPostDto> pageImp = postService.getPostUserPrivate(pageNumber,loginUser);
	if (!pageImp.isEmpty()) {
		return ResponseEntity.ok(pageImp);
	}else {
		return ResponseEntity.noContent().build();
	}
	}
	return ResponseEntity.notFound().build();
	}
	
	//////////////// filter by have report (n+h) (show post) /////////////////
	
	@GetMapping("/post/report/show") // read post
	public ResponseEntity<List<ForumReportForPostDto>> postShowWithReport(@RequestParam(defaultValue = "1")Integer pageNumber,
																	@RequestParam(defaultValue = "1") List<Integer> verifyIdArray) {//
		List<ForumReportForPostDto> postDtos = postService.getPostWithReport(pageNumber,verifyIdArray);
		if (!postDtos.isEmpty()) {
			return ResponseEntity.ok(postDtos);
		}else {
			return ResponseEntity.noContent().build();
		}
	}
	@GetMapping("/post/report/page/show") // read post
	public ResponseEntity<PageImpl<ForumReportForPostDto>> postShowWithReportWithPage(@RequestParam(defaultValue = "1")Integer pageNumber,
			@RequestParam(defaultValue = "1,2") List<Integer>verifyIdArray) {//
		PageImpl<ForumReportForPostDto> pageImp = postService.getPostWithReportWithPage(pageNumber,verifyIdArray);
		if (!pageImp.isEmpty()) {
			return ResponseEntity.ok(pageImp);
		}else {
			return ResponseEntity.noContent().build();
		}
	}
	
	//////////////// filter by search (n+h) (show post) /////////////////
	@GetMapping("/post/show/search/new") //read tag
	public ResponseEntity<PageImpl<ForumPostDto>> postShowBySearchOrderByNew(@RequestParam(value="searchString") String searchString,
																@RequestParam Integer loginUserId,
																@RequestParam(value="pageNumber",defaultValue = "1")Integer pageNumber) {
		System.out.println("進入到show new search,pageNumber="+pageNumber+";loginUserId="+loginUserId+";searchString="+searchString);
		
		Optional<ForumUser> findById = userAService.findById(loginUserId);
		if(findById.isPresent()&&searchString!=null) {
			PageImpl<ForumPostDto> pageImp = postService.getNewPostBySearch(pageNumber,findById.get(),searchString);
			if (!pageImp.isEmpty()) {
				return ResponseEntity.ok(pageImp);
			}else {
				return ResponseEntity.noContent().build();
			}
		}
		return ResponseEntity.notFound().build();
	}
	
	@GetMapping("/post/show/search/hot") //read tag
	public ResponseEntity<Page<ForumPostDto>> postShowBySearchOrderByHot(@RequestParam(value="searchString") String searchString,
																@RequestParam Integer loginUserId,
																@RequestParam(value="pageNumber",defaultValue = "1")Integer pageNumber,
																@RequestParam(defaultValue = "1") Integer periodNumber) {
		System.out.println("進入到show hot search,pageNumber="+pageNumber+";periodNumber="+periodNumber+";loginUserId="+loginUserId+";searchString="+searchString);
		
		Optional<ForumUser> findById = userAService.findById(loginUserId);
		if(findById.isPresent()&&searchString!=null) {
			Page<ForumPostDto> pageImp = postService.getHotPostInTimePeriodBySearch(pageNumber,periodNumber,findById.get(),searchString);
			if (!pageImp.isEmpty()) {
				return ResponseEntity.ok(pageImp);
			}else {
				return ResponseEntity.noContent().build();
			}
		}
		return ResponseEntity.notFound().build();
	}
	
	//////////////// filter by users in group (n+h) (show post) /////////////////
	@GetMapping("/post/group/show/new") // read post
	public ResponseEntity<PageImpl<ForumPostDto>> postNewShowInGroup(@RequestParam(value="pageNumber",defaultValue = "1")Integer pageNumber,
																@RequestParam Integer userId,@RequestParam Integer groupId) {//
		Optional<ForumUser> findById = userAService.findById(userId);
		Optional<ForumGroup> findGroupById = forumUserService.findGroupById(groupId);
		if(findById.isPresent() && findGroupById.isPresent()) {
			PageImpl<ForumPostDto> pageImp = postService.getNewPostInGroup(pageNumber,findById.get(),findGroupById.get());
			if (!pageImp.isEmpty()) {
				return ResponseEntity.ok(pageImp);
			}else {
				return ResponseEntity.noContent().build();
			}
		}
		return ResponseEntity.notFound().build();
	}
	@GetMapping("/post/group/show/hot") // read post
	public ResponseEntity<PageImpl<ForumPostDto>> postHotPostInGroup(@RequestParam(value="pageNumber",defaultValue = "1")Integer pageNumber,
														@RequestParam(defaultValue = "1") Integer periodNumber,
														@RequestParam Integer userId,@RequestParam Integer groupId) {//
		Optional<ForumUser> findById = userAService.findById(userId);
		Optional<ForumGroup> findGroupById = forumUserService.findGroupById(groupId);
		if(findById.isPresent() && findGroupById.isPresent()) {
			PageImpl<ForumPostDto> pageImp = postService.getHotPostInTimePeriodInGroup(pageNumber,periodNumber,findById.get(),groupId);
			if (!pageImp.isEmpty()) {
				return ResponseEntity.ok(pageImp);
			}else {
				return ResponseEntity.noContent().build();
			}
		}
		return ResponseEntity.notFound().build();
	}
	
	///////////////////////////// show comments belong a post (show post)///////////////////////////////
	@GetMapping("/post/show/comment") // read post
	public ResponseEntity<Page<ForumPostDto>> postShowComment(
										@RequestParam Integer userId,@RequestParam Integer postId,
										@RequestParam(value="pageNumber",defaultValue = "1")Integer pageNumber) {//
		Optional<ForumUser> findLogUserResult = userAService.findById(userId);
		ForumPost post = postService.findOnePostByPostId(postId);
		if(findLogUserResult.isPresent()&&post!=null) {
			ForumUser user = findLogUserResult.get();
			Page<ForumPostDto> pageImp = postService.getCommentByPost(pageNumber,user,post);
			if (!pageImp.isEmpty()) {
				return ResponseEntity.ok(pageImp);
			}else {
				return ResponseEntity.noContent().build();
			}
		}
		return ResponseEntity.notFound().build();
	}
	
////////////////////////////////////////////////////

	// read : show all data from a table
	@GetMapping("/post/show/all") // read post
	public ResponseEntity<List<ForumPostDto>> postShowAll() {//
		List<ForumPostDto> allPosts = postService.getAllPost();
		if (allPosts != null) {
			return ResponseEntity.ok(allPosts);
		}
		return ResponseEntity.notFound().build();
	}

	@GetMapping("/comment/show") // read comment
	public ResponseEntity<List<ForumPostCommentedPost>> commentShowAll() {
		List<ForumPostCommentedPost> allComment = postService.getAllComment();
		if (allComment != null) {
			return ResponseEntity.ok(allComment);
		}
		return ResponseEntity.notFound().build();
	}


//////////////////////////// add post //////////////////////////////
	// to comment
	// with tags
	// with photos
	
	// add a row data into table
	@PostMapping("/post/add") // 必要參數： postContent, postUserId, (toCommentPostId), (tagArray)
	public ResponseEntity<ForumPostDto> postAddOne(@RequestBody Map<String,String> request) {
		
		String postContent = request.get("postContent");
		
		Integer postUserId = Integer.valueOf(request.get("postUserId"));
		
		Integer toCommentPostId = null;
		if(request.get("toCommentPostId")!=null) {
			toCommentPostId = Integer.valueOf(request.get("toCommentPostId"));
		}
		
		String tagArrayString = request.get("tagArray");
		String photoArrayString = request.get("photoArray");
		
		Integer groupId = null;
		if(request.get("groupId")!=null) {
			groupId = Integer.valueOf(request.get("groupId"));
		}
		String publicStatus = request.get("publicStatus");
		
		System.out.println("request="+request);
		
		if(postContent==null||postUserId==null) {
			return ResponseEntity.badRequest().build();
		}
		if(!(publicStatus.equals("private")||publicStatus.equals("public"))) {
			return ResponseEntity.badRequest().build();
		}
		
		Optional<ForumUser> findUserResult = userAService.findById(postUserId);
		
		//處理group
		ForumGroup group = null;
		if(groupId!=null) {
			Optional<ForumGroup> findGroupById = forumUserService.findGroupById(groupId);
			if(findGroupById.isPresent()) {
				group = findGroupById.get();
			}else {
				return ResponseEntity.notFound().build();
			}
		}
		String[] tagArray = null;
		if(tagArrayString!=null) {
			tagArray = tagArrayString.split(",");
		}
		String[] photoArray = null;
		if(photoArrayString!=null) {
			photoArray = photoArrayString.split(",");
		}
		if (findUserResult.isPresent()) {
			if (toCommentPostId != null) { // 新增某則貼文下的一則評論
				ForumPost toCommentpost = postService.findOnePostByPostId(toCommentPostId);
				if (toCommentpost != null) { // 新增一則評論
					ForumPostDto savePost = postService.addOnePost(toCommentpost, postContent, findUserResult.get(),
							tagArray,photoArray,group,publicStatus);
					if (savePost != null) {
						return ResponseEntity.ok(savePost);
					}
				} else { // 如果想評論的貼文實際上不存在
					return ResponseEntity.notFound().build();
				}
			} else { // 單純新增一則貼文
				ForumPostDto savePost = postService.addOnePost(null, postContent, findUserResult.get(),
						tagArray,photoArray,group,publicStatus);
				if (savePost != null) {
					return ResponseEntity.ok(savePost);
				}
			}
		}
		return ResponseEntity.notFound().build();
	}
	
///////////////////////////// update post //////////////////////////////
	// update a post
	@PutMapping("/post/update") // 必要參數： postContent, postUserId
	public ResponseEntity<ForumPostDto> postUpdateOne(@RequestBody Map<String,String> requestBody){
		Integer postId = Integer.valueOf(requestBody.get("postId")) ;
		String postContent = (String) requestBody.get("postContent");
		String tagArrayString = requestBody.get("tagArray");
		String photoArrayString = requestBody.get("photoArray");
		
//		System.out.println("update postId="+postId+";postContent="+postContent+";tagArrayString="+(tagArrayString!=null?tagArrayString.toString():null)+";photoArrayString="+(photoArrayString!=null?photoArrayString.toString():null));
		String[] tagArray = null;
		String[] photoArray = null;
		
		if(tagArrayString!=null) {
			tagArray = tagArrayString.split(",");
		}
		if(photoArrayString!=null) {
			photoArray =photoArrayString.split(",");
		}
		
		ForumPostDto post = postService.updateOnePost(postId, postContent, tagArray,photoArray);
		if (post != null) {
			return ResponseEntity.ok(post);
		}
		return ResponseEntity.notFound().build();
	}
	//update a post ( only public status )
	@PutMapping("/post/update/publicStatus") // 必要參數： postId, publicStatusString
	public ResponseEntity<ForumPostDto> postUpdatePublicStatus(@RequestBody Map<String,String> requestBody){
		Integer postId = Integer.valueOf(requestBody.get("postId"));
		String publicStatusString = requestBody.get("publicStatusString");
		System.out.println("postId="+postId+";publicStatusString="+publicStatusString);
		ForumPostDto post = postService.changePostPublicStatus(postId,publicStatusString); //在這個方法中會把該貼文審核通過的report刪掉
		//這邊可能會導向建立一個notify(通知發布該貼文的使用者)的controller
		if (post != null) {
			return ResponseEntity.ok(post);
		}
		return ResponseEntity.notFound().build();
	}
	//update a post // by verify : post has already received 3 reports, so your post public status has become private
	@PutMapping("/post/update/verifyToHidden") // 必要參數： postContent, postUserId
	public ResponseEntity<List<ForumReportForPostDto>> postUpdateByVerify(@RequestBody Map<String,String> request) {
		
		List<Integer> postIdList = new ArrayList<>();
		 String postIdListString = request.get("postIdList");
		 if(postIdListString!=null) {
			 String[] postIdListStringArray = postIdListString.split(",");
			 
			 for(String ele : postIdListStringArray) {
				 postIdList.add(Integer.valueOf(ele));
			 }
		 }
		 List<Integer> verifyIdList = new ArrayList<>();
		 String verifyIdListString = request.get("verifyIdList");
		 if(verifyIdListString!=null) {
			 String[] verifyIdListStringArray = verifyIdListString.split(",");
			 
			 for(String ele : verifyIdListStringArray) {
				 verifyIdList.add(Integer.valueOf(ele));
			 }
		 }
		List<ForumReportForPostDto> post = postService.hiddenPostsByVerify(postIdList,verifyIdList); //在這個方法中會把該貼文審核通過的report刪掉
		//這邊可能會導向建立一個notify(通知發布該貼文的使用者)的controller
		if (post != null) {
			return ResponseEntity.ok(post);
		}
		return ResponseEntity.notFound().build();
	}

///////////////////////////// delete post /////////////////////////////
	// delete a row
	@DeleteMapping("/post/delete") // 必要參數： postId
	public ResponseEntity<Object> postDeleteOne(@RequestParam("postId") Integer postId) {
		System.out.println("delete postId="+postId);
		boolean isDeleteSuccess = postService.deleteOnePostById(postId);
		if (isDeleteSuccess) {
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.notFound().build();
	}
	//delete post list // by verify : post has already received many reports,so your post has been deleted
	@DeleteMapping("/post/delete/verifyToDelete") // 必要參數： postId
	public ResponseEntity<Object> postDeleteByVerify(@RequestBody Map<String,String> request) {
		
		List<Integer> postIdList = new ArrayList<>();
		 String postIdListString = request.get("postIdList");
		 if(postIdListString!=null) {
			 String[] postIdListStringArray = postIdListString.split(",");
			 
			 for(String ele : postIdListStringArray) {
				 postIdList.add(Integer.valueOf(ele));
			 }
		 }
		
		boolean isDeleteSuccess = postService.deletePostsByVerify(postIdList);
		//這邊可能會導向建立一個notify(通知發布該貼文的使用者)的controller
		if (isDeleteSuccess) {
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.notFound().build();
	}



//	@DeleteMapping("/tag/delete") //必要參數： tagName
//	public ResponseEntity<ForumTag> tagDeleteOne(@RequestBody ForumTag oneTag){
//		ForumTag saveTag = tagService.addOneTag(oneTag);
//		if(saveTag!=null) {
//			return ResponseEntity.ok(saveTag);			
//		}
//		return ResponseEntity.notFound().build();
//	}

	
/////////////////////////// post interactive //////////////////////////////
	
	//////////////// like (post interactive) /////////////////
	@GetMapping("/like/show") // read like
	public ResponseEntity<List<ForumPostLike>> likeShowAll() {
		List<ForumPostLike> allLikes = postService.getAllLikes();
		if (allLikes != null) {
			return ResponseEntity.ok(allLikes);
		}
		return ResponseEntity.notFound().build();
	}
	@PostMapping("/like/change") // 必要參數：postId, userId
	public ResponseEntity<ForumPostLike> likeChange(@RequestBody Map<String, Integer> requestBody) {
		Integer postId = requestBody.get("postId");
	    Integer userId = requestBody.get("userId");
		System.out.println("postId="+postId+",userId="+userId);
		ForumPost post = postService.findOnePostByPostId(postId);
		Optional<ForumUser> findUserResult = userAService.findById(userId);
		if ((post != null) && (findUserResult.isPresent())) {
			ForumPostLike like = postInteractiveService.changeLike(post, findUserResult.get());
			if (like != null) { // 成功喜歡該post
				return ResponseEntity.ok(like);
			} else { // 成功取消喜歡
				return ResponseEntity.noContent().build();
			}
		}
		return ResponseEntity.notFound().build();
	}
	
	//////////////// forward (post interactive) /////////////////
	@PostMapping("/forward/change") // 必要參數：postId, userId
	public ResponseEntity<ForumPostForward> forwardChange(@RequestBody Map<String, Integer> requestBody) {
		Integer postId = requestBody.get("postId");
	    Integer userId = requestBody.get("userId");
		System.out.println("postId="+postId+",userId="+userId);
		ForumPost post = postService.findOnePostByPostId(postId);
		Optional<ForumUser> findUserResult = userAService.findById(userId);
		if ((post != null) && (findUserResult.isPresent())) {
			ForumPostForward forward = postInteractiveService.changeForward(post, findUserResult.get());
			if (forward != null) { // 成功喜歡該post
				return ResponseEntity.ok(forward);
			} else { // 成功取消喜歡
				return ResponseEntity.noContent().build();
			}
		}
		return ResponseEntity.notFound().build();
	}
	
	//////////////// collect (post interactive) /////////////////
	@PostMapping("/collect/change") // 必要參數：postId, userId
	public ResponseEntity<ForumPostCollect> collectChange(@RequestBody Map<String, Integer> requestBody) {
		Integer postId = requestBody.get("postId");
	    Integer userId = requestBody.get("userId");
		System.out.println("postId="+postId+",userId="+userId);
		ForumPost post = postService.findOnePostByPostId(postId);
		Optional<ForumUser> findUserResult = userAService.findById(userId);
		if ((post != null) && (findUserResult.isPresent())) {
			ForumPostCollect collect = postInteractiveService.changeCollect(post, findUserResult.get());
			if (collect != null) { // 成功喜歡該post
				return ResponseEntity.ok(collect);
			} else { // 成功取消喜歡
				return ResponseEntity.noContent().build();
			}
		}
		return ResponseEntity.notFound().build();
	}

	
	//////////////// report (post interactive) /////////////////
//	@GetMapping("/report/show/dto") //read report
//	public ResponseEntity<List<ForumReportDto>> reportShowAllDto() {
//		List<ForumReportDto> allReports = postInteractiveService.getAllReportsDto();
//		if(allReports!=null) {
//			return ResponseEntity.ok(allReports);	
//		}
//		return ResponseEntity.notFound().build();
//	}
//	@GetMapping("/report/show") //read report
//	public ResponseEntity<List<ForumPostReport>> reportShowAll() {
//		List<ForumPostReport> allReports = postInteractiveService.getAllReports();
//		if(allReports!=null) {
//			return ResponseEntity.ok(allReports);	
//		}
//		return ResponseEntity.notFound().build();
//	}
	@GetMapping("/reason/show") //read reason
	public ResponseEntity<List<Map<String, Object[]>>> reasonShowAll() {
		List<Map<String,Object[]>> allReasons = postInteractiveService.getAllReasons();
		if(allReasons!=null) {
			return ResponseEntity.ok(allReasons);	
		}
		return ResponseEntity.notFound().build();
	}
	@GetMapping("/verify/show") //read verify
	public ResponseEntity<List<ForumReportVerify>> verifyShowAll() {
		List<ForumReportVerify> allVerify = postInteractiveService.getAllVerify();
		if(allVerify!=null) {
			return ResponseEntity.ok(allVerify);	
		}
		return ResponseEntity.notFound().build();
	}
	@GetMapping("/report/show") //read 
	public ResponseEntity<List<ForumPostReport>> reportShowAll() {
		List<ForumPostReport> allVerify = postInteractiveService.getAllReports();
		if(allVerify!=null) {
			return ResponseEntity.ok(allVerify);	
		}
		return ResponseEntity.notFound().build();
	}
	
	
	@GetMapping("/report/table/show") //read 
	public ResponseEntity<List<ForumReportFormatDto>> reportShowAllTableFormat() {
		List<ForumReportFormatDto> allVerify = postInteractiveService.getAllReportsTableFormat();
		if(allVerify!=null) {
			return ResponseEntity.ok(allVerify);	
		}
		return ResponseEntity.notFound().build();
	}
	
	@GetMapping("/report/show/withVerify") //read verify
	public ResponseEntity<List<ForumReportSingleDto>> reportShowAllWithVerify(@RequestParam(defaultValue = "1") List<Integer> verifyIdList) {
		List<ForumReportVerify> verifyList = postInteractiveService.getVerifyById(verifyIdList);
		if(verifyList!=null) {
			List<ForumPostReport> allVerify = postInteractiveService.getAllReportsByVerify(verifyList);
			if(allVerify!=null) {
				List<ForumReportSingleDto> reportSingleDtos = allVerify.stream().map(ForumReportSingleDto::sendToFrontend).toList();
				return ResponseEntity.ok(reportSingleDtos);	
			}
		}
		return ResponseEntity.notFound().build();
	}
	@PostMapping("/report/add") // add report
	public ResponseEntity<ForumReportForPostDto> reportAddOne(@RequestBody Map<String,String> request) {
		
		Integer postId = Integer.valueOf(request.get("postId"));
		Integer userId = Integer.valueOf(request.get("userId"));
		Integer reasonId = Integer.valueOf(request.get("reasonId"));
		
		System.out.println("進入到此，postId="+postId+";userId="+userId+";reasonId"+reasonId);
		
		ForumPost post = postService.findOnePostByPostId(postId);
		Optional<ForumUser> findUserResult = userAService.findById(userId);
		ForumReason reason = postInteractiveService.findReasonByReasonId(reasonId);
		if(post!=null && findUserResult.isPresent() && reason!=null) {
			ForumReportForPostDto saveReport = postInteractiveService.addOneReport(post,findUserResult.get(),reason);
			if(saveReport!=null) { //順利增加一個檢舉
				return ResponseEntity.ok(saveReport);
			}else { //該使用者已經檢舉過這則貼文了
				return ResponseEntity.status(403).build();
			}
		}
		return ResponseEntity.notFound().build();
	}
	@Transactional //必要參數：Map<Integer(要轉換成的目標審核狀態verifyId),Integer[](要轉換的所有report的id)>
	@PutMapping("/report/update") 
	public ResponseEntity<List<ForumReportForPostDto>> reportUpdateStatus(@RequestBody Map<String,String[]> changeMap){
		System.out.println("changeMap="+changeMap.toString());
		if(changeMap!=null) {
			Set<Entry<String, String[]>> entrySet = changeMap.entrySet();
			List<ForumReportForPostDto> allChangedReports = new ArrayList<>();
			for(Entry<String, String[]> element : entrySet) {
				String targetStatusString = element.getKey();
				String[] targetReportIdString = element.getValue();
				Integer targetStatus = Integer.valueOf(targetStatusString);
				Integer[] targetReportId = new Integer[targetReportIdString.length];
				for (int i = 0; i < targetReportId.length; i++) {
					targetReportId[i] = Integer.valueOf(targetReportIdString[i]);
				}
//				System.out.println("");
				List<ForumReportForPostDto> hasChangedReport = postInteractiveService.changeReportStatus(targetStatus, targetReportId);
				if (hasChangedReport != null) {
	                allChangedReports.addAll(hasChangedReport);
	            }
			}
			return !allChangedReports.isEmpty() ? ResponseEntity.ok(allChangedReports) : ResponseEntity.notFound().build();
		}
		return ResponseEntity.badRequest().build();
	}
	@DeleteMapping("/report/delete")
	public ResponseEntity<List<ForumReportForPostDto>> reportDelete(@RequestBody Integer[] reportIdList){
		if(reportIdList!=null) {
			boolean result = postInteractiveService.deleteReprorts(reportIdList);
			return result ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
		}
		return ResponseEntity.badRequest().build();
		
	}
}
