package database;

import models.Users;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

public class UserDB {

    public void insert(Users user) throws Exception {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        EntityTransaction trans = em.getTransaction();
        try{
            trans.begin();
            em.persist(user);
            trans.commit();
        }catch(Exception e){
            trans.rollback();
        }finally{
            em.close();
        }    
    }

    public void update(Users user) throws Exception {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        EntityTransaction trans = em.getTransaction();
        try{
            trans.begin();
            em.merge(user);
            trans.commit();
        }catch(Exception e){
            trans.rollback();
        }finally{
            em.close();
        }
    }

    public List<Users> getAll() throws Exception {
         EntityManager em = DBUtil.getEmFactory().createEntityManager();
        try{
            List<Users> users = em.createNamedQuery("Users.findAll", Users.class).getResultList();
            return users;
        } finally {
            em.close();
        }
    }

    public Users getUser(String username) throws Exception {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();

        try {
            Users user = em.find(Users.class, username);
            return user;
            
        } finally {
            em.close();
        }
    }

    public void delete(Users user) throws Exception {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        EntityTransaction trans = em.getTransaction();
        try{
            trans.begin();
            em.remove(em.merge(user));
            trans.commit();
        }catch(Exception e){
            trans.rollback();
        }finally{
            em.close();
        } 
    }
}
