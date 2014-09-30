package flights;

import org.springframework.data.repository.CrudRepository;
import org.springframework.security.access.prepost.PreAuthorize;

public interface FlightRepository extends CrudRepository<Flight, Long> {

	//@PreAuthorize("hasAuthority('ROLE_USER')")
	@PreAuthorize("#oauth2.hasScope('write')")
	@Override
	Flight save(Flight entity);

}
