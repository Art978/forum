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
	
//	@Query("{'scores.?0' : {'$gte': ?1}}")

//	dateCreated: {
//        $gt: ISODate("2020-01-21"),$lt: ISODate("2024-01-24")
 //   }}
	@Query( value =  "{dateCreated:{'$gte': ISODate(?0) ,'$lt' : ISODate(?1)}}")
//	@Query(value = "Select t from Post t where dateCreated BETWEEN :startDate AND :endDate")
	//public List<EntityClassTable> getAllBetweenDates(@Param("startDate")Date startDate,@Param("endDate")Date endDate);
	Stream<Post> findPostsByPeriod(@Param("startDate")LocalDate startDate,@Param("endDate") LocalDate endDate);
	
	//Stream<Post> findPostsByPeriod(LocalDate startDate,LocalDate endDate);
}
