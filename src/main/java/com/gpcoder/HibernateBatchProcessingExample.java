package com.gpcoder;

import com.gpcoder.entities.Tag;
import com.gpcoder.utils.HibernateUtils;
import lombok.extern.log4j.Log4j2;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.stat.Statistics;

@Log4j2
public class HibernateBatchProcessingExample {

    public static void main(String[] args) {
        try (SessionFactory sessionFactory = HibernateUtils.getSessionFactory();
             Session session = sessionFactory.openSession();) {

            log.info("Statistics enabled = " + sessionFactory.getStatistics().isStatisticsEnabled());

            session.beginTransaction();

            final int numberOfRecords = 30;
            final int batchSize = 10; // same as the JDBC batch size
            for (int i = 1; i <= numberOfRecords; i++) {
                Tag tag = new Tag();
                tag.setName("Hibernate Batch Processing " + i);
                session.persist(tag);

                if (i % batchSize == 0 && i != numberOfRecords) {
                    log.info("Flush a batch of INSERT & release memory: {} time(s)", (i / batchSize));
                    session.flush();
                    session.clear();
                }
            }

            log.info("Flush the last time at commit time");

            session.getTransaction().commit();
        }
    }
}
