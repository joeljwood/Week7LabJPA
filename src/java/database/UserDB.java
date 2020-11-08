package database;

import models.User;
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

    public int insert(User user) throws Exception {
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

    public int update(User user) throws Exception {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        EntityTransaction trans = em.getTransaction();
        try{
            trans.begin();
            em.merge(user);
            
        }catch(Exception e){
            
        }finally{
            em.close();
        }
    }

    public List<User> getAll() throws Exception {
         EntityManager em = DBUtil.getEmFactory().createEntityManager();
        try{
            List<User> users = em.createNamedQuery("Users.findAll", User.class);
            return users;
        } finally {
            em.close();
        }
    }

    /**
     * Get a single user by their username.
     *
     * @param username The unique username.
     * @return A User object if found, null otherwise.
     * @throws DBUtil
     */
    public User getUser(String username) throws Exception {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();

        try {
            User user = em.find(User.class, username);
            return user;
            
        } finally {
            em.close();
        }
    }

    public int delete(User user) throws DBUtil {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        String preparedQuery = "DELETE FROM users WHERE username = ?";
        PreparedStatement ps;

        try {
            ps = connection.prepareStatement(preparedQuery);
            ps.setString(1, user.getUsername());
            int rows = ps.executeUpdate();
            return rows;
        } catch (SQLException ex) {
            Logger.getLogger(UserDB.class.getName()).log(Level.SEVERE, "Cannot delete " + user.toString(), ex);
            throw new DBUtil("Error deleting User");
        } finally {
            pool.freeConnection(connection);
        }
    }
}
