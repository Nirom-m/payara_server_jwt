package com.example.jwt_implementation.dao.generic;

import com.example.jwt_implementation.model.Entidad;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;

public abstract class GenericDao<T extends Entidad> {
    @Inject
    EntityManager em;

    private final Class<T> entityClass;

    @Inject
    public GenericDao(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    @Transactional
    public T save(T entity) {
        em.persist(entity);
        return entity;
    }

    @Transactional
    public T update(T entity) {
        var entityToUpdate = findById(entityClass, entity.getId());
        return em.merge(entity);
    }

    @Transactional
    public void delete(T entity) {
        em.remove(entity);
    }

    public List<T> findAll() {
        return em.createQuery("SELECT e FROM " + entityClass.getSimpleName() + " e", entityClass).getResultList();
    }

    public Optional<T> findById(Class<T> entityClass, Long id) {
        return Optional.ofNullable(em.find(this.entityClass, id));
    }
}
