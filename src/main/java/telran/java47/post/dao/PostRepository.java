package telran.java47.post.dao;

import java.util.stream.Stream;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;

import telran.java47.post.model.Post;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Set;


public interface PostRepository  extends MongoRepository<Post, String> {

	Stream<Post> findPostsByAuthor(String author);
	
	Stream<Post> findPostsByTags(List<String> tags);
	
	@Query("{'dateCreated' : { $gte: ?0, $lte: ?1 } }")  
	Stream<Post> findPostsByPeriod(LocalDate startDate,LocalDate endDate);
}
