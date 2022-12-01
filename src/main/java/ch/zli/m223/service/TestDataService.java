package ch.zli.m223.service;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashSet;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import ch.zli.m223.model.Category;
import ch.zli.m223.model.Entry;
import ch.zli.m223.model.Tag;
import io.quarkus.arc.profile.IfBuildProfile;
import io.quarkus.runtime.StartupEvent;

@IfBuildProfile("dev")
@ApplicationScoped
public class TestDataService {

    @Inject
    EntityManager entityManager;

    @Transactional
    void generateTestData(@Observes StartupEvent event) {
        clearData();
        //Categories
        var projectACategory = new Category();
        projectACategory.setTitle("Project A");
        entityManager.persist(projectACategory);

        var projectBCategory = new Category();
        projectBCategory.setTitle("Project B");
        entityManager.persist(projectBCategory);
        
        var projectCCategory = new Category();
        projectCCategory.setTitle("Project C");
        entityManager.persist(projectCCategory);

        //Tags
        var programmingTag = new Tag();
        programmingTag.setTitle("Programming");
        entityManager.persist(programmingTag);

        var debuggingTag = new Tag();
        debuggingTag.setTitle("Debugging");
        entityManager.persist(debuggingTag);

        var meetingTag = new Tag();
        meetingTag.setTitle("Meeting");
        entityManager.persist(meetingTag);

        //Entries
        var firstEntry = new Entry(LocalDateTime.now().minusHours(3), LocalDateTime.now().minusHours(2));
        firstEntry.setTags(new HashSet<>(Arrays.asList(programmingTag, debuggingTag)));
        firstEntry.setCategory(projectACategory);
        entityManager.persist(firstEntry);

        var secondEntry = new Entry(LocalDateTime.now().minusHours(6), LocalDateTime.now().minusHours(3));
        secondEntry.setTags(new HashSet<>(Arrays.asList(meetingTag)));
        secondEntry.setCategory(projectBCategory);
        entityManager.persist(secondEntry);

        var thirdEntry = new Entry(LocalDateTime.now().minusHours(2), LocalDateTime.now());
        thirdEntry.setTags(new HashSet<>(Arrays.asList(debuggingTag)));
        thirdEntry.setCategory(projectCCategory);
        entityManager.persist(thirdEntry);
    }

    private void clearData() {
        entityManager.createQuery("DELETE FROM Entry").executeUpdate();
        entityManager.createQuery("DELETE FROM Tag").executeUpdate();
        entityManager.createQuery("DELETE FROM Category").executeUpdate();
    }
    
}
