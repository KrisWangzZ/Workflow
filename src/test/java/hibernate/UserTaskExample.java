package hibernate;

import engine.bpmn.process.activities.UserTask;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.util.Date;

public class UserTaskExample{

    public static void main(String[] args){

        UserTask userTask = new UserTask();

        userTask.setId("first:userTask2");

        userTask.setName("test");

        userTask.setAssignee("kris");

        userTask.setOwner("root");

        userTask.setTime(new Date());

        userTask.setSTATE(1);

        //获取加载配置管理类
        Configuration configuration = new Configuration();

        //不给参数就默认加载hibernate.cfg.xml文件，
        configuration.configure();

        //创建Session工厂对象
        SessionFactory factory = configuration.buildSessionFactory();

        //得到Session对象
        Session session = factory.openSession();

        //使用Hibernate操作数据库，都要开启事务,得到事务对象
        Transaction transaction = session.getTransaction();

        //开启事务
        transaction.begin();

        //把对象添加到数据库中
        session.save(userTask);

        try {

            //提交事务
            transaction.commit();

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            //关闭Session
            session.close();

            //关闭SessionFactory
            //hibernate建立SessionFactory的时候，会建立一个线程
            factory.close();
        }

    }

}
