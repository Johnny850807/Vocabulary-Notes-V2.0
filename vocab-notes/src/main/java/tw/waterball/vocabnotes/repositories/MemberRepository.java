package tw.waterball.vocabnotes.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import tw.waterball.vocabnotes.entities.Member;

@Repository
public interface MemberRepository extends CrudRepository<Member, Integer> {
}
