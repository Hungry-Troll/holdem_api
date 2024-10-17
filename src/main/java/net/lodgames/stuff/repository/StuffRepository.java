package net.lodgames.stuff.repository;

import net.lodgames.stuff.modle.Stuff;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StuffRepository extends JpaRepository<Stuff,Long> {
}
