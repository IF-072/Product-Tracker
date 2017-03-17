package com.softserve.if072.restservice.dao.HibernateDAO;

import com.softserve.if072.common.model.History;
import com.softserve.if072.common.model.dto.HistoryDTO;
import com.softserve.if072.restservice.dao.DAOInterfaces.HistoryDAOIn;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Nazar Vynnyk
 */

@Repository
public class HistoryDAOImpl implements HistoryDAOIn {

    private SessionFactory sessionFactory;

//    @Autowired
    public HistoryDAOImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    @Transactional
    public List<History> getByUserId(int userId) {

        String hql = "select  h from History h WHERE h.user.id = :userId";
        Query query = sessionFactory.getCurrentSession().createQuery(hql);
        query.setParameter("userId", userId);

        @SuppressWarnings("unchecked")
        List<History> histories = (List<History>) query.getResultList();

        return histories;
    }

    @Override
    @Transactional
    public List<History> getByProductId(int userId, int productId) {

        String hql = "FROM History H WHERE H.user.id = :userId AND H.product.id = :productId";
        Query query = sessionFactory.getCurrentSession().createQuery(hql);
        query.setParameter("userId", userId);
        query.setParameter("productId", productId);

        @SuppressWarnings("unchecked")
        List<History> histories = (List<History>) query.list();

        return histories;
    }

    @Override
    @Transactional
    public History getByHistoryId(int historyId) {

        return sessionFactory.getCurrentSession().get(History.class, historyId);
    }

    @Override
    @Transactional
    public void insert(HistoryDTO historyDTO) {
        sessionFactory.getCurrentSession().save(historyDTO);

    }

    @Override
    @Transactional
    public void update(HistoryDTO historyDTO) {
        sessionFactory.getCurrentSession().update(historyDTO);
    }

    @Override
    @Transactional
    public void delete(int historyId) {

        History history = sessionFactory.getCurrentSession().get(History.class, historyId);
        sessionFactory.getCurrentSession().delete(history);
    }

    @Override
    @Transactional
    public void deleteAll(int userId) {
        String hql = "Delete FROM History WHERE user.id = :userId";
        Query query = sessionFactory.getCurrentSession().createQuery(hql);
        query.setParameter("userId", userId);
    }
}
