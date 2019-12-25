package engine.hibernate;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtils {

    private static Configuration configuration;

    private static SessionFactory factory;

    static {
        //获取加载配置管理类
        configuration = new Configuration();

        //不给参数就默认加载hibernate.cfg.xml文件，
        configuration.configure();


    }

    public static Session getSession(){

        //创建Session工厂对象
        factory = configuration.buildSessionFactory();

        //得到Session对象
        Session session = factory.openSession();

        return session;

    }

    public static void closeSession(){
        //关闭Session
        factory.getCurrentSession().close();

    }

    public static void closeSessionFactory(){
        //关闭SessionFactory
        //hibernate建立SessionFactory的时候，会建立一个线程
        factory.close();
    }
}
