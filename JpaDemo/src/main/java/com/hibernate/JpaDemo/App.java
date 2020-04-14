package com.hibernate.JpaDemo;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

public class App 
{
   private static EntityManagerFactory entitymanagerfactory=Persistence.createEntityManagerFactory("pu");
   public static void main(String[] args) {
	try {
		addClient(103,7600,"Betty Somes");
		getClientByAccountNumber(102);
		getAllClients();
	}
	finally
	{
		entitymanagerfactory.close();
	}

}
   
   public static void addClient(int accountNumber,int amount,String clientName )
   {
	     EntityManager em = entitymanagerfactory.createEntityManager();
	      
	      Opportunity opp = new Opportunity();
	      opp.setAccountNumber(accountNumber);
	      opp.setAmount(amount);
	      opp.setClientName(clientName);
	     
	      em.getTransaction().begin();
	      em.persist(opp);
	      em.getTransaction().commit();
	      System.out.println(" Data commited ");
	      em.close();
	      System.out.println(" Session Closed ");
   }
   public static void getClientByAccountNumber(int accountNumber)
   {
	   EntityManager em = entitymanagerfactory.createEntityManager();
	   String query="SELECT op FROM Opportunity op WHERE op.accountNumber=:accountNumber";
	   TypedQuery<Opportunity> tq = em.createQuery(query, Opportunity.class);
   		tq.setParameter("custID", accountNumber);
   	
   		Opportunity o = null;
   
   		o = tq.getSingleResult();
   		System.out.println(o.getClientName() + " " + o.getAmount());
   	
   }
   
   public static void getAllClients() {
	   
	   EntityManager em = entitymanagerfactory.createEntityManager();
   	String strQuery = "SELECT o FROM Opportunity o WHERE o.accountNumber IS NOT NULL";
  
   	TypedQuery<Opportunity> tq = em.createQuery(strQuery, Opportunity.class);
   	List<Opportunity> o=tq.getResultList();
   		o.forEach(op->System.out.println(op.getAccountNumber() + " " + op.getAmount() + " " + op.getClientName()));
  
   }
   public static void deleteCustomer(int id) {
   	EntityManager em = entitymanagerfactory.createEntityManager();
      
       
           em.getTransaction().begin();
           Opportunity opp=new Opportunity();
           opp = em.find(Opportunity.class, id);
           em.remove(opp);
           ((EntityTransaction) em).commit();
      
       }
   }

