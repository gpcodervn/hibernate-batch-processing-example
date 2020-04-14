package com.gpcoder;

import com.gpcoder.entities.Tag;
import com.gpcoder.utils.HibernateUtils;
import lombok.extern.log4j.Log4j2;
import org.hibernate.*;

@Log4j2
public class HibernateBatchUpdateExample {

    public static void main(String[] args) {
        try (SessionFactory sessionFactory = HibernateUtils.getSessionFactory();
             Session session = sessionFactory.openSession();) {

            log.info("Statistics enabled = " + sessionFactory.getStatistics().isStatisticsEnabled());

            session.beginTransaction();

            final int batchSize = 10; // same as the JDBC batch size

            try (ScrollableResults scrollableResults = session
                    .createQuery( "FROM Tag" )
                    .setCacheMode( CacheMode.IGNORE )
                    .scroll( ScrollMode.FORWARD_ONLY );
            ) {
                int count = 1;
                while ( scrollableResults.next() ) {
                    Tag tag = (Tag) scrollableResults.get( 0 );
                    tag.setName("Hibernate Batch Update " + count);
                    if ( count % batchSize == 0 && !scrollableResults.isLast()) {
                        log.info("Flush a batch of UPDATE & release memory: {} time(s)", (count / batchSize));
                        session.flush();
                        session.clear();
                    }
                    count++;
                }

                log.info("Flush the last time at commit time");
            }

            session.getTransaction().commit();
        }
    }
}
