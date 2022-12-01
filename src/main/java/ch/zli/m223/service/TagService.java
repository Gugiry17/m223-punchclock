package ch.zli.m223.service;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import ch.zli.m223.model.Tag;

@ApplicationScoped
public class TagService {
    @Inject
    private EntityManager entityManager;

    @Transactional
    public Tag createTag(Tag tag) {
        entityManager.persist(tag);
        return tag;
    }

    @Transactional
    public void deleteTag(Long id) {
        var entity = entityManager.find(Tag.class, id);
        entityManager.remove(entity);
    }

    @Transactional
    public Tag updateTag(Long id, Tag tag) {
        return entityManager.merge(tag);
        
    }

    public List<Tag> findAll() {
        var query = entityManager.createQuery("FROM Tag", Tag.class);
        return query.getResultList();
    }
}
