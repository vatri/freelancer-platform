package net.vatri.freelanceplatform.repositories;

import net.vatri.freelanceplatform.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("categoryRepository")
public interface CategoryRepository extends JpaRepository<Category, Long> { }