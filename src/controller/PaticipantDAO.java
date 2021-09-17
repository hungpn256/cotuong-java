package controller;
import java.util.ArrayList;

import org.hibernate.Transaction;

import model.Paticipant;

 
public class PaticipantDAO extends DAO{
    public PaticipantDAO() {
        super();
        // TODO Auto-generated constructor stub
    }
 
    /**
     * search all clients in the tblClient whose name contains the @key
     * @param key
     * @return list of client whose name contains the @key
     */
    @SuppressWarnings("unchecked")
    public ArrayList<Paticipant> searchPaticipant(String key){
        ArrayList<Paticipant> result = (ArrayList<Paticipant>)session.createQuery("from Paticipant where name like '%"+key+"%'").list();
        return result;
    }
    
    public void register(Paticipant p) {
    	Transaction trans = session.getTransaction();
    	if(!trans.isActive()) trans.begin();
    	session.save(p);
    	trans.commit();
    	return;
    }
    
    public Paticipant login(String username, String password) throws Exception {
    	Paticipant p = null;
    	p = (Paticipant)session.createQuery("from Paticipant where username = '"+username+"'").getSingleResult();
    	System.out.println("get paticipant done"+password+"   "+p.getPassword());
    	if(!password.equals(p.getPassword())) {
    		throw new Exception("sai tai khoan mat khau");
    	}
    	return p;
    }
}